package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class that implements ls shell command. <br>
 * takes a single argument – directory – and writes a directory listing (not
 * recursive).
 * 
 * @author tina
 *
 */
public class LsShellCommand implements ShellCommand {

	/** Command name. */
	private static final String NAME = "ls";
	/** Description of command */
	private static List<String> DESCRIPTION;
	{

		DESCRIPTION = new ArrayList<>();
		DESCRIPTION.add(
				"Command ls takes a single argument – directory path - and writes its content (not recursive).");

	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> argumentList = CommandUtil.getArguments(arguments);
		
		if (argumentList.size() != 1) {
			env.writeln("Wrong argument.");
			env.writeln("Usage: " + NAME + " directoryPath");
			return ShellStatus.CONTINUE;
		}

		Path path = Paths.get(argumentList.get(0));
	
		if (!Files.isDirectory(path)) {
			env.writeln("No such directory.");
			env.writeln("Usage: " + NAME + " directoryPath");
			return ShellStatus.CONTINUE;
		}

		File[] children = path.toFile().listFiles();
		if (children == null) {
			env.writeln("Error occured.");
			return ShellStatus.CONTINUE;
		}

		for (File file : children) {
			try {
				env.writeln(getFormattedAttributes(file));
			} catch (ShellIOException | IOException e) {
				env.writeln(e.getMessage());
				return ShellStatus.CONTINUE;
			}
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * Method that creates and returns formated output of file attributes.<br>
	 * Example: -rw- 53412 2009-03-15 12:59:31 azuriraj.ZIP
	 * 
	 * @param file
	 *            file whose attributes are being formated
	 * @return formated string of attributes
	 * @throws IOException
	 *             if error occurs
	 */
	public String getFormattedAttributes(File file) throws IOException {
		StringBuilder sb = new StringBuilder();

		if (file.isDirectory()) {
			sb.append("d");
		} else {
			sb.append("-");
		}

		if (file.canRead()) {
			sb.append("r");
		} else {
			sb.append("-");
		}

		if (file.canWrite()) {
			sb.append("w");
		} else {
			sb.append("-");
		}

		if (file.canExecute()) {
			sb.append("x");
		} else {
			sb.append("-");
		}

//		sb.append('\t');
		String access = sb.toString();
		sb.setLength(0);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Path path = file.toPath();
		BasicFileAttributeView faView = Files.getFileAttributeView(path, BasicFileAttributeView.class,
				LinkOption.NOFOLLOW_LINKS);
		BasicFileAttributes attributes = faView.readAttributes();
		FileTime fileTime = attributes.creationTime();
		String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));

		return String.format("%s %10d %s %s", access, file.length(), formattedDateTime, file.getName());

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
