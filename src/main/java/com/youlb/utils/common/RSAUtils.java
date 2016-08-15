package com.youlb.utils.common;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class RSAUtils {
	
	public static final String KEY_ALGORITHM = "RSA";
	private static final String PUBLIC_KEY = "RSAPublicKey";
	private static final String PRIVATE_KEY = "RSAPrivateKey";
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
	/**
	 * 获取公钥
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY); 
        byte[] publicKey = key.getEncoded(); 
       return encryptBASE64(publicKey);

   }
	/**
	 * 获取私钥
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
   public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY); 
        byte[] privateKey =key.getEncoded(); 
      return encryptBASE64(privateKey);

    }  

          

   public static byte[] decryptBASE64(String key) throws Exception {               
       return (new BASE64Decoder()).decodeBuffer(key);               

   }                                 


   public static String encryptBASE64(byte[] key) throws Exception {               
       return (new BASE64Encoder()).encodeBuffer(key);               

   }       

   
    
	public static Map<String, Object> initKey() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		keyPairGen.initialize(1024);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		Map<String, Object> keyMap = new HashMap<String, Object>(2);
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
	       return keyMap;
	
   }
	    /**
	     * 用私钥解密
	     * @param data  加密数据
	     * @param key   密钥
	     * @return
	     * @throws Exception
	     */
	    public static byte[] decryptByPrivateKey(byte[] data,String key)throws Exception{
	        //对私钥解密
	        byte[] keyBytes = decryptBASE64(key);
	        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
	        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
	        Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
	        //对数据解密
	        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
	        cipher.init(Cipher.DECRYPT_MODE, privateKey);
	        return cipher.doFinal(data);
	    }
	    /**
	     * 用公钥加密
	     * @param data  加密数据
	     * @param key   密钥
	     * @return
	     * @throws Exception
	     */
	    public static byte[] encryptByPublicKey(byte[] data,String key)throws Exception{
	        //对公钥解密
	        byte[] keyBytes = decryptBASE64(key);
	        //取公钥
	        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
	        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
	        Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
	        //对数据解密
	        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
	        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
	        return cipher.doFinal(data);
	    }
	    /**
          * 用私钥对信息生成数字签名
          * 
          * @param data加密数据
          * @param privateKey私钥
          * @return
          * @throws Exception
	     */
         public static String sign(byte[] data, String privateKey) throws Exception {
//             // 解密由base64编码的私钥
//             byte[] keyBytes = decryptBASE64(privateKey);
           //对私钥解密
   	        byte[] hexBytes = DES3.hexStringToBytes(privateKey);
   	        byte[] keyBytes = DES3.decryptMode(SysStatic.KEYBYTES,hexBytes);
            // 构造PKCS8EncodedKeySpec对象
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            // KEY_ALGORITHM 指定的加密算法
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
             // 取私钥匙对象
             PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
             // 用私钥对信息生成数字签名
             Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
             signature.initSign(priKey);
             signature.update(data);
             return DES3.bytesToHexString(signature.sign());
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
          public static boolean verifySign(byte[] data, String publicKey, String sign) throws Exception {
              // 解密由base64编码的公钥
//              byte[] keyBytes = decryptBASE64(publicKey);
              //对私钥解密
 	          byte[] hexBytes = DES3.hexStringToBytes(publicKey);
 	          byte[] keyBytes = DES3.decryptMode(SysStatic.KEYBYTES,hexBytes);
              // 构造X509EncodedKeySpec对象
              X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
              // KEY_ALGORITHM 指定的加密算法
              KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
              // 取公钥匙对象
              PublicKey pubKey = keyFactory.generatePublic(keySpec);
              Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
              signature.initVerify(pubKey);
              signature.update(data);
              // 验证签名是否正常
              return signature.verify(DES3.hexStringToBytes(sign));
          }
  	    
  	  /**
  	     * 用私钥加密
  	     * @param data  加密数据
  	     * @param key   密钥
  	     * @return
  	     * @throws Exception
  	     */

  	    public static byte[] encryptByPrivateKey(byte[] data,String key,byte[] des3PW)throws Exception{
  	    	//对私钥解密
  	        byte[] hexBytes = DES3.hexStringToBytes(key);
  	        byte[] keyBytes = DES3.decryptMode(des3PW,hexBytes);
  	        //取私钥
  	        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
  	        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
  	        Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
  	        //对数据加密
  	        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
  	        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
  	        return cipher.doFinal(data);
  	    }
  	    
  	  /**
	     * 用公钥解密
	     * @param data  加密数据
	     * @param key   密钥
	     * @return
	     * @throws Exception
	     */
	    public static byte[] decryptByPublicKey(byte[] data,String key,byte[] des3PW)throws Exception{
	        //对私钥解密
	        byte[] hexBytes = DES3.hexStringToBytes(key);
	        byte[] keyBytes = DES3.decryptMode(des3PW, hexBytes);
	        
	        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
	        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
	        Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
	        //对数据解密
	        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
	        cipher.init(Cipher.DECRYPT_MODE, publicKey);
	        return cipher.doFinal(data);
	    }
	    
	    //用私钥加密需要公钥解，公钥加密需要私钥解
	    public static void main(String[] args) {
	    	
	    	String sign = "2d67bcb2807283ad3ccc142e9b184380b3957a65f39046dbef60a6991a19eef76db5081aa407bbb908bdf2bece75cb4c41ac4835721c5450a2d92cc47abe24f9c7ecae36b47c4ec0d83de12f4ee274b804c8030cfbb7436dac90b8f0eaf20456df9b671003cdbc9bb8a97cdfee5b0195829e6b2d34b4eaab2159c3f98c726356";
			
	    	try {
				String sss = "hello world";
				Map<String, Object> keyMap = initKey();
				//获取私钥
				String privateKey = getPrivateKey(keyMap);
				 privateKey = "2385d83466f96c62e085917a03570d674b4341201b347026ace759acb87c3e9b3214e81c675b1907fc3b0d1e3d67d221b0f54e773717cd2b7f37c41279924f209bf0c4f1b653e849cd7a1855c04967ae30e244d2ff8bc988400711016d65a9787f3492670ec3aec1a23bee9edb8c169c8e1fd1c9dee659378f32d35c4b6cc7b16993fe916b77cfa3bab1991b9d604320b45106017e6b67e29f102283ae4fbc1fc93e553d8a4c580d9c5aedee684ea67f3721e24ffe674efbd6984c6e981983d6fa9d4c6521a3dbcf3d09d6b8cf97b29a5cb262c0b1a861510228bf0fa6028704eb223f9188ce0b4bdec409be8ba92d458a5ca8d3caa01bdb514c70bbb94967d5433a748f7621c49223bd8008f99ab98073801714528e245263e1bbe2c1e068776beda6aa3a991727114966ddfe12ff511e04b1d47e88d40d9cb28e96aa55a78181f0fc3784294b1be483fa85283bfcf310e712d5e049984e50b4788dc0bcc1606e851827dc13588333b731fe67e6b4cc5b7bc42229556c3cec88dae9d5e7c0aa7b27999fd9cca761ca0c3001ab15c2ca3028fe8b62bf531b857b2d545bd72702bc9aa8690cc3d3f2098924bcf3e886075351651864a9687bb45b24c5928b7a534ea283175bd1b320b06d228c64a6d4affc63f51cd83974bc334dbd8c6ce442f306f8a6a961959418738671057d44faf273454629f0f57964e634a8b3cd597ff4a40eb46063a01d6d8a57fcd4a7c1d7ec6fc86e2f8d77ac8c86c6ca0efb19386a633eb379232539cb4dff375247f87a9a7a5acffdd238f61c1bfab9e59f25cd4ba13722ee376576e1ce6bd556e315529d57af27d5f5b8cea08ef70cc3cc1dc20b853d106ff1fba957140b6122d2925fd5bc3fcce07b51e0d75100a93b3604ff97";
//				System.out.println(privateKey);
				byte[] encryptByPrivateKey = encryptByPrivateKey(sss.getBytes(), privateKey,SysStatic.KEYBYTES);
				String s = DES3.bytesToHexString(encryptByPrivateKey);
				System.out.println(s);
                System.out.println("加密:"+new String(encryptByPrivateKey));
                //获取公钥
                String publicKey = getPublicKey(keyMap);
                publicKey="64bec213c6ca3321543a3d3b2a5fdbf3b911dee6e2f40fe89be6e4fa1ec8d0d7fc3b0d1e3d67d221b0f54e773717cd2b7f37c41279924f209bf0c4f1b653e849cd7a1855c04967ae30e244d2ff8bc988400711016d65a9787f3492670ec3aec1a23bee9edb8c169c8e1fd1c9dee659378f32d35c4b6cc7b16993fe916b77cfa3bab1991b9d604320b45106017e6b67e29f102283ae4fbc1fc93e553d8a4c580dae377730b3f35963";
//                System.out.println(publicKey);
                byte[] decryptByPrivateKey = decryptByPublicKey(DES3.hexStringToBytes(s), publicKey,SysStatic.KEYBYTES);
                System.out.println("解密:"+new String(decryptByPrivateKey));
				
//                byte[] encryptByPublicKey = encryptByPublicKey(sss.getBytes(), publicKey);
//                byte[] decryptByPrivateKey2 = decryptByPrivateKey(encryptByPublicKey, privateKey);
//                System.out.println(new String(decryptByPrivateKey2));
//                String sign = sign(sss.getBytes(), privateKey);
                
                
//                boolean b = verifySign(sss.getBytes(), publicKey, sign);
//                System.out.println(b);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
}
