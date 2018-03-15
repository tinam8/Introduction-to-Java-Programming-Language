package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Class that represents node for a piece of textual data. <br>
 * 
 * @author tina
 *
 */
public class TextNode extends Node {

	/**
	 * Read-only <code>String</code> property
	 */
	private String text;

	/**
	 * Constructor with one argument.
	 * 
	 * @param text
	 *            text to be placed as value that node contains.
	 */
	public TextNode(String text) {
		this.text = text;
	}

	/**
	 * Gets textual data that node contains.
	 * 
	 * @return textual data
	 */
	public String getText() {
		return text;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < text.length(); i++) {
			switch (text.charAt(i)) {
			case '\\':
				sb.append("\\\\");
				break;
			case '{':
				sb.append("\\{");
				break;
			default:
				sb.append(text.charAt(i));
				break;
			}
		}
		
		return sb.toString();
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitTextNode(this);
	}
	
	
}
