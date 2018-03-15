package hr.fer.zemris.bf.model;

/**
 * Interface that models the parsing tree node.
 * 
 * @author tina
 *
 */
public interface Node {
	/**
	 * Method that accepts visitor.
	 * 
	 * @param visitor
	 *            instance of {@link NodeVisitor}
	 */
	void accept(NodeVisitor visitor);
}
