package com.pstickney.echo.core.tokens;

import com.pstickney.echo.core.exceptions.UnimplementedMethodException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Operator extends Token 
{
	// Arithmetic
	public static final String T_ADDITION							= "+";
	public static final String T_SUBTRACTION						= "-";
	public static final String T_MULTIPLICATION						= "*";
	public static final String T_DIVISION							= "/";
	public static final String T_REMAINDER							= "%";
	public static final String T_EXPONENTIATION						= "**";
	public static final String T_INCREMENT							= "++";
	public static final String T_DECREMENT							= "--";

	// Relational
	public static final String T_LESS_THAN							= "<";
	public static final String T_GREATER_THAN						= ">";
	public static final String T_LESS_THAN_OR_EQUAL					= "<=";
	public static final String T_GREATER_THAN_OR_EQUAL				= ">=";
	public static final String T_EQUALITY							= "==";
	public static final String T_INEQUALITY							= "!=";
	public static final String T_STRICT_EQUALITY					= "===";
	public static final String T_STRICT_INEQUALITY					= "!==";

	// Bitwise
	public static final String T_BITWISE_NOT						= "~";
	public static final String T_BITWISE_AND						= "&";
	public static final String T_BITWISE_OR							= "|";
	public static final String T_BITWISE_XOR						= "^";
	public static final String T_BITWISE_LEFT_SHIFT					= "<<";
	public static final String T_BITWISE_RIGHT_SHIFT				= ">>";
	public static final String T_BITWISE_UNSIGNED_RIGHT_SHIFT		= ">>>";

	// Logical
	public static final String T_LOGICAL_NOT						= "!";
	public static final String T_LOGICAL_AND						= "&&";
	public static final String T_LOGICAL_OR							= "||";

	// Assignment
	public static final String T_ASSIGNMENT							= "=";
	public static final String T_ASSIGNMENT_ADDITION				= "+=";
	public static final String T_ASSIGNMENT_SUBTRACTION				= "-=";
	public static final String T_ASSIGNMENT_MULTIPLICATION			= "*=";
	public static final String T_ASSIGNMENT_DIVISION				= "/=";
	public static final String T_ASSIGNMENT_REMAINDER				= "%=";
	public static final String T_ASSIGNMENT_EXPONENTIATION			= "**=";
	public static final String T_ASSIGNMENT_LEFT_SHIFT				= "<<=";
	public static final String T_ASSIGNMENT_RIGHT_SHIFT				= ">>=";
	public static final String T_ASSIGNMENT_UNSIGNED_RIGHT_SHIFT	= ">>>=";
	public static final String T_ASSIGNMENT_BITWISE_AND				= "&=";
	public static final String T_ASSIGNMENT_BITWISE_OR				= "|=";
	public static final String T_ASSIGNMENT_BITWISE_XOR				= "^=";

	// Misc
	public static final String T_PERIOD								= ".";
	public static final String T_COMMA								= ",";

	public static final int NOT_APPLICABLE	= 0;
	public static final int LEFT_TO_RIGHT 	= 1;
	public static final int RIGHT_TO_LEFT	= 2;
	
	private static final String[] UNARY_OPERATORS = {
		T_ADDITION, T_SUBTRACTION, T_INCREMENT, T_DECREMENT, T_BITWISE_NOT, T_LOGICAL_NOT
	};
	private static final String[] BINARY_OPERATORS = {
		T_ADDITION, T_SUBTRACTION, T_MULTIPLICATION, T_DIVISION, T_REMAINDER, T_EXPONENTIATION,
		T_LESS_THAN, T_GREATER_THAN, T_LESS_THAN_OR_EQUAL, T_GREATER_THAN_OR_EQUAL, T_EQUALITY, T_INEQUALITY, T_STRICT_EQUALITY, T_STRICT_INEQUALITY,
		T_BITWISE_AND, T_BITWISE_OR, T_BITWISE_XOR, T_BITWISE_LEFT_SHIFT, T_BITWISE_RIGHT_SHIFT, T_BITWISE_UNSIGNED_RIGHT_SHIFT,
		T_LOGICAL_AND, T_LOGICAL_OR,
		T_ASSIGNMENT, T_ASSIGNMENT_ADDITION, T_ASSIGNMENT_SUBTRACTION, T_ASSIGNMENT_MULTIPLICATION, T_ASSIGNMENT_DIVISION,
		T_ASSIGNMENT_REMAINDER, T_ASSIGNMENT_EXPONENTIATION, T_ASSIGNMENT_LEFT_SHIFT, T_ASSIGNMENT_RIGHT_SHIFT, T_ASSIGNMENT_UNSIGNED_RIGHT_SHIFT,
		T_ASSIGNMENT_BITWISE_AND, T_ASSIGNMENT_BITWISE_OR, T_ASSIGNMENT_BITWISE_XOR,
		T_PERIOD, T_COMMA
	};
	private static final String[] ASSIGNMENT_OPERATORS = {
		T_ASSIGNMENT, T_ASSIGNMENT_ADDITION, T_ASSIGNMENT_SUBTRACTION, T_ASSIGNMENT_MULTIPLICATION, T_ASSIGNMENT_DIVISION,
		T_ASSIGNMENT_REMAINDER, T_ASSIGNMENT_EXPONENTIATION, T_ASSIGNMENT_LEFT_SHIFT, T_ASSIGNMENT_RIGHT_SHIFT, T_ASSIGNMENT_UNSIGNED_RIGHT_SHIFT,
		T_ASSIGNMENT_BITWISE_AND, T_ASSIGNMENT_BITWISE_OR, T_ASSIGNMENT_BITWISE_XOR
	};

	private static Set<String> unaryOperators = null;
	private static Set<String> binaryOperators = null;
	private static Set<String> assignmentOperators = null;
	private static Set<String> operators = null;
	
	protected String OPERATOR = null;
	protected int PRECEDENCE = 0; 
	protected int OPERANDS = 0;
	protected int ASSOCIATIVITY = NOT_APPLICABLE;

	public Operator(String op)
	{
		super(T_OPERATOR, op);
		
		OPERATOR = op;
	}
	
	public Operator(String op, int prec, int assoc)
	{
		super(T_OPERATOR, op);
		
		OPERATOR = op;
		PRECEDENCE = prec;
		ASSOCIATIVITY = assoc;
	}
	
	public static Set<String> getUnaryOperators()
	{
		if( unaryOperators == null )
			unaryOperators = new HashSet<>(Arrays.asList(UNARY_OPERATORS));
		return unaryOperators;
	}
	
	public static Set<String> getBinaryOperators()
	{
		if( binaryOperators == null )
			binaryOperators = new HashSet<>(Arrays.asList(BINARY_OPERATORS));
		return binaryOperators;
	}
	
	public static Set<String> getAssignmentOperators()
	{
		if( assignmentOperators == null )
			assignmentOperators = new HashSet<>(Arrays.asList(ASSIGNMENT_OPERATORS));
		return assignmentOperators;
	}
	
	public static Set<String> getOperators()
	{
		if( operators == null ) {
			operators = new HashSet<>();
			operators.addAll(getUnaryOperators());
			operators.addAll(getBinaryOperators());
			operators.addAll(getAssignmentOperators());
		}
		return operators;
	}

	public static boolean isOperator(Character c)
	{
		return isOperator(c.toString());
	}

	public static boolean isOperator(String s)
	{
		return getOperators().contains(s);
	}

	public static boolean contains(Character c)
	{
		return contains(c.toString());
	}

	public static boolean contains(String s)
	{
		for( String k : getOperators() )
			if( k.startsWith(s) )
				return true;
		return false;
	}
	
	public String operator()
	{
		return OPERATOR;
	}
	
	public int operands()
	{
		return OPERANDS;
	}
	
	public int precedence()
	{
		return PRECEDENCE;
	}
	
	public int associativity()
	{
		return ASSOCIATIVITY;
	}
	
	public Object call(Object a)
	{
		throw new UnimplementedMethodException("This method is not yet implemented");
	}
	public Object call(Object a, Object b)
	{
		throw new UnimplementedMethodException("This method is not yet implemented");
	}
}
