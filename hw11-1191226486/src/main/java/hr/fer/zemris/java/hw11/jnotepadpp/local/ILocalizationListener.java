package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Interface that contains method for getting information when language has
 * changed. (It is a method that observer has to implement.)
 * 
 * @author tina
 *
 */
public interface ILocalizationListener {
	/**
	 * Method that the subject (instance of {@link ILocalizationProvider} calls
	 * when language change occurs.
	 */
	public void localizationChanged();
}
