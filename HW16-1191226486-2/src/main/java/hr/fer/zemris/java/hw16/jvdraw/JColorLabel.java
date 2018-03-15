package hr.fer.zemris.java.hw16.jvdraw;
import java.awt.Color;
import javax.swing.JLabel;

/**
 * Class that extends {@link JLabel}. It is used to display currently selected
 * foreground and background colors.
 * 
 * @author tina
 *
 */
public class JColorLabel extends JLabel implements ColorChangeListener {
	/**
	 * Provider for foreground color
	 */
	JColorArea fgColorArea;
	/**
	 * Provider for background color
	 */
	JColorArea bgColorArea;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor that register itself as listener on observers for foreground
	 * and background color choosers.
	 * 
	 * @param fgColorArea
	 *            instance of {@link JColorArea} for foreground color
	 * @param bgColorArea
	 *            instance of {@link JColorArea} for background color
	 */
	public JColorLabel(JColorArea fgColorArea, JColorArea bgColorArea) {
		this.fgColorArea = fgColorArea;
		this.bgColorArea = bgColorArea;
		fgColorArea.addColorChangeListener(this);
		bgColorArea.addColorChangeListener(this);
		setTextColors();
	}

	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
		setTextColors();
	}
	
	/**
	 * Sets label text based on currently selected colors.
	 */
	private void setTextColors() {
		Color fgColor = fgColorArea.getCurrentColor();
		Color bgColor = bgColorArea.getCurrentColor();
		setText(String.format("Foreground color: (%d, %d, %d), background color: (%d, %d, %d).", fgColor.getRed(),
				fgColor.getGreen(), fgColor.getBlue(), bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue()));
	}
}
