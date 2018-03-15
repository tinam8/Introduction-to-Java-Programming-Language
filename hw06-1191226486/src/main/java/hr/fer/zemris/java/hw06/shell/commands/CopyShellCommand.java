package hr.fer.zemris.java.hw06.shell.commands;

import java.io.FileInputStream;
import java.io.FileOutputStream;
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
 * Class that implements copy command.<br>
 * The copy command expects two arguments: source file name and destination file
 * name (i.e. paths and names). <br>
 * If destination file exists, user is asked is it allowed to overwrite it. <br>
 * If the second argument is directory, command assumes that user wants to copy
 * the original file into that directory using the original file name.<br>
 * Command works only with files (no directories).
 * 
 * @author tina
 *
 */
public class CopyShellCommand implements ShellCommand {
	/** Command name. */
	private static final String NAME = "copy";
	/** Description of command */
	private static List<String> DESCRIPTION;
	{

		DESCRIPTION = new ArrayList<>();
		DESCRIPTION.add(
				"The copy command expects two arguments: source file name and destination file name (i.e. paths and names).");
		DESCRIPTION.add("If destination file exists, user is asked is it allowed to overwrite it.");
		DESCRIPTION.add("If the second argument is directory, command assumes that user wants to copy  the "
				+ "original file into that directory using the original file name.");
		DESCRIPTION.add("Command works only with files (no directories).");

	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> argumentList = CommandUtil.getArguments(arguments);

		if (argumentList.size() != 2) {
			env.writeln("Wrong arguments");
			env.writeln("Usage: " + NAME + " filePath" + " file/directoryPath");
			return ShellStatus.CONTINUE;
		}

		Path pathFirst = Paths.get(argumentList.get(0));
		Path pathSecond = Paths.get(argumentList.get(1));

		if (!Files.isRegularFile(pathFirst)) {
			env.writeln("Usage: " + NAME + " filePath" + " file/directoryPath");
			return ShellStatus.CONTINUE;
		}

		if (Files.isDirectory(pathSecond)) {
			pathSecond = Paths.get(argumentList.get(1) + "/" + pathFirst.toFile().getName());
		}
		
		if (Files.isRegularFile(pathSecond)) {
			try {
				env.writeln("Do you want to overwrite the file? (Yes/No)");
				env.write(env.getPromptSymbol() + " ");

				try {
					if (!env.readLine().equals("Yes")) {
						return ShellStatus.CONTINUE;
					}
				} catch (Exception e) {
					env.writeln("Error occurred");
					return ShellStatus.CONTINUE;
				}

			} catch (Exception e) {
				env.writeln("Error occurred");
				return ShellStatus.CONTINUE;
			}
		}

		try (FileInputStream is = new FileInputStream(pathFirst.toFile());
				FileOutputStream os = new FileOutputStream(pathSecond.toFile());) {
			byte[] buff = new byte[1024];
			while (true) {
				int r = is.read(buff);
				if (r < 1)
					break;
				os.write(buff, 0, r);
			}

			return ShellStatus.CONTINUE;
		} catch (IOException ex) {
			env.writeln("Error occurred");
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
