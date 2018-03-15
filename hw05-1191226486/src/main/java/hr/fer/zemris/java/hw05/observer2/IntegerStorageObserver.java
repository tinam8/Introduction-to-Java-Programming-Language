package hr.fer.zemris.java.hw05.observer2;

/**
 * The Observer interface in Observer pattern.<br>
 * Has one method valueChanged that performes some action when observer is
 * notified.
 * 
 * @author tina
 *
 */
public interface IntegerStorageObserver {

	/**
	 * Method that is called each time when a state in subject (to which
	 * instance that implements this interface is registered to) is modified,
	 * 
	 * @param istorageChange
	 *            reference to instance of IntegerStorageChange
	 */
	public void valueChanged(IntegerStorageChange istorageChange);
}
