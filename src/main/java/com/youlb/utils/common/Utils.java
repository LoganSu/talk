package com.youlb.utils.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

/** 
 * @ClassName: Utils.java 
 * @Description: 普通方法工具类 
 * @author: Pengjy
 * @date: 2015-11-5
 * 
 */
public class Utils {
   /**
    * byte[]转换成string
    * @param bit
    * @return
    */
   public static String byteToString(byte[] bit){
	   StringBuilder sb = new StringBuilder();
	   if(bit==null){
		   return null;
	   }
	   for(byte b:bit){
		   sb.append(b);
	   }
	   return sb.toString();
   }
   /**
    * 字符串原样转换成byte[]
    * @param string
    * @return
    */
   public static byte[] stringToByte(String string){
	   if(StringUtils.isBlank(string)||!string.matches("\\d+")){
		   return null;
	   }
	   String[] s = string.split("");
	   byte[]  b= new byte[s.length-1];
	   for(int i=1;i<s.length;i++){
		   b[i-1]= Byte.parseByte(s[i]);
	   }
	   return b;
   }
   /**
    * 格式化时间
    * @param date
    * @param pattern
    * @return
    */
   public static String dateToString(Date date,String pattern ){
	   SimpleDateFormat sd = new SimpleDateFormat(pattern);
	   if(date!=null){
		  return sd.format(date);
	   }
	   return null;
   }
   
   public static String getRemoteAddrIp(HttpServletRequest request) {
       String ipFromNginx = getHeader(request, "X-Real-IP");
//       System.out.println("ipFromNginx:" + ipFromNginx);
//       System.out.println("getRemoteAddr:" + request.getRemoteAddr());
       return StringUtils.isEmpty(ipFromNginx) ? request.getRemoteAddr() : ipFromNginx;
   }


   private static String getHeader(HttpServletRequest request, String headName) {
       String value = request.getHeader(headName);
       return !StringUtils.isBlank(value) && !"unknown".equalsIgnoreCase(value) ? value : "";
   }
   
   public static String getIpAddr(HttpServletRequest request) {
       String ip = request.getHeader("x-forwarded-for");  
       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
           ip = request.getHeader("Proxy-Client-IP");  
       }  
       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
           ip = request.getHeader("WL-Proxy-Client-IP");  
       }  
       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
           ip = request.getRemoteAddr();  
       }  
       return ip;  
}  
}
