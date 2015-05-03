package org.skycrawl.wsdemo.localservice;

import java.net.MalformedURLException;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.skycrawl.wsdemo.localservice.controller.AuthorityServiceImpl;
import org.skycrawl.wsdemo.localservice.model.Data;
import org.skycrawl.wsdemo.localservice.view.Certificate;
import org.skycrawl.wsdemo.localservice.view.exceptions.IncorrectArgumentException;
import org.skycrawl.wsdemo.localservice.view.exceptions.SiteNotRegisteredException;
import org.skycrawl.wsdemo.localservice.view.exceptions.TLDNotRegisteredException;

public class WSSitesForRootAndTLDTest
{
	private final AuthorityServiceImpl	service;

	public WSSitesForRootAndTLDTest()
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
	public void testTLDNotRegistered() throws TLDNotRegisteredException, IncorrectArgumentException
	{
		service.getDomainsForRootAndTLD("-1", "test");
	}

	@Test(expected = IncorrectArgumentException.class)
	public void testIncorrectID() throws TLDNotRegisteredException, IncorrectArgumentException
	{
		Assert.assertTrue(service.registerSites(new String[] { "www.twitter.com" }).loadWasACompleteSuccess());
		service.getDomainsForRootAndTLD("bla", "com");
	}

	@Test
	public void testDomainsBasic() throws TLDNotRegisteredException, IncorrectArgumentException, MalformedURLException, SiteNotRegisteredException
	{
		String[] sites = {
				"www.google.com",
				"translate.google.com",
				"facebook.com"
		};
		Assert.assertTrue(service.registerSites(sites).loadWasACompleteSuccess());
		
		Certificate cert = service.getRootForSite(sites[0]);
		List<String> certDomains = (List<String>) service.getDomainsForRootAndTLD(String.valueOf(cert.getId()), "com");
		Assert.assertTrue(certDomains.size() == 2);
		Assert.assertTrue(certDomains.contains(sites[0]));
		Assert.assertTrue(certDomains.contains(sites[1]));
		
		Certificate cert2 = service.getRootForSite(sites[2]);
		List<String> cert1Domains = (List<String>) service.getDomainsForRootAndTLD(String.valueOf(cert2.getId()), "com");
		Assert.assertTrue(cert1Domains.size() == 1);
		Assert.assertTrue(cert1Domains.contains(sites[2]));
	}

	@Test
	public void testDomainsAdvanced() throws TLDNotRegisteredException, IncorrectArgumentException, MalformedURLException, SiteNotRegisteredException
	{
		String[] sites = {
				"https://ww.google.com"
		};
		Assert.assertTrue(service.registerSites(sites).loadWasACompleteSuccess());
		
		Certificate cert = service.getRootForSite(sites[0]);
		List<String> certDomains = (List<String>) service.getDomainsForRootAndTLD(String.valueOf(cert.getId()), "com");
		Assert.assertTrue(certDomains.size() == 1);
		Assert.assertTrue(certDomains.contains("ww.google.com"));
	}
}
