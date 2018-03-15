package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;

/**
 * Represents subject in observer pattern where listeners are instances of
 * {@link ColorChangeListener}. Classes that implement this interface have
 * access to the chosen color and retrieves it.
 * 
 * @author tina
 *
 */
public interface IColorProvider {
	/**
	 * Gets currently selected color.
	 * 
	 * @return selected color
	 */
	public Color getCurrentColor();

	/**
	 * Adds listener that will be notified if change of color occurs.
	 * 
	 * @param l
	 *            instance of {@link ColorChangeListener}
	 */
	public void addColorChangeListener(ColorChangeListener l);

	/**
	 * Removes listener from list of listeners so that it will no longer be
	 * notified about color changes.
	 * 
	 * @param l
	 *            instance fo {@link ColorChangeListener}
	 */
	public void removeColorChangeListener(ColorChangeListener l);
}
