package com.youlb.utils.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/** 
 * @ClassName: EmailMatcher.java 
 * @Description: TODO 
 * @author: Pengjy
 * @date: 2015年7月21日
 * 
 */
public class EmailMatcher {
   
	/**
	 * 邮箱格式验证
	 * @param email
	 * @return
	 */
	public static Boolean match(String email){
		if(StringUtils.isNotBlank(email)){
			String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
			Pattern p = Pattern.compile(str);
			Matcher matcher = p.matcher(email);
			return matcher.matches();
		}
		return false;
	}
}
