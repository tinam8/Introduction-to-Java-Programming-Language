package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * CLass that implements help command. <br>
 * If started with no arguments, it lists names of all supported commands. <br>
 * If started with single argument, it prints name and the description of
 * selected command (or print appropriate error message if no such command
 * exists).
 * 
 * @author tina
 *
 */
public class HelpShellCommand implements ShellCommand {
	/** Command name. */
	private static final String NAME = "help";
	/** Description of command */
	private static List<String> DESCRIPTION;
	{

		DESCRIPTION = new ArrayList<>();
		DESCRIPTION.add("If started with no arguments, it lists names of all supported commands.");
		DESCRIPTION.add("If started with single argument, it prints name and the description of  "
				+ "selected command (or print appropriate error message if no such command exists).");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.equals("")) {
			env.commands().forEach((k, v) -> env.writeln(k));
			return ShellStatus.CONTINUE;
		}

		ShellCommand shelCommand = env.commands().get(arguments);
		
		if (shelCommand == null) {
			env.writeln("Command " + arguments + " is not supported");
			return ShellStatus.CONTINUE;
		}
		
		env.writeln(arguments);
		shelCommand.getCommandDescription().forEach(text -> env.writeln(text));
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(DESCRIPTION);
	}

}
