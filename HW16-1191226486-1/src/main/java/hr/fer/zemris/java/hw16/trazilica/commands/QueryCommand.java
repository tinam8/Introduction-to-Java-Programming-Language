package hr.fer.zemris.java.hw16.trazilica.commands;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import hr.fer.zemris.java.hw16.trazilica.Konzola;

/**
 * Class that implements functionality of query command. User writes its query
 * and than the database of documents is searched. 10 paths of most relevant
 * documents are being shown.
 * 
 * @author tina
 *
 */
public class QueryCommand implements ShellCommand {

	@Override
	public void executeCommand(String arguments) {
		Map<String, Integer> words = null;
		words = parseQuery(arguments);

		List<Double> vector = createVector(words);
		Konzola.setResults(vector);
		Konzola.displayResults();

	}

	/**
	 * Extracts words from the query. Takes into the account only the ones that
	 * are in the vocabulary.
	 * 
	 * @param query
	 *            users input
	 * @return map of words that appear in the query and their frequencies.s
	 */
	private Map<String, Integer> parseQuery(String query) {
		char[] data = query.trim().toCharArray();
		int currentIndex = 0;
		Map<String, Integer> words = new LinkedHashMap<>();
		Set<String> vocabulary = Konzola.getVocabulary();
		List<String> allWords = new ArrayList<>();

		StringBuilder sb = new StringBuilder();
		while (currentIndex < data.length) {
			if (Character.isLetter(data[currentIndex]) && currentIndex != data.length - 1) {
				sb.append(data[currentIndex++]);
				continue;
			}

			if (currentIndex == data.length - 1) {
				sb.append(data[currentIndex++]);
			}
			if (sb.length() == 0) {
				currentIndex++;
				continue;
			}

			if (vocabulary.contains(sb.toString().toLowerCase())) {
				words.merge(sb.toString().toLowerCase(), 1, (v1, v2) -> v1 + 1);
			}
			allWords.add(sb.toString());
			sb.setLength(0);
			currentIndex++;
		}

		System.out.println("Query is: " + allWords);
		return words;
	}

	/**
	 * Creates vector of query according to the vocabulary made of set of
	 * documents.
	 * 
	 * @param words
	 *            map of words that appear in the query and their frequencies
	 * @return list of double values that represent vector for the query
	 */
	private List<Double> createVector(Map<String, Integer> words) {
		Set<String> vocabulary = Konzola.getVocabulary();
		Map<String, Double> idfValues = Konzola.getIdfValues();

		List<Double> vector = new ArrayList<Double>(vocabulary.size());
		Set<String> wordsSet = words.keySet();

		vocabulary.forEach(vocabularyWord -> {
			if (wordsSet.contains(vocabularyWord)) {
				vector.add(idfValues.get(vocabularyWord) * words.get(vocabularyWord));
			} else {
				vector.add(0.0);
			}
		});

		return vector;
	}

}
