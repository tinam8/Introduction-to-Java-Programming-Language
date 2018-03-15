package hr.fer.zemris.java.hw06.shell;

import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.CatShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HexdumpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.LsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.TreeShellCommand;

/**
 * Command-line program. When started, program should writes a greeting message
 * to user (Welcome to MyShell v 1.0), writes a prompt symbol and waits for the
 * user to enter a command.<br>
 * The command can span across multiple lines. However, each line that is not
 * the last line of command must end with a special symbol that is used to
 * inform the shell that more lines as expected. <br>
 * These symbols are refereed as PROMPTSYMBOL and MORELINESSYMBOL. <br>
 * For each line that is part of multi-line command (except for the first one) a
 * shell writes MULTILINESYMBOL at the beginning followed by a single
 * whitespace. Shell provides a command symbol that can be used to change these
 * symbols.
 * 
 * @author tina
 *
 */
public class MyShell {
	/**
	 * Method that starts program.
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		SortedMap<String, ShellCommand> commands = new TreeMap<String, ShellCommand>();

		commands.put("charsets", new CharsetsShellCommand());
		commands.put("exit", new ExitShellCommand());
		commands.put("symbol", new SymbolShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("cat", new CatShellCommand());
		commands.put("tree", new TreeShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
		commands.put("help", new HelpShellCommand());

		Environment env = new MyEnvironment(commands);

		System.out.println("Welcome to MyShell v 1.0");
		ShellStatus status = ShellStatus.CONTINUE;

		while (status == ShellStatus.CONTINUE) {
			String line = null;
			
			try {
				line = readLineOrLines(env).trim();
			} catch (ShellIOException e) {
				System.exit(-1);
			}
			
			String[] parts = line.split("\\s+");

			String commandName = parts[0];
			String arguments = line.substring(commandName.length(), line.length()).trim();

			ShellCommand command = commands.get(commandName);
			if (command != null) {
				try {
					status = command.executeCommand(env, arguments);
				} catch (ShellIOException e) {
					System.exit(-1);
				}
			} else {
				env.writeln("No such command.");
			}

		}
	}

	/**
	 * Method that reads user input and returns it.
	 * 
	 * @param env
	 *            environment used for communication with the user.
	 * @return user input
	 */
	private static String readLineOrLines(Environment env) {
		StringBuilder sb = new StringBuilder();

		String line = "";
		env.write(env.getPromptSymbol().toString() + " ");
		while (true) {
			line = env.readLine();
			if (!line.endsWith(env.getMorelinesSymbol().toString())) {
				sb.append(line);
				return sb.toString();
			}

			sb.append(line.substring(0, line.length() - 1));
			env.write(env.getMultilineSymbol().toString() + " ");
		}

	}

}
