package org.skycrawl.wsdemo.localservice;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.skycrawl.wsdemo.localservice.controller.AuthorityServiceImpl;
import org.skycrawl.wsdemo.localservice.model.Data;

public class WSRegisterTest
{
	private final AuthorityServiceImpl	service;

	public WSRegisterTest()
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

	@Test
	public void testInvalidSites()
	{
		Assert.assertFalse(service.registerSites(new String[] { null }).getMalformed().isEmpty());
		Assert.assertFalse(service.registerSites(new String[] { "" }).getMalformed().isEmpty());
		Assert.assertFalse(service.registerSites(new String[] { "www.regular-expressions.info" }).getIgnored().isEmpty());
		Assert.assertFalse(service.registerSites(new String[] { "recepty.vareni.cz" }).getBare().isEmpty());
		Assert.assertFalse(service.registerSites(new String[] { "http://www.google.com" }).getMalformed().isEmpty());
		Assert.assertFalse(service.registerSites(new String[] { "www.muj-totalni-vymysl.test" }).getBare().isEmpty());
	}

	@Test
	public void testValidSites()
	{
		Assert.assertTrue(service.registerSites(new String[] { "www.amazon.com" }).loadWasACompleteSuccess());
	}

	@Test
	public void testDuplicateSites()
	{
		Assert.assertTrue(service.registerSites(new String[] { "www.google.cz" }).loadWasACompleteSuccess());
		Assert.assertFalse(service.registerSites(new String[] { "www.google.cz" }).getDuplicate().isEmpty());
	}
}
