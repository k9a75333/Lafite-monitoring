package com.eooker.lafite.modules.sys.utils;

import java.io.UnsupportedEncodingException;

/**
 * 字符集编码常量类
 * 
 * 
 */
public class CharsetEncoding {

	public static final String ISO_8859_1 = "ISO-8859-1";

	public static final String US_ASCII = "US-ASCII";

	public static final String UTF_16 = "UTF-16";

	public static final String UTF_16BE = "UTF-16BE";

	public static final String UTF_16LE = "UTF-16LE";

	public static final String UTF_8 = "UTF-8";

	public static final String GBK = "GBK";

	public static final String GB2312 = "GB2312";

	/**
	 * <p>
	 * 返回传入字符集是否被当前JVM支持.
	 * </p>
	 * 
	 * @param name
	 *            字符集名称
	 * @return <code>true</code> 当前JVM支持该字符集
	 * 
	 * @see <a
	 *      href="http://java.sun.com/j2se/1.3/docs/api/java/lang/package-summary.html#charenc">JRE
	 *      character encoding names</a>
	 */
	public static boolean isSupported(String name) {
		if (name == null) {
			return false;
		}
		try {
			new String(new byte[0], name);
		} catch (UnsupportedEncodingException e) {
			return false;
		}
		return true;
	}

	/**
	 * 加密字符串
	 * 
	 * @throws BusinessException
	 */
	public static String getEncryptPwd(String pwd) {
		try {
			return CryptoUtils.encryptAES(pwd, getkey());
		} catch (Exception e) {
			System.out.println("加密错误：");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密字符串
	 * 
	 * @throws BusinessException
	 */
	public static String delEncryptPwd(String pwd) {
		try {
			return CryptoUtils.decryptAES(pwd, getkey());
		} catch (Exception e) {
			System.out.println("解密错误：");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取加密私钥
	 */
	private static String getkey() {
		return "zxrxyxyxyxyxyxye";
	}

 
}
