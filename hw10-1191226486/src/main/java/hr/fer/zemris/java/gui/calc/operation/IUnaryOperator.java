package hr.fer.zemris.java.gui.calc.operation;
/**
 * Interface that implements method for computing unary operation.
 * 
 * @author tina
 *
 */
public interface IUnaryOperator {
	/**
	 * Method that performs unary operation on given argument.
	 * @param x first operand
	 * @return result of unary operation
	 */
	public double compute (double x);
}
