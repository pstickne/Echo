package com.pstickney.echo.core.expr;

import com.pstickney.echo.core.tokens.Identifier;
import com.pstickney.echo.core.tokens.Operator;
import com.pstickney.echo.core.tokens.Token;

import java.util.List;

public class AssignmentExpression extends Expression
{
	private Operator op = null;
	private Identifier lhs = null;
	private Expression rhs = null;
	
	public AssignmentExpression(List<Token> tokens)
	{
		super(tokens);
	}
	
	@Override
	public Object eval() 
	{
		return rhs.eval();
	}
}
