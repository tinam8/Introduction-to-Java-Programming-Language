package hr.fer.zemris.java.hw11.jnotepadpp.selection;

import java.awt.event.ActionEvent;
import java.util.PrimitiveIterator.OfDouble;

import javax.swing.JTextArea;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * Class that extend {@link LocalizableAction} it holds instance of
 * {@link ISelection} which determines what kind of operation will be
 * performed
 * 
 * @author tina
 *
 */
public class SelectionAction extends LocalizableAction {
	/**  */
	private static final long serialVersionUID = 1L;
	/** instance of {@link ISelection} */
	private ISelection selectionWork;
	/** Instance of {@link JNotepadPP} */
	private JNotepadPP notepad;

	/**
	 * Constructor
	 * 
	 * @param key
	 *            key for action
	 * @param lp
	 *            instance of {@link LocalizationProvider}
	 * @param selectionWork
	 *            instance of {@link ISelection}
	 * @param notepad
	 *            instance of {@link JNotepadPP}
	 */
	public SelectionAction(String key, ILocalizationProvider lp, ISelection selectionWork, JNotepadPP notepad) {
		super(key, lp);
		this.selectionWork = selectionWork;
		this.notepad = notepad;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int index = notepad.getTabbedPane().getSelectedIndex();
		JTextArea textArea = notepad.getTabs().get(index).getTextArea();

		int offset = Math.min(textArea.getCaret().getDot(), textArea.getCaret().getMark());
		int len = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());

		selectionWork.process(offset, len, textArea, notepad);
	}

}
