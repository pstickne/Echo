package com.pstickney.echo.core.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CollectionUtils 
{
	public static Set<String> filter(Collection<String> collection, Character key)
	{
		return filter(collection, key.toString());
	}
	
	public static Set<String> filter(Collection<String> collection, String key)
	{
		Set<String> result = new HashSet<>();
		for( String k : collection )
			if( k.startsWith(key) )
				result.add(k);
		return result;
	}
	
	public static Object get(Collection<String> collection, String key)
	{
		if( collection.contains(key) )
			for( String k : collection )
				if( k.equals(key) )
					return k;
		return null;
	}
}
