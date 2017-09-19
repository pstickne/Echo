package com.pstickney.echo.core.util;

import com.pstickney.echo.core.tokens.literal.BooleanLiteral;
import com.pstickney.echo.core.tokens.literal.Literal;
import com.pstickney.echo.core.tokens.literal.NumericLiteral;
import com.pstickney.echo.core.tokens.literal.StringLiteral;

public class ConversionUtils
{
	public static Literal toLiteral(Object token)
	{
		if( token instanceof Literal )
			return (Literal) token;
		return null;
	}
	
	public static NumericLiteral toNumericLiteral(Object token)
	{
		NumericLiteral val = null;
		if( token instanceof BooleanLiteral)		val = ((BooleanLiteral) token).value() ? NumericLiteral.ONE : NumericLiteral.ZERO;
		else if( token instanceof NumericLiteral )	val = (NumericLiteral) token;
		else if( token instanceof StringLiteral)
			try {
				String tokenValue = ((StringLiteral) token).value();
				val = ( tokenValue == null || tokenValue.trim().equals("") ) ? NumericLiteral.ZERO : new NumericLiteral(Double.valueOf(tokenValue));
			} catch (Exception e) {
				val = NumericLiteral.NaN;
			}
		return val;
	}
	
	public static BooleanLiteral toBooleanLiteral(Object token)
	{
		BooleanLiteral val = null;
		if( token instanceof BooleanLiteral )		val = (BooleanLiteral) token;
		else if( token instanceof NumericLiteral )	val = ((NumericLiteral) token).value() == 0 ? BooleanLiteral.FALSE : BooleanLiteral.TRUE;
		else if( token instanceof StringLiteral )	
			val = 	((StringLiteral) token).value().equals("") || 
					((StringLiteral) token).value().equals("0") ? BooleanLiteral.FALSE : BooleanLiteral.TRUE;
		return val;
	}
	
	public static StringLiteral toStringLiteral(Object token)
	{
		StringLiteral val = null;
		if( token instanceof BooleanLiteral )		val = new StringLiteral(((BooleanLiteral) token).value() ? "true" : "");
		else if( token instanceof NumericLiteral )	val = new StringLiteral("" + ((NumericLiteral) token).value());
		else if( token instanceof StringLiteral )	val = (StringLiteral) token;
		return val;
	}
}
