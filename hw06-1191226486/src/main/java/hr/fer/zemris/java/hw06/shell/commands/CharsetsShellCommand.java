package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class that implements charsets shell command. <br>
 * Command charsets takes no arguments and lists names of supported charsets for
 * your Java platform. A single charset name is written per line.
 * 
 * @author tina
 *
 */
public class CharsetsShellCommand implements ShellCommand {
	/** Command name. */
	private static final String NAME = "charsets";
	/** Description of command */
	private static List<String> DESCRIPTION;
	{

		DESCRIPTION = new ArrayList<>();
		DESCRIPTION.add(
				"Command charsets takes no arguments and lists names of supported charsets.");
		DESCRIPTION.add(
				"A single charset name is written per line.");

	}
	
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		SortedMap<String, Charset> map = Charset.availableCharsets();
		try {
			map.forEach((s, a) -> env.writeln(s));
		} catch (ShellIOException e) {
			System.err.println(e.getMessage());
			return ShellStatus.CONTINUE;
		}
		
		return ShellStatus.CONTINUE;
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
