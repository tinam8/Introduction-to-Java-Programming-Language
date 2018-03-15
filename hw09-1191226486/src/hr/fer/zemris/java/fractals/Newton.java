package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Class that draws fractals derived from Newton-Raphson iteration. <br>
 * xn+1 = xn− f (xn) / f '(xn) <br>
 * Complex polynomial functions are considered in this class.
 * 
 * User enters at least two roots, one root per line. <br>
 * Enters 'done' when done. <br>
 * Example of usage: <br>
 * Root 1> 1 <br>
 * Root 2> -1 + i0 <br>
 * Root 3> i <br>
 * Root 4> 0 - i1 <br>
 * Root 5> done <br>
 * <br>
 * 
 * General syntax for complex numbers is of form a+ib or a-ib where parts that
 * are zero can be dropped, but not both (empty string is not legal complex
 * number); for example, zero can be given as 0, i0, 0+i0, 0-i0. If there is 'i'
 * present but no b is given, it is assumed that b=1.
 * 
 * @author tina
 *
 */
public class Newton {

	/** Polynomial on which the Newton-Raphson iterations will be calculated. */
	public static ComplexRootedPolynomial polynomial;
	/** Derived polynomial. */
	public static ComplexPolynomial derived;

	/**
	 * Method that starts program
	 * 
	 * 
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		int rootNumber = 1;
		Scanner sc = new Scanner(System.in);
		List<Complex> roots = new ArrayList<>();
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");

		while (true) {
			System.out.printf("Root %d> ", rootNumber);
			if (sc.hasNextLine()) {
				String input = sc.nextLine();
				if (input.equals("")) {
					continue;
				}
				
				if (input.equals("done")) {
					if (roots.size() < 2) {
						System.out.println("Please enter at least two roots.");
						continue;
					}
					
					System.out.println("Image of fractal will appear shortly. Thank you.");

					Complex[] complexArray = roots.toArray(new Complex[roots.size()]);

					polynomial = new ComplexRootedPolynomial(complexArray);
					derived = polynomial.toComplexPolynom().derive();

					System.out.println(polynomial.toString());

					FractalViewer.show(new MyProducer());

					sc.close();
					return;

				}

				try {
					Complex root = parseArgument(input);
					if (roots.contains(root)) {
						System.out.println("Root has been given.");
						continue;
					}
					
					roots.add(parseArgument(input));
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
					continue;
				}

			}

			rootNumber++;
		}

	}

	/**
	 * Class for calculating Newton-Raphson iterations.
	 * 
	 * @author tina
	 *
	 */
	public static class NRCalculation implements Callable<Void> {
		/** Maximum iterations for Newton-Rapshon computation. **/
		private static final int MAX_ITERATIONS = 16;
		/** Convergence threshold for Newton-Rapshon computation. **/
		private static final double CONVERGENCE_THRESHOLD = 0.001;
		/** Root threshold for Newton-Rapshon computation. **/
		private static final double ROOT_THRESHOLD = 0.002;
		/** Minimum real value. */
		double reMin;
		/** Maximum real value. */
		double reMax;
		/** Minimum imaginary value. */
		double imMin;
		/** Maximum imaginary value. */
		double imMax;
		/** Screen width. */
		int width;
		/** Screen height. */
		int height;
		/** Minimum value of Y-axis per block. */
		int yMin;
		/** Maximum value of Y-axis per block. */
		int yMax;
		/** Array for storing the results. */
		short[] data;

		/**
		 * Constructor that sets class variables
		 * 
		 * @param reMin
		 *            Minimum real value.
		 * @param reMax
		 *            Maximum real value.
		 * @param imMin
		 *            Minimum imaginary value.
		 * @param imMax
		 *            Maximum imaginary value.
		 * @param width
		 *            Screen width.
		 * @param height
		 *            Screen height.
		 * @param yMin
		 *            Minimum value of Y-axis per block.
		 * @param yMax
		 *            Maximum value of Y-axis per block.
		 * @param data
		 *            Array for storing the results.
		 */
		public NRCalculation(double reMin, double reMax, double imMin, double imMax, int width, int height, int yMin,
				int yMax, short[] data) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.data = data;
		}

		@Override
		public Void call() {
			int offset = yMin * width;

			for (int y = yMin; y <= yMax; y++) {
				for (int x = 0; x < width; x++) {
					Complex c = map_to_complex_plain(x, y, 0, width, yMin, height, reMin, reMax, imMin, imMax);
					Complex zn = c;
					double module;
					int iter = 0;

					do {
						Complex numerator = polynomial.apply(zn);
						Complex denominator = derived.apply(zn);
						Complex fraction = numerator.divide(denominator);
						Complex zn1 = zn.sub(fraction);
						module = zn1.sub(zn).module();
						zn = zn1;
					} while (module > CONVERGENCE_THRESHOLD && iter < MAX_ITERATIONS);

					int index = polynomial.indexOfClosestRootFor(zn, ROOT_THRESHOLD);
					if (index == -1) {
						data[offset++] = 0;
					} else {
						data[offset++] = (short) (index + 1);
					}
				}
			}
			return null;
		}

