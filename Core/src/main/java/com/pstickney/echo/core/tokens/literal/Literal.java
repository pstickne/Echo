package com.pstickney.echo.core.tokens.literal;

import com.pstickney.echo.core.tokens.Token;

public class Literal extends Token
{
	public static final int T_UNKNOWN	= (1 << 0);
	public static final int T_STRING 	= (1 << 1);
	public static final int T_NUMERIC	= (1 << 2);
	public static final int T_BOOLEAN	= (1 << 3);
	public static final int T_OBJECT	= (1 << 4);
	public static final int T_ARRAY		= (1 << 5);
	
	protected Object value = null;
	protected int LITERAL_TYPE = T_UNKNOWN;
	
	public Literal(int type)
	{
		super(T_LITERAL, "");
		LITERAL_TYPE = type;
	}
	
	public int getLiteralType()
	{
		return LITERAL_TYPE;
	}

	public void value(Object value) 
	{
		this.value = value;
	}

	public Object value() 
	{
		return this.value;
	}
}
