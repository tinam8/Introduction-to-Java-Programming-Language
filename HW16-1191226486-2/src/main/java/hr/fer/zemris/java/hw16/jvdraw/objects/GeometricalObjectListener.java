package hr.fer.zemris.java.hw16.jvdraw.objects;

/**
 * Listeners for {@link GeometricalObject}. If change in object occurs it
 * notifies all registered objects.
 * 
 * @author tina
 *
 */
public interface GeometricalObjectListener {
	/**
	 * Method that applies necessary changes when change in object occurs.
	 * 
	 * @param object
	 *            instance of {@link GeometricalObject} that is notifying its
	 *            change.
	 * 
	 * 
	 */
	public void objectChanged(GeometricalObject object);
}
