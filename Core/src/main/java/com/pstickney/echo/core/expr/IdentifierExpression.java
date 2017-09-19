package com.pstickney.echo.core.expr;

import com.pstickney.echo.core.exceptions.ParseException;
import com.pstickney.echo.core.tokens.Identifier;
import com.pstickney.echo.core.tokens.Token;

import java.util.List;

public class IdentifierExpression extends Expression
{
	public IdentifierExpression(List<Token> tokens)
	{
		super(tokens);
	}

	@Override
	public Object eval() 
	{
		if( tokens.size() != 1 )
			throw new ParseException("Error parsing identifier: " + tokens.toString());
		
		if( !(tokens.get(0) instanceof Identifier) )
			throw new ParseException("Token is not an identifier: " + tokens.toString());
		
		return ((Identifier)tokens.get(0)).value();
	}
}
