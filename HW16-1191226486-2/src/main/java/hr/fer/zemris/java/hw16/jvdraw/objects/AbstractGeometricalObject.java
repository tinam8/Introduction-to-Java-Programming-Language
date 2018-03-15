package hr.fer.zemris.java.hw16.jvdraw.objects;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;

/**
 * Class that represents objects that can be drawn in program {@link JVDraw}.
 * (area is filled with background color, circle is drawn with foreground
 * color). Holds variables and methods that objects share.
 * 
 * @author tina
 *
 */
public abstract class AbstractGeometricalObject implements GeometricalObject {
	/**
	 * Point at which drawing started.
	 */
	Point startPoint;
	/**
	 * Point at which drawing ended.
	 */
	Point endPoint;
	/**
	 * Foreground color
	 */
	Color fgColor;
	/**
	 * Name of drawn geometric object
	 */
	String name;
	/**
	 * X coordinate of upper left corner of objects bounding box.
	 */
	int x;
	/**
	 * Y coordinate of upper left corner of objects bounding box.
	 */
	int y;
	/**
	 * List of {@link GeometricalObjectListener} that are going to be notified
	 * when change of object occurs.
	 */
	List<GeometricalObjectListener> listeners;

	/**
	 * Constructor that sets starting end ending point.
	 * 
	 * @param startPoint
	 *            point at which drawing started
	 * @param endPoint
	 *            point at which drawing ended
	 * @param fgColor foreground color
	 */
	public AbstractGeometricalObject(Point startPoint, Point endPoint, Color fgColor) {
		super();
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.fgColor = fgColor;

		listeners = new ArrayList<>();
	}

	@Override
	public void addGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.add(l);
	}

	@Override
	public void removeGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.remove(l);
	}

	/**
	 * Getter for geometric object name
	 * @return object geometric name
	 */
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}

	/**
	 * 
	 */
	public void notifyListeners() {
		if (listeners.size() != 0) {
			List<GeometricalObjectListener> listenersTmp = new ArrayList<>(listeners);

			listenersTmp.forEach(listener -> {
				listener.objectChanged(this);
			});
		}
	}

	
	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof AbstractGeometricalObject))
			return false;
		AbstractGeometricalObject other = (AbstractGeometricalObject) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	
}
