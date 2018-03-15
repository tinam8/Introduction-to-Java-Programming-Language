package hr.fer.zemris.math;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Class that represents complex numbers.
 * 
 * @author tina
 *
 */
public class Complex {
	/** real part */
	double re;
	/** Imaginary part */
	double im;
	/** Imaginary and real part zero */
	public static final Complex ZERO = new Complex(0, 0);
	/** Real one */
	public static final Complex ONE = new Complex(1, 0);
	/** Real minus one */
	public static final Complex ONE_NEG = new Complex(-1, 0);
	/** Imaginary one */
	public static final Complex IM = new Complex(0, 1);
	/** Imaginaru minus one */
	public static final Complex IM_NEG = new Complex(0, -1);

	/**
	 * Default constructor
	 */
	public Complex() {

	}

	/**
	 * Constructor with two parameters.
	 * 
	 * @param re
	 *            real part
	 * @param im
	 *            imaginary part
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}

	/**
	 * Method that returns module of complex number
	 * 
	 * @return module of complex number
	 */
	public double module() {
		return Math.sqrt(re * re + im * im);
	}

	/**
	 * Method that returns multiply two complex numbers.
	 * 
	 * @param c
	 *            other complex number
	 * @return new instance that represents result
	 */
	public Complex multiply(Complex c) {
		return new Complex(re * c.re - im * c.im, re * c.im + im * c.re);
	}

	/**
	 * Method that divides two complex numbers.
	 * 
	 * @param c
	 *            divider
	 * @return new instance that represents the result
	 */
	public Complex divide(Complex c) {
		Complex conjugate = new Complex(c.re, -c.im);
		Complex numerator = this.multiply(conjugate);
		double divisor = c.multiply(conjugate).re;

		return new Complex(numerator.re / divisor, numerator.im / divisor);
	}

	/**
	 * Method that adds two complex numbers.
	 * 
	 * @param c
	 *            other complex number
	 * @return new instance that represents the result
	 */
	public Complex add(Complex c) {
		return new Complex(re + c.re, im + c.im);
	}

	/**
	 * Method that subtracts two complex numbers.
	 * 
	 * @param c
	 *            subtracter
	 * @return new instance that represents the result
	 */
	public Complex sub(Complex c) {
		return new Complex(re - c.re, im - c.im);
	}

	/**
	 * Method that negates complex number
	 * 
	 * @return negated complex number
	 */
	public Complex negate() {
		return new Complex(re, -im);
	}

	/**
	 * Instance method that calculates n-th power of complex number.
	 * 
	 * @param n
	 *           non-negative integer
	 * @return calculated result
	 */
	public Complex power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Power can not be negative!");
		}

		double magnitude = Math.pow(getMagnitude(), n);
		double angle = n * getAngle();

		return fromMagnitudeAndAngle(magnitude, angle);
	}

	/**
	 * Method that returns n-th root of complex number
	 * 
	 * @param n
	 *            positive integer
	 * @return list of n-th roots
	 */
	public List<Complex> root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("Wanted root should be positive number!");
		}

		double magnitude = getMagnitude();
		double angle = getAngle();
		List<Complex> roots = new ArrayList<>();

		magnitude = Math.pow(magnitude, 1.0 / n);
		angle = angle / (double) n;

		for (int i = 0; i < n; i++) {
			roots.add(fromMagnitudeAndAngle(magnitude, angle));
			angle += (2 * Math.PI) / (double) n;
		}

		return roots;
	}

	/**
	 * Supplementary method that returns magnitude of complex number.
	 * 
	 * @return magnitude of complex number in polar representation
	 */
	double getMagnitude() {
		return Math.sqrt(Math.pow(re, 2) + Math.pow(im, 2));
	}

	/**
	 * Supplementary method that returns angle of complex number (angle is in radians, from 0
	 * to 2 Pi).
	 * 
	 * @return angle of complex number in polar representation
	 */
	double getAngle() {
		double angle = Math.atan2(im, re);

		if (angle < 0) {
			return angle + Math.PI * 2;
		}

		return angle;
	}
	
	/**
	 * Suplementary factory method that creates new instance of ComplexNumber from magnitude
	 * and angle in polar representation.
	 * 
	 * @param magnitude
	 *            magnitude of complex number in polar representation; double
	 * @param angle
	 *            angle of complex number in polar representation (radians);
	 *            double
	 * @return new instance of complex number cartesian representation
	 */
	public static Complex fromMagnitudeAndAngle(double magnitude, double angle) {
		return new Complex(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
	}
	
	@Override
	public String toString() {
		DecimalFormat formaterReal = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		formaterReal.applyPattern("0.#####;-0.#####");
		DecimalFormat formaterImaginary = (DecimalFormat) NumberFormat.getInstance(Locale.US);
		formaterImaginary.applyPattern("0.#####i;-0.#####i");

		String realPart = re == 0 ? "" : formaterReal.format(re);

		String imaginaryPart;
		if (im == 1) {
			imaginaryPart = "i";
		} else if (im == -1) {
			imaginaryPart = "-i";
		} else if (im == 0) {
			imaginaryPart = "";
		} else {
			imaginaryPart = formaterImaginary.format(im		);
		}

		String operator = re != 0 && im > 0 ? "+" : "";

		return String.format(realPart + operator + imaginaryPart);
	}
	

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Complex)) {
			return false;
		}
		
		Complex other = (Complex) obj;
		
		if (im != other.im || re != other.re) {
			return false;
		}
		return true;
	}
	
	
}