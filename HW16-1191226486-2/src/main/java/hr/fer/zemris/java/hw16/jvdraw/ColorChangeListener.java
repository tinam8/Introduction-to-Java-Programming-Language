package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;

/**
 * Interface that represents listeners for color changes. This class represents
 * Subject in Observer pattern where subject is {@link IColorProvider}.
 * 
 * @author tina
 */

public interface ColorChangeListener {
	/**
	 * Method that applies necessary changes when color is changed.
	 * 
	 * @param source
	 *            instance of {@link IColorProvider} that tracks color changes.
	 * @param oldColor
	 *            old color
	 * @param newColor
	 *            new selected color
	 */
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}
