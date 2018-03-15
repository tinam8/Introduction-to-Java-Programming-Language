package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.*;

import org.junit.Test;

public class ObjectMultistackTest {

	@Test
	public void isEmptyAndPush() {
		ObjectMultistack map = new ObjectMultistack();
		assertTrue(map.isEmpty("year"));

		map.push("year", new ValueWrapper(Integer.valueOf(5)));
		assertFalse(map.isEmpty("year"));
		assertTrue(map.isEmpty("y"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void pushNull() {
		ObjectMultistack map = new ObjectMultistack();

		map.push("year", null);
	}
	
	@Test(expected=EmptyStackException.class)
	public void popEmpty() {
		ObjectMultistack map = new ObjectMultistack();
		map.push("year", new ValueWrapper(Integer.valueOf(5)));
		map.pop("y");
	}
	
	@Test
	public void popAndPeek() {
		ObjectMultistack map = new ObjectMultistack();
		assertTrue(map.isEmpty("year"));

		map.push("year", new ValueWrapper(Integer.valueOf(5)));
		assertEquals(5, map.pop("year").getValue());
		assertTrue(map.isEmpty("year"));
		
		map.push("year", new ValueWrapper(Integer.valueOf(5)));
		map.push("year", new ValueWrapper("stefica"));
		map.push("year", new ValueWrapper(11.5));
		map.push("day", new ValueWrapper("11.12"));
		
		assertEquals(11.5, map.peek("year").getValue());
		map.pop("year");
		assertEquals("stefica", map.peek("year").getValue());
		assertFalse(map.isEmpty("year"));
		
		assertEquals("11.12", map.peek("day").getValue());
	}
	
	@Test(expected=EmptyStackException.class)
	public void peekNull() {
		ObjectMultistack map = new ObjectMultistack();
		map.push("year", new ValueWrapper(Integer.valueOf(5)));
		map.push("year", new ValueWrapper("stefica"));

		map.peek("day");
	}

}
