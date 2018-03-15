package hr.fer.zemris.math;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;


public class ComplexTest {

	@Test
	public void getMagnitudeGetAngle() {
		Complex number = Complex.fromMagnitudeAndAngle(2, 1.57 );
		double magnitude = number.getMagnitude();
		double angle = number.getAngle();

		assertEquals(2, magnitude, 1e-15);
		assertEquals(1.57, angle, 1e-15);
	}
	
	@Test
	public void addMethod() {
		Complex number1 = new Complex(5, 3.5);
		Complex number2 = new Complex(3, 1);
		Complex number3 = new Complex(-1.5, -1);
 		
		number1 = number1.add(number2);
		assertEquals(8, number1.re, 1e-15);
		assertEquals(4.5, number1.im, 1e-15);
		
		number1 = number1.add(number3);
		assertEquals(6.5, number1.re, 1e-15);
		assertEquals(3.5, number1.im, 1e-15);
	}
	
	@Test
	public void subMethod() {
		Complex number1 = new Complex(5, 3.5);
		Complex number2 = new Complex(3, 1);
		Complex number3 = new Complex(-1.5, -1);
 		
		number1 = number1.sub(number2);
		assertEquals(2, number1.re, 1e-5);
		assertEquals(2.5, number1.im, 1e-5);
		
		number1 = number1.sub(number3);
		assertEquals(3.5, number1.re, 1e-5);
		assertEquals(3.5, number1.im, 1e-5);
	}
	
	@Test
	public void mulMethod() {
		Complex number1 = new Complex(5, 3.5);
		Complex number2 = new Complex(3, 1);
		
		number1 = number1.multiply(number2);
		assertEquals(11.5, number1.re, 1e-5);
		assertEquals(15.5, number1.im, 1e-5);
	}
	
	@Test
	public void divMethod() {
		Complex number1 = new Complex(5, 3.5);
		Complex number2 = new Complex(3, -1);
		
		number1 = number1.divide(number2);
		assertEquals(1.15, number1.re, 1e-5);
		assertEquals(1.55, number1.im, 1e-5);
	}
	
	@Test
	public void powerMethod() {
		Complex number1 = new Complex(2, 3);
		number1 = number1.power(3);
		assertEquals(-46, number1.re, 1e-5);
		assertEquals(9, number1.im, 1e-5);
	}
	
	@Test
	public void rootMethod() {
		Complex number1 = new Complex(2, 1);
		List<Complex> roots = number1.root(2);
		assertEquals(1.4553 , roots.get(0).re, 1e-2);
		assertEquals(0.3436 , roots.get(0).im, 1e-2);
		assertEquals(-1.4553, roots.get(1).re, 1e-2);
		assertEquals(-0.3436 , roots.get(1).im, 1e-2);
	}

}
