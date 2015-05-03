package org.skycrawl.wsdemo.localservice;

import java.io.IOException;

import org.skycrawl.wsdemo.Logger;

public class Helpers
{
    public static final String wsdl_namespace = "http://wsdemo.skycrawl.org";
    public static final String osName = System.getProperty("os.name").replaceAll("\\s", "").toLowerCase();
    public static final boolean isAppRunningOnMac = osName.matches("macosx?");
    
    public static void appleScript(String appleScript)
    {
        try
        {
            Runtime.getRuntime().exec(new String[] { "osascript", "-e", appleScript });
        }
        catch (IOException e)
        {
            Logger.logThrowable("Not supposed to happen: ", e);
        }
    }
}
