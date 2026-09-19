package com.chunkflow.mod;

public final class CaesarCipher {

	private CaesarCipher() {
	}

	public static String encode(String input, int shift) {
		StringBuilder sb = new StringBuilder();
		for (char c : input.toCharArray()) {
			if (Character.isUpperCase(c)) {
				sb.append((char) ('A' + Math.floorMod(c - 'A' + shift, 26)));
			} else if (Character.isLowerCase(c)) {
				sb.append((char) ('a' + Math.floorMod(c - 'a' + shift, 26)));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String decode(String input, int shift) {
		return encode(input, -shift);
	}
}
