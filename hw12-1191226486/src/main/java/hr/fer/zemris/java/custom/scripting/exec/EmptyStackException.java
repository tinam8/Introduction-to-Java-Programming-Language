package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Class which implements EmptyStackException that is thrown when pop or peek
 * method is called on empty stack.
 * 
 * @author tina
 *
 */
public class EmptyStackException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public EmptyStackException() {
		super();
	}

	/**
	 * Constructor that has one argument.
	 * 
	 * @param message
	 *            message to be shown when exception is thrown.
	 */
	public EmptyStackException(String message) {
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
	public EmptyStackException(String s, Throwable throwable) {
		super(s, throwable);
	}

	/**
	 * Constructs a new runtime exception with the specified cause and a detail
	 * message.
	 * 
	 * @param throwable cause
	 */
	public EmptyStackException(Throwable throwable) {
		super(throwable);
	}

}
