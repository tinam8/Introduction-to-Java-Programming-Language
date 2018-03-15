package hr.fer.zemris.bf.lexer;

/**
 * Simple lexer analyzer for Boolean expressions.
 * 
 * @author tina
 *
 */
public class Lexer {
	/**
	 * Input text as char array.
	 */
	private char[] data;
	/**
	 * Current token.
	 */
	private Token token;
	/**
	 * Index of first data element that has not been analyzed yet.
	 */
	private int currentIndex;

	/**
	 * Constructor
	 * 
	 * @param text
	 *            text for lexer analyzer; string
	 * @throws NullPointerException
	 *             if text is null.
	 */
	public Lexer(String text) {
		if (text == null) {
			throw new IllegalArgumentException("Text can not be null");
		}

		data = text.toCharArray();
		currentIndex = 0;
	}

	/**
	 * Generates next token, sets it as current one and returns it.
	 * 
	 * @return generated token.
	 * 
	 * @throws LexerException
	 *             if there are no tokens available.
	 */
	public Token nextToken() {
		if (token != null && token.getTokenType() == TokenType.EOF) {
			throw new LexerException("No tokens available.");
		}

		skipBlanks();

		if (currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
		} else {
			extractNextToken();
		}

		return getToken();
	}

	/**
	 * Supplementary method that generates the next token.
	 * 
	 */
	private void extractNextToken() {
		while (currentIndex < data.length) {

			if (Character.isLetter(data[currentIndex])) {
				extractIdentifier();
				return;
			}

			if (Character.isDigit(data[currentIndex])) {
				if (data[currentIndex] == '0') {
					extractFalse();
					return;
				} else if (data[currentIndex] == '1') {
					extractTrue();
					return;
				}

				throw new LexerException("Syntax error at position " + currentIndex + " Wrong numerical sequence.");
			}

			if (data[currentIndex] == '(') {
				token = new Token(TokenType.OPEN_BRACKET, '(');
				currentIndex++;
				return;
			}
			if (data[currentIndex] == ')') {
				token = new Token(TokenType.CLOSED_BRACKET, ')');
				currentIndex++;
				return;
			}

			if (data[currentIndex] == '*') {
				token = new Token(TokenType.OPERATOR, "and");
				currentIndex++;
				return;
			}
			if (data[currentIndex] == '+') {
				token = new Token(TokenType.OPERATOR, "or");
				currentIndex++;
				return;
			}
			if (data[currentIndex] == '!') {
				token = new Token(TokenType.OPERATOR, "not");
				currentIndex++;
				return;
			}
			if (data[currentIndex] == ':') {
				if (currentIndex + 2 >= data.length) {
					throw new LexerException("Syntax error at position " + currentIndex + ". Incorrect operator.");
				}

				if (data[currentIndex + 1] != '+' || data[currentIndex + 2] != ':') {
					throw new LexerException("Syntax error at position " + currentIndex + ". Incorrect operator.");
				}

				token = new Token(TokenType.OPERATOR, "xor");
				currentIndex += 3;
				return;
			}

			throw new LexerException("Syntax error at position " + currentIndex + " with " + data[currentIndex]);

		}
	}

	/**
	 * Method that generates token of field type.
	 * 
	 * @throws LexerException
	 *             if identifier is invalid
	 */
	private void extractIdentifier() {
		StringBuilder sb = new StringBuilder();
		sb.append(data[currentIndex++]);

		while (currentIndex < data.length) {
			// if next character is blank, or brackets end of identifier
			if (isBlank(data[currentIndex]) || data[currentIndex] == '(' || data[currentIndex] == ')') {
				break;
			}

			if (!Character.isLetterOrDigit(data[currentIndex]) && data[currentIndex] != '_') {
				throw new LexerException("Invalid identifier.");
			}

			sb.append(data[currentIndex++]);
		}

		// if end of input also end of identifier
		extractCorrectIdentifier(sb.toString());
	}

	/**
	 * Method that generates correct type of identifier: <br>
	 * OPERATOR: "and", "xor", "or", "not"<br>
	 * CONSTANT: true or false <br>
	 * VARIABLE: String value
	 * 
	 * @param identifier
	 *            Identifier to extract
	 */
	private void extractCorrectIdentifier(String identifier) {
		switch (identifier.toLowerCase()) {
		case "and":
			token = new Token(TokenType.OPERATOR, "and");
			return;
		case "xor":
			token = new Token(TokenType.OPERATOR, "xor");
			return;
		case "or":
			token = new Token(TokenType.OPERATOR, "or");
			return;
		case "not":
			token = new Token(TokenType.OPERATOR, "not");
			return;
		case "true":
			token = new Token(TokenType.CONSTANT, true);
			return;
		case "false":
			token = new Token(TokenType.CONSTANT, false);
			return;
		default:
			token = new Token(TokenType.VARIABLE, identifier.toUpperCase());
			return;
		}
	}

	/**
	 * Supplementary method that detects sequence of ones and generates true
	 * token.
	 */
	private void extractTrue() {
		if (isValidSequence('1')) {
			token = new Token(TokenType.CONSTANT, true);
		}
	}

	/**
	 * Supplementary method that detects sequence of zeros and generates false
	 * token.
	 */
	private void extractFalse() {
		if (isValidSequence('0')) {
			token = new Token(TokenType.CONSTANT, false);
		}
	}

	/**
	 * Method that checks if given numerical sequence is contains only given
	 * argument.
	 * 
	 * @param c
	 *            character that should be in sequence
	 * @return true if and only if sequence contains only character given as
	 *         argument
	 * @throws LexerException
	 *             if sequence contains anything but given argument
	 */
	private boolean isValidSequence(Character c) {
		StringBuilder sb = new StringBuilder();
		while (currentIndex < data.length) {
			sb.append(data[currentIndex]);
			
			if (data[currentIndex] == ' ') {
				currentIndex++;
				break;
			}

			if (data[currentIndex] != c) {
				throw new LexerException("Unexpected number: " + sb.toString());
			}

			currentIndex++;
		}

		return true;
	}

	/**
	 * Method that moves current position to the first non blank character.
	 */
	private void skipBlanks() {
		while (currentIndex < data.length) {
			char c = data[currentIndex];

			if (isBlank(c)) {
				currentIndex++;
				continue;
			}

			break;
		}
	}

	/**
	 * Method that checks if character is blank or not
	 * 
	 * @param c
	 *            character to check
	 * @return true if character is blank, false otherwise
	 */
	private static boolean isBlank(char c) {
		if (c == ' ' || c == '\t' || c == '\r' || c == '\n') {
			return true;
		}

		return false;

	}

	// TODO delete
	/**
	 * Returns last generated token. Can be called multiple times. Does not start
	 * generation of next token.
	 * 
	 * @return generated token.
	 */
	public Token getToken() {
		return token;
	}

}
