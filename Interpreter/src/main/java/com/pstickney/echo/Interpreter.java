package com.pstickney.echo;

import com.pstickney.echo.core.CustomMap;
import com.pstickney.echo.core.exceptions.InvalidArgumentException;
import com.pstickney.echo.core.exceptions.UnimplementedMethodException;
import com.pstickney.echo.core.tokens.*;
import com.pstickney.echo.core.tokens.literal.*;
import com.pstickney.echo.core.util.ConversionUtils;
import com.pstickney.echo.reflect.Reflect;

public class Interpreter
{
	private static CustomMap<Keyword> keywords = new CustomMap<>();
	private static CustomMap<Operator> operators = new CustomMap<>();
	private static CustomMap<Separator> separators = new CustomMap<>();
	private static CustomMap<Identifier> identifiers = new CustomMap<>();

	public static Keyword defineKeyword(Keyword word)
	{
		return keywords.put(word.getToken(), word);
	}

	public static Operator defineOperator(Operator op)
	{
		return operators.put(op.operator(), op);
	}

	public static Separator defineSeparator(Separator separator)
	{
		return separators.put(separator.getToken(), separator);
	}

	public static Identifier defineVariable(Identifier id)
	{
		return identifiers.put(id.getToken(), id);
	}

	public static CustomMap<Keyword> getKeywords()
	{
		return keywords;
	}
	public static CustomMap<Operator> getOperators()
	{
		return operators;
	}
	public static CustomMap<Separator> getSeparators()
	{
		return separators;
	}
	public static CustomMap<Identifier> getIdentifiers()
	{
		return identifiers;
	}

