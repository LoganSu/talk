package com.youlb.utils.common;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.digest.DigestUtils;

public class DES3EncodeUtils {
	private final static String Algorithm = "DESede";
	public static byte[] encryptMode(byte[] keybyte, byte[] src) {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
			// 加密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
    }
	
	
	/**
	 * 转换成十六进制字符串
	 * @param src
	 * @return
	 */
	public static String bytesToHexString(byte[] src){  
	    StringBuilder stringBuilder = new StringBuilder("");  
	    if (src == null || src.length <= 0) {  
	        return null;  
	    }  
	    for (int i = 0; i < src.length; i++) { 
	        int v = src[i] & 0xFF;  
	        String hv = Integer.toHexString(v);  
	        if (hv.length() < 2) {  
	            stringBuilder.append(0);  
	        }  
	        stringBuilder.append(hv);  
	    }  
	    return stringBuilder.toString();  
	}
	
	
	public static void main(String[] args) throws IOException {
		final byte[] keyBytes = {0x11, 0x22, 0x4C, 0x56, (byte) 0x87, 0x13,
			 0x40, 0x38, 0x28, 0x25, 0x79, 0x51, (byte) 0xCB, (byte) 0xDD,
			 0x56, 0x65, 0x77, 0x23, 0x74, (byte) 0x98, 0x30, 0x40, 0x36,(byte) 0xE2}; // 24字节的密钥

		 String token = "1a3ae1e64cef429bb201d464db378775";
		 //加密
		 byte[] tokenByte = encryptMode(keyBytes, token.getBytes());
		 String dec =  bytesToHexString(tokenByte);
		 //加密后字符
		 System.out.println(dec);
		 //MD5传参
		 System.out.println(DigestUtils.md5Hex(dec));
	}
}
