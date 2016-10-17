package cn.com.dhc.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHAUtil {

	public static String encoder(String shaName,String str){
		if(str==null){
			return null;
		}
		MessageDigest message;
		try {
			message = MessageDigest.getInstance(shaName);
			message.update(str.getBytes());
			byte[] messageDigest = message.digest();
			StringBuffer hexString = new StringBuffer();  
            // 字节数组转换为 十六进制 数  
            for (int i = 0; i < messageDigest.length; i++) {  
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);  
                if (shaHex.length() < 2) {  
                    hexString.append(0);  
                }  
                hexString.append(shaHex);  
            }  
            return hexString.toString().toLowerCase();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
//	private static String getFormattedText(byte[] bytes) {
//		int len = bytes.length;
//		StringBuilder buf = new StringBuilder(len * 2);
//		// 把密文转换成十六进制的字符串形式
//		for (int j = 0; j < len; j++) { 			buf.append(HEX_DIGITS[(bytes[j] &gt;&gt; 4) &amp; 0x0f]);
//			buf.append(HEX_DIGITS[bytes[j]]);
//		}
//		return buf.toString();
//	}
}
