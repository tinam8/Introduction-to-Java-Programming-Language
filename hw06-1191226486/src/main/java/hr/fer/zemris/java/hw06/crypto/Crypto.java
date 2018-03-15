package hr.fer.zemris.java.hw06.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Program that allows the user to encrypt/decrypt given file using the AES
 * cryptoalgorithm and the 128-bit encryption key or calculate and check the
 * SHA-256 file digest.
 * 
 * @author tina
 *
 */
public class Crypto {
	/**
	 * Method that starts the program.
	 * 
	 * @param args
	 *            command line arguments, first is name of operation to use,
	 *            second is file path
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			System.err.println("Arguments missing.");
			System.exit(-1);
		}

		Scanner sc = new Scanner(System.in);
		boolean encrypt = false;

		switch (args[0]) {
		case "checksha":
			if (args.length != 2) {
				System.err.println("More arguments provided than is needed.");
				System.exit(-1);
			}

			System.out.println("Please provide expected sha-256 digest for: " + args[1]);
			System.out.print("> ");

			if (sc.hasNext()) {
				checkDigestAndPrint(sc.next(), args[1]);
			}

			sc.close();
			return;

		case "encrypt":
			if (args.length != 3) {
				System.out.println(
						"Wrong number of arguments for encription. Input file path and output file path needed.");
				System.exit(-1);
			}

			encrypt = true;
			break;
		case "decrypt":

			break;
		default:
			System.out.println("Wrong name for wanted operation.");
			System.exit(-1);
		}

		String keyText = null;
		String ivText = null;

		System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
		System.out.print("> ");
		if (sc.hasNext()) {
			keyText = sc.next();
		}

		System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits)::");
		System.out.print("> ");
		if (sc.hasNext()) {
			ivText = sc.next();
		}

		sc.close();

		try (InputStream is = new BufferedInputStream(new FileInputStream(args[1]));
				OutputStream os = new BufferedOutputStream(new FileOutputStream(args[2]));) {

			try {
				generateCryptFile(encrypt, is, os, keyText, ivText);
			} catch (Exception e) {
				System.err.println("Error while generating a file." + e.getMessage());
				System.exit(-1);
			}

			String type = encrypt ? "Encryption" : "Decryption";
			System.out.printf("%s completed. Generated file %s based on file %s%n", type, args[2], args[1]);

		} catch (IOException ex) {
			System.err.println("Error while opening files." + ex.getMessage());
			System.exit(-1);
		}

	}

	/**
	 * Method that checks if given input is expected digest of a file.
	 * 
	 * @param digestInput
	 *            input to be checked
	 * @param filePath
	 *            file whose digest is expected
	 */
	private static void checkDigestAndPrint(String digestInput, String filePath) {
		try (InputStream is = new BufferedInputStream(new FileInputStream(filePath))) {

			MessageDigest msgDigest = null;
			try {
				msgDigest = MessageDigest.getInstance("SHA-256");
			} catch (Exception e) {
				System.out.println("Somthing is wrong with getting instance of MessageDigest.");
				System.exit(-1);
			}

			byte[] dataBytes = new byte[1024];
			int read = 0;

			while ((read = is.read(dataBytes)) != -1) {
				msgDigest.update(dataBytes, 0, read);
			}
			;

			byte[] digest = msgDigest.digest();

			String expectedDigest = Util.bytetohex(digest);
			if (expectedDigest.equals(digestInput)) {
				System.out.println("Digesting completed. Digest of " + filePath + " matches expected digest.");
			} else {
				System.out.println("Digesting completed. Digest of " + filePath + " does not match the "
						+ "expected digest. Digest was: " + expectedDigest);
			}

		} catch (IOException ex) {
			System.err.println("Error while opening file." + ex.getMessage());
			System.exit(-1);
		}
	}

	/**
	 * Method that decrypts or encrypts data from input stream and generated
	 * data writes in output stream.
	 * 
	 * @param encrypt
	 *            if true encrypt if false decrypt data
	 * @param is
	 *            input stream
	 * @param os
	 *            output stream
	 * @param keyText
	 *            password
	 * @param ivText
	 *            initialization vector
	 * @throws Exception
	 *             if error occurs
	 */
	private static void generateCryptFile(boolean encrypt, InputStream is, OutputStream os, String keyText,
			String ivText) throws Exception {
		if (!keyText.matches("-?[0-9a-fA-F]+") || keyText.length() != 32) {
			throw new IllegalArgumentException("Password should be hex-encoded text (16 bytes, i.e. 32 hex-digits).");
		}

		if (!ivText.matches("-?[0-9a-fA-F]+") || ivText.length() != 32) {
			throw new IllegalArgumentException(
					"Initialization vector shoule be hex-encoded text (16 bytes, i.e. 32 hex-digits)");
		}

		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
		Cipher cipher = null;
		cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, paramSpec);
		cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);

		byte[] dataBytes = new byte[4096];
		byte[] processedBytes;
		int read = 0;

		while ((read = is.read(dataBytes)) != -1) {
			processedBytes = cipher.update(dataBytes, 0, read);
			os.write(processedBytes);
		}

		processedBytes = cipher.doFinal();
		os.write(processedBytes);
	}

}
