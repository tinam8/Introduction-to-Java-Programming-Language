package hr.fer.zemris.java.hw05.observer2;

/**
 * Class that represents concrete observer. <br>
 * Writes a square of the integer stored in the {@IntegerStorage} to the
 * standard output (but the stored integer itself is not modified!)
 * 
 * @author tina
 *
 */
public class SquareValue  implements IntegerStorageObserver {

	@Override
	public void valueChanged(IntegerStorageChange istorageChange) {
		System.out.println(Math.pow(istorageChange.getNewValue(),2));
	}
	
}
