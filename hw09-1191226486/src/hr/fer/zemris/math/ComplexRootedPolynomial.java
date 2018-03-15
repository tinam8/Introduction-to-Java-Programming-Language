package hr.fer.zemris.math;

/**
 * Class that represents polynomial with complex coefficients in given form:
 * <br>
 * f(z) = (z-z1)*(z-z2)*...*(z-zn)
 * 
 * 
 * @author tina
 *
 */
public class ComplexRootedPolynomial {
	/** Roots of polynomial */
	private Complex[] roots;

	/**
	 * Constructor
	 * 
	 * @param roots
	 *            polynomial roots
	 */
	public ComplexRootedPolynomial(Complex... roots) {
		if (roots == null) {
			throw new IllegalArgumentException("Argument can not be null");
		}
		if (roots.length < 1) {
			throw new IllegalArgumentException("Number of roots shoud be positive.");
		}

		this.roots = roots;
	}

	/**
	 * Method that computes polynomial value at given point z.
	 * 
	 * @param z
	 *            complex number
	 * @return polynomial value
	 */
	public Complex apply(Complex z) {
		Complex value = z.sub(roots[0]);

		for (int i = 1; i < roots.length; i++) {
			value = value.multiply(z.sub(roots[i]));
		}

		return value;
	}

	/**
	 * Method that converts this representation to ComplexPolynomial type.
	 * 
	 * @return polynomial in next form: <br>
	 *         f(z) = zn*zn+zn-1*zn-1+...+z2*z2+z1*z+z0
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial complexPolynomial = new ComplexPolynomial(new Complex[]{ roots[0].multiply(new Complex(-1,0)), new Complex (1, 0)});

		for (int i = 1; i < roots.length; i++) {
			complexPolynomial = complexPolynomial.multiply(new ComplexPolynomial(new Complex []{roots[i].multiply(new Complex(-1,0)), new Complex (1, 0)}));
		}
		
		return complexPolynomial;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("f(z) = ");
		for (int i = 0; i < roots.length; i++) {
			if (roots[i].im != 0 && roots[i].re != 0) {
				sb.append("(z-(" + roots[i] + "))");
			} else if (roots[i].re == 0 && roots[i].im == 0) {
				sb.append("z");
				continue;
			} else  {
				if(roots[i].re > 0 || roots[i].im > 0) {
					sb.append("(z-" + roots[i] + ")");
				}else {
					sb.append("(z+" + (roots[i].multiply(new Complex(-1,0))) + ")");
				}	
			}
			
			if (i != roots.length - 1) {
				sb.append("*");
			}
		}

		return sb.toString();
	}

	/**
	 * Method that finds index of closest root for given complex number z that
	 * is within treshold; if there is no such root, returns -1
	 * 
	 * @param z
	 *            given complex number
	 * @param treshold
	 *            treshold
	 * @return index of closest root for given complex number z that is within
	 *         treshold; if there is no such root, returns -1
	 */
	// finds index of closest root for given complex number z that is within
	// treshold; if there is no such root, returns -1
	public int indexOfClosestRootFor(Complex z, double treshold) {
		int minIndex = -1;
		double minDistance = -1;
		for (int i = 0; i < roots.length; i++) {
			double distance = Math.abs(roots[i].sub(z).module());

			if (minDistance == -1) {
				minDistance = distance;
				minIndex = i;
				continue;
			}
			if (distance < minDistance) {
				minDistance = distance;
				minIndex = i;
			}
		}

		if (minDistance > treshold) {
			return -1;
		}

		return minIndex;
	}

}
