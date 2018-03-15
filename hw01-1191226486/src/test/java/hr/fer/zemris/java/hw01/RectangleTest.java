package hr.fer.zemris.java.hw01;

import static org.junit.Assert.*;

import org.junit.Test;



public class RectangleTest {


	@Test
	public void testArea() {
		assertEquals(Rectangle.calculateArea(2, 8), 16, 1e-15);
		assertEquals(Rectangle.calculateArea(2.1, 8), 16.8, 1e-15);
		assertEquals(Rectangle.calculateArea(2.1, 2.1), 4.41, 1e-15);
	}
	
	@Test
	public void testPerimeter() {
		assertEquals(Rectangle.calculatePerimeter(2, 8), 20, 1e-15);
		assertEquals(Rectangle.calculatePerimeter(2.1, 8), 20.2, 1e-15);
		assertEquals(Rectangle.calculatePerimeter(2.1, 2.1), 8.4, 1e-15);
	}

}
