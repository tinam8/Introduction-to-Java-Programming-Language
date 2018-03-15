package hr.fer.zemris.java.hw04.db;

/**
 * Class containing different ways for getting different types of attributes.
 * 
 * @author tina
 *
 */
public class FieldValueGetters {
	/**
	 * Returns first name.
	 */
	public static final IFieldValueGetter FIRST_NAME = (record) -> {
		checkRecord(record);
		return record.getFirstName();
	};
	/**
	 * Returns first name.
	 */
	public static final IFieldValueGetter LAST_NAME = (record) -> {
		checkRecord(record);
		return record.getLastName();
	};
	/**
	 * Returns first name.
	 */
	public static final IFieldValueGetter JMBAG = (record) -> {
		checkRecord(record);
		return record.getJmbag();
	};
	
	/**
	 * Private method that throws {@link IllegalArgumentException} if recor is null
	 * @param record record for checking
	 * @throws IllegalArgumentException if argument is null
	 */
	private static void checkRecord(StudentRecord record) {
		if (record == null) {
			throw new IllegalArgumentException("Record can not be null.");
		}
	}

}
