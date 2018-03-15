package hr.fer.zemris.java.hw05.demo2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class that contains a number of consecutive primes.
 * 
 * @author tina
 *
 */
public class PrimesCollection implements Iterable<Integer> {
	/** Number of primes that collection contains */
	private int numberOfPrimes;

	/**
	 * Constructor with one argument.
	 * 
	 * @param numberOfPrimes
	 *            how many primes should instance of class contain
	 * @throws IllegalArgumentException
	 *             if given argument is not nonnegative
	 * 
	 */
	public PrimesCollection(int numberOfPrimes) {
		if (numberOfPrimes < 0) {
			throw new IllegalArgumentException("Number of primes must be positive number.");
		}

		this.numberOfPrimes = numberOfPrimes;
	}

	@Override
	public Iterator<Integer> iterator() {
		return new primesIterator();
	}

	/**
	 * Class that implements iterator for iterating through primes in
	 * PrimesCollection.
	 */
	private class primesIterator implements Iterator<Integer> {
		/** Prime that will be returned when next method is called. */
		private Integer currentPrime = 1;
		/** How many primes has already been generated */
		private int numberOfGenerated = 0;

		@Override
		public boolean hasNext() {
			return numberOfGenerated < numberOfPrimes;
		}

		@Override
		public Integer next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			numberOfGenerated++;
			int nextPrime = getPrime(currentPrime);

			currentPrime = nextPrime;
			return currentPrime;
		}

		/**
		 * Private method that returns first prime number greater from number
		 * given as argument.
		 * 
		 * @param startingNumber
		 *            prime number must be greater than this value
		 * @return first prime number after given value
		 */
		private int getPrime(int startingNumber) {
			int nextPrime = startingNumber + 1;
			while (!isPrime(nextPrime)) {
				nextPrime++;
			}

			return nextPrime;
		}

		/**
		 * Determines whether given value is prime number or not
		 * 
		 * @param number
		 *            to be checked
		 * @return true if number is prime, otherwise false
		 */
		private boolean isPrime(int number) {
			for (int i = 2; i <= number / 2; i++) {
				if (number % i == 0) {
					return false;
				}
			}

			return true;
		}

	}

}
