package com.youlb.utils.test;

import com.youlb.utils.sms.SmsUtil;

/** 
 * @ClassName: TestSms.java 
 * @Description: TODO 
 * @author: Pengjy
 * @date: 2015年7月28日
 * 
 */
public class TestSms {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String mess = "【德士特科技】欢迎使用空气小子，您的验证码：566105，10分钟内有效";
		boolean b = SmsUtil.sendSMS("15974105603", mess);
         System.out.println(b);
	}

}
