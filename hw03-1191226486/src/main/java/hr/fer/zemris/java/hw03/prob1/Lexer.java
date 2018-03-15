package hr.fer.zemris.java.hw03.prob1;

/**
 * Simple lexer analyzer.
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
	 * Maximum number of digits that number token can consist of.
	 */
	private static final int MAX_DIGITS = 19;

	/**
	 * Current Lexer state.
	 */
	private LexerState currentState;

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
		currentState = LexerState.BASIC;
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
		} else if (currentState == LexerState.BASIC) {
			extractNextTokenBasic();
		} else {
			extractNextTokenExtended();
		}

		return getToken();
	}

	/**
	 * Supplementary method that generates the next token so as the state of
	 * lexer is EXTENDED.
	 * 
	 */
	private void extractNextTokenExtended() {
		if(data[currentIndex] == '#') {
			token = new Token(TokenType.SYMBOL, data[currentIndex++]);
			setState(LexerState.BASIC);
			return;
		}
	
		StringBuilder sb = new StringBuilder();
		
		while (currentIndex < data.length) {
			if (isBlank(data[currentIndex]) || data[currentIndex] == '#') {
				break;
			}
			
			sb.append(data[currentIndex++]);
		}

		token = new Token(TokenType.WORD, sb.toString());
	}

	/**
	 * Supplementary method that generates the next token so as the state of
	 * lexer is BASIC.
	 * 
	 */
	private void extractNextTokenBasic() {
		if (Character.isDigit(data[currentIndex])) {
			extractNumberToken();
		} else if (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') {
			extractWordToken();
		} else {
			if (data[currentIndex] == '#') {
				setState(LexerState.EXTENDED);
			}

			token = new Token(TokenType.SYMBOL, data[currentIndex++]);
		}
	}

	/**
	 * Method that generates token whose type is WORD.
	 * 
	 */
	private void extractWordToken() {
		StringBuilder sb = new StringBuilder(); 

		while (currentIndex < data.length) {
			if (Character.isLetter(data[currentIndex])) {
				sb.append(data[currentIndex++]);
				continue;
			} else if (data[currentIndex] == '\\') {
				currentIndex++;

				if (currentIndex == data.length || Character.isLetter(data[currentIndex])) {
					throw new LexerException("After '\\' needs to be a character not EOF!");
				}

				sb.append(data[currentIndex++]);
			} else {
				break;
			}
		}
		
		token = new Token(TokenType.WORD, sb.toString());
	}

	/**
	 * Method that generates token whose type is NUMBER.
	 */
	private void extractNumberToken() {
		int numberOfDigits = 0;
		StringBuilder sb = new StringBuilder();

		while (currentIndex < data.length) {
			if (numberOfDigits > MAX_DIGITS) {
				throw new LexerException("Number is too big can not be stored!");
			}

			if (Character.isDigit(data[currentIndex])) {
				sb.append(data[currentIndex++]);
				numberOfDigits++;
				continue;
			} else {
				break;
			}
		}
		token = new Token(TokenType.NUMBER, Long.parseLong(sb.toString()));
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