		/**
		 * Supplementary method for finding Complex point for given x and y
		 * coordinate.
		 * 
		 * @param x
		 *            x coordinate
		 * @param y
		 *            y coordinate
		 * @param xMin
		 *            screen minimum x coordinate
		 * @param xMax
		 *            screen maximum x coordinate
		 * @param yMin
		 *            screen minimum y coordinate
		 * @param yMax
		 *            screen maximum y coordinate
		 * @param reMin
		 *            minimum real value
		 * @param reMax
		 *            maximum real value
		 * @param imMin
		 *            minimum imaginary value.
		 * @param imMax
		 *            maximum imaginary value.
		 * @return complex point corresponding to given screen (x, y) point
		 */
		private static Complex map_to_complex_plain(int x, int y, int xMin, int xMax, int yMin, int yMax, double reMin,
				double reMax, double imMin, double imMax) {
			double cRe = x / (xMax - 1.0) * (reMax - reMin) + reMin;
			double cIm = (yMax - 1.0 - y) / (yMax - 1.0) * (imMax - imMin) + imMin;
			return new Complex(cRe, cIm);
		}
	}

	/**
	 * 
	 * @author tina
	 *
	 */
	public static class MyProducer implements IFractalProducer {

		/** Number of blocks (jobs) */
		private final int numberOfBlocks;
		/** Pool of threads that will be used in fractal production */
		private ExecutorService pool;

		/**
		 * Constructor that creates pool of Threads and defines number of jobs.
		 */
		public MyProducer() {
			numberOfBlocks = 8 * (Runtime.getRuntime().availableProcessors());
			pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(),
					new DaemonicThreadFactory());
		}

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height,
				long requestNo, IFractalResultObserver observer) {
			System.out.println("Zapocinjem izracun...");

			short[] data = new short[width * height];
			int yPerBlock = height / numberOfBlocks;

			List<Future<Void>> results = new ArrayList<>();

			for (int i = 0; i < numberOfBlocks; i++) {
				int yMin = i * yPerBlock;
				int yMax = (i + 1) * yPerBlock - 1;
				if (i == numberOfBlocks - 1) {
					yMax = height - 1;
				}
				NRCalculation job = new NRCalculation(reMin, reMax, imMin, imMax, width, height, yMin, yMax, data);
				results.add(pool.submit(job));
			}
			for (Future<Void> job : results) {
				try {
					job.get();
				} catch (InterruptedException | ExecutionException e) {
				}
			}
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short) (polynomial.toComplexPolynom().order() + 1), requestNo);
		}
	}

	/**
	 * Class for creating deamonic thread.
	 * 
	 * @author tina
	 *
	 */
	public static class DaemonicThreadFactory implements ThreadFactory {

		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r);
			t.setDaemon(true);
			return t;
		}

	}

	/**
	 * Method that parses user input.
	 * 
	 * @param input
	 *            string input
	 * @return complex number id input is valid
	 * @throws IllegalArgumentException
	 *             if input is invalid
	 */
	private static Complex parseArgument(String input) {
		input = input.replaceAll("\\s+", "");
		char[] data = input.toCharArray();
		StringBuilder real = new StringBuilder("");
		StringBuilder imaginary = new StringBuilder("");
		boolean re = true;
		boolean im = false;

		for (int i = 0; i < data.length; i++) {
			if (data[i] == '+' || data[i] == '-') {
				if (data.length == 1 || im) {
					throw new IllegalArgumentException("Invalid input.");
				}

				if (i < data.length - 1) {
					if (data[i + 1] == 'i') {
						imaginary.append(data[i]);
						re = false;
						im = true;
					} else {
						real.append(data[i]);
					}
				} else {
					throw new IllegalArgumentException("Ivalid input.");
				}

				continue;
			}

			if (data[i] == 'i') {
				if (i != 0 && re) {
					throw new IllegalArgumentException("Invalid input fist i and than number.");
				}
				if (i == 0) {
					re = false;
				}
				if (i == data.length - 1 && !re) {
					imaginary.append("1");
					break;
				}

				im = true;
				continue;
			}

			if (im) {
				imaginary.append(data[i]);
			} else {
				real.append(data[i]);
			}

		}

		double reValue;
		double imValue;
		try {
			reValue = real.toString().equals("") ? 0 : Double.parseDouble(real.toString());
			imValue = imaginary.toString().equals("") ? 0 : Double.parseDouble(imaginary.toString());
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid input.");
		}

		return new Complex(reValue, imValue);
	}

}
