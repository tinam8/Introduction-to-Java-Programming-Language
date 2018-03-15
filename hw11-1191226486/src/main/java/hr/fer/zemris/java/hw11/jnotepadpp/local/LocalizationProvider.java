package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Class that represents concrete subject that contains language current status.
 * 
 * @author tina
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {
	/** Currently active language. */
	private String language;
	/** Contains all translations for given language. */
	private ResourceBundle bundle;
	/** Variable that has path to the translation files. */
	private static final String TRANSLATION_PATH = "hr.fer.zemris.java.hw11.jnotepadpp.local.translation";
	/**
	 * Instance of {@link LocalizationProvider} represents Singleton class in
	 * singleton pattern.
	 */
	private static final LocalizationProvider instance = new LocalizationProvider();

	/**
	 * Constructor that sets default language to be English.
	 * 
	 * @throws MissingResourceException
	 *             if translation files can not be found.
	 */
	public LocalizationProvider() {
		language = "en";
		Locale locale = Locale.forLanguageTag(language);
		try {
			bundle = ResourceBundle.getBundle(TRANSLATION_PATH, locale);
		} catch (MissingResourceException e) {
			throw new IllegalArgumentException("Wrong path to the translation files.");
		}

	}

	/**
	 * Method for getting "global" instance of {@link LocalizationProvider}.
	 * 
	 * @return instance of {@link LocalizationProvider}
	 */
	public static LocalizationProvider getInstance() {
		return instance;
	}

	/**
	 * Method that sets language of subject
	 * 
	 * @param language
	 *            language to set as currently active
	 */
	public void setLanguage(String language) {
		this.language = language;
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle(TRANSLATION_PATH, locale);
		fire();
	}

	/**
	 * Getter for current language.
	 * 
	 * @return current language
	 */
	public String getLanguage() {
		return language;
	}
	
	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}
}
