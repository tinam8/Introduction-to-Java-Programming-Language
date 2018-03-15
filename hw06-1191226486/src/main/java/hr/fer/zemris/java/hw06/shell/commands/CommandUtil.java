package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class that implement methods that are shared among different commands
 * implementation.
 * 
 * @author tina
 *
 */
public class CommandUtil {

	/**
	 * Method that accepts String of arguments. <br>
	 * if argument starts with quotation, it is supported that during parsing \" as
	 * escape sequence represents " as regular character (and not string end).
	 * Additionally, a sequence \\ is treated as single \ . Every other
	 * situation in which after \ follows anything but " and \ should be
	 * literally copied as two characters
	 * 
	 * @param arguments
	 *            arguments
	 * @return list of separated arguments
	 */
	public static List<String> getArguments(String arguments) {
		List<String> argumentsList = new ArrayList<String>();

		if (!arguments.contains("\"")) {
			return Arrays.asList(arguments.split("\\s+"));
		}

		StringBuilder sb = new StringBuilder();
		char[] charArray = arguments.toCharArray();

		boolean quotes = false;
		int i = 0;
		if (charArray[0] == '\"') {
			quotes = true;
			i++;
		} else {
			sb.append(charArray[0]);
		}

		int len = charArray.length;
		for (i = 1; i < len; i++) {
			if (quotes) {
				if (charArray[i] == '\"') {
					if (i == (len - 1)) {
						argumentsList.add(sb.toString());
						return argumentsList;
					}

					if (charArray[i + 1] != ' ') {
						argumentsList.clear();
						return argumentsList;
					}

					argumentsList.add(sb.toString());
					sb = new StringBuilder();

					i++;
					while (charArray[i] == ' ') {
						i++;
					}

					if (charArray[i] != '"') {
						quotes = false;
						i--;
					}
				} else if (charArray[i] == '\\' && i > 0 && charArray[i-1] == '\\') {
					continue;
				} else {
					sb.append(charArray[i]);
				}
			} else {
				if (i + 1 == len) {
					sb.append(charArray[i]);
					argumentsList.add(sb.toString());
					return argumentsList;
				}

				if (charArray[i] == ' ') {
					argumentsList.add(sb.toString());
					sb = new StringBuilder();

					while (charArray[i] == ' ') {
						i++;
					}

					if (charArray[i] == '"') {
						quotes = true;
					} else {
						i--;
					}
				} else {
					if (charArray[i] == '\"') {
						argumentsList.clear();
						return argumentsList;
					}
					sb.append(charArray[i]);
				}
			}
		}
		return argumentsList;

	}

}
