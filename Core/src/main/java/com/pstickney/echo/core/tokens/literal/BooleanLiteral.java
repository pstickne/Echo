package com.pstickney.echo.core.tokens.literal;

public class BooleanLiteral extends Literal 
{
	public static final BooleanLiteral TRUE = new BooleanLiteral(true);
	public static final BooleanLiteral FALSE = new BooleanLiteral(false);
	
	public BooleanLiteral(Boolean val)
	{
		super(T_BOOLEAN);
		value = val;
	}
	
	public void value(Boolean value) 
	{
		super.value(value);
	}
	
	@Override
	public Boolean value() 
	{
		return (Boolean) super.value();
	}
	
	@Override
	public String toString() 
	{
		return value() ? "TRUE" : "FALSE";
	}
}
