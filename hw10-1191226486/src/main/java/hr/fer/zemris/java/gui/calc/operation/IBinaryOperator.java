package hr.fer.zemris.java.gui.calc.operation;

/**
 * Interface that implements method for computing binary operation.
 * 
 * @author tina
 *
 */
public interface IBinaryOperator {
	/**
	 * Method that performs operation on two given arguments.
	 * @param x first operand
	 * @param y second operand
	 * @return result of binary operation
	 */
	public double compute (double x, double y);
}
