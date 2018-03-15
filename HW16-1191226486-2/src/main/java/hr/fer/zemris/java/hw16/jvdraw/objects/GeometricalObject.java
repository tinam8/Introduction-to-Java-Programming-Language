package hr.fer.zemris.java.hw16.jvdraw.objects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;

/**
 * Interface that defines methods for objects that will be drawn in program
 * {@link JVDraw}. Graphical object that implments this interface can register
 * {@link GeometricalObjectListener} and notify them when changes occur.
 * 
 * @author tina
 *
 */
public interface GeometricalObject {
	/**
	 * Method that repaints object.
	 * 
	 * @param g
	 *            instance of {@link Graphics}
	 */
	void repaint(Graphics g);

	/**
	 * Adds listener that will be notified if change of object.
	 * 
	 * @param l
	 *            instance of {@link GeometricalObjectListener}
	 */
	public void addGeometricalObjectListener(GeometricalObjectListener l);

	/**
	 * Removes listener from list of listeners so that it will no longer be
	 * notified about color changes.
	 * 
	 * @param l
	 *            instance fo {@link GeometricalObjectListener}
	 */
	public void removeGeometricalObjectListener(GeometricalObjectListener l);

	/**
	 * Method that sets ending point that is created after users first click.
	 * 
	 * @param endPoint
	 *            ending point
	 */
	public void setEndPoint(Point endPoint);

	/**
	 * Gets upper x coordinate of objects bounding box.
	 * 
	 * @return upper x coordinate of objects bounding box
	 */
	int getX();

	/**
	 * Gets upper y coordinate of objects bounding box.
	 * 
	 * @return upper y coordinate of objects bounding box
	 */
	int getY();

	/**
	 * Gets width of the objects bounding box.
	 * 
	 * @return width of the objects bounding box
	 */
	int getWidth();

	/**
	 * Gets height of the objects bounding box.
	 * 
	 * @return height of the objects bounding box
	 */
	int getHeight();

	/**
	 * Method that creates objects representations for file creation.
	 * 
	 * @return creates string representation of objects
	 */
	String formatOutput();

	/**
	 * Draws object when given coordinates for translation.
	 * 
	 * @param g
	 *            instance of {@link Graphics2D}
	 * @param upperX
	 *            translation in x direction
	 * @param upperY
	 *            translation in y direction
	 */
	void draw(Graphics2D g, int upperX, int upperY);

	/**
	 * Method that creates dialog for editing end edit geometric component
	 */
	void editWithDialog();

}
