package cn.com.dhc.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;

import cn.com.dhc.entity.TranslateResult;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BaiduTranslateService {

	public static String httpRequest(String requestUrl){
		StringBuffer buffer = new StringBuffer();
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
			httpUrlConn.setDoOutput(false);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			httpUrlConn.setRequestMethod("GET");
			httpUrlConn.connect();
			
			//获取返回的输入流并转为字符串
			InputStream in = httpUrlConn.getInputStream();
			InputStreamReader reader = new InputStreamReader(in,"utf-8");
			BufferedReader bufferedReader = new BufferedReader(reader);
			String str = null;
			while((str=bufferedReader.readLine())!=null){
				buffer.append(str);
			}
			bufferedReader.close();
			reader.close();
			in.close();
			httpUrlConn.disconnect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return buffer.toString();
	}
	
	public static String urlEncodeUTF8(String source){
		String encoder = null;
		try {
			encoder =URLEncoder.encode(source, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encoder;
	}
	
	public static String translate(String source){
		String dst = null;
		//随机数
		int salt = new Random().nextInt(999999);
		//百度APP ID
		String appid = "20161018000030376";
		//百度密钥
		String mkey = "eDLdn82mIGCnhMFHmUhb";
		//签名：appid+q+salt+密钥 的MD5值
		String sign = appid+source+salt+mkey;
		//百度翻译API地址
		String requestUrl = "http://api.fanyi.baidu.com/api/trans/vip/translate?from=auto&to=auto&q={0}&appid="+appid+"&salt="+salt+"&sign=";
		try {
			/*MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(sign.getBytes());
			byte[] res = digest.digest();
			sign = byteToHexString(res);*/
			sign = DigestUtils.md5Hex(sign);
			requestUrl = (requestUrl+sign).replace("{0}", urlEncodeUTF8(source));
			String json = httpRequest(requestUrl);
			System.out.println(json);
			TranslateResult result = new ObjectMapper().readValue(json, TranslateResult.class) ;
			dst = result.getTrans_result().get(0).getDst();
		} catch (Exception e) {
			e.printStackTrace();
			dst = "翻译出错，请稍后重试！";
		}		
		return "翻译结果："+dst;
	}
	
	/**
	 * 将传入的数组转化为十六进制的字符串形式
	 * @param args
	 * @return
	 */
	public static String byteToHexString(byte args[]){
		
			char[] hex = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
			StringBuffer buffer = new StringBuffer();
			for(byte b:args){
				int iRet = b;
				if(iRet<0){
					iRet+=256;	
				}
				int d1 = iRet / 16;
				int d2 = iRet % 16;
				buffer.append(hex[d1]).append(hex[d2]);
			}
			return buffer.toString();	
	}
	
	public static void main(String args[]){
		System.out.println(translate("who"));
	}
}
