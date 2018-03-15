package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Class representing Element of type variable.
 * 
 * @author tina
 *
 */
public class ElementVariable extends Element {

	/**
	 * Read-only <code>String</code> property - name of variable.
	 */
	private String name;

	/**
	 * Constructor with one argument.
	 * 
	 * @param name
	 *            name of variable
	 */
	public ElementVariable(String name) {
		this.name = name;
	}

	/**
	 * Gets the name of variable.
	 * 
	 * @return the name of variable.
	 */
	public String getName() {
		return name;
	}

	@Override
	public String asText() {
		return name;
	}

	@Override
	public void accept(IElementVisitor visitor) {
		visitor.visitElementVariable(this);
	}
	
	
}
