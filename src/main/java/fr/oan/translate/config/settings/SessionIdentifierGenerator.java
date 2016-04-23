package fr.oan.translate.config.settings;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;

public final class SessionIdentifierGenerator {
	// private SecureRandom random = new SecureRandom();

	public static String nextSessionId() throws NoSuchAlgorithmException {
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		return new BigInteger(256, sr).toString(32);
	}

	public static String longToken() {
		String uuid = UUID.randomUUID().toString();

		return uuid.replaceAll("-", "");
	}

	public static String getSalt() throws NoSuchAlgorithmException {
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[128];
		sr.nextBytes(salt);
		return salt.toString();
	}
}
