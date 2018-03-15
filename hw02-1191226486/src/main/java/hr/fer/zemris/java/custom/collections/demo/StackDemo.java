package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * This is command-line application which accepts a single command-line
 * argument: expression which should be evaluated. Expression must be in postfix
 * representation. When starting program from console, you will enclose whole
 * expression into quotation marks, so that your program always gets just one
 * argument
 * 
 * @author tina
 *
 */
public class StackDemo {

	/**
	 * Method which starts program.
	 * 
	 * @param args
	 *            expression which should be evaluated
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Program accepts only one argument.");
			System.exit(1);
		}

		String[] expression = args[0].split(" +");
		ObjectStack stack = new ObjectStack();

		for (String element : expression) {
			if (element.matches("^[+-]?\\d+$")) {
				stack.push(Integer.parseInt(element));
			} else {
				int right = 0;
				int left = 0;

				try {
					right = (int) stack.pop();
					left = (int) stack.pop();
				} catch (EmptyStackException e) {
					System.out.println("Invalid expression!");
					System.exit(1);
				}

				int result = evaluate(left, right, element);
				stack.push(result);
			}
		}
		
		if(stack.size() != 1) {
			System.out.println("Invalid expression!");
			System.exit(1);
		} 
		
		System.out.println(stack.pop());
		
	}

	/**
	 * Private method which calculates basic operations.
	 * 
	 * @param leftNumber
	 *            first operand
	 * @param rightNumber
	 *            second operand
	 * @param operation
	 *            type of operation
	 * @return calculated value
	 */
	private static int evaluate(int leftNumber, int rightNumber, String operation) {
		if (rightNumber == 0 && (operation.equals("/") || operation.equals("%"))) {
			System.out.println("Can not divide by zero!");
			System.exit(1);
		}
		
		int result = 0;
		switch(operation) {
			case "+": 
				result = leftNumber + rightNumber;
				break;
			case "-":
				result = leftNumber - rightNumber;
				break;
			case "/":
				result = leftNumber / rightNumber;
				break;
			case "*":
				result = leftNumber * rightNumber;
				break;
			default:
				System.out.println("Invalid expression!");
				System.exit(1);
		}
		return result;
	}

}
