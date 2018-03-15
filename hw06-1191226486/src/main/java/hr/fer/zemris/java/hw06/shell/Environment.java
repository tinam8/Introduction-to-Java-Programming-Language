package hr.fer.zemris.java.hw06.shell;

import java.util.SortedMap;

/**
 * Abstraction which implements communication with user.
 * 
 * @author tina
 *
 */
public interface Environment {
	/**
	 * Method used for reading from user (console).
	 * 
	 * @return user input
	 * @throws ShellIOException
	 *             if reading fails
	 */
	String readLine() throws ShellIOException;

	/**
	 * Method that writes response to the user.
	 * 
	 * @param text
	 *            what should be written
	 * @throws ShellIOException
	 *             if writing fails
	 */
	void write(String text) throws ShellIOException;

	/**
	 * Method that writes response to the user in one line.
	 * 
	 * @param text
	 *            what should be written
	 * @throws ShellIOException
	 *             if writing fails
	 */
	void writeln(String text) throws ShellIOException;

	/**
	 * Returns unmodifiable map of commands.
	 * 
	 * @return unmodifiable map
	 */
	SortedMap<String, ShellCommand> commands();
	
	/**
	 * Gets multiline symbol.
	 * 
	 * @return character that represents multiline symbol
	 */
	Character getMultilineSymbol();

	/**
	 * Method that sets value for multiline symbol.
	 * 
	 * @param symbol
	 *            Character that should represent multiline symbol
	 */
	void setMultilineSymbol(Character symbol);

	/**
	 * Gets prompt symbol.
	 * @return character that represents prompt symbol
	 */
	Character getPromptSymbol();

	/**
	 * Method that sets value for prompt symbol.
	 * @param symbol Character that should represent prompt symbol
	 */
	void setPromptSymbol(Character symbol);

	/**
	 *  Gets more lines symbol.
	 * @return symbol Character that represents more lines symbol
	 */
	Character getMorelinesSymbol();

	/**
	 * Method that sets value for more lines symbol.
	 * @param symbol symbol Character that should represent more lines symbol
	 */
	void setMorelinesSymbol(Character symbol);
}
