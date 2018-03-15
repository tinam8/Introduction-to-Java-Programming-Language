package hr.fer.zemris.java.hw05.observer1;

/**
 * Class that represents concrete observer. <br>
 * Instances of DoubleValue class write to the standard output double value
 * (i.e. “value * 2”) of the current value which is stored in subject but only
 * first n times since its registration with the subject <br>
 * (n is given in constructor); <br>
 * after writing the double value for the n-th time, the observer automatically
 * de-registers from the subject
 * 
 * @author tina
 *
 */
public class DoubleValue implements IntegerStorageObserver {
	/**
	 * Stores how many more times instance of object can write to the standard
	 * output.
	 */
	private int limit;

	/**
	 * Constructor with one parameter.
	 * 
	 * @param limit
	 *            how many times observer can write to standard output.
	 */
	public DoubleValue(int limit) {
		super();
		this.limit = limit;
	}

	/**
	 * Returns how many times observer can write to standard output.
	 * 
	 * @return number of times left
	 */
	public int getLimit() {
		return limit;
	}

	@Override
	public void valueChanged(IntegerStorage istorage) {
		if (limit < 1) {
			istorage.removeObserver(this);
			return;
		}

		System.out.println("Double value: " + istorage.getValue() * 2);
		limit--;
	}

}
