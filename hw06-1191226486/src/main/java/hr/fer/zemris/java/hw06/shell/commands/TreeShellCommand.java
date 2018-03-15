package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class that implements tree shell command. <br>
 * The tree command expects a single argument: directory name and prints a tree
 * (each directory level shifts output two characters to the right).
 * 
 * @author tina
 *
 */
public class TreeShellCommand implements ShellCommand {
	/** Command name. */
	private static final String NAME = "tree";
	/** Description of command */
	private static List<String> DESCRIPTION;
	{

		DESCRIPTION = new ArrayList<>();
		DESCRIPTION.add("Command expects a single argument: directory name and prints a tree containing of subdirectories.");
	}

	/**
	 * Class that prints directory tree.
	 * 
	 * @author tina
	 *
	 */
	private static class PrintTree implements FileVisitor<Path> {

		/** Current level directory level */
		private int level = 0;
		/** Environment that manages communication with user */
		Environment env;

		/**
		 * Constructor
		 * 
		 * @param env
		 *            environment for commendation with user
		 */
		public PrintTree(Environment env) {
			this.env = env;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < level; i++) {
				sb.append("  ");
			}
			
			sb.append(dir.getFileName());	
			env.writeln(sb.toString());
			level++;
			
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < level; i++) {
				sb.append("  ");
			}
			
			sb.append(file.getFileName());	
			env.writeln(sb.toString());

			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			level--;
			return FileVisitResult.CONTINUE;
		}
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> arg = CommandUtil.getArguments(arguments);

		if (arg.size() != 1) {
			env.writeln("Usage: " + NAME + " directoryPath");
			return ShellStatus.CONTINUE;
		}

		Path path = Paths.get(arg.get(0));
		if (!Files.isDirectory(path)) {
			env.writeln("Usage: " + NAME + " directoryPath");
			return ShellStatus.CONTINUE;
		}

		PrintTree printTree = new PrintTree(env);

		try {
			Files.walkFileTree(path, printTree);
		} catch (IOException e) {
			env.writeln(e.getMessage());
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
