package dev.hicksm.climbing.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHasher {
	
	private static final String HASH_ALGORITHM = "SHA-256";
	
	public static String generateSalt() {
		// TODO: Implement stub
		return null;
	}
	
	/// from https://www.baeldung.com/sha-256-hashing-java
	private static String bytesToHex(byte[] hash) {
	    StringBuilder hexString = new StringBuilder(2 * hash.length);
	    for (int i = 0; i < hash.length; i++) {
	        String hex = Integer.toHexString(0xff & hash[i]);
	        if(hex.length() == 1) {
	            hexString.append('0');
	        }
	        hexString.append(hex);
	    }
	    return hexString.toString();
	}
	
	public static HashedPassword hashPassword(String rawPassword, String salt) 
			throws IllegalArgumentException {
		HashedPassword res = new HashedPassword();
		
		// Pre-process password and salt
		if (rawPassword == null)
			throw new IllegalArgumentException("Raw password cannot be null.");
		if (salt == null)
			salt = "";
		String s = salt + rawPassword;
		
		// Retrieve SHA-256 digest instance and hash string to UTF-8 bytes
		MessageDigest d;
		try {
			d = MessageDigest.getInstance(HASH_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException(
					"PasswordHasher tried to use invalid hashing algorithm " + HASH_ALGORITHM);
		}
		byte[] encoded = d.digest(s.getBytes(StandardCharsets.UTF_8));
		
		res.hashedPassword = bytesToHex(encoded);
		
		return res;
	}
	
	public static class HashedPassword {
		
		public String salt;
		
		public String hashedPassword;
		
	}

}
