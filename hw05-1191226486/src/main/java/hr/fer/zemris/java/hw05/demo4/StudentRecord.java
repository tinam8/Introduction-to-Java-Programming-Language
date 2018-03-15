package hr.fer.zemris.java.hw05.demo4;

/**
 * Instances of this class will represent records for each student.
 * 
 * @author tina
 *
 */
public class StudentRecord {

	/** Student jmbag. */
	private String jmbag;
	/** Student last name. */
	private String lastName;
	/** Student first name. */
	private String firstName;
	/** Student points at mid exam. */
	private double midPoints;
	/** Student points at final exam. */
	private double finalPoints;
	/** Student points at laboratory exams. */
	private double labPoints;
	/** Student final grade. */
	private int finalGrade;

	/**
	 * Constructor.
	 * 
	 * @param jmbag
	 *            Jmbag of student
	 * @param lastName
	 *            Last name
	 * @param firstName
	 *            First name
	 * @param midPoints
	 *            Points from mid exam
	 * @param finalPoints
	 *            Points from final exam
	 * @param labPoints
	 *            points from laboratory exams
	 * @param finalGrade
	 *            final grade
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, double midPoints, double finalPoints,
			double labPoints, int finalGrade) {
		super();
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.midPoints = midPoints;
		this.finalPoints = finalPoints;
		this.labPoints = labPoints;
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

	/**
	 * Gets last name of a student.
	 * 
	 * @return last name
	 */
	public String getLastName() {
		return lastName;
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
	 * Gets points from mid exam of a student
	 * 
	 * @return points from mid exam
	 */
	public double getMidPoints() {
		return midPoints;
	}

	/**
	 * Gets points from final exam of a student
	 * 
	 * @return points from final exam
	 */
	public double getFinalPoints() {
		return finalPoints;
	}

	/**
	 * Gets points from laboratory exams of a student
	 * 
	 * @return points from laboratory exams
	 */
	public double getLabPoints() {
		return labPoints;
	}

	/**
	 * Returns final grade of a student.
	 * 
	 * @return final grade
	 */
	public int getFinalGrade() {
		return finalGrade;
	}

	@Override
	public String toString() {
		return String.format("%s\t%s\t%s\t%f\t%f\t%f\t%d", jmbag, lastName, firstName, midPoints, finalPoints,
				labPoints, finalGrade);
	}

}
