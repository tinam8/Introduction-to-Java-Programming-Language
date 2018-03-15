package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Class that represents one tab of editor. It holds relevant information about
 * document that its textArea contains.
 * 
 * @author tina
 *
 */
public class Tab {
	/** instance of {@link JTextArea} that holds document text. */
	private JTextArea textArea;
	/** VAriable that determines if any editing occurred on this tab */
	private boolean isChanged = false;
	/** Fail path to the location on the disc */
	private Path filePath;
	/** Instance of {@link JTabbedPane} that contains tabs */
	private JTabbedPane tabbedPane;
	/** Title of the tab */
	private JLabel tabTitle;

	/**
	 * Constructor that sets variables and adds some of theirs functionalities.
	 * 
	 * @param textArea
	 *            textArea that has file text
	 * @param filePath
	 *            path to the location on disc
	 * @param index
	 *            index in list of tabs
	 * @param tabbedPane
	 *            instance of {@link JTabbedPane} from notepad
	 * @param closeBtn
	 *            button used for closing the tab

	 */
	public Tab(JTextArea textArea, Path filePath, int index, JTabbedPane tabbedPane, JButton closeBtn) {
		this.textArea = textArea;
		this.filePath = filePath;
		this.tabbedPane = tabbedPane;

		tabbedPane.add(new JScrollPane(textArea));

		JPanel panel = createTabPanel(index, closeBtn);
		panel.setOpaque(false);

		tabbedPane.setTabComponentAt(index, panel);
		tabbedPane.setIconAt(index, new Icons().getGreenIcon());
		textArea.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				isChanged = true;
				tabTitle.setIcon(new Icons().getRedIcon());
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				isChanged = true;
				tabTitle.setIcon(new Icons().getRedIcon());
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				isChanged = true;
				tabTitle.setIcon(new Icons().getRedIcon());
			}
		});
	}

	/**
	 * Method that creates panel that is going to be added as tab to tabbed pane
	 * of notepad
	 * 
	 * @param index
	 *            index of this tab
	 * @param closeBtn
	 *            button that is used for closing this tab
	 * @return created panel
	 */
	private JPanel createTabPanel(int index, JButton closeBtn) {
		JPanel panel = new JPanel();

		tabTitle = new JLabel();
		if (filePath == null) {
			tabTitle.setText("");
		} else {
			tabTitle.setText(filePath.getFileName().toString());
			tabbedPane.setToolTipTextAt(index, filePath.toString());
		}

		tabTitle.setIcon(new Icons().getGreenIcon());
		tabTitle.setOpaque(false);
		panel.add(tabTitle);

		closeBtn.setText("x");
		panel.add(closeBtn);

		return panel;
	}

	/**
	 * Getter for text area
	 * 
	 * @return text area that holds document text
	 */
	public JTextArea getTextArea() {
		return textArea;
	}

	/**
	 * Determines if document has been changed.
	 * 
	 * @return true if it has been changed, false otherwise
	 */
	public boolean isChanged() {
		return isChanged;
	}

	/**
	 * Sets status of to unchanged
	 */
	public void setChange() {
		isChanged = false;
		tabTitle.setIcon(new Icons().getGreenIcon());
	}

	/**
	 * Getter for file path of document.
	 * 
	 * @return file path
	 */
	public Path getFilePath() {
		return filePath;
	}

	/**
	 * Sets file path of document
	 * 
	 * @param filePath
	 *            file path
	 * @param index
	 *            index of tab
	 */
	public void setFilePath(Path filePath, int index) {
		this.filePath = filePath;
		tabTitle.setText(filePath.getFileName().toString());
		tabbedPane.setToolTipTextAt(index, filePath.toString());
	}

	/**
	 * Sets text to text area that represent document content.
	 * 
	 * @param text
	 *            text to set
	 */
	public void setText(String text) {
		textArea.setText(text);
	}

	/**
	 * Getter for document text.
	 * 
	 * @return document text
	 */
	public String getText() {
		return textArea.getText();
	}

	/**
	 * Getter for text length.
	 * 
	 * @return text length
	 */
	public int getLength() {
		return getText().length();
	}

	/**
	 * Getter for line that is caret currently on.
	 * 
	 * @return line where is caret.
	 */
	public int getLn() {
		try {
			return textArea.getLineOfOffset(textArea.getCaretPosition()) + 1;
		} catch (BadLocationException ignorable) {
		}

		return 0;
	}

	/**
	 * Method that calculates and returns length of selected text.
	 * 
	 * @return length of selected text
	 */
	public int getSel() {
		return Math.abs(getCaret().getDot() - getCaret().getMark());
	}

	/**
	 * Return text area caret.
	 * 
	 * @return text area caret tab.
	 */
	public Caret getCaret() {
		return textArea.getCaret();
	}

	/**
	 * Method that calculates and returns current column in which the caret is.
	 * 
	 * @return current column in which the caret is
	 */
	public int getCol() {
		int line = getLn() - 1;
		String text = getText();
		String currentLineFromBegining = null;

		try {
			currentLineFromBegining = text.substring(textArea.getLineStartOffset(line), textArea.getCaretPosition());
		} catch (BadLocationException ignrable) {
		}

		return currentLineFromBegining == null ? 0 : currentLineFromBegining.length();
	}

}
