package hr.fer.zemris.java.gui.calc.operation;

/**
 * Class containing different binary operations.
 * 
 * @author tina
 *
 */
public class BinaryOperators {
	/** Addition of two numbers. */
	public static final IBinaryOperator ADD = (value1, value2) -> {
		return value1 + value2;
	};
	/** Subtraction of two numbers. */
	public static final IBinaryOperator SUB = (value1, value2) -> {
		return value1 - value2;
	};
	/** Multiplication of two numbers. */
	public static final IBinaryOperator MUL = (value1, value2) -> {
		return value1 * value2;
	};
	/** Division of two numbers. */
	public static final IBinaryOperator DIV = (value1, value2) -> {
		return value1 / value2;
	};
	/** Computes number to the power. */
	public static final IBinaryOperator POW = (value1, value2) -> {
		return Math.pow(value1, value2);
	};
	/** Computes nth root of number. */
	public static final IBinaryOperator SQRT = (value1, value2) -> {
		return Math.pow(value1, 1 / value2);
	};
}
