package com.pstickney.echo.core.exceptions;

@SuppressWarnings("serial")
public class ParseException extends RuntimeException
{
	public ParseException()
	{
		super();
	}
	
	public ParseException(String message)
	{
		super(message);
	}
}
