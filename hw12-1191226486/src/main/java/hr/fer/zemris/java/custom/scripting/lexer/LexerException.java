package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Class which implements LexerException that is thrown when an error <br>
 * occurs while lexer analysis.
 * 
 * @author tina
 *
 */
public class LexerException extends RuntimeException {

	/**
	 * Version
	 */
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
