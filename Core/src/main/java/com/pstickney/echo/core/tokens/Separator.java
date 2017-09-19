package com.pstickney.echo.core.tokens;

import com.pstickney.echo.core.util.CollectionUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class Separator extends Token 
{
	public static final String T_SEMICOLON		= ";";
	public static final String T_OPEN_PAREN		= "(";
	public static final String T_CLOSE_PAREN	= ")";
	public static final String T_OPEN_BRACE		= "{";
	public static final String T_CLOSE_BRACE	= "}";
	public static final String T_OPEN_BRACKET	= "[";
	public static final String T_CLOSE_BRACKET	= "]";
	
	private static final String[] SEPARATORS = {
		T_SEMICOLON, 
		T_OPEN_PAREN, T_CLOSE_PAREN, 
		T_OPEN_BRACE, T_CLOSE_BRACE, 
		T_OPEN_BRACKET, T_CLOSE_BRACKET
	};
	private static final String[] OPEN_SEPARATORS = {
		T_OPEN_PAREN, T_OPEN_BRACE, T_OPEN_BRACKET
	};
	private static final String[] CLOSE_SEPARATORS = {
		T_CLOSE_PAREN, T_CLOSE_BRACE, T_CLOSE_BRACKET
	};
	
	private static Set<String> openSeparators = null;
	private static Set<String> closeSEparators = null;
	private static Set<String> separators = null;
	
	public Separator(String value)
	{
		super(T_SEPARATOR, value);
	}
	
	public static Set<String> getOpenSeparators()
	{
		if( openSeparators == null )
			openSeparators = new HashSet<>(Arrays.asList(OPEN_SEPARATORS));
		return openSeparators;
	}
	
	public static Set<String> getCloseSeparators()
	{
		if( closeSEparators == null )
			closeSEparators = new HashSet<>(Arrays.asList(CLOSE_SEPARATORS));
		return closeSEparators;
	}
	
	public static Set<String> getSeparators()
	{
		if( separators == null ) {
			separators = new HashSet<>();
			separators.addAll(Arrays.asList(SEPARATORS));
			separators.addAll(getOpenSeparators());
			separators.addAll(getCloseSeparators());
		}
		return separators;
	}

	public static boolean isSeparator(Character c)
	{
		return isSeparator(c.toString());
	}

	public static boolean isSeparator(String s)
	{
		return getSeparators().contains(s);
	}

	public static boolean contains(Character c)
	{
		return contains(c.toString());
	}

	public static boolean contains(String s)
	{
		for( String k : getSeparators() )
			if( k.startsWith(s) )
				return true;
		return false;
	}
}
