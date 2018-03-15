package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Interface that has methods for visiting all types of nodes.
 * 
 * @author tina
 *
 */
public interface INodeVisitor {
	/**
	 * Method that visits {@link TextNode}
	 * 
	 * @param node
	 *            node to visit
	 */
	public void visitTextNode(TextNode node);

	/**
	 * Method that visits {@link ForLoopNode}
	 * 
	 * @param node
	 *            node to visits
	 */
	public void visitForLoopNode(ForLoopNode node);

	/**
	 * Method that visits {@link EchoNode}
	 * 
	 * @param node
	 *            node to visit
	 */
	public void visitEchoNode(EchoNode node);

	/**
	 * Method that visits {@link DocumentNode}
	 * 
	 * @param node
	 *            node to visit
	 */
	public void visitDocumentNode(DocumentNode node);
}
