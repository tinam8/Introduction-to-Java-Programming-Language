package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class representing Element of type string.
 * 
 * @author tina
 *
 */
public class ElementString extends Element {

	/**
	 * A single read-only <code>String</code> property.
	 */
	private String value;

	/**
	 * Constructor with one argument.
	 * 
	 * @param value
	 *            string that instace is going to hold.
	 */
	public ElementString(String value) {
		this.value = value;
	}

	/**
	 * Returns read-only property <code>value</code>.
	 * 
	 * @return element value; String
	 */
	public String getValue() {
		return value;
	}

	@Override
	public String asText() {
		return value;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\"");
		
		for (int i = 0; i < value.length(); i++) {
			switch (value.charAt(i)) {
			case '\\':
				sb.append("\\\\");
				break;
			case '\"':
				sb.append("\\\"");
				break;
			case '\t':
				sb.append("\\t");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			default:
				sb.append(value.charAt(i));
				break;
			}
		}
		
		sb.append("\"");
		return sb.toString();
	}

	@Override
	public void accept(IElementVisitor visitor) {
		visitor.visitElementString(this);
	}

}
