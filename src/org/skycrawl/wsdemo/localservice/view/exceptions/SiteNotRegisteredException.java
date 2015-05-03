package org.skycrawl.wsdemo.localservice.view.exceptions;

import javax.xml.ws.WebFault;

@WebFault(targetNamespace = "http://wsdemo.skycrawl.org")
public class SiteNotRegisteredException extends Exception
{
	private static final long	serialVersionUID	= 3436369899145276764L;
	
	private final String		site;

	public SiteNotRegisteredException(String site)
	{
		super("The given site has never been registered.");
		this.site = site;
	}

	public String getSite()
	{
		return this.site;
	}
}
