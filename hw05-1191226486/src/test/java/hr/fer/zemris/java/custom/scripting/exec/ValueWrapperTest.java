package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.*;

import org.junit.Test;

public class ValueWrapperTest {

	@Test
	public void testAdd() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue());

		assertEquals(null, v2.getValue());
		assertEquals(0, v1.getValue());

		ValueWrapper v3 = new ValueWrapper("1.2E1");
		ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));
		v3.add(v4.getValue());

		assertEquals(13.0, v3.getValue());
		assertEquals(1, v4.getValue());

		ValueWrapper v5 = new ValueWrapper("12");
		ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1));
		v5.add(v6.getValue());

		assertEquals(13, v5.getValue());
		assertEquals(1, v6.getValue());
	}

	@Test(expected = RuntimeException.class)
	public void testAddException() {
		ValueWrapper v7 = new ValueWrapper("Ankica");
		ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));
		v7.add(v8.getValue());
	}

	@Test
	public void testSubtract() {
		ValueWrapper v1 = new ValueWrapper(11.2);
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		v1.subtract(v2.getValue());

		assertEquals(10.2, v1.getValue());
		assertEquals(1, v2.getValue());

		ValueWrapper v3 = new ValueWrapper(null);
		v3.subtract(v2.getValue());

		assertEquals(-1, v3.getValue());
		assertEquals(1, v2.getValue());

	}

	@Test
	public void testMultiply() {
		ValueWrapper v1 = new ValueWrapper(11.2);
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		v1.multiply(v2.getValue());

		assertEquals(11.2, v1.getValue());
		assertEquals(1, v2.getValue());

		ValueWrapper v3 = new ValueWrapper(null);
		v3.multiply(v2.getValue());

		assertEquals(0, v3.getValue());
		assertEquals(1, v2.getValue());

	}

	@Test
	public void testDivide() {
		ValueWrapper v1 = new ValueWrapper(11.2);
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		v1.divide(v2.getValue());

		assertEquals(11.2, v1.getValue());
		assertEquals(1, v2.getValue());

		ValueWrapper v3 = new ValueWrapper(null);
		v3.divide(v2.getValue());

		assertEquals(0, v3.getValue());
		assertEquals(1, v2.getValue());

		ValueWrapper v4 = new ValueWrapper(5);
		ValueWrapper v5 = new ValueWrapper(2.5);

		v4.divide(v5.getValue());

		assertEquals(2.0, v4.getValue());
		assertEquals(2.5, v5.getValue());

	}

	@Test(expected = RuntimeException.class)
	public void testDivideException() {
		ValueWrapper v1 = new ValueWrapper(11.2);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.divide(v2.getValue());
	}

	@Test
	public void testCompare() {
		ValueWrapper v1 = new ValueWrapper(11.2);
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));

		assertEquals(1, v1.numCompare(v2.getValue()));
		assertEquals(-1, v2.numCompare(v1.getValue()));

		ValueWrapper v3 = new ValueWrapper("11.2");
		ValueWrapper v4 = new ValueWrapper(11.2);

		assertEquals(0, v3.numCompare(v4.getValue()));
		assertEquals(0, v4.numCompare(v3.getValue()));
	}
}
