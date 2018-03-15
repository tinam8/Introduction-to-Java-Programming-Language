package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class that implements exit command. <br>
 * Command terminates the program. Takes no arguments. If arguments are
 * provided, they are ignored.
 * 
 * @author tina
 *
 */
public class ExitShellCommand implements ShellCommand {
	/** Command name. */
	private static final String NAME = "exit";
	/** Description of command */
	private static List<String> DESCRIPTION;
	{

		DESCRIPTION = new ArrayList<>();
		DESCRIPTION.add("Termminates the program.");
		DESCRIPTION.add(" Takes no arguments. If arguments are provided, they are ignored.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		return ShellStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		return NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		return  Collections.unmodifiableList(DESCRIPTION);
	}

}
