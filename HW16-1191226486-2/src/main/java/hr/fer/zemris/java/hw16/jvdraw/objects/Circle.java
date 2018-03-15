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

import hr.fer.zemris.java.hw16.jvdraw.GeometryUtil;

/**
 * Class that represents circle that the user draws. The first click defines the
 * circle center and as user moves the mouse, a circle radius is defined. On
 * second click, circle is added.
 * 
 * @author tina
 *
 */
public class Circle extends AbstractGeometricalObject {
	/**
	 * Color to draw circle with
	 */
	private Color fgColor;
	/**
	 * Circle radius
	 */
	private int radius;
	/**
	 * Number of created instances
	 */
	private static int numberOfObjects = 0;

	/**
	 * Constructor that sets start point that user clicked and the end point of
	 * the second click.
	 * 
	 * @param startPoint
	 *            point of first click
	 * @param endPoint
	 *            point of second click
	 * @param fgColor
	 *            foreground color that circle will be drawn with
	 */
	public Circle(Point startPoint, Point endPoint, Color fgColor) {
		super(startPoint, endPoint, fgColor);
		this.fgColor = fgColor;
		name = "Circle " + numberOfObjects;
		numberOfObjects++;
		radius = (int) GeometryUtil.distanceFromPoint(startPoint, endPoint);

	}

	@Override
	public void repaint(Graphics g) {
		g.setColor(fgColor);
		x = (int) startPoint.getX() - radius;
		y = (int) startPoint.getY() - radius;

		g.drawOval(x, y, 2 * radius, 2 * radius);
	}

	@Override
	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
		radius = (int) GeometryUtil.distanceFromPoint(startPoint, endPoint);
		notifyListeners();
	}

	/**
	 * Pattern: CIRCLE centerx centery radius red green blue
	 */
	@Override
	public String formatOutput() {
		return String.format("CIRCLE %d %d %d %d %d %d", startPoint.x, startPoint.y, radius, fgColor.getRed(),
				fgColor.getGreen(), fgColor.getBlue());
	}

	@Override
	public int getWidth() {
		return 2 * radius;
	}

	@Override
	public int getHeight() {
		return 2 * radius;
	}

	@Override
	public void draw(Graphics2D g, int upperX, int upperY) {
		g.setColor(fgColor);
		g.drawOval(x - upperX, y - upperY, 2 * radius, 2 * radius);
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
	 * Method that ads labels for the inputs
	 * 
	 * @param labels
	 *            list of labels
	 */
	private void addLabels(List<JLabel> labels) {
		labels.add(new JLabel("center x"));
		labels.add(new JLabel("cnter y"));
		labels.add(new JLabel("radius"));
		labels.add(new JLabel("Color red"));
		labels.add(new JLabel("Color green"));
		labels.add(new JLabel("Color blue"));
	}
	
	/**
	 * Adds inputs for circle.
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
		field.setText(String.valueOf(radius));
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
	 * Method checks if inputs are valid
	 * 
	 * @param inputs
	 *            list of inputs placed in {@link JTextField}
	 */
	private void setEdition(List<JTextField> inputs) {
		for (int i = 0; i < 3; i++) {
			if (!DialogUtil.isValidNumber(inputs.get(i).getText())) {
				DialogUtil.error("Could not make changes, invalid input, should have been a number");
				return;
			}
		}
		for (int i = 3; i < inputs.size(); i++) {
			if (!DialogUtil.isValidColor(inputs.get(i).getText())) {
				DialogUtil.error("Coul not make changes, invalid input for color");
				return;
			}
		}

		setParameters(inputs);
	}
	
	/**
	 * Sets parameters and notifies listeners about the change
	 * 
	 * @param inputs
	 *            list of {@link JTextField} that hold inputs
	 */
	private void setParameters(List<JTextField> inputs) {
		startPoint = new Point((int) Double.parseDouble(inputs.get(0).getText()),
				(int) Double.parseDouble(inputs.get(1).getText()));
		radius = (int) Double.parseDouble(inputs.get(2).getText());
		fgColor = new Color((int) Double.parseDouble(inputs.get(3).getText()),
				(int) Double.parseDouble(inputs.get(4).getText()), (int) Double.parseDouble(inputs.get(5).getText()));
		notifyListeners();
	}

}
