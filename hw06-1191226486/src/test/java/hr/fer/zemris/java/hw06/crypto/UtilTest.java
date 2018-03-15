package hr.fer.zemris.java.hw06.crypto;

import static org.junit.Assert.*;

import org.junit.Test;

public class UtilTest {

	@Test
	public void hextobyte() {
		assertEquals(1, Util.hextobyte("01aE22")[0]);
		assertEquals(-82, Util.hextobyte("01aE22")[1]);
		assertEquals(34, Util.hextobyte("01aE22")[2]);
		assertEquals(0, Util.hextobyte("000a0f")[0]);
		assertEquals(10, Util.hextobyte("000a0f")[1]);
		assertEquals(15, Util.hextobyte("000a0f")[2]);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void hextobyteOdd() {
		assertEquals(1, Util.hextobyte("01aE221")[0]);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void hextobyteIvalidChar() {
		assertEquals(1, Util.hextobyte("01a.221")[0]);
	}

	@Test
	public void bytetohex() {
		assertEquals("01ae22", Util.bytetohex(new byte[] {1, -82, 34}));
	}
	
	@Test
	public void bytetohexEmpty() {
		assertEquals("", Util.bytetohex(new byte[0]));
	}
	
}
