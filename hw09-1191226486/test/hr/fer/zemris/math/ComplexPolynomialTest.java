package hr.fer.zemris.math;

import static org.junit.Assert.*;

import org.junit.Test;

public class ComplexPolynomialTest {

	@Test
	public void testOrder() {
		ComplexPolynomial complex = new ComplexPolynomial(new Complex[]{
				new Complex(5, 1),
				new Complex(1, 1),
				new Complex(2, 0),
				new Complex(2, 0),
				new Complex(2, 0),
				new Complex(2, 0),
			});
		assertEquals(5, complex.order());
	}
	
	@Test
	public void testApply() {
		ComplexPolynomial complex = new ComplexPolynomial(new Complex[]{
				new Complex(36, 28),
				new Complex(-66, -26),
				new Complex(42, 8),
				new Complex(-11, -1),
				new Complex(1, 0)
			});
		
		assertEquals(-24, complex.apply(new Complex(1,2)).re, 0.00001);
		assertEquals(23, complex.apply(new Complex(1,2)).im, 0.00001);
	}
	
	
	
	
	
	
	@Test
	public void testMutiply() {
		ComplexPolynomial first = new ComplexPolynomial(new Complex[]{
				new Complex(2, 1),
				new Complex(5, 1),
				new Complex(1, 1),
				new Complex(2, 0)
			});
		ComplexPolynomial second = new ComplexPolynomial(new Complex[]{
				new Complex(-2, 1),
				new Complex(1, 3)
			});
		
		ComplexPolynomial expected = new ComplexPolynomial(new Complex[]{
				new Complex(-5, 0),
				new Complex(-12, 10),
				new Complex(-1, 15),
				new Complex(-6, 6),
				new Complex(2, 6)
			});
		
		assertTrue(compare(expected, first.multiply(second)));
	}
	
	@Test
	public void testDerive() {
		ComplexPolynomial first = new ComplexPolynomial(new Complex[]{
				new Complex(1, 0),
				new Complex(5, 0),
				new Complex(2, 0),
				new Complex(7, 2)
			});
		ComplexPolynomial expected = new ComplexPolynomial(new Complex[]{
				new Complex(5, 0),
				new Complex(4, 0),
				new Complex(21, 6)
				
			});
		
		assertTrue(compare(expected, first.derive()));
		
		
		first  = new ComplexPolynomial(new Complex[]{
				new Complex(-1, 0),
				new Complex(0, 0),
				new Complex(0, 0),
				new Complex(0, 0),
				new Complex(1, 0)
			});
		
		 expected = new ComplexPolynomial(new Complex[]{
				new Complex(0, 0),
				new Complex(0, 0),
				new Complex(0, 0),
				new Complex(4, 0),
			});
		 
		 assertTrue(compare(expected, first.derive()));
	}
	
	/**
	 * Method that compares two  instances of ComplexPolynomial
	 * @param first first polynomial
	 * @param second second polynomial
	 * @return true if equal, false otherwise
	 */
	public static boolean compare(ComplexPolynomial first, ComplexPolynomial second) {
		if (first.order() != second.order()) {
			return false;
		}
		
		for (int i = 0; i < first.order()+1; i++) {
			if(first.getFactorAt(i).re != second.getFactorAt(i).re) {
				return false;
			}
			if(first.getFactorAt(i).im != second.getFactorAt(i).im) {
				return false;
			}
		}
		return true;
	}


}
