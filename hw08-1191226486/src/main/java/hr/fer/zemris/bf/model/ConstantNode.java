package hr.fer.zemris.bf.model;

/**
 * Class that represents constants in boolean expressions. (true or false).
 * 
 * @author tina
 *
 */
public class ConstantNode implements Node{
	/** Stored value. */
	private boolean value;
	
	/**
	 * Constructor that accepts one value and remembers it.
	 * @param value value to store
	 */
	public ConstantNode(boolean value) {
		this.value =value;
	}
	/**
	 * Getter for value.
	 * @return stored value
	 */
	public boolean getValue() {
		return value;
	}
	
	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this); 
	}
}
