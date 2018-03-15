package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * Class that represents a node for a command which generates<br>
 * some textual output dynamically.
 * 
 * @author tina
 *
 */
public class EchoNode extends Node {

	/**
	 * Elements of EchoNode.
	 */
	private Element[] elements;

	/**
	 * Constructor.
	 * 
	 * @param elements
	 *            arguments that EchoNode should contain.
	 */
	public EchoNode(Element[] elements) {
		this.elements = elements;
	}

	/**
	 * Method that returns elements.
	 * 
	 * @return elements; <code>Elements[]</code>	
	 */
	public Element[] getElements() {
		return elements;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{$= "); 
		
		for (Element element : elements) {
			sb.append(element.toString() + " ");
		}
		sb.append("$}");
		
		return sb.toString();
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitEchoNode(this);
	}

}
