package com.pstickney.echo.core.stmt;

import com.pstickney.echo.core.tokens.Identifier;
import com.pstickney.echo.core.tokens.Token;

import java.util.List;

public class ImportStatement extends Statement
{
	private Identifier className;
	private List<Identifier> fullyQualifiedName;
	
	public ImportStatement(List<Token> tokens)
	{
		super(tokens);
	}
	
	@Override
	public void run()
	{

	}
}
