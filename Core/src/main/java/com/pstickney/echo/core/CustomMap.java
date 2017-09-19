package com.pstickney.echo.core;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

@SuppressWarnings("serial")
public class CustomMap<V> extends TreeMap<String, V> 
{
	public List<String> keySetMatching(String key)
	{
		List<String> list = new ArrayList<>();

		for( String k : keySet() )
			if( k.startsWith(key) )
				list.add(k);
		
		return list;
	}
	public List<String> keySetMatching(Character key)
	{
		List<String> list = new ArrayList<>();
		
		for( String k : keySet() )
			if( k.startsWith(key.toString()) )
				list.add(k);
				
		return list;
	}
	public List<String> keySetExactly(String key)
	{
		List<String> list = new ArrayList<>();
		
		for( String k : keySet() )
			if( k.equals(key) )
				list.add(k);
		
		return list;
	}
}
