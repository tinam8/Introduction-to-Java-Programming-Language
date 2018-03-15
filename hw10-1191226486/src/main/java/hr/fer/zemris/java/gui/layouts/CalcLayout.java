package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.gui.calc.Calculator;

/**
 * Implements layout for {@link Calculator}. Has 5 row and 7 columns, only in
 * first row first cell goes trough first 5 columns.
 * 
 * @author tina
 *
 */
public class CalcLayout implements LayoutManager2 {
	/** gap between elements in component */
	private int gap;
	/** minimal width of layout */
	private int minWidth = 0;
	/** maximal width of layout */
	private int minHeight = 0;
	/** maximal height of layout */
	private int maxWidth = 0;
	/** minimal height of layout */
	private int maxHeight = 0;
	/** preferred width of layout */
	private int preferredWidth = 0;
	/** preferred height of layout */
	private int preferredHeight = 0;
	/** number of columns */
	private final int COLUMNS = 7;
	/** number of rows */
	private final int ROWS = 5;
	/** determines if size components have been set */
	private boolean sizeUnknown = true;
	/** positions that have components */
	private List<RCPosition> positions;

	/**
	 * Constructor that set layout gap to zero.
	 */
	public CalcLayout() {
		this(0);
	}

	/**
	 * Constructor that sets layout with custom gap.
	 * 
	 * @param gap
	 *            gap between columns and rows
	 */
	public CalcLayout(int gap) {
		positions = new ArrayList<>();
		this.gap = gap;
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
	}

	@Override
	public void removeLayoutComponent(Component comp) {
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		Dimension dim = new Dimension(0, 0);
		setSizes(parent);

		Insets insets = parent.getInsets();
		dim.width = preferredWidth + insets.left + insets.right;
		dim.height = preferredHeight + insets.top + insets.bottom;

		sizeUnknown = false;

		return dim;

	}

	/**
	 * Method that sets minimal maximal and preferred sizes of parent container.
	 * 
	 * @param parent
	 *            parent container
	 */
	private void setSizes(Container parent) {
		int nComps = parent.getComponentCount();

		preferredWidth = 0;
		preferredHeight = 0;
		int maxPrefWidth = 0;
		int maxPrefHeight = 0;
		int maxCompWidth = 0;
		int maxCompHeight = 0;
		int minCompWidth = 0;
		int minCompHeight = 0;

		for (int i = 0; i < nComps; i++) {
			Component c = parent.getComponent(i);
			if (c.isVisible()) {
				RCPosition position = positions.get(i);
				if (position.getRow() == 1 && position.getColumn() == 1) {
					maxPrefWidth = (int) Math.max(maxPrefWidth, c.getPreferredSize().getWidth() / 5);
					maxCompWidth = (int) Math.max(maxCompWidth, c.getMaximumSize().getWidth() / 5);
					minCompWidth = (int) Math.max(minCompWidth, c.getMinimumSize().getWidth() / 5);
				} else {
					maxPrefWidth = (int) Math.max(maxPrefWidth, c.getPreferredSize().getWidth());
					maxCompWidth = (int) Math.max(maxCompHeight, c.getMaximumSize().getWidth());
					minCompWidth = (int) Math.max(minCompWidth, c.getMinimumSize().getWidth());
				}

				maxPrefHeight = (int) Math.max(maxPrefHeight, c.getPreferredSize().getHeight());
				maxCompHeight = (int) Math.max(maxCompHeight, c.getMaximumSize().getHeight());
				minCompHeight = (int) Math.max(minCompHeight, c.getMinimumSize().getHeight());

			}
		}

		preferredWidth = maxPrefWidth * COLUMNS + gap * (COLUMNS - 1);
		preferredHeight = maxPrefHeight * ROWS + gap * (ROWS - 1);
		minWidth = maxCompWidth * COLUMNS + gap * (COLUMNS - 1);
		minHeight = maxCompHeight * ROWS + gap * (ROWS - 1);
		maxWidth = minCompWidth * COLUMNS + gap * (COLUMNS - 1);
		maxHeight = minCompHeight * ROWS + gap * (ROWS - 1);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		Dimension dim = new Dimension(0, 0);

		setSizes(parent);

		Insets insets = parent.getInsets();
		dim.width = minWidth + insets.left + insets.right;
		dim.height = minHeight + insets.top + insets.bottom;

		sizeUnknown = false;

		return dim;
	}

