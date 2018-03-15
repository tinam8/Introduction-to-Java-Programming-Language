package hr.fer.zemris.java.gui.calc;

import javax.swing.SwingUtilities;

/**
 * Class represents simple calculator. User inputs numbers and operation with
 * clicks on buttons. Operations are standard, if inverse checkbox is checked
 * inverse operations are performed.
 * 
 * @author tina
 *
 */
public class Calculator {
	/**
	 * Method that starts program
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		
		CalculatorFrame calc = new CalculatorFrame();
		calc.setSize(600,200);
		
		SwingUtilities.invokeLater(() -> calc.setVisible(true));
	}
}
