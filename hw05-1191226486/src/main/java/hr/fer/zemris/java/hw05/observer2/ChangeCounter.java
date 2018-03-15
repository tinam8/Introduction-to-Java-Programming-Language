package hr.fer.zemris.java.hw05.observer2;

/**
 * Class that represents concrete observer. <br>
 * Counts (and writes to the standard output) the number of times the value
 * stored has been changed since the registration.
 * 
 * @author tina
 *
 */
public class ChangeCounter implements IntegerStorageObserver {
	/**
	 * Variable for counting how many times the stored value has changed in
	 * registed subject.
	 */
	private int changeCount;

	{
		changeCount = 0;
	}

	@Override
	public void valueChanged(IntegerStorageChange istorageChange) {
		changeCount++;
		System.out.println("Number of value changes since tracking: " + changeCount);
	}
}
