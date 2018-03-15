package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * Class that represents a node for a single for-loop construct. <br>
 * 
 * @author tina
 *
 */
public class ForLoopNode extends Node {

	// Class ForLoopNode defines several additional read-only properties:
	// • property variable (of type ElementVariable)
	// • property startExpression (of type Element)
	// • property endExpression (of type Element)
	// • property stepExpression (of type Element, which can be null)

	/**
	 * Read-only property, representing the vriable on which the for-loop is
	 * iterated.
	 */
	private ElementVariable variable;
	/**
	 * Read-only property, representing the start expression of for loop.
	 */
	private Element startExpression;

	/**
	 * Read-only property, representing the end expression of for loop. <br>
	 * When endExpression is reached, for-loop should stop iterating.
	 */
	private Element endExpression;

	/**
	 * Read-only property representitng the step of iterations. <br>
	 * Can be <code>null</code>s
	 */
	private Element stepExpression;

	/**
	 * Constructor
	 * 
	 * @param variable
	 *            instance that hols variable name
	 * @param startExpression
	 *            instance that holds integer value, start of loop
	 * @param endExpression
	 *            instance that holds integer value, end of loop
	 * @param stepExpression
	 *            instance that holds integer value, step of loop
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{$ FOR " + variable + " " + startExpression + " " + endExpression);

		if (stepExpression != null) {
			sb.append(" " + stepExpression);
		}
		sb.append(" $}");

		for (int i = 0, n = numberOfChildren(); i < n; i++) {
			sb.append(getChild(i).toString());
		}
		
		sb.append("{$ END $}");

		return sb.toString();
	}

}
