package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * lass that implements cat shell command. <br>
 * Command cat takes one or two arguments. The first argument is path to some
 * file and is mandatory. The second argument is charset name that should be
 * used to interpret chars from bytes. If not provided, a default platform
 * charset is going to be used.<br>
 * This command opens given file and writes its content to console.
 * 
 * @author tina
 *
 */
public class CatShellCommand implements ShellCommand {
	/** Command name. */
	private static final String NAME = "cat";
	/** Description of command */
	private static List<String> DESCRIPTION;
	{

		DESCRIPTION = new ArrayList<>();
		DESCRIPTION.add("cat command opens given file and writes its content to console.");
		DESCRIPTION.add("Command cat takes one or two arguments.");
		DESCRIPTION.add("The first argument is path to some file and is mandatory.");
		DESCRIPTION.add("The second argument is charset name that should be used to interpret chars from bytes.");
		DESCRIPTION.add("If not provided, a default platform charset is going to be used.");

	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) throws ShellIOException {
		List<String> arg = CommandUtil.getArguments(arguments);
		
		if (arg.size() == 0 || arg.size() > 2) {
			env.writeln("Wrong arguments.");
			env.writeln("Usage: " + NAME + " filePath" + " charsetName (optinal)");
			return ShellStatus.CONTINUE;
		}

		Path path = Paths.get(arg.get(0));
		if (!Files.isRegularFile(path)) {
			env.writeln("No such file.");
			env.writeln("Usage: " + NAME + " filePath" + " charsetName (optinal)");
			return ShellStatus.CONTINUE;
		}
		
		BufferedReader br = null;
		if (arg.size()== 2) {
			try {
				br = new BufferedReader(
						new InputStreamReader(new BufferedInputStream(new FileInputStream(arg.get(0))), arg.get(1)));
			} catch (UnsupportedEncodingException | FileNotFoundException e) {
				env.writeln("Error occurred." + e.getMessage());
				return ShellStatus.CONTINUE;
			}
		} else {
			try {
				br = new BufferedReader(
						new InputStreamReader(new BufferedInputStream(new FileInputStream(arg.get(0))), Charset.defaultCharset()));
			} catch (FileNotFoundException e) {
				env.writeln("Error occurred" + e.getMessage());
				return ShellStatus.CONTINUE;
			}
		}
		
		String line;
		try {
			while ((line = br.readLine()) != null){
				env.writeln(line);
			}
		} catch (IOException e) {
			env.writeln("Error occurred" + e.getMessage());
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
