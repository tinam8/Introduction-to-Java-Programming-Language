package hr.fer.zemris.bf.lexer;

/**
 * Enumeration of token types.
 * 
 * @author tina
 *
 */
public enum TokenType {
	/** Represents that there are no more tokens. */
	EOF,
	/** Operator */
	OPERATOR,
	/** String variable  */
	VARIABLE,
	/** Constant */
	CONSTANT,
	/** Opened bracket '(' */
	OPEN_BRACKET,
	/** Closed bracket ')' */ 
	CLOSED_BRACKET
}
