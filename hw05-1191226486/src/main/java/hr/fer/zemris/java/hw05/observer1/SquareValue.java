package hr.fer.zemris.java.hw05.observer1;

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
	public void valueChanged(IntegerStorage istorage) {
		System.out.println(Math.pow(istorage.getValue(), 2));
	}
	
}
