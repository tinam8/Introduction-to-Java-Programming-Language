package hr.fer.zemris.java.hw16.trazilica.commands;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import hr.fer.zemris.java.hw16.trazilica.Konzola;
import hr.fer.zemris.java.hw16.trazilica.Result;

/**
 * Class that implements functionality of type command. After "type" there
 * should be a number that represents position of relevant document among
 * results. Content of relevant document is being shown to the user in console.
 * 
 * @author tina
 *
 */
public class TypeCommand implements ShellCommand {

	@Override
	public void executeCommand(String arguments) {
		arguments = arguments.trim();
		int number = 0;

		try {
			number = Integer.parseInt(arguments);
		} catch (NumberFormatException e) {
			System.out.println("Invalid argument.");
			return;
		}

		List<Result> results = Konzola.getResults();
		if (number < 0 || number >= results.size() || number >= 10) {
			System.out.println("No such dcument among relevant ones..");
			return;
		}

		Result result = results.get(number);
		Path path = result.getFilePath();

		System.out.println(path.toString());
		try {
			String text = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
			System.out.println(text);
		} catch (IOException e) {
			System.out.println("Error while reading a document.");
		}
	}

}
