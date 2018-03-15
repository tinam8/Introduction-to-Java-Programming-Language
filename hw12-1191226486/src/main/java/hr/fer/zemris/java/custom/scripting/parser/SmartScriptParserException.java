package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Class which implements LexerException that is thrown when an error <br>
 * . occurs while parsing.
 * 
 * @author tina
 *
 */
public class SmartScriptParserException extends RuntimeException {
	/**  */
	private static final long serialVersionUID = 1L;

	/**
	 * Defalut constructor.
	 */
	public SmartScriptParserException() {
		super();
	}

	/**
	 * Constructor that has one argument.
	 * 
	 * @param message
	 *            message to be shown when exception is thrown.
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}
}
