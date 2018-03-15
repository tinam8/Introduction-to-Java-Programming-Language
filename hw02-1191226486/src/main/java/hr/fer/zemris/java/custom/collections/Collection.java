package hr.fer.zemris.java.custom.collections;

/**
 * class which represents some general collection of objects
 * 
 * @author Tina Maric
 *
 */
public class Collection {

	/**
	 * protected default constructor
	 */
	protected Collection() {
	}

	/**
	 * Method which determines wheter collection contains any objects
	 * 
	 * @return true if collection contains no objects and false otherwise;
	 *         boolean
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Method which returns the number of currently stored objects in this
	 * collections
	 * 
	 * @return always returns 0
	 */
	public int size() {
		return 0;
	}

	/**
	 * Method adds the given object into this collection
	 * 
	 * @param value
	 *            object that is being added to collection
	 */
	public void add(Object value) {

	}

	/**
	 * Method which checks if collection contains given value
	 * 
	 * @param value
	 *            object to be checked wheter if it is contained in collection
	 *            or not
	 * @return true only if the collection contains given value
	 */
	public boolean contains(Object value) {
		return false;
	}

	/**
	 * Method that removes object from colletion, it is not specified which one
	 * 
	 * @param value
	 *            object to be removed
	 * @return true only if the collection contains given value as determined by
	 *         equals method and removes one occurrence of it; boolean
	 */
	public boolean remove(Object value) {
		return false;
	}

	/**
	 * Method that allocates new array with size equals to the size of this
	 * collections, fills it with collection content and returns the array
	 * 
	 * @return Object[] array filled with collection content
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Method calls processor.process() for each element of this collection, The
	 * order in which elements will be sent is undefined in this class
	 * 
	 * @param processor
	 *            instance of Processor class whose process method should be
	 *            called
	 */
	public void forEach(Processor processor) {
		
	}

	/**
	 * Method adds into itself all elements from given collection. This other
	 * collection remains unchanged.
	 * 
	 * @param other
	 *            collection to be added to the current one
	 */
	public void addAll(Collection other) {

		/**
		 * Local class
		 * 
		 * @author Tina Maric
		 *
		 */
		class LocalProcessor extends Processor {

			/**
			 * Method that adds object into collection
			 * 
			 * @param value object to be added
			 */
			@Override
			public void process(Object value) {
				add(value);
			}
		}
		
		LocalProcessor processor = new LocalProcessor();
		other.forEach(processor);

	}

	/**
	 * Removes all elements from this collection.
	 */
	public void clear() {

	}

}
