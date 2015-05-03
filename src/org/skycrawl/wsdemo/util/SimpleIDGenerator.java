package org.skycrawl.wsdemo.util;

import java.io.*;

public class SimpleIDGenerator implements Serializable
{
    private static final long serialVersionUID = -8774263381709855657L;
    private Integer currentID;
    
    public SimpleIDGenerator()
    {
        this.reset();
    }
    
    public Integer getAndIncrement()
    {
        this.currentID++;
        return this.currentID - 1;
    }
    
    public void reset()
    {
        this.currentID = getFirstID();
    }
    
    public static Integer getFirstID()
    {
        return 0;
    }
}
