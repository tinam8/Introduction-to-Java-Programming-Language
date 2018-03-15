package hr.fer.zemris.java.custom.collections;

/**
 * Class which represents resizable array-backed collection of objects
 * 
 * @author Tina Maric
 *
 */
public class ArrayIndexedCollection extends Collection {

	/**
	 * current size of collection (number of elements actually stored)
	 */
	private int size;
	/**
	 * current capacity of allocated array of object references
	 */
	private int capacity;
	/**
	 * an array of object references which length is determined by capacity
	 * variable
	 */
	private Object[] elements;

	/**
	 * Constructor that creates an instance with given capacity
	 * 
	 * @param initialCapacity
	 *            capacity of object that is being created
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException("Capacity has to be grater than 0.");
		}
		capacity = initialCapacity;
		elements = new Object[initialCapacity];
		size = 0;
	}

	/**
	 * The default constructor. <br>
	 * Default capacity is 16.
	 */
	public ArrayIndexedCollection() {
		this(16);
	}

	/**
	 * Constructor that creates an instance with elements from other collection.
	 * 
	 * @param initialCapacity
	 *            capacity of a new instance
	 * @param other
	 *            some other Collection instance which elements are copied into
	 *            this newly constructed collection
	 * 
	 * @throws IllegalArgumentException
	 *             if capacity is invalid
	 */
	public ArrayIndexedCollection(int initialCapacity, Collection other) {
		if (initialCapacity < other.size()) {
			throw new IllegalArgumentException("Initial capacity is less" + " than a size of a given collection.");
		}

		if (other.isEmpty()) {
			if (initialCapacity < 1) {
				throw new IllegalArgumentException("Capacity has to be grater than 0.");
			}
		}

		capacity = initialCapacity;
		elements = new Object[initialCapacity];
		this.addAll(other);

	}

	/**
	 * Constructor that creates an instance with elements from other collection
	 * 
	 * @param other
	 *            some other Collection instance which elements are copied into
	 *            this newly constructed collection
	 */
	public ArrayIndexedCollection(Collection other) {
		this(other.size(), other);
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public int size() {
		return size;
	}

	/**
	 * Adds the given object into this collection at the end. Complexity is
	 * O(1).
	 */
	@Override
	public void add(Object value) {
		insert(value, size);
	}

	@Override
	public boolean contains(Object value) {
		if (indexOf(value) == -1) {
			return false;
		}

		return true;
	}

	@Override
	public boolean remove(Object value) {
		int position = indexOf(value);

		if (position == -1) {
			return false;
		}

		remove(position);
		return true;

	}

	@Override
	public Object[] toArray() {
		return elements;
	}

	@Override
	public void forEach(Processor processor) {
		for (int i = 0; i < size; i++) {
			processor.process(elements[i]);
		}
	}

	@Override
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}

		size = 0;
	}

	/**
	 * Method that gets the object that is stored in backing array at position
	 * index. Complexity is O(1).
	 * 
	 * @param index
	 *            position of object that we eant to get
	 * @return object that is stored at position index; Object
	 * @throws IndexOutOfBoundsException
	 *             if index < 0 or > size -1
	 */
	public Object get(int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException("Given position is not legal.");
		}

		return elements[index];
	}

	/**
	 * Method inserts the given value at the given position in array. Complexity
	 * is O(n).
	 * 
	 * @param value
	 *            Object that is being inserted in array
	 * @param position
	 *            position in array to insert object
	 * @throws IllegalArgumentException
	 *             if object to add is null IndexOutOfBoundsException id index
	 *             is <0 or >size
	 */
	public void insert(Object value, int position) {
		if (value == null) {
			throw new IllegalArgumentException("You can not add null.");
		}

		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException("Given position is not legal.");
		}

		// adding to the end
		if (size < capacity && position == size) {
			elements[position] = value;
			size++;
			return;
		}

		if (size == capacity) {
			capacity *= 2;
			Object[] temp = new Object[capacity];

			for (int i = 0; i < position; i++) {
				temp[i] = elements[i];
			}
			elements = temp;
		}

		for (int i = size - 1; i >= position; i--) {
			elements[i + 1] = elements[i];
		}

		elements[position] = value;
		size++;

	}

	/**
	 * Searches the collection and returns the index of the first occurrence of
	 * the given value or -1 if the value is not found. Average complexity of
	 * this method is O(n).
	 * 
	 * @param value
	 *            object whose index is wanted
	 * @return the index of the first occurrence of the given value or -1 if the
	 *         value is not found
	 */
	public int indexOf(Object value) {
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * Removes element at specified index from collection.
	 * 
	 * @param index
	 *            position of object to be removed.
	 * @throws IllegalArgumentException
	 *             id index is < 0 or > size -1
	 */
	public void remove(int index) {
		if (index < 0 || index > size - 1) {
			throw new IllegalArgumentException("Index shoud be beetwen 0 and " + size);
		}

		// removing to the end
		if (index == size - 1) {
			elements[index] = null;
			size--;
			return;
		}

		for (int i = index; i < size - 1; i++) {
			elements[i] = elements[i + 1];
		}

		elements[size - 1] = null;
		size--;
	}

}
