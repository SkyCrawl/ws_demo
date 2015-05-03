package org.skycrawl.wsdemo.remoteservice;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.skycrawl.wsdemo.util.cache.MyCacheManager;

public class RWSAvailableAndWorkingTest
{
	
	/*
	@BeforeClass
	public void before() throws Exception
	{
	}
	
	@Before
	public void beforeEach() throws Exception
	{
	}
	
	@After
	public void afterEach() throws Exception
	{
	}
	*/
	
	@AfterClass
	public static void after() throws Exception
	{
		MyCacheManager.destroyAllCaches();
	}

	@Test
	public void test()
	{
		GeoInfo result = IpToGeo.ipToGeo("seznam.cz");
		Assert.assertEquals(result.getOrganizationName(), "Seznam.cz, a.s.");
	}
}
