package hr.fer.zemris.java.hw11.statusbar;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import hr.fer.zemris.java.hw11.jnotepadpp.Clock;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.TranslationKeys;

/**
 * Class that that extends {@link JPanel} and holds information about currently
 * edited document: <br>
 * 1. Length <br>
 * 2. Current line <br>
 * 3. Current column in which the caret is <br>
 * 4. Length of current selection <br>
 * 5. {@link Clock} <br>
 * 
 * @author tina
 *
 */
public class StatusBarPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Length of document text */
	private JLabel length;
	/** Current line number */
	private JLabel ln;
	/** Current column in which is the caret */
	private JLabel col;
	/** Length of current selection */
	private JLabel sel;
	/** Time and date in form -> 2015/05/15 15:35:24. */
	private Clock clock;
	/** Instance of {@link ILocalizationProvider} that manages language changes */
	ILocalizationProvider lp;

	/**
	 * Constructor that initializes GUI components of status bar.
	 * @param lp instance of {@link ILocalizationProvider}
	 */
	public StatusBarPanel(ILocalizationProvider lp) {
		this.lp = lp;
		
		initGUI();
	}

	/**
	 * Method that initializes GUI components of status bar.
	 */
	private void initGUI() {
		setLayout(new GridLayout(1, 3));

		LJLabel lengthText = new LJLabel(TranslationKeys.lenght, lp);
		lengthText.setText("Lenght");
		length = new JLabel();
	
		ln = new JLabel("Ln: ");
		
		col = new JLabel("Col: ");
		sel = new JLabel("Sel: ");
		clock = new Clock();

		lengthText.setPreferredSize(lengthText.getMinimumSize());
		ln.setPreferredSize(ln.getPreferredSize());
		col.setPreferredSize(col.getPreferredSize());
		sel.setPreferredSize(sel.getPreferredSize());
		clock.setMinimumSize(clock.getMaximumSize());
		
		JPanel temp = new JPanel(new FlowLayout(FlowLayout.LEFT));
		temp.add(lengthText);
		temp.add(length);
		add(temp);
		
		JPanel panel = new JPanel(new GridLayout(1, 2));
		panel.add(ln);
		panel.add(col);
		panel.add(sel);
		
		add(panel);
		JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		add(panel.add(clock));
	}

	/**
	 * Sets new length of document
	 * 
	 * @param len
	 *            new length
	 */
	public void setLength(int len) {
		length.setText(": " + len);
	}

	/**
	 * Sets new current line
	 * 
	 * @param line
	 *            current line
	 */
	public void setLn(int line) {
		ln.setText("Ln: " + line);
	}

	/**
	 * Sets current column in which is the caret
	 * 
	 * @param column
	 *            current column in which is the caret
	 */
	public void setCol(int column) {
		col.setText("Col: " + column);
	}

	/**
	 * Sets length of current selection.
	 * 
	 * @param selected
	 *            length of current selection
	 */
	public void setSel(int selected) {
		sel.setText("Sel: " + selected);
	}

	/**
	 * Returns instance of {@link Clock} that holds as variable.
	 * 
	 * @return clock
	 */
	public Clock getClock() {
		return clock;
	}

}
