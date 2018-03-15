package hr.fer.zemris.java.hw06.shell.commands;

import java.io.FileInputStream;
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
 * Class that implements hexdump command. <br>
 * The hexdump command expects a single argument: file name, and produces
 * hex-output. <br>
 * For all other characters a '.' is printed instead <br>
 * (i.e. replace all bytes whose value is less than 32 or greater than 127 with
 * '.'). <br>
 * If user provides invalid or wrong argument for any of commands (i.e. user
 * provides directory name for hexdump command), appropriate message is written
 * and shell is prepared to accept a new command from user.
 * 
 * @author tina
 *
 */
public class HexdumpShellCommand implements ShellCommand {
	/** Command name. */
	private static final String NAME = "hexdump";
	/** Description of command */
	private static List<String> DESCRIPTION;
	{

		DESCRIPTION = new ArrayList<>();
		DESCRIPTION.add("The hexdump command expects a single argument: file name, and produces hex-output.");
		DESCRIPTION.add("For all other characters a '.' is printed instead.");
		DESCRIPTION.add(
				"I If user provides invalid or wrong argument for any of commands appropriate message is written.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> arg = CommandUtil.getArguments(arguments);

		if (arg.size() != 1) {
			env.writeln("Wrong argument.");
			env.writeln("Usage: " + NAME + " filePath");
			return ShellStatus.CONTINUE;
		}

		Path path = Paths.get(arg.get(0));
		if (!Files.isRegularFile(path)) {
			env.writeln("No such file.");
			env.writeln("Usage: " + NAME + " filePath");
			return ShellStatus.CONTINUE;
		}

		printHexdump(path, env);

		return ShellStatus.CONTINUE;

	}

	/**
	 * Prints hexdump to the user using input stream.
	 * 
	 * @param path
	 *            path to file
	 * 
	 *            input stream
	 * @param env
	 *            environment for communication with the user
	 */
	private static void printHexdump(Path path, Environment env) {

		try (FileInputStream is = new FileInputStream(path.toFile())) {
			byte[] buff = new byte[1024];
			
			while (true) {
				int bytesProcessed = 0;
				int read = is.read(buff);
				if (read < 1)
					break;

				int cycleProcessed = 0;
				while (cycleProcessed < read) {
					int limit = 16;

					if (read - cycleProcessed < 16) {
						limit = read - cycleProcessed;
					}

					env.write(String.format("%08X: ", bytesProcessed));

					for (int i = 0; i < 16; i++) {
						if (i < limit) {
							if (i == 8) {
								env.write("|");
							}
		
							env.write(String.format("%02X", buff[cycleProcessed + i]));
							
							if (i != 7) {
								env.write(" ");
							}
						} else {
							env.write("   ");
						}
					}

					env.write("| ");
					for (int i = 0; i < 16; i++) {
						if (i < limit) {
							if (buff[cycleProcessed + i] < 32 || buff[cycleProcessed + i] > 127) {
								env.write(".");
							} else {
								env.write("" + (char) buff[cycleProcessed + i]);
							}
						}
					}

					env.write("\n");
					cycleProcessed += 16;
					bytesProcessed += 16;
				}

				
			}
		} catch (IOException ex) {
			env.writeln("Error occurred");
			return;
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
