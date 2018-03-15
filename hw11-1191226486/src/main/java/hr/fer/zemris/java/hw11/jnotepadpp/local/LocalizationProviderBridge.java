package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Decorator for some other {@link ILocalizationProvider}. This class offers two
 * additional methods: connect() and disconnect(). when asked to resolve a key
 * delegates this request to wrapped (decorated) ILocalizationProvider object.
 * When user calls connect() on it, the method will register an instance of
 * anonymous {@link ILocalizationListener} on the decorated object. When user
 * calls disconnect(), this object will be unregistered from decorated object.
 * It listens for localization changes so it can l notify all listeners that are
 * registered as its listeners.
 * 
 * @author tina
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {
	/** Decorated instance of {@link ILocalizationProvider} */
	private ILocalizationProvider provider;
	/**
	 * Instance of anonymous {@link ILocalizationListener} that is going to be
	 * registered at decorated object.
	 */
	private ILocalizationListener listener;
	/**
	 * Bridge status, detects if it is connected to the decorated object or not.
	 */
	private boolean connected = false;

	/**
	 * Constructor that sets object that decorates.
	 * 
	 * @param provider
	 *            instance of {@link ILocalizationProvider} that should be
	 *            decorated.
	 */
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		this.provider = provider;
	}

	@Override
	public String getString(String text) {
		return provider.getString(text);
	}

	/**
	 * Method that registers an instance of anonymous
	 * {@link ILocalizationListener} on the decorated object if it has not
	 * already been connected.
	 */
	public void connect() {
		if (!connected) {
			listener = new ILocalizationListener() {

				@Override
				public void localizationChanged() {
					fire();
				}
			};

			provider.addLocalizationListener(listener);
		}
	}

	/**
	 * Method that unregisters an instance of anonymous
	 * {@link ILocalizationListener} from the decorated object.
	 */
	public void disconnect() {
		if (connected) {
			provider.removeLocalizationListener(listener);
		}
	}

}
