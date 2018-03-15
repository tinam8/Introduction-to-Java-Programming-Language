package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class that implements mkdir command.<br>
 * The mkdir command takes a single argument: directory name, and creates the
 * appropriate directory structure.
 * 
 * @author tina
 *
 */
public class MkdirShellCommand implements ShellCommand {
	/** Command name. */
	private static final String NAME = "mkdir";
	/** Description of command */
	private static List<String> DESCRIPTION;
	{

		DESCRIPTION = new ArrayList<>();
		DESCRIPTION.add(
				"The mkdir command takes a single argument: directory name, and creates the appropriate directory structure.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> arg = CommandUtil.getArguments(arguments);

		if (arg.size() != 1) {
			env.writeln("Usage: " + NAME + " directoryPath");
			return ShellStatus.CONTINUE;
		}
		
		Path path = Paths.get(arg.get(0));
		try {
			Files.createDirectories(path);
			return ShellStatus.CONTINUE;
		} catch (IOException e) {
			env.writeln("Error occurred");
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}
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
