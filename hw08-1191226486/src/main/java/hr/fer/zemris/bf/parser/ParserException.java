package hr.fer.zemris.bf.parser;

/**
 * Class which implements ParserException that is thrown when an error occurs
 * while parsing boolean expression. <br>
 * 
 * 
 * @author tina
 *
 */
public class ParserException extends RuntimeException {

	/**
	 * Version id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public ParserException() {
		super();
	}

	/**
	 * Constructor that has one argument.
	 * 
	 * @param message
	 *            message to be shown when exception is thrown.
	 */
	public ParserException(String message) {
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
	public ParserException(String s, Throwable throwable) {
		super(s, throwable);
	}

	/**
	 * Constructs a new runtime exception with the specified cause and a detail
	 * message.
	 * 
	 * @param throwable
	 *            cause
	 */
	public ParserException(Throwable throwable) {
		super(throwable);
	}
}
