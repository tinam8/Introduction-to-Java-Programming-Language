package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Interface that implements method for performing actions on each type of
 * elements.
 * 
 * @author tina
 *
 */
public interface IElementVisitor {
	/**
	 * Method that visits instances of {@link ElementConstantDouble}
	 * @param element instance of {@link ElementConstantDouble}
	 */
	public void visitElementConstantDouble(ElementConstantDouble element);
	/**
	 * Method that visits instances of {@link ElementConstantInteger}
	 * @param element instance of {@link ElementConstantInteger}
	 */
	public void visitElementConstantInteger(ElementConstantInteger element);
	/**
	 * Method that visits instances of {@link ElementFunction}
	 * @param element instance of {@link ElementFunction}
	 */
	public void visitElementFuntion(ElementFunction element);
	/**
	 * Method that visits instances of {@link ElementOperator}
	 * @param element instance of {@link ElementOperator}
	 */
	public void visitElementOperator(ElementOperator element);
	/**
	 * Method that visits instances of {@link ElementString}
	 * @param element instance of {@link ElementString}
	 */
	public void visitElementString(ElementString element);
	/**
	 * Method that visits instances of {@link ElementVariable}
	 * @param element instance of {@link ElementVariable}
	 */
	public void visitElementVariable(ElementVariable element);

}
