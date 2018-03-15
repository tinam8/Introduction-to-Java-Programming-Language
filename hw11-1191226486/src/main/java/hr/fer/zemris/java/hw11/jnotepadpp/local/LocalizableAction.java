package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * Class that extends {@link AbstractAction} and assures that all GUI components
 * (buttons, menu items), that are based on it's instance, automatically refresh
 * itself.
 * 
 * @author tina
 *
 */
public class LocalizableAction extends AbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Key that represent name of action  */
	private String key;

	/**
	 * Constructor that sets action key and adds itself to localization
	 * provider.
	 * 
	 * @param key
	 *            key of the action
	 * @param lp
	 *            localization provider that holds language status
	 */
	public LocalizableAction(String key, ILocalizationProvider lp) {
		putValue(NAME, lp.getString(key));
		
		lp.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				putValue(NAME, lp.getString(key));
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	

}
