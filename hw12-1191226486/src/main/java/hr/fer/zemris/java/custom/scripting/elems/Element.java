package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Base class having only a single public function: String asText(); Will be
 * used for the representation of expressions.
 * 
 * @author tina
 *
 */
public abstract class Element {

	/**
	 * String representation of expression held by this instance of class
	 * elements.
	 * 
	 * @return string representation of element that represents expression;string
	 * 	 */
	public String asText() {
		return "";
	}
	
	@Override
	public String toString() {
		return asText();
	}
	
	/**
	 * Method that accepts visitor.
	 * @param visitor instance of {@link IElementVisitor}
	 */
	public abstract void accept(IElementVisitor visitor);
}
