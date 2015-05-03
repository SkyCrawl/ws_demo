package org.skycrawl.wsdemo.localservice.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.skycrawl.wsdemo.localservice.view.Certificate;

public class TLDData
{
	private final Map<String,Certificate> siteToRoot;

	public TLDData()
	{
		this.siteToRoot = new HashMap<String,Certificate>();
	}

	public boolean contains(String domain)
	{
		return siteToRoot.containsKey(domain);
	}

	public void register(String domain, Certificate root)
	{
		if (siteToRoot.containsKey(domain))
		{
			throw new IllegalArgumentException(String.format("The '%s' site is already registered.", domain));
		}
		siteToRoot.put(domain, root);
	}

	public Certificate getCertificate(String domain)
	{
		return siteToRoot.get(domain);
	}

	public List<String> getDomainsForRoot(int rootID)
	{
		List<String> result = new ArrayList<String>();
		for (Map.Entry<String,Certificate> entry : siteToRoot.entrySet())
		{
			if (entry.getValue().getId() == rootID)
			{
				result.add(entry.getKey());
			}
		}
		return result;
	}

	public Map<Certificate,Integer> map()
	{
		Map<Certificate,Integer> result = new HashMap<Certificate,Integer>();
		for (Certificate cert : siteToRoot.values())
		{
			if (result.containsKey(cert))
			{
				result.put(cert, result.get(cert) + 1);
			}
			else
			{
				result.put(cert, 1);
			}
		}
		return result;
	}
}
