package org.skycrawl.wsdemo.localservice.view.exceptions;

import javax.xml.ws.WebFault;

@WebFault(targetNamespace = "http://wsdemo.skycrawl.org")
public class IncorrectArgumentException extends Exception
{
	private static final long	serialVersionUID	= 4479078257043128071L;
	
	private final Object		argument;

	public IncorrectArgumentException(String message, Object argument)
	{
		super(message);
		this.argument = argument;
	}

	public Object getArgument()
	{
		return this.argument;
	}
}
