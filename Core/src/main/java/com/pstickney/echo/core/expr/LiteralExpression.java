package com.pstickney.echo.core.expr;

import com.pstickney.echo.core.tokens.Identifier;
import com.pstickney.echo.core.tokens.Token;
import com.pstickney.echo.core.tokens.literal.BooleanLiteral;
import com.pstickney.echo.core.tokens.literal.Literal;
import com.pstickney.echo.core.tokens.literal.NumericLiteral;
import com.pstickney.echo.core.tokens.literal.StringLiteral;

import java.util.List;

public class LiteralExpression extends Expression
{
	private Literal literal = null;
	
	public LiteralExpression(List<Token> tokens)
	{
		super(tokens);
	}

	@Override
	public Token eval() 
	{
		if( tokens.size() == 1 )
		{
			Token first = tokens.get(0);
			if( first instanceof StringLiteral || first instanceof BooleanLiteral || first instanceof NumericLiteral)
				literal = (Literal) first;
			else if( first instanceof Identifier )
				literal = ((Identifier) first).value();
		}
		return literal;
	}
}
