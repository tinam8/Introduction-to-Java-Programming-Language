package hr.fer.zemris.java.custom.scripting.elems;

/**
 * /**
 * *Class representing Element of type operator.
 * 
 * @author tina
 *
 */
public class ElementOperator extends Element {

	/**
	 * A single read-only <code>String</code> property - name of function.
	 */
	private String symbol;

	/**
	 * Constructor.
	 * 
	 * @param symbol
	 *           name of funtion
	 */
	public ElementOperator(String symbol) {
		this.symbol  = symbol;
	}

	/**
	 * Returns read-only property <code>symbol</code> that represents operator.
	 * 
	 * @return symbol of operator; <code>String</code>
	 */
	public String getSymbol() {
		return symbol;
	}

	@Override
	public String asText() {
		return symbol;
	}

	@Override
	public void accept(IElementVisitor visitor) {
		visitor.visitElementOperator(this);
	}
	
}
