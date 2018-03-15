package hr.fer.zemris.java.hw11.jnotepadpp.selection;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * Class that implements all operation on selected part of text.
 * 
 * @author tina
 *
 */
public class SelectionOperations {
	/** Detect selected text and cuts it. */
	public static final ISelection cut = (offset, len, textArea, notepad) -> {
		Document doc = textArea.getDocument();
		try {
			notepad.setTextClip(doc.getText(offset, len));
			doc.remove(offset, len);
			textArea.setCaretPosition(offset);
			textArea.requestFocusInWindow();
		} catch (BadLocationException ignorable) {
		}
	};

	/** Detect selected text and copies it. */
	public static final ISelection copy = (offset, len, textArea, notepad) -> {
		Document doc = textArea.getDocument();
		try {
			notepad.setTextClip(doc.getText(offset, len));
		} catch (BadLocationException ignorable) {
		}
	};

	/** Pastes copied or cut text it. */
	public static final ISelection paste = (offset, len, textArea, notepad) -> {
		Document doc = textArea.getDocument();
		try {
			doc.insertString(offset, notepad.getTextClip(), null);
			textArea.setCaretPosition(offset + notepad.getTextClip().length());
			textArea.requestFocusInWindow();
		} catch (BadLocationException ignorable) {
		}
	};

	/** Transforms selected text to upper case */
	public static final ISelection toUpperCase = (offset, len, textArea, notepad) -> {
		Document doc = textArea.getDocument();
		try {
			String text = doc.getText(offset, len);
			doc.remove(offset, len);
			doc.insertString(offset, text.toUpperCase(), null);

			restoreSelection(textArea, offset, offset + len);
		} catch (BadLocationException ignorable) {
		}
	};

	/** Transforms selected text to upper case */
	public static final ISelection toLowerCase = (offset, len, textArea, notepad) -> {
		Document doc = textArea.getDocument();
		try {
			String text = doc.getText(offset, len);
			doc.remove(offset, len);
			doc.insertString(offset, text.toLowerCase(), null);

			restoreSelection(textArea, offset, offset + len);
		} catch (BadLocationException ignorable) {
		}
	};

	/** Transforms selected text to upper case */
	public static final ISelection toggleCase = (offset, len, textArea, notepad) -> {
		Document doc = textArea.getDocument();
		try {
			String text = doc.getText(offset, len);
			doc.remove(offset, len);
			doc.insertString(offset, toggleText(text), null);

			restoreSelection(textArea, offset, offset + len);
		} catch (BadLocationException ignorable) {
		}
	};

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

	/**
	 * Method that changes lower case letter to upper and otherwise.
	 * 
	 * @param text
	 *            text to toggle
	 * @return transformed text
	 */
	private static String toggleText(String text) {
		StringBuilder sb = new StringBuilder(text.length());
		for (char c : text.toCharArray()) {
			if (Character.isUpperCase(c)) {
				sb.append(Character.toLowerCase(c));
			} else if (Character.isLowerCase(c)) {
				sb.append(Character.toUpperCase(c));
			} else {
				sb.append(c);
			}
		}

		return sb.toString();
	}

}
