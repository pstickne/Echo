package com.pstickney.echo.core.tokens;

import com.pstickney.echo.core.tokens.literal.ObjectLiteral;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Keyword extends Token
{
	public static final String T_IMPORT			= "import";
	public static final String T_VAR			= "var";
	public static final String T_NULL			= "null";
	public static final String T_TRUE			= "true";
	public static final String T_FALSE			= "false";
	public static final String T_IF				= "if";
	public static final String T_ELSE			= "else";
	public static final String T_IN				= "in";
	public static final String T_NEW			= "new";
	public static final String T_TYPEOF			= "typeof";
	public static final String T_INSTANCEOF		= "instanceof";

	private static final String[] KEYWORDS = {
		T_IMPORT, T_VAR,
		T_NULL, T_TRUE, T_FALSE,
		T_IF, T_ELSE,
		T_IN, T_NEW, T_TYPEOF, T_INSTANCEOF
	};
	
	private static Set<String> keywords = null;
	
	public Keyword(String word)
	{
		super(T_KEYWORD, word);
	}
	
	public static Set<String> getKeywords()
	{
		if( keywords == null ) {
			keywords = new HashSet<>();
			keywords.addAll(Arrays.asList(KEYWORDS));
		}
		return keywords;
	}

	public static boolean isKeyword(Character c)
	{
		return isKeyword(c.toString());
	}

	public static boolean isKeyword(String s)
	{
		return getKeywords().contains(s);
	}

	public static boolean contains(Character c)
	{
		return contains(c.toString());
	}

	public static boolean contains(String s)
	{
		for( String k : getKeywords() )
			if( k.startsWith(s) )
				return true;
		return false;
	}
	
	public Token call(Token ...arguments)
	{
		return ObjectLiteral.NULL;
	}
}
