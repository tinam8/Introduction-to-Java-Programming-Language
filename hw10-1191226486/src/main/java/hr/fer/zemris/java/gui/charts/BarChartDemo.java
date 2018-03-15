package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * Program and shows one instance {@link BarChartComponent}. Program takes one
 * command line argument: path to the file that has data that determines bar
 * -chart. <br>
 * Example of data: <br>
 * Number of people in the car <br>
 * Frequency <br>
 * 1,8 2,20 3,22 4,10 5,4 <br>
 * 0 <br>
 * 22 <br>
 * 2<br>
 * 
 * @author tina
 *
 */
public class BarChartDemo extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor that accepts data that determines bar-chart
	 * 
	 * @param data
	 *            data that determines bar-chart
	 * @param file
	 *            path to file with data
	 */
	public BarChartDemo(BarChart data, String file) {
		setLocation(300, 300);
		setSize(700, 500);
		setTitle("Bar-chart");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		initGUI(data, file);
	}

	/**
	 * Method that creates GUI of bar-chart.
	 * 
	 * @param data
	 *            data that determines bar-chart
	 * @param file
	 *            path to file with data
	 */
	private void initGUI(BarChart data, String file) {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.setBackground(Color.WHITE);

		JLabel title = new JLabel(file);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		cp.add(title, BorderLayout.PAGE_START);

		BarChartComponent chartComponent = new BarChartComponent(data);
		cp.add(chartComponent, BorderLayout.CENTER);
	}

	/**
	 * Method that starts program
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("One argument is required.");
			System.exit(-1);
		}

		List<XYValue> values = null;
		String xDecs = null;
		String yDesc = null;
		int yMin = 0;
		int yMax = 0;
		int gap = 0;

		try (BufferedReader br = new BufferedReader(new InputStreamReader(
				new BufferedInputStream(new FileInputStream(args[0])), StandardCharsets.UTF_8));) {
			xDecs = br.readLine().trim();
			yDesc = br.readLine().trim();

			String[] stringValues = br.readLine().trim().split(" ");

			values = getValues(stringValues);
			
			try {
				yMin = Integer.parseInt(br.readLine());
				yMax = Integer.parseInt(br.readLine());
				gap = Integer.parseInt(br.readLine());
			} catch (NumberFormatException e) {
				System.err.println("Error in data");
				System.exit(-1);
			}

		} catch (IOException ex) {
			System.err.println("Error while opening file." + ex.getMessage());
			System.exit(-1);
		}

		BarChart barChart = new BarChart(values, xDecs, yDesc, yMin, yMax, gap);

		SwingUtilities.invokeLater(() -> {
			new BarChartDemo(barChart, args[0]).setVisible(true);
		});
	}

	/**
	 * Method that extracts values from strings. Expected form of each is for
	 * example "1, 2"
	 * 
	 * @param stringValues
	 *            array of string values
	 * @return list of {@link XYValue}
	 */
	private static List<XYValue> getValues(String[] stringValues) {
		List<XYValue> values = new ArrayList<>();

		for (int i = 0; i < stringValues.length; i++) {
			String[] stringValue = stringValues[i].split(",");
			if (stringValue.length != 2) {
				System.err.println("Error in data");
				System.exit(-1);
			}

			try {
				values.add(new XYValue(Integer.parseInt(stringValue[0]), Integer.parseInt(stringValue[1])));
			} catch (NumberFormatException e) {
				System.err.println("Error in data");
				System.exit(-1);
			}
		}

		return values;
	}

}
