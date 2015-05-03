package org.skycrawl.wsdemo;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.skycrawl.wsdemo.localservice.WSRegisterTest;
import org.skycrawl.wsdemo.localservice.WSRootStatsForTLDTest;
import org.skycrawl.wsdemo.localservice.WSSiteToRootTest;
import org.skycrawl.wsdemo.localservice.WSSitesForRootAndTLDTest;
import org.skycrawl.wsdemo.remoteservice.RWSAvailableAndWorkingTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	WSRegisterTest.class,
	WSSiteToRootTest.class,
	WSRootStatsForTLDTest.class,
	WSSitesForRootAndTLDTest.class,
	RWSAvailableAndWorkingTest.class,
	PrintRequestInfoTest.class
})
public class AllTests
{
}
