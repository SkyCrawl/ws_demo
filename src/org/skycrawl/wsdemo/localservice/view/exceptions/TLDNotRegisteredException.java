package org.skycrawl.wsdemo.localservice.view.exceptions;

import javax.xml.ws.WebFault;

@WebFault(targetNamespace = "http://wsdemo.skycrawl.org")
public class TLDNotRegisteredException extends Exception
{
	private static final long	serialVersionUID	= -5084460404335817104L;

	private final String		tld;

	public TLDNotRegisteredException(String tld)
	{
		super("The given top level domain has never been used in a site being registered.");
		this.tld = tld;
	}

	public String getTld()
	{
		return this.tld;
	}
}