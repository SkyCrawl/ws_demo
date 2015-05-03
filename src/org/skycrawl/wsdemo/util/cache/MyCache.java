package org.skycrawl.wsdemo.util.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

@SuppressWarnings("unchecked")
public class MyCache<K extends Serializable, V extends Serializable> implements Map<K,V>
{
	private final Cache innerCache;
	
	public MyCache(Cache innerCache)
	{
		this.innerCache = innerCache;
	}
	
	public int size()
	{
		return innerCache.getSize();
	}

	public boolean isEmpty()
	{
		return size() == 0;
	}

	public boolean containsKey(Object key)
	{
		return get(key) != null;
	}

	public boolean containsValue(Object value)
	{
		return values().contains(value);
	}

	public V get(Object key)
	{
		Element elem = innerCache.get(key);
		return elem == null ? null : (V) elem.getKey();
	}

	public V put(K key, V value)
	{
		Element elem = new Element(key, value);
		innerCache.put(elem);
		return value;
	}

	public V remove(Object key)
	{
		Element elem = innerCache.removeAndReturnElement(key);
		return elem == null ? null : (V) elem.getValue();
	}

	public void putAll(Map<? extends K,? extends V> m)
	{
		if(m != null)
		{
			for(Entry<? extends K,? extends V> entry : m.entrySet())
			{
				put(entry.getKey(), entry.getValue());
			}
		}
	}

	public void clear()
	{
		innerCache.removeAll();
	}

	public Set<K> keySet()
	{
		return new HashSet<K>(innerCache.getKeysWithExpiryCheck());
	}

	public Collection<V> values()
	{
		Collection<Element> elems = innerCache.getAll(innerCache.getKeysWithExpiryCheck()).values();
		List<V> result = new ArrayList<V>();
		for(Element elem : elems)
		{
			result.add((V) elem.getValue());
		}
		return result;
	}

	public Set<Entry<K,V>> entrySet()
	{
		Map<Object, Element> elems = innerCache.getAll(innerCache.getKeysWithExpiryCheck());
		Set<Entry<K,V>> result = new HashSet<Entry<K,V>>();
		for(final Entry<Object, Element> entry : elems.entrySet())
		{
			result.add(new Entry<K,V>()
			{
				private V value = (V) entry.getValue().getValue(); 
				
				public K getKey()
				{
					return (K) entry.getValue().getKey();
				}

				public V getValue()
				{
					return value;
				}

				public V setValue(V value)
				{
					this.value = value;
					return value;
				}
			});
		}
		return result;
	}
}
