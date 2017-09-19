package com.pstickney.echo.core.stmt;

import com.pstickney.echo.core.tokens.Token;

import java.util.ArrayList;
import java.util.List;

public class BlockStatement extends Statement
{
	private List<Statement> statements = null;
	
	public BlockStatement()
	{
		super();
		statements = new ArrayList<>();
	}
	
	public BlockStatement(List<Token> tokens)
	{
		super(tokens);
		statements = new ArrayList<>();
	}
	
	public Boolean add(Statement stmt)
	{
		return statements.add(stmt);
	}
	
	public int length()
	{
		return statements.size();
	}
	
	@Override
	public void run()
	{
		for( int i = 0; i < statements.size(); i++ )
			statements.get(i).run();
	}
	
	@Override
	public String toString() 
	{
		StringBuilder sb = new StringBuilder();
		
		for( int i = 0; i < statements.size(); i++ )
			sb.append(statements.get(i).toString() + "\n");
			
		return sb.toString();
	}
}
