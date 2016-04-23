package fr.oan.translate.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.springframework.security.crypto.codec.Hex;

import fr.oan.translate.domain.UserTransfer;

public class TokenUtils {

	// public static final String MAGIC_KEY = "obfuscate";
	public static final String MAGIC_KEY = System.getenv("MAGIC_KEY");

	public static String createToken(UserTransfer userTransfer) {
		/* Expires in one hour */
		long expires = System.currentTimeMillis() + 1000L * 60 * (60 * 6);

		StringBuilder tokenBuilder = new StringBuilder();
		tokenBuilder.append(userTransfer.getUsername());
		tokenBuilder.append(":");
		tokenBuilder.append(expires);
		tokenBuilder.append(":");
		tokenBuilder.append(TokenUtils.computeSignature(userTransfer, expires));

		// return tokenBuilder.toString();

		return new String(Base64.encodeBase64(tokenBuilder.toString().getBytes()));

	}

	public static String computeSignature(UserTransfer userTransfer, long expires) {
		StringBuilder signatureBuilder = new StringBuilder();
		signatureBuilder.append(userTransfer.getUsername());
		signatureBuilder.append(":");
		signatureBuilder.append(expires);
		signatureBuilder.append(":");
		signatureBuilder.append(userTransfer.getTokenAttribut());
		signatureBuilder.append(":");
		signatureBuilder.append(TokenUtils.MAGIC_KEY);

		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("No MD5 algorithm available!");
		}

		return new String(Hex.encode(digest.digest(signatureBuilder.toString().getBytes())));
	}

	public static String getUserNameFromToken(String authToken) {
		if (null == authToken) {
			return null;
		}

		authToken = new String(Base64.decodeBase64(authToken.toString().getBytes()));
		String[] parts = authToken.split(":");
		return parts[0];
	}

	public static boolean validateToken(String authToken, UserTransfer userTransfer) {

		authToken = new String(Base64.decodeBase64(authToken.toString().getBytes()));

		String[] parts = authToken.split(":");
		long expires = Long.parseLong(parts[1]);
		String signature = parts[2];

		if (expires < System.currentTimeMillis()) {
			return false;
		}

		return signature.equals(TokenUtils.computeSignature(userTransfer, expires));
	}
}
