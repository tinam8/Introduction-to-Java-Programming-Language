package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.objects.AbstractGeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.objects.Line;

/**
 * Central component that paints all created objects (
 * {@link AbstractGeometricalObject}). as a component derived from the
 * JComponent. It registers itself as a listener on the DrawingModel. Each time
 * it is notified that something has changed, it repaints new state.
 * 
 * @author tina
 *
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * {@link JColorArea} for foreground color
	 */
	private JColorArea fgColorArea;
	/**
	 * {@link JColorArea} for background color
	 */
	private JColorArea bgColorArea;
	/**
	 * model that holds drawn objects, instance of {@link DrawingModel}
	 */
	private DrawingModel model;
	/**
	 * Determines if drawing is being started
	 */
	private boolean isStart = true;
	/**
	 * Type of object that is selected (line circle, filled circle)
	 */
	private String selectedObjectType;
	/**
	 * Point of first click
	 */
	private Point startPoint;
	/**
	 * Last created geometric object
	 */
	private GeometricalObject createdObject;

	/**
	 * Constructor that register this instance to the given instance of
	 * {@link DrawingModel} so that it can be notified when changes occur.
	 * 
	 * @param model
	 *            instance of {@link DrawingModel} that tracts state of
	 *            geometric objects.
	 * @param fgColorArea
	 *            Instance {@link JColorArea} that tracks currently selected
	 *            foreground color
	 * @param bgColorArea
	 *            Instance {@link JColorArea} that tracks currently selected
	 *            background color
	 * @param frame
	 *            Frame that uses this canvas
	 */
	public JDrawingCanvas(DrawingModel model, JColorArea fgColorArea, JColorArea bgColorArea, JVDraw frame) {
		model.addDrawingModelListener(this);
		this.model = model;
		this.fgColorArea = fgColorArea;
		this.bgColorArea = bgColorArea;

		setBackground(Color.GREEN);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (isStart) {
					isStart = false;
					startPoint = e.getPoint();
					createdObject = getObject();
					model.add(createdObject);
					frame.setHasUnsavedWork(true);
				} else {
					isStart = true;
				}
			}
		});

		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				if (!isStart) {
					createdObject.setEndPoint(e.getPoint());
				}
			}

			@Override
			public void mouseDragged(MouseEvent e) {
			}
		});
	}

	/**
	 * Method that creates object based on selected type.
	 * 
	 * @return appropriate instance of {@link GeometricalObject}
	 */
	private GeometricalObject getObject() {
		switch (selectedObjectType) {
		case "Line":
			return new Line(startPoint, startPoint, fgColorArea.getCurrentColor());
		case "Circle":
			return new Circle(startPoint, startPoint, fgColorArea.getCurrentColor());
		case "Filled circle":
			return new FilledCircle(startPoint, startPoint, fgColorArea.getCurrentColor(),
					bgColorArea.getCurrentColor());
		default:
			return null;
		}
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < model.getSize(); i++) {
			model.getObject(i).repaint(g);
		}
	}

	/**
	 * Method that sets selected type
	 * 
	 * @param selectedObjectType
	 *            string that represents type, can be 'Line', 'Circle' and
	 *            'Filled circle'
	 */
	public void setSelectedObjectType(String selectedObjectType) {
		this.selectedObjectType = selectedObjectType;
	}
}
