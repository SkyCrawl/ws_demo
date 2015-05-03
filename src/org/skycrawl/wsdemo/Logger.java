package org.skycrawl.wsdemo;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;

public class Logger
{
	private static final java.util.logging.Logger innerLogger = java.util.logging.Logger.getAnonymousLogger();

	public static void log(Level logLevel, String message)
	{
		Logger.innerLogger.log(logLevel, message);
	}

	public static void logThrowable(String message, Throwable t)
	{
		Logger.innerLogger.log(Level.SEVERE, String.format("exception occured: %s\n%s", message, throwableToStackTrace(t)));
	}

	private static String throwableToStackTrace(Throwable t)
	{
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		Throwable tt = t;
		while (tt != null)
		{
			tt.printStackTrace(pw);
			tt = tt.getCause();
			if (tt != null)
			{
				pw.print("caused by: ");
			}
		}
		return sw.toString();
	}
}
