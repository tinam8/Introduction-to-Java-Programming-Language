package hr.fer.zemris.java.hw02;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Class which represents an unmodifiable complex number
 * 
 * @author Tina Maric
 *
 */
public class ComplexNumber {
	/**
	 * real part of complex number
	 */
	private double real;
	/**
	 * imaginary part of complex number
	 */
	private double imaginary;

	/**
	 * Constructor with two arguments
	 * 
	 * @param real
	 *            real part of complex number; double
	 * 
	 * @param imaginary
	 *            imaginary part of complex number; double
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * Factory method that creates new instance of ComplexNumber whose imaginary
	 * part is zero
	 * 
	 * @param real
	 *            real pat of complex number
	 * @return new instance of ComplexNumber
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}

	/**
	 * Factory method that creates new instance of ComplexNumber whose real part
	 * is zero
	 * 
	 * @param imaginary
	 *            imaginary pat of complex number
	 * @return new instance of ComplexNumber
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}

	/**
	 * Factory method that creates new instance of ComplexNumber from magnitude
	 * and angle in polar representation.
	 * 
	 * @param magnitude
	 *            magnitude of complex number in polar representation; double
	 * @param angle
	 *            angle of complex number in polar representation (radians);
	 *            double
	 * @return new instance of complex number cartesian representation
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		return new ComplexNumber(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
	}

	/**
	 * Factory method that creates new instance of ComplexNumber from strings
	 * such as: "3.51", "-3.17", "-2.71i", "i", "1", "-2.71-3.15i".
	 * 
	 * @param s
	 *            string that represents complex number
	 * @return new instance of ComplexNumber
	 */

	public static ComplexNumber parse(String s) {
		boolean firstPositive = true;
		boolean secondPositive = true;

		if (s.charAt(0) == '-') // See if first expr is negative
			firstPositive = false;
		if (s.substring(1).contains("-"))
			secondPositive = false;
		
		String[] split = s.split("[+-]");
		int length = split.length;
		if (split[0].equals("")) {
			split[0] = split[1];
			if(length == 3) {
				split[1] = split[2];
				length = 2;
			} else {
				length = 1;
			}
			
		}

		double realPart = 0;
		double imgaginaryPart = 0;
		if (split[0].contains("i")) {
			if (split[0].length() == 1) {
				imgaginaryPart = firstPositive ?  1 : -1;
			} else {
				imgaginaryPart = Double
						.parseDouble((firstPositive ? "+" : "-") + split[0].substring(0, split[0].length() - 1));
			}	
		} else {
			realPart = Double.parseDouble((firstPositive ? "+" : "-") + split[0]);
		}
			
		if (length > 1) {
			if (split[1].contains("i")) {
				if (split[1].length() == 1) {
					imgaginaryPart = secondPositive ?  1 : -1;
				} else {
					imgaginaryPart = Double
							.parseDouble((secondPositive ? "+" : "-") + split[1].substring(0, split[1].length() - 1));
				}
			} else
				realPart = Double.parseDouble((secondPositive ? "+" : "-") + split[1]);
		}

		return new ComplexNumber(realPart, imgaginaryPart);
	}

	/**
	 * Method that returns real part of complex number
	 * 
	 * @return real part of complex number; double
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Method that returns imaginary part of complex number.
	 * 
	 * @return imaginary part of complex number; double
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**
	 * Method that returns magnitude of complex number.
	 * 
	 * @return magnitude of complex number in polar representation
	 */
	double getMagnitude() {
		return Math.sqrt(Math.pow(real, 2) + Math.pow(imaginary, 2));
	}

	/**
	 * Method that returns angle of complex number (angle is in radians, from 0
	 * to 2 Pi).
	 * 
	 * @return angle of complex number in polar representation
	 */
	double getAngle() {
		double angle = Math.atan2(imaginary, real);

		if (angle < 0) {
			return angle + Math.PI * 2;
		}

		return angle;
	}

	/**
	 * Instance method that calculates addition of two complex numbers.
	 * 
	 * @param c
	 *            complex number to be added
	 * @return result of addition; ComplexNumber
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(real + c.real, imaginary + c.imaginary);
	}

	/**
	 * Instance method that calculates subtraction of two complex numbers.
	 * Instance that is calling method is being substracted by complex number
	 * passed as argument.
	 * 
	 * @param c
	 *            subtrahend
	 * @return result of substraction; ComplexNumber
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(real - c.real, imaginary - c.imaginary);
	}

	/**
	 * Instance method that calculates multiplication of two complex numbers.
	 * 
	 * @param c
	 *            complex number to be miltiplied with instance tjat is calling
	 *            methods
	 * @return result od multiplication
	 */
	public ComplexNumber mul(ComplexNumber c) {
		double realPart = real * c.real - imaginary * c.imaginary;
		double imaginaryPart = real * c.imaginary + imaginary * c.real;

		return new ComplexNumber(realPart, imaginaryPart);
	}

	/**
	 * Instance method that calculates division of two complex numbers. Instance
	 * that is calling method is being devided by complex number passed as
	 * argument.
	 * 
	 * @param c
	 *            divisor
	 * @return result of diviosin; ComplexNumber
	 */
	public ComplexNumber div(ComplexNumber c) {
		ComplexNumber conjugate = new ComplexNumber(c.real, -c.imaginary);
		ComplexNumber numerator = this.mul(conjugate);
		double divisor = c.mul(conjugate).getReal();

		return new ComplexNumber(numerator.getReal() / divisor, numerator.getImaginary() / divisor);
	}

	/**
	 * Instance method that calculates n-th power of complex number. n >= 0
	 * 
	 * @param n
	 *            power
	 * @return calculated result; ComplexNumber
	 */
	public ComplexNumber power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Power can not be negative!");
		}

		double magnitude = Math.pow(getMagnitude(), n);
		double angle = n * getAngle();

		return fromMagnitudeAndAngle(magnitude, angle);
	}

	/**
	 * Instance method that calculates n-th roots of complex number. n > 0
	 * 
	 * @param n
	 *            rooths that shoud be calculated; int
	 * @return array of roots; ComplexNumber[]
	 */
	public ComplexNumber[] root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("Power should be positive number!");
		}

		double magnitude = getMagnitude();
		double angle = getAngle();
		ComplexNumber[] roots = new ComplexNumber[n];

		magnitude = Math.pow(magnitude, 1.0 / n);
		angle = angle / (double) n;

		for (int i = 0; i < n; i++) {
			roots[i] = fromMagnitudeAndAngle(magnitude, angle);
			angle += (2 * Math.PI) / (double) n;
		}

		return roots;
	}

	@Override
	public String toString() {
		DecimalFormat formaterReal = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		formaterReal.applyPattern("0.#####;-0.#####");
		DecimalFormat formaterImaginary = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		formaterImaginary.applyPattern("0.#####i;-0.#####i");

		String realPart = real == 0 ? "" : formaterReal.format(real);

		String imaginaryPart;
		if (imaginary == 1) {
			imaginaryPart = "i";
		} else if (imaginary == -1) {
			imaginaryPart = "-i";
		} else if (imaginary == 0) {
			imaginaryPart = "";
		} else {
			imaginaryPart = formaterImaginary.format(imaginary);
		}

		String operator = real != 0 && imaginary > 0 ? "+" : "";

		return String.format(realPart + operator + imaginaryPart);
	}

}
