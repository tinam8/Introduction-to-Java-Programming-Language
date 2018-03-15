package hr.fer.zemris.java.hw04.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Simple database emulator. <br>
 * Program reads the data from current directory from file database.txt.. <br>
 * User inputs querys, if query is invalid, appropriate message is written. <br>
 * User is allowed to write spaces/tabs.<br>
 * The program terminates when user enters the exit command.
 * 
 * @author tina
 *
 */
public class StudentDB {
	/**
	 * Length of jmbag used for printing table
	 */
	private static final int JMBAG_LENGTH = 10;

	/**
	 * Length of jmbag used for printing table
	 */
	private static final int GRADE_LENGTH = 1;

	/**
	 * Characters used for separating attributes in table
	 */
	private static final String SEPARATOR = "|";

	/**
	 * Characters used for edges of table
	 */
	private static final String EDGE = "=";

	/**
	 * Characters used for separating columns in edges of table
	 */
	private static final String EDGE_SEPARATOR = "+";

	/**
	 * Method that starts program.
	 * 
	 * @param args
	 *            arguments
	 */
	public static void main(String[] args) {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}

		Scanner sc = new Scanner(System.in);
		StudentDatabase database = null;
		
		try {
			database = new StudentDatabase(lines);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(-1);
		}

		while (true) {
			System.out.printf("> ");

			String query = "";

			if (sc.hasNext()) {
				query = sc.nextLine();
				if (query.equals("exit")) {
					System.out.println("Goodbye!");
					System.exit(0);
				}

				QueryParser parser = null;

				try {
					parser = new QueryParser(query);
				} catch (Exception e) {
					System.out.println("Error in query " + e.getMessage());
					continue;
				}

				if (parser.isDirectQuery()) {
					StudentRecord r = database.forJMBAG(parser.getQueriedJMBAG());
					System.out.println("Using index for record retrieval.");
					if (r == null) {
						System.out.println("Records selected: 0.");
					} else {
						List<StudentRecord> list = new ArrayList<>();
						list.add(r);
						printSelected(list);
						System.out.println("Records selected: 1.");
					}
				} else {
					List<StudentRecord> records = database.filter(new QueryFilter(parser.getQuery()));
					if(records.size() > 0) {
						printSelected(records);
					}
					System.out.println("Records selected: " + records.size() + " ");
				}

			}
		}

	}

	/**
	 * Method that prints list of rstudent records. Foe example:
	 * +============+========+========+===+ 
	 * | 0000000003 | Bosnić | Andrea | 4 |
	 * +============+========+========+===+
	 * 
	 * @param records
	 *            list of student records.
	 */
	private static void printSelected(List<StudentRecord> records) {
		int firstNameMax = 0;
		int lastNameMax = 0;

		for (StudentRecord record : records) {
			int firstNameTemp = record.getFirstName().length();
			int lastNameTemp = record.getLastName().length();

			if (firstNameTemp > firstNameMax) {
				firstNameMax = firstNameTemp;
			}
			if (lastNameTemp > lastNameMax) {
				lastNameMax = lastNameTemp;
			}
		}

		printBorder(firstNameMax, lastNameMax);

		for (StudentRecord record : records) {
			printRecord(record, firstNameMax, lastNameMax);
		}

		printBorder(firstNameMax, lastNameMax);

	}

	/**
	 * Supplementary method that prints bottom and top edge of the table.
	 * 
	 * @param lastNameLength
	 *            lenght of third column
	 * @param firstNameLength
	 *            lenght of second column
	 */
	private static void printBorder(int firstNameLength, int lastNameLength) {
		StringBuilder sb = new StringBuilder();

		sb.append(EDGE_SEPARATOR);
		appendEdgeSection(sb, JMBAG_LENGTH + 2);
		sb.append(EDGE_SEPARATOR);
		appendEdgeSection(sb, lastNameLength + 2);
		sb.append(EDGE_SEPARATOR);
		appendEdgeSection(sb, firstNameLength + 2);
		sb.append(EDGE_SEPARATOR);
		appendEdgeSection(sb, GRADE_LENGTH + 2);
		sb.append(EDGE_SEPARATOR);

		System.out.println(sb.toString());

	}

	/**
	 * Supplemetary method that adds edge of one collumn of the table
	 * 
	 * @param sb
	 *            instance of StringBuilder for appendgin
	 * @param lenght
	 *            of edge section
	 */
	private static void appendEdgeSection(StringBuilder sb, int lenght) {
		for (int i = 0; i < lenght; i++) {
			sb.append(EDGE);
		}
	}

	/**
	 * Supplementary method that prints one row of the table table.
	 * 
	 * 
	 * @param lastNameLength
	 *            lenght of third column
	 * @param firstNameLength
	 *            lenght of second column
	 * @param record
	 *            recort to print in row
	 */
	private static void printRecord(StudentRecord record, int firstNameLength, int lastNameLength) {
		StringBuilder sb = new StringBuilder();

		sb.append(SEPARATOR);
		sb.append(" " + record.getJmbag() + " ");

		appendAdjustableTableBox(sb, record.getLastName(), lastNameLength);

		appendAdjustableTableBox(sb, record.getFirstName(), firstNameLength);

		sb.append(SEPARATOR);
		sb.append(" " + record.getFinalGrade() + " " + SEPARATOR);

		System.out.println(sb.toString());

	}

	/**
	 * Supplemetary method that adds edge of one collumn of the table
	 * 
	 * @param sb
	 *            instance of StringBuilder for appendgin
	 * 
	 * @param content
	 *            content of the box
	 * 
	 * @param length
	 *           length of box 
	 */
	private static void appendAdjustableTableBox(StringBuilder sb, String content, int length) {
		sb.append(SEPARATOR);
		sb.append(" " + content);
		for (int i = content.length(); i < length + 1; i++) {
			sb.append(' ');
		}

	}
}
