package hr.fer.zemris.java.hw05.observer2;

/**
 * Program that demonstrates the usage of the Observer pattern.
 * 
 * @author tina
 *
 */
public class ObserverExample {
	/**
	 * Method that starts program.
	 * 
	 * @param args
	 *            comman line arguments
	 */
	public static void main(String[] args) {
		IntegerStorage istorage = new IntegerStorage(20);
		IntegerStorageObserver observer = new SquareValue();
		istorage.addObserver(observer);
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue(2));
		istorage.addObserver(new DoubleValue(1));
		istorage.addObserver(new DoubleValue(2));
		
		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);
		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
	}
}
