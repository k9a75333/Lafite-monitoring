package com.eooker.lafite.common.utils;


import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Random;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import net.sf.json.JSONObject;

public class Encryption {
	/***
	 * @author liujunwei
	 * 加密算法
	 * 加密
	 * 解密
	 */
		public static String Encode = "Encode";
		public static String Decode = "Decode";


		/**
		 * @功能：从字符串的指定位置截取指定长度的子字符串
		 * @param str:原字符串
		 * @param startIndex:子字符串的起始位置
		 * @param length
		 * @return:子字符串
		 */
		public static String CutString(String str, int startIndex, int length) {
			if (startIndex >= 0) {
				if (length < 0) {
					length = length * -1;
					if (startIndex - length < 0) {
						length = startIndex;
						startIndex = 0;
					} else {
						startIndex = startIndex - length;
					}
				}

				if (startIndex > str.length()) {
					return "";
				}

			} else {
				if (length < 0) {
					return "";
				} else {
					if (length + startIndex > 0) {
						length = length + startIndex;
						startIndex = 0;
					} else {
						return "";
					}
				}
			}

			if (str.length() - startIndex < length) {

				length = str.length() - startIndex;
			}

			return str.substring(startIndex, startIndex + length);
		}

		/**
		 * @功能：从字符串的指定位置开始截取到字符串结尾的了符串
		 * @param str:原字符串
		 * @param startIndex:子字符串的起始位置
		 * @return:子字符串
		 */
		public static String CutString(String str, int startIndex) {
			return CutString(str, startIndex, str.length());
		}

		/**
		 * @功能:返回文件是否存在
		 * @param filename:文件名
		 * @return:是否存在
		 */
		public static boolean FileExists(String filename) {
			File f = new File(filename);
			return f.exists();
		}

		/**
		 * @：功能MD5函数
		 * @param str:原始字符串
		 * @return:原始字符串
		 */
		public static String MD5(String str) {
			// return md5.convert(str);
			StringBuffer sb = new StringBuffer();
			String part = null;
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] md5 = md.digest(str.getBytes());

