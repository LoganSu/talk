package com.youlb.utils.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.StringUtils;
import org.bouncycastle.util.encoders.Hex;


public class DecodUtils {
	private final static String Algorithm = "DESede";
	/**
	 * 获取证书信息
	 * @param CAPath证书地址
	 * @return String[] [0]类型[1]版本[2]有效开始时间[3]失效时间[4]序列号[5]颁发者[6]签名算法[7]公钥算法[8]公钥算法[9]公钥
	 */
	  
	public static String[] read_Normal(String CAPath) {
	    String[] values = new String[10];
		FileInputStream file_inputstream=null;
	    try {
			file_inputstream = new FileInputStream(CAPath);
			if(file_inputstream!=null){
				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				CertificateFactory certificate_factory = CertificateFactory.getInstance("X.509");
				X509Certificate x509certificate = (X509Certificate) certificate_factory.generateCertificate(file_inputstream);
				values[0]=x509certificate.getType(); //类型
				values[1]=Integer.toString(x509certificate.getVersion());//版本
				values[2]=sd.format(x509certificate.getNotBefore());//有效开始时间
				values[3]=sd.format(x509certificate.getNotAfter());//失效时间
				values[4]=x509certificate.getSerialNumber().toString(10);//序列号
				values[5]=x509certificate.getIssuerDN().getName();//颁发者
				values[6]=x509certificate.getSigAlgName();//签名算法
				values[7]=new String(Hex.encode(x509certificate.getSignature()));//签名
				values[8]=x509certificate.getPublicKey().getAlgorithm();//公钥算法
				PublicKey publicKey = x509certificate.getPublicKey();
				values[9]=getPublicKey(publicKey);
			}
	    } catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(file_inputstream!=null){
				try {
					file_inputstream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return values;
	}
	/**
	 * 获取公钥
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String getPublicKey(PublicKey pubKey) throws Exception {
        byte[] publicKey = pubKey.getEncoded(); 
        return bytesToHexString(encryptMode(SysStatic.KEYBYTES, publicKey));

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
	/**
     * 用公钥解密
     * @param data  加密后的数据字符串
     * @param key   密钥
     * @return
     * @throws Exception
     */
    public static String decryptByPublicKey(String data,String publicKeyStr,byte[] des3PW)throws Exception{
    	if(StringUtils.isBlank(data)||StringUtils.isBlank(publicKeyStr)||des3PW==null){
    		return null;
    	}
        //对共钥解密
        byte[] hexBytes = hexStringToBytes(publicKeyStr);
        byte[] keyBytes = decryptMode(des3PW, hexBytes);
        
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        //对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] b = cipher.doFinal(hexStringToBytes(data));
        return new String(b);
    }
    
    /** 十六进制转换成十进制字符串
	 * Convert hex string to byte[] 
	 * @param hexString the hex string 
	 * @return byte[] 
	 */  
	private static byte[] hexStringToBytes(String hexString) {  
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
    /**
     * 3des解密
     * @param keybyte 秘钥
     * @param src 加密字节数组
     * @return
     */
     
	private static byte[] decryptMode(byte[] keybyte, byte[] src) {
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
	
	 /**
     * 校验数字签名
     * @param data加密数据
     * @param publicKey公钥
     * @param sign 数字签名
     * @return 校验成功返回true 失败返回false
     * @throws Exception
     * 
    */
    public static boolean verifySign(byte[] data, String publicKey, String sign,byte[] des3PW) throws Exception {
        // 解密由base64编码的公钥
//        byte[] keyBytes = decryptBASE64(publicKey);
        //对私钥解密
         byte[] hexBytes = hexStringToBytes(publicKey);
         byte[] keyBytes = decryptMode(des3PW,hexBytes);
        // 构造X509EncodedKeySpec对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        // 取公钥匙对象
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initVerify(pubKey);
        signature.update(data);
        // 验证签名是否正常
        return signature.verify(hexStringToBytes(sign));
    }
    public static void main(String[] args) throws Exception {
    	//获取私钥
    	StringBuffer str=new StringBuffer();
        int len=0;
        FileInputStream is=new FileInputStream(new File("e:/key.txt"));
        InputStreamReader isr= new InputStreamReader(is);
        BufferedReader in= new BufferedReader(isr);
        String line=null;
        while( (line=in.readLine())!=null ){
        	// 处理换行符的问题
            if(len != 0){
                str.append("\r\n"+line);
            }else{
                str.append(line);
            }
            len++;
        }
        in.close();
        is.close();
        
        
        String sss = "10000023 23456789 192.168.1.222 8011";
        System.out.println("私钥："+str);
        byte[] encryptByPrivateKey = RSAUtils.encryptByPrivateKey(sss.getBytes(), str.toString(),SysStatic.KEYBYTES);
		String s = DES3.bytesToHexString(encryptByPrivateKey);
        System.out.println("加密后:"+s);
        //获取公钥
        String[] info = read_Normal("e:/certificate/lee.cer");
        String publicKey=info[9];
        System.out.println("公钥："+publicKey);
        String decryptByPrivateKey = decryptByPublicKey(s, publicKey,SysStatic.KEYBYTES);
        System.out.println("解密后:"+new String(decryptByPrivateKey));
		
        
	}
}
