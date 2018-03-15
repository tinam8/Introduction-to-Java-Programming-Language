package hr.fer.zemris.java.gui.layouts;

/**
 * Class that implements instances that represent positions that are determined
 * with row and column number.
 * 
 * @author tina
 *
 */
public class RCPosition {
	/** number of row */
	private int row;
	/** number of column */
	private int column;

	/**
	 * Constructor that sets row and column
	 * 
	 * @param row
	 *            number of row
	 * @param column
	 *            number of column
	 */
	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}

	/**
	 * Getter for row of the position
	 * @return row of the positions
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Getter for column of the position.
	 * @return column of the position
	 */
	public int getColumn() {
		return column;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof RCPosition)) {
			return false;
		}
		
		RCPosition position = (RCPosition) obj;
		
		if (position.getRow() != row || position.getColumn() != column) {
			return false;
		}
		
		return true;
	}

}
