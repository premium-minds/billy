package com.premiumminds.billy.portugal.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.apache.commons.io.IOUtils;

/**
 * Class for generating Private and Public Keys.
 */
public class GenerateKeys {

	private static String getKey(String fullPath) throws FileNotFoundException,
			IOException {
		FileInputStream inputStream = new FileInputStream(fullPath);
		String key = IOUtils.toString(inputStream);
		key = key.replace("-----BEGIN RSA PRIVATE KEY-----\n", "").replace(
				"-----END RSA PRIVATE KEY-----", "");
		return key;
	}

	/**
	 * Creates a new {@link PrivateKey} given a PEM type file.
	 * 
	 * @param fullPath
	 * @return the newly created {@link PrivateKey}.
	 * @throws {@link IOException}
	 */
	public static PrivateKey generatePrivateKey(String fullPath)
			throws IOException {
		String privKeyPEM = getKey(fullPath);

		System.out.println(privKeyPEM);

		// Base64 decode the data

		// byte[] encoded = Base64.decode(privKeyPEM);
		//
		// // PKCS8 decode the encoded RSA private key
		//
		// PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
		// KeyFactory kf = KeyFactory.getInstance("RSA");
		// PrivateKey privKey = kf.generatePrivate(keySpec);
		//
		// // Display the results
		return null;
	}

	/**
	 * Creates a new {@link PublicKey} given a PEM type file.mvn
	 * 
	 * @param fullPath
	 * @return the newly created {@link PublicKey}.
	 * @throws {@link IOException }
	 */
	public static PublicKey generatePublicKey(String fullPath)
			throws IOException {
		String privKeyPEM = getKey(fullPath);
		return null;
	}

	public static void main(String[] args) {
		try {
			generatePrivateKey(System.getProperty("user.dir")
					+ "/src/test/resources/keys/private_key_test.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
