package hr.fer.zemris.java.hw04.collections;

import static org.junit.Assert.*;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import org.junit.Test;


public class SimpleHashtableTest {

	@Test
	public void constructorDefaultCapacity() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();
		assertEquals(0, table.size());
		assertEquals(true, table.isEmpty());
		assertEquals(null, table.get("ana"));
		assertEquals(false, table.containsKey("ana"));
		assertEquals(false, table.containsValue("ana"));

	}

	@Test
	public void constructorCustomCapacity() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		assertEquals(4, examMarks.size());
		assertEquals(Integer.valueOf(2), examMarks.get("Ivana"));

		// System.out.println(examMarks.toString());

		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		assertEquals(4, examMarks.size());
		assertEquals(Integer.valueOf(5), examMarks.get("Ivana"));

		assertEquals(true, examMarks.containsKey("Ivana"));
		assertEquals(true, examMarks.containsValue(5));
		assertEquals(false, examMarks.containsValue(4));
		assertEquals(Integer.valueOf(2), examMarks.get("Ante"));

		examMarks.remove("Ivana");
		assertEquals(false, examMarks.containsKey("Ivana"));
		assertEquals(3, examMarks.size());

		examMarks.clear();
		assertEquals(0, examMarks.size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructorAddNullKey() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);

		examMarks.put(null, 5); // overwrites old grade for Ivana
	}

	@Test
	public void doubleCapacity() {
		SimpleHashtable<String, Integer> examMarks = getCollection();

		assertEquals(8, examMarks.getCapacity());
		assertEquals(true, examMarks.containsKey("Ivana"));
		assertEquals(5, examMarks.size());

	}

	@Test
	public void iteratorRemove() {
		SimpleHashtable<String, Integer> examMarks = getCollection();

		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals("Ivana")) {
				iter.remove(); // sam iterator kontrolirano uklanja trenutni
								// element
			}
		}
		
		assertEquals(false, examMarks.containsKey("Ivana"));
	}
	
	@Test(expected=IllegalStateException.class)
	public void iteratorRemoveException() {
		SimpleHashtable<String, Integer> examMarks = getCollection();

		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals("Ivana")) {
				iter.remove(); // sam iterator kontrolirano uklanja trenutni
								// element
				iter.remove();
			}
		}
		
		assertEquals(false, examMarks.containsKey("Ivana"));
	}
	
	@Test(expected=ConcurrentModificationException.class)
	public void putDuringIteration() {
		SimpleHashtable<String, Integer> examMarks = getCollection();

		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			examMarks.put("Novo", 4);
		}
		
		assertEquals(false, examMarks.containsKey("Ivana"));

	}

	@Test(expected=ConcurrentModificationException.class)
	public void removeDuringIteration() {
		SimpleHashtable<String, Integer> examMarks = getCollection();

		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			examMarks.remove("Ivana");
		}
		
		assertEquals(false, examMarks.containsKey("Ivana"));

	}
	// Helper method that creates and returns populated hash table.
		private SimpleHashtable<String, Integer> getCollection() {
			SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
			// fill data:
			examMarks.put("Ivana", 2);
			examMarks.put("Ante", 2);
			examMarks.put("Jasna", 2);
			examMarks.put("Kristina", 5);
			examMarks.put("Martina", 5);
			
			return examMarks;
	}

}