				for (int i = 0; i < md5.length; i++) {
					part = Integer.toHexString(md5[i] & 0xFF);
					if (part.length() == 1) {
						part = "0" + part;
					}
					sb.append(part);
				}

			} catch (NoSuchAlgorithmException ex) {
			}
			return sb.toString();
		}

		/**
		 * 功能:字段串是否为Null或为
		 * @param str
		 * @return
		 */
		public static boolean StrIsNullOrEmpty(String str) {
			// #if NET1
			if (str == null || str.trim().equals("")) {
				return true;
			}

			return false;
		}

		/**
		 * 功能：用于 RC4 处理密码
		 * @param pass:密码字串
		 * @param kLen:>密钥长度，一般为 256
		 * @return
		 */
		static private byte[] GetKey(byte[] pass, int kLen) {
			byte[] mBox = new byte[kLen];

			for (int i = 0; i < kLen; i++) {
				mBox[i] = (byte) i;
			}

			int j = 0;
			for (int i = 0; i < kLen; i++) {

				j = (j + (int) ((mBox[i] + 256) % 256) + pass[i % pass.length])
						% kLen;

				byte temp = mBox[i];
				mBox[i] = mBox[j];
				mBox[j] = temp;
			}

			return mBox;
		}

		/**
		 * 功能：生成随机字符
		 * @param lens:随机字符长度
		 * @return:随机字符
		 */
		public static String RandomString(int lens) {
			char[] CharArray = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k',
					'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
					'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
			int clens = CharArray.length;
			String sCode = "";
			Random random = new Random();
			for (int i = 0; i < lens; i++) {
				sCode += CharArray[Math.abs(random.nextInt(clens))];
			}
			return sCode;
		}

		/**
		 * @author liujunwei
		 * @功能：加密算法
		 * @param source：需要解密的字符串
		 * @param key：加密的秘钥
		 * @param expiry
		 * @return
		 * @date 20150107
		 */
		public static String authcodeEncode(String source, String key) {
			return decipher(source, key, Encode, 0);

		}

		


		/**
		 * 功能:RC4 原始算法
		 * @param input:原始字串数组
		 * @param pass:密钥
		 * @return:处理后的字串数组
		 */
		private static byte[] RC4(byte[] input, String pass) {
			if (input == null || pass == null)
				return null;

			byte[] output = new byte[input.length];
			byte[] mBox = GetKey(pass.getBytes(), 256);

			// 加密
			int i = 0;
			int j = 0;

			for (int offset = 0; offset < input.length; offset++) {
				i = (i + 1) % mBox.length;
				j = (j + (int) ((mBox[i] + 256) % 256)) % mBox.length;

				byte temp = mBox[i];
				mBox[i] = mBox[j];
				mBox[j] = temp;
				byte a = input[offset];

				// byte b = mBox[(mBox[i] + mBox[j] % mBox.Length) % mBox.Length];
				// mBox[j] 一定比 mBox.Length 小，不需要在取模
				byte b = mBox[(toInt(mBox[i]) + toInt(mBox[j])) % mBox.length];

				output[offset] = (byte) ((int) a ^ (int) toInt(b));
			}

			return output;
		}

		public static int toInt(byte b) {
			return (int) ((b + 256) % 256);
		}

		public long getUnixTimestamp() {
			Calendar cal = Calendar.getInstance();
			return cal.getTimeInMillis() / 1000;
		}
		/**
		 * @param source:原始字符串
		 * @param key：秘钥
		 * @param operation：加解密操作
		 * @param expiry：加密字串过期时间
		 * @return：加密后的字符串
		 */
		private static String decipher(String source, String key,
				String operation, int expiry){
			try{
			if (source == null || key == null) {
				return "";
			}

			int ckey_length = 4;
			String keya, keyb, keyc, cryptkey, result;

			key = MD5(key);

			keya = MD5(CutString(key, 0, 16));

			keyb = MD5(CutString(key, 16, 16));

			keyc = ckey_length > 0 ? (operation.equals(Decode) ? CutString(
					source, 0, ckey_length) : RandomString(ckey_length))
					: "";

			cryptkey = keya + MD5(keya + keyc);
			
			source = "0000000000" + CutString(MD5(source + keyb), 0, 16)
					+ source;

			byte[] temp = RC4(source.getBytes("GBK"), cryptkey);

			return keyc + Base64.encode(temp);
			}catch(Exception e){
				e.printStackTrace();
				return"";
			}
		}
		public static void main(String[] args) throws UnsupportedEncodingException {
			String key = "007ac1demeca8m4dbbmbe22mf83d97b0d15a";
			String ss="10.10.0.193    ";
//			String xml = "10.10.0.193    <?xml version=\"1.0\" encoding=\"GBK\"?><PACKET type=\"REQUEST\" version=\"1.0\"> <HEAD><TRANSTYPE>SNY</TRANSTYPE><TRANSCODE>20022</TRANSCODE><USER>test</USER><PASSWORD>test002</PASSWORD><SVCSEQNO>201106241400005</SVCSEQNO></HEAD><THIRD><EXTENTERPCODE>0041</EXTENTERPCODE><EXTBRANCHCODE>00000000</EXTBRANCHCODE><EXTSEQUENCENO>0000001</EXTSEQUENCENO><EXTRISKCODE>0507</EXTRISKCODE><TRANSDATE>2017-10-19</TRANSDATE><TRANSTIME>16:35:00</TRANSTIME><EXTOPERATORCODE>08040006</EXTOPERATORCODE></THIRD><BODY><PAGENUM>20</PAGENUM><PAGE>1</PAGE><INFOTRANSNO></INFOTRANSNO><OPERATESITE>DMZYG</OPERATESITE><NEWFLAG>1</NEWFLAG><BRANDNAME>骊威DFL7163AA旅行轿车</BRANDNAME><MODELCODE>RCABWD0009</MODELCODE><CARBRAND></CARBRAND><FAMILYID></FAMILYID><ALIASNAME></ALIASNAME><CARYEAR></CARYEAR><CARKIND></CARKIND><CARSTYLE></CARSTYLE><SEATCOUNT></SEATCOUNT><TONCOUNT></TONCOUNT><PURCHASEPRICE></PURCHASEPRICE><BIZFLAG></BIZFLAG><COMCODE>08510000</COMCODE><RISKCODE>0507</RISKCODE><LICENSENO>新车</LICENSENO><LICENSETYPE></LICENSETYPE><ENGINENO></ENGINENO><FRAMENO></FRAMENO><ECDEMICVEHICLE>0</ECDEMICVEHICLE><MODELNAME>骊威DFL7163AA旅行轿车</MODELNAME><COMPLETEKERBMASS></COMPLETEKERBMASS><ENROLLDATE></ENROLLDATE><VEHICLESTYLEDESC>K33</VEHICLESTYLEDESC><EXHAUSTSCALE></EXHAUSTSCALE><MAKEDATE></MAKEDATE><MADEFACTORY></MADEFACTORY><CHANNELTYPE>N05</CHANNELTYPE><USENATURECODE>8A</USENATURECODE><MAINCARKINDCODE>A</MAINCARKINDCODE><NEWCARFLAG>1</NEWCARFLAG></BODY></PACKET>";
//			System.out.println(xml);
			
			JSONObject json = new JSONObject();
			json.put("username", "thinkgem");
			json.put("password", "admin");
			String xml = json.toString();
//			try {
//				xml = new String(xml.getBytes(),"GBK");
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
			System.out.println(xml);
			String reXMl=authcodeEncode(xml,key);
			System.out.println(reXMl);
			System.out.println("\n分割线");
			String reXML=authcodeDecode(reXMl,key);
//			try {
//				reXML = new String(reXMl.getBytes(),"UTF-8");
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			System.out.println(reXML);  
//			String a = "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";
			String b = new String(authcodeDecode(reXML, "007ac1demeca8m4dbbmbe22mf83d97b0d15a").getBytes(),"GBK");
			System.out.println(b);
			System.out.println(authcodeDecode(reXML, "007ac1demeca8m4dbbmbe22mf83d97b0d15a"));
			System.out.println(new String (b.getBytes("GBK"),"UTF-8"));
			if(reXML.equals("")){
				System.out.println("解密出错");
			}
			System.out.println("paytest:"+authcodeEncode("DMZYG","wtf8Y59PMv1jlaqYJgPvj254yis68P"));
			
			
		}
		/**
		 * @param source:原始字符串
		 * @param key：秘钥
		 * @param operation：加解密操作
		 * @param expiry：加密字串过期时间
		 * @return：加密后的字符串
		 */
		private static String authcode(String source, String key,
				String operation, int expiry) {
			try {
				if (source == null || key == null) {
					return "";
				}
				
				int ckey_length = 4;
				String keya, keyb, keyc, cryptkey, result;
				
				key = MD5(key);
				
				keya = MD5(CutString(key, 0, 16));
				
				keyb = MD5(CutString(key, 16, 16));
				
				keyc = ckey_length > 0 ? (operation.equals(Decode) ? CutString(
						source, 0, ckey_length) : RandomString(ckey_length))
						: "";
						
						cryptkey = keya + MD5(keya + keyc);
						
						if (operation.equals(Decode)) {
							byte[] temp;
							
							temp = Base64.decode(CutString(source, ckey_length));
							result = new String(RC4(temp, cryptkey));
							
							//add by zhaoyq 20161103 start reason:因编码问题配合去哪儿网修改加密方法
							if (result != null && result != "") {
			                    return CutString(result, 26);
			                }
							//add by zhaoyq 20161103 end reason:因编码问题配合去哪儿网修改加密方法
							
							if (CutString(result, 10, 16).equals(
									CutString(MD5(CutString(result, 26) + keyb), 0, 16))) {
								return CutString(result, 26);
							} else {
								temp = Base64.decode(CutString(source + "=", ckey_length));
								result = new String(RC4(temp, cryptkey));
								if (CutString(result, 10, 16)
										.equals(CutString(
												MD5(CutString(result, 26) + keyb), 0, 16))) {
									return CutString(result, 26);
								} else {
									temp = Base64.decode(CutString(source + "==",
											ckey_length));
									result = new String(RC4(temp, cryptkey));
									if (CutString(result, 10, 16).equals(
											CutString(MD5(CutString(result, 26) + keyb), 0,
													16))) {
										return CutString(result, 26);
									} else {
										return "2";
									}
								}
							}
						} else {
							return "加密操作不正确";
						}
			} catch (Exception e) {
				return "";
			}
		}
		/**
		 * @author liujunwei
		 * @功能：解密算法
		 * @param source
		 * @param key
		 * @param expiry
		 * @return
		 * @date 20150107
		 */
		public static String authcodeDecode(String source, String key) {
			return authcode(source, key, Decode, 0);

		}
}
