package org.skycrawl.wsdemo.localservice;

import java.net.MalformedURLException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.skycrawl.wsdemo.localservice.controller.AuthorityServiceImpl;
import org.skycrawl.wsdemo.localservice.model.Data;
import org.skycrawl.wsdemo.localservice.view.Certificate;
import org.skycrawl.wsdemo.localservice.view.exceptions.SiteNotRegisteredException;

public class WSSiteToRootTest
{
	private final AuthorityServiceImpl	service;

	public WSSiteToRootTest()
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

	@Test(expected = SiteNotRegisteredException.class)
	public void testSiteNotRegistered() throws MalformedURLException, SiteNotRegisteredException
	{
		service.getRootForSite("ww.google.cz");
	}

	@Test(expected = MalformedURLException.class)
	public void testMalformedURL() throws MalformedURLException, SiteNotRegisteredException
	{
		service.getRootForSite("http:/www.google.cz");
	}

	@Test
	public void testCertificates() throws MalformedURLException, SiteNotRegisteredException
	{
		String[] sites = {
				"www.google.com",
				"translate.google.com"
		};
		Assert.assertTrue(service.registerSites(sites).loadWasACompleteSuccess());
		
		Certificate cert1 = service.getRootForSite(sites[0]);
		Certificate cert2 = service.getRootForSite(sites[1]);
		Assert.assertTrue(cert1.equals(cert2));
		Assert.assertTrue(cert1.getId() == cert2.getId());
	}
}
