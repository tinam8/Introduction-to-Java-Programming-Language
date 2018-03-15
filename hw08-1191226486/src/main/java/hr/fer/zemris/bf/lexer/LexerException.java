package hr.fer.zemris.bf.lexer;

/**
 * Class which implements LexerException that is thrown if there is sintax error
 * in input.
 * 
 * @author tina
 *
 */
public class LexerException extends RuntimeException {
	/**
	 * version id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
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

	/**
	 * Constructs a new runtime exception with the specified detail message and
	 * cause.
	 * 
	 * @param s
	 *            message
	 * @param throwable
	 *            cause
	 */
	public LexerException(String s, Throwable throwable) {
		super(s, throwable);
	}

	/**
	 * Constructs a new runtime exception with the specified cause and a detail
	 * message.
	 * 
	 * @param throwable
	 *            cause
	 */
	public LexerException(Throwable throwable) {
		super(throwable);
	}
}
