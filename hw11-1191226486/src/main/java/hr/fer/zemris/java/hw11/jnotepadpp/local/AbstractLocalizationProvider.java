package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.HashSet;
import java.util.Set;

/**
 * Abstract class that implements {@link ILocalizationProvider} interface and
 * adds it the ability to register, de-register and inform) listeners. It is an
 * abstract class – it does not implement getString(...) method.
 * 
 * @author tina
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {
	/** List of registered listeners. */
	Set<ILocalizationListener> listeners;

	/**
	 * Constructor that initializes list of listeners.
	 */
	public AbstractLocalizationProvider() {
		listeners = new HashSet<>();
	}

	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		listeners.add(l);
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		listeners.remove(l);
	}

	/**
	 * Method that notifies all registered observers ({@link ILocalizationListener}) when language change occurs.
	 */
	public void fire() {
		Set<ILocalizationListener> copyListeners = new HashSet<>();
		listeners.forEach(l -> {
			copyListeners.add(l);
		});

		copyListeners.forEach(l -> {
			l.localizationChanged();
		});
	}
}
