package com.youlb.utils.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.youlb.entity.common.ResultDTO;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.exception.JsonException;

public class Synchronization_sip {
	/**
	    * 同步sip账号
	    * @param sipNum
	    * @param user_sip
	    * @param password
	    * @param sipType
	    * @param neiborName
	    * @throws IOException
	    * @throws ClientProtocolException
	    * @throws JsonException
	    * @throws UnsupportedEncodingException
	    * @throws BizException
	    */
		public static void synchronization_sip(String sipNum, String user_sip,
				String password, String sipType, String fs_ip, Integer fs_port)
				throws IOException, ClientProtocolException, JsonException,
				UnsupportedEncodingException, BizException {
			CloseableHttpClient httpClient = HttpClients.createDefault();
				//同步数据以及平台
				HttpPost request = new HttpPost(SysStatic.FIRSTSERVER+"/device/save_device_usersToFirst.json");
				List<BasicNameValuePair> formParams = new ArrayList<BasicNameValuePair>();
				//获取服务器ip
				formParams.add(new BasicNameValuePair("user_sip", user_sip));
				formParams.add(new BasicNameValuePair("user_password", password));
				formParams.add(new BasicNameValuePair("local_sip", sipNum));
				formParams.add(new BasicNameValuePair("sip_type", sipType));
				formParams.add(new BasicNameValuePair("fs_ip", fs_ip));
				formParams.add(new BasicNameValuePair("fs_port", fs_port+""));
				UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formParams, "UTF-8");
				request.setEntity(uefEntity);
				CloseableHttpResponse response = httpClient.execute(request);
				if(response.getStatusLine().getStatusCode()==200){
					HttpEntity entity_rsp = response.getEntity();
					ResultDTO resultDto = JsonUtils.fromJson(EntityUtils.toString(entity_rsp), ResultDTO.class);
					if(resultDto!=null){
						if(!"0".equals(resultDto.getCode())){
							throw new BizException("同步sip账号出错");
						}
					}
				}else{
					throw new BizException("同步sip账号出错！");
				}
		}
}
