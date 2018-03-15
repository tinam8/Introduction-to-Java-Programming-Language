package hr.fer.zemris.java.hw16.trazilica.commands;

/**
 * Interface that all commands implement.
 * 
 * @author tina
 *
 */
public interface ShellCommand {
	/**
	 * Method that executes command.
	 * 
	 * @param arguments
	 *            single string which represents everything that user entered
	 *            AFTER the command name. 
	 */
	public void executeCommand(String arguments);

}
