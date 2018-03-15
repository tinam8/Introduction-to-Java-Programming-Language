package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that allow the user to store multiple values for same key and provides
 * a stack-like abstraction.
 * 
 * @author tina
 *
 */
public class ObjectMultistack {
	/** Map that associates strings with stacks. */
	Map<String, MultistackEntry> map = new HashMap<>();

	/**
	 * Class that acts as a node of a single-linked list
	 * 
	 * @author tina
	 *
	 */
	private static class MultistackEntry {
		/** pointer to next list MultistackEntry node */
		MultistackEntry next;
		/** reference for node value */
		ValueWrapper value;

		/**
		 * Constructor with two arguments
		 * 
		 * @param value
		 *            value of entry
		 * @param next
		 *            reference to the next entry
		 */
		public MultistackEntry(ValueWrapper value, MultistackEntry next) {
			this.value = value;
			this.next = next;
		}

		/**
		 * Gets value stored in instance of {@link MultistackEntry}
		 * 
		 * @return value; {@code ValueWrapper}
		 */
		public ValueWrapper getValue() {
			return value;
		}

		/**
		 * gets refrence to the next instance of {@link MultistackEntry}
		 * 
		 * @return next; {@code MultistackEntry}
		 */
		public MultistackEntry getNext() {
			return next;
		}
	}

	/**
	 * Pushes given instance of {@link ValueWrapper} to the correct multistack
	 * (which is value of the map) based on given name (which is key of the
	 * map).
	 * 
	 * @param name
	 *            key value of the map
	 * @param valueWrapper
	 *            value to be added to the map
	 * @throws IllegalArgumentException
	 *             if key or value is null
	 */
	public void push(String name, ValueWrapper valueWrapper) {
		if (valueWrapper == null) {
			throw new IllegalArgumentException("Can not add null refrence to the multistack.");
		}

		if (name == null) {
			throw new IllegalArgumentException("Key ca not be a null refrence.");
		}

		if (!map.containsKey(name) || map.get(name) == null) {
			map.put(name, new MultistackEntry(valueWrapper, null));
			return;
		}

		MultistackEntry entry = map.get(name);
		map.put(name, new MultistackEntry(valueWrapper, entry));
	}

	/**
	 * Method finds stack by key given as argument and removes last value pushed
	 * on stack from stack and returns it.
	 * 
	 * @param name
	 *            key value of stack to pop value from
	 * @return last value pushed on stack
	 * @throws EmptyStackException
	 *             if the stack is empty when method is called
	 */
	public ValueWrapper pop(String name) {
		if (!map.containsKey(name) || map.get(name) == null) {
			throw new EmptyStackException("Can not pop from empty stack.");
		}

		MultistackEntry lastEntry = map.get(name);
		map.put(name, lastEntry.getNext());

		return lastEntry.getValue();

	}

	/**
	 * Method finds stack by key given as argument and returns last element
	 * placed on stack but does not delete it from stack.
	 * 
	 * @param name
	 *            key value of stack to peek
	 * @return returns last element placed on stack
	 */
	public ValueWrapper peek(String name) {
		if (!map.containsKey(name) || map.get(name) == null) {
			throw new EmptyStackException("Can not pop from empty stack.");
		}

		return map.get(name).getValue();
	}

	/**
	 * Method finds stack by key given as argument and determines whether it is
	 * empty or not.
	 * 
	 * @param name
	 *            key value of stack to peek
	 * @return true if stack is empty, otherwise false;
	 */
	public boolean isEmpty(String name) {
		return map.get(name) == null;
	}
}