	static {
		defineKeyword(new Keyword("import") {
			@Override
			public Token call(Token... arguments) {
				throw new UnimplementedMethodException("This method is not yet implemented");
			}
		});
		defineKeyword(new Keyword("var") {
			@Override
			public Token call(Token... arguments) {
				throw new UnimplementedMethodException("This method is not yet implemented");
			}
		});
		defineKeyword(new Keyword("null") {
			@Override
			public Token call(Token... arguments) {
				return ObjectLiteral.NULL;
			}
		});
		defineKeyword(new Keyword("true") {
			@Override
			public Token call(Token... arguments) {
				return BooleanLiteral.TRUE;
			}
		});
		defineKeyword(new Keyword("false") {
			@Override
			public Token call(Token... arguments) {
				return BooleanLiteral.FALSE;
			}
		});
		defineKeyword(new Keyword("if") {
			@Override
			public Token call(Token... arguments) {
				throw new UnimplementedMethodException("This method is not yet implemented");
			}
		});
		defineKeyword(new Keyword("else") {
			@Override
			public Token call(Token... arguments) {
				throw new UnimplementedMethodException("This method is not yet implemented");
			}
		});

		defineSeparator(new Separator("("));
		defineSeparator(new Separator(")"));
		defineSeparator(new Separator("{"));
		defineSeparator(new Separator("}"));
		defineSeparator(new Separator("["));
		defineSeparator(new Separator("]"));
		defineSeparator(new Separator(","));
		defineSeparator(new Separator(";"));


		// Keyword Operators
		defineOperator(new Operator("typeof", 15, Operator.RIGHT_TO_LEFT) {
			@Override
			public Object call(Object a) {
				if( a instanceof BooleanLiteral )
					return new StringLiteral("boolean");
				else if( a instanceof NumericLiteral)
					return new StringLiteral("number");
				else if( a instanceof StringLiteral )
					return new StringLiteral("string");
				else if( a instanceof ObjectLiteral || a instanceof ArrayLiteral)
					return new StringLiteral("object");

				return new StringLiteral("undefined");
			}
		});
		defineOperator(new Operator("delete", 15, Operator.RIGHT_TO_LEFT) {
			@Override
			public Object call(Object a) {
				return BooleanLiteral.TRUE;
			}
		});
		defineOperator(new Operator("in", 11, Operator.LEFT_TO_RIGHT) {
			@Override
			public Object call(Object a, Object b) {
				if( b instanceof ObjectLiteral ) {

				}
				return BooleanLiteral.FALSE;
			}
		});
		defineOperator(new Operator("instanceof", 11, Operator.LEFT_TO_RIGHT) {
			@Override
			public Object call(Object a, Object b) {
				return b.getClass().isAssignableFrom(a.getClass()) ? BooleanLiteral.TRUE : BooleanLiteral.FALSE;
			}
		});
		defineOperator(new Operator("is", 11, Operator.LEFT_TO_RIGHT) {
			@Override
			public Object call(Object a, Object b) {
				return operators.get("instanceof").call(a, b);
			}
		});

		defineOperator(new Operator("new", 18, Operator.NOT_APPLICABLE) {
			@Override
			public Object call(Object a) {
				try {
					return Reflect.constructor("", ((Token)a).getToken());
				} catch (Exception e) {
					e.printStackTrace();
				}
				return ObjectLiteral.NULL;
			}
		});

		defineOperator(new Operator(".", 18, Operator.LEFT_TO_RIGHT) {
			@Override
			public Object call(Object a, Object b) {
				if( a instanceof Identifier && b instanceof Identifier )
				{

				}
				return null;
			}
		});
		defineOperator(new Operator(Operator.T_ADDITION, 13, Operator.LEFT_TO_RIGHT) {
			@Override
			public Object call(Object a) {
				if( a instanceof BooleanLiteral )		return new NumericLiteral(+ConversionUtils.toNumericLiteral(a).value());
				else if( a instanceof NumericLiteral )	return new NumericLiteral(+((NumericLiteral)a).value());
				else if( a instanceof StringLiteral )	return new NumericLiteral(ConversionUtils.toNumericLiteral(a).value());
				else if( a instanceof Identifier )		return operators.get(T_ADDITION).call(((Identifier) a).value());

				throw new InvalidArgumentException("Incompatible operand type");
			}
			@Override
			public Object call(Object a, Object b) {
				if( a instanceof BooleanLiteral && b instanceof BooleanLiteral )		return new NumericLiteral(ConversionUtils.toNumericLiteral(a).value() + ConversionUtils.toNumericLiteral(b).value());
				else if( a instanceof BooleanLiteral && b instanceof NumericLiteral )	return new NumericLiteral(ConversionUtils.toNumericLiteral(a).value() + ((NumericLiteral) b).value());
				else if( a instanceof BooleanLiteral && b instanceof StringLiteral )	return new StringLiteral((((BooleanLiteral) a).value() ? "true" : "false") + ((StringLiteral) b).value());
				else if( a instanceof NumericLiteral && b instanceof BooleanLiteral )	return new NumericLiteral(((NumericLiteral) a).value() + ConversionUtils.toNumericLiteral(b).value());
				else if( a instanceof NumericLiteral && b instanceof NumericLiteral )	return new NumericLiteral(((NumericLiteral) a).value() + ((NumericLiteral) b).value());
				else if( a instanceof NumericLiteral && b instanceof StringLiteral )	return new StringLiteral(ConversionUtils.toStringLiteral(a).value() + ((StringLiteral) b).value());
				else if( a instanceof StringLiteral && b instanceof BooleanLiteral )	return new StringLiteral(((StringLiteral) a).value() + (((BooleanLiteral) b).value() ? "true" : "false"));
				else if( a instanceof StringLiteral && b instanceof NumericLiteral )	return new StringLiteral(((StringLiteral) a).value() + ConversionUtils.toStringLiteral(b).value());
				else if( a instanceof StringLiteral && b instanceof StringLiteral )		return new StringLiteral(((StringLiteral) a).value() + ((StringLiteral) b).value());

				throw new InvalidArgumentException("Incompatible operand types");
			}
		});
		defineOperator(new Operator(Operator.T_SUBTRACTION, 13, Operator.LEFT_TO_RIGHT) {
			@Override
			public Object call(Object a) {
				if( a instanceof BooleanLiteral )		return new NumericLiteral(-ConversionUtils.toNumericLiteral(a).value());
				else if( a instanceof NumericLiteral )	return new NumericLiteral(-((NumericLiteral)a).value());
				else if( a instanceof StringLiteral )	return ConversionUtils.toNumericLiteral(a).value();
				else if( a instanceof Identifier )		return operators.get(T_SUBTRACTION).call(((Identifier) a).value());

				throw new InvalidArgumentException("Incompatible operand type");
			}
			@Override
			public Object call(Object a, Object b) {
				if( a instanceof StringLiteral || b instanceof StringLiteral )			return NumericLiteral.NaN;
				if( a instanceof BooleanLiteral && b instanceof BooleanLiteral )		return new NumericLiteral(ConversionUtils.toNumericLiteral(a).value() - ConversionUtils.toNumericLiteral(b).value());
				else if( a instanceof BooleanLiteral && b instanceof NumericLiteral )	return new NumericLiteral(ConversionUtils.toNumericLiteral(a).value() - ((NumericLiteral) b).value());
				else if( a instanceof NumericLiteral && b instanceof BooleanLiteral )	return new NumericLiteral(((NumericLiteral) a).value() - ConversionUtils.toNumericLiteral(b).value());
				else if( a instanceof NumericLiteral && b instanceof NumericLiteral )	return new NumericLiteral(((NumericLiteral) a).value() - ((NumericLiteral) b).value());

				throw new InvalidArgumentException("Incompatible operand types");
			}
		});
		defineOperator(new Operator("*", 14, Operator.LEFT_TO_RIGHT) {
			@Override
			public Object call(Object a, Object b) {
				if( a instanceof StringLiteral || b instanceof StringLiteral )			return NumericLiteral.NaN;
				if( a instanceof BooleanLiteral && b instanceof BooleanLiteral )		return new NumericLiteral(ConversionUtils.toNumericLiteral(a).value() * ConversionUtils.toNumericLiteral(b).value());
				else if( a instanceof BooleanLiteral && b instanceof NumericLiteral )	return new NumericLiteral(ConversionUtils.toNumericLiteral(a).value() * ((NumericLiteral) b).value());
				else if( a instanceof NumericLiteral && b instanceof BooleanLiteral )	return new NumericLiteral(((NumericLiteral) a).value() * ConversionUtils.toNumericLiteral(b).value());
				else if( a instanceof NumericLiteral && b instanceof NumericLiteral )	return new NumericLiteral(((NumericLiteral) a).value() * ((NumericLiteral) b).value());

				throw new InvalidArgumentException("Incompatible operand types");
			}
		});
		defineOperator(new Operator("/", 14, Operator.LEFT_TO_RIGHT) {
			@Override
			public Object call(Object a, Object b) {
				if( a instanceof StringLiteral || b instanceof StringLiteral )			return NumericLiteral.NaN;
				if( a instanceof BooleanLiteral && b instanceof BooleanLiteral )		return new NumericLiteral(ConversionUtils.toNumericLiteral(a).value() / ConversionUtils.toNumericLiteral(b).value());
				else if( a instanceof BooleanLiteral && b instanceof NumericLiteral )	return new NumericLiteral(ConversionUtils.toNumericLiteral(a).value() / ((NumericLiteral) b).value());
				else if( a instanceof NumericLiteral && b instanceof BooleanLiteral )	return new NumericLiteral(((NumericLiteral) a).value() / ConversionUtils.toNumericLiteral(b).value());
				else if( a instanceof NumericLiteral && b instanceof NumericLiteral )	return new NumericLiteral(((NumericLiteral) a).value() / ((NumericLiteral) b).value());

				throw new InvalidArgumentException("Incompatible operand types");
			}
		});
		defineOperator(new Operator("%", 14, Operator.LEFT_TO_RIGHT) {
			@Override
			public Object call(Object a, Object b) {
				if( a instanceof StringLiteral || b instanceof StringLiteral )			return NumericLiteral.NaN;
				if( a instanceof BooleanLiteral && b instanceof BooleanLiteral )		return new NumericLiteral(ConversionUtils.toNumericLiteral(a).value() % ConversionUtils.toNumericLiteral(b).value());
				else if( a instanceof BooleanLiteral && b instanceof NumericLiteral )	return new NumericLiteral(ConversionUtils.toNumericLiteral(a).value() % ((NumericLiteral) b).value());
				else if( a instanceof NumericLiteral && b instanceof BooleanLiteral )	return new NumericLiteral(((NumericLiteral) a).value() % ConversionUtils.toNumericLiteral(b).value());
				else if( a instanceof NumericLiteral && b instanceof NumericLiteral )	return new NumericLiteral(((NumericLiteral) a).value() % ((NumericLiteral) b).value());

				throw new InvalidArgumentException("Incompatible operand types");
			}
		});
		defineOperator(new Operator("**", 14, Operator.RIGHT_TO_LEFT) {
			@Override
			public Object call(Object a, Object b) {
				if( a instanceof StringLiteral || b instanceof StringLiteral )			return NumericLiteral.NaN;
				if( a instanceof BooleanLiteral && b instanceof BooleanLiteral )		return new NumericLiteral(Math.pow(ConversionUtils.toNumericLiteral(a).value(), ConversionUtils.toNumericLiteral(b).value()));
				else if( a instanceof BooleanLiteral && b instanceof NumericLiteral )	return new NumericLiteral(Math.pow(ConversionUtils.toNumericLiteral(a).value(), ((NumericLiteral) b).value()));
				else if( a instanceof NumericLiteral && b instanceof BooleanLiteral )	return new NumericLiteral(Math.pow(((NumericLiteral) a).value(), ConversionUtils.toNumericLiteral(b).value()));
				else if( a instanceof NumericLiteral && b instanceof NumericLiteral )	return new NumericLiteral(Math.pow(((NumericLiteral) a).value(), ((NumericLiteral) b).value()));

				throw new InvalidArgumentException("Incompatible operand types");
			}
		});
		defineOperator(new Operator(Operator.T_INCREMENT, 16, Operator.NOT_APPLICABLE) {
			@Override
			public Object call(Object a, Object b) {
				if( a instanceof Identifier )
				{
					((Identifier) a).value((Literal) operators.get(Operator.T_ADDITION).call(((Identifier) a).value(), NumericLiteral.ONE));
					return (Identifier) a;
				}
				throw new InvalidArgumentException("Invalid increment operand");
			}
		});


		// =========== Logical =========== //
		defineOperator(new Operator("&&", 6, Operator.LEFT_TO_RIGHT) {
			@Override
			public Object call(Object a, Object b) {
				return ConversionUtils.toBooleanLiteral(!ConversionUtils.toBooleanLiteral(a).value() ? a : b);
			}
		});
		defineOperator(new Operator("||", 5, Operator.LEFT_TO_RIGHT) {
			@Override
			public Object call(Object a, Object b) {
				return ConversionUtils.toBooleanLiteral(ConversionUtils.toBooleanLiteral(a).value() ? a : b);
			}
		});
		defineOperator(new Operator("!", 15, Operator.RIGHT_TO_LEFT) {
			@Override
			public Object call(Object a) {
				return ConversionUtils.toBooleanLiteral(a).value() ? BooleanLiteral.FALSE : BooleanLiteral.TRUE;
			}
		});


		// =========== Bitwise =========== //
		defineOperator(new Operator("<<", 12, Operator.LEFT_TO_RIGHT) {
			@Override
			public Object call(Object a, Object b) {
				return new NumericLiteral(ConversionUtils.toNumericLiteral(a).value().intValue() << ConversionUtils.toNumericLiteral(b).value().intValue());
			}
		});
		defineOperator(new Operator(">>", 12, Operator.LEFT_TO_RIGHT) {
			@Override
			public Object call(Object a, Object b) {
				return new NumericLiteral(ConversionUtils.toNumericLiteral(a).value().intValue() >> ConversionUtils.toNumericLiteral(b).value().intValue());
			}
		});
		defineOperator(new Operator(">>>", 12, Operator.LEFT_TO_RIGHT) {
			@Override
			public Object call(Object a, Object b) {
				return new NumericLiteral(ConversionUtils.toNumericLiteral(a).value().intValue() >>> ConversionUtils.toNumericLiteral(b).value().intValue());
			}
		});
		defineOperator(new Operator("&", 9, Operator.LEFT_TO_RIGHT) {
			@Override
			public Object call(Object a, Object b) {
				return new NumericLiteral(ConversionUtils.toNumericLiteral(a).value().intValue() & ConversionUtils.toNumericLiteral(b).value().intValue());
			}
		});
		defineOperator(new Operator("^", 8, Operator.LEFT_TO_RIGHT) {
			@Override
			public Object call(Object a, Object b) {
				return new NumericLiteral(ConversionUtils.toNumericLiteral(a).value().intValue() ^ ConversionUtils.toNumericLiteral(b).value().intValue());
			}
		});
		defineOperator(new Operator("|", 7, Operator.LEFT_TO_RIGHT) {
			@Override
			public Object call(Object a, Object b) {
				return new NumericLiteral(ConversionUtils.toNumericLiteral(a).value().intValue() | ConversionUtils.toNumericLiteral(b).value().intValue());
			}
		});
		defineOperator(new Operator("~", 15, Operator.RIGHT_TO_LEFT) {
			@Override
			public Object call(Object a) {
				return new NumericLiteral(~ConversionUtils.toNumericLiteral(a).value().intValue());
			}
		});


		// =========== Equality =========== //
		defineOperator(new Operator("==", 10, Operator.LEFT_TO_RIGHT) {
			@Override
			public Object call(Object a, Object b) {
				if( a instanceof BooleanLiteral && b instanceof BooleanLiteral )		return ((BooleanLiteral) a).value() == ((BooleanLiteral) b).value() ? BooleanLiteral.TRUE : BooleanLiteral.FALSE;
				else if( a instanceof BooleanLiteral && b instanceof NumericLiteral )	return ConversionUtils.toNumericLiteral(a).value() == ((NumericLiteral) b).value() ? BooleanLiteral.TRUE : BooleanLiteral.FALSE;
				else if( a instanceof BooleanLiteral && b instanceof StringLiteral )	return ((BooleanLiteral) a).value() == ConversionUtils.toBooleanLiteral(b).value() ? BooleanLiteral.TRUE : BooleanLiteral.FALSE;
				else if( a instanceof NumericLiteral && b instanceof BooleanLiteral )	return ((NumericLiteral) a).value() == ConversionUtils.toNumericLiteral(b).value() ? BooleanLiteral.TRUE : BooleanLiteral.FALSE;
				else if( a instanceof NumericLiteral && b instanceof NumericLiteral )	return ((NumericLiteral) a).value() == ((NumericLiteral) b).value() ? BooleanLiteral.TRUE : BooleanLiteral.FALSE;
				else if( a instanceof NumericLiteral && b instanceof StringLiteral )	return ((NumericLiteral) a).value() == ConversionUtils.toNumericLiteral(b).value() ? BooleanLiteral.TRUE : BooleanLiteral.FALSE;
				else if( a instanceof StringLiteral && b instanceof BooleanLiteral )	return ConversionUtils.toBooleanLiteral(a).value() == ((BooleanLiteral) b).value() ? BooleanLiteral.TRUE : BooleanLiteral.FALSE;
				else if( a instanceof StringLiteral && b instanceof NumericLiteral )	return ConversionUtils.toNumericLiteral(a).value() == ((NumericLiteral) b).value() ? BooleanLiteral.TRUE : BooleanLiteral.FALSE;
				else if( a instanceof StringLiteral && b instanceof StringLiteral )		return ((StringLiteral) a).value().equals(((StringLiteral) b).value()) ? BooleanLiteral.TRUE : BooleanLiteral.FALSE;

				throw new InvalidArgumentException("Incompatible operand types");
			}
		});
		defineOperator(new Operator("!=", 10, Operator.LEFT_TO_RIGHT) {
			@Override
			public Object call(Object a, Object b) {
				if( a instanceof BooleanLiteral && b instanceof BooleanLiteral )		return ((BooleanLiteral) a).value() != ((BooleanLiteral) b).value() ? BooleanLiteral.TRUE : BooleanLiteral.FALSE;
				else if( a instanceof BooleanLiteral && b instanceof NumericLiteral )	return ConversionUtils.toNumericLiteral(a).value() != ((NumericLiteral) b).value() ? BooleanLiteral.TRUE : BooleanLiteral.FALSE;
				else if( a instanceof BooleanLiteral && b instanceof StringLiteral )	return ((BooleanLiteral) a).value() != ConversionUtils.toBooleanLiteral(b).value() ? BooleanLiteral.TRUE : BooleanLiteral.FALSE;
				else if( a instanceof NumericLiteral && b instanceof BooleanLiteral )	return ((NumericLiteral) a).value() != ConversionUtils.toNumericLiteral(b).value() ? BooleanLiteral.TRUE : BooleanLiteral.FALSE;
				else if( a instanceof NumericLiteral && b instanceof NumericLiteral )	return ((NumericLiteral) a).value() != ((NumericLiteral) b).value() ? BooleanLiteral.TRUE : BooleanLiteral.FALSE;
				else if( a instanceof NumericLiteral && b instanceof StringLiteral )	return ((NumericLiteral) a).value() != ConversionUtils.toNumericLiteral(b).value() ? BooleanLiteral.TRUE : BooleanLiteral.FALSE;
				else if( a instanceof StringLiteral && b instanceof BooleanLiteral )	return ConversionUtils.toBooleanLiteral(a).value() != ((BooleanLiteral) b).value() ? BooleanLiteral.TRUE : BooleanLiteral.FALSE;
				else if( a instanceof StringLiteral && b instanceof NumericLiteral )	return ConversionUtils.toNumericLiteral(a).value() != ((NumericLiteral) b).value() ? BooleanLiteral.TRUE : BooleanLiteral.FALSE;
				else if( a instanceof StringLiteral && b instanceof StringLiteral )		return !((StringLiteral) a).value().equals(((StringLiteral) b).value()) ? BooleanLiteral.TRUE : BooleanLiteral.FALSE;

				throw new InvalidArgumentException("Incompatible operand types");
			}
		});
		defineOperator(new Operator("===", 10, Operator.LEFT_TO_RIGHT) {
			@Override
			public Object call(Object a, Object b) {
				return ConversionUtils.toLiteral(a).getLiteralType() != ConversionUtils.toLiteral(b).getLiteralType()
						? BooleanLiteral.FALSE
						: operators.get("==").call(a, b);
			}
		});
		defineOperator(new Operator("!==", 10, Operator.LEFT_TO_RIGHT) {
			@Override
			public Object call(Object a, Object b) {
				return ConversionUtils.toLiteral(a).getLiteralType() != ConversionUtils.toLiteral(b).getLiteralType()
						? BooleanLiteral.FALSE
						: operators.get("!=").call(a, b);
			}
		});


		// =========== Comparison =========== //
		defineOperator(new Operator("<", 11, Operator.LEFT_TO_RIGHT) {
			@Override
			public Object call(Object a, Object b) {
				return ConversionUtils.toNumericLiteral(a).value() < ConversionUtils.toNumericLiteral(b).value() ? BooleanLiteral.TRUE : BooleanLiteral.FALSE;
			}
		});
		defineOperator(new Operator("<=", 11, Operator.LEFT_TO_RIGHT) {
			@Override
			public Object call(Object a, Object b) {
				BooleanLiteral lessThan = (BooleanLiteral) operators.get("<").call(a, b);
				BooleanLiteral equalTo = (BooleanLiteral) operators.get("==").call(a, b);
				return lessThan.value() || equalTo.value() ? BooleanLiteral.TRUE : BooleanLiteral.FALSE;
			}
		});
		defineOperator(new Operator(">", 11, Operator.LEFT_TO_RIGHT) {
			@Override
			public Object call(Object a, Object b) {
				return ConversionUtils.toNumericLiteral(a).value() > ConversionUtils.toNumericLiteral(b).value() ? BooleanLiteral.TRUE : BooleanLiteral.FALSE;
			}
		});
		defineOperator(new Operator(">=", 11, Operator.LEFT_TO_RIGHT) {
			@Override
			public Object call(Object a, Object b) {
				BooleanLiteral greaterThan = (BooleanLiteral) operators.get(">").call(a, b);
				BooleanLiteral equalTo = (BooleanLiteral) operators.get("==").call(a, b);
				return greaterThan.value() || equalTo.value() ? BooleanLiteral.TRUE : BooleanLiteral.FALSE;
			}
		});


		// =========== Assignment =========== //
		defineOperator(new Operator("=", 3, Operator.RIGHT_TO_LEFT) {
			@Override
			public Object call(Object a, Object b) {
				if( !(a instanceof Identifier) )
					throw new InvalidArgumentException("The left-hand side of an assignment must be a variable");

				Identifier variable = (Identifier) a;
				variable.value(ConversionUtils.toLiteral(b));
				defineVariable(variable);
				return variable.value();
			}
		});
		defineOperator(new Operator("+=", 3, Operator.RIGHT_TO_LEFT) {
			@Override
			public Object call(Object a, Object b) {
				return operators.get("=").call(a, operators.get("+").call(((Identifier) a).value(), b));
			}
		});
		defineOperator(new Operator("-=", 3, Operator.RIGHT_TO_LEFT) {
			@Override
			public Object call(Object a, Object b) {
				return operators.get("=").call(a, operators.get("-").call(((Identifier) a).value(), b));
			}
		});
		defineOperator(new Operator("*=", 3, Operator.RIGHT_TO_LEFT) {
			@Override
			public Object call(Object a, Object b) {
				return operators.get("=").call(a, operators.get("*").call(((Identifier) a).value(), b));
			}
		});
		defineOperator(new Operator("/=", 3, Operator.RIGHT_TO_LEFT) {
			@Override
			public Object call(Object a, Object b) {
				return operators.get("=").call(a, operators.get("/").call(((Identifier) a).value(), b));
			}
		});
		defineOperator(new Operator("%=", 3, Operator.RIGHT_TO_LEFT) {
			@Override
			public Object call(Object a, Object b) {
				return operators.get("=").call(a, operators.get("%").call(((Identifier) a).value(), b));
			}
		});
		defineOperator(new Operator("**=", 3, Operator.RIGHT_TO_LEFT) {
			@Override
			public Object call(Object a, Object b) {
				return operators.get("=").call(a, operators.get("**").call(((Identifier) a).value(), b));
			}
		});
		defineOperator(new Operator("<<=", 3, Operator.RIGHT_TO_LEFT) {
			@Override
			public Object call(Object a, Object b) {
				return operators.get("=").call(a, operators.get("<<").call(((Identifier) a).value(), b));
			}
		});
		defineOperator(new Operator(">>=", 3, Operator.RIGHT_TO_LEFT) {
			@Override
			public Object call(Object a, Object b) {
				return operators.get("=").call(a, operators.get(">>").call(((Identifier) a).value(), b));
			}
		});
		defineOperator(new Operator(">>>=", 3, Operator.RIGHT_TO_LEFT) {
			@Override
			public Object call(Object a, Object b) {
				return operators.get("=").call(a, operators.get(">>>").call(((Identifier) a).value(), b));
			}
		});
		defineOperator(new Operator("&=", 3, Operator.RIGHT_TO_LEFT) {
			@Override
			public Object call(Object a, Object b) {
				return operators.get("=").call(a, operators.get("&").call(((Identifier) a).value(), b));
			}
		});
		defineOperator(new Operator("^=", 3, Operator.RIGHT_TO_LEFT) {
			@Override
			public Object call(Object a, Object b) {
				return operators.get("=").call(a, operators.get("^").call(((Identifier) a).value(), b));
			}
		});
		defineOperator(new Operator("|=", 3, Operator.RIGHT_TO_LEFT) {
			@Override
			public Object call(Object a, Object b) {
				return operators.get("=").call(a, operators.get("|").call(((Identifier) a).value(), b));
			}
		});
	}
}
