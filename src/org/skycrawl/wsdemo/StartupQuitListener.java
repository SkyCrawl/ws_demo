package org.skycrawl.wsdemo;

import java.util.logging.Level;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.skycrawl.wsdemo.util.cache.MyCacheManager;

public class StartupQuitListener implements ServletContextListener
{
	/**
	 * Application startup.
	 */
	public void contextInitialized(final ServletContextEvent arg0)
	{
	}

	/**
	 * Application shutdown.
	 */
	public void contextDestroyed(final ServletContextEvent arg0)
	{
		Logger.log(Level.INFO, "Closing caches...");
		MyCacheManager.destroyAllCaches();
		Logger.log(Level.INFO, "Caches closed.");
	}
}
