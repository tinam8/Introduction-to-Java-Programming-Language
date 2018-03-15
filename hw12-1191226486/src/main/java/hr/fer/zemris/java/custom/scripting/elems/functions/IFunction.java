package hr.fer.zemris.java.custom.scripting.elems.functions;

import java.util.Stack;

import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Interface that implements method for calculating the function.
 * 
 * @author tina
 *
 */
public interface IFunction {
	/**
	 * Method that calculates function
	 * 
	 * @param stack
	 *            stack that holds values that should be used in calculation.
	 * @param requestContext context of a request; {@link RequestContext}
	 */
	public void calculate(Stack<Object> stack,RequestContext requestContext);
}
