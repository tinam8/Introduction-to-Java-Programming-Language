package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * Class that stores data that will be used far drawing bar-chart.
 * 
 * @author tina
 *
 */
public class BarChart {
	/** list that holds data for bar-chart */
	private List<XYValue> values;
	/**  description for x axis data */
	private String xDecs;
	/** description for y axis data */
	private String yDesc;
	/** minimal value on y axis */
	private int yMin;
	/** maximal value in y axis */ 
	private int yMax;
	/** gap between two values on y axis */
	private int gap;
	
	/**
	 * Constructor that sets data for bar chart.
	 * @param values values of data
	 * @param xDecs description for x axis data
	 * @param yDesc description for y axis data
	 * @param yMin minimal value on y axis
	 * @param yMax maximal value on y axis
	 * @param gap gap between two values on y axis
	 */
	public BarChart(List<XYValue> values, String xDecs, String yDesc, int yMin, int yMax, int gap) {
		super();
		this.values = values;
		this.xDecs = xDecs;
		this.yDesc = yDesc;
		this.yMin = yMin;
		this.yMax = yMax;
		this.gap = gap;
	}
	
	/**
	 * Getter for data values
	 * @return data values
	 */
	public List<XYValue> getValues() {
		return values;
	}
	/**
	 * Getter for x axis description
	 * @return x axis description
	 */
	public String getxDecs() {
		return xDecs;
	}
	/**
	 * Getter for y axis description
	 * @return y axis description
	 */
	public String getyDesc() {
		return yDesc;
	}
	/**
	 * Getter for minimal value on y axis
	 * @return  minimal value on y axis
	 */
	public int getyMin() {
		return yMin;
	}
	/**
	 * Getter for maximal value on y axis
	 * @return maximal value on y axis
	 */
	public int getyMax() {
		return yMax;
	}
	/**
	 * Getter for gap on y axis
	 * @return gap on y axis
	 */
	public int getGap() {
		return gap;
	}
	
}
