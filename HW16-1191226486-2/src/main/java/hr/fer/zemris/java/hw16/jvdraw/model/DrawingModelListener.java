package hr.fer.zemris.java.hw16.jvdraw.model;

/**
 * Listeners for {@link DrawingModel}. Notified when list of objects in model is
 * changed.
 * 
 * @author tina
 *
 */
public interface DrawingModelListener {
	/**
	 * Method called from {@link DrawingModel} when objects in model are added.
	 * 
	 * @param source
	 *            instance of {@link DrawingModel} that notifies
	 * @param index0
	 *            index of object in a list from which change is being made
	 * @param index1
	 *            index of object in a list to which change is being made
	 *            (excluded)
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);

	/**
	 * Method called from {@link DrawingModel} when objects in model are removeed.
	 * 
	 * @param source
	 *            instance of {@link DrawingModel} that notifies
	 * @param index0
	 *            index of object in a list from which change is being made
	 * @param index1
	 *            index of object in a list to which change is being made
	 *            (excluded)
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);

	/**
	 * Method called from {@link DrawingModel} when objects in model are changed.
	 * 
	 * @param source
	 *            instance of {@link DrawingModel} that notifies
	 * @param index0
	 *            index of object in a list from which change is being made
	 * @param index1
	 *            index of object in a list to which change is being made
	 *            (excluded)
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);
}