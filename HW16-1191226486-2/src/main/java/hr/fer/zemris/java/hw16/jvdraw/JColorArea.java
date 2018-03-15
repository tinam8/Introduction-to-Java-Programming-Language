package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

/**
 * Class that extends {@link JComponent} and is used as color chooser. When user
 * clicks on this component, component opens color chooser dialog and allows
 * user to select color that will become new selected color.
 * 
 * @author tina
 *
 */
public class JColorArea extends JComponent implements IColorProvider {
	
	/**
	 * Currently selected color.
	 */
	private Color selectedColor;
	/**
	 * List of listeners that will be notified when color changes.
	 */
	private List<ColorChangeListener> listeners;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor that sets default color as blue.
	 */
	public JColorArea() {
		selectedColor = Color.BLUE;
		listeners = new ArrayList<>();
		setSize(getPreferredSize());
		setMaximumSize(new Dimension(20, 20));

		addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent me) {
				Color color = JColorChooser.showDialog(JColorArea.this, "Choose color", Color.WHITE);
				if (color != null) {
					setSelectedColor(color);
				}
			}
		});
	}

	/**
	 * Method that notifies all listeners about color change, and sets new color
	 * as currently selected.
	 * 
	 * @param color
	 *            new selected color
	 */
	protected void setSelectedColor(Color color) {
		Color oldColor = selectedColor;
		selectedColor = color;
		
		if (listeners.size() != 0) {
			List<ColorChangeListener> listenersTmp = new ArrayList<>(listeners);

			listenersTmp.forEach(listener -> {
				listener.newColorSelected(this, oldColor, color);
			});
		}
		
		repaint();
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(15, 15);
	}

	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}

	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		listeners.add(l);
	}

	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		listeners.remove(l);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Dimension dim = getPreferredSize();
		setSize(dim);
		
		g.setColor(selectedColor);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

}
