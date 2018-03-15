package hr.fer.zemris.java.hw16.jvdraw.model;

import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;

/**
 * Interface that imlpmenets maethos of Subject in observer pattern. Class that
 * implements this interface hold informations about objects in model and
 * notifies when changes happen.
 * 
 * @author tina
 *
 */
public interface DrawingModel {
	/**
	 * Getter for number of object in the model.
	 * 
	 * @return number of object in the model
	 */
	public int getSize();

	/**
	 * Getter for object at given position.
	 * 
	 * @param index
	 *            position in the list of objects
	 * @return object at given position
	 */
	public GeometricalObject getObject(int index);

	/**
	 * Adds new object to the list of objects.
	 * 
	 * @param object
	 *            object to add to the model
	 */
	public void add(GeometricalObject object);

	/**
	 * 
	 * @param object
	 *            object to remove from list of currently drawn objects
	 */
	public void remove(GeometricalObject object);

	/**
	 * Adds listener to the list of listeners.
	 * 
	 * @param l
	 *            listener that should be registered
	 */
	public void addDrawingModelListener(DrawingModelListener l);

	/**
	 * Removes listener from the list of listeners, It will no longer be
	 * notified when changes in model occur.
	 * 
	 * @param l
	 *            listener that should be registered
	 */
	public void removeDrawingModelListener(DrawingModelListener l);

	/**
	 * Method used for removal of all objects, automatically they are removed
	 * from all registered listeners.
	 */
	public void removeAll();
}