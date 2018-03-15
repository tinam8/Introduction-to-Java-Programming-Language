package hr.fer.zemris.java.hw04.db;

/**
 * Instances of this class will represent records for each student.
 * 
 * @author tina
 *
 */
public class StudentRecord {
	/**
	 * Student jmbag.
	 */
	private String jmbag;
	/**
	 * Student last name.
	 */
	private String lastName;
	/**
	 * Student first name.
	 */
	private String firstName;
	/**
	 * Student final grade;
	 */
	private String finalGrade;
	/**
	 * Regular expression that JMBAG has to match.
	 */
	public static final String JMBAG_REG = "[0-9]{10}";
	/**
	 * Regular expression that first name has to match.
	 */
	public static final String FIRST_NAME_REG = "([A-ZČĆĐŽŠ]{1}[a-zčćžšđ]+[- ]{0,1})+";
	/**
	 * Regular expression that last name has to match.
	 */
	public static final String LAST_NAME_REG = "([A-ZČĆĐŽŠ]{1}[a-zčćžšđ]+[- ]{0,1})+";
	/**
	 * Regular expression that grade has to match.
	 */
	public static final String FINAL_GRADE_REG = "[1-5]{1}";

	/**
	 * Constructors taht creates new instance with given students record
	 * attributes.
	 * 
	 * @param jmbag
	 *            jmbag
	 * @param lastName
	 *            last name
	 * @param firstName
	 *            first name
	 * @param finalGrade
	 *            final grade
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, String finalGrade) {
		super();
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}

	/**
	 * Gets jmbag of a student.
	 * 
	 * @return jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}

	/**
	 * Gets first name of a student.
	 * 
	 * @return first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Gets last name of a student.
	 * 
	 * @return last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Returns final garde of a student.
	 * 
	 * @return final grade
	 */
	public String getFinalGrade() {
		return finalGrade;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(jmbag + "\t" + lastName + "\t" + firstName + "\t" + finalGrade);
		
		return sb.toString();
	}

}
