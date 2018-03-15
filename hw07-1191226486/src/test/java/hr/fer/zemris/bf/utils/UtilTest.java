package hr.fer.zemris.bf.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class UtilTest {

	@Test
	public void testBooleanArrayToInt() {
		assertEquals(1, Util.booleanArrayToInt(new boolean[]{false, false, true}));
		assertEquals(2, Util.booleanArrayToInt(new boolean[]{false, true, false}));
		assertEquals(3, Util.booleanArrayToInt(new boolean[]{false, true, true}));
		assertEquals(7, Util.booleanArrayToInt(new boolean[]{true, true, true}));
		assertEquals(15, Util.booleanArrayToInt(new boolean[]{true, true, true, true}));
	}

}
