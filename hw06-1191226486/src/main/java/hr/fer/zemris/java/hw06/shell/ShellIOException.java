package hr.fer.zemris.java.hw06.shell;

/**
 * Class which implements ShellIOException that is thrown if reading or
 * writing fails.
 * 
 * @author tina
 *
 */
public class ShellIOException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public ShellIOException() {
		super();
	}

	/**
	 * Constructor that has one argument.
	 * 
	 * @param message
	 *            message to be shown when exception is thrown.
	 */
	public ShellIOException(String message) {
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
	public ShellIOException(String s, Throwable throwable) {
		super(s, throwable);
	}

	/**
	 * Constructs a new runtime exception with the specified cause and a detail
	 * message.
	 * 
	 * @param throwable
	 *            cause
	 */
	public ShellIOException(Throwable throwable) {
		super(throwable);
	}
}
