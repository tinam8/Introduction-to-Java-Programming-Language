package hr.fer.zemris.bf.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;

import hr.fer.zemris.bf.model.Node;

/**
 * Has static methods for boolean expressions.
 * 
 * @author tina
 *
 */
public class Util {
	/**
	 * Generates all possible combinations of values for variables in the list.
	 * 
	 * @param variables
	 *            list of variables
	 * @param consumer
	 *            implementation of operator
	 */
	public static void forEach(List<String> variables, Consumer<boolean[]> consumer) {
		List<boolean[]> valuesList = getValues(variables.size());

		valuesList.forEach(values -> consumer.accept(values));
	}

	/**
	 * Method that returns a list off all values combinations for given number
	 * of values.
	 * 
	 * @param n
	 *            number of variables
	 * @return list of values combinations
	 */
	public static List<boolean[]> getValues(int n) {
		List<boolean[]> valuesList = new ArrayList<>();

		for (int i = 0; i < Math.pow(2, n); i++) {
			String bin = Integer.toBinaryString(i);

			while (bin.length() < n) {
				bin = "0" + bin;
			}

			char[] charValues = bin.toCharArray();
			boolean[] values = new boolean[n];
			for (int j = 0; j < n; j++) {
				values[j] = charValues[j] == '0' ? false : true;
			}

			valuesList.add(values);
		}

		return valuesList;
	}

	/**
	 * returns all variable value combinations that matches given value.
	 * 
	 * @param variables
	 *            variables
	 * @param expression
	 *            boolean expression
	 * @param expressionValue
	 *            wanted value as a result of the expression
	 * @return list of arrays of variable values that satisfied the condition
	 */
	public static Set<boolean[]> filterAssignments(List<String> variables, Node expression, boolean expressionValue) {
		ExpressionEvaluator eval = new ExpressionEvaluator(variables);
		Set<boolean[]> valuesMatch = new TreeSet<>(new BooleanComparator());

		List<boolean[]> valuesList = getValues(variables.size());

		valuesList.forEach(values -> {
			eval.setValues(values);
			expression.accept(eval);
			if (eval.getResult() == expressionValue) {
				valuesMatch.add(values);
			}
		});

		return valuesMatch;
	}

	/**
	 * Implements comparison of boolean arrays. <br>
	 * If they have different sizes, shorter is smaller. <br>
	 * if they have the same size, than the first position of two arrays that is
	 * not the same determines which one is smaller. Smaller one at that
	 * position has false, while other has true. <br>
	 * Example: <br>
	 * [0, 0, 1] <br>
	 * [0, 1, 1] <br>
	 * [1, 0, 1] <br>
	 * [1, 1, 0] <br>
	 * [1, 1, 1] <br>
	 * 
	 * @author tina
	 *
	 */
	static class BooleanComparator implements Comparator<boolean[]> {

		@Override
		public int compare(boolean[] o1, boolean[] o2) {
			if (o1.length != o2.length) {
				return o1.length < o2.length ? -1 : 1;
			}

			for (int i = 0; i < o1.length; i++) {
				if (o1[i] && !o2[i]) {
					return 1;
				}
				if (!o1[i] && o2[i]) {
					return -1;
				}
			}

			return 0;
		}

	}

	/**
	 * 
	 * Method calculates and returns row number of table in which given array of
	 * boolean values would be placed. <br>
	 * Example: for array [false, false, true, true] returns 3.
	 * 
	 * @param values
	 *            array of boolean values
	 * @return table row
	 */
	public static int booleanArrayToInt(boolean[] values) {
		int n = values.length;
		int row = 0;

		for (int i = 0; i < n; i++) {
			if (values[n - i - 1]) {
				row += Math.pow(2, i);
			}
		}

		return row;
	}

	/**
	 * 
	 * @param variables list of variables
	 * @param expression parsed expression
	 * @return set of minterm indexes
	 */
	public static Set<Integer> toSumOfMinterms(List<String> variables, Node expression) {
		return getSumOrProduct(variables, expression, true);
	}

	/**
	 * 
	 * @param variables list of variables
	 * @param expression parsed expression
	 * @return set of maxterm indexes
	 */
	public static Set<Integer> toProductOfMaxterms(List<String> variables, Node expression) {
		return getSumOrProduct(variables, expression, false);
	}

	/**
	 * Method that returns indexes that satisfies given expression value
	 * 
	 * @param variables
	 *            list of variables
	 * @param expression
	 *            parsed expression
	 * @param expressionValue
	 *            expression value
	 * @return indexes of minterms if given expression value is true, if given
	 *         value is false it return indexes of maxterms
	 */
	private static Set<Integer> getSumOrProduct(List<String> variables, Node expression, boolean expressionValue) {
		Set<boolean[]> valuesList = filterAssignments(variables, expression, expressionValue);
		Set<Integer> terms = new TreeSet<>();

		valuesList.forEach(values -> terms.add(booleanArrayToInt(values)));

		return terms;
	}

	/**
	 * Method that converts integer to binary representation whose length is n
	 * bytes.
	 * 
	 * @param x
	 *            integer to convert
	 * @param n
	 *            Number of bytes
	 * @return converted integer; byte []
	 * @throws IllegalArgumentException
	 *             if length of new representation is not positive number
	 */
	public static byte[] indexToByteArray(int x, int n) {
		if (n < 1) {
			throw new IllegalArgumentException("Array length has to be greater than 1.");
		}

		System.out.println(Integer.toBinaryString(x));

		byte[] array = new byte[n];

		for (int i = 0; i < n; i++) {
			array[n - i - 1] = (byte) (((x & 1) == 0) ? 0 : 1);
			x = x >> 1;
		}

		return array;
	}

}
