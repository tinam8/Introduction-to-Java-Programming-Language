package hr.fer.zemris.java.hw05.demo4;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Program that is solving multiple queries on list of {@link StudentRecord},
 * using stream API.
 * 
 * @author tina
 *
 */
public class StudentDemo {
	/* Method that starts program */
	/**
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		List<String> lines = null;

		try {
			lines = Files.readAllLines(Paths.get("./studenti.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}

		List<StudentRecord> records = convert(lines);

		System.out.println("Broj studenata sa bodovima vise od 25 bodova: " + vratiBodovaViseOd25(records));
		System.out.println("Broj studenata sa ocjenom odlican: " + vratiBrojOdlikasa(records));

		System.out.println("Studenti odlikasi");
		List<StudentRecord> filteredRecords = odlikasi(records);
		filteredRecords.forEach(System.out::println);

		System.out.println("Odlikasi sortirano");
		filteredRecords = odlikasiSortirano(records);
		filteredRecords.forEach(System.out::println);

		System.out.println("Jmbagovi studenata koji nisu polozili");
		List<String> faildJMBAG = nepolozeniJMBAGovi(records);
		faildJMBAG.forEach(System.out::println);

		System.out.println("Mapa po ocjenama1");
		System.out.println("Studenti koji imaju ocjenu 4");
		Map<Integer, List<StudentRecord>> mapGrades = mapaPoOcjenama(records);
		List<StudentRecord>  students = mapGrades.get(4);
		students.forEach(System.out::println);
		
		System.out.println("Mapa po ocjenama2");
		Map<Integer, Integer> mapGrades2 = mapaPoOcjenama2(records);
		for(Map.Entry<Integer, Integer> entry : mapGrades2.entrySet()){
			System.out.printf("%d -> %d%n",entry.getKey(), entry.getValue());
		}
		
		System.out.println("Mapa sa listom studenata koji su prosli i pali");
		Map<Boolean, List<StudentRecord>> mapGrades3 = prolazNeprolaz(records);
		System.out.println("Prosli");
		students = mapGrades3.get(true);
		students.forEach(System.out::println);
	}

	/**
	 * Returns number of students whose sum of poins from mid exam, final exam
	 * and laboratory exams is > 25
	 * 
	 * @param records
	 *            list of student records
	 * @return number of students
	 */
	public static long vratiBodovaViseOd25(List<StudentRecord> records) {
		return records.stream().filter(s -> (s.getMidPoints() + s.getFinalPoints() + s.getLabPoints()) > 25)
				.collect(Collectors.counting());
	}

	/**
	 * Returns number of students that have final grade equal to 5.
	 * 
	 * @param records
	 *            list of student records
	 * @return number of students
	 * 
	 */
	public static long vratiBrojOdlikasa(List<StudentRecord> records) {
		return records.stream().filter(s -> s.getFinalGrade() == 5).collect(Collectors.counting());
	}

	/**
	 * Returns list of student records of students that have final grade equal
	 * to 5.
	 * 
	 * @param records
	 *            list of student records
	 * @return filtered student records
	 */
	public static List<StudentRecord> odlikasi(List<StudentRecord> records) {
		return records.stream().filter(s -> s.getFinalGrade() == 5).collect(Collectors.toList());
	}

	/**
	 * Method that returns list of student records of students that have final
	 * grade equal to 5, sorted by sum of points from exams.
	 * 
	 * @param records
	 *            list of student records
	 * @return sorted filtered list of student records
	 */
	public static List<StudentRecord> odlikasiSortirano(List<StudentRecord> records) {
		return records.stream().filter(s -> s.getFinalGrade() == 5)
				.sorted((s1, s2) -> Double.compare(s2.getMidPoints() + s2.getFinalPoints() + s2.getLabPoints(),
						s1.getMidPoints() + s1.getFinalPoints() + s1.getLabPoints()))
				.collect(Collectors.toList());
	}

	/**
	 * Returns list of student jmbags that failed.
	 * 
	 * @param records
	 *            list of student records
	 * @return sorted list of student that failed
	 */
	public static List<String> nepolozeniJMBAGovi(List<StudentRecord> records) {
		return records.stream().filter(s -> s.getFinalGrade() == 1).map(s -> s.getJmbag())
				.sorted((s1, s2) -> s1.compareTo(s2)).collect(Collectors.toList());
	}

	/**
	 * Returns map that has final grade as a key and list of students with that
	 * grade as value.
	 * 
	 * @param records
	 *            list of student records
	 * @return map with grades and list of students
	 */
	public static Map<Integer, List<StudentRecord>> mapaPoOcjenama(List<StudentRecord> records) {
		return records.stream().collect(Collectors.groupingBy(StudentRecord::getFinalGrade));
	}

	/**
	 * Returns map that has final grade as key and number of students that have
	 * that grade as value.
	 * 
	 * @param records
	 *            list of student records
	 * @return map with grades and number of student that have that grade
	 */
	public static Map<Integer, Integer> mapaPoOcjenama2(List<StudentRecord> records) {
		return records.stream().collect(Collectors.toMap(StudentRecord::getFinalGrade, s -> 1, (s1, s2) -> s1 + s2));
	}

	/**
	 * Returns map that has true/false as keys and list of students that have
	 * passed/failed as values.
	 * 
	 * @param records
	 *            list of student records
	 * @return map with list of students that have failed and list of students
	 *         that have passed
	 */
	public static Map<Boolean, List<StudentRecord>> prolazNeprolaz(List<StudentRecord> records) {
		return records.stream().collect(Collectors.partitioningBy(s -> s.getFinalGrade() > 1));
	}

	/**
	 * Method that accepts list of strings that represent database and converts
	 * that list into list of student records.
	 * 
	 * 
	 * @param lines
	 *            list of strings
	 * @return list of student records
	 * @throws IllegalArgumentException
	 *             if some input in file is invalid.
	 */
	public static List<StudentRecord> convert(List<String> lines) {
		List<StudentRecord> records = new ArrayList<StudentRecord>();
		for (String line : lines) {
			if (line == null) {
				throw new IllegalArgumentException("Row can not be null.");
			}

			line.trim();
			// skipping empty rows
			if (line.equals("")) {
				continue;
			}

			String[] parts = line.split("\t");

			if (parts.length != 7) {
				throw new IllegalArgumentException("Invalid input in file.");
			}
			if (records.contains(parts[0])) {
				throw new IllegalArgumentException(
						"Invalid input in database for " + line + " ->  jmbag already exists.");
			}

			StudentRecord student = null;
				student = new StudentRecord(parts[0], parts[1], parts[2], Double.parseDouble(parts[3]),
						Double.parseDouble(parts[4]), Double.parseDouble(parts[5]), Integer.parseInt(parts[6]));

			records.add(student);
		}

		return records;
	}

}
