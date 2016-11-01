package com.youlb.utils.common;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.BucketManager.Batch;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

public class QiniuUtils {
	private static Logger log = LoggerFactory.getLogger(QiniuUtils.class);

	//设置好账号的ACCESS_KEY和SECRET_KEY
	 public static String ACCESS_KEY = "hojgJGktFTEHjSsGFJWHtkj7hgsiVY9NNZFWW7Zs";
	 public static String SECRET_KEY = "G-9k5uef_zxofDvDAn84rj8qCiVJgiBuGAOTeoEh";
	  //要上传的空间
	 public static String bucketname = "sayee";
	 //url
	 public static String URL = "http://upload.gdsayee.com.cn/";
	  //密钥配置
	 public static  Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

	  //简单上传，使用默认策略，只需要设置上传的空间名就可以了
	  public static String getUpToken(){
		  //默认3600s
	      return auth.uploadToken(bucketname);
	  }
      /** 
       * 
       * @param FilePath 文件路径
       * @param key 上传到七牛后保存的文件名
       * @throws IOException
       */
	  public static int upload(String FilePath,String key) throws IOException{
		  //创建上传对象
		  UploadManager uploadManager = new UploadManager();
	    try {
	          //调用put方法上传
		      String token = getUpToken();
		      Response res = uploadManager.put(FilePath, key, token);
		      //打印返回的信息
		      return res.statusCode;
	      } catch (QiniuException e) {
	          Response r = e.response;
	          // 请求失败时打印的异常的信息
		      log.error("qiniu:"+r.error);
	      }
		  return 0;       
	  }
	  
	  /** 
       * 
       * @param FilePath 文件路径
       * @param key 上传到七牛后保存的文件名
       * @throws IOException
       */
	  public static int upload(byte[] data,String key) throws IOException{
		  //创建上传对象
		  UploadManager uploadManager = new UploadManager();
	    try {
	          //调用put方法上传
		      String token = getUpToken();
		      Response res = uploadManager.put(data, key, token);
		      //打印返回的信息
		      return res.statusCode;
	      } catch (QiniuException e) {
	          Response r = e.response;
	          // 请求失败时打印的异常的信息
		      log.error("qiniu:"+r.error);
	      }
		  return 0;       
	  }
	  
	  
       /**
        * 获取下载地址
        * @param key 上传到七牛后保存的文件名 
        * @return
        */
	   public static String getDownloadUrl(String key){
	       //调用privateDownloadUrl方法生成下载链接,第二个参数可以设置Token的过期时间
		   return auth.privateDownloadUrl(URL+key,3600);
	   }
	  /**
	   * 删除文件
	   * @param key 上传到七牛后保存的文件名 
	   * @throws QiniuException
	   */
	  public static void delete(String key) throws QiniuException{
		    //实例化一个BucketManager对象
		    BucketManager bucketManager = new BucketManager(auth);
		    Batch batch = new Batch();
		    batch.delete(bucketname, new String[]{});
		    bucketManager.batch(batch);
		    try {
		      //调用delete方法移动文件
		      bucketManager.delete(bucketname, key);
		    } catch (QiniuException e) {
		      //捕获异常信息
		      Response r = e.response;
		      log.error("qiniu:"+r.error);
		    }
	  }
	  

	public static void main(String[] args) throws IOException {
		System.out.println(getUpToken());
//		String key ="test/test.java";
//		upload("d:/Test.java", key);
//		System.out.println(getDownloadUrl(key));
//		delete(key);
	}

}
