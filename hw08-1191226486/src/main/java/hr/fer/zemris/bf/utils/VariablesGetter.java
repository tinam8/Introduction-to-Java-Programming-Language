package hr.fer.zemris.bf.utils;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.bf.model.BinaryOperatorNode;
import hr.fer.zemris.bf.model.ConstantNode;
import hr.fer.zemris.bf.model.NodeVisitor;
import hr.fer.zemris.bf.model.UnaryOperatorNode;
import hr.fer.zemris.bf.model.VariableNode;

/**
 * Implements {@link NodeVisitor} that returns all variables that amperes in the
 * expression as a list. Elements of a list are unique, sorted
 * lexicographically.
 * 
 * @author tina
 */
public class VariablesGetter implements NodeVisitor {
	/** list of variables that appear in expression */
	List<String> variables = new ArrayList<>();

	@Override
	public void visit(ConstantNode node) {
		return;
	}

	@Override
	public void visit(VariableNode node) {
		if (node == null) {
			throw new IllegalArgumentException("Node can not be null.");
		}

		if (!variables.contains(node.getName())) {
			variables.add(node.getName());
		}
	}

	@Override
	public void visit(UnaryOperatorNode node) {
		if (node == null) {
			throw new IllegalArgumentException("Node can not be null.");
		}

		node.getChild().accept(this);
	}

	@Override
	public void visit(BinaryOperatorNode node) {
		if (node == null) {
			throw new IllegalArgumentException("Node can not be null.");
		}

		node.getChildren().forEach(n -> n.accept(this));
	}

	/**
	 * Method that returns sorted list of variables in boolean expression.
	 * @return list of variables
	 */
	public List<String> getVariables() {
		variables.sort((s1, s2) -> s1.compareTo(s2));
		return variables;
	}

}
