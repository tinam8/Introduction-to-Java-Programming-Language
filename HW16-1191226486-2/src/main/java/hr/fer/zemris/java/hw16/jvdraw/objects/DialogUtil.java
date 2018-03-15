package hr.fer.zemris.java.hw16.jvdraw.objects;

import javax.swing.JOptionPane;

/**
 * Implements methods used for creation and validation of editing dialogs for
 * geometric objects.
 * 
 * @author tina
 *
 */
public class DialogUtil {
	/**
	 * Method that validates if color components are numbers in valid range
	 * (0-255)
	 * 
	 * @param input
	 *            input that should be color component
	 * @return true if input is valid otherwise, false
	 */
	public static boolean isValidColor(String input) {
		int color = 0;
		try {
			color =Integer.parseInt(input);
		} catch (Exception e) {
			return false;
		}

		if (color > 255 || color < 0) {
			return false;
		}

		return true;
	}

	/**
	 * MEssage do display if there is an error
	 * 
	 * @param message
	 *            message that tells user what went wrong
	 */
	public static void error(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	/**
	 * Method that checks if input is number
	 * 
	 * @param input
	 *            string that should represent number
	 * @return true if input is number, false otherwise
	 */
	public static boolean isValidNumber(String input) {
		try {
			Double.parseDouble(input);
		} catch (Exception e) {
			return false;
		}

		return true;
	}
}
