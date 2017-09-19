package com.pstickney.echo.core.tokens.literal;

import java.util.ArrayList;
import java.util.List;

public class ArrayLiteral extends Literal 
{
	private List<Literal> value = null;
	
	public ArrayLiteral() 
	{
		super(T_ARRAY);
		value = new ArrayList<Literal>();
	}
	
	public ArrayLiteral(List<Literal> list)
	{
		super(T_ARRAY);
		value = list;
	}
	
	public boolean add(Literal item)
	{
		return value.add(item);
	}
	
	public void value(List<Literal> list)
	{
		value = list;
	}
	
	@Override
	public List<Literal> value() 
	{
		return value;
	}

	@Override
	public String toString()
	{
		return value.toString();
	}
}
