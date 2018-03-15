package hr.fer.zemris.java.hw04.db;

/**
 * Compares two strings. Way of comparison can be different due to the satisfied
 * method implementation.
 * 
 * @author tina
 *
 */
public interface IComparisonOperator {

	/**
	 * Compares two strings.
	 * @param value1 first string
	 * @param value2 second string
	 * @return true is strings are related due to the comparison,
	 *         false otherwise
	 */
	public boolean satisfied(String value1, String value2);
}
