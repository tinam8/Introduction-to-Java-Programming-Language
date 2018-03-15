package hr.fer.zemris.java.custom.scripting.elems;

/**
 * *Class representing Element of type function.
 * 
 * @author tina
 *
 */
public class ElementFunction extends Element {

	/**
	 * A single read-only <code>String</code> property - name of function.
	 */
	private String name;

	/**
	 * Constructor.
	 * 
	 * @param name
	 *           name of funtion
	 */
	public ElementFunction(String name) {
		this.name = name;
	}

	/**
	 * Returns read-only property <code>name</code> of function.
	 * 
	 * @return name of function; <code>String</code>
	 */
	public String getName() {
		return name;
	}

	@Override
	public String asText() {
		return "@" + name;
	}

	@Override
	public void accept(IElementVisitor visitor) {
		visitor.visitElementFuntion(this);
	}
	
	
}
