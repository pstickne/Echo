package com.pstickney.echo.core.tokens.literal;

public class NumericLiteral extends Literal 
{
	public static final NumericLiteral ZERO = new NumericLiteral(0);
	public static final NumericLiteral ONE = new NumericLiteral(1);
	public static final NumericLiteral NaN = new NumericLiteral(Double.NaN);
	
	public NumericLiteral(int val)
	{
		this((double)val);
	}

	public NumericLiteral(Double val)
	{
		super(T_NUMERIC);
		value(val);
	}
	
	public void value(Double val)
	{
		super.value(val);
	}
	
	@Override
	public Double value()
	{
		return (Double) super.value();
	}

	@Override
	public String toString() 
	{
		return String.format(((value() % 1 == 0) ? "%.0f":"%s"), value());
	}
}
