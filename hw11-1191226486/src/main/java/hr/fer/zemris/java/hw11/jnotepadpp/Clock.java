package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

/**
 * Class that implements {@link JComponent} that contains date and time in next
 * form: 2015/05/15 15:35:24
 * 
 * @author tina
 *
 */
public class Clock extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Date and time */
	private Date date;
	/** Indicates if there is request to stop clock */
	private volatile boolean stopRequester = false;
	/** Format of the date */
	private DateFormat dateFormat;

	/**  
	 * Constructor
	 */
	public Clock() {
		dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Thread worker = new Thread(() -> {
			while (!stopRequester) {
				SwingUtilities.invokeLater(() -> {
					date = new Date();
					repaint();
				});
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ignorable) {
				}
			}
		});
		worker.start();
	}

	/**
	 * Method that stops the clock.
	 */
	public void finish() {
		stopRequester = true;
	}

	@Override
	protected void paintComponent(Graphics g) {
		Dimension dim = getSize();
		Insets ins = getInsets();

		if (date == null) {
			return;
		}

		g.setColor(getForeground());
		String str = dateFormat.format(date);

		FontMetrics fm = g.getFontMetrics();
		fm.stringWidth(str);
		g.drawString(str, (dim.width - ins.left - ins.right - fm.stringWidth(str)) / 2 - ins.left,
				(dim.height - ins.top - ins.bottom - fm.getAscent()) / 2 + fm.getAscent() + ins.top);

	}
}
