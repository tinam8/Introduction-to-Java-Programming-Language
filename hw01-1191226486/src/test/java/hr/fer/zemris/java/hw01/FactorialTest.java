package hr.fer.zemris.java.hw01;

import static org.junit.Assert.*;

import org.junit.Test;


public class FactorialTest {

	
	@Test
	public void brojeviUDozvoljenomRasponu() {
		assertEquals(Factorial.calculateFactorial(1), 1);
		assertEquals(Factorial.calculateFactorial(5), 120);
		assertEquals(Factorial.calculateFactorial(15), 1307674368000L);
	}

	@Test
	public void rubniSlucajevi() {
		assertEquals(Factorial.calculateFactorial(0), 1);
		assertEquals(Factorial.calculateFactorial(20), 2432902008176640000L);
	}


	@Test
	public void nedopusteniBrojevi() {
		assertEquals(Factorial.calculateFactorial(-1), -1);
		assertEquals(Factorial.calculateFactorial(21), -1);
	}
	
}
