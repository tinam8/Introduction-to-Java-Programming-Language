package hr.fer.zemris.java.hw16.jvdraw.model;

import javax.swing.AbstractListModel;

import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;

/**
 * this class id an object adapter for the DrawingModel: it stores a reference
 * to the DrawingModel and implement all methods in such a way that the
 * information is retrieved from DrawingModel. DrawingObjectListModel a listener
 * on DrawingModel, so that, when user defines a new object by clicking in
 * JDrawingCanvas, the DrawingObjectListModel can get a notification that the
 * model has changed and that it can re-fire necessary notifications to it's own
 * listeners (to the JList that shows the list of currently available objects)
 * 
 * @author tina
 *
 */
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject> implements DrawingModelListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Adapted instance of {@link DrawingModel}
	 */
	DrawingModel model;

	/**
	 * Constructor 
	 * @param model model that should be adapted
	 */
	public DrawingObjectListModel(DrawingModel model) {
		this.model = model;
		model.addDrawingModelListener(this);
	}

	@Override
	public int getSize() {
		return model.getSize();
	}

	@Override
	public GeometricalObject getElementAt(int index) {
		return model.getObject(index);
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		fireIntervalAdded(source, index0, index1);
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		fireIntervalRemoved(source, index0, index1);
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		fireContentsChanged(source, index0, index1);
	}

}
