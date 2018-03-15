package hr.fer.zemris.java.hw04.db;

import java.util.List;

/**
 * 
 * @author tina
 *
 */
public class QueryFilter implements IFilter {
	/**
	 * Variable that holds list of conditional expressions.
	 */
	List<ConditionalExpression> conditionalExpressions;

	/**
	 * Constructor that has one argument.
	 * 
	 * @param conditionalExpressions
	 *            list of conditional expressions.
	 */
	public QueryFilter(List<ConditionalExpression> conditionalExpressions) {
		this.conditionalExpressions = conditionalExpressions;
	}

	@Override
	public boolean accepts(StudentRecord record) {
		for (ConditionalExpression condExpression : conditionalExpressions) {
			if (!condExpression.getComparisonOperator().satisfied(condExpression.getFieldGetter().get(record),
					condExpression.getStringLiteral())) {
				return false;
			}
		}
		
		return true;
	}

}
