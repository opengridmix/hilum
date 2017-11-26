package com.hilum.otp.otp;

import java.security.SecureRandom;
import java.util.Random;

public class OtpGenerator {
	private static final char[] CHARS = {'0', '1', '2', '3',
		'4', '5', '6', '7', '8', '9'};
	
	private Random rand;
	private int length;

	public OtpGenerator(int length) {
		this.length = length;
		this.rand = new SecureRandom();
	}

	public String generateToken() {
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			sb.append(CHARS[rand.nextInt(CHARS.length)]);
		}
		return sb.toString();
	}
}
