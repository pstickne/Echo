package com.pstickney.echo.core.exceptions;

@SuppressWarnings("serial")
public class UnimplementedMethodException extends RuntimeException 
{
	public UnimplementedMethodException() 
	{
		super();
	}
	
	public UnimplementedMethodException(String message)
	{
		super(message);
	}
}
