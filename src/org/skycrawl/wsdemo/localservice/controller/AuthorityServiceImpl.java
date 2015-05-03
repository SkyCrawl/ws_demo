package org.skycrawl.wsdemo.localservice.controller;

import java.net.MalformedURLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.skycrawl.wsdemo.Logger;
import org.skycrawl.wsdemo.localservice.Helpers;
import org.skycrawl.wsdemo.localservice.model.Data;
import org.skycrawl.wsdemo.localservice.view.Certificate;
import org.skycrawl.wsdemo.localservice.view.RegisterResult;
import org.skycrawl.wsdemo.localservice.view.StatsResult;
import org.skycrawl.wsdemo.localservice.view.exceptions.IncorrectArgumentException;
import org.skycrawl.wsdemo.localservice.view.exceptions.SiteNotRegisteredException;
import org.skycrawl.wsdemo.localservice.view.exceptions.TLDNotRegisteredException;
import org.skycrawl.wsdemo.remoteservice.GeoInfo;
import org.skycrawl.wsdemo.remoteservice.IpToGeo;

@WebService(targetNamespace = "http://wsdemo.skycrawl.org", endpointInterface = "org.skycrawl.wsdemo.localservice.controller.AuthorityService", serviceName = "AuthorityService", portName = "AuthorityServicePort")
public class AuthorityServiceImpl implements AuthorityService
{
	private final boolean logRequests;
	
	public AuthorityServiceImpl()
	{
		this(true);
	}
	
	public AuthorityServiceImpl(boolean logRequests)
	{
		this.logRequests = logRequests;
	}
	
	//-----------------------------------------------------------------------
	// SERVICE
	
	public RegisterResult registerSites(String[] sites)
	{
		try
		{
			return Data.registerSites(sites);
		}
		finally
		{
			if(logRequests)
			{
				printRequestInfo(getRequestIP());
			}
		}
	}
	
	public Certificate getRootForSite(String site) throws MalformedURLException, SiteNotRegisteredException
	{
		try
		{
			return Data.getRootForSite(site);
		}
		finally
		{
			if(logRequests)
			{
				printRequestInfo(getRequestIP());
			}
		}
	}

	public StatsResult getRootStatsForTLD(String tld) throws TLDNotRegisteredException, IncorrectArgumentException
	{
		try
		{
			return Data.getRootStatsForTLD(tld);
		}
		finally
		{
			if(logRequests)
			{
				printRequestInfo(getRequestIP());
			}
		}
	}

	public List<String> getDomainsForRootAndTLD(String rootID, String tld) throws TLDNotRegisteredException, IncorrectArgumentException
	{
		try
		{
			return (List<String>) Data.getDomainsForRootAndTLD(rootID, tld);
		}
		finally
		{
			if(logRequests)
			{
				printRequestInfo(getRequestIP());
			}
		}
	}
	
	//-----------------------------------------------------------------------
	// PRIVATE INTERFACE
	
	private String getRequestIP()
	{
		Message message = PhaseInterceptorChain.getCurrentMessage();
		HttpServletRequest request = (HttpServletRequest) message.get((Object) "HTTP.REQUEST");
		return request.getRemoteAddr();
	}

	protected void printRequestInfo(String ip)
	{
		GeoInfo ipInfo = IpToGeo.ipToGeo(ip);
		if (Helpers.isAppRunningOnMac)
		{
			Helpers.appleScript(String.format("display dialog \"%s\" with title \"Request received\" with icon caution buttons { \"Ok\" } default button 1", printInfo(ipInfo)));
		}
		else
		{
			Logger.log(Level.INFO, "Request received:\n" + this.printInfo(ipInfo));
		}
	}

	private String printInfo(GeoInfo info)
	{
		Map<String,String> map = new LinkedHashMap<String,String>();
		if (isDefined(info.getIpAddress()))
		{
			map.put("IP", info.getIpAddress());
		}
		if (isDefined(info.getCity()))
		{
			map.put("City", info.getCity());
		}
		if (isDefined(info.getCountryName()))
		{
			map.put("Country", info.getCountryName());
		}
		
		StringBuilder sb = new StringBuilder();
		for (Entry<String,String> entry : map.entrySet())
		{
			sb.append(entry.getKey());
			sb.append(": ");
			sb.append(entry.getValue());
			sb.append('\n');
		}
		return sb.toString();
	}

	private boolean isDefined(String arg)
	{
		return arg != null && !arg.isEmpty();
	}
}
