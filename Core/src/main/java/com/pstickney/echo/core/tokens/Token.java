package com.pstickney.echo.core.tokens;

public class Token implements Comparable<Token>
{
	public static final int T_UNKNOWN		= 0;
	public static final int T_IDENTIFIER 	= 1;
	public static final int T_KEYWORD		= 2;
	public static final int T_SEPARATOR		= 3;
	public static final int T_OPERATOR		= 4;
	public static final int T_LITERAL		= 5;
	
	protected String TOKEN = "";
	protected int TOKEN_TYPE = T_UNKNOWN;
	
	public Token(int type, String token)
	{
		TOKEN_TYPE = type;
		TOKEN = token;
	}
	
	public int getTokenType()
	{
		return TOKEN_TYPE;
	}
	
	public String getToken()
	{
		return TOKEN;
	}
	
	@Override
	public String toString() 
	{
		return getToken();
	}

	@Override
	public boolean equals(Object obj) 
	{
		if( obj instanceof Token )
			return (getTokenType() == ((Token) obj).getTokenType() && getToken().equals(((Token) obj).getToken()));
		
		return false;
	}
	
	@Override
	public int compareTo(Token o) 
	{
		if( getTokenType() != o.getTokenType() )
			return -1;
		
		return getToken().compareTo(o.getToken());
	}
}
