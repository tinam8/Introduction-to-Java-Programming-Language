package hr.fer.zemris.java.hw04.collections;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

import javax.naming.directory.ModificationItem;

/**
 * This class implements a hash table, which maps keys to values. Any non-null
 * object can be used as a key.Values can be null. The number of ordered pairs (
 * key , value ) that are stored in this collection can be greater than the
 * number of slots table.
 * 
 * @param <K>
 *            type of key
 * @param <V>
 *            type of value
 * 
 * @author tina
 *
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {
	/**
	 * Default number of slots in hash table.
	 */
	private static final int DEFAULT_SLOTS = 16;
	/**
	 * Percentage of elements that collection can conatin based on number of
	 * slots.
	 */
	private static final double CAPACITY_LIMIT = 0.75;
	/**
	 * separates pairs in string representation of the hash table collection.
	 */
	private static final String DELIMITER = ", ";

	/**
	 * Begin of the string representation of the collection.
	 */
	private static final String BEGIN = "[";

	/**
	 * End of the string representation of the collection.
	 */
	private static final String END = "]";
	/**
	 * Array of slots of the hash table.
	 */
	private TableEntry<K, V>[] table;
	/**
	 * Number of elements in hash table.
	 */
	private int size;
	/**
	 * Number of slots.
	 */
	private int capacity;
	/**
	 * number of modification of instance of the class.
	 */
	private int modificationCount;

	/**
	 * Class that represents one slot of hash table.
	 * 
	 * @param <K>
	 *            Type of key in ordered pair.
	 * @param <V>
	 *            Type of value in ordered pair.
	 * 
	 */
	public static class TableEntry<K, V> {
		/**
		 * The key of ordered pair.
		 */
		private K key;
		/**
		 * The value of ordered pair.
		 */
		private V value;
		/**
		 * Refrence to the next instance of TableEntry.
		 */
		private TableEntry<K, V> next;

		// TODO: override toString
		/**
		 * Constructor that has three argumeents.
		 * 
		 * @param key
		 *            key of oredred pair; can not be null
		 * @param value
		 *            value of oredered pair
		 * @param next
		 *            refrence to the next instance of TableEntry that
		 *            represents ordered pair.
		 */
		public TableEntry(K key, V value, TableEntry<K, V> next) {
			super();
			this.key = key;
			this.value = value;
			this.next = next;
		}

		/**
		 * Method that returns key value.
		 * 
		 * @return value of the key;
		 */
		public K getKey() {
			return key;
		}

		/**
		 * Method that returns value of ordered pair.
		 * 
		 * @return value of ordered pair
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Method that returns value of ordered pair.
		 * 
		 * @param value
		 *            of ordered pair.
		 */
		public void setValue(V value) {
			this.value = value;
		}

	}

	/**
	 * Constructor that creates hash table with default number of slots (16).
	 * 
	 */
	public SimpleHashtable() {
		this(DEFAULT_SLOTS);
	}

	/**
	 * Constructor that creates hash table with capacity that is equal to the
	 * closest power of 2 that is equal to or greater than desired capacity.
	 * 
	 * @param initialCapacity
	 *            number of wanted slots
	 * @throws IllegalArgumentException
	 *             i wanted capacity is less than 1
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException("Capacity has to be greater that 1.");
		}

		capacity = (int) Math.ceil(Math.log(initialCapacity) / Math.log(2));
		capacity = (int) Math.pow(2, capacity);

		table = (TableEntry<K, V>[]) new TableEntry[capacity];
		size = 0;
		modificationCount = 0;
	}

	/**
	 * Method that gets number of pairs in hash table.
	 * 
	 * @return number of elements
	 */
	public int size() {
		return size;
	}

	/**
	 * Method that checks if collection is empty
	 * 
	 * @return true if hash table is empty, otherwise false
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Method that checks if hash table has a given key. <br>
	 * Complexity is O(1)
	 * 
	 * @param key
	 *            key whose existance is to be checked.
	 * @return true if key exists, otherwise false
	 */
	public boolean containsKey(Object key) {
		TableEntry<K, V> pair = getPair(key);

		return pair == null ? false : true;
	}

	/**
	 * Method that checks if hash table has a given value. <br>
	 * Complexity is O(n)
	 * 
	 * @param value
	 *            whose existance is to be checked.
	 * @return truen if value exists, otherwise false
	 */
	public boolean containsValue(Object value) {
		for (TableEntry<K, V> slot : table) {

			while (slot != null) {
				if (slot.getValue().equals(value)) {
					return true;
				}

				slot = slot.next;
			}
		}

		return false;
	}

	/**
	 * Method which return returns value of pair with given key. If pair with
	 * that key does not exist method returns null/
	 * 
	 * @param key
	 *            key of ordered pair
	 * @return value of pair with given key if key exiscts, otherwise null
	 */
	public V get(Object key) {
		TableEntry<K, V> pair = getPair(key);

		return (pair == null) ? null : pair.getValue();
	}

	/**
	 * Suplemmentary method for testing thar returns capacity.s
	 * 
	 * @return number of slots.
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * Suplemmentary method that finds element in hash table (pair (key, value)
	 * ) based on given key. <br>
	 * If key argument is null method return null.
	 * 
	 * @param key
	 *            key of pair that we eant to get
	 * @return instance of {@link TableEntry} if has table contains key
	 *         otherwise null
	 */
	private TableEntry<K, V> getPair(Object key) {
		if (key == null) {
			return null;
		}

		int numberOfSlot = getSlot(key);

		TableEntry<K, V> temp = table[numberOfSlot];

		while (temp != null) {
			if (temp.getKey().equals(key)) {
				return temp;
			}

			temp = temp.next;
		}

		return null;
	}

	/**
	 * Method that calculates to which slot key belongs.
	 * 
	 * @param key
	 *            key whose slot is to be calculated
	 * @return calculated slot
	 */
	private int getSlot(Object key) {
		return Math.abs(key.hashCode()) % capacity;
	}

	/**
	 * Method that inserts ordered pait into hash table. <br>
	 * If pair already exists method updates its walue with givn one. <br>
	 * If key is null it throws IllegalArgumentException.<br>
	 * Value can be null.
	 * 
	 * @param key
	 *            key of ordered pair
	 * @param value
	 *            value of oredered pair
	 */
	public void put(K key, V value) {
		if (key == null) {
			throw new IllegalArgumentException("Can not add pair with key value null.");
		}

		int numberOfSlot = getSlot(key);

		TableEntry<K, V> temp = table[numberOfSlot];

		if (temp == null) {
			table[numberOfSlot] = new TableEntry<K, V>(key, value, null);
			size++;
			modificationCount++;
			if ((double) size / capacity >= CAPACITY_LIMIT) {
				doubleCapacity();
			}
			return;
		}

		while (true) {
			if (temp.getKey().equals(key)) {
				temp.value = value;
				return;
			}

			if (temp.next == null) {
				break;
			}
			temp = temp.next;
		}

		temp.next = new TableEntry<K, V>(key, value, null);
		size++;
		modificationCount++;
		if ((double) size / capacity >= CAPACITY_LIMIT) {
			doubleCapacity();
		}
	}

	/**
	 * Private method that doubles capacity of hash table.
	 */
	@SuppressWarnings("unchecked")
	private void doubleCapacity() {
		TableEntry<K, V>[] tempTable = table;
		capacity *= 2;
		size = 0;

		table = (TableEntry<K, V>[]) new TableEntry[capacity];
		TableEntry<K, V> temp;

		for (int i = 0; i < tempTable.length; i++) {
			temp = tempTable[i];
			while (temp != null) {
				put(temp.key, temp.value);
				temp = temp.next;
			}
		}
	}

	/**
	 * Method that removes pair with given key if has table contains it.
	 * 
	 * @param key
	 *            key of pair to remove.
	 */
	public void remove(Object key) {
		if (key == null) {
			return;
		}

		int numberOfSlot = getSlot(key);
		TableEntry<K, V> temp = table[numberOfSlot];

		if (temp.key.equals(key)) {
			table[numberOfSlot] = null;
			size--;
			modificationCount++;
			return;
		}

		while (temp != null) {
			if (temp.next.key.equals(key)) {
				temp = temp.next;
				size--;
				modificationCount++;
				return;
			}

			temp = temp.next;
		}

	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(BEGIN);
		
		for (TableEntry<K, V> pair : this) {
			sb.append(pair.key + "=" + pair.value + DELIMITER);
			pair = pair.next;
		}

		// TODO delete
//		for (TableEntry<K, V> slot : table) {
//			TableEntry<K, V> temp = slot;
//			while (temp != null) {
//				sb.append(temp.key + "=" + temp.value + DELIMITER);
//				temp = temp.next;
//			}
//		}

		sb.replace(sb.length() - 2, sb.length() + 1, END);
		return sb.toString();
	}

	/**
	 * Method that removes all elements from hash table.
	 */
	public void clear() {
		if (isEmpty()) {
			return;
		}

		for (int i = 0; i < capacity; i++) {
			table[i] = null;
		}
		size = 0;
	}

	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}

	/**
	 * Class that realises iterator.
	 * 
	 * @author tina
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {
		/**
		 * Element of hash table that iterator currently referes to.
		 */
		private TableEntry<K, V> currentElement;
		/**
		 * Element that will be returned when next method is called.
		 */
		private TableEntry<K, V> nextElement;
		/**
		 * Position of a slot that conatins current element that iterator refers
		 * to.
		 */
		private int currentSlot;
		/**
		 * Contains number of modifications that ocured outside of instance of
		 * iterator.
		 */
		private int initialModificationCount;
		/**
		 * Determines wheter current element has been removed.
		 */
		private boolean currentIsRemoved;

		/**
		 * Constructor.
		 */
		public IteratorImpl() {
			initialModificationCount = modificationCount;
			currentIsRemoved = false;

			for (int i = 0; i < capacity; i++) {
				if (table[i] != null) {
					currentSlot = i;
					nextElement = table[currentSlot];
					return;
				}
			}

		}

		@Override
		public boolean hasNext() {
			/**
			 * @throws ConcurrentModificationException
			 *             if collection has been changed outside of the
			 *             iterator
			 * 
			 */
			if (initialModificationCount != modificationCount) {
				throw new ConcurrentModificationException("Collection was modified outside of the iterator");
			}

			return nextElement != null;
		}

		/**
		 * @throws ConcurrentModificationException
		 *             if collection has been changed outside of the iterator
		 */
		@Override
		public TableEntry<K, V> next() {
			if (initialModificationCount != modificationCount) {
				throw new ConcurrentModificationException("Collection was modified outside of the iterator");
			}

			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			currentElement = nextElement;
			currentIsRemoved = false;

			// if we are not at the end of linked list of a slot
			if (nextElement.next != null) {
				nextElement = nextElement.next;
			} else {
				int nextSlot = -1;
				// finding next slot that is not null
				for (int i = currentSlot + 1; i < table.length; i++) {
					if (table[i] != null) {
						nextSlot = i;
						break;
					}
				}

				// if there is next slot that is not null
				if (nextSlot != -1) {
					currentSlot = nextSlot;
					nextElement = table[currentSlot];
				} else {
					nextElement = null;
				}
			}

			return currentElement;

		}

		/**
		 * @throws ConcurrentModificationException
		 *             if collection has been changed outside of the iterator
		 */
		@Override
		public void remove() {
			if (initialModificationCount != modificationCount) {
				throw new ConcurrentModificationException("Collection was modified outside of the iterator");
			}

			if (currentIsRemoved) {
				throw new IllegalStateException("Element has already been removed.");
			}

			SimpleHashtable.this.remove(currentElement.getKey());
			initialModificationCount++;
			currentIsRemoved = true;
		}
	}

}
