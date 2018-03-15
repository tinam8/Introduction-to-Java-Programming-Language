package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;;

/**
 * Lexer analyzer for {@link SmartScriptParser}
 * 
 * @author tina
 *
 */
public class Lexer {

	/**
	 * Input text.
	 */
	private char[] data;
	/**
	 * Current token.
	 */
	private Token token;
	/**
	 * Index of first data emelent that has not been analysied yet.
	 */
	private int currentIndex;
	/**
	 * Current Lexer state.
	 */
	private LexerState currentState;

	/**
	 * True if token for tag name was created, otherwise false.
	 */
	private boolean tagNameState;

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
		currentState = LexerState.TEXT;
		tagNameState = false;
	}

	/**
	 * Sets way of lexer analysing.
	 * 
	 * @param state
	 *            state of lexer; LexerState
	 * 
	 * @throws IllegalArgumentException
	 *             if state is null
	 */
	public void setState(LexerState state) {
		if (state == null) {
			throw new IllegalArgumentException("State can not be null!");
		}

		currentState = state;
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

		if (currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
		} else if (currentState == LexerState.TEXT) {
			extractNextTokenText();
		} else {
			extractNextTokenTag();
		}

		return getToken();
	}

	/**
	 * Supplementary method that generates the next token so as the state of
	 * lexer is TEXT.
	 * 
	 * @throws LexerException
	 *             if there are invalid escapes.
	 */
	private void extractNextTokenText() {
		// detction of tag beginnig
		if (data[currentIndex] == '{' && currentIndex + 1 < data.length && data[currentIndex + 1] == '$') {
			setState(LexerState.TAG);
			tagNameState = false;
			currentIndex += 2;
			token = new Token(TokenType.BEGINNIG_TAG, "{$");
			return;
		}

		StringBuilder sb = new StringBuilder();

		while (currentIndex < data.length) {
			// detection of escaping
			if (data[currentIndex] == '\\' && currentIndex + 1 < data.length) {
				if (data[currentIndex + 1] != '{' && data[currentIndex + 1] != '\\') {
					throw new LexerException("Invalid escape in text: " + data[currentIndex] + data[currentIndex + 1]);
				}

				currentIndex++;
			}

			sb.append(data[currentIndex++]);

			if (currentIndex < data.length && data[currentIndex] == '{') {
				break;
			}
		}

		token = new Token(TokenType.TEXT, sb.toString());
	}

	/**
	 * Supplementary method that generates the next token so as the state of
	 * lexer is TAG.
	 * 
	 * @throws LexerException
	 *             if Tag sintax is incorrect.
	 */
	public void extractNextTokenTag() {
		if (!tagNameState) {
			extractTagName();
			return;
		}

		skipBlanks();

		if (data[currentIndex] == '$' && currentIndex + 1 < data.length && data[currentIndex + 1] == '}') {
			token = new Token(TokenType.ENDING_TAG, "$}");
			currentIndex += 2;
			setState(LexerState.TEXT);
			tagNameState = false;
			return;
		}

		if (Character.isLetter(data[currentIndex])) {
			extractVariable();
			return;
		}
		if (data[currentIndex] == '@') {
			extractFunction();
			return;
		}
		if (data[currentIndex] == '"') {
			extractString();
			return;
		}
		if (Character.isDigit(data[currentIndex])) {
			extractNumber();
			return;
		}
		if (data[currentIndex] == '+' || data[currentIndex] == '/' || data[currentIndex] == '*'
				|| data[currentIndex] == '^') {
			token = new Token(TokenType.OPERATOR, data[currentIndex++]);
			return;
		}
		if (data[currentIndex] == '-' && currentIndex + 1 < data.length) {
			if (!Character.isDigit(data[currentIndex + 1])) {
				token = new Token(TokenType.OPERATOR, data[currentIndex++]);
				return;
			}

			extractNumber();
			return;
		}

		throw new LexerException("Error at position " + currentIndex + "with " + data[currentIndex]);

	}

	/**
	 * Supplementary method that generates the next token of type VARIABLE.
	 * 
	 * @throws LexerException
	 *             if variable is invalid
	 */
	private void extractVariable() {
		StringBuilder sb = new StringBuilder();
		sb.append(data[currentIndex++]);

		while (currentIndex < data.length) {
			if (isValidChar(data[currentIndex])) {
				sb.append(data[currentIndex++]);
				continue;
			} else {
				break;
			}
		}

		token = new Token(TokenType.VARIABLE, sb.toString());

	}

	/**
	 * Supplementary method that generates the next token of type FUNCTION.
	 * 
	 * @throws LexerException
	 *             if function name is invalid
	 */
	private void extractFunction() {
		StringBuilder sb = new StringBuilder();
//		sb.append(data[currentIndex++]);
		currentIndex++;

		if (currentIndex >= data.length || (!Character.isLetter(data[currentIndex]))) {
			throw new LexerException("Function name invalid at position " + currentIndex);
		}

		// adding first letter after @
		sb.append(data[currentIndex++]);

		while (currentIndex < data.length) {
			if (isValidChar(data[currentIndex])) {
				sb.append(data[currentIndex++]);
				continue;
			} else {
				break;
			}
		}

		token = new Token(TokenType.FUNCTION, sb.toString());

	}

	/**
	 * Supplementary method that determines if function name or variable can
	 * contain character c
	 * 
	 * @param c
	 *            character to check
	 * @return true if c is valid, false otherwise
	 */
	private static boolean isValidChar(char c) {
		if (Character.isLetter(c) || Character.isDigit(c) || c == '_') {
			return true;
		}

		return false;
	}

	/**
	 * Supplementary method that generates the next token of type STRING.
	 * 
	 * @throws LexerException
	 *             if string is invalid
	 */
	private void extractString() {
		StringBuilder sb = new StringBuilder();
		currentIndex++;

		while (currentIndex < data.length) {
			// check if escaping is correct
			if (data[currentIndex] == '\\') {
				currentIndex++;
				if (currentIndex < data.length) {
					switch (data[currentIndex]) {
					case '\\':
						sb.append(data[currentIndex++]);
						continue;
					case '\"':
						sb.append(data[currentIndex++]);
						continue;
					case 't':
						sb.append('\t');
						currentIndex++;
						continue;
					case 'n':
						sb.append('\n');
						currentIndex++;
						continue;
					case 'r':
						sb.append('\r');
						currentIndex++;
						continue;
					default:
						throw new LexerException("Invalid escaping in string at position " + currentIndex);
					}
				}
			}
			
			// ending of string
			if (data[currentIndex] == '"') {
				token = new Token(TokenType.STRING, sb.toString());
				currentIndex++;
				return;
			}

			// append any other character
			sb.append(data[currentIndex]);
			currentIndex++;	
		}

		throw new LexerException("Invalid string, no closing at position " + currentIndex);
	}

	/**
	 * Supplementary method that generates the next token of type NUMBER.
	 * 
	 * @throws LexerException
	 *             if string is invalid
	 */
	private void extractNumber() {
		StringBuilder sb = new StringBuilder();
		boolean isDecimal = false;

		sb.append(data[currentIndex++]);

		while (currentIndex < data.length) {
			if (Character.isDigit(data[currentIndex])) {
				sb.append(data[currentIndex++]);
				continue;
			} else if (data[currentIndex] == '.' && currentIndex + 1 < data.length
					&& Character.isDigit(data[currentIndex + 1])) {
				sb.append(data[currentIndex++]);
				sb.append(data[currentIndex++]);
				isDecimal = true;
				continue;
			} else {
				break;
			}
		}

		if (isDecimal) {
			token = new Token(TokenType.DECIMAL_NUMBER, Double.parseDouble(sb.toString()));
		} else {
			token = new Token(TokenType.INTEGER_NUMBER, Integer.parseInt(sb.toString()));
		}

	}

	/**
	 * Supplementary method that generates the next token that is TAG_NAME.
	 * 
	 * @throws LexerException
	 *             if tag name is invalid
	 */
	private void extractTagName() {
		if (data[currentIndex] == '=') {
			token = new Token(TokenType.TAG_ECHO, "ECHO");
			currentIndex++;
			tagNameState = true;
			return;
		}

		skipBlanks();

		if (currentIndex + 3 > data.length) {
			throw new LexerException("Unexpected end of input, tag name is not defined.");
		}

		char first = data[currentIndex];
		char second = data[currentIndex + 1];
		char third = data[currentIndex + 2];

		if (Character.toUpperCase(first) == 'E' && Character.toUpperCase(second) == 'N'
				&& Character.toUpperCase(third) == 'D') {
			token = new Token(TokenType.TAG_END, String.format("%c%c%c", first, second, third));
		} else if (Character.toUpperCase(first) == 'F' && Character.toUpperCase(second) == 'O'
				&& Character.toUpperCase(third) == 'R' && data[currentIndex + 3] == ' ') {
			token = new Token(TokenType.TAG_FOR, String.format("%c%c%c", first, second, third));

		} else {
			throw new LexerException("Unknown tag name.");
		}

		currentIndex += 3;
		skipBlanks();
		tagNameState = true;
		return;

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

}
