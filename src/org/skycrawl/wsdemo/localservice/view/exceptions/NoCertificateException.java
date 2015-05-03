package org.skycrawl.wsdemo.localservice.view.exceptions;

import javax.xml.ws.WebFault;

@WebFault(targetNamespace = "http://wsdemo.skycrawl.org")
public class NoCertificateException extends Exception
{
	private static final long	serialVersionUID	= -3086359429503629723L;

	private final String		domain;

	public NoCertificateException(String domain)
	{
		super("The given domain either can not be reached or doesn't provide any certificate authorities.");
		this.domain = domain;
	}

	public String getDomain()
	{
		return this.domain;
	}
}
