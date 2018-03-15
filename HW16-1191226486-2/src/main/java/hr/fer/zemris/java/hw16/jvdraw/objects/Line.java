package hr.fer.zemris.java.hw16.jvdraw.objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Class that represents line that the user draws. The first click defines the
 * start point for the line and the second click defines the end point for the
 * line. Before the second click occurs, as user moves the mouse, the line is
 * drawn with end-point tracking the mouse so that the user can see what will be
 * the final result.
 * 
 * @author tina
 *
 */
public class Line extends AbstractGeometricalObject {
	/**
	 * Number of created instances
	 */
	private static int numberOfObjects;

	/**
	 * Constructor that sets point of first click and the point of second click
	 * which define start end end of a line.
	 * 
	 * @param startPoint
	 *            start of the line
	 * @param endPoint
	 *            end of the line
	 * @param fgColor
	 *            foreground color
	 */
	public Line(Point startPoint, Point endPoint, Color fgColor) {
		super(startPoint, endPoint, fgColor);
		name = "Line " + numberOfObjects;
		numberOfObjects++;
		x = Math.min(startPoint.x, endPoint.x);
		y = Math.min(startPoint.y, endPoint.y);
	}

	@Override
	public void repaint(Graphics g) {
		g.setColor(fgColor);
		g.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
	}

	@Override
	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
		notifyListeners();
	}

	/**
	 * Pattern: LINE x0 y0 x1 y1 red green blue
	 */
	@Override
	public String formatOutput() {
		return String.format("LINE %d %d %d %d %d %d %d", startPoint.x, startPoint.y, endPoint.x, endPoint.y,
				fgColor.getRed(), fgColor.getGreen(), fgColor.getBlue());
	}

	@Override
	public int getWidth() {
		return Math.max(startPoint.x, endPoint.x) - x;
	}

	@Override
	public int getHeight() {
		return Math.max(startPoint.y, endPoint.y) - y;
	}

	@Override
	public void draw(Graphics2D g, int upperX, int upperY) {
		g.setColor(fgColor);
		g.drawLine(x - upperX, y - upperY, x + getWidth() + upperX, y + getHeight() + upperY);
	}

	@Override
	public void editWithDialog() {
		JPanel panel = new JPanel();
		panel.setSize(new Dimension(300, 200));
		panel.setLayout(new GridLayout(10, 1));

		List<JLabel> labels = new ArrayList<>();
		addLabels(labels);

		List<JTextField> inputs = new ArrayList<>();
		addInputs(inputs);

		for (int i = 0; i < labels.size(); i++) {
			panel.add(labels.get(i));
			panel.add(inputs.get(i));
		}

		int result = JOptionPane.showConfirmDialog(null, panel, "Edit object", JOptionPane.OK_CANCEL_OPTION);
		if (result != JOptionPane.CANCEL_OPTION) {
			setEdition(inputs);
		}

	}

	/**
	 * Method checks if inputs are valid
	 * 
	 * @param inputs
	 *            list of inputs placed in {@link JTextField}
	 */
	private void setEdition(List<JTextField> inputs) {
		for (int i = 0; i < 4; i++) {
			if (!DialogUtil.isValidNumber(inputs.get(i).getText())) {
				DialogUtil.error("Could not make changes, invalid input, should have been a number");
				return;
			}
		}
		for (int i = 4; i < inputs.size(); i++) {
			if (!DialogUtil.isValidColor(inputs.get(i).getText())) {
				DialogUtil.error("Coul not make changes, invalid input for color");
				return;
			}
		}

		setParameters(inputs);
	}

	/**
	 * Method that ads labels for the inputs
	 * 
	 * @param labels
	 *            list of labels
	 */
	private void addLabels(List<JLabel> labels) {
		labels.add(new JLabel("Start x"));
		labels.add(new JLabel("Start y"));
		labels.add(new JLabel("End x"));
		labels.add(new JLabel("End x"));
		labels.add(new JLabel("Color red"));
		labels.add(new JLabel("Color green"));
		labels.add(new JLabel("Color blue"));
	}

	/**
	 * Adds inputs for line.
	 * 
	 * @param inputs
	 *            list of {@link JTextField}
	 */
	private void addInputs(List<JTextField> inputs) {
		JTextField field = new JTextField();
		field.setText(String.valueOf(startPoint.getX()));
		inputs.add(field);

		field = new JTextField();
		field.setText(String.valueOf(startPoint.getY()));
		inputs.add(field);
		
		field = new JTextField();
		field.setText(String.valueOf(endPoint.getY()));
		inputs.add(field);

		field = new JTextField();
		field.setText(String.valueOf(endPoint.getY()));
		inputs.add(field);

		field = new JTextField();
		field.setText(String.valueOf(fgColor.getRed()));
		inputs.add(field);

		field = new JTextField();
		field.setText(String.valueOf(fgColor.getGreen()));
		inputs.add(field);

		field = new JTextField();
		field.setText(String.valueOf(fgColor.getBlue()));
		inputs.add(field);

	}

	/**
	 * Sets parameters and notifies listeners about the change
	 * 
	 * @param inputs
	 *            list of {@link JTextField} that hold inputs
	 */
	private void setParameters(List<JTextField> inputs) {
		startPoint = new Point((int) Double.parseDouble(inputs.get(0).getText()),
				(int) Double.parseDouble(inputs.get(1).getText().toString()));
		endPoint = new Point((int) Double.parseDouble(inputs.get(2).getText()),
				(int) Double.parseDouble(inputs.get(3).getText()));
		fgColor = new Color((int) Double.parseDouble(inputs.get(4).getText()),
				(int) Double.parseDouble(inputs.get(5).getText()), (int) Double.parseDouble(inputs.get(6).getText()));
		notifyListeners();
	}

}
