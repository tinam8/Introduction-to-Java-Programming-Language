package hr.fer.zemris.java.hw02;

import static org.junit.Assert.*;

import org.junit.Test;


public class ComplexNumberTest {

	@Test
	public void fromRealMethod() {
		ComplexNumber number = ComplexNumber.fromReal(5);
		assertEquals(5, number.getReal(), 1e-15);
		String s = number.toString();
		assertEquals("5", s);
	}
	
	@Test
	public void fromImaginaryMethod() {
		ComplexNumber number = ComplexNumber.fromImaginary(5.32);
		assertEquals(5.32, number.getImaginary(), 1e-5);
		String s = number.toString();
		assertEquals("5.32i",s);
	}

	@Test
	public void fromMagnitudeAndAngleMethod() {
		ComplexNumber number = ComplexNumber.fromMagnitudeAndAngle(2, 1.57);
		assertEquals(0.00159265342147, number.getReal(), 1e-5);
		assertEquals(2, number.getImaginary(), 1e-5);	
	}
	
	@Test
	public void fromParseMethod() {
		ComplexNumber number = ComplexNumber.parse("3.51");
		assertEquals(3.51, number.getReal(), 1e-15);
		assertEquals(0, number.getImaginary(), 1e-15);
		
		number = ComplexNumber.parse("-3.17");
		assertEquals(-3.17, number.getReal(), 1e-15);
		assertEquals(0, number.getImaginary(), 1e-15);
		
		number = ComplexNumber.parse( "-2.71i");
		assertEquals(0, number.getReal(), 1e-15);
		assertEquals(-2.71, number.getImaginary(), 1e-15);
		
		number = ComplexNumber.parse("i");
		assertEquals(0, number.getReal(), 1e-15);
		assertEquals(1, number.getImaginary(), 1e-15);
		
		number = ComplexNumber.parse("1");
		assertEquals(1, number.getReal(), 1e-15);
		assertEquals(0, number.getImaginary(), 1e-15);
		
		number = ComplexNumber.parse("-2.71-3.15i");
		assertEquals(-2.71, number.getReal(), 1e-15);
		assertEquals(-3.15, number.getImaginary(), 1e-15);
	}
	
	@Test
	public void getRealGetImaginary() {
		ComplexNumber number = new ComplexNumber(2, 1.57);
		assertEquals(2, number.getReal(), 1e-5);
		assertEquals(1.57, number.getImaginary(), 1e-5);	
	}

	
	@Test
	public void getMagnitudeGetAngle() {
		ComplexNumber number = ComplexNumber.fromMagnitudeAndAngle(2, 1.57 );
		double magnitude = number.getMagnitude();
		double angle = number.getAngle();

		assertEquals(2, magnitude, 1e-15);
		assertEquals(1.57, angle, 1e-15);
	}
	
	@Test
	public void addMethod() {
		ComplexNumber number1 = new ComplexNumber(5, 3.5);
		ComplexNumber number2 = new ComplexNumber(3, 1);
		ComplexNumber number3 = new ComplexNumber(-1.5, -1);
 		
		number1 = number1.add(number2);
		assertEquals(8, number1.getReal(), 1e-15);
		assertEquals(4.5, number1.getImaginary(), 1e-15);
		
		number1 = number1.add(number3);
		assertEquals(6.5, number1.getReal(), 1e-15);
		assertEquals(3.5, number1.getImaginary(), 1e-15);
	}
	
	@Test
	public void subMethod() {
		ComplexNumber number1 = new ComplexNumber(5, 3.5);
		ComplexNumber number2 = new ComplexNumber(3, 1);
		ComplexNumber number3 = new ComplexNumber(-1.5, -1);
 		
		number1 = number1.sub(number2);
		assertEquals(2, number1.getReal(), 1e-5);
		assertEquals(2.5, number1.getImaginary(), 1e-5);
		
		number1 = number1.sub(number3);
		assertEquals(3.5, number1.getReal(), 1e-5);
		assertEquals(3.5, number1.getImaginary(), 1e-5);
	}
	
	@Test
	public void mulMethod() {
		ComplexNumber number1 = new ComplexNumber(5, 3.5);
		ComplexNumber number2 = new ComplexNumber(3, 1);
		
		number1 = number1.mul(number2);
		assertEquals(11.5, number1.getReal(), 1e-5);
		assertEquals(15.5, number1.getImaginary(), 1e-5);
	}
	
	@Test
	public void divMethod() {
		ComplexNumber number1 = new ComplexNumber(5, 3.5);
		ComplexNumber number2 = new ComplexNumber(3, -1);
		
		number1 = number1.div(number2);
		assertEquals(1.15, number1.getReal(), 1e-5);
		assertEquals(1.55, number1.getImaginary(), 1e-5);
	}
	
	@Test
	public void powerMethod() {
		ComplexNumber number1 = new ComplexNumber(2, 3);
		number1 = number1.power(3);
		assertEquals(-46, number1.getReal(), 1e-5);
		assertEquals(9, number1.getImaginary(), 1e-5);
	}
	
	@Test
	public void rootMethod() {
		ComplexNumber number1 = new ComplexNumber(2, 1);
		ComplexNumber[] roots = number1.root(2);
		assertEquals(1.4553 , roots[0].getReal(), 1e-2);
		assertEquals(0.3436 , roots[0].getImaginary(), 1e-2);
		assertEquals(-1.4553, roots[1].getReal(), 1e-2);
		assertEquals(-0.3436 , roots[1].getImaginary(), 1e-2);
	}
	
	@Test
	public void toStringMethod() {
		ComplexNumber number1 = new ComplexNumber(2, 1);
		assertEquals("2+i", number1.toString());
		number1 = new ComplexNumber(0, -1);
		assertEquals("-i", number1.toString());
		number1 = new ComplexNumber(2, 0);
		assertEquals("2", number1.toString());
		number1 = new ComplexNumber(-2, -1);
	}
	
}
