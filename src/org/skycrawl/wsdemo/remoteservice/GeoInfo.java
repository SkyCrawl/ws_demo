package org.skycrawl.wsdemo.remoteservice;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("query")
public class GeoInfo implements Serializable
{
	@XStreamOmitField
	private static final long	serialVersionUID	= 2089724988850812010L;
	
	@XStreamAlias("status")
	private String	status;
	@XStreamAlias("countryCode")
	private String	countryCode;
	@XStreamAlias("country")
	private String	countryName;
	@XStreamAlias("region")
	private String	regionCode;
	@XStreamAlias("regionName")
	private String	regionName;
	@XStreamAlias("city")
	private String	city;
	@XStreamAlias("zip")
	private String	postalCode;
	@XStreamAlias("lat")
	private Float	lattitude;
	@XStreamAlias("lon")
	private Float	longtitude;
	@XStreamAlias("timezone")
	private String	timezone;
	@XStreamAlias("isp")
	private String	isp;
	@XStreamAlias("org")
	private String	organizationName;
	@XStreamAlias("as")
	private String	as;
	@XStreamAlias("query")
	private String	ipAddress;

	protected GeoInfo()
	{
	}

	public GeoInfo(String status, String countryCode, String countryName,
			String regionCode, String regionName, String city,
			String postalCode, Float lattitude, Float longtitude,
			String timezone, String isp, String organizationName, String as,
			String ipAddress)
	{
		this.status = status;
		this.countryCode = countryCode;
		this.countryName = countryName;
		this.regionCode = regionCode;
		this.regionName = regionName;
		this.city = city;
		this.postalCode = postalCode;
		this.lattitude = lattitude;
		this.longtitude = longtitude;
		this.timezone = timezone;
		this.isp = isp;
		this.organizationName = organizationName;
		this.as = as;
		this.ipAddress = ipAddress;
	}

	public String getStatus()
	{
		return this.status;
	}

	public String getCountryCode()
	{
		return this.countryCode;
	}

	public String getCountryName()
	{
		return this.countryName;
	}

	public String getRegionCode()
	{
		return this.regionCode;
	}

	public String getRegionName()
	{
		return this.regionName;
	}

	public String getCity()
	{
		return this.city;
	}

	public String getPostalCode()
	{
		return this.postalCode;
	}

	public Float getLattitude()
	{
		return this.lattitude;
	}

	public Float getLongtitude()
	{
		return this.longtitude;
	}

	public String getTimezone()
	{
		return this.timezone;
	}

	public String getIsp()
	{
		return this.isp;
	}

	public String getOrganizationName()
	{
		return this.organizationName;
	}

	public String getAs()
	{
		return this.as;
	}

	public String getIpAddress()
	{
		return this.ipAddress;
	}
}
