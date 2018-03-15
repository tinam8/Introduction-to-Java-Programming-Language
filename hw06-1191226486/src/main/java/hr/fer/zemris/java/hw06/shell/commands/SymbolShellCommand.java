package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class that implement symbol command. <br>
 * Command takes one or two arguments. <br>
 * If one argument is given (options: PROMPT, MULTILINE, MORELINES), character
 * that represents it is written to the user. <br>
 * If two arguments are given first should be PROMPT, MULTILINE or MORELINES and
 * the second is character that represents role of the first.
 * 
 * @author tina
 *
 */
public class SymbolShellCommand implements ShellCommand {
	/** Command name. */
	private static final String NAME = "symbol";
	/** Description of command */
	private static List<String> DESCRIPTION;
	{

		DESCRIPTION = new ArrayList<>();
		DESCRIPTION.add("Command cat takes one or two arguments.");
		DESCRIPTION.add(" If one argument is given (options: PROMPT, MULTILINE, MORELINES), "
				+ "character that represents it is written to the user.");
		DESCRIPTION.add("If two arguments are given first should be PROMPT, MULTILINE or MORELINES"
				+ " and the second is character that represents role given with first argumnet.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {	
		String[] arg = arguments.split("\\s+");
		boolean change = false;

		if (arg.length == 0 || arg.length > 2) {
			env.writeln("Usage: " + NAME + "  PROMPT/MULTILINE/MORELINES" + " newSymbol");
			return ShellStatus.CONTINUE;
		}
		
		if (arg.length == 2) {
			if (arg[1].length() != 1) {
				env.writeln("New symbol should conatin only one symbol (character)");
				return ShellStatus.CONTINUE;
			}
			
			change = true;
		}

		switch (arg[0]) {
		case "PROMPT":
			if (change) {
				env.writeln("Symbol for PROMPT changed from '" + env.getPromptSymbol() + "' to '" + arg[1] + "'");
				env.setPromptSymbol(arg[1].toCharArray()[0]);
			} else {
				env.writeln("Symbol for PROMPT is '" + env.getPromptSymbol() +"'");
			}

			return ShellStatus.CONTINUE;
		case "MULTILINE":
			if (change) {
				env.writeln("Symbol for MULTILINE changed from '" + env.getMultilineSymbol() + "' to '" + arg[1] + "'");
				env.setMultilineSymbol(arg[1].toCharArray()[0]);
			} else {
				env.writeln("Symbol for MULTILINE is '" + env.getMultilineSymbol() +"'");
			}
			
			return ShellStatus.CONTINUE;
		case "MORELINES":
			if (change) {
				env.writeln("Symbol for MORELINES changed from '" + env.getMorelinesSymbol() + "' to '" + arg[1] + "'");
				env.setMorelinesSymbol(arg[1].toCharArray()[0]);
			} else {
				env.writeln("Symbol for MORELINES is '" + env.getMorelinesSymbol() +"'");
			}
			
			return ShellStatus.CONTINUE;
		default:
			env.writeln("No such symbol.");
			return ShellStatus.CONTINUE;
		}
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
