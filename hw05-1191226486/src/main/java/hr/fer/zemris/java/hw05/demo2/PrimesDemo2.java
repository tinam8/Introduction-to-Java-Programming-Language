package hr.fer.zemris.java.hw05.demo2;

/**
 * Program that demonstrates iterating through elements of instance of
 * PreimesCollection.
 * 
 * @author tina
 *
 */
public class PrimesDemo2 {
	/**
	 * Method that starts program
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(2);
		for (Integer prime : primesCollection) {
			for (Integer prime2 : primesCollection) {
				System.out.println("Got prime pair: " + prime + ", " + prime2);
			}
		}
	}
}
