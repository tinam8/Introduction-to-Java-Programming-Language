package hr.fer.zemris.java.gui.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.Objects;

import javax.swing.JComponent;

/**
 * Class that implements component for showing bar charts.
 * 
 * @author tina
 *
 */
public class BarChartComponent extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** list that holds data for bar-chart */
	private List<XYValue> values;
	/** description for x axis data */
	private String xDecs;
	/** description for y axis data */
	private String yDesc;
	/** minimal value on y axis */
	private int yMin;
	/** maximal value in y axis */
	private int yMax;
	/** gap between two values on y axis */
	private int gap;

	/** Free space between elements of the graph. */
	private static final int FREE = 10;
	/** Gap between columns of graph */
	private static final int COLUMN_GAP = 2;
	/** Axis width */
	private static final int AXIS_WIDTH = 3;
	/** Width of axis arrow */
	private static final int ARROW_LENGTH = 5;
	/** Color of grid lines */
	private static Color gridColor = new Color(250, 227, 220);

	/**
	 * Constructor that stores data for drawing bar chart.
	 * 
	 * @param data
	 *            data that determines one bar chart
	 */
	public BarChartComponent(BarChart data) {
		Objects.requireNonNull(data);

		this.values = data.getValues();
		this.xDecs = data.getxDecs();
		this.yDesc = data.getyDesc();
		this.yMin = data.getyMin();
		this.gap = data.getGap();
		this.yMax = (data.getyMax() - yMin) % gap == 0 ? data.getyMax()
				: data.getyMax() + (gap - (data.getyMax() - yMin) % gap);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2D = (Graphics2D) g.create();
		FontMetrics fm = getFontMetrics(new Font("Serif", Font.BOLD, 12));
		Insets insets = getInsets();

		drawDescription(g2D, fm);

		int axisFromEdgeY = insets.left + 2 * (fm.getDescent() + fm.getAscent()) + FREE
				+ fm.stringWidth(Integer.toString(yMax)) + FREE + AXIS_WIDTH;
		int axisFromEdgeX = insets.bottom + 2 * (fm.getDescent() + fm.getAscent()) + 2 * FREE + AXIS_WIDTH;

		int chartGraphX = axisFromEdgeY;
		int chartGraphY = insets.top + ARROW_LENGTH + 2 * FREE;
		int chartGraphWidth = getSize().width - chartGraphX - insets.right - ARROW_LENGTH - 2 * FREE;
		int chartGraphHeight = getSize().height - axisFromEdgeX - chartGraphY - ARROW_LENGTH;

		
		drawRows(g2D, fm, chartGraphX, chartGraphY, chartGraphWidth, chartGraphHeight);
		drawColumns(g2D, fm, chartGraphX, chartGraphY, chartGraphWidth, chartGraphHeight);
		drawAxis(g2D, fm, chartGraphX, chartGraphY, chartGraphWidth, chartGraphHeight);
		

		g.dispose();
	}
	
	/**
	 * Method that draws row grid.
	 * 
	 * @param g
	 *            instance of {@link Graphics2D}
	 * @param fm
	 *            font metrics for numbers
	 * @param xStart
	 *            start position (x coordinate) of space that will hold columns
	 * @param yStart
	 *            start position (y coordinate) of space that will hold columns
	 * @param width
	 *            width of space that will hold columns
	 * @param height
	 *            height of space that will hold columns
	 */
	private void drawRows(Graphics2D g, FontMetrics fm, int xStart, int yStart, int width, int height) {
		int yRange = yMax - yMin;
		int noRows = yRange / gap;
		int heighPerUnit = height / (yRange /gap);
		
		int fromBottom = yStart + height;
		
		for (int i = 0; i < noRows; i++) {
			g.setColor(Color.BLACK);
			g.drawString(Integer.toString(i*gap), xStart - fm.stringWidth(Integer.toString(i*gap)) -FREE, fromBottom + fm.getDescent());
			
			if (i!= 0) {
				g.setColor(gridColor);
				g.drawLine(xStart, fromBottom, xStart+width, fromBottom);
			}
			fromBottom -= heighPerUnit;
		}
		
		g.drawLine(xStart, fromBottom, xStart+width, fromBottom);
		g.setColor(Color.BLACK);
		g.drawString(Integer.toString(noRows*gap), xStart - fm.stringWidth(Integer.toString(noRows*gap)) -FREE, fromBottom + fm.getDescent());
	}

	/**
	 * Method that draws x and y axis.
	 * 
	 * @param g
	 *            instance of {@link Graphics2D}
	 * @param fm
	 *            font metrics for numbers
	 * @param xStart
	 *            start position (x coordinate) of space that will hold columns
	 * @param yStart
	 *            start position (y coordinate) of space that will hold columns
	 * @param width
	 *            width of space that will hold columns
	 * @param height
	 *            height of space that will hold columns
	 */
	private void drawAxis(Graphics2D g, FontMetrics fm, int xStart, int yStart, int width, int height) {
		g.setColor(Color.GRAY);
		g.setStroke(new BasicStroke(AXIS_WIDTH));
		g.drawLine(xStart, yStart, xStart, yStart + height);
		g.drawLine(xStart, yStart + height, xStart + width +FREE, yStart + height);
		
		g.drawPolygon(new int[] { xStart - ARROW_LENGTH / 2, xStart + ARROW_LENGTH / 2, xStart },
				new int[] { yStart, yStart, yStart - ARROW_LENGTH }, 3);
		g.drawPolygon(new int[] { xStart + width + FREE - ARROW_LENGTH, xStart + width + FREE  - ARROW_LENGTH, xStart + width +FREE },
				new int[] { yStart + height - ARROW_LENGTH / 2, yStart + height + ARROW_LENGTH / 2, yStart + height },
				3);
		g.setStroke(new BasicStroke(1));
	}

	/**
	 * Method than draws description of axis
	 * 
	 * @param g
	 *            instance of Graphics2D
	 * @param fm
	 *            instance of FontMetrics
	 */
	private void drawDescription(Graphics2D g, FontMetrics fm) {

		int widthx = fm.stringWidth(xDecs);
		g.setColor(Color.BLACK);
		g.drawString(xDecs, getInsets().left + getSize().width / 2 - widthx / 2,
				getSize().height - getInsets().bottom - 2 * fm.getDescent());
		int strWidth = fm.stringWidth(yDesc);
		AffineTransform at = new AffineTransform();
		at.rotate(-Math.PI / 2);
		g.setTransform(at);
		g.drawString(yDesc, -getInsets().top - getSize().height / 2 - strWidth / 2, fm.getAscent() + getInsets().left);

		g.setColor(Color.ORANGE);
		g.drawString("aa", 200, 200);
		g.drawRect(5, 5, 300, 300);
		g.fillRect(5, 5, 300, 300);
		at.rotate(Math.PI / 2);
		g.setTransform(at);
	}

	/**
	 * Method that draws columns of chart based on data in {@link BarChart}
	 * 
	 * @param g
	 *            instance of {@link Graphics2D}
	 * @param fm
	 *            font metrics for numbers
	 * @param xStart
	 *            start position (x coordinate) of space that will hold columns
	 * @param yStart
	 *            start position (y coordinate) of space that will hold columns
	 * @param width
	 *            width of space that will hold columns
	 * @param height
	 *            height of space that will hold columns
	 */
	private void drawColumns(Graphics2D g, FontMetrics fm, int xStart, int yStart, int width, int height) {

		int noColumns = values.size();
		int yRange = yMax - yMin;
		int heighPerUnit = height / yRange;
		int widthPerUnit = (width - COLUMN_GAP * noColumns) / noColumns;

		int xPoint = xStart;
		int yPoint = yStart;

		for (int i = 0; i < noColumns; ++i) {
			yPoint = yStart + height - values.get(i).getY() * heighPerUnit;
			g.setColor(Color.ORANGE);

			g.drawRect(xPoint, yPoint, widthPerUnit, values.get(i).getY() * heighPerUnit);
			g.fillRect(xPoint, yPoint, widthPerUnit, values.get(i).getY() * heighPerUnit);

			g.setColor(gridColor);
			if (i != 0) {
				g.drawLine(xPoint, yStart, xPoint, yStart + height);
			}

			g.drawLine(xPoint, yPoint, xPoint + widthPerUnit, yPoint);

			g.setColor(Color.BLACK);
			int numberWidth = fm.stringWidth(Integer.toString(values.get(i).getX()));
			g.drawString(Integer.toString(values.get(i).getX()), xPoint + widthPerUnit / 2 - numberWidth / 2,
					yStart + height + AXIS_WIDTH + FREE + fm.getAscent());

			xPoint += widthPerUnit + COLUMN_GAP;
		}
		g.setColor(gridColor);
		g.drawLine(xPoint, yStart, xPoint, yStart + height);

	}
}
