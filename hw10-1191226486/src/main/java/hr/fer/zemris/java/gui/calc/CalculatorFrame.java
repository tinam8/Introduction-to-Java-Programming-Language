package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import hr.fer.zemris.java.gui.calc.operation.BinaryOperators;
import hr.fer.zemris.java.gui.calc.operation.IBinaryOperator;
import hr.fer.zemris.java.gui.calc.operation.IUnaryOperator;
import hr.fer.zemris.java.gui.calc.operation.UnaryOperators;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Frame that implements GUI for simple calculator. More info at
 * {@link Calculator}.
 * 
 * @author tina
 *
 */
public class CalculatorFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Map that connects buttons for binary operations and correct instance of
	 * binary operators
	 */
	private Map<String, IBinaryOperator> binOperators;
	/**
	 * Map that connects buttons for unary operations and correct instance of
	 * unary operators
	 */
	private Map<String, IUnaryOperator> unOperators;
	/**
	 * Map that connects buttons for inverse unary operations and correct
	 * instance of unary operators.
	 */
	private Map<String, IUnaryOperator> inversUnOperators;
	/** Sack that contains values that user pushes when pressing button push */
	private Stack<Double> stack;
	/** Last used binary operator */
	private IBinaryOperator binOperator;
	/** Indicates if binary operator expects second argument */
	private boolean binActive = false;
	/** Detects if input is positive or not */
	private boolean isPositive = true;
	/** Label that contains every input and output. */
	private JLabel display;
	/** Hold last calculated value */
	private Double result = null;
	/** Checkbox that indicates if inverse function should be used */
	private JCheckBox inverseBox;
	/** Indicates that error has occurred and that calculator has to be reset */
	private boolean error = false;
	/** Message that is show to the user when error occurs. */
	private final String MESSAGE = "Error. Reset calculation.";
	/** Button background color */
	private Color buttonColor = new Color(114, 159, 207);
	
	/**
	 * Constructor that fills frame with content.
	 */
	public CalculatorFrame() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocation(400, 200);
		setSize(500, 200);
		setTitle("Calculator");

		stack = new Stack<>();
		binOperators = new HashMap<>();
		addBinOperators();
		unOperators = new HashMap<>();
		addUnOperators();
		inversUnOperators = new HashMap<>();
		addInverseUnOperators();

		initGUI();
	}

	/**
	 * Method that creates GUI of calculator.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(5));

		addUnaryButtons(cp);
		addBinaryButtons(cp);
		addNumberButtons(cp);
		addDisplay(cp);
		addStackButtons(cp);
		addResetButton(cp);
		addClearButton(cp);
		addEqualsButton(cp);
		addSignButton(cp);
		addInverseButton(cp);
		
		setMinimumSize(getContentPane().getMinimumSize());
	}

	/**
	 * Method that adds negative sign
	 * 
	 * @param cp
	 *            container that should contain button
	 */
	private void addSignButton(Container cp) {
		addButton(cp, "+/-", new RCPosition(5, 4), new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (isPositive) {
					display.setText("-" + display.getText());
					isPositive = false;
				} else {
					display.setText(display.getText().substring(1));
					isPositive = true;
				}

			}
		});
	}

	/**
	 * Method that adds button for displaying result of operations.
	 * 
	 * @param cp
	 *            container that should contain button.
	 */
	private void addEqualsButton(Container cp) {
		addButton(cp, "=", new RCPosition(1, 6), new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Double input = getCurrentInput();

				if (input == null) {
					display.setText(MESSAGE);
					error = true;
				}

				if (result != null) {
					if (binActive) {
						result = binOperator.compute(result, input);
						binActive = false;
					}

					display.setText(result.toString());
				} else {
					result = input;
				}
			}
		});
	}

	/**
	 * Method that adds button for reseting calculator
	 * 
	 * @param cp
	 *            container that should contain it
	 */
	private void addClearButton(Container cp) {

		addButton(cp, "clr", new RCPosition(1, 7), new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (error) {
					return;
				}

				display.setText("");
			}
		});

	}

	/**
	 * Method that adds button for reseting calculator
	 * 
	 * @param cp
	 *            container that should contain it
	 */
	private void addResetButton(Container cp) {
		addButton(cp, "res", new RCPosition(2, 7), new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				binActive = false;
				// unActive = false;
				stack.clear();
				display.setText("");
				result = null;
				error = false;
			}
		});

	}

	/**
	 * Method that adds label component for display of results and inputs.
	 * 
	 * @param cp
	 *            container that should contain label
	 */
	private void addDisplay(Container cp) {
		display = new JLabel("");
		display.setBackground(Color.YELLOW);
		display.setBorder(new LineBorder(Color.BLUE));
		display.setOpaque(true);
		display.setHorizontalAlignment(SwingConstants.RIGHT);
		cp.add(display, new RCPosition(1, 1));
	}

	/**
	 * Method that adds all binary operators that calculator supports to a map
	 * for binary operators.
	 */
	private void addBinOperators() {
		binOperators.put("+", BinaryOperators.ADD);
		binOperators.put("-", BinaryOperators.SUB);
		binOperators.put("*", BinaryOperators.MUL);
		binOperators.put("/", BinaryOperators.DIV);
		binOperators.put("x^n", BinaryOperators.DIV);
	}

	/**
	 * Method that adds all unary operators that calculator supports to a map
	 * for unary operators.
	 */
	private void addUnOperators() {
		unOperators.put("1/n", UnaryOperators.INVERSE);
		unOperators.put("log", UnaryOperators.LOG);
		unOperators.put("ln", UnaryOperators.LN);
		unOperators.put("sin", UnaryOperators.SIN);
		unOperators.put("cos", UnaryOperators.COS);
		unOperators.put("tan", UnaryOperators.TAN);
		unOperators.put("ctg", UnaryOperators.CTG);
	}

	/**
	 * Method that adds all inverse unary operators that calculator supports to
	 * a map for inverse unary operators.
	 */
	private void addInverseUnOperators() {
		inversUnOperators.put("1/n", UnaryOperators.INVERSE);
		inversUnOperators.put("log", UnaryOperators.EXP_10);
		inversUnOperators.put("ln", UnaryOperators.EXP_E);
		inversUnOperators.put("sin", UnaryOperators.ARCSIN);
		inversUnOperators.put("cos", UnaryOperators.ARCCOS);
		inversUnOperators.put("tan", UnaryOperators.ARCTAN);
		inversUnOperators.put("ctg", UnaryOperators.ARCCTG);
	}

	/**
	 * Method that adds buttons for binary operates to the container.
	 * 
	 * @param cp
	 *            container to put buttons in
	 */
	private void addBinaryButtons(Container cp) {
		BinaryOperatorListener binListener = new BinaryOperatorListener();

		addButton(cp, "x^n", new RCPosition(5, 1), binListener);
		addButton(cp, "/", new RCPosition(2, 6), binListener);
		addButton(cp, "*", new RCPosition(3, 6), binListener);
		addButton(cp, "+", new RCPosition(4, 6), binListener);
		addButton(cp, "-", new RCPosition(5, 6), binListener);
	}

	/**
	 * Method that adds buttons for unary operators to the container.
	 * 
	 * @param cp
	 *            container to put buttons in
	 */
	private void addUnaryButtons(Container cp) {
		UnaryOperatorListener unListener = new UnaryOperatorListener();

		addButton(cp, "1/n", new RCPosition(2, 1), unListener);
		addButton(cp, "log", new RCPosition(3, 1), unListener);
		addButton(cp, "ln", new RCPosition(4, 1), unListener);
		addButton(cp, "sin", new RCPosition(2, 2), unListener);
		addButton(cp, "cos", new RCPosition(3, 2), unListener);
		addButton(cp, "tan", new RCPosition(4, 2), unListener);
		addButton(cp, "ctg", new RCPosition(5, 2), unListener);
	}

	/**
	 * Method that adds buttons for number inputs.
	 * 
	 * @param cp
	 *            container that should contain numbers
	 */
	private void addNumberButtons(Container cp) {
		NumberListener numListener = new NumberListener();

		addButton(cp, "0", new RCPosition(5, 3), numListener);
		addButton(cp, "1", new RCPosition(4, 3), numListener);
		addButton(cp, "2", new RCPosition(4, 4), numListener);
		addButton(cp, "3", new RCPosition(4, 5), numListener);
		addButton(cp, "4", new RCPosition(3, 3), numListener);
		addButton(cp, "5", new RCPosition(3, 4), numListener);
		addButton(cp, "6", new RCPosition(3, 5), numListener);
		addButton(cp, "7", new RCPosition(2, 3), numListener);
		addButton(cp, "8", new RCPosition(2, 4), numListener);
		addButton(cp, "9", new RCPosition(2, 5), numListener);
		addButton(cp, ".", new RCPosition(5, 5), numListener);
	}

	/**
	 * Method that adds button with given name at given position to the given
	 * container and also adds given listener. Validity of arguments is not
	 * being checked!
	 * 
	 * @param cp
	 *            container
	 * @param name
	 *            name of button
	 * @param position
	 *            wanted position of button
	 * @param listener
	 *            listener that follows action performed on button
	 */
	private void addButton(Container cp, String name, RCPosition position, ActionListener listener) {
		JButton button = new JButton(name);
		button.setBackground(buttonColor);
		button.setBorder(new LineBorder(Color.BLUE));
		button.addActionListener(listener);
		cp.add(button, position);
	}

	/**
	 * Method that adds button that provides use of inverse unary operators
	 * 
	 * @param cp
	 *            container that should contain button
	 */
	private void addInverseButton(Container cp) {
		inverseBox = new JCheckBox("Invs");
		inverseBox.setBackground(buttonColor);
		inverseBox.setBorder(new LineBorder(Color.BLUE));
		cp.add(inverseBox, new RCPosition(5, 7));
	}

	/**
	 * Method that adds push and pop buttons.
	 * 
	 * @param cp
	 *            container that should contain buttons
	 */
	private void addStackButtons(Container cp) {
		ActionListener l = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (error) {
					return;
				}

				JButton button = (JButton) e.getSource();
				if (button.getText().equals("pop")) {
					try {
						display.setText(stack.pop().toString());
					} catch (EmptyStackException e2) {
						JOptionPane.showMessageDialog(CalculatorFrame.this, "Stack is empty.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					Double input = getCurrentInput();
					if (input == null) {
						display.setText(MESSAGE);
						error = true;
						return;
					}

					stack.push(input);
				}
			}
		};

		addButton(cp, "push", new RCPosition(3, 7), l);
		addButton(cp, "pop", new RCPosition(4, 7), l);
	}

	/**
	 * Class that determines actions on buttons for binary operation.
	 * 
	 * @author tina
	 *
	 */
	private class BinaryOperatorListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (error) {
				return;
			}

			JButton button = (JButton) e.getSource();
			Double input = getCurrentInput();

			if (input == null) {
				display.setText(MESSAGE);
				error = true;
				return;
			}

			if (result == null) {
				result = input;
			}

			if (binActive) {
				result = binOperator.compute(result, input);
			}
			
			if (inverseBox.isSelected() && button.getText().equals("x^n")) {
				binOperator = BinaryOperators.SQRT;
			} else {
				binOperator = binOperators.get(button.getText());
			}
			binActive = true;
			display.setText("");
		}

	}

	/**
	 * Class that determines actions on buttons for unary operation.
	 * 
	 * @author tina
	 *
	 */
	private class UnaryOperatorListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (error) {
				return;
			}

			IUnaryOperator unOperator;
			JButton button = (JButton) e.getSource();
			if (inverseBox.isSelected()) {
				unOperator = inversUnOperators.get(button.getText());
			} else {
				unOperator = unOperators.get(button.getText());
			}

			Double input = getCurrentInput();

			if (input == null) {
				display.setText(MESSAGE);
				error = true;
				return;
			}

			double temp = unOperator.compute(input);
			if (binActive) {
				result = binOperator.compute(result, temp);
				binActive = false;
			} else {
				result = temp;
			}

			display.setText(result.toString());
		}

	}

	/**
	 * Method that extracts number from input.
	 * 
	 * @return number if input is valid, otherwise null
	 */
	private Double getCurrentInput() {
		if (display.getText().equals("")) {
			return null;
		}

		try {
			return Double.parseDouble(display.getText());
		} catch (NumberFormatException e) {
			return null;
		}

	}

	/**
	 * Class that determines actions on buttons with numbers.
	 * 
	 * @author tina
	 *
	 */
	private class NumberListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (error) {
				return;
			}

			JButton button = (JButton) e.getSource();
			String input = display.getText();
			display.setText(input + button.getText());
		}

	}

}
