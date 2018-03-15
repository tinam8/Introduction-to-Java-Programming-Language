package hr.fer.zemris.bf.model;

import java.util.function.UnaryOperator;

/**
 * Implements node that represents unary operations such as complement.
 * 
 * @author tina
 *
 */
public class UnaryOperatorNode implements Node {
	/** Operator name. */
	private String name;
	/** reference to node */
	private Node child;
	/** Operator implementation */
	UnaryOperator<Boolean> operator;
	
	/**
	 * Constructor with 3 parameters.
	 * 
	 * @param name operator name
	 * @param child reference to node that represents operand on which operations should be performed.
	 * @param operator strategy that implements operator
	 */
	public UnaryOperatorNode(String name, Node child, UnaryOperator<Boolean> operator) {
		this.name = name;
		this.child = child;
		this.operator = operator;
	}

	/**
	 * Getter for operator name.
	 * @return operator name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Getter for child reference.
	 * @return reference to node that represents operand
	 */
	public Node getChild() {
		return child;
	}

	/** 
	 * Getter for operator implementation.
	 * @return operator implementation
	 */
	public UnaryOperator<Boolean> getOperator() {
		return operator;
	}

	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this); 
	}
}
