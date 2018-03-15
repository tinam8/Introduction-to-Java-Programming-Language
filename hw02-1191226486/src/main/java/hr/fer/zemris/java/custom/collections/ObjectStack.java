package hr.fer.zemris.java.custom.collections;

/**
 * Class that represents stack-like collection. have methods such as push, pop
 * and peek, and not insert, add etc.
 * 
 * @author tina
 *
 */
public class ObjectStack {

	/**
	 * {@link ArrayIndexedCollection} instance for actual element storage.
	 */
	private ArrayIndexedCollection storage;

	{
		storage = new ArrayIndexedCollection();
	}

	/**
	 * Method which determines wheter stack contains any objects.
	 * 
	 * @return true if stack contains no objects and false otherwise; boolean
	 */
	public boolean isEmpty() {
		return storage.isEmpty();
	}

	/**
	 * Method which returns the number of currently stored objects in stack.
	 * 
	 * @return number of elements in stack
	 */
	public int size() {
		return storage.size();
	}

	/**
	 * Method removes last value pushed on stack from stack and returns it. If
	 * the stack is empty when method is called, the method throws
	 * EmptyStackException.
	 * 
	 * @return last value pushed on stack
	 */
	public Object pop() {
		if (storage.isEmpty()) {
			throw new EmptyStackException("Can not pop from empty stack!");
		}

		Object object = storage.get(storage.size() - 1);
		storage.remove(storage.size() - 1);
		return object;
	}

	/**
	 * Pushes given value on the stack. null value is not allowed to be placed
	 * on stack.
	 * 
	 * @param value
	 *            object to be placed on top of the stack
	 */
	public void push(Object value) {
		storage.add(value);
	}

	/**
	 * Returns last element placed on stack but does not delete it from stack.
	 * If the stack is empty when method is called, the method throws
	 * EmptyStackException.
	 * 
	 * @return last element placed on stack
	 */
	public Object peek() {
		return storage.get(storage.size() - 1);
	}

	/**
	 * Removes all elements from stack.
	 */
	public void clear() {
		storage.clear();
	}

}
