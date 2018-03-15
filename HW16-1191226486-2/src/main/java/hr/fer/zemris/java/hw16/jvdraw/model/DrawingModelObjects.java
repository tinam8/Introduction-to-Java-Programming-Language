package hr.fer.zemris.java.hw16.jvdraw.model;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObjectListener;

/**
 * Implementation of {@link DrawingModel} methods. Holds list of
 * {@link GeometricalObject} that are currently drawn on canvas. It implements
 * {@link GeometricalObjectListener} to track changes of every single object. if
 * change occurs notifies all of its listeners.
 * 
 * @author tina
 *
 */
public class DrawingModelObjects implements DrawingModel, GeometricalObjectListener {
	/**
	 * List of {@link GeometricalObject} that are currently drawn on canvas.
	 */
	List<GeometricalObject> objects;
	/**
	 * List of listeners, {@link DrawingModelListener}
	 */
	List<DrawingModelListener> listeners;

	/**
	 * Constructor
	 */
	public DrawingModelObjects() {
		objects = new ArrayList<>();
		listeners = new ArrayList<>();
	}

	@Override
	public int getSize() {
		return objects.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		return objects.get(index);
	}

	@Override
	public void add(GeometricalObject object) {
		objects.add(object);
		object.addGeometricalObjectListener(this);

		if (listeners.size() != 0) {
			List<DrawingModelListener> listenersTmp = new ArrayList<>(listeners);

			listenersTmp.forEach(listener -> {
				listener.objectsAdded(this, listenersTmp.size() - 1, listenersTmp.size() - 1);
			});
		}

		object.addGeometricalObjectListener(this);
	}

	@Override
	public void remove(GeometricalObject object) {
		objects.remove(object);
		int index = getPosition(object);

		List<DrawingModelListener> listenersTmp = new ArrayList<>(listeners);
		if (listeners.size() != 0) {
			listenersTmp.forEach(listener -> {
				listener.objectsRemoved(this, index+1, index+1);
			});
		}

	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		listeners.add(l);
	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		listeners.remove(l);

	}

	@Override
	public void objectChanged(GeometricalObject object) {
		int index = getPosition(object);

		if (listeners.size() != 0) {
			List<DrawingModelListener> listenersTmp = new ArrayList<>(listeners);

			for (int i = 0; i < listenersTmp.size(); i++) {
				listenersTmp.get(i).objectsChanged(this, index, index);
			}
		}
	}

	/**
	 * Gets position of given object.
	 * 
	 * @param object
	 *            object whose position is going to be found
	 * @return position if object is in list of object, otherwise 0
	 */
	private int getPosition(GeometricalObject object) {
		int position = -1;
		for (int i = 0; i < objects.size(); i++) {
			if (object.equals(objects.get(i))){
				position = i;
			}
		}
		return position;
	}

	@Override
	public void removeAll() {
		for (int i = getSize() - 1; i >= 0; i--) {
			objects.remove(i);
			if (listeners.size() != 0) {
				List<DrawingModelListener> listenersTmp = new ArrayList<>(listeners);

				for (int i1 = 0; i1 < listenersTmp.size(); i1++) {
					listenersTmp.get(i1).objectsChanged(this, i1, i1);
				}
			}
		}
	}

}
