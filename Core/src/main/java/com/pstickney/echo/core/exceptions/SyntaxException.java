package com.pstickney.echo.core.exceptions;

@SuppressWarnings("serial")
public class SyntaxException extends RuntimeException
{
	public SyntaxException()
	{
		super();
	}
	
	public SyntaxException(String message)
	{
		super(message);
	}
}
