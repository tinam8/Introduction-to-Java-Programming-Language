package hr.fer.zemris.java.gui.calc.operation;

/**
 * Class containing different unary operations.
 * 
 * @author tina
 *
 */
public class UnaryOperators {
	/** Sine of a numbers. */
	public static final IUnaryOperator SIN = (value) -> {
		return Math.sin(value);
	};
	/** Cosine of a numbers. */
	public static final IUnaryOperator COS = (value) -> {
		return Math.cos(value);
	};
	/** Tangens of a numbers. */
	public static final IUnaryOperator TAN = (value) -> {
		return Math.tan(value);
	};
	/** Cotangents of a numbers. */
	public static final IUnaryOperator CTG = (value) -> {
		return 1 / Math.tan(value);
	};
	/** Arc-sine of a numbers. */
	public static final IUnaryOperator ARCSIN = (value) -> {
		return Math.asin(value);
	};
	/** Arc-cosine of a numbers. */
	public static final IUnaryOperator ARCCOS = (value) -> {
		return Math.acos(value);
	};
	/** Arc-tangens of a numbers. */
	public static final IUnaryOperator ARCTAN = (value) -> {
		return Math.atan(value);
	};
	/** Arc-cotangens of a numbers. */
	public static final IUnaryOperator ARCCTG = (value) -> {
		return Math.atan(1 / value);
	};
	/** Natural logarithm of a numbers. */
	public static final IUnaryOperator LN = (value) -> {
		return Math.log(value);
	};
	/** E to the given power. */
	public static final IUnaryOperator EXP_E = (value) -> {
		return Math.pow(Math.E, value);
	};
	/** Logarithm in the base of 10, of a numbers. */
	public static final IUnaryOperator LOG = (value) -> {
		return Math.log10(value);
	};
	/** 10 to the given power. */
	public static final IUnaryOperator EXP_10 = (value) -> {
		return Math.pow(10, value);
	};
	/** Inverse of a numbers. */
	public static final IUnaryOperator INVERSE = (value) -> {
		if (value == 0) {
			return 0;
		}

		return 1 / value;
	};

}
