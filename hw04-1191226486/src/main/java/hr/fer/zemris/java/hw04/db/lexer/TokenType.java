package hr.fer.zemris.java.hw04.db.lexer;

/**
 * Enumeration of token types.
 * 
 * @author tina
 *
 */
public enum TokenType {
	/**
	 * Represents that there are no more tokens.
	 */
	EOF,
	/**
	 * Field name.
	 */
	FIELD_NAME,
	/**
	 * 
	 * Operator
	 */
	OPERATOR,
	/**
	 * String value
	 */
	VALUE
}
