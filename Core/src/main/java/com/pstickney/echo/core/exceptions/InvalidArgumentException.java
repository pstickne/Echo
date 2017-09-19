package com.pstickney.echo.core.exceptions;

@SuppressWarnings("serial")
public class InvalidArgumentException extends RuntimeException 
{
	public InvalidArgumentException() 
	{
		super();
	}
	
	public InvalidArgumentException(String message)
	{
		super(message);
	}
}
