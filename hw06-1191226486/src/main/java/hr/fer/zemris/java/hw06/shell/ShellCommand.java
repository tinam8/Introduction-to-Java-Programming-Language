package hr.fer.zemris.java.hw06.shell;

import java.util.List;

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
	 * @param env
	 *            environment communication class
	 * @param arguments
	 *            single string which represents everything that user entered
	 *            AFTER the command name. It is expected that in case of
	 *            multiline input, the shell has already concatenated all lines
	 *            into a single line and removed MORELINES symbol from line
	 *            endings (before concatenation).
	 * @return shellStatus after the execution
	 */
	ShellStatus executeCommand(Environment env, String arguments);

	/**
	 * Returns the name of the command.
	 * 
	 * @return command name
	 */
	String getCommandName();

	/**
	 * Returns a description (usage instructions);
	 * 
	 * @return usage instruction
	 */
	List<String> getCommandDescription();
}
