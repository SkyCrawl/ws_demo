package org.skycrawl.wsdemo.localservice;

import java.net.MalformedURLException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.skycrawl.wsdemo.localservice.controller.AuthorityServiceImpl;
import org.skycrawl.wsdemo.localservice.model.Data;
import org.skycrawl.wsdemo.localservice.view.Certificate;
import org.skycrawl.wsdemo.localservice.view.StatsResult;
import org.skycrawl.wsdemo.localservice.view.exceptions.IncorrectArgumentException;
import org.skycrawl.wsdemo.localservice.view.exceptions.SiteNotRegisteredException;
import org.skycrawl.wsdemo.localservice.view.exceptions.TLDNotRegisteredException;

public class WSRootStatsForTLDTest
{
	private final AuthorityServiceImpl	service;

	public WSRootStatsForTLDTest()
	{
		this.service = new AuthorityServiceImpl(false);
	}
	
	/*
	@BeforeClass
	public void before() throws Exception
	{
	}
	
	@AfterClass
	public void after() throws Exception
	{
	}
	
	@After
	public void afterEach() throws Exception
	{
	}
	*/
	
	@Before
	public void beforeEach() throws Exception
	{
		Data.clear();
	}

	@Test(expected = TLDNotRegisteredException.class)
	public void testSiteNotRegistered() throws TLDNotRegisteredException, IncorrectArgumentException
	{
		service.getRootStatsForTLD("test");
	}

	@Test
	public void testStats() throws TLDNotRegisteredException, MalformedURLException, SiteNotRegisteredException, IncorrectArgumentException
	{
		String[] sites = {
				"www.google.com",
				"translate.google.com",
				"facebook.com"
		};
		Assert.assertTrue(service.registerSites(sites).loadWasACompleteSuccess());
		
		StatsResult stats = service.getRootStatsForTLD("com");
		Assert.assertTrue(stats.getStats().size() == 2);
		Certificate cert = service.getRootForSite(sites[0]);
		Assert.assertTrue(stats.getStats().get(cert) == 2);
	}
}
