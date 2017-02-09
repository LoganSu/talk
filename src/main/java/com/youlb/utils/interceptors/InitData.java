package com.youlb.utils.interceptors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.youlb.dao.common.BaseDaoBySql;
import com.youlb.entity.common.ResultDTO;
import com.youlb.entity.houseInfo.Room;
import com.youlb.entity.management.Worker;
import com.youlb.utils.common.JsonUtils;
import com.youlb.utils.common.SysStatic;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.exception.JsonException;

public class InitData {
	private static Logger logger = LoggerFactory.getLogger(InitData.class);
	
	public void InitWorkerSipDate(ApplicationContext app) throws ClientProtocolException, IOException, BizException, ParseException, JsonException{
		BaseDaoBySql<Worker> workerDao = (BaseDaoBySql<Worker>) app.getBean("workerDao");
		//查询分组为空的房间
		String find = "from Worker";
	    String addSip ="update users set user_sip=?,user_password=?,sip_type=?,fs_ip=?,fs_port=? where local_sip=?";
		//更新分组
		List<Worker> workerList = workerDao.find(find, new Object[]{});
		if(workerList!=null&&!workerList.isEmpty()){
			logger.info("用户数："+workerList.size());
			//查询社区名称
			 StringBuilder sb= new StringBuilder();
			    sb.append("WITH RECURSIVE r AS (SELECT d.* from t_department d  where d.id=? ")
			    .append(" union ALL SELECT t_department.* FROM t_department, r WHERE t_department.id = r.fparentid )")
			    .append(" SELECT td.fremark from r INNER JOIN t_department_domain tdd  on tdd.fdepartmentid=r.id INNER JOIN t_domain td on td.id=tdd.fdomainid  where r.flayer='0'");
			for(Worker worker:workerList){
				List<String> list = workerDao.pageFindBySql(sb.toString(), new Object[]{worker.getDepartmentId()});
				if(list!=null&&!list.isEmpty()){
					String neibName = list.get(0).trim();
					logger.info("社区名称："+neibName);
					//同步数据以及平台
					CloseableHttpClient httpClient = HttpClients.createDefault();
					HttpPost request = new HttpPost(SysStatic.FIRSTSERVER+"/fir_platform/create_sip_num");
					List<BasicNameValuePair> formParams = new ArrayList<BasicNameValuePair>();
					formParams.add(new BasicNameValuePair("local_sip", worker.getId()));
					formParams.add(new BasicNameValuePair("sip_type", "3"));
					formParams.add(new BasicNameValuePair("neibName", neibName));
					UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formParams, "UTF-8");
					request.setEntity(uefEntity);
					CloseableHttpResponse response = httpClient.execute(request);
					if(response.getStatusLine().getStatusCode()==200){
						HttpEntity entity_rsp = response.getEntity();
						ResultDTO resultDto = JsonUtils.fromJson(EntityUtils.toString(entity_rsp), ResultDTO.class);
						if(resultDto!=null){
//							if(!"0".equals(resultDto.getCode())){
//								throw new BizException(resultDto.getMsg());
							 if("7".equals(resultDto.getCode())){
								 System.out.println("已经存在："+worker.getPhone());
								continue;
							}else{
								Map<String,Object> map = (Map<String, Object>) resultDto.getResult();
								if(map!=null&&!map.isEmpty()){
									Map<String,Object> user_sipMap = (Map<String, Object>) map.get("user_sip");
								    workerDao.updateSQL(addSip, new Object[]{user_sipMap.get("user_sip"),user_sipMap.get("userPassword"),
								    		user_sipMap.get("sipType"),user_sipMap.get("fs_ip"),user_sipMap.get("fs_post"),user_sipMap.get("linkId")});//住户sip 6
								}
							}
						}else{
							throw new BizException("获取房间分组号出错！");
						}
				}
			}
				workerDao.getCurrSession().flush();
		}
       }
	}
	

	public void InitRoomSipGroupDate(ApplicationContext app) throws ClientProtocolException, IOException, BizException, ParseException, JsonException{
		BaseDaoBySql<Room> roomSqlDao = (BaseDaoBySql<Room>) app.getBean("roomSqlDao");
		//查询分组为空的房间
		String find = "select id from t_room where fsip_group is null";
		//更新分组
		String updateSipGroup = "update Room set sipGroup=? where id=? ";
		List<String> roomIdList = roomSqlDao.pageFindBySql(find, new Object[]{});
		if(roomIdList!=null&&!roomIdList.isEmpty()){
			logger.info("房间数："+roomIdList.size());
			//查询社区名称
			StringBuilder sb = new StringBuilder();
			sb.append("WITH RECURSIVE r AS (SELECT d.* from t_domain d  where d.fentityid=? ")
			.append(" union ALL SELECT t_domain.* FROM t_domain, r WHERE t_domain.id = r.fparentid )")
			.append(" SELECT r.fremark from r where r.flayer='1'");
			for(String roomId:roomIdList){
				List<String> list = roomSqlDao.pageFindBySql(sb.toString(), new Object[]{roomId});
				if(list!=null&&!list.isEmpty()){
					String neibName = list.get(0).trim();
					List<String> nei = new ArrayList<String>();
					nei.add("赛翼");
					nei.add("未来城一期");
					if(!nei.contains(neibName)){
						continue;
					}
					logger.info("社区名称："+neibName);
					//同步数据以及平台
					CloseableHttpClient httpClient = HttpClients.createDefault();
					HttpPost request = new HttpPost(SysStatic.FIRSTSERVER+"/fir_platform/create_sip_num");
					List<BasicNameValuePair> formParams = new ArrayList<BasicNameValuePair>();
					formParams.add(new BasicNameValuePair("local_sip", roomId));
					formParams.add(new BasicNameValuePair("sip_type", "7"));
					formParams.add(new BasicNameValuePair("neibName", neibName));
					UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formParams, "UTF-8");
					request.setEntity(uefEntity);
					CloseableHttpResponse response = httpClient.execute(request);
					if(response.getStatusLine().getStatusCode()==200){
						HttpEntity entity_rsp = response.getEntity();
						ResultDTO resultDto = JsonUtils.fromJson(EntityUtils.toString(entity_rsp), ResultDTO.class);
						if(resultDto!=null){
							if(!"0".equals(resultDto.getCode())){
								throw new BizException(resultDto.getMsg());
							}else{
								Map<String,Object> map = (Map<String, Object>) resultDto.getResult();
								if(map!=null&&!map.isEmpty()){
									Map<String,Object> user_sipMap = (Map<String, Object>) map.get("user_sip");
									
									logger.info("房间sip分组："+user_sipMap.get("user_sip"));
									roomSqlDao.update(updateSipGroup, new Object[]{(Integer) user_sipMap.get("user_sip"),roomId});
//									room.setSipGroup((Integer) user_sipMap.get("user_sip"));
								}
							}
						}else{
							throw new BizException("获取房间分组号出错！");
						}
				}
			}
				roomSqlDao.getCurrSession().flush();
		}
       }
	}
	
	
}
