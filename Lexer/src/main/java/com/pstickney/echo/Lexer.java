package com.pstickney.echo;

import com.pstickney.echo.core.exceptions.SyntaxException;
import com.pstickney.echo.core.tokens.*;
import com.pstickney.echo.core.tokens.literal.NumericLiteral;
import com.pstickney.echo.core.tokens.literal.ObjectLiteral;
import com.pstickney.echo.core.tokens.literal.StringLiteral;

import java.util.ArrayList;
import java.util.List;

public class Lexer
{
	private static int pos;
	private static Character c;
	private static String script;
	private static List<Token> tokens;

	public static void main(String ...args)
	{
		for( int i = 0; i < args.length; i++ )
			System.out.printf("%s\n", tokenize(args[i]));
	}

	public static List<Token> tokenize(String s)
	{
		pos = -1;
		script = s;
		tokens = new ArrayList<>();

		nextChar();

		Token token;
		while( (token = getNextToken()) != null )
			tokens.add(token);

		return tokens;
	}

	private static void prevChar()
	{
		pos = pos > 0 ? (pos-1) : 0;
		c = script.charAt(pos);
	}

	private static void nextChar()
	{
		pos = pos < script.length() ? (pos+1) : script.length();
		c = pos >= script.length() ? null : script.charAt(pos);
	}

	private static boolean isIdentifierStart(Character c)
	{
		return Character.isLetter(c) || c == '_' || c == '$';
	}

	private static boolean removeLeadingWhitespace()
	{
		if( c == null )
			return true;

		while( Character.isWhitespace(c) ) {
			nextChar();

			if( c == null )
				return true;
		}
		return false;
	}

	private static Token getNextToken()
	{
		StringBuilder buffer;

		if( c == null )
			return null;

		if( removeLeadingWhitespace() )
			return null;

		// Check for comments
		if( c == '/' ) {
			nextChar();

			// Line comment
			if( c == '/' ) {
				while( c != '\n' ) {
					nextChar();

					if( c == null )
						return null;
				}
			}

			// Block comment
			else if( c == '*' ) {
				nextChar();
				while( true ) {
					while( c != '*' ) {
						nextChar();

						if( c == null )
							throw new SyntaxException("Unterminated Block Comment");
					}
					nextChar();
					if( c == '/' ) {
						nextChar();
						break;
					}
				}
			}


			// Not a comment
			else {
				prevChar();
			}
		}


		if( removeLeadingWhitespace() )
			return null;


		// Check for numbers
		if( Character.isDigit(c) ) {
			buffer = new StringBuilder();
			while( c != null && (Character.isDigit(c) || c == '.') ) {
				buffer.append(c);
				nextChar();
			}
			return new NumericLiteral(Double.valueOf(buffer.toString()));
		}


		// Check for strings
		if( c == '"' ) {
			buffer = new StringBuilder();
			nextChar();
			while( c != '"' ) {
				buffer.append(c);
				nextChar();

				if( c == null )
					throw new SyntaxException("Unterminated String literal");
			}
			nextChar();
			return new StringLiteral(buffer.toString());
		}


		// Check for separators
		if( Separator.contains(c) ) {
			buffer = new StringBuilder();
			buffer.append(c);

			while( Separator.contains(buffer.toString()) ) {
				nextChar();

				if( c == null ) {
					buffer.append(" ");
					break;
				}

				buffer.append(c);
			}
			buffer.deleteCharAt(buffer.length() - 1);

			return new Separator(buffer.toString());
		}


		// Check for operators
		if( Operator.contains(c) ) {
			buffer = new StringBuilder();
			buffer.append(c);

			while( Operator.contains(buffer.toString()) ) {
				nextChar();

				if( c == null ) {
					buffer.append(" ");
					break;
				}

				buffer.append(c);
			}

			buffer.deleteCharAt(buffer.length() - 1);

			return new Operator(buffer.toString());
		}


		// Everything else is an identifier
		if( isIdentifierStart(c) ) {
			buffer = new StringBuilder();

			while( Character.isLetterOrDigit(c) ) {
				buffer.append(c);
				nextChar();
			}

			if( Keyword.isKeyword(buffer.toString()) )
				return new Keyword(buffer.toString());
			else
				return new Identifier(buffer.toString(), ObjectLiteral.NULL);
		}

		// Don't know what this is so throw an error.
		throw new SyntaxException(String.format("Invalid token: \"%s\" at position %d", c, pos+1));
	}
}
