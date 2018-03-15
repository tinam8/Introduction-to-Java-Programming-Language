package hr.fer.zemris.java.tecaj_13.model;

import java.security.MessageDigest;

/**
 * Class that implements methods used for user identification
 * 
 * @author tina
 *
 */
public class BlogUserUtil {

	/**
	 * Calculates hash value of given password.
	 * 
	 * @param password
	 *            password
	 * @return hash value of password
	 */
	public static String calcHash(String password) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (Exception e) {
			return null;
		}
		md.reset();
		md.update(password.getBytes());

		byte[] digest = md.digest();

		String hash = "";
		for (byte d : digest) {
			hash += String.valueOf((int) d);
		}
		return hash;
	}

	/**
	 * Method that checks if password matches hashed password.
	 * 
	 * @param password
	 *            password
	 * @param hashPassword
	 *            hashed password
	 * @return true if password is correct, otherwise false
	 */
	public static boolean checkPassword(String password, String hashPassword) {
		String hash = calcHash(password);
		return hash.equals(hashPassword);
	}
}
