package hr.fer.zemris.java.custom.collections;

import static org.junit.Assert.*;

import org.junit.Test;

public class LinkedListIndexedCollectionTest {

	@Test
	public void add() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		Object object1 = new Integer(2);
		list.add(object1);
		list.add(new Integer(3));
		list.add(new Integer(10));
		assertEquals(true, list.get(0).equals(object1));
		assertEquals(false, list.get(2).equals(object1));
	}
	
	@Test
	public void insertAndRemove() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		Object object1 = new Integer(2);
		list.add(object1);
		list.add(new Integer(3));
		list.add(new Integer(10));
		list.insert(new String("New York"), 1);
		assertEquals(true, list.get(1).equals("New York"));
		assertEquals(true, list.get(2).equals(3));
		assertEquals(true, list.get(3).equals(10));
		assertEquals(4, list.size());
		
		list.remove(0);
		assertEquals(true, list.get(0).equals("New York"));
		assertEquals(3, list.size());
		
	}
	
	

}
