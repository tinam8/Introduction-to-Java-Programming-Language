package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.TranslationKeys;
import hr.fer.zemris.java.hw11.jnotepadpp.selection.SelectionAction;
import hr.fer.zemris.java.hw11.jnotepadpp.selection.SelectionOperations;
import hr.fer.zemris.java.hw11.jnotepadpp.sort.SortAction;
import hr.fer.zemris.java.hw11.jnotepadpp.sort.SortOperations;
import hr.fer.zemris.java.hw11.statusbar.StatusBarPanel;

/**
 * Program that implements text editor. Gives basic functionalities like: <br>
 * creating a new blank document<br>
 * • opening existing document<br>
 * • saving document<br>
 * • saving-as document (warns user if file already exists)<br>
 * • close document shown it a tab (and remove that tab)<br>
 * • cut/copy/paste text<br>
 * • statistical info<br>
 * • exiting application<br>
 * Also it is available to choose one of the following languages: Croatian,
 * English and German.
 * 
 * @author tina
 *
 */
public class JNotepadPP extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * instance of {@link JTabbedPane}, each tab has one document for editing.
	 */
	private JTabbedPane tabbedPane = new JTabbedPane();
	/** List of buttons that tabs contain, used for closing them */
	private List<JButton> closeButtons;
	/** List of instances of {@link Tab} */
	private List<Tab> tabs;
	/** Text that user cuts of document or copies. */
	private String textClip;
	/**
	 * Instance of {@link StatusBarPanel} that holds information about editor
	 * and document current state.
	 */
	private StatusBarPanel statusBar;
	/** Localization provider that informs when language has been changed. */
	private FormLocalizationProvider flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
	/** Properties file name for Croatian */
	private static String HR = "hr";
	/** Properties file name for English */
	private static String EN = "en";
	/** Properties file name for German */
	private static String DE = "de";

	/**
	 * Constructor that sets GUI and all of the other functionalities of the
	 * editor.
	 */
	public JNotepadPP() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setSize(800, 600);
		setLocationRelativeTo(null);

		tabs = new ArrayList<>();
		closeButtons = new ArrayList<>();
		statusBar = new StatusBarPanel(flp);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closeJNotepadPP();
			}

			@Override
			public void windowClosed(WindowEvent e) {
				statusBar.getClock().finish();
			}
		});

		initGUI();
	}

	/**
	 * Method that creates components of GUI.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		JPanel panel = new JPanel(new BorderLayout());

		getTabbedPane().addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (getTabs().size() == 0 || getTabbedPane().getTabCount() == 0) {
					setTitle("JNotepad++");
					return;
				}
				String name = "";

				Path filePath = getTabs().get(getTabbedPane().getSelectedIndex()).getFilePath();
				if (filePath == null) {
					name = "Unsaved";
				} else {
					name = filePath.toString();
				}

				setTitle(name + " - JNotepad++");
				updateStatusBar(getTabs().get(getTabbedPane().getSelectedIndex()));
				updateSelectionTools();
			}
		});

		panel.add(getTabbedPane());

		createActions();
		createMenus();
		creteToolbars(panel);
		cp.add(panel, BorderLayout.CENTER);
		cp.add(statusBar, BorderLayout.PAGE_END);
	}

	/**
	 * Method that adds values to all of the actions.
	 */
	private void createActions() {
		createBlankDocumentAction.putValue(Action.NAME, "New file");
		createBlankDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		createBlankDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		createBlankDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Used to create a new blank document.");

		openDocumentAction.putValue(Action.NAME, "Open file");
		openDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		openDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Used to open new file from disk.");

		saveDocumentAction.putValue(Action.NAME, "Save");
		saveDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Used to save file to disk.");

		saveDocumentAsAction.putValue(Action.NAME, "Save as");
		saveDocumentAsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control A"));
		saveDocumentAsAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		saveDocumentAsAction.putValue(Action.SHORT_DESCRIPTION, "Used to save file to chosen location at disk.");

		closeTabAction.putValue(Action.NAME, "Close tab");
		closeTabAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		closeTabAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		closeTabAction.putValue(Action.SHORT_DESCRIPTION, "Closes tab.");

		getStatInfoAction.putValue(Action.NAME, "Info");
		getStatInfoAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		getStatInfoAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
		getStatInfoAction.putValue(Action.SHORT_DESCRIPTION, "Shows statistics info.");

		cutTextAction.putValue(Action.NAME, "Cut");
		cutTextAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		cutTextAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		cutTextAction.putValue(Action.SHORT_DESCRIPTION, "Cuts selected text.");
		cutTextAction.setEnabled(false);

		copyTextAction.putValue(Action.NAME, "Copy");
		copyTextAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		copyTextAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		copyTextAction.putValue(Action.SHORT_DESCRIPTION, "Copies selected text.");
		copyTextAction.setEnabled(false);

		pasteTextAction.putValue(Action.NAME, "Paste");
		pasteTextAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		pasteTextAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
		pasteTextAction.putValue(Action.SHORT_DESCRIPTION, "Paste previouslu cut or copied text.");

		toUpperCaseAction.putValue(Action.NAME, "ToUpper");
		toUpperCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));
		toUpperCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
		toUpperCaseAction.putValue(Action.SHORT_DESCRIPTION, "Tranforms selected text to uppercase.");
		toUpperCaseAction.setEnabled(false);

		toLowerCaseAction.putValue(Action.NAME, "ToLower");
		toLowerCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control L"));
		toLowerCaseAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
		toLowerCaseAction.putValue(Action.SHORT_DESCRIPTION, "Tranforms selected text to lowercase.");
		toLowerCaseAction.setEnabled(false);

		toggleAction.putValue(Action.NAME, "Toggle");
		toggleAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control T"));
		toggleAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		toggleAction.putValue(Action.SHORT_DESCRIPTION, "Toggles selected text.");
		toggleAction.setEnabled(false);

		sortAscendingAction.putValue(Action.NAME, "Ascending");
		sortAscendingAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
		sortAscendingAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		sortAscendingAction.putValue(Action.SHORT_DESCRIPTION, "Sorts selected lines ascending.");
		sortAscendingAction.setEnabled(false);

		sortDescendingAction.putValue(Action.NAME, "Descending");
		sortDescendingAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control D"));
		sortDescendingAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
		sortDescendingAction.putValue(Action.SHORT_DESCRIPTION, "Sorts selected lines descending.");
		sortDescendingAction.setEnabled(false);

		sortUniqueAction.putValue(Action.NAME, "Unique");
		sortUniqueAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
		sortUniqueAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_Q);
		sortUniqueAction.putValue(Action.SHORT_DESCRIPTION, "Removes from selection all lines which are duplicates.");
		sortUniqueAction.setEnabled(false);

	}

	/**
	 * Method that creates menu bar with file and edit options.
	 */
	private void createMenus() {
		JMenuBar menubar = new JMenuBar();

		JMenu fileMenu = new JMenu(new LocalizableAction(TranslationKeys.file, flp));
		menubar.add(fileMenu);

		fileMenu.add(new JMenuItem(createBlankDocumentAction));
		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAsAction));
		fileMenu.add(new JMenuItem(closeTabAction));
		fileMenu.add(new JMenuItem(getStatInfoAction));

		JMenu editMenu = new JMenu(new LocalizableAction(TranslationKeys.edit, flp));
		menubar.add(editMenu);

		editMenu.add(new JMenuItem(cutTextAction));
		editMenu.add(new JMenuItem(copyTextAction));
		editMenu.add(new JMenuItem(pasteTextAction));

		JMenu languagesMenu = new JMenu(new LocalizableAction(TranslationKeys.language, flp));
		menubar.add(languagesMenu);

		JMenuItem hr = new JMenuItem(new LocalizableAction(TranslationKeys.croatian, flp));
		JMenuItem en = new JMenuItem(new LocalizableAction(TranslationKeys.english, flp));
		JMenuItem de = new JMenuItem(new LocalizableAction(TranslationKeys.german, flp));

		addLanguageChangeListener(hr, HR);
		addLanguageChangeListener(en, EN);
		addLanguageChangeListener(de, DE);

		languagesMenu.add(hr);
		languagesMenu.add(en);
		languagesMenu.add(de);

		JMenu tools = new JMenu(new LocalizableAction(TranslationKeys.tools, flp));
		menubar.add(tools);

		JMenu changeCase = new JMenu(new LocalizableAction(TranslationKeys.changeCase, flp));
		tools.add(changeCase);

		JMenuItem upCase = new JMenuItem(toUpperCaseAction);
		JMenuItem lowCase = new JMenuItem(toLowerCaseAction);
		JMenuItem toggle = new JMenuItem(toggleAction);

		changeCase.add(upCase);
		changeCase.add(lowCase);
		changeCase.add(toggle);

		JMenu sort = new JMenu(new LocalizableAction(TranslationKeys.sort, flp));
		tools.add(sort);

		JMenuItem sortAsc = new JMenuItem(sortAscendingAction);
		JMenuItem sortDesc = new JMenuItem(sortDescendingAction);
		JMenuItem sortUniq = new JMenuItem(sortUniqueAction);

		sort.add(sortAsc);
		sort.add(sortDesc);
		sort.add(sortUniq);

		setJMenuBar(menubar);
	}

	/**
	 * Method that adds listener to the language menu item.
	 * 
	 * @param item
	 *            instance of {@link JMenuItem}
	 * @param language
	 *            language to set to {@link LocalizationProvider}
	 */
	private void addLanguageChangeListener(JMenuItem item, String language) {
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage(language);
			}
		});
	}

	/**
	 * Method that creates toolbar with options for opening and saving
	 * documents, and for editing them.
	 * 
	 * @param panel
	 *            panel that will contain toolbar
	 */
	private void creteToolbars(JPanel panel) {
		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(true);

		toolbar.add(new JButton(createBlankDocumentAction));
		toolbar.add(new JButton(openDocumentAction));
		toolbar.add(new JButton(saveDocumentAction));
		toolbar.add(new JButton(saveDocumentAsAction));
		toolbar.addSeparator();
		toolbar.add(new JButton(cutTextAction));
		toolbar.add(new JButton(copyTextAction));
		toolbar.add(new JButton(pasteTextAction));
		toolbar.addSeparator();
		toolbar.add(new JButton(getStatInfoAction));
		toolbar.addSeparator();
		toolbar.add(new JButton(sortAscendingAction));
		toolbar.add(new JButton(sortDescendingAction));
		toolbar.add(new JButton(sortUniqueAction));

		panel.add(toolbar, BorderLayout.PAGE_START);
	}

	/**
	 * Method that closes window but before closing checks if there is any
	 * unsaved work and asks user if it should be saved or not.
	 */
	private void closeJNotepadPP() {
		boolean hasUnsavedWork = false;
		for (Tab tab : getTabs()) {
			if (tab.isChanged()) {
				hasUnsavedWork = true;
			}
		}

		if (hasUnsavedWork) {
			int result = JOptionPane.showConfirmDialog(JNotepadPP.this,
					flp.getString(TranslationKeys.saveChangesQuestion), flp.getString(TranslationKeys.systemMessage),
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

			switch (result) {
			case JOptionPane.YES_OPTION:
				for (int i = 0; i < getTabs().size(); i++) {
					if (getTabs().get(i).isChanged()) {
						saveFile(getTabs().get(i).getFilePath(), i);
					}
				}
				return;
			case JOptionPane.NO_OPTION:
				dispose();
			case JOptionPane.CANCEL_OPTION:
				return;
			case JOptionPane.CLOSED_OPTION:
				return;
			}
		}

		int result = JOptionPane.showConfirmDialog(JNotepadPP.this, flp.getString(TranslationKeys.exitQuestion),
				flp.getString(TranslationKeys.systemMessage), JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE);

		if (result == JOptionPane.YES_OPTION) {
			dispose();
		}

	}

	/**
	 * Action instance that creates new blank document.
	 */
	private Action createBlankDocumentAction = new LocalizableAction(TranslationKeys.createBlankDocumentAction, flp) {
		/** */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			addTab(null, "");
		}
	};

	/**
	 * Action instance that opens existing document.
	 */
	private Action openDocumentAction = new LocalizableAction(TranslationKeys.openDocumentAction, flp) {
		/** */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Open file");
			if (fc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}

			Path filePath = fc.getSelectedFile().toPath();

			if (!Files.isReadable(filePath)) {
				JOptionPane.showMessageDialog(JNotepadPP.this, filePath + flp.getString(TranslationKeys.cantRead),
						flp.getString(TranslationKeys.errorMessage), JOptionPane.ERROR_MESSAGE);
				return;
			}

			int position = getPositionFromtabs(filePath);
			if (position != -1) {
				tabbedPane.setSelectedIndex(position);
				return;
			}

			String text = null;
			try {
				text = new String(Files.readAllBytes(filePath), StandardCharsets.UTF_8);
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(JNotepadPP.this,
						flp.getString(TranslationKeys.cantRead) + ": " + filePath + ".",
						flp.getString(TranslationKeys.errorMessage), JOptionPane.ERROR_MESSAGE);
				return;
			}

			addTab(filePath, text);

		}

		/**
		 * Supplementary method that gets position of file in tabbed pane.
		 * 
		 * @param filePath
		 *            path to the file on disk
		 * @return position if file is opened, -1 otherwise
		 */
		private int getPositionFromtabs(Path filePath) {
			for (int i = 0; i < tabs.size(); i++) {
				Path path = tabs.get(i).getFilePath();
				if (path == null) continue;
				
				if (path.equals(filePath)) {
					return i;
				}
			}

			return -1;
		}

	};

	/**
	 * Method that adds instance of {@link Tab} to the tabbed pane.
	 * 
	 * @param filePath
	 *            file path f the file that tab will hold
	 * @param text
	 *            text of document to show
	 */
	private void addTab(Path filePath, String text) {
		JTextArea textArea = new JTextArea(text);

		JButton closeBtn = new JButton(closeTabAction);
		
		closeButtons.add(closeBtn);
		getTabs().add(new Tab(textArea, filePath, getTabs().size(), getTabbedPane(), closeBtn));
		getTabbedPane().setSelectedIndex(getTabs().size() - 1);
		textArea.addCaretListener(new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent e) {
				updateStatusBar(getTabs().get(getTabs().size() - 1));
				updateSelectionTools();
			}

		});
	}

	/**
	 * Method that enables tools that use selected text if there is any,
	 * otherwise it disables it.
	 */
	private void updateSelectionTools() {
		if (getTabs().get(tabbedPane.getSelectedIndex()).getSel() == 0) {
			disableSelectionActions();
		} else {
			enableSelectionActions();
		}
	}

	/**
	 * Enables action that use selected text.
	 */
	private void enableSelectionActions() {
		cutTextAction.setEnabled(true);
		copyTextAction.setEnabled(true);
		toUpperCaseAction.setEnabled(true);
		toLowerCaseAction.setEnabled(true);
		toggleAction.setEnabled(true);

		sortAscendingAction.setEnabled(true);
		sortDescendingAction.setEnabled(true);
		sortUniqueAction.setEnabled(true);
	}

	/**
	 * Disables action that use selected text.
	 */
	private void disableSelectionActions() {
		cutTextAction.setEnabled(false);
		copyTextAction.setEnabled(false);
		toUpperCaseAction.setEnabled(false);
		toLowerCaseAction.setEnabled(false);
		toggleAction.setEnabled(false);

		sortAscendingAction.setEnabled(false);
		sortDescendingAction.setEnabled(false);
		sortUniqueAction.setEnabled(false);
	}

	/**
	 * Method that changes data in status bar ({@link StatusBarPanel}).
	 * 
	 * @param tab
	 *            instance of {@link Tab} that hold relevant data about
	 *            documents.
	 */
	private void updateStatusBar(Tab tab) {
		statusBar.setLength(tab.getLength());
		statusBar.setLn(tab.getLn());
		statusBar.setSel(Math.abs(tab.getCaret().getDot() - tab.getCaret().getMark()));
		statusBar.setCol(tab.getCol());
	}

	/** Action that saves changes of the file if they occurred. */
	private Action saveDocumentAction = new LocalizableAction(TranslationKeys.saveDocumentAction, flp) {
		/** */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			int selectedIndex = getTabbedPane().getSelectedIndex();
			if (selectedIndex < 0) {
				return;
			}

			Path openedFilePath = getTabs().get(selectedIndex).getFilePath();

			if (openedFilePath != null) {
				saveFile(openedFilePath, selectedIndex);
			} else {
				saveAsFile(selectedIndex);
			}
		}

	};

	/**
	 * Method that saves file changes
	 * 
	 * @param openedFilePath
	 *            path f file
	 * @param selectedIndex
	 *            index of selected tab
	 */
	private void saveFile(Path openedFilePath, int selectedIndex) {
		if (openedFilePath == null) {
			saveAsFile(selectedIndex);
			return;
		}

		try {
			Files.write(openedFilePath, getTabs().get(selectedIndex).getText().getBytes(StandardCharsets.UTF_8));
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(JNotepadPP.this, flp.getString(TranslationKeys.unsuccessfulSaveMessage),
					flp.getString(TranslationKeys.errorMessage), JOptionPane.ERROR_MESSAGE);
			return;
		}

		getTabs().get(selectedIndex).setChange();
		JOptionPane.showMessageDialog(JNotepadPP.this, flp.getString(TranslationKeys.successfulSaveMessage),
				flp.getString(TranslationKeys.informationMessage), JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Method that gives user option to choose location and name to save a
	 * document.
	 * 
	 * @param selectedIndex
	 *            index of selected tab
	 */
	private void saveAsFile(int selectedIndex) {
		JFileChooser fc = new JFileChooser() {
			/** */
			private static final long serialVersionUID = 1L;

			@Override
			public void approveSelection() {
				Path openedFilePath = getSelectedFile().toPath();
				if (Files.exists(openedFilePath)) {
					int result = JOptionPane.showConfirmDialog(JNotepadPP.this,
							flp.getString(TranslationKeys.overwriteFileMessage),
							flp.getString(TranslationKeys.warningMessage), JOptionPane.YES_NO_CANCEL_OPTION);

					switch (result) {
					case JOptionPane.YES_OPTION:
						super.approveSelection();
						return;
					case JOptionPane.NO_OPTION:
						return;
					case JOptionPane.CLOSED_OPTION:
						return;

					case JOptionPane.CANCEL_OPTION:
						cancelSelection();
						return;
					}
				}

				super.approveSelection();
			}
		};

		fc.setDialogTitle(flp.getString(TranslationKeys.saveFileDialogTitle));
		if (fc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(JNotepadPP.this, flp.getString(TranslationKeys.saveCancelingMessage),
					flp.getString(TranslationKeys.warningMessage), JOptionPane.WARNING_MESSAGE);
			return;

		}

		Path openedFilePath = fc.getSelectedFile().toPath();
		getTabs().get(selectedIndex).setFilePath(openedFilePath, selectedIndex);
		saveFile(openedFilePath, selectedIndex);
	}

	/**
	 * Action that saves document at preferred destination. Warns user if file
	 * already exists.
	 */
	private Action saveDocumentAsAction = new LocalizableAction(TranslationKeys.saveDocumentAsAction, flp) {
		/** */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (getTabs().size() == 0) {
				return;
			}
			saveAsFile(getTabbedPane().getSelectedIndex());
		}
	};

	/** Action for closing tabs. */
	private Action closeTabAction = new LocalizableAction(TranslationKeys.closeTabAction, flp) {
		/**  */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (getTabs().size() == 0) {
				return;
			}

			int index = getTabbedPane().getSelectedIndex();

			if (e.getSource() instanceof JButton) {
				JButton closeBtn = (JButton) e.getSource();
				index = closeButtons.indexOf(closeBtn);
			}

			Tab tab = getTabs().get(index);
			if (!tab.isChanged()) {
				closeTab(index);
				return;
			}

			new JOptionPane();
			int result = JOptionPane.showConfirmDialog(JNotepadPP.this,
					flp.getString(TranslationKeys.saveChangesQuestion), flp.getString(TranslationKeys.warningMessage),
					JOptionPane.YES_NO_CANCEL_OPTION);
			switch (result) {
			case JOptionPane.YES_OPTION:
				saveAsFile(index);
				closeTab(index);
			case JOptionPane.NO_OPTION:
				closeTab(index);
			case JOptionPane.CANCEL_OPTION:
				return;
			case JOptionPane.CLOSED_OPTION:
				return;
			}

		}

		/**
		 * Method that closes tab
		 * 
		 * @param index
		 *            index of tab to close
		 */
		private void closeTab(int index) {
			getTabbedPane().remove(index);
			closeButtons.remove(index);
			getTabs().remove(index);

			if (getTabs().size() == 0) {
				setTitle("JNotepad++");
			} else {
				setTitle(getTabs().get(getTabbedPane().getSelectedIndex()).getFilePath() + " - JNotepad++");
			}
		}
	};

	/** Action for cutting selected part of text. */
	private Action cutTextAction = new SelectionAction(TranslationKeys.cutTextAction, flp, SelectionOperations.cut,
			this) {
		/** */
		private static final long serialVersionUID = 1L;

	};

	/** Action for copying selected part of text. */
	private Action copyTextAction = new SelectionAction(TranslationKeys.copyTextAction, flp, SelectionOperations.copy,
			this) {
		/** */
		private static final long serialVersionUID = 1L;

	};

	/** Action for pasting text. */
	private Action pasteTextAction = new SelectionAction(TranslationKeys.pasteTextAction, flp,
			SelectionOperations.paste, this) {
		/** */
		private static final long serialVersionUID = 1L;

	};

	/** Action that changes selected text to upper case. */
	private Action toUpperCaseAction = new SelectionAction(TranslationKeys.toUpperCaseAction, flp,
			SelectionOperations.toUpperCase, this) {
		/** */
		private static final long serialVersionUID = -1093551484340083102L;

	};

	/** Action that changes selected text to lower case. */
	private Action toLowerCaseAction = new SelectionAction(TranslationKeys.toLowerCaseAction, flp,
			SelectionOperations.toLowerCase, this) {
		/** */
		private static final long serialVersionUID = 1L;

	};

	/** Action that changes selected text to lower case. */
	private Action toggleAction = new SelectionAction(TranslationKeys.toggleAction, flp, SelectionOperations.toggleCase,
			this) {
		/** */
		private static final long serialVersionUID = 1L;

	};
	/** Action for sorting selected lines asscending */
	private Action sortAscendingAction = new SortAction(TranslationKeys.sortAscendingAction, flp, this,
			SortOperations.sortAscendding) {
		/**  */
		private static final long serialVersionUID = 1L;

	};
	/** Action for sorting selected lines descending */
	private Action sortDescendingAction = new SortAction(TranslationKeys.sortDescendingAction, flp, this,
			SortOperations.sortDescending) {
		/** */
		private static final long serialVersionUID = 1L;

	};
	/**
	 * Action that removes from selection all lines which are duplicates (only
	 * the first occurrence is retained).
	 */
	private Action sortUniqueAction = new SortAction(TranslationKeys.sortUniqueAction, flp, this,
			SortOperations.sortUnique) {
		/** */
		private static final long serialVersionUID = 1L;

	};

	/**
	 * Action for getting statistical info about currently selected text.
	 */
	private Action getStatInfoAction = new LocalizableAction(TranslationKeys.getStatInfoAction, flp) {
		/** */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (getTabs().size() == 0) {
				JOptionPane.showMessageDialog(JNotepadPP.this, flp.getString(TranslationKeys.noOpenedFilesMsessage),
						flp.getString(TranslationKeys.informationMessage), JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			int index = getTabbedPane().getSelectedIndex();
			String text = getTabs().get(index).getText();
			int charNumber = text.length();

			int linesNumber = charNumber - text.replaceAll("\n+", "").length();
			int charNonBlank = text.replaceAll("\\s+", "").length();

			JOptionPane.showMessageDialog(JNotepadPP.this,
					flp.getString(TranslationKeys.yourDocumentHas) + charNumber + " "
							+ flp.getString(TranslationKeys.characters) + " " + charNonBlank + " "
							+ flp.getString(TranslationKeys.nonBlankCharacters) + linesNumber + " "
							+ flp.getString(TranslationKeys.lines),
					flp.getString(TranslationKeys.informationMessage), JOptionPane.INFORMATION_MESSAGE);
		}
	};

	/**
	 * Getter for tab pane.
	 * 
	 * @return tab pane
	 */
	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	/**
	 * Getter for list of tabs
	 * 
	 * @return list of {@link Tab}
	 */
	public List<Tab> getTabs() {
		return tabs;
	}

	/**
	 * Getter for text clip that containers remembered text from cutting or
	 * copying.
	 * 
	 * @return remembered text
	 */
	public String getTextClip() {
		return textClip;
	}

	/**
	 * Sets text that needs to be remembers.
	 * 
	 * @param textClip
	 *            text to remember
	 */
	public void setTextClip(String textClip) {
		this.textClip = textClip;
	}

	/**
	 * Method that starts program
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JNotepadPP().setVisible(true);
		});
	}
}