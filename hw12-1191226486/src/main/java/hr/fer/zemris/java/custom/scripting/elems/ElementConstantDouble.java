package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class representing Element of type Double.
 * 
 * @author tina
 *
 */
public class ElementConstantDouble extends Element {

	/**
	 * Read-only double property - value of expression; double
	 */
	private double value;

	/**
	 * Constructor
	 * 
	 * @param value
	 *            value of expression.
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}

	/**
	 * Gets value of read-only property <code>value</code>
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
		visitor.visitElementConstantDouble(this);
	}

}
