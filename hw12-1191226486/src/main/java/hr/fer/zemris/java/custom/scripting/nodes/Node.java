package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * Class used for representation of structured documents. <br>
 * Base class for all graph nodes.
 * 
 * @author tina
 *
 */
public abstract class Node {

	/**
	 * Collection of children.
	 */
	private ArrayIndexedCollection children;

	/**
	 * Adds given child to an internally managed collection of children.
	 * 
	 * @param child
	 *            node to add as a child;
	 */
	public void addChildNode(Node child) {
		if (children == null) {
			children = new ArrayIndexedCollection();
		}

		children.add(child);
	}

	/**
	 * Method that returns a number of (direct) children.
	 * 
	 * @return number of (direct) children; int
	 */
	public int numberOfChildren() {
		return children.size();
	}

	/**
	 * Returns selected child that is at cetrain position.
	 * @param index position of child to get.
	 * @return selected child; Node
	 * @throws IllegalArgumentException if the index is invalid.
	 */
	public Node getChild(int index) {
		if (index < 0 || index >= children.size()) {
			throw new IllegalArgumentException("Index is invalid, should be >0 and <" + children.size() + 
					"but it is " + index );
		}
		
		return (Node)children.get(index);
	}
	
	/**
	 * Method that accepts {@link INodeVisitor}
	 * @param visitor visitor to accept
	 */
	public abstract void accept(INodeVisitor visitor);
}
