package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Class that represents a node for an entire document. <br>
 * 
 * @author tina
 *
 */
public class DocumentNode extends Node {
		
	
	@Override
	public String toString() {
		StringBuilder  sb = new StringBuilder();
		for (int i = 0, n = numberOfChildren(); i < n; i++) {
			sb.append(getChild(i).toString());
		}
		
		return sb.toString();
	}
	
}
