package hr.fer.zemris.java.hw05.observer2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The Subject class in Observer pattern. Holds one integer value and notifies
 * subjects when it changes.
 * 
 * @author tina
 *
 */
public class IntegerStorage {
	/** Stored value */
	private int value;

	/** List of registered obserers */
	private List<IntegerStorageObserver> observers;

	/**
	 * Constructor wuth one argument.
	 * 
	 * @param initialValue
	 *            initial value to be stored
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
	}

	/**
	 * Method that adds obesever to the list of registered observers if it not
	 * already there.
	 * 
	 * @param observer
	 *            instance of observer to be registered
	 */
	public void addObserver(IntegerStorageObserver observer) {
		// add the observer in observers if not already there ...
		if (observer == null) {
			return;
		}

		if (observers == null) {
			observers = new ArrayList<>();
		}

		if (!observers.contains(observer)) {
			observers.add(observer);
		}

	}

	/**
	 * Method that unregisters observer if it is already registered.
	 * 
	 * @param observer
	 *            to remove from list of registered objects.
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		if (observers == null) {
			return;
		}

		observers.remove(observer);
	}

	/**
	 * Unregisters all observeres that are registered.
	 */
	public void clearObservers() {
		if (observers == null) {
			return;
		}

		observers.clear();
	}

	/**
	 * Redurns currently stored integer value.
	 * 
	 * @return stored value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Modifies the object’s state only if new value is different than the
	 * current value.<br>
	 * Notifies all registered observers if value has been changed.
	 * 
	 * @param value
	 *            value to be stored in
	 */
	public void setValue(int value) {
		if (this.value == value) {
			return;
		}

		// Notify all registered observers
		if (observers != null) {
			Iterator<IntegerStorageObserver> iter = observers.iterator();
			IntegerStorageObserver observer;
			IntegerStorageChange storageChange = new IntegerStorageChange(this, this.value, value);
			
			while (iter.hasNext()) {
				observer = iter.next();
				observer.valueChanged(storageChange);

				if (observer instanceof DoubleValue) {
					if (((DoubleValue) observer).getLimit() <= 0) {
						iter.remove();
					}
				}
			}
		}
		
		this.value = value;
	}
}
