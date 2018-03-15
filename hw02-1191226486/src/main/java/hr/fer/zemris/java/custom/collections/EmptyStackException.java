package hr.fer.zemris.java.custom.collections;

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
	 * Defalut constructor.
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

}
