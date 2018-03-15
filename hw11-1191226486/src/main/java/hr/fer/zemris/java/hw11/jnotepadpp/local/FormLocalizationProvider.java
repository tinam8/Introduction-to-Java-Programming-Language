package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * Class derived from {@link LocalizationProviderBridge}. It registers itself as
 * a WindowListener to its JFrame; when frame is opened, it calls connect and
 * when frame is closed, it calls disconnect.
 * 
 * @author tina
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {
	/**
	 * Constructor that decorates given instance of
	 * {@link ILocalizationProvider}, and connects and disconnects based on
	 * given frame.
	 * 
	 * @param provider
	 *            object to decorate
	 * @param frame
	 *            frame that determines when this instance will be connected and
	 *            disconnected
	 */
	public FormLocalizationProvider(ILocalizationProvider provider, JFrame frame) {
		super(provider);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}

			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}
		});
	}

}
