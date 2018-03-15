package demo;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import hr.fer.zemris.bf.lexer.LexerException;
import hr.fer.zemris.bf.model.Node;
import hr.fer.zemris.bf.parser.Parser;
import hr.fer.zemris.bf.parser.ParserException;
import hr.fer.zemris.bf.qmc.Mask;
import hr.fer.zemris.bf.qmc.Minimizer;
import hr.fer.zemris.bf.utils.Util;
import hr.fer.zemris.bf.utils.VariablesGetter;

/**
 * Program that reads function inputs from user, starts minimization and outputs
 * the result. <br>
 * Example of input functions: <br>
 * 1. f(A,B,C,D) = <br>
 * NOT A AND NOT B AND (NOT C OR D) OR A AND C | NOT A AND B AND NOT D <br>
 * 2. f(A,B,C,D) = NOT A AND NOT B AND (NOT C OR D) OR A AND C | [4,6] <br>
 * part |.. is optional and it represents don't cares.
 * 
 * @author tina
 *
 */
public class QMC {
	/**
	 * Method that starts program.
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.print("> ");

			if (sc.hasNext()) {

				String input = sc.nextLine();
				input.trim();

				if (input.equals("quit")) {
					sc.close();
					System.exit(1);
				}

				String[] parts = input.split("=");
				if (parts.length != 2) {
					System.out.println("Error: Function is incorect, no '=' sign.");
					continue;
				}

				String rex = "^[a-zA-Z]+(\\d*[a-zA-Z]*)*\\s*\\(\\s*[a-zA-Z]+(\\d*[a-zA-Z]*)*(\\s*\\,\\s*[a-zA-Z]+(\\d*[a-zA-Z]*)*)*\\s*\\)$";
				if (!parts[0].trim().matches(rex)) {
					System.out.println("Error: Invalid definition of function.");
					continue;
				}

				List<String> variables = null;
				try {
					variables = getVariables(parts[0]);
				} catch (Exception e) {
					System.out.println("Error: Variables can not have the same name.");
					continue;
				}

				List<String> parsedVariables = null;
				Set<Integer> mintermSet;

				String[] function = parts[1].split("\\|");
				if (function.length > 2) {
					System.out.println("Error: Invalid don't care specification.");
					continue;
				}

				try {
					Node expression = new Parser(function[0]).getExpression();
					VariablesGetter getter = new VariablesGetter();
					expression.accept(getter);
					parsedVariables = getter.getVariables();

					mintermSet = Util.toSumOfMinterms(parsedVariables, expression);
				} catch (ParserException ex) {
					System.out.println("Error: Exception: " + ex.getClass() + " - " + ex.getMessage());
					continue;
				}

				if (parsedVariables.size() > variables.size()) {
					System.out.println("Error: Function definitions has more variables than declarationa.");
					continue;
				}

				if (!hasValidVariables(variables, parsedVariables)) {
					System.out
							.println("Error: Function definitions has variables that dose not appear in declarationa.");
					continue;
				}

				Set<Integer> dontCareSet = new LinkedHashSet<>();

				if (function.length == 2) {
					String optional = function[1].trim();

					if (optional.startsWith("[")) {
						try {
							dontCareSet = getDontCareIndex(optional, parsedVariables.size());
						} catch (Exception e) {
							System.out.println("Error: " + e.getMessage());
							continue;
						}
					} else {
						List<String> dontCareVariables;

						try {
							Node expression = new Parser(function[1]).getExpression();
							VariablesGetter getter = new VariablesGetter();
							expression.accept(getter);
							dontCareVariables = getter.getVariables();

							dontCareSet = Util.toSumOfMinterms(variables, expression);
						} catch (ParserException ex) {
							System.out.println("Exception: " + ex.getClass() + " - " + ex.getMessage());
							continue;
						}

						if (!hasValidVariables(variables, dontCareVariables)) {
							System.out.println(
									"Error: Don't care definition has variables that dose not appear in declarationa.");
							continue;
						}

					}

				}

				Minimizer minimizer = null;

				try {
					minimizer = new Minimizer(mintermSet, dontCareSet, variables);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					continue;
				}

				List<Set<Mask>> forms = minimizer.getMinimalForms();

				if (forms.size() > 0 && forms.get(0).size() == 0) {
					System.out.println("Contradiction");
					continue;
				}
				
				if (mintermSet.size() == Math.pow(2, variables.size())) {
					System.out.println("Tauthology");
					continue;
				}

				printMinimalForms(minimizer);

			}

		}
	}

	/**
	 * Method that prints minimal forms.
	 * 
	 * @param minimizer
	 *            minimizer
	 */
	private static void printMinimalForms(Minimizer minimizer) {
		List<String> formExpressions = minimizer.getMinimalFormsAsString();

		for (int i = 0; i < formExpressions.size(); i++) {
			System.out.println(String.format("%d. %s", i + 1, formExpressions.get(i)));
		}
	}

	/**
	 * Method that gets variables from string whose pattern is f(A,B)
	 * 
	 * @param definition
	 *            of function
	 * @return variables
	 */
	private static List<String> getVariables(String definition) {
		definition = definition.replaceAll("\\s+", "");
		String variablesList = definition.substring(definition.indexOf('(') + 1, definition.indexOf(')'));

		List<String> variables = Arrays.asList(variablesList.split(","));
		Set<String> variablesSet = new HashSet<>(variables);

		if (variables.size() != variablesSet.size()) {
			throw new LexerException();
		}

		return variables;
	}

	/**
	 * If optional part is in form [1,3] method return indexes.
	 * 
	 * @param optional
	 *            optional par of input
	 * @param numberOfVariables
	 *            number of variables
	 * @return don't care indexes
	 */
	private static Set<Integer> getDontCareIndex(String optional, int numberOfVariables) {
		optional.replace("\\s+", "");
		String rex = "^\\[\\d+(\\,\\d+)*\\]$";

		if (!optional.matches(rex)) {
			throw new LexerException("Invalid input of indexes for don't cares.");
		}

		int max = (int) (Math.pow(2, numberOfVariables) - 1);
		String indexesString = optional.substring(optional.indexOf('[') + 1, optional.indexOf(']'));
		String[] indexes = indexesString.split(",");

		Set<Integer> indexSet = new TreeSet<>();
		for (int i = 0; i < indexes.length; i++) {
			int index = Integer.parseInt(indexes[i]);

			if (index > max) {
				throw new LexerException(
						"Index is greater than limit set by number of variables in function definition.");
			}

			indexSet.add(index);
		}

		return indexSet;
	}

	/**
	 * Method that checks if first argument contains second arguments elements.
	 * It checks if only declared variables appear in boolean expressions.
	 * 
	 * @param declarationVariables
	 *            variables appearing in declaration of function
	 * @param definitionVariable
	 *            boolean expression
	 * @return true if variables are valid, false otherwise
	 */
	private static boolean hasValidVariables(List<String> declarationVariables, List<String> definitionVariable) {
		for (String variable : definitionVariable) {
			if (!declarationVariables.contains(variable)) {
				return false;
			}
		}

		return true;
	}

}
