package hr.fer.zemris.bf.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import hr.fer.zemris.bf.model.BinaryOperatorNode;
import hr.fer.zemris.bf.model.ConstantNode;
import hr.fer.zemris.bf.model.NodeVisitor;
import hr.fer.zemris.bf.model.UnaryOperatorNode;
import hr.fer.zemris.bf.model.VariableNode;

/**
 * Class that implements evaluation of expression for given variable values.
 * 
 * @author tina
 *
 */
public class ExpressionEvaluator implements NodeVisitor {
	/** values of variables */
	private boolean[] values;
	/**
	 * Map that connects variables (key( and their position in given list
	 * (value)
	 */
	private Map<String, Integer> positions;
	/** Stack that helps with expression evaluation. */
	private Stack<Boolean> stack;

	/**
	 * Constructor that creates map of given variables and their positions.
	 * 
	 * @param variables
	 *            list of variables
	 */
	public ExpressionEvaluator(List<String> variables) {
		stack = new Stack<>();
		values = new boolean[variables.size()];
		positions = new HashMap<>();

		for (int i = 0; i < variables.size(); i++) {
			// if list unexpectedly contains same values, keep position of the first occurrence
			positions.merge(variables.get(i), i, (v1, v2) -> v1);
		}
	}

	/**
	 * Method that sets values for variables.
	 * 
	 * @param values
	 *            array of values
	 * @throws IllegalArgumentException
	 *             if given number of values is invalid
	 */
	public void setValues(boolean[] values) {
		if (values.length != this.values.length) {
			throw new IllegalArgumentException("Number of values doesn't match with the number of variables.");
		}

		start();
		this.values = values;
	}

	/**
	 * Clears stack so that new evaluation can be performed.
	 */
	public void start() {
		stack.clear();
	}

	/**
	 * Returns result of evaluation.
	 * 
	 * @return result
	 * @throws IllegalStateException
	 *             if there is not one element on stack
	 */
	public boolean getResult() {
		if (stack.size() != 1) {
			throw new IllegalStateException("Something went wrong.");
		}

		return stack.peek();
	}

	@Override
	public void visit(ConstantNode node) {
		stack.push(node.getValue());
	}

	@Override
	public void visit(VariableNode node) {
		Integer position = positions.get(node.getName());
		if (position == null) {
			throw new IllegalStateException("Map does not contain variable " + node.getName());
		}

		stack.push(values[position]);
	}

	@Override
	public void visit(UnaryOperatorNode node) {
		node.getChild().accept(this);
		boolean value = node.getOperator().apply(stack.pop());
		
		stack.push(value);
	}

	@Override
	public void visit(BinaryOperatorNode node) {
		node.getChildren().forEach(v -> v.accept(this));
		boolean result = stack.pop();

		for (int i = 1; i < node.getChildren().size(); i++) {
			result = node.getOperator().apply(result, stack.pop());
		}

		stack.push(result);
	}

}
