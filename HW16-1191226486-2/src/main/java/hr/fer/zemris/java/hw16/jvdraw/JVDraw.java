package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelObjects;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingObjectListModel;
import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;

/**
 * Program that allows user to draw lines, circles and filled circles. Program
 * features menubar, toolbar, drawing canvas and object list. Some
 * functionalities are exporting drawn images, saving images as special file and
 * reopening them. Drawn object are presented in the list and can be removed and
 * edited.
 * 
 * @author tina
 *
 */
public class JVDraw extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Model that holds geometric objects and tracks their state.
	 */
	private DrawingModel model;
	/**
	 * Canvas used for drawing.
	 */
	private JDrawingCanvas canvas;
	/**
	 * Determines if image has been modified after saving.
	 */
	private boolean hasUnsavedWork = false;
	/**
	 * File path to the place where drawing is saved
	 */
	private Path filePath;
	/**
	 * Extension for image export
	 */
	private String extension;

	/**
	 * Constructor that sets GUI and all of the other functionalities of the
	 * program.
	 */
	public JVDraw() {
		model = new DrawingModelObjects();
		// setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		setSize(800, 300);
		setLocationRelativeTo(null);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closeJVDraw();
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

		JColorArea fgColorArea = new JColorArea();
		JColorArea bgColorArea = new JColorArea();
		canvas = new JDrawingCanvas(model, fgColorArea, bgColorArea, this);
		JColorLabel colorLabel = new JColorLabel(fgColorArea, bgColorArea);
		JToolBar toolbar = createToolbar(fgColorArea, bgColorArea);

		cp.add(toolbar, BorderLayout.PAGE_START);
		cp.add(colorLabel, BorderLayout.PAGE_END);
		cp.add(canvas, BorderLayout.CENTER);

		addList(cp);
		createActions();
		createMenus();
	}

	/**
	 * Supplementary method that creates menu
	 */
	private void createMenus() {
		JMenuBar menubar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		menubar.add(fileMenu);

		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAsAction));
		fileMenu.add(new JMenuItem(exportDocumentAction));
		fileMenu.add(new JMenuItem(exitDocumentAsAction));

		setJMenuBar(menubar);
	}
	
	/**
	 * Method that creates toolbar that has two color choosers and three buttons
	 * for choosing geometric types to draw.
	 * 
	 * @param fgColorArea
	 *            instance of {@link JColorArea} for foreground color
	 * @param bgColorArea
	 *            instance of {@link JColorArea} for background color
	 * @return instance of {@link JToolBar}
	 */
	private JToolBar createToolbar(JColorArea fgColorArea, JColorArea bgColorArea) {
		fgColorArea.setToolTipText("Choose foreground color");
		bgColorArea.setToolTipText("Choose background color");

		JToolBar toolbar = new JToolBar();
		toolbar.add(fgColorArea);
		toolbar.add(bgColorArea);

		JToggleButton toggleButton1 = createToggleButton("Line");
		toggleButton1.setSelected(true);
		JToggleButton toggleButton2 = createToggleButton("Circle");
		JToggleButton toggleButton3 = createToggleButton("Filled circle");

		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(toggleButton1);
		buttonGroup.add(toggleButton2);
		buttonGroup.add(toggleButton3);

		toolbar.add(toggleButton1);
		toolbar.add(toggleButton2);
		toolbar.add(toggleButton3);

		return toolbar;
	}

	/**
	 * Method that creates toggle button for selection of geometric types.
	 * Button click changes geometric type to draw.
	 * 
	 * @param string
	 *            name of type
	 * @return toggle button that has listener when change occurs
	 */
	private JToggleButton createToggleButton(String string) {
		JToggleButton button = new JToggleButton(string);
		button.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					canvas.setSelectedObjectType(string);
				}
			}
		});

		return button;
	}

	/**
	 * Adds list that holds all currently defined objects. Each object has its
	 * automatically generated name.
	 * 
	 * @param cp
	 *            {@link Container} that holds list
	 */
	private void addList(Container cp) {
		JList<GeometricalObject> list = new JList<>(new DrawingObjectListModel(model));

		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					System.out.println("double click");
					 int index = list.locationToIndex(e.getPoint());
					 GeometricalObject selectedObject =
					 model.getObject(index);
					 selectedObject.editWithDialog();
				}
			}

		});

		list.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (JVDraw.this.getFocusOwner() != list) {
					return;
				}

				if (e.getKeyCode() == KeyEvent.VK_DELETE) {
					int index = list.getSelectedIndex();
					GeometricalObject selectedObject = model.getObject(index);
					System.out.println("dohvacen " + selectedObject);
					model.remove(selectedObject);
					hasUnsavedWork = true;
				}
			}
		});

		cp.add(new JScrollPane(list), BorderLayout.LINE_END);
	}

	/**
	 * Supplementary method that creates actions
	 */
	private void createActions() {
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

		exitDocumentAsAction.putValue(Action.NAME, "Exit");
		exitDocumentAsAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		exitDocumentAsAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		exitDocumentAsAction.putValue(Action.SHORT_DESCRIPTION, "Exits application");

		exportDocumentAction.putValue(Action.NAME, "Export");
		exportDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
		exportDocumentAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		exportDocumentAction.putValue(Action.SHORT_DESCRIPTION, "Exports image");
	}

	/**
	 * Action that implements opening existing files that have extension '.jvd'
	 */
	Action openDocumentAction = new AbstractAction() {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (hasUnsavedWork) {
				int result = JOptionPane.showConfirmDialog(JVDraw.this, "Do you want to save work.", "exit",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

				switch (result) {
				case JOptionPane.YES_OPTION:
					saveFile();
					return;
				case JOptionPane.NO_OPTION:
					break;
				case JOptionPane.CANCEL_OPTION:
					break;
				case JOptionPane.CLOSED_OPTION:
					break;
				}
				
				hasUnsavedWork = false;
			}

			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Open file");
			if (fc.showOpenDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}

			model.removeAll();

			filePath = fc.getSelectedFile().toPath();

			if (!Files.isReadable(filePath) || !filePath.getFileName().toString().toLowerCase().endsWith(".jvd")) {
				JOptionPane.showMessageDialog(JVDraw.this, "File can not be read", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			String text = null;
			try {
				text = new String(Files.readAllBytes(filePath), StandardCharsets.UTF_8);
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(JVDraw.this, "Can not read the file" + ": " + filePath + ".",
						"Error message", JOptionPane.ERROR_MESSAGE);
				return;
			}

			List<GeometricalObject> objects = ActionsUtil.parseImage(text);
			if (objects == null) {
				return;
			}

			objects.forEach(object -> {
				model.add(object);
			});
		}
	};

	/**
	 * Action that saves changes of the file if they occurred.
	 */
	private Action saveDocumentAction = new AbstractAction() {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {

			if (filePath != null) {
				saveFile();
			} else {
				saveAsFile();
			}
		}
	};

	/**
	 * Method that saves file changes. If file has not been save before file
	 * choose dialog opens.
	 */
	private void saveFile() {
		if (filePath == null) {
			saveAsFile();
			return;
		}

		try {
			Files.write(filePath, ActionsUtil.createJVDfile(model).getBytes(StandardCharsets.UTF_8));
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(JVDraw.this, "Error while saving file", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		JOptionPane.showMessageDialog(JVDraw.this, "Saved.", "Information", JOptionPane.INFORMATION_MESSAGE);
		hasUnsavedWork = false;
	}

	/**
	 * Method that gives user option to choose location and name to save a
	 * document.
	 * 
	 */
	private void saveAsFile() {
		JFileChooser fc = new JFileChooser() {
			/** */
			private static final long serialVersionUID = 1L;

			@Override
			public void approveSelection() {
				filePath = getSelectedFile().toPath();
				if (Files.exists(filePath)) {
					int result = JOptionPane.showConfirmDialog(JVDraw.this, "File exist do you want to overwrite it",
							"Warning", JOptionPane.YES_NO_CANCEL_OPTION);

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

		fc.setDialogTitle("Save file");
		if (fc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(JVDraw.this, "Canceled", "Warning", JOptionPane.WARNING_MESSAGE);
			return;

		}

		String path = fc.getSelectedFile() + ".jvd";
		filePath = Paths.get(path);
		saveFile();
	}

	/**
	 * Action that saves document at preferred destination. Warns user if file
	 * already exists.
	 */
	private Action saveDocumentAsAction = new AbstractAction() {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			saveAsFile();
		}
	};

	/**
	 * Action which checks to see if DrawingModel has changed since the last
	 * saving; if so, user is asked if he wants to save the image, cancel the
	 * exit action or reject the changes.
	 */
	private Action exitDocumentAsAction = new AbstractAction() {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			closeJVDraw();
		}
	};

	/**
	 * When user selects export this action is called. User is asked which
	 * format he wants (offered: JPG, PNG, GIF) and then he is asked to select
	 * where he wants to save the image.
	 */
	private Action exportDocumentAction = new AbstractAction() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JPanel panel = new JPanel();
			panel.setSize(new Dimension(300, 200));

			addExtensionOptions(panel);

			int result = JOptionPane.showConfirmDialog(null, panel, "Edit object", JOptionPane.OK_CANCEL_OPTION);
			if (result == JOptionPane.CANCEL_OPTION) {
				return;
			}
			
			JFileChooser fc = new JFileChooser() {
				/** */
				private static final long serialVersionUID = 1L;

				@Override
				public void approveSelection() {
					filePath = getSelectedFile().toPath();
					if (Files.exists(filePath)) {
						int result = JOptionPane.showConfirmDialog(JVDraw.this, "Image exist do you want to overwrite it",
								"Warning", JOptionPane.YES_NO_CANCEL_OPTION);

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
			fc.setDialogTitle("Export image");
			if (fc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(JVDraw.this, "Canceled", "Warning", JOptionPane.WARNING_MESSAGE);
				return;

			}
			
			filePath = fc.getSelectedFile().toPath();
			ActionsUtil.exportImage(canvas, model, extension, filePath);
		}

		private void addExtensionOptions(JPanel panel) {
			JToggleButton toggleButton1 = createButton("png");
			toggleButton1.setSelected(true);
			JToggleButton toggleButton2 = createButton("jpg");
			JToggleButton toggleButton3 = createButton("gif");

			ButtonGroup buttonGroup = new ButtonGroup();
			buttonGroup.add(toggleButton1);
			buttonGroup.add(toggleButton2);
			buttonGroup.add(toggleButton3);

			panel.add(toggleButton1);
			panel.add(toggleButton2);
			panel.add(toggleButton3);
		}

		private JToggleButton createButton(String string) {
			JToggleButton button = new JToggleButton(string);
			
			button.addItemListener(new ItemListener() {

				@Override
				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						extension = string;
					}
				}
			});

			return button;
		}
	};

	

	/**
	 * Method for closing main window. Checks to see if DrawingModel has changed
	 * since the last saving; if so, user is asked if he wants to save the
	 * image, cancel the exit action or reject the changes.
	 */
	private void closeJVDraw() {
		if (hasUnsavedWork) {
			int result = JOptionPane.showConfirmDialog(JVDraw.this, "Do you want to save work.", "exit",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

			switch (result) {
			case JOptionPane.YES_OPTION:
				saveFile();
				return;
			case JOptionPane.NO_OPTION:
				dispose();
			case JOptionPane.CANCEL_OPTION:
				return;
			case JOptionPane.CLOSED_OPTION:
				return;
			}
		}

		int result = JOptionPane.showConfirmDialog(JVDraw.this, "Do you want to exit.", "exit",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

		if (result == JOptionPane.YES_OPTION) {
			dispose();
		}
	}

	/**
	 * Setter for hashasUnsavedWork. 
	 * @param hasUnsavedWork true if there is unsaved work, false otherwise
	 */
	public void setHasUnsavedWork(boolean hasUnsavedWork) {
		this.hasUnsavedWork = hasUnsavedWork;
	}

	/**
	 * Method that starts program
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JVDraw().setVisible(true);
		});
	}
}
