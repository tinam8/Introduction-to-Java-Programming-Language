package hr.fer.zemris.bf.qmc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class MaskTest {

	@Test
	public void testConstructors() {
		Mask mask = new Mask(2, 3, true);
		Set<Integer> set = new HashSet<>();	
		set.add(1);
		
		mask = new Mask(new byte[]{1, 1}, set, false);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorsIvalid1() {
		Mask mask = new Mask(20, 3, true);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorsIvalid2() {
		Set<Integer> set = new HashSet<>();	
		set.add(1);
		
		Mask mask = new Mask(null, set, false);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorsIvalid3() {
		Set<Integer> set = new HashSet<>();	
		
		Mask mask = new Mask(null, set, false);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorsIvalid4() {		
		Mask mask = new Mask(new byte[]{1, 1}, null, false);
	}
	
	@Test
	public void testEquals() {
		Mask mask = new Mask(2, 3, true);
		Mask mask2 = new Mask(2, 3, true);
		
		assertTrue(mask.equals(mask2));
		
	}
	
	
	@Test
	public void testCountOfOnes() {
		Set<Integer> set = new HashSet<>();	
		set.add(1);
		set.add(4);
		
		Mask mask = new Mask(new byte[]{1, 1, 2, 0}, set, false);
		
		assertEquals(2, mask.countOfOnes());	
		
	}
	
	@Test
	public void testCombine() {
		Mask mask = new Mask(0, 3, false);
		Mask mask2 = new Mask(1, 3, true);
		Mask mask3 = new Mask(6, 3, false);
		Set<Integer> indexes = new HashSet<>();
		indexes.add(0);
		indexes.add(1);
		
		Mask expected = new Mask(new byte[]{0,0,2}, indexes, true);
		
		assertTrue(expected.equals(mask2.combineWith(mask).get()));
		assertTrue(mask2.combineWith(mask).isPresent());
		
		assertFalse(mask2.combineWith(mask3).isPresent());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testCombineInvalidLenght() {
		Mask mask = new Mask(0, 3, false);
		Mask mask2 = new Mask(1, 4, true);

		assertTrue(mask2.combineWith(mask).isPresent());

	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCombineInvalidArgument() {
		Mask mask = new Mask(0, 3, false);
		Mask mask2 = new Mask(1, 4, true);

		assertTrue(mask2.combineWith(null).isPresent());

	}
}
