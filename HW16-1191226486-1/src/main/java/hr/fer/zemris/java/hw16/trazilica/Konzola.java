package hr.fer.zemris.java.hw16.trazilica;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw16.trazilica.commands.QueryCommand;
import hr.fer.zemris.java.hw16.trazilica.commands.ShellCommand;
import hr.fer.zemris.java.hw16.trazilica.commands.TypeCommand;

/**
 * Program that implements simple search engine. User can enter following
 * commands in console:
 * 
 * <li>query - to type what is wanted to be find</li>
 * <li>results - to get list of most relevant documents</li>
 * <li>type - to open one of the retrieved documents</li>
 * <li>exit - exits program</li>
 * 
 * 
 * @author tina
 *
 */
public class Konzola {
	/**
	 * Collection of all words from set of documents.
	 */
	private static Set<String> vocabulary = new LinkedHashSet<>();
	/**
	 * Map of file paths and words that appear mapped with their frequency.
	 */
	private static Map<Path, Map<String, Integer>> documentWords;
	/**
	 * Map of paths to the documents and their idf values.
	 */
	private static Map<String, Double> idfValues;
	/**
	 * Map of paths to the documents and their vector representations. (if *
	 * idf)
	 */
	private static Map<Path, List<Double>> documentVectors;
	/**
	 * List of stop words.
	 */

	private static List<Result> results = new ArrayList<>();
	/**
	 * List of stop words.
	 */
	private static List<String> stopWords;
	/**
	 * Path to stop words file.
	 */
	private static final String STOP_WORDS_PATH = "hrvatski_stoprijeci.txt";

	/**
	 * Getter for the vocabulary.
	 * 
	 * @return vocabulary
	 */
	public static Set<String> getVocabulary() {
		return vocabulary;
	}

	/**
	 * Getter for vectors that represent documents.
	 * 
	 * @return vectors that represent documents
	 */
	public static Map<Path, List<Double>> getDocumentVectors() {
		return documentVectors;
	}

	/**
	 * Getter for idf values of documents.
	 * 
	 * @return idf values of documents
	 */
	public static Map<String, Double> getIdfValues() {
		return idfValues;
	}

	/**
	 * Getter for results that contains most relevant documents.
	 * 
	 * @return results that contains most relevant documents
	 */
	public static List<Result> getResults() {
		return results;
	}

