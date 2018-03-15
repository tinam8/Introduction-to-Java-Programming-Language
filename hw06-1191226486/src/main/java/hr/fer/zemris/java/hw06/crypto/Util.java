package hr.fer.zemris.java.hw06.crypto;

/**
 * Class that has methods for converting data between byte arrays anf hex-encoded
 * strings.
 * 
 * @author tina
 *
 */
public class Util {
	/**
	 * Used in bytetohex method.
	 */
	private static final char[] hexArray = "0123456789ABCDEF".toCharArray();

	/**
	 * Method that takes hex-encoded String and returns appropriate byte[]. For
	 * zero-length string, method returns zero-length byte array.
	 * 
	 * @param keyText
	 *            hex-encoded String
	 * @return appropriate byte[]
	 * @throws IllegalArgumentException
	 *             if string is not valid (odd-sized, has invalid characters, …)
	 */
	public static byte[] hextobyte(String keyText) {
		int len = keyText.length();

		if (len % 2 != 0) {
			throw new IllegalArgumentException("Text lenght should be even number.");
		}

		if (!keyText.matches("-?[0-9a-fA-F]+")) {
			throw new IllegalArgumentException("Text is not valid, has invalid characters.");
		}

		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(keyText.charAt(i), 16) << 4)
					+ Character.digit(keyText.charAt(i + 1), 16));
		}

		return data;
	}

	/**
	 * Method takes a byte array and creates its hex-encoding: for each byte of
	 * given array, two characters are returned in string, in big-endian
	 * notation. For zero-length array an empty string is returned. Method uses
	 * lowercase letters for creating encoding.
	 * 
	 * @param bytearray
	 *            array of bytes
	 * @return hex-encoding
	 */
	public static String bytetohex(byte[] bytearray) {
		char[] hexChars = new char[bytearray.length * 2];

		for (int i = 0; i < bytearray.length; i++) {
			int v = bytearray[i] & 0xFF;
			hexChars[i * 2] = hexArray[v >>> 4];
			hexChars[i * 2 + 1] = hexArray[v & 0x0F];
		}

		return new String(hexChars).toLowerCase();
	}

}
