package com.youlb.utils.sms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import com.youlb.utils.exception.BizException;

public class SmsUtil {
	private final static String UID = "unionbon";
	private final static String KEY = "95a988860dc4d7b0bf2a";
//	private final static String SIGN = "【友联邦科技】";
	private final static String SMS_URL = "http://%s.sms.webchinese.cn/";
	
	public static boolean sendSMS(String mobilePhone, String message) {
		String url = String.format(SMS_URL, "utf8");
		Header[] headers = new Header[] { new BasicHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=utf8") };
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("Uid", UID));
		params.add(new BasicNameValuePair("Key", KEY));
		params.add(new BasicNameValuePair("smsMob", mobilePhone));
		params.add(new BasicNameValuePair("smsText", message));
		String result = HttpClientUtil.post(url, headers, params, "utf8");
		if (result != null) {
			if (Integer.parseInt(result) > 0) {
				return true;
			}
		}
		return false;
	}
	
	 
	/**
	 * 获取6位随机数字符串
	 * @return
	 */
	public static String random() throws BizException{
		Random r = new Random ();
		String num = r.nextInt(999999)+"";
		if(num.length()<6){
			num = random();
		}
		return num;
	}
	
	public static void main(String[] args) {
		String s = random();
		String message = "哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈";
		Boolean b = sendSMS("13610068576", message);
		System.out.println(b);
	}
	
}
