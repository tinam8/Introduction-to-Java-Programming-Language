package hr.fer.zemris.math;

/**
 * Class that represents polynomials in next form: <br>
 * f(z) = zn*zn+zn-1*zn-1+...+z2*z2+z1*z+z0
 * 
 * @author tina
 *
 */
public class ComplexPolynomial {
	/** Polynomial coefficients. At first position is lowest degree of polynomial 0 */
	private Complex[] factors;

	/**
	 * Constructor with one argument.
	 * 
	 * @param factors
	 *            Polynomial coefficients
	 */
	public ComplexPolynomial(Complex... factors) {
		if (factors == null) {
			throw new IllegalArgumentException("Argument can not be null");
		}
		if (factors.length < 1) {
			throw new IllegalArgumentException("Number of factors shoud be positive.");
		}
		
		this.factors = factors;
	}

	/**
	 * Method that returns order of this polynomial;<br>
	 * eg. For (7+2i)z^3+2z^2+5z+1 returns 3
	 * 
	 * @return order of polynomial
	 */
	public short order() {
		return (short) (factors.length - 1);
	}

	/**
	 * Method that returns coefficient at given position.
	 * 
	 * @param index
	 *            position
	 * @return complex coefficient of given position
	 * @throws IllegalArgumentException
	 *             if index is invalid
	 */
	public Complex getFactorAt(int index) {
		if (index < 0 || index > (factors.length - 1)) {
			throw new IllegalArgumentException("Index out of bounds.");
		}

		return factors[index];
	}
	
	
	/**
	 * Method that computes a new polynomial this*p (multiplication)
	 * 
	 * @param p
	 *            another polynomial
	 * @return result of multiplication
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		ComplexPolynomial greaterOder;
		ComplexPolynomial lessOrder;
		
		if( order() > p.order() ){
			greaterOder = this;
			lessOrder = p;
		} else {
			greaterOder = p;
			lessOrder = this;
		}
		
		Complex[] multyplyFactors = new Complex[order()*p.order()+2];
		
		for (int i = 0; i < greaterOder.order() + 1 ; i++) {
			for (int j = 0; j < lessOrder.order() +1 ; j++) {
				if (multyplyFactors[i+j] == null) {
					multyplyFactors[i+j] = greaterOder.getFactorAt(i).multiply(lessOrder.getFactorAt(j));
					continue;
				}
				
				multyplyFactors[i+j] = multyplyFactors[i+j].add(greaterOder.getFactorAt(i).multiply(lessOrder.getFactorAt(j)));
			}
		}
		
		return new ComplexPolynomial(multyplyFactors);
	}

	/**
	 * Method that computes first derivative of this polynomial;<br>
	 * for example, for (7+2i)z^3+2z^2+5z+1 returns (21+6i)z^2+4z+5
	 * 
	 * @return first derivative
	 */
	//
	public ComplexPolynomial derive() {
		Complex[] deriveFactors= new Complex[factors.length-1];
		for (int i = 1; i < factors.length; i++) {
			deriveFactors[i-1] = new Complex(factors[i].re * i, factors[i].im * i);
		}
		
		return new ComplexPolynomial(deriveFactors);
	}

	/**
	 * Method that computes polynomial value at given point z
	 * 
	 * @param z
	 *            complex number (point)
	 * @return result of computation
	 */
	public Complex apply(Complex z) {
		Complex value = null;
		for (int i = 0; i < factors.length; i++) {
			if(i == 0) {
				value = factors[0];
				continue;
			} 
			
			value = value.add(factors[i].multiply(z.power(i)));
		}
		
		return value;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("f(z) = ");
		for (int i = 0; i < factors.length; i++) {
			if (factors[i].re== 0 && factors[i].im == 0){
				continue;
			}
			
			if (factors[i].im != 0 && factors[i].re != 0) {
				sb.append("(" + factors[i] + ")");
			} else {
				sb.append(factors[i]);
			}
			
			if (i!=0) {
				if (i == 1) {
					sb.append("*z");
				} else {
					sb.append("*z^"+ (i));
				}
				
				if (i!=factors.length-1) {
					sb.append("+");
				}
			} else {
				sb.append("+");
			}
		}
		
		return sb.toString();
	}
}