package hr.fer.zemris.java.hw05.observer1;

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
	 *            command line arguments
	 */
	public static void main(String[] args) {
		IntegerStorage istorage = new IntegerStorage(20);
		IntegerStorageObserver observer = new SquareValue();
		istorage.addObserver(observer);
		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);
//		istorage.removeObserver(observer);
//		istorage.addObserver(new ChangeCounter());
//		istorage.addObserver(new DoubleValue(5));
		
		istorage.removeObserver(observer);
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue(2));
		istorage.addObserver(new DoubleValue(1));
		istorage.addObserver(new DoubleValue(2));
		
		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
	}
}
