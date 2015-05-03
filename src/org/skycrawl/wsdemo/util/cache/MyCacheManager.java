package org.skycrawl.wsdemo.util.cache;

import java.io.Serializable;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;

public class MyCacheManager
{
	private static final CacheManager manager = CacheManager.create();

	public static <K extends Serializable, V extends Serializable> MyCache<K,V> create(String cacheName)
	{
		Cache innerCache = new Cache(new CacheConfiguration(cacheName, 1000)
			.eternal(true)
			.memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LRU)
		);
		manager.addCache(innerCache);
		return new MyCache<K,V>(innerCache);
	}
	
	public static void destroyAllCaches()
    {
		manager.clearAll();
    }
}
