package com.eooker.lafite.modules.sys.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


/**
 * 加密方法工具类
 * @author chengl
 *
 */
public class CryptoUtils {

    static final char[] HEX_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 对密码进行MD5散列
     * @param input @see String
     * @return
     */
    public static String md5Hash(String input) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            byte[] out = m.digest(input.getBytes());
            return new String(Base64.encode(out));
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * MD5散列码
     * 散列结果被转化成16进制的字符串
     * @param value
     * @return
     * @throws Exception
     */
    public static String hexMD5(String value) throws Exception {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(value.getBytes("utf-8"));
            byte[] digest = messageDigest.digest();
            return byteToHexString(digest);
        } catch (Exception ex) {
            throw new Exception(ex);
        }
    }

    /**
     * 对目标进行AES-128方式加密
     * @param value
     * @param privateKey 长度必须为16字节
     * @return
     * @throws Exception
     */
    public static String encryptAES(String value, String privateKey) throws Exception {
        try {
            byte[] raw = privateKey.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            return CryptoUtils.byteToHexString(cipher.doFinal(value.getBytes()));
        } catch (Exception ex) {
            throw new Exception(ex);
        }
    }
    

    /**
     * 对目标进行AES-128方式解密
     * @param value
     * @param privateKey 长度必须为16字节
     * @return
     * @throws Exception
     */
    public static String decryptAES(String value, String privateKey) throws Exception {
        try {
            byte[] raw = privateKey.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            return new String(cipher.doFinal(CryptoUtils.hexStringToByte(value)));
        } catch (Exception ex) {
            throw new Exception(ex);
        }
    }
    
    /**
     * SHA-1散列码    
     * @param value
     * @return
     * @throws Exception
     */
    public static String hexSHA1(String value) throws Exception {
        try {
            MessageDigest md;
            md = MessageDigest.getInstance("SHA-1");
            byte[] digest = new byte[40];
            md.update(value.getBytes("utf-8"));
            digest = md.digest();
            return byteToHexString(digest);
        } catch (Exception ex) {
            throw new Exception(ex);
        }
    }

    /**
     * 字节转为16进制串
     * @param bytes
     * @return
     */
    public static String byteToHexString(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < bytes.length; ++i) {
            int v = bytes[i];
            if (v < 0) {
                v += 256;
            }
            String n = Integer.toHexString(v);
            if(n.length() == 1)
                n = "0" + n;
            builder.append(n);
        }

        return builder.toString();
    }

    /**
     * 16进制串转为字节
     * @param hexString
     * @return
     */
    public static byte[] hexStringToByte(String hexString) {
        byte[] raw = new byte[16];
        for(int i=0;i<16;i++) {
            raw[i] = Integer.decode("0x" + hexString.substring(i*2, i*2+2)).byteValue();
        }
        return raw;
    }
    
    /**
     * 将目标编码成base64（字符集默认为utf-8）
     * @param value
     * @return
     * @throws Exception 
     */
    public static String encodeBASE64(String value) throws Exception {
        try {
            return new String(Base64.encode(value.getBytes("utf-8")));
        } catch (UnsupportedEncodingException ex) {
            throw new Exception(ex);
        }
    }
    
    /**
     * 将目标编码成base64
     * @param value
     * @param charsetName 所使用的字符集
     * @return
     * @throws Exception
     */
    public static String encodeBASE64(String value,String charsetName) throws Exception {
        try {
            return new String(Base64.encode(value.getBytes(charsetName)));
        } catch (UnsupportedEncodingException ex) {
            throw new Exception(ex);
        }
    }

    /**
     * 将目标编码成base64
     * @param value
     * @return
     */
    public static String encodeBASE64(byte[] value) {
        return new String(Base64.encode(value));
    }

    /**
     * 将base64目标解码
     * @param value
     * @return
     */
    public static byte[] decodeBASE64(String value) {
        return Base64.decode(value);
    }

    public static void main(String[] args) throws Exception{
    	System.out.println(CryptoUtils.hexMD5("20150000qiushaojian2015101032321"));
    }

}
