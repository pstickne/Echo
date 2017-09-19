package com.pstickney.echo.core.expr;

import com.pstickney.echo.core.stmt.Statement;
import com.pstickney.echo.core.tokens.Token;

import java.util.List;

public abstract class Expression extends Statement
{
	public Expression(List<Token> tokens)
	{
		super(tokens);
	}
	
	@Override
	public void run()
	{
		eval();
	}
	
	public abstract Object eval();
}
