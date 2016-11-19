package com.youlb.utils.common;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException; 
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.multipart.MultipartFile;


 
 
 

 
public class SHAEncrypt {
	public final static String MD5 = "MD5";
	public final static String NONE = "NONE";
	public final static String SHA_256 = "SHA-256";
	public final static String SHA_512 = "SHA-512";
	public final static String SHA_384 = "SHA-384";

	 /**
	 * 加密密码算法
	 * 
	 * @param pass    需要加密的原始密码
	 * @param algorithm    加密算法名称
	 * @return 加密后的密码
	 * @throws NoSuchAlgorithmException
	 *             当加密算法不可用时抛出此异常
	 */
	public static String digestString(String password, String alg) throws NoSuchAlgorithmException {
		String newPass;
		if (alg == null || MD5.equals(alg)) {
			newPass = DigestUtils.md5Hex(password);
		} else if (NONE.equals(alg)) {
			newPass = password;
		} else if (SHA_256.equals(alg)) {
			newPass = DigestUtils.sha256Hex(password);
		} else if (SHA_384.equals(alg)) {
			newPass = DigestUtils.sha384Hex(password);
		} else if (SHA_512.equals(alg)) {
			newPass = DigestUtils.sha512Hex(password);
		 
		} else {
			newPass = DigestUtils.shaHex(password);
		}
		return newPass;
	}

	/**
	 * 加密密码算法，默认的加密算法是MD5
	 * 
	 * @param password
	 *            未加密的密码
	 * @return String 加密后的密码
	 */
	public static String digestPassword(String password) {
		try {
			if (password != null && !"".equals(password)) {
				return digestString(password, MD5);
			} else {
				return null;
			}
		} catch (NoSuchAlgorithmException nsae) {
			throw new RuntimeException("Security error: " + nsae);
		}
	}

	/**
	 * 判断密码是不是相等，默认的加密算法是MD5
	 * 
	 * @param beforePwdd
	 *            要判断的密码
	 * @param afterPwd
	 *            加密后的数据库密码
	 * @return Boolean true 密码相等
	 * @throws UnsupportedEncodingException 
	 */
	public static boolean isPasswordEnable(String afterPwd ,String passWord,String userName) throws UnsupportedEncodingException {
		  if(passWord == null || userName == null ){
			 return false;
		 }else{
			String pwd = encodePassWord(passWord,userName);
			return pwd.equals(afterPwd);
		  }
   }

	
	/***
	 * 
	 * @param passWord 原密码
	 * @param userName 用户名
	 * @return 加密后密码
	 * @throws UnsupportedEncodingException 
 	 */
	public static String encodePassWord(String passWord,String userName) throws UnsupportedEncodingException{
		 //加密原密码
	 	byte[] pwby= DigestUtils.sha512(passWord.getBytes("utf-8"));
	 	//密码换位
	 	byte[] changepwby = new byte[pwby.length];
		for (int i = 0; i < pwby.length; i++) {
			changepwby[i]=pwby[pwby.length-i-1];
		}
		changepwby =  Base64.encodeBase64(changepwby);
		String pwdStr = new String (changepwby,"utf-8"); 
	 	for (int i = 0; i < 6; i++) {
			 pwdStr+=userName;
			 changepwby = DigestUtils.sha512(pwdStr.getBytes("utf-8"));
			 changepwby = Base64.encodeBase64(changepwby);
			 pwdStr = new String (changepwby,"utf-8"); 
		}
	 	return pwdStr;
	}
	/**
	 * springMVC上传文件生成md5校验码
	 * @param upload
	 * @return
	 * @throws Exception
	 */
	public static String fileMd5(File file) throws Exception {
		FileInputStream in = new FileInputStream(file);
		MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());  
        MessageDigest md5 = MessageDigest.getInstance("MD5");  
        md5.update(byteBuffer);  
        BigInteger bi = new BigInteger(1, md5.digest());  
        String value = bi.toString(16);  
	    return value.toUpperCase(); 
	}
	
	    
	public static void main(String[] args) throws Exception {
	 
        //Y0Dh3+2cuteG/0QXEA32hrBmiHgcit4aQOnX0egaxtkIS/nPjVjzCPMnxx9Qgxdrj/H1IsLMIKh2eyvvfgPaQ==
		try {
		String paw = encodePassWord("admin", "admin");
		 System.out.println(paw);
		boolean vv = isPasswordEnable(paw, "admin", "admin");
		 System.out.println(vv);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
//			logger.warn(e.getMessage(),e);
		}
//		String digestPassword = digestPassword("123456");
//		System.out.println(digestPassword.toUpperCase());46797DEC3E45C151552F6B63D830FFC5
		                                               //46797DEC3E45C151552F6B63D830FFC5
		String md = fileMd5(new File("d:/ylb-android(1.1.0.009).apk"));
         System.out.println(md);

		
   	}
   	
}
