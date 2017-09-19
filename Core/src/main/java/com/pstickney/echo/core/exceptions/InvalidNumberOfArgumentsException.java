package com.pstickney.echo.core.exceptions;

@SuppressWarnings("serial")
public class InvalidNumberOfArgumentsException extends RuntimeException 
{
	public InvalidNumberOfArgumentsException() 
	{
		super();
	}
	
	public InvalidNumberOfArgumentsException(String message)
	{
		super(message);
	}
}
