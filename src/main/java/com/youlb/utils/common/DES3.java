package com.youlb.utils.common;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class DES3 {

	private final static String Algorithm = "DESede";
	static {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
	}

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

	public static byte[] decryptMode(byte[] keybyte, byte[] src) {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
			// 解密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey);
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

	// 转换成十六进制字符串
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
			if (n < b.length - 1) {
				hs = hs + ":";
			}
		}
		return hs.toUpperCase();
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
	
	/** 十六进制转换成十进制字符串
	 * Convert hex string to byte[] 
	 * @param hexString the hex string 
	 * @return byte[] 
	 */  
	public static byte[] hexStringToBytes(String hexString) {  
	    if (hexString == null || hexString.equals("")) {  
	        return null;  
	    }  
	    hexString = hexString.toUpperCase();  
	    int length = hexString.length() / 2;  
	    char[] hexChars = hexString.toCharArray();  
	    byte[] d = new byte[length];  
	    for (int i = 0; i < length; i++) {  
	        int pos = i * 2;  
	        d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));  
	    }  
	    return d;  
	}  
	/** 
	 * Convert char to byte 
	 * @param c char 
	 * @return byte 
	 */  
	 private static byte charToByte(char c) {  
	    return (byte) "0123456789ABCDEF".indexOf(c);  
	}  
	public static void main(String[] args) throws IOException {
		final byte[] keyBytes = {0x11, 0x22, 0x4C, 0x56, (byte) 0x88, 0x13,
			 0x40, 0x38, 0x28, 0x25, 0x79, 0x51, (byte) 0xCB, (byte) 0xDD,
			 0x55, 0x66, 0x77, 0x29, 0x74, (byte) 0x98, 0x30, 0x40, 0x36,(byte) 0xE2}; // 24字节的密钥

		 // 添加新安全算法,如果用JCE就要把它添加进去
		// Security.addProvider(new com.sun.crypto.provider.SunJCE());
//		 String mima = "123456123456123456123456";
		 
//		 System.out.println(Arrays.toString(mima.getBytes(16)));
//		 Byte[] all = new Byte[32];
//		 List<Byte> list = new ArrayList<Byte>();
		 String username = "10000021";
		 byte[] usernameByte = encryptMode(keyBytes, username.getBytes());
		 
//		 usernameByte=username.getBytes();
//		 System.out.println(Arrays.toString(usernameByte));
		 System.out.println(bytesToHexString(usernameByte));
		 
		 byte[] srcBytes = decryptMode(keyBytes, usernameByte);
		 System.out.println("解密后的字符串:" + new String(srcBytes));
//		 for(int i=0;i<usernameByte.length;i++){
//			 list.add(usernameByte[i]);
//		 }
//		 String psw = "12345698";
//		 byte[] pswByte = encryptMode(keyBytes, psw.getBytes());
//		 for(int i=0;i<pswByte.length;i++){
//			 list.add(pswByte[i]);
//		 }
//		 pswByte=psw.getBytes();
//		 System.out.println(bytesToHexString(pswByte));
//		 Byte[] array = list.toArray(all);
//		 byte[] b = new byte[array.length];
//		 for(int i=0;i<array.length;i++){
//			 b[i]=array[i];
//		 }

//		 System.out.println(Arrays.toString(b));
//		 System.out.println(bytesToHexString(b));
//		 String ip = "19216811";
//		 byte[] ipByte = encryptMode(mima.getBytes(), ip.getBytes());
////		 ipByte=ip.getBytes();
//		 System.out.println(Arrays.toString(ipByte));
//		 System.out.println(bytesToHexString(ipByte));
//		 
//		 String port = "8080";
//		 byte[] portByte = encryptMode(mima.getBytes(), port.getBytes());
//		 portByte=port.getBytes();
//		 System.out.println(bytesToHexString(portByte));
//		 System.out.println("解密后的字符串:" + byte2hex(srcBytes));
//		     StringBuffer str=new StringBuffer();
//             int len=0;
//             FileInputStream is=new FileInputStream(new File("d:/key.txt"));
//             InputStreamReader isr= new InputStreamReader(is);
//             BufferedReader in= new BufferedReader(isr);
//             String line=null;
//             while( (line=in.readLine())!=null )
//             {
//                 if(len != 0)  // 处理换行符的问题
//                 {
//                     str.append("\r\n"+line);
//                 }
//                 else
//                 {
//                     str.append(line);
//                 }
//                 len++;
//             }
//             in.close();
//             is.close();
//		 byte[] srcBytes = decryptMode(mima.getBytes(), hexStringToBytes(str.toString()));
//		 System.out.println("解密后的字符串:" + Arrays.toString(srcBytes));
	}
}
