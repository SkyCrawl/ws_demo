package org.skycrawl.wsdemo;

import org.junit.Test;
import org.skycrawl.wsdemo.localservice.controller.AuthorityServiceImpl;

public class PrintRequestInfoTest
{
	private class PrintRequestInfoTestClass extends AuthorityServiceImpl
	{
		@Override
		public void printRequestInfo(String ip)
		{
			super.printRequestInfo(ip);
		}
	}

	@Test
	public void test()
	{
		new PrintRequestInfoTestClass().printRequestInfo("centrum.cz");
	}
}
