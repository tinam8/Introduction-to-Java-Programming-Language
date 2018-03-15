package hr.fer.zemris.bf.lexer;

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
	 * @param tokenType type of token
	 * @param tokenValue value of token
	 */
	public Token(TokenType tokenType, Object tokenValue) {
		this.type = tokenType;
		this.value = tokenValue;
	}

	/**
	 * Returns token value.
	 * @return token value; Object
	 */
	public Object getTokenValue() {
		return  value;
	}

	/**
	 * Returns token type.
	 * @return token type; {@link TokenType}
	 */
	public TokenType getTokenType() {
		return type;
	}

	@Override
	public String toString() {
		if (type == TokenType.EOF) {
			return ("Type: EOF, Value: null");
		}
		return String.format("Type: " + type + ", Value: "  + value + ", Value is instance of: " + value.getClass());
	}
}
