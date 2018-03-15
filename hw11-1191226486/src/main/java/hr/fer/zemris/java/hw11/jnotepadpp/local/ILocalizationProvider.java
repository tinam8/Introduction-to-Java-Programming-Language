package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Objects which are instances of classes that implement this interface will be
 * able to give us the translations for given keys. (They represent subject in
 * observer pattern)
 * 
 * @author tina
 *
 */
public interface ILocalizationProvider {
	/**
	 * Method that adds listener.
	 * 
	 * @param l
	 *            listener to add; instance of {@link ILocalizationListener}
	 */
	public void addLocalizationListener(ILocalizationListener l);

	/**
	 * Method that removes listener.
	 * 
	 * @param l
	 *            listener to remove; instance of {@link ILocalizationListener}
	 */
	public void removeLocalizationListener(ILocalizationListener l);

	/**
	 * Method that returns translation for given key.
	 * 
	 * @param text
	 *            key of string to translate
	 * @return translation on currently active language in provider.
	 */
	public String getString(String text);

}
