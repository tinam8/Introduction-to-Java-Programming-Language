package hr.fer.zemris.java.hw11.jnotepadpp.sort;

import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * Class that holds different type of sorting list of strings. Sorts based on
 * current locale settings.
 * 
 * @author tina
 *
 */
public class SortOperations {
	/** Sorts lines ascending */
	public static final ISort sortAscendding = (lines) -> {
		Collator collator = getCollator();

		lines.sort((o1, o2) -> collator.compare(o1, o2));
		return getText(lines);
	};
	/** Sorts line descending */
	public static final ISort sortDescending = (lines) -> {
		Collator collator = getCollator();

		lines.sort((o1, o2) -> collator.reversed().compare(o1, o2));
		return getText(lines);
	};
	/** Removes all lines which are duplicate */
	public static final ISort sortUnique = (lines) -> {
		List<String> temp = new ArrayList<>();
		lines.forEach(line -> {
			if (!temp.contains(line)) {
				temp.add(line);
			}
		});

		return getText(temp);
	};

	/**
	 * Supplementary method that returns list of strings as text where each
	 * string is a new line of text.
	 * 
	 * @param listLines
	 *            list of lines
	 * @return String that is made out of list of strings
	 */
	private static String getText(List<String> listLines) {
		StringBuilder sb = new StringBuilder();

		listLines.forEach(line -> {
			sb.append(line + '\n');
		});

		return sb.toString();
	}

	/**
	 * Supplementary method that gets and returns instance of {@link Collator}
	 * based on current language.
	 * 
	 * @return instance of {@link Collator}
	 */
	private static Collator getCollator() {
		String langugage = LocalizationProvider.getInstance().getLanguage();
		Locale locale = new Locale(langugage);
		return Collator.getInstance(locale);
	}
	
	
	
};
