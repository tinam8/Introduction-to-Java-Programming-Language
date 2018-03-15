package hr.fer.zemris.bf.utils;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class UtilTest {

	@Test
	public void testBooleanArrayToInt() {
		assertEquals(1, Util.booleanArrayToInt(new boolean[] { false, false, true }));
		assertEquals(2, Util.booleanArrayToInt(new boolean[] { false, true, false }));
		assertEquals(3, Util.booleanArrayToInt(new boolean[] { false, true, true }));
		assertEquals(7, Util.booleanArrayToInt(new boolean[] { true, true, true }));
		assertEquals(15, Util.booleanArrayToInt(new boolean[] { true, true, true, true }));
	}

	@Test
	public void testIndexToByteArray() {

		byte[] array = Util.indexToByteArray(3, 2);
		byte[] expected = new byte[] { 1, 1 };
		
		assertTrue(Arrays.equals(array, expected));

		array = Util.indexToByteArray(3, 4);
		expected = new byte[] { 0, 0, 1, 1 };
		
		assertTrue(Arrays.equals(array, expected));

		array = Util.indexToByteArray(3, 6);
		expected = new byte[] { 0, 0, 0, 0, 1, 1 };
		
		assertTrue(Arrays.equals(array, expected));

		array = Util.indexToByteArray(19, 4) ;
		expected = new byte[] {0, 0,  1, 1 };
		
		assertTrue(Arrays.equals(array, expected));		
	}


	@Test
	public void testIndexNegativeToByteArray() {

		byte[] array = Util.indexToByteArray(-2, 32);
		byte[] expected = new byte[32];
		
		expected[31] = (byte) 0;
		for(int i = 0; i < 31; i++) {
			expected[i] = (byte) 1;
		}

		assertTrue(Arrays.equals(array, expected));	
		
		array = Util.indexToByteArray(-2, 16);
		expected = new byte[16];
		
		expected[15] = (byte) 0;
		for(int i = 0; i < 15; i++) {
			expected[i] = (byte) 1;
		}

		assertTrue(Arrays.equals(array, expected));	
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testIndexToByteArrayInvalid() {

		byte[] array = Util.indexToByteArray(-2, -32);
		
	}

}