	/**
	 * Method that starts program.
	 * 
	 * @param args
	 *            command line arguments - first argument is path to the
	 *            articles folder
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Nedostaje argument komandne linije koji predstavlja put do direktorija sa clancima");
			System.exit(-1);
		}

		SortedMap<String, ShellCommand> commands = new TreeMap<String, ShellCommand>();

		commands.put("query", new QueryCommand());
		commands.put("type", new TypeCommand());

		loadStopWords();
		initializeVocabulary(args[0]);
		documentVectors = new HashMap<>();
		calculateIdf();
		createDocumentRepresentations();

		Scanner sc = new Scanner(System.in);
		System.out.print("Enter command > ");

		ShellCommand command = null;
		String input = null;
		String[] arguments = null;

		while (sc.hasNext()) {
			input = sc.nextLine().trim();
			arguments = input.split("\\s+");

			if (arguments[0].equals("exit") && arguments.length == 1) {
				sc.close();
				System.exit(1);
			}

			if (arguments[0].equals("results") && arguments.length == 1) {
				displayResults();

			} else {
				command = commands.get(arguments[0]);

				if (command == null) {
					System.out.println("Invalid comand.");
				} else {
					command.executeCommand(input.substring(arguments[0].length()));
				}
			}
			System.out.print("Enter command > ");
		}
	}

	/**
	 * Loads stop words into the list.
	 */
	private static void loadStopWords() {
		Path path = Paths.get(STOP_WORDS_PATH);
		if (!Files.exists(path)) {
			System.out.println("Dokument za stop rijeci ne postoji.");
			return;
		}

		if (!Files.isRegularFile(path)) {
			System.out.println("Dokument se ne moze citati. " + path);
			return;
		}

		try {
			stopWords = Files.readAllLines(path, StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.out.println("Pogreska prilikom citanja stop rijeci.");
			e.printStackTrace();
		}

	}

	/**
	 * Method that initializes vectors that represent documents and also it
	 * initializes vocabulary made of entire collection.
	 * 
	 * @param documentsPath
	 *            path to the
	 */
	private static void initializeVocabulary(String documentsPath) {
		Path path = Paths.get(documentsPath);

		try {
			Files.walkFileTree(path, new AnalyzeFiles());
		} catch (IOException e) {
			System.out.println("Pogreska prilikom citanja dokumenata");
			e.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * Class that visits each document and builds vocabulary and maps document
	 * word occurrences with their frequencies. Implements {@link FileVisitor}.
	 * 
	 * @author tina
	 *
	 */
	private static class AnalyzeFiles implements FileVisitor<Path> {

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			Map<String, Integer> fileWords = getWords(file);

			if (fileWords == null) {
				return FileVisitResult.CONTINUE;
			}

			if (documentWords == null) {
				documentWords = new HashMap<>();
			}
			documentWords.put(file, fileWords);
			vocabulary.addAll(fileWords.keySet());

			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		/**
		 * Method that creates map of words that appear in the document with
		 * their frequencies.
		 * 
		 * @param file
		 *            path to the document
		 * @return map of words that appear in the document with their
		 *         frequencies <code> Map<String, Integer></code>
		 */
		private Map<String, Integer> getWords(Path file) {
			Map<String, Integer> words = new HashMap<>();

			try {
				String text = new String(Files.readAllBytes(file), StandardCharsets.UTF_8);
				parseText(text, words);
			} catch (IOException e) {
				System.out.println("Pogreska prilikom citanja dokumenta " + file.toString());
				e.printStackTrace();
				return null;
			}

			return words;
		}

		/**
		 * Method that extracts words from text and their frequencies. Every
		 * character that is not letter is considered to be separator. (i.e.
		 * beach123house -> beach, house)
		 * 
		 * @param text
		 *            text from which words are going to be extracted.
		 * @param words
		 *            map in which extracted words and their frequencies will be
		 *            added.
		 */
		public static void parseText(String text, Map<String, Integer> words) {
			char[] data = text.trim().toCharArray();
			int currentIndex = 0;

			StringBuilder sb = new StringBuilder();
			while (currentIndex < data.length) {
				if (Character.isLetter(data[currentIndex])) {
					sb.append(data[currentIndex++]);
				} else {
					if (sb.length() == 0) {
						currentIndex++;
						continue;
					}
					if (!stopWords.contains(sb.toString().toLowerCase())
							&& (!stopWords.contains(sb.toString().toLowerCase() + "."))) {
						words.merge(sb.toString().toLowerCase(), 1, (v1, v2) -> v1 + 1);
					}
					sb.setLength(0);
					currentIndex++;
				}
			}

		}

	}

	/**
	 * Method that calculated idf value for documents.
	 */
	private static void calculateIdf() {
		vocabulary.forEach(vocabularyWord -> {
			int numberContains = getNumberOfDocuments(vocabularyWord);
			Double ifTdf = Math.log(documentWords.size() / numberContains);

			if (idfValues == null) {
				idfValues = new HashMap<>();
			}
			idfValues.put(vocabularyWord, ifTdf);
		});
	}

	/**
	 * Method that gets number of documents that contain word from vocabulary.
	 * 
	 * @param vocabularyWord
	 *            word from vocabulary
	 * @return number of documents that conatin that word
	 */
	private static int getNumberOfDocuments(String vocabularyWord) {
		int numberOfMatches = 0;

		for (Map<String, Integer> words : documentWords.values()) {
			if (words.keySet().contains(vocabularyWord)) {
				numberOfMatches++;
			}
		}

		return numberOfMatches;
	}

	/**
	 * Method that creates vector representations of documents by multiplying
	 * their if and idf values.
	 */
	private static void createDocumentRepresentations() {
		for (Entry<Path, Map<String, Integer>> document : documentWords.entrySet()) {
			Path path = document.getKey();
			Map<String, Integer> words = document.getValue();

			List<Double> vector = new ArrayList<Double>(vocabulary.size());
			Set<String> wordsSet = words.keySet();

			vocabulary.forEach(vocabularyWord -> {
				if (wordsSet.contains(vocabularyWord)) {
					vector.add(idfValues.get(vocabularyWord) * words.get(vocabularyWord));
				} else {
					vector.add(0.0);
				}
			});

			documentVectors.put(path, vector);
		}

	}

	/**
	 * Method that receives query vector and creates list of results.
	 * 
	 * @param vector
	 *            vector that represents querys
	 */
	public static void setResults(List<Double> vector) {
		results.clear();

		documentVectors.forEach((path, documentVector) -> {
			Double similarity = calculateSimilarity(vector, documentVector);
			if (similarity > 0) {
				results.add(new Result(similarity, path));
			}

		});

		results.sort(null);
	}

	/**
	 * Calculates similarity of two vectors. (cosine similarity)
	 * 
	 * @param vector
	 *            query vector
	 * @param documentVector
	 *            document vector
	 * @return calculated similarity
	 */
	private static Double calculateSimilarity(List<Double> vector, List<Double> documentVector) {
		Double similarity = 0.0;
		Double vectorNorm1 = 0.0;
		Double vectorNorm2 = 0.0;

		for (int i = 0; i < vector.size(); i++) {
			similarity = similarity + (vector.get(i) * documentVector.get(i));
			vectorNorm1 = vectorNorm1 + vector.get(i) * vector.get(i);
			vectorNorm2 = vectorNorm2 + documentVector.get(i) * documentVector.get(i);
		}

		if (similarity == 0.0) {
			return 0.0;
		}

		vectorNorm1 = Math.sqrt(vectorNorm1);
		vectorNorm2 = Math.sqrt(vectorNorm2);

		similarity = similarity / (vectorNorm1 * vectorNorm2);

		return similarity;
	}

	/**
	 * Method that displays top 10 results, or les if there are not 10 of them.
	 */
	public static void displayResults() {
		if (results.size() == 0) {
			System.out.println("no relevant documents.");
			return;
		}

		System.out.println("Best 10 results:");
		for (int i = 0; i < results.size(); i++) {
			if (i == 10) {
				return;
			}

			System.out.printf("[%2d] (%.4f) %s %n", i, results.get(i).getSimilarity(),
					results.get(i).getFilePath().toString());
		}
	}

}
