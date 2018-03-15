package hr.fer.zemris.java.hw05.observer2;

/**
 * Instances of IntegerStorageChange class encapsulate following information:
 * <br>
 * (a) a reference to IntegerStorage (Subject)<br>
 * (b) the value of stored integer before the change has occurred<br>
 * (c) the new value of currently stored integer
 * 
 * @author tina
 *
 */
public class IntegerStorageChange {
	/** a reference to IntegerStorage */
	private IntegerStorage subject;
	/** the value of stored integer before the change has occurred */
	private int previousValue;
	/** the new value of stored integer */
	private int newValue;

	/**
	 * Constructor with three arguments
	 * 
	 * @param subject
	 *            refrence to instance of IntegerStorage
	 * @param previousValue
	 *            value stored in subject before change
	 * @param newValue
	 *            new value to be stored
	 */
	public IntegerStorageChange(IntegerStorage subject, int previousValue, int newValue) {
		super();
		this.subject = subject;
		this.previousValue = previousValue;
		this.newValue = newValue;
	}

	/**
	 * Gets instance of IntegerStorage - instance of subject
	 * 
	 * @return refrence to IntegerStorage instance
	 */
	public IntegerStorage getSubject() {
		return subject;
	}

	/**
	 * Gets value that was stored in subject before change
	 * 
	 * @return previous value
	 */
	public int getPreviousValue() {
		return previousValue;
	}

	/**
	 * Gets value that is stored in subject after change
	 * 
	 * @return new value
	 */
	public int getNewValue() {
		return newValue;
	}
}
