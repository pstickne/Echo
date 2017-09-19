package com.pstickney.echo.core.tokens;

import com.pstickney.echo.core.tokens.literal.Literal;
import com.pstickney.echo.core.tokens.literal.ObjectLiteral;

public class Identifier extends Token
{
	public static final Identifier VARIABLE = new VariableIdentifier();
	
	private Literal value = null;
	
	public Identifier(String name)
	{
		super(T_IDENTIFIER, name);
		this.value = ObjectLiteral.NULL;
	}
	
	public Identifier(String name, Literal value)
	{
		super(T_IDENTIFIER, name);
		this.value = value;
	}
	
	public Literal value()				{ return value; }
	public void value(Literal value)	{ this.value = value; }
	
	@Override
	public String toString()
	{
		return "`" + getToken() + "`";
	}

	static class VariableIdentifier extends Identifier implements Comparable<Token>
	{
		public VariableIdentifier()
		{
			super("");
		}
		
		public VariableIdentifier(String name, Literal value) 
		{
			super(name, value);
		}
		
		@Override
		public boolean equals(Object obj) 
		{
			if( obj instanceof Token )
				return compareTo((Token)obj) == 0;
			
			return false;
		}
		
		@Override
		public int compareTo(Token o) 
		{
			return getTokenType() == o.getTokenType() ? 0 : 1;
		}
	}
}
