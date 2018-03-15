package hr.fer.zemris.java.hw04.db;

/**
 * Class containing different ways of comparing two strings.
 * 
 * @author tina
 *
 */
public class ComparisonOperators {
	/**
	 * Checks if first value is strictly less than the second one.
	 */
	public static final IComparisonOperator LESS = (value1, value2) -> {
		return value1.compareTo(value2) < 0;
	};
	/**
	 * Checks if first value is strictly less or equal to the second one.
	 */
	public static final IComparisonOperator LESS_OR_EQUALS = (value1, value2) -> {
		return value1.compareTo(value2) <= 0;
	};
	/**
	 * Checks if first value is strictly greater than the second one.
	 */
	public static final IComparisonOperator GREATER = (value1, value2) -> {
		return value1.compareTo(value2) > 0;
	};
	/**
	 * Checks if first value is strictly greater or equal to the second one.
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS = (value1, value2) -> {
		return value1.compareTo(value2) >= 0;
	};
	/**
	 * Checks if first value is equal to the second one.
	 */
	public static final IComparisonOperator EQUALS = (value1, value2) -> {
		return value1.equals(value2);
	};
	/**
	 * Checks if first value is not equal to the second one.
	 */
	public static final IComparisonOperator NOT_EQUALS = (value1, value2) -> {
		return !value1.equals(value2);
	};
	/**
	 * Checks if first value contains paterrn of the second one.
	 */
	public static final IComparisonOperator LIKE = new IComparisonOperator() {
		// TODO razmisli triba li razlikovat je li prvo jmbag?
		@Override
		public boolean satisfied(String value1, String value2) {
			value2 = value2.replace("*", ".*");

			return value1.matches(value2);
		}
	};
}
