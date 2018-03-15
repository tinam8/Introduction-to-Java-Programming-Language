package hr.fer.zemris.bf.model;

/**
 * Interface that has methods for visiting nodes.
 * 
 * @author tina
 *
 */
public interface NodeVisitor {
	/**
	 * method for visiting node {@link ConstantNode}
	 * 
	 * @param node
	 *            constant node to visit
	 */
	void visit(ConstantNode node);

	/**
	 * method for visiting node {@link VariableNode}
	 * 
	 * @param node
	 *            variable node to visit
	 */
	void visit(VariableNode node);

	/**
	 * method for visiting {@link UnaryOperatorNode}
	 * 
	 * @param node
	 *            unary operator node to visit
	 */
	void visit(UnaryOperatorNode node);

	/**
	 * method for visiting {@link BinaryOperatorNode}
	 * 
	 * @param node
	 *            binary operator node to visit
	 */
	void visit(BinaryOperatorNode node);
}
