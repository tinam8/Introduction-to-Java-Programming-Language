package hr.fer.zemris.java.hw05.demo2;

/**
 * Program that demonstrates iterating through elements of instance of
 * PreimesCollection.
 * 
 * @author tina
 *
 */
public class PrimesDemo1 {
	/**
	 * Method that starts program
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(5);
		for (Integer prime : primesCollection) {
			System.out.println("Got prime: " + prime);
		}
	}
}
