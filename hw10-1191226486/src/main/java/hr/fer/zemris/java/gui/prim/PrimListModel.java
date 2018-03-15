package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Class that implements model that has method for generating prime numbers
 * numbers.
 * 
 * @author tina
 *
 */
public class PrimListModel implements ListModel<Integer> {
	/** list that holds generated numbers */
	private List<Integer> data;
	/** list that holds all observers */
	private List<ListDataListener> observers;
	/** temporary list of data listeners (observers) that is used for notifying */
	private List<ListDataListener> temp;
	/**
	 * Constructor that creates data and adds 1 to it.
	 */
	public PrimListModel() {
		data = new ArrayList<>();
		data.add(1);
		observers = new ArrayList<>();
		temp = new ArrayList<>();
	}
	
	
	@Override
	public int getSize() {
		return data.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return data.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		observers.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		observers.remove(l);
	}
	
	/**
	 * Method that generates next prime number.
	 */
	public void next() {
		data.add(getPrime(data.get(data.size()-1)));
		
		temp.clear();;
		for(ListDataListener listener : observers) {
			temp.add(listener);
		}
		
		
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, data.size()-1, data.size() -1);
		for (ListDataListener l : temp) {
			l.intervalAdded(event);
		}
	}
	

	/**
	 * Private method that returns first prime number greater from number
	 * given as argument.
	 * 
	 * @param startingNumber
	 *            prime number must be greater than this value
	 * @return first prime number after given value
	 */
	private static int getPrime(int startingNumber) {
		int nextPrime = startingNumber + 1;
		while (!isPrime(nextPrime)) {
			nextPrime++;
		}

		return nextPrime;
	}

	/**
	 * Determines whether given value is prime number or not
	 * 
	 * @param number
	 *            to be checked
	 * @return true if number is prime, otherwise false
	 */
	private static boolean isPrime(int number) {
		for (int i = 2; i <= Math.sqrt(number); i++) {
			if (number % i == 0) {
				return false;
			}
		}

		return true;
	}

}
