package hr.fer.zemris.math;

import static org.junit.Assert.*;

import org.junit.Test;

public class Vector3Test {

	@Test
	public void testNorm() {
		Vector3 i = new Vector3(3,4,0);
		assertEquals(5, i.norm(), 0.0001);
		
		i = new Vector3(5,2,3);
		assertEquals(6.164414002, i.norm(), 0.0001);
	}
	
	@Test
	public void testNormalized() {
		Vector3 i = new Vector3(3,4,0);
		Vector3 expected = new Vector3(0.6, 0.8, 0);
		Vector3 normalized = i.normalized();
		
		assertTrue(compareVectors(expected, normalized));
	}
	
	@Test
	public void testCross() {
		Vector3 i = new Vector3(1,0,0);
		Vector3 j = new Vector3(0,1,0);
		Vector3 expected = new Vector3(0, 0, 1);
		Vector3 cross = i.cross(j);
		assertTrue(compareVectors(expected, cross));
	}
	
	@Test
	public void testAdd() {
		Vector3 i = new Vector3(1,0,0);
		Vector3 j = new Vector3(0,1,0);
		Vector3 expected = new Vector3(1, 1, 0);
		Vector3 add = i.add(j);
		assertTrue(compareVectors(expected, add));
	}
	
	@Test
	public void testSub() {
		Vector3 i = new Vector3(1,0,0);
		Vector3 j = new Vector3(0,1,0);
		Vector3 expected = new Vector3(1, -1, 0);
		Vector3 sub = i.sub(j);
		
		assertTrue(compareVectors(expected, sub));
	}
	
	
	@Test
	public void testDot() {
		Vector3 i = new Vector3(5,2,3);
		Vector3 j = new Vector3(1,2,4);
		assertEquals(21.0, i.dot(j), 0.00001);
	}
	
	@Test
	public void testScale() {
		Vector3 i = new Vector3(5,2,3);
		Vector3 expected = new Vector3(10, 4, 6);
		Vector3 scaled = i.scale(2);
		assertTrue(compareVectors(expected, scaled));
	}
	
	
	@Test
	public void testCosAngle() {
		Vector3 i = new Vector3(5,2,3);
		Vector3 j = new Vector3(0,2,1);
		double cos = i.cosAngle(j);
		assertEquals(0.507833375,cos, 0.000001);
	}
	
	@Test
	public void testToArray() {
		Vector3 i = new Vector3(5,2,3);
		double[] array = i.toArray();
		assertEquals(3, array.length);
	}
	
	
	/**
	 * Method that compares two vectors.
	 * 
	 * @param first
	 *            first vector
	 * @param second
	 *            second vector
	 * @return true if equals, false otherwise
	 */
	private static boolean compareVectors(Vector3 first, Vector3 second) {
		if (first.getX() != second.getX()) {
			return false;
		}
		if (first.getY() != second.getY()) {
			return false;
		}
		if (first.getZ() != second.getZ()) {
			return false;
		}
		
		return true;
	}
}
