package org.skycrawl.wsdemo.localservice.view;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.skycrawl.wsdemo.localservice.Helpers;

@XmlRootElement(namespace = Helpers.wsdl_namespace)
@XmlAccessorType(XmlAccessType.FIELD)
public class RegisterResult
{
	@XmlElement(namespace = "##default", name = "Malformed")
	private List<String>	malformed;
	@XmlElement(namespace = "##default", name = "Duplicate")
	private List<String>	duplicate;
	@XmlElement(namespace = "##default", name = "Bare")
	private List<String>	bare;
	@XmlElement(namespace = "##default", name = "Ignored")
	private List<String>	ignored;

	public RegisterResult()
	{
		this.malformed = new ArrayList<String>();
		this.duplicate = new ArrayList<String>();
		this.bare = new ArrayList<String>();
		this.ignored = new ArrayList<String>();
	}

	public List<String> getMalformed()
	{
		return this.malformed;
	}

	public List<String> getDuplicate()
	{
		return this.duplicate;
	}

	public List<String> getBare()
	{
		return this.bare;
	}

	public List<String> getIgnored()
	{
		return this.ignored;
	}

	public boolean loadWasACompleteSuccess()
	{
		return this.malformed.isEmpty() && this.duplicate.isEmpty() && this.bare.isEmpty() && this.ignored.isEmpty();
	}
}
