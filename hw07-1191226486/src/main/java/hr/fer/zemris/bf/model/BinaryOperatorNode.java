package hr.fer.zemris.bf.model;

import java.util.List;
import java.util.function.BinaryOperator;

/**
 * Implements node that represents binary operations such as "and", "or".
 * 
 * @author tina
 *
 */
public class BinaryOperatorNode implements Node {
	/** Operator name */
	String name;
	/** List of references to the operands. */
	List<Node> children;
	/** Operation to perform on operands. */
	BinaryOperator<Boolean> operator;

	/**
	 * Constructor with three parameters.
	 * 
	 * @param name
	 *            operator name
	 * @param children
	 *            list of references to the operands
	 * @param operator
	 *            strategy that implements operator
	 */
	public BinaryOperatorNode(String name, List<Node> children, BinaryOperator<Boolean> operator) {
		this.name = name;
		this.children = children;
		this.operator = operator;
	}

	/**
	 * Getter for operator name.
	 * 
	 * @return operator name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter for operands.
	 * 
	 * @return list of operands
	 */
	public List<Node> getChildren() {
		return children;
	}

	/**
	 * Getter for operator implementation.
	 * 
	 * @return operator
	 */
	public BinaryOperator<Boolean> getOperator() {
		return operator;
	}

	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this); 
	}

}
