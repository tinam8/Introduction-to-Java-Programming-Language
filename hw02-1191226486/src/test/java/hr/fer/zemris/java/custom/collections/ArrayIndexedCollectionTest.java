package hr.fer.zemris.java.custom.collections;

import static org.junit.Assert.*;

import org.junit.Test;

public class ArrayIndexedCollectionTest {

	@Test
	public void constructorAcceptingCollection() {
		ArrayIndexedCollection array1 = new ArrayIndexedCollection();
		Object object1 = new Object();
		Object object2 = new Object();
		array1.add(object1);
		array1.add(object2);
		ArrayIndexedCollection array2 = new ArrayIndexedCollection(array1);
		assertEquals(2, array2.size());
	}
	
	@Test
	public void isEmpty() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		assertEquals(true, array.isEmpty());
	}
	
	@Test
	public void isNotEmpty() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		Object object = new Object();
		array.add(object);
		assertEquals(false, array.isEmpty());
	}
	
	@Test
	public void size() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		assertEquals(0, array.size());
		Object object = new Object();
		array.add(object);
		assertEquals(1, array.size());
		array.add(object);
		array.add(object);
		assertEquals(3, array.size());
		array.remove(2);
		assertEquals(2, array.size());
	}
	
	
	@Test
	public void contains() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		Object secondObject = new Object();
		array.add("test");
		assertEquals(true, array.contains("test"));
		assertEquals(false, array.contains(secondObject));
	}
	
	@Test
	public void remove() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		Object object = new Object();
		array.add(object);
		array.remove(object);
		assertEquals(false, array.contains(object));
		array.add(object);
		array.remove(0);
		assertEquals(false, array.contains(object));
	}
	
	@Test
	public void clear() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		Object object = new Object();
		array.add(object);
		array.add(object);
		array.add(object);
		array.clear();
		assertEquals(true, array.isEmpty());
	}
	
	@Test
	public void get() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		Object object1 = new Object();
		Object object2 = new Object();
		array.add(new Integer(5));
		array.add(object2);
		array.add(object2);
		array.add(object1);
		assertEquals(true, array.get(2).equals(object2));
		assertEquals(false, array.get(2).equals(object1));
		assertEquals(true, array.get(0).equals(5));
	}
	
	@Test
	public void toArray() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add("first");
		array.add("second");
		array.add("third");
		Object[] arr = array.toArray();
		assertEquals(true, arr[2].equals("third"));
		assertEquals(true, arr[0].equals("first"));
	}
	
	
	@Test
	public void indexOf() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add("first");
		array.add("second");
		array.add("third");
		assertEquals(2, array.indexOf("third"));
	}
	
	
	@Test
	public void insert() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add("first");
		array.add("second");
		array.add("third");
		array.insert("inserted", 1);
		assertEquals(1, array.indexOf("inserted"));
		assertEquals(2, array.indexOf("second"));
	}
	
	

}
