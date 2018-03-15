package hr.fer.zemris.java.hw04.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw04.collections.SimpleHashtable;
import hr.fer.zemris.java.hw04.collections.SimpleHashtable.TableEntry;

/**
 * Database that contains students records.
 * 
 * @author tina
 *
 */
public class StudentDatabase  {
	/**
	 * Hash table that holds student records from database.<br>
	 * Key = jmbag, value = instance of StudentRecord class
	 */
	private SimpleHashtable<String, StudentRecord> database;
	/**
	 * List that holds all student records.
	 */
	private List<StudentRecord> recordsList;

	/**
	 * Constructor taht accepts list of strings that represent database.
	 * 
	 * @param database
	 *            list of strings
	 */
	public StudentDatabase(List<String> database) {
		this.database = new SimpleHashtable<String, StudentRecord>();
		recordsList = new ArrayList<StudentRecord>();
		
		for (String databaseRow : database) {
			addDatabaseRow(databaseRow);
		}
	}

	/**
	 * Supplementary private method that extraxts student record atrributes from
	 * string and adds it to the hash table and to the list of student records..
	 * 
	 * @param databaseRow
	 *            string that represents student record
	 * @throws IllegalArgumentException
	 *             if some student record attribute is invalid
	 */
	private void addDatabaseRow(String databaseRow) {
		if (databaseRow == null) {
			throw new IllegalArgumentException("Row can not be null.");
		}

		databaseRow.trim();
		String[] parts = databaseRow.split("\t");

		if (parts.length != 4) {
			throw new IllegalArgumentException("Invalid input in database for " + databaseRow);
		}
		if (database.get(parts[0]) != null) {
			throw new IllegalArgumentException("Invalid input in database for " + databaseRow + " ->  jmbag already exists.");
		}

		if (!parts[0].matches(StudentRecord.JMBAG_REG)) {
			throw new IllegalArgumentException("Invalid JMBAG in database for " + databaseRow);
		}
		if (!parts[1].matches(StudentRecord.LAST_NAME_REG)) {
			throw new IllegalArgumentException("Invalid last name in database for " + databaseRow);
		}
		if (!parts[2].matches(StudentRecord.FIRST_NAME_REG)) {
			throw new IllegalArgumentException("Invalid first name in database for " + databaseRow);
		}
		if (!parts[3].matches(StudentRecord.FINAL_GRADE_REG)) {
			throw new IllegalArgumentException("Invalid final grade in database for " + databaseRow);
		}

		StudentRecord student = new StudentRecord(parts[0], parts[1], parts[2], parts[3]);
		database.put(parts[0], student);
		recordsList.add(student);
		
	}

	/**
	 * Method that gets student record based on JMBAG
	 * 
	 * @param jmbag
	 *            student jmbag
	 * @return instance of StudentRecord, if record does not exists, the method
	 *         returns null.
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return database.get(jmbag);
	}

	/**
	 * Method loops through all student records in its internal list. <br>
	 * It calls accepts method on given filter-object with current record <br>
	 * Each record for which accepts returns true is added to temporary list and
	 * this list is then returned by the filter method.
	 * 
	 * @param filter
	 *            filter-object with current record
	 * @return list of records that satisfie the given filter
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> listFiltered = new ArrayList<>();
		
		for(TableEntry<String, StudentRecord> studentRecord : database) {
			StudentRecord record = studentRecord.getValue();
			
			if(filter.accepts(record)) {
				listFiltered.add(record);
			}
		}
		
		return listFiltered;
	}


}
