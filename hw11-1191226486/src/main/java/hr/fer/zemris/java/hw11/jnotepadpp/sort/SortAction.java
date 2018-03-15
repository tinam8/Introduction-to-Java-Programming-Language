package hr.fer.zemris.java.hw11.jnotepadpp.sort;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.Tab;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Class that extends {@link LocalizableAction} and implements actions for
 * sorting lines of text.
 * 
 * @author tina
 *
 */
public class SortAction extends LocalizableAction {
	/** */
	private static final long serialVersionUID = 1L;
	/** Notepad whose it's going to be sorted. */
	private JNotepadPP notepad;
	/** Instance of {@link ISort} that implements sort method. */
	private ISort sortMethod;

	/**
	 * Constructor
	 * @param key key for action name
	 * @param lp instance of {@link ILocalizationProvider}
	 * @param notepad notepad whose text is going to be sorted
	 * @param sortMethod instance of {@link ISort} that implements method for sorting
	 */
	public SortAction(String key, ILocalizationProvider lp, JNotepadPP notepad, ISort sortMethod) {
		super(key, lp);
		this.notepad = notepad;
		this.sortMethod = sortMethod;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Tab tab = notepad.getTabs().get(notepad.getTabbedPane().getSelectedIndex());
		JTextArea textArea = tab.getTextArea();

		int offset = Math.min(textArea.getCaret().getDot(), textArea.getCaret().getMark());
		int start = 0;
		try {
			start = textArea.getLineStartOffset(textArea.getLineOfOffset(offset));
		} catch (BadLocationException ignorable) {
		}

		List<String> listLines = getLines(textArea, offset, start);
		String text = sortMethod.sort(listLines);

		try {

			textArea.getDocument().insertString(start, text, null);
		} catch (BadLocationException ignorable) {
		}
		
		restoreSelection(textArea, start, start +  text.length());
	}

	/**
	 * Supplementary method that returns text as list of strings.
	 * @param textArea text area with selected text
	 * @param offset start of selection
	 * @param start start of selected line
	 * @return list of strings
	 */
	private List<String> getLines(JTextArea textArea, int offset, int start) {
		String[] lines = null;
		int len = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());

		try {
			int lineStart = textArea.getLineOfOffset(offset);
			int startOffset = textArea.getLineStartOffset(lineStart);
			int lineEnd = textArea.getLineOfOffset(offset + len);
			int endOffset = textArea.getLineEndOffset(lineEnd);

			String text = textArea.getDocument().getText(startOffset, endOffset - startOffset);
			textArea.getDocument().remove(startOffset, endOffset - startOffset);
			lines = text.split("\n");

		} catch (BadLocationException ignorable) {
		}

		try {
			return Arrays.asList(lines);
		} catch (Exception e) {
			System.out.println(e.getCause().toString());
		}
		
		return null;
		
	}
	
	/**
	 * Method that sets selected part of text in text area.
	 * @param textArea text area for selection
	 * @param start start offset
	 * @param end end offset
	 */
	private static void restoreSelection(JTextArea textArea, int start, int end) {
		textArea.requestFocus();
		textArea.select(start, end);
		textArea.requestFocusInWindow();
	}

}