	@Override
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();
		int nComps = parent.getComponentCount();

		if (sizeUnknown) {
			setSizes(parent);
		}

		int totalGapsWidth = (COLUMNS - 1) * gap;
		int widthWOInsets = parent.getWidth() - (insets.left + insets.right);
		int widthOnComponent = (widthWOInsets - totalGapsWidth) / COLUMNS;
		int extraWidthAvailable = (widthWOInsets - (widthOnComponent * COLUMNS + totalGapsWidth)) / 2;

		int totalGapsHeight = (ROWS - 1) * gap;
		int heightWOInsets = parent.getHeight() - (insets.top + insets.bottom);
		int heightOnComponent = (heightWOInsets - totalGapsHeight) / ROWS;
		int extraHeightAvailable = (heightWOInsets - (heightOnComponent * ROWS + totalGapsHeight)) / 2;

		for (int i = 0; i < nComps; ++i) {
			Component c = parent.getComponent(i);
			int row = positions.get(i).getRow() - 1;
			int col = positions.get(i).getColumn() - 1;

			int x = insets.left + extraWidthAvailable + (widthOnComponent + gap) * col;
			int y = insets.top + extraHeightAvailable + (heightOnComponent + gap) * row;

			if (c != null && c.isVisible()) {
				if (row == 0 && col == 0) {
					c.setBounds(x, y, widthOnComponent * 5 + gap * 4, heightOnComponent);
				} else {
					c.setBounds(x, y, widthOnComponent, heightOnComponent);
				}
			}
		}
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if (comp == null || constraints == null) {
			throw new IllegalArgumentException("Arguments can not be null.");
		}

		RCPosition position;

		if (constraints instanceof RCPosition) {
			position = (RCPosition) constraints;
		} else if (constraints instanceof String) {
			position = getRCPosition((String) constraints);
		} else {
			throw new IllegalArgumentException("Illegal constraints.");
		}

		if (!isValidPosition(position)) {
			throw new IllegalArgumentException("Illegal constraints.");
		}

		if (positions.contains(position)) {
			throw new IllegalArgumentException("There is already a component placed at given position.");
		}

		positions.add(position);

	}

	/**
	 * Method that parses string to get information about row and column. If
	 * string is not in appropriate form returns null.
	 * 
	 * @param constraints
	 *            string that should contain information about row and column
	 * @return instance of RCPosition if string is in valid form, otherwise null
	 */
	private RCPosition getRCPosition(String constraints) {
		String[] parts = constraints.split(",");
		if (parts.length != 2) {
			return null;
		}

		int row;
		int column;
		try {
			row = Integer.parseInt(parts[0]);
			column = Integer.parseInt(parts[1]);
		} catch (NumberFormatException e) {
			return null;
		}

		return new RCPosition(row, column);
	}

	/**
	 * Method that checks if given position is valid. <br>
	 * (Max rows = 5, max column = 7, in first row only legal positions are:
	 * (1,1),(1,6),(1,7)
	 * 
	 * @param position
	 *            position in layout
	 * @return true if position i valid, false otherwise
	 */
	private boolean isValidPosition(RCPosition position) {
		int row = position.getRow();
		int column = position.getColumn();

		if (row < 1 || row > 5) {
			return false;
		}
		if (row == 1 && (column > 1 && column < 6)) {
			return false;
		}
		if (column < 1 || column > 7) {
			return false;
		}

		return true;
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		Dimension dim = new Dimension(0, 0);

		setSizes(target);

		Insets insets = target.getInsets();
		dim.width = maxWidth + insets.left + insets.right;
		dim.height = maxHeight + insets.top + insets.bottom;

		sizeUnknown = false;

		return dim;
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return (float) 0.5;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return (float) 0.5;
	}

	@Override
	public void invalidateLayout(Container target) {

	}

}
