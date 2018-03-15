package hr.fer.zemris.java.hw04.db;

/**
 * Interface ysed for filtering based on specific filter.
 * 
 * @author tina
 *
 */
public interface IFilter {
	/**
	 * Method that checks if student record is acceptable based on a filter.
	 * 
	 * @param record
	 *            record of a student to be checked
	 * @return true if record is acceptable baed on filter; false otherwise
	 */
	public boolean accepts(StudentRecord record);
}
