package hr.fer.zemris.java.hw05.observer1;

/**
 * The Observer interface in Observer pattern.<br>
 * Has one method valueChanged that performs some action when observer is
 * notified.
 * 
 * @author tina
 *
 */
public interface IntegerStorageObserver {

	/**
	 * Method that is called each time when a state in subject (to which
	 * instance that implements this interface is registered toF) is modified,
	 * 
	 * @param istorage
	 *            reference to subject that is calling this method
	 */
	public void valueChanged(IntegerStorage istorage);
}
