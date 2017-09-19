package com.pstickney.echo.core.stmt;

import com.pstickney.echo.core.tokens.Token;

import java.util.List;

public class EmptyStatement extends Statement
{
	public EmptyStatement(List<Token> tokens)
	{
		super(tokens);
	}
	
	@Override
	public void run()
	{

	}
}
