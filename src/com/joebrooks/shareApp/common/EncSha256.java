package com.joebrooks.shareApp.common;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncSha256 {
	
	public String GetSha(String data) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(data.getBytes());
		
		return String.format("%64x", new BigInteger(1, md.digest()));
	}
}
