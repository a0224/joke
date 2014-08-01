package com.joke.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityUtil {
	// private static String base64_random = "sviptodo";

	/**
	 * 将字符串编码为md5格式
	 * 
	 * @param value
	 * @return
	 */
	public static String md5Encode(String value) {
		String tmp = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(value.getBytes("utf8"));
			byte[] md = md5.digest();
			tmp = binToHex(md);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return tmp;
	}

	public static String binToHex(byte[] md) {
		StringBuffer sb = new StringBuffer("");
		int read = 0;
		for (int i = 0; i < md.length; i++) {
			read = md[i];
			if (read < 0)
				read += 256;
			if (read < 16)
				sb.append("0");
			sb.append(Integer.toHexString(read));
		}
		return sb.toString();
	}

	/**
	 * base64编码
	 * 
	 * @param value
	 *            字符串
	 * @return
	 */
	public static String encodeBase64(String value) {
		// return base64_random + Base64.encode(value);
		return Base64.encode(value);
	}

	/**
	 * base64解码
	 * 
	 * @param value
	 *            字符串
	 * @param random
	 *            混淆码
	 * @return
	 */
	public static String decodeBase64(String value) {
		// if (value == null || value.length() <= base64_random.length()) {
		if (StringUtil.isNullOrBlank(value)) {
			return "";

		} else {
			// int count = value.length();
			// int last = base64_random.length();
			// return Base64.decode(value.substring(last, count), "utf-8");
			return Base64.decode(value, "utf-8");
		}
	}

	public static String encodeDevice(String value) {
		StringBuilder sb = new StringBuilder(32);
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] array = md.digest(value.getBytes("utf-8"));

			for (int i = 0; i < array.length; i++) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
						.toUpperCase().substring(1, 3));
			}
		} catch (Exception e) {
			return null;
		}
		return sb.toString();
	}
}
