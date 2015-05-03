package org.skycrawl.wsdemo.remoteservice;

import org.skycrawl.wsdemo.util.cache.MyCache;
import org.skycrawl.wsdemo.util.cache.MyCacheManager;

import br.com.caelum.restfulie.Response;
import br.com.caelum.restfulie.Restfulie;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class IpToGeo
{
    private static final MyCache<String,GeoInfo> cache = MyCacheManager.create("remoteGeoServiceCache"); 
    private static final XStream xstream = new XStream(new XppDriver());
    static
    {
        xstream.processAnnotations(GeoInfo.class);
    }
    
    public static GeoInfo ipToGeo(String ip)
    {
    	// preliminary processing and checks
        ip = ip.trim();
        if (ip.equals("127.0.0.1"))
        {
            return new GeoInfo(null, null, null, null, null, "The comfortable home of myself", null, null, null, null, null, null, null, ip);
        }
        
        // if the given ip is suitable to be resolved:
        if (cache.containsKey(ip))
        {
        	// first look into cache
            return cache.get(ip);
        }
        else
        {
        	// otherwise resolve and cache
        	Response resp = Restfulie.at("http://ip-api.com/xml/".concat(ip)).get();
            GeoInfo info = (GeoInfo) xstream.fromXML(resp.getContent());
            cache.put(ip, info);
            return info;
        }
    }
}
