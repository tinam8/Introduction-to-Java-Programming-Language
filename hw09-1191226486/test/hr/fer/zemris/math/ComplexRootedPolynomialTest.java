package hr.fer.zemris.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ComplexRootedPolynomialTest {

	@Test
	public void testApply() {
		ComplexRootedPolynomial complexRooted = new ComplexRootedPolynomial(new Complex[]{
			new Complex(5, 1),
			new Complex(1, 1),
			new Complex(2, 0),
		});
		
		Complex result = complexRooted.apply(new Complex(2,2));
		assertEquals(4, result.re, 0.0000001);
		assertEquals(-8, result.im, 0.0000001);
		
		complexRooted = new ComplexRootedPolynomial(new Complex[]{
				new Complex(5, 1),
				new Complex(1, 1),
				new Complex(2, 0),
				new Complex(3, -1)
			});
		
			result = complexRooted.apply(new Complex(1,2));
		assertEquals(-24, result.re, 0.0000001);
		assertEquals(23, result.im, 0.0000001);
	}
	
	@Test
	public void indexOfClosestRootFor() {
		ComplexRootedPolynomial complexRooted = new ComplexRootedPolynomial(new Complex[]{
			new Complex(5, 1),
			new Complex(1, 1),
			new Complex(2, 0),
		});
		
		int result = complexRooted.indexOfClosestRootFor(new Complex(2,2), 2.0);
		assertEquals(1, result);
		
		result = complexRooted.indexOfClosestRootFor(new Complex(2,2), 1.0);
		assertEquals(-1, result);
	}
	
	@Test
	public void testIndexToString() {
		ComplexRootedPolynomial complexRooted = new ComplexRootedPolynomial(new Complex[]{
			new Complex(5, 1),
			new Complex(1, 1),
			new Complex(2, 0),
		});
		
		assertEquals("f(z) = (z-(5+i))*(z-(1+i))*(z-2)", complexRooted.toString());	
	}
	
	@Test
	public void testToComplexPolynom() {
		ComplexRootedPolynomial complexRooted = new ComplexRootedPolynomial(new Complex[]{
			new Complex(5, 1),
			new Complex(1, 1),
			new Complex(2, 0),
			new Complex(3, -1)
		});
		
		ComplexPolynomial expected = new ComplexPolynomial(new Complex[]{
				new Complex(36, 28),
				new Complex(-66, -26),
				new Complex(42, 8),
				new Complex(-11, -1),
				new Complex(1, 0)
			});
		
		assertTrue(ComplexPolynomialTest.compare(expected, complexRooted.toComplexPolynom()));
		
		assertEquals(complexRooted.apply(new Complex(1,2)).im, expected.apply(new Complex(1,2)).im, 0.00001);
		
		complexRooted = new ComplexRootedPolynomial(new Complex[]{
				new Complex(1, 0),
				new Complex(-1, 0),
				new Complex(0, 1),
				new Complex(0, -1)
			});
		
		expected = new ComplexPolynomial(new Complex[]{
				new Complex(-1, 0),
				new Complex(0, 0),
				new Complex(0, 0),
				new Complex(0, 0),
				new Complex(1, 0)
			});
		//assertTrue(ComplexPolynomialTest.compare(expected, complexRooted.toComplexPolynom()));
	}

}
