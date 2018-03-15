package hr.fer.zemris.java.hw04.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw04.db.lexer.Lexer;
import hr.fer.zemris.java.hw04.db.lexer.Token;
import hr.fer.zemris.java.hw04.db.lexer.TokenType;

/**
 * Represents a parser of query statement and it gets query string through
 * constructor.
 * 
 * @author tina
 *
 */
public class QueryParser {
	/**
	 * List of conditional expressions from query
	 */
	List<ConditionalExpression> conditionalExpressions;

	/**
	 * Constructor thah accepts query.
	 * 
	 * @param query
	 *            query
	 * @throws QueryParserException
	 *             if query is invalid.
	 */
	public QueryParser(String query) {
		Lexer lexer = new Lexer(query);
		
		conditionalExpressions = new ArrayList<ConditionalExpression>();
		
		try {
			parse(lexer);
		} catch (Exception e) {
			throw new QueryParserException(e.getMessage());
		}
	}

	/**
	 * Private method that goes trough tokens using lexer
	 * 
	 * @param lexer
	 *            instance of Lexer
	 */
	private void parse(Lexer lexer) {
		Token token = lexer.nextToken();

		while (token.getType() != TokenType.EOF) {
			IFieldValueGetter fieldGetter = null;
			String literal;
			IComparisonOperator comparisonOperator = null;

			if (token.getType() != TokenType.FIELD_NAME) {
				throw new QueryParserException("Expected field name was given " + token.getValue());
			}

			switch (token.getValue().toString()) {
			case "jmbag":
				fieldGetter = FieldValueGetters.JMBAG;
				break;
			case "firstName":
				fieldGetter = FieldValueGetters.FIRST_NAME;
				break;

			case "lastName":
				fieldGetter = FieldValueGetters.LAST_NAME;
				break;
			default:
				throw new QueryParserException("Expected field name was given " + token.getValue());
			}

			token = lexer.nextToken();
			if (token.getType() != TokenType.OPERATOR) {
				throw new QueryParserException("Expected operator was given " + token.getValue());
			}

			switch (token.getValue().toString()) {
			case "=":
				comparisonOperator = ComparisonOperators.EQUALS;
				break;
			case "<":
				comparisonOperator = ComparisonOperators.LESS;
				break;
			case ">":
				comparisonOperator = ComparisonOperators.GREATER;
				break;
			case "<=":
				comparisonOperator = ComparisonOperators.LESS_OR_EQUALS;
				break;
			case ">=":
				comparisonOperator = ComparisonOperators.GREATER_OR_EQUALS;
				break;
			case "!=":
				comparisonOperator = ComparisonOperators.NOT_EQUALS;
				break;
			case "LIKE":
				comparisonOperator = ComparisonOperators.LIKE;
				break;
			default:
				throw new QueryParserException("Expected field name was given " + token.getValue());
			}

			token = lexer.nextToken();
			if (token.getType() != TokenType.VALUE) {
				throw new QueryParserException("Expected sting value was given " + token.getValue());
			}

			literal = token.getValue().toString();
			token = lexer.nextToken();

			ConditionalExpression conditionalExpression = new ConditionalExpression(fieldGetter, literal,
					comparisonOperator);
			conditionalExpressions.add(conditionalExpression);
		}
	}

	/**
	 * Method checks if query was of of the form jmbag="xxx" (i.e. it has only
	 * one comparison, on attribute jmbag, and operator is equals). We will can
	 * queries of this form as direct queries.
	 * 
	 * @return true if query was of of the form jmbag="xxx", false otherwise
	 */
	public boolean isDirectQuery() {
		if (conditionalExpressions.size() != 1) {
			return false;
		}

		ConditionalExpression conditionalExpression = conditionalExpressions.get(0);
		if (conditionalExpression.getFieldGetter().getClass() != FieldValueGetters.JMBAG.getClass()) {
			return false;
		}

		if (conditionalExpression.getComparisonOperator().getClass() != ComparisonOperators.EQUALS.getClass()) {
			return false;
		}

		return true;
	}

	/**
	 * Method must return the string which was given in equality comparison in
	 * direct query.
	 * 
	 * @return tring which was given in equality comparison
	 * @throws IllegalStateException
	 *             if the query was not direct one
	 */
	public String getQueriedJMBAG() {
		if (!isDirectQuery()) {
			throw new IllegalStateException("Query is not direct.");
		}

		return conditionalExpressions.get(0).getStringLiteral();
	}

	/**
	 * For all queries, this method returns a list of conditional expressions
	 * from query.
	 * 
	 * @return list of conditional expressions
	 */
	public List<ConditionalExpression> getQuery() {
		return conditionalExpressions;
	}

}
