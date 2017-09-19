package com.pstickney.echo.core.stmt;

import com.pstickney.echo.core.expr.Expression;
import com.pstickney.echo.core.tokens.Token;
import com.pstickney.echo.core.util.ConversionUtils;

import java.util.List;

public class IfStatement extends Statement
{
	private static Expression conditional = null;
	private static BlockStatement thenBlock = null;
	private static BlockStatement elseBlock = null;
	
	public IfStatement(List<Token> tokens)
	{
		super(tokens);
	}

	@Override
	public void run()
	{
		if( ConversionUtils.toBooleanLiteral(conditional.eval()).value() && thenBlock != null ) {
			thenBlock.run();
			return;
		}
		
		if( elseBlock != null ) {
			elseBlock.run();
			return;
		}
	}
}
