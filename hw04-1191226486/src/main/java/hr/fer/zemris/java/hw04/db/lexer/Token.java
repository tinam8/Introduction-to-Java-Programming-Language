package hr.fer.zemris.java.hw04.db.lexer;

/**
 * Class that creates one token.
 * 
 * @author tina
 *
 */
public class Token {

	/**
	 * Type of token.
	 */
	private TokenType type;
	/**
	 * Value of token.
	 */
	private Object value;

	/**
	 * Constructor
	 * @param type type of token
	 * @param value value of token
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}

	/**
	 * Returns token value.
	 * @return tkoen value; Object
	 */
	public Object getValue() {
		return  value;
	}

	/**
	 * Returns token type.
	 * @return token type; {@link TokenType}
	 */
	public TokenType getType() {
		return type;
	}
}
