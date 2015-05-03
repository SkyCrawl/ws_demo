package org.skycrawl.wsdemo.localservice.view;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.skycrawl.wsdemo.localservice.Helpers;

@XmlRootElement(namespace = Helpers.wsdl_namespace)
@XmlAccessorType(XmlAccessType.FIELD)
public class StatsResult
{
    @XmlElement(namespace = "##default", name = "Stats")
    private Map<Certificate, Integer> stats;
    
    protected StatsResult()
    {
        this.stats = new HashMap<Certificate, Integer>();
    }
    
    public StatsResult(Map<Certificate, Integer> stats)
    {
        this.stats = stats;
    }
    
    public Map<Certificate, Integer> getStats()
    {
        return this.stats;
    }
}
