package hr.fer.zemris.java.custom.collections;

/**
 * Class that represents linked list-backed collection of objects.
 * 
 * @author Tina Maric
 *
 */
public class LinkedListIndexedCollection extends Collection {

	/**
	 * Class that prepresents ement of a LinkedListIndexedCollection instance
	 * 
	 * @author tina
	 *
	 */
	private static class ListNode {
		/** pointer to previous list node */
		ListNode previous;
		/** pointer to next list node */
		ListNode next;
		/** reference for value storage */
		Object value;

		/**
		 * Defalut constructor
		 */
		public ListNode() {
		}

		/**
		 * Construstor that has three arguments
		 * 
		 * @param previous
		 *            pointer to previous list node
		 * @param next
		 *            pointer to next list node
		 * @param value
		 *            reference for value storage
		 */
		public ListNode(ListNode previous, ListNode next, Object value) {
			this.previous = previous;
			this.next = next;
			this.value = value;
		}

	}

	/**
	 * current size of collection (number of elements actually stored; number of
	 * nodes in list)
	 */
	private int size;
	/**
	 * reference to the first node of the linked list
	 */
	private ListNode first;
	/**
	 * reference to the last node of the linked list
	 */
	private ListNode last;

	/**
	 * default constructor creates an empty collection
	 */
	public LinkedListIndexedCollection() {
		first = last = null;
		size = 0;
	}

	/**
	 * Constructor that creates an instance with elements from other collecti
	 * 
	 * @param other
	 *            some other Collection instance which elements are copied into
	 *            this newly constructed collection
	 */
	public LinkedListIndexedCollection(Collection other) {
		this();
		addAll(other);
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Object[] toArray() {
		Object[] array = new Object[size];
		ListNode iterator = first;
		int index = 0;

		while (iterator != null) {
			array[index] = iterator.value;
			index++;
			iterator = iterator.next;
		}

		return array;
	}

	@Override
	public void forEach(Processor processor) {
		ListNode iterator = first;
		for (int i = 0; i < size; i++) {
			processor.process(iterator.value);
			iterator = iterator.next;
		}
	}

	/**
	 * Adds the given object into this collection at the end of collection;
	 * newly added element becomes the element at the biggest index. Complexity
	 * is O(1). The method refuses to add null as element by throwing the
	 * exception
	 * 
	 * @param value
	 *            object to add
	 */
	@Override
	public void add(Object value) {
		this.insert(value, size);
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

	/**
	 * Helpers method which returns the ListNode instance that is stored in
	 * linked list at position received as argument. Valid indexes are 0 to
	 * size-1. If index is invalid, the implementation should throw the
	 * exception. Complexity is less than n/2+1
	 * 
	 * @param index
	 *            position of object that we want to get
	 * @return object at index
	 * @throws IndexOutOfBoundsException
	 *             id index <0 or > size -1
	 */
	private ListNode getListNode(int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException("index should be beewtwen 0 and " + (size - 1));
		}
		ListNode temp = new ListNode();

		if (index > size / 2) {
			int numberOfSteps = size - index - 1;
			temp = last;

			while (numberOfSteps != 0) {
				temp = temp.previous;
			}
		} else {
			temp = first;

			while (index != 0) {
				temp = temp.next;
				index--;
			}
		}

		return temp;
	}

	/**
	 * Method returns the object that is stored in linked list at position
	 * index. Valid indexes are 0 to size-1. If index is invalid, the
	 * implementation should throw the exception. Complexity is less than n/2+1
	 * 
	 * @param index
	 *            position of object that we want to get
	 * @return object at index
	 */
	public Object get(int index) {
		return this.getListNode(index).value;
	}

	/**
	 * Removes all elements from the collection. Collection “forgets” about
	 * current linked list.
	 */
	public void clear() {
		first = last = null;
		size = 0;
	}

	/**
	 * Inserts (does not overwrite) the given value at the given position in
	 * linked-list. Elements starting from this position are shifted one
	 * position. The legal positions are 0 to size. If position is invalid, an
	 * exception is thrown. Average complexity is O(n).
	 * 
	 * @param value
	 *            object to be inserted in list
	 * @param position
	 *            position at whisch object should be inserted
	 * @throws IllegalArgumentException
	 *             if object to add is null IndexOutOfBoundsException if index <
	 *             0 or > size
	 */
	public void insert(Object value, int position) {
		if (value == null) {
			throw new IllegalArgumentException("Null reference can not be added.");
		}

		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException("Given position is not legal.");
		}

		// if list is empty
		if (last == null) {
			last = first = new ListNode(null, null, value);
		} else {

			if (position == 0) {
				// insert at the beggining
				ListNode newNode = new ListNode(null, first, value);
				first.previous = newNode;
				first = newNode;
			} else if (position == size) {
				// add at the end
				ListNode newNode = new ListNode(last, null, value);
				last.next = newNode;
				last = newNode;
			} else {
				// insert in the middle
				ListNode currentNode = this.getListNode(position);
				ListNode newNode = new ListNode(currentNode.previous, currentNode, value);
				currentNode.previous.next = newNode;
				currentNode.previous = newNode;
			}

		}
		size++;
	}

	/**
	 * Searches the collection and returns the index of the first occurrence of
	 * the given object. Average complexity is O(n).
	 * 
	 * @param value
	 *            object whose position is wanted
	 * @return first occurrence of the given value or -1 if the value is not
	 *         found
	 */
	public int indexOf(Object value) {
		if (value == null) {
			return -1;
		}

		ListNode iter = first;
		int index = 0;
		while (iter != null) {
			if (iter.value.equals(value)) {
				return index;
			}
			index++;
			iter = iter.next;
		}

		return -1;
	}

	/**
	 * Removes element at specified index from collection. Element that was
	 * previously at location index+1 after this operation is on location index,
	 * etc. Legal indexes are 0 to size-1. In case of invalid index throws an
	 * exception.
	 * 
	 * @param index
	 *            position of the object to remove from the list
	 * @throws IndexOutOfBoundsException
	 *             if index < 0 or > size -1
	 */
	public void remove(int index) {
		if (index < 0 || index > size - 1) {
			throw new IllegalArgumentException("Index shoud be beetwen 0 and " + size);
		}

		if (this.isEmpty()) {
			return;
		}

		if (index == 0) {
			if (size == 1) {
				first = last = null;
			} else {
				// remove from the beggining
				first.next.previous = null;
				first = first.next;
			}
		} else if (index == size - 1) {
			// remove from the end
			last.previous.next = null;
			last = last.previous;
		} else {
			// remove from the middle
			ListNode currentNode = this.getListNode(index);
			currentNode.previous.next = currentNode.next;
			currentNode.next.previous = currentNode.previous;
		}

		size--;

	}

}
