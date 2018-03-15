package hr.fer.zemris.java.hw11.jnotepadpp.selection;

import javax.swing.JTextArea;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;

/**
 * Interface for that has method for processing selected text in text area.
 * 
 * @author tina
 *
 */
public interface ISelection {
	/**
	 * Method that processes selected text.
	 * @param offset  of selected area 
	 * @param len length of selected area
	 * @param textArea text area that holds selected text
	 * @param notepad notepad that holds text area
	 */
	public void process(int offset, int len, JTextArea textArea, JNotepadPP notepad);
}
