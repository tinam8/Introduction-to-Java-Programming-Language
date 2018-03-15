package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.hw02.ComplexNumber;

/**
 * Class that demonstrates methods of {@link ComplexNumber} class
 * 
 * @author tina
 *
 */
public class ComplexDemo {

	/**
	 * Method which starts program.
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		System.out.println(c1.toString());
		ComplexNumber c2 = ComplexNumber.parse("2.5-i");
		ComplexNumber c3 = c1.add(ComplexNumber.fromMagnitudeAndAngle(2, 1.57)).div(c2).power(3).root(2)[1];
		System.out.println(c3);

	}

}
