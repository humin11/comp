/**
 * File Name：HashAlgorithm.java
 *
 * Version：
 * Date：Feb 28, 2012
 * Copyright CloudWei Dev Team 2012 
 * All Rights Reserved.
 *
 */
package utils;

import java.security.MessageDigest;

/**
 * 
 * Project Name：com.cldouwei.monitor.model Class Name：HashAlgorithm Class
 * Desc：Colletor some algorithm within hash Author：Spring Create Date：Feb 28,
 * 2012 1:42:27 PM Last Modified By：Spring Last Modified：Feb 28, 2012 1:42:27 PM
 * Remarks：
 * 
 * @version
 * 
 */
public class HashAlgorithm {
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	public static HashAlgorithm getInstance() {

		return new HashAlgorithm();
	}

	/**
	 * @Title: generate
	 * @Description: Return a uniqueId through hash algorithm
	 * @param @param str
	 * @param @return
	 * @return int
	 * @throws
	 * @since CloudWei v1.0.0
	 */
	public String generate(String... string) {
		String str = "";
		for (String columeValue : string) {
			str += columeValue;
		}
		return this.Hash(str);
	}

	// 十六进制下数字到字符的映射数组

	/** */
	/**
	 * 把inputString加密
	 * 
	 * @param inputString
	 *            字符串
	 * @return hash化的字符串 ,String类型
	 */
	public String Hash(String inputString) {
		if (inputString != null) {
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] results = md.digest(inputString.getBytes());
				String resultString = byteArrayToHexString(results);
				return resultString.toUpperCase();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	/** */
	/**
	 * 验证输入的密码是否正确
	 * 
	 * @param password
	 *            真正的密码（加密后的真密码）
	 * @param inputString
	 *            输入的字符串
	 * @return 验证结果，boolean类型
	 */
	public boolean authenticatePassword(String password, String inputString) {
		if (password.equals(Hash(inputString))) {
			return true;
		} else {
			return false;
		}
	}

	/** */
	/**
	 * 转换字节数组为十六进制字符串
	 * 
	 * @param b
	 *            字节数组
	 * @return 十六进制字符串 ,String类型
	 */
	private static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	/** */
	/**
	 * 将一个字节转化成十六进制形式的字符串
	 * 
	 * @param b
	 *            字节
	 * @return 16进制字符串 ,String类型
	 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

}
