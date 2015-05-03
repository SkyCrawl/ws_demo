package org.skycrawl.wsdemo.localservice.model;

import java.io.IOException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.skycrawl.wsdemo.Logger;
import org.skycrawl.wsdemo.localservice.view.Certificate;
import org.skycrawl.wsdemo.localservice.view.RegisterResult;
import org.skycrawl.wsdemo.localservice.view.StatsResult;
import org.skycrawl.wsdemo.localservice.view.exceptions.IncorrectArgumentException;
import org.skycrawl.wsdemo.localservice.view.exceptions.NoCertificateException;
import org.skycrawl.wsdemo.localservice.view.exceptions.SiteNotRegisteredException;
import org.skycrawl.wsdemo.localservice.view.exceptions.TLDNotRegisteredException;
import org.skycrawl.wsdemo.util.SimpleIDGenerator;

public class Data
{
	private static final Map<Certificate,Certificate>	roots = new HashMap<Certificate,Certificate>();
	private static final Map<String,TLDData>			tldToData = new HashMap<String,TLDData>();
	private static final SimpleIDGenerator				rootIDGenerator = new SimpleIDGenerator();

	public static RegisterResult registerSites(String[] sites)
	{
		RegisterResult result = new RegisterResult();
		for (String site : sites)
		{
			if (site == null || site.isEmpty())
			{
				result.getMalformed().add(site);
				continue;
			}
			
			URL url = null;
			try
			{
				url = siteToURL(site);
			}
			catch (MalformedURLException e2)
			{
				result.getMalformed().add(site);
				continue;
			}
			
			String tld = extractTLD(url.getHost());
			if (!tldToData.containsKey(tld))
			{
				tldToData.put(tld, new TLDData());
			}
			if (tldToData.get(tld).contains(url.getHost()))
			{
				result.getDuplicate().add(site);
				continue;
			}
			
			try
			{
				Certificate cert = downloadRoot(url, result);
				if (roots.containsKey(cert))
				{
					cert = roots.get(cert);
				}
				else
				{
					roots.put(cert, cert);
				}
				tldToData.get(tld).register(url.getHost(), cert);
			}
			catch (IncorrectArgumentException e3)
			{
				result.getMalformed().add(url.toExternalForm());
			}
			catch (ConnectException e4)
			{
				result.getBare().add(url.toExternalForm());
			}
			catch (NoCertificateException e5)
			{
				result.getBare().add(url.toExternalForm());
			}
			catch (IOException e)
			{
				result.getIgnored().add(url.toExternalForm());
			}
			catch (Exception e)
			{
				Logger.logThrowable("Not supposed to happen: ", e);
				result.getIgnored().add(url.toExternalForm());
			}
		}
		return result;
	}

	public static Certificate getRootForSite(String site) throws MalformedURLException, SiteNotRegisteredException
	{
		URL url = siteToURL(site);
		String tld = extractTLD(url.getHost());
		if (!tldToData.containsKey(tld) || !tldToData.get(tld).contains(url.getHost()))
		{
			throw new SiteNotRegisteredException(site);
		}
		return tldToData.get(tld).getCertificate(url.getHost());
	}

	public static StatsResult getRootStatsForTLD(String tld) throws TLDNotRegisteredException, IncorrectArgumentException
	{
		validate_tld(tld);
		if (!tldToData.containsKey(tld))
		{
			throw new TLDNotRegisteredException(tld);
		}
		return new StatsResult(tldToData.get(tld).map());
	}

	public static List<String> getDomainsForRootAndTLD(String rootID, String tld) throws TLDNotRegisteredException, IncorrectArgumentException
	{
		validate_tld(tld);
		if (!tldToData.containsKey(tld))
		{
			throw new TLDNotRegisteredException(tld);
		}
		try
		{
			int id = Integer.parseInt(rootID);
			if (id >= 0)
			{
				return tldToData.get(tld).getDomainsForRoot(id);
			}
			else
			{
				throw new IncorrectArgumentException("The given root id is not a non-negative integer number.", rootID);
			}
		}
		catch (NumberFormatException e)
		{
			throw new IncorrectArgumentException("The given root id is not an integer number.", rootID);
		}
	}

	public static void clear()
	{
		roots.clear();
		tldToData.clear();
		rootIDGenerator.reset();
	}
	
	//-----------------------------------------------------------------------
	// PRIVATE INTERFACE

	private static void validate_tld(String tld) throws IncorrectArgumentException
	{
		if (tld.contains("."))
		{
			throw new IncorrectArgumentException("The given top-level domain contains a dot and thus is NOT a top-level domain.", tld);
		}
	}

	private static URL siteToURL(String site) throws MalformedURLException
	{
		URL result = null;
		if (site.startsWith("http") || site.startsWith("https"))
		{
			result = new URL(site);
		}
		else
		{
			result = new URL("https://".concat(site));
		}
		if (result.getHost() == null || result.getHost().isEmpty())
		{
			throw new MalformedURLException(String.format("Could not determine host from the given URL ('%s').", site));
		}
		return result;
	}

	private static String extractTLD(String host)
	{
		String[] components = host.split("\\.");
		return components[components.length - 1].toLowerCase();
	}

	private static Certificate downloadRoot(URL url, RegisterResult result) throws IOException, NoCertificateException, IncorrectArgumentException
	{
		HttpsURLConnection con = null;
		try
		{
			con = (HttpsURLConnection) url.openConnection();
		}
		catch (ClassCastException e)
		{
			throw new IncorrectArgumentException("The given URL declares HTTP scheme. Either remove it or change it to https.", url.toExternalForm());
		}
		try
		{
			con.connect();
		}
		catch (UnknownHostException e2)
		{
			throw new NoCertificateException(url.getHost());
		}
		catch (SSLException e)
		{
			throw new IOException(e);
		}
		
		X509Certificate[] certs = (X509Certificate[]) con.getServerCertificates();
		if (certs.length == 0)
		{
			throw new NoCertificateException(url.getHost());
		}
		return toDBObject(certs[certs.length - 1]);
	}

	private static Certificate toDBObject(X509Certificate cert) throws IOException
	{
		Certificate result = new Certificate(rootIDGenerator.getAndIncrement());
		result.setVersion(cert.getVersion());
		result.setPubKeyAlg(cert.getPublicKey().getAlgorithm());
		fillIssuerInfo(result, cert.getIssuerX500Principal().getName("RFC2253"));
		return result;
	}

	private static void fillIssuerInfo(Certificate to, String from) throws IOException
	{
		CSVParser parser = CSVParser.parse(from.replace("\\\\,", "\\,"), getCSVFormat()); 
		for (CSVRecord csvRecord : parser)
		{
			for (String column : csvRecord)
			{
				if (column.startsWith("O="))
				{
					to.setOrganizationName(column.substring(2));
				}
				else if (column.startsWith("OU="))
				{
					to.setOrganizationUnit(column.substring(3));
				}
				else if (column.startsWith("CN="))
				{
					to.setCommonName(column.substring(3));
				}
			}
		}
	}
	
	private static CSVFormat getCSVFormat()
	{
		return CSVFormat.newFormat(',')
				.withAllowMissingColumnNames(true)
				.withIgnoreEmptyLines(false)
				.withIgnoreSurroundingSpaces(true)
				.withQuote('"')
				.withEscape('\\');
	}
}
