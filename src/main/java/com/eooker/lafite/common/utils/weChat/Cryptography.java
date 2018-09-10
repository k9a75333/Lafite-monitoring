package com.eooker.lafite.common.utils.weChat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 类说明
 * 
 * @author chenyuhao
 * @date 2017年10月7日 新建
 */
public class Cryptography {

    private static Log log = LogFactory.getLog(Cryptography.class);

    /*
     * sha1方式加密
     */
    public static String sha1Encode(String expressStr) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(expressStr.getBytes());
            byte messageDigest[] = digest.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest)
                hexString.append(String.format("%02x", 0xFF & aMessageDigest));
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            log.info(e.getMessage());
        }
        return "";
    }

    /*
     * md5方式加密
     */
    public static String md5Encode(String expressStr) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");

				try {
					digest.update(expressStr.getBytes("utf-8"));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
            byte messageDigest[] = digest.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest)
                hexString.append(String.format("%02x", 0xFF & aMessageDigest));
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            log.info(e.getMessage());
        }
        return "";
    }

}
