package hr.fer.zemris.java.hw04.db;

/**
 * CLass that implements contitional expressions.
 * 
 * @author tina
 *
 */
public class ConditionalExpression {
	/**
	 * Varibale that implements interface for getting field value.
	 */
	private IFieldValueGetter fieldGetter;
	/**
	 * String literal.
	 */
	private String stringLiteral;
	/**
	 * Variable that implements interface for coaping two string literals.
	 */
	private IComparisonOperator comparisonOperator;

	/**
	 * Costructor with three arguments.
	 * 
	 * @param fieldGetter
	 *            instance of {@code IFieldValueGetter}
	 * @param stringLiteral
	 *            string literal
	 * @param comparisonOperator
	 *            instance of {@code IComparisonOperator}
	 */
	public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral,
			IComparisonOperator comparisonOperator) {
		super();
		this.fieldGetter = fieldGetter;
		this.stringLiteral = stringLiteral;
		this.comparisonOperator = comparisonOperator;
	}

	/**
	 * Gets field value getter.
	 * 
	 * @return instance of {@code IFieldValueGetter }
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	/**
	 * Gets String literal
	 * 
	 * @return ;iteral
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

	/**
	 * Gets comparison operator.
	 * 
	 * @return instance of {@code IComparisonOperator}
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}

}
