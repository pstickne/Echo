package com.pstickney.echo.core.tokens.literal;


public class StringLiteral extends Literal 
{
	private String value = null;
	
	public StringLiteral() 
	{
		super(T_STRING);
	}
	
	public StringLiteral(String val)
	{
		this();
		value = val;
	}
	
	public void value(String val)
	{
		value = val;
	}
	
	@Override
	public String value()
	{
		return value;
	}

	@Override
	public String toString() 
	{
		return "\"" + value + "\"";
	}
}
