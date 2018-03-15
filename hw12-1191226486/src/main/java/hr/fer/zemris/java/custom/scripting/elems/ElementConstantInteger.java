package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class representing Element of type Integer.
 * 
 * @author tina
 *
 */
public class ElementConstantInteger extends Element {

	/**
	 * Read-only double property - value of expression; int
	 */
	private int value;

	/**
	 * Constructor
	 * 
	 * @param value
	 *            value of expression.
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}

	/**
	 * Gets value of read-only propertu <code>value</code>
	 * 
	 * @return value of expression.
	 */
	public double getValue() {
		return value;
	}

	@Override
	public String asText() {
		return String.valueOf(value);
	}

	@Override
	public void accept(IElementVisitor visitor) {
		visitor.visitElementConstantInteger(this);
	}
}
