package com.pstickney.echo.core.stmt;

import com.pstickney.echo.core.tokens.Token;

import java.util.ArrayList;
import java.util.List;

public abstract class Statement
{
	protected List<Token> tokens = null;
	
	public Statement()
	{
		tokens = new ArrayList<>();
	}
	
	public Statement(List<Token> tokens)
	{
		this.tokens = tokens;
	}

	public abstract void run();
	
	@Override
	public String toString() 
	{
		StringBuilder sb = new StringBuilder();
		for( int i = 0; i < tokens.size(); i++ )
			sb.append(tokens.get(i).toString() + " ");
		return sb.toString();
	}
}
