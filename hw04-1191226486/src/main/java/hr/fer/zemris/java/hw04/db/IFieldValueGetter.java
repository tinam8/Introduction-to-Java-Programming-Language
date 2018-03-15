package hr.fer.zemris.java.hw04.db;

/**
 * Intergace responsible for obtaining a requested field value from given
 * StudentRecord.
 * 
 * @author tina
 *
 */
public interface IFieldValueGetter {
	/**
	 * Method that returns value from given StudentRecord.
	 * 
	 * @param record
	 *            student record
	 * @return value of one of the attributes from student record
	 */
	public String get(StudentRecord record);
}
