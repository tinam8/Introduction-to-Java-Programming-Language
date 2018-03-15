package hr.fer.zemris.java.hw11.statusbar;

import javax.swing.JLabel;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * Class that extends {@link JLabel} and implements a way for label to change
 * text when language changes.
 * 
 * @author tina
 *
 */
public class LJLabel extends JLabel {
	/** */
	private static final long serialVersionUID = 1L;
	/** Key for translation */
	private String key;

	/**
	 * Constructor that adds this instance to localization provider and updates
	 * text based on language change.s
	 * 
	 * @param key
	 *            key for language translation
	 * @param lp
	 *            instance of {@link LocalizationProvider}
	 */
	public LJLabel(String key, ILocalizationProvider lp) {
		lp.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				LJLabel.this.setText(lp.getString(key));
			}
		});
	}

}
