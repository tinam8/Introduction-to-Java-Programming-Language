package hr.fer.zemris.java.hw04.db.lexer;

/**
 * Simple lexer analyzer.
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

		skipBlanks();
		checkQuery();
	}

	/**
	 * Generates next token, sets it as current one and returns it.
	 * 
	 * @return generated token.
	 * 
	 * @throws LexerException
	 *             if there are no tokens avaliable.
	 */
	public Token nextToken() {
		if (token != null && token.getType() == TokenType.EOF) {
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
	 * Supplementary method that generates the next token so as the state of
	 * lexer is EXTENDED.
	 * 
	 */
	private void extractNextToken() {
		StringBuilder sb = new StringBuilder();

		while (currentIndex < data.length) {
			skipBlanks();

			if (data[currentIndex] == 'L') {
				extractLikeOperatorToken();
				return;
			}
			if (Character.isLetter(data[currentIndex])) {
				extractFieldNameToken();
				return;
			}
			if (data[currentIndex] == '\"') {
				currentIndex++;
				extractValueToken();
				return;
			}
			if (nextIsOperator()) {
				extractOperatorToken();
				return;
			}

			throw new LexerException("Syntax error at position " + currentIndex + " with " + data[currentIndex]);

		}
	}

	/**
	 * Method that checks if first word of input is "query" and skips it.
	 * 
	 * @throws LexerException
	 *             if query is not at the beginning
	 */
	private void checkQuery() {
		if (currentIndex + 5 >= data.length) {
			throw new LexerException("Unexpected end of query.");
		}

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < 5; i++) {
			sb.append(data[currentIndex++]);
		}

		if (!sb.toString().trim().equals("query")) {
			throw new LexerException("Syntax error query has to start with key word \"query\"");
		}
	}

	/**
	 * Method that generates token of field type.
	 * 
	 * @throws LexerException
	 *             if field name is invalid
	 */
	private void extractFieldNameToken() {
		StringBuilder sb = new StringBuilder();

		while (currentIndex < data.length) {
			if (nextIsOperator() || data[currentIndex] == 'L') {
				switch (sb.toString().trim()) {
				case "jmbag":
					token = new Token(TokenType.FIELD_NAME, sb.toString().trim());
					return;
				case "firstName":
					token = new Token(TokenType.FIELD_NAME, sb.toString().trim());
					return;
				case "lastName":
					token = new Token(TokenType.FIELD_NAME, sb.toString().trim());
					return;
				default:
					throw new LexerException("Invalid field name was given > " + sb.toString());
				}
			} else {
				sb.append(data[currentIndex++]);
			}
		}

		throw new LexerException("Syntax error, unexpected end of query");

	}

	/**
	 * Private method that checks if next token could be one of the operators.
	 * 
	 * @return true if next token could be operator, otherwise false
	 */
	private boolean nextIsOperator() {
		if (data[currentIndex] == '<' || data[currentIndex] == '>' || data[currentIndex] == '='
				|| data[currentIndex] == '!') {
			return true;
		}

		return false;
	}

	/**
	 * Method that generates token for operators.
	 * 
	 * @throws LexerException
	 *             if operator is invalid
	 */
	private void extractOperatorToken() {
		if (data[currentIndex] == '!') {
			if (currentIndex + 1 < data.length && data[currentIndex + 1] == '=') {
				token = new Token(TokenType.OPERATOR, "!=");

				currentIndex += 2;
			} else {
				throw new LexerException(
						"Syntax error ivalid operator at position, " + currentIndex + " with " + data[currentIndex]);
			}
		}

		String operator = "" + data[currentIndex++];
		if (currentIndex < data.length && data[currentIndex] == '=') {
			operator += data[currentIndex++];
		}

		token = new Token(TokenType.OPERATOR, operator);
	}

	/**
	 * Method that generates token for like operator.
	 * 
	 * @throws LexerException
	 *             if like operator is invalid
	 */
	private void extractLikeOperatorToken() {
		if (currentIndex + 3 >= data.length) {
			throw new LexerException("Unexpected end of query.");

		}

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < 4; i++) {
			sb.append(data[currentIndex++]);
		}

		if (!sb.toString().equals("LIKE")) {
			throw new LexerException(
					"Syntax error ivalid operator at position, " + currentIndex + " with " + data[currentIndex]);
		}

		token = new Token(TokenType.OPERATOR, "LIKE");
	}

	/**
	 * Method that generates token of field type.
	 * 
	 * @throws LexerException
	 *             if string value is invalid
	 */
	private void extractValueToken() {
		StringBuilder sb = new StringBuilder();
		boolean operatorAppeared = false;

		while (currentIndex < data.length) {
			if (data[currentIndex] == '*' && operatorAppeared) {
				throw new LexerException("Operator * can not appear two times.");
			}

			if (data[currentIndex] == '\"') {
				token = new Token(TokenType.VALUE, sb.toString());
				currentIndex++;

				skipBlanks();
				if (currentIndex < data.length - 1) {
					skipAnd();
				}

				return;
			}

			sb.append(data[currentIndex++]);
		}

		throw new LexerException("There is no closing \" of string value.");
	}

	/**
	 * Method that moves current position to the first non blank character.
	 * 
	 * @throws LexerException
	 *             if after value there is something other than and
	 */
	private void skipAnd() {
		if (currentIndex + 2 >= data.length) {
			throw new LexerException("Sintax error at position " + currentIndex + " with " + data[currentIndex]);
		}

		String andOperator = "" + data[currentIndex++] + data[currentIndex++] + data[currentIndex++];

		if (!andOperator.toUpperCase().equals("AND")) {
			throw new LexerException("Sintax error at position " + currentIndex + " with " + data[currentIndex]);
		}

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
	 * Method that ckecks if character is blank or not
	 * 
	 * @param c
	 *            character to check
	 * @return true if character is blank, false otherwises
	 */
	private static boolean isBlank(char c) {
		if (c == ' ' || c == '\t' || c == '\r' || c == '\n') {
			return true;
		}

		return false;

	}

	/**
	 * Returns last generated tokken. Can balled multiple times. Does not start
	 * generation of next token.
	 * 
	 * @return generated token.
	 */
	public Token getToken() {
		return token;
	}

}
