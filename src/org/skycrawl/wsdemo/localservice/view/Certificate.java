package org.skycrawl.wsdemo.localservice.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.skycrawl.wsdemo.localservice.Helpers;

@XmlRootElement(namespace = Helpers.wsdl_namespace)
@XmlAccessorType(XmlAccessType.FIELD)
public class Certificate
{
	@XmlElement(namespace = "##default", name = "Id")
	private int		id;
	@XmlElement(namespace = "##default", name = "CommonName")
	private String	commonName;
	@XmlElement(namespace = "##default", name = "OrganizationName")
	private String	organizationName;
	@XmlElement(namespace = "##default", name = "OrganizationUnit")
	private String	organizationUnit;
	@XmlElement(namespace = "##default", name = "PublicKeyAlgorithm")
	private String	pubKeyAlg;
	@XmlElement(namespace = "##default", name = "Version")
	private int		version;
	
	protected Certificate()
	{
	}

	public Certificate(int id)
	{
		this.id = id;
	}
	
	public int getId()
	{
		return this.id;
	}

	//---------------------------------------------------------
	// GENERATED - id is intentionally ommitted from these:

	public String getCommonName()
	{
		return this.commonName;
	}

	public void setCommonName(String commonName)
	{
		this.commonName = commonName;
	}

	public String getOrganizationName()
	{
		return this.organizationName;
	}

	public void setOrganizationName(String organizationName)
	{
		this.organizationName = organizationName;
	}

	public String getOrganizationUnit()
	{
		return this.organizationUnit;
	}

	public void setOrganizationUnit(String organizationUnit)
	{
		this.organizationUnit = organizationUnit;
	}

	public String getPubKeyAlg()
	{
		return this.pubKeyAlg;
	}

	public void setPubKeyAlg(String pubKeyAlg)
	{
		this.pubKeyAlg = pubKeyAlg;
	}

	public int getVersion()
	{
		return this.version;
	}

	public void setVersion(int version)
	{
		this.version = version;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.commonName == null) ? 0 : this.commonName.hashCode());
		result = prime
				* result
				+ ((this.organizationName == null) ? 0 : this.organizationName
						.hashCode());
		result = prime
				* result
				+ ((this.organizationUnit == null) ? 0 : this.organizationUnit
						.hashCode());
		result = prime * result
				+ ((this.pubKeyAlg == null) ? 0 : this.pubKeyAlg.hashCode());
		result = prime * result + this.version;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Certificate other = (Certificate) obj;
		if (this.commonName == null)
		{
			if (other.commonName != null)
				return false;
		}
		else if (!this.commonName.equals(other.commonName))
			return false;
		if (this.organizationName == null)
		{
			if (other.organizationName != null)
				return false;
		}
		else if (!this.organizationName.equals(other.organizationName))
			return false;
		if (this.organizationUnit == null)
		{
			if (other.organizationUnit != null)
				return false;
		}
		else if (!this.organizationUnit.equals(other.organizationUnit))
			return false;
		if (this.pubKeyAlg == null)
		{
			if (other.pubKeyAlg != null)
				return false;
		}
		else if (!this.pubKeyAlg.equals(other.pubKeyAlg))
			return false;
		if (this.version != other.version)
			return false;
		return true;
	}
}
