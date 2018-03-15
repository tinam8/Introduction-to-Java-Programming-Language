package hr.fer.zemris.java.gui.charts;

/**
 * Class that contains two values.
 * 
 * @author tina
 *
 */
public class XYValue {
	/** first value */
	private int x;
	/** second value */
	private int y;

	/**
	 * Constructor that sets values
	 * 
	 * @param x
	 *            first value
	 * @param y
	 *            second value
	 */
	public XYValue(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * Getter for x value.
	 * 
	 * @return x value
	 */
	public int getX() {
		return x;
	}

	/**
	 * Getter for y value.
	 * 
	 * @return y value
	 */
	public int getY() {
		return y;
	}

}
