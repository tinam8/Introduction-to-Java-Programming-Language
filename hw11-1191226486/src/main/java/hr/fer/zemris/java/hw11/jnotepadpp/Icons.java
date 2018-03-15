package hr.fer.zemris.java.hw11.jnotepadpp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;

/**
 * Class that implements loading and getting icons.
 * 
 * @author tina
 *
 */
public class Icons {
	/** Path to the red disk icon */
	private final String RED_DISK_PATH = "/redDisk.png";
	/** Path to the green disk path */
	private final String GREEN_DISK_PATH = "/greenDisk.png";

	/**
	 * Getter for the red disc icon
	 * 
	 * @return red disc icon
	 */
	public ImageIcon getRedIcon() {
		return getIcon(RED_DISK_PATH);
	}

	/**
	 * Getter for the green disc icon
	 * 
	 * @return green disc icon
	 */
	public ImageIcon getGreenIcon() {
		return getIcon(GREEN_DISK_PATH);
	}

	/**
	 * Getter for icon
	 * 
	 * @param relativePath
	 *            relative path to the icon
	 * @return return appropriate icon
	 */
	private ImageIcon getIcon(String relativePath) {
		InputStream is = this.getClass().getResourceAsStream(relativePath);
		if (is == null) {
			throw new IllegalArgumentException("Can not load image.");
		}

		byte[] bytes = null;
		try {
			bytes = readAllBytes(is);
		} catch (IOException ignorable) {
		}

		try {
			is.close();//
		} catch (IOException ignorable) {
		}

		return new ImageIcon(bytes);
	}

	/**
	 * Method that reads all bytes from given stream
	 * 
	 * @param is
	 *            stream to use for reading
	 * @return array of bytes
	 * @throws IOException
	 *             if error occurs while reading
	 */
	private byte[] readAllBytes(InputStream is) throws IOException {
		try (ByteArrayOutputStream os = new ByteArrayOutputStream();) {
			byte[] buffer = new byte[0xFFFF];

			for (int len; (len = is.read(buffer)) != -1;)
				os.write(buffer, 0, len);

			os.flush();

			return os.toByteArray();
		}
	}
}
