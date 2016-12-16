package com.youlb.job;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.oltu.oauth2.common.utils.JSONUtils;
import org.json.JSONArray;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.youlb.dao.common.BaseDaoBySql;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.common.SHAEncrypt;
import com.youlb.utils.exception.BizException;

public class AishequUsernameJob implements Job{

	@Override
	public void execute(JobExecutionContext context)throws JobExecutionException {
		
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		BaseDaoBySql<Operator> operatorSqlDao = (BaseDaoBySql<Operator>) webApplicationContext.getBean("operatorSqlDao");
		try {
			 //同步用户账号 用户名以asq_开头
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpGet get = new HttpGet("http://121.40.23.157:8383/ba_wisq/ctuser.nx?action=getUserList");
			CloseableHttpResponse execute = httpClient.execute(get);
			if(execute.getStatusLine().getStatusCode()==200){
				HttpEntity entity_rsp = execute.getEntity();
//				System.out.println(entity_rsp);
				String json = EntityUtils.toString(entity_rsp);
				Map<String, Object> parseJSON = JSONUtils.parseJSON(json);
				Object[] list = (Object[]) parseJSON.get("itemList");
				if(list!=null&&list.length>0){
					String insert="insert into t_operator (id,floginname,fpassword) values(?,?,?)";
					String role = "insert into t_operator_role (foperatorid,froleid) values(?,?)";
					String defultRole = "SELECT r.id from t_role r INNER JOIN t_carrier c on c.id=r.fcarrierid"+
                         " where c.fcarriernum='asq' and r.id not in(SELECT sr.froleid from t_role_domain sr) and r.id not in(SELECT rp.froleid from t_role_privilege rp)";
					String delet = "delete from t_operator where floginname=?";
					List<String> roleIdList = operatorSqlDao.pageFindBySql(defultRole,new Object[]{});
					if(roleIdList==null||roleIdList.isEmpty()){
						throw new BizException("请先创建一个默认角色，即所有权限为空");
					}
					String roleId=roleIdList.get(0);
					for(Object obj:list){
						JSONArray arr=(JSONArray) obj;
						String oId=UUID.randomUUID().toString().replace("-", "");
						//先删除记录
						operatorSqlDao.executeSql(delet, new Object[]{arr.getString(0)});
						operatorSqlDao.getCurrSession().flush();
						//插入记录
						operatorSqlDao.executeSql(insert, new Object[]{oId,arr.getString(0),SHAEncrypt.digestPassword(arr.getString(0))});
						operatorSqlDao.getCurrSession().flush();
						//门禁管理员
//						if("0".equals((String)obj[1])){
							operatorSqlDao.executeSql(role, new Object[]{oId,roleId});
						//物业管理员 
//						}else if("1".equals((String)obj[1])){
//							operatorSqlDao.executeSql(role, new Object[]{oId,});
//						}
					}
				}
//				System.out.println(list);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
