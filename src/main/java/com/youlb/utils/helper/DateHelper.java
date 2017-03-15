package com.youlb.utils.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/** 
 * @ClassName: DateHelper.java 
 * @Description: 日期工具类
 * @author: Pengjy
 * @date: 2015年6月26日
 * 
 */
public class DateHelper {
	/**
	 * 按照格式把字符串转换成Date
	 * @param str
	 * @param format
	 * @return
	 */
	public static Date strParseDate(String str,String format){
		if(StringUtils.isNotBlank(format)&&StringUtils.isNotBlank(str)){
			SimpleDateFormat sd = new SimpleDateFormat(format);
			Date date = null;
			try {
				if(StringUtils.isNotBlank(str)){
					date =  sd.parse(str);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return date;
		}else{
			return null;
		}
		
	}
	public static String dateFormat(Date date,String format){
		if(date!=null&&StringUtils.isNotBlank(format)){
			SimpleDateFormat sd = new SimpleDateFormat(format);
			return sd.format(date);
		}else{
			return "";
		}
	}
	
   
}
