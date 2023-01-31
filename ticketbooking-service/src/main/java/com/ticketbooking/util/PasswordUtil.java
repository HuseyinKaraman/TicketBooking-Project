package com.ticketbooking.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {
	private PasswordUtil() {

	}

	public static String preparePasswordHash(String password) {
		return getSHA512SecurePassword(password);
	}

	private static String getSHA512SecurePassword(String passwordToHash) {
		String generatedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(passwordToHash.getBytes());		
			// Get the hash's bytes
		    byte[] bytes = md.digest();
		    StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return generatedPassword;
	}

	public static boolean validatePassword(String passwordHash, String password) {
		return passwordHash.equals(password);
	}
}
