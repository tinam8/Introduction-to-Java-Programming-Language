package hr.fer.zemris.bf.model;

/**
 * Class that represents variables in boolean expressions.
 * 
 * @author tina
 *
 */
public class VariableNode implements Node {
	/** Variable name */
	private String name;

	/**
	 * Constructor that accepts one String value and stores it.
	 * 
	 * @param name
	 *            name to store
	 */
	public VariableNode(String name) {
		this.name = name;
	}

	/**
	 * Getter for variable name.
	 * 
	 * @return variable name
	 */
	public String getName() {
		return name;
	}

	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this); 
	}
}
