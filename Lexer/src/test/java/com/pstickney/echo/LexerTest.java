package com.pstickney.echo;

import com.pstickney.echo.core.exceptions.SyntaxException;
import com.pstickney.echo.core.tokens.Token;
import com.pstickney.echo.core.tokens.literal.NumericLiteral;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class LexerTest
{
	private List<Token> results = null;
	private List<Token> emptyResults = Arrays.asList();

	@Test
	public void shouldIgnoreComments()
	{
		results = Lexer.tokenize("// This is a line comment");
		assertEquals(0, results.size());
		assertEquals(emptyResults, results);

		results = Lexer.tokenize("/* This is a block comment */");
		assertEquals(0, results.size());
		assertEquals(emptyResults, results);

		results = Lexer.tokenize("/* This block comment" + '\n' + "can also be multi-line */");
		assertEquals(0, results.size());
		assertEquals(emptyResults, results);

		results = Lexer.tokenize("/** This is a javadoc comment */");
		assertEquals(0, results.size());
		assertEquals(emptyResults, results);

		results = Lexer.tokenize("/** This javadoc comment" + '\n' + "is also multi-line */");
		assertEquals(0, results.size());
		assertEquals(emptyResults, results);

		results = Lexer.tokenize("/**/");
		assertEquals(0, results.size());
		assertEquals(emptyResults, results);
	}

	@Test(expected = SyntaxException.class)
	public void shouldHandleCommentFailures()
	{
		results = Lexer.tokenize("/*/");
		assertEquals(0, results.size());
		assertEquals(emptyResults, results);

		results = Lexer.tokenize("/* Unterminated block comment");
		assertEquals(0, results.size());
		assertEquals(emptyResults, results);

		results = Lexer.tokenize("/** Unterminated javadoc comment");
		assertEquals(0, results.size());
		assertEquals(emptyResults, results);
	}

	@Test
	public void shouldIgnoreWhitespace()
	{
		results = Lexer.tokenize(" ");
		assertEquals(0, results.size());
		assertEquals(emptyResults, results);

		results = Lexer.tokenize("\n");
		assertEquals(0, results.size());
		assertEquals(emptyResults, results);

		results = Lexer.tokenize("\r");
		assertEquals(0, results.size());
		assertEquals(emptyResults, results);

		results = Lexer.tokenize("\t");
		assertEquals(0, results.size());
		assertEquals(emptyResults, results);

		results = Lexer.tokenize("\f");
		assertEquals(0, results.size());
		assertEquals(emptyResults, results);
	}

	@Test
	public void shouldParseNumbers()
	{
		assertEquals(Arrays.asList(NumericLiteral.ZERO), Lexer.tokenize("0"));
		assertEquals(Arrays.asList(new NumericLiteral(4.45)), Lexer.tokenize("4.45"));
		assertEquals(Arrays.asList(new NumericLiteral(1000)), Lexer.tokenize("1000"));

		assertNotEquals(Arrays.asList(new NumericLiteral(1000)), Lexer.tokenize("1,000"));
	}

	@Test
	public void shouldParseStrings()
	{

	}

	@Test
	public void lexerShouldParseCorrectly()
	{
		assertEquals(5, Lexer.tokenize("var x = 4;").size());
		assertEquals(7, Lexer.tokenize("import org.junit.Test;").size());
	}
}
