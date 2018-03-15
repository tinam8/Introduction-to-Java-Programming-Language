package hr.fer.zemris.java.hw04.db;

/**
 * . occurs while parsing.
 * 
 * @author tina
 *
 */
public class QueryParserException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Defalut constructor.
	 */
	public QueryParserException() {
		super();
	}

	/**
	 * Constructor that has one argument.
	 * 
	 * @param message
	 *            message to be shown when exception is thrown.
	 */
	public QueryParserException(String message) {
		super(message);
	}

}
