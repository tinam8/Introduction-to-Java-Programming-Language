package hr.fer.zemris.java.hw03.prob1;

public class LexerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Defalut constructor.
	 */
	public LexerException() {
		super();
	}

	/**
	 * Constructor that has one argument.
	 * 
	 * @param message
	 *            message to be shown when exception is thrown.
	 */
	public LexerException(String message) {
		super(message);
	}
}
