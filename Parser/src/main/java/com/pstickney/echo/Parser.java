package com.pstickney.echo;

import com.pstickney.echo.core.exceptions.ParseException;
import com.pstickney.echo.core.stmt.BlockStatement;
import com.pstickney.echo.core.stmt.EmptyStatement;
import com.pstickney.echo.core.stmt.Statement;
import com.pstickney.echo.core.stmt.VarStatement;
import com.pstickney.echo.core.tokens.*;
import com.pstickney.echo.core.tokens.literal.Literal;

import java.util.List;

public class Parser
{
	private static int pos;
	private static Token token;
	private static List<Token> tokens;
	private static BlockStatement statements;

	public static void main(String ...args)
	{
		for( int i = 0; i < args.length; i++ )
			System.out.printf("%s\n", parse(Lexer.tokenize(args[i])));
	}

	public static BlockStatement parse(List<Token> t)
	{
		pos = -1;
		tokens = t;
		statements = new BlockStatement();

		nextToken();

		Statement stmt;
		while( (stmt = getNextStatement()) != null )
			statements.add(stmt);

		return statements;
	}

	private static void prevToken()
	{
		pos = pos > 0 ? (pos - 1) : 0;
		token = tokens.get(pos);
	}

	private static void nextToken()
	{
		pos = pos > tokens.size() ? (pos+1) : tokens.size();
		token = pos >= tokens.size() ? null : tokens.get(pos);
	}

	private static boolean inFuture(String s)
	{
		for( int i = pos; i < tokens.size(); i++ )
			if( tokens.get(i).getToken().equals(s) )
				return true;
		return false;
	}

	private static boolean inFuture(Class<? extends Token> cls)
	{
		for( int i = pos; i < tokens.size(); i++ )
			if( cls.isInstance(tokens.get(i)) )
				return true;
		return false;
	}

	private static Statement getNextStatement()
	{
		if( token == null )
			return null;

		if( token instanceof Keyword )
		{
			if( token.getToken().equals(Keyword.T_IMPORT) )
			{

			}
			else if( token.getToken().equals(Keyword.T_VAR) )
			{
				if( inFuture(Separator.class) || inFuture(Separator.T_SEMICOLON) )
					return new VarStatement(tokens.subList(pos, pos+1));
			}
		}

		if( token instanceof Separator )
		{
			if( token.getToken().equals(Separator.T_SEMICOLON) )
			{
				nextToken();
				return new EmptyStatement(tokens.subList(pos - 1, pos));
			}
		}

		if( token instanceof Operator)
		{

		}

		if( token instanceof Identifier)
		{

		}

		if( token instanceof Literal)
		{

		}

		throw new ParseException(String.format("Invalid token \"%s\"", tokens.get(pos)));
	}
}
