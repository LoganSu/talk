package com.youlb.biz.management.impl;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youlb.biz.management.IWorkerBiz;
import com.youlb.dao.common.BaseDaoBySql;
import com.youlb.entity.common.ResultDTO;
import com.youlb.entity.management.Worker;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.common.JsonUtils;
import com.youlb.utils.common.Synchronization_sip;
import com.youlb.utils.common.SysStatic;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.exception.JsonException;
import com.youlb.utils.helper.OrderHelperUtils;
import com.youlb.utils.helper.SearchHelper;
@Service("workerBiz")
public class WorkerBizImpl implements IWorkerBiz {
	@Autowired
	private BaseDaoBySql<Worker> workerDao;
	public void setWorkerDao(BaseDaoBySql<Worker> workerDao) {
		this.workerDao = workerDao;
	}

	@Override
	public String save(Worker target) throws BizException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Worker target) throws BizException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Serializable id) throws BizException {
		workerDao.delete(id);

	}

	@Override
	public void delete(String[] ids) throws BizException {
		if(ids!=null){
			for(String id:ids){
				delete(id);
			}
		}

	}

	@Override
	public Worker get(Serializable id) throws BizException {
		String sql ="SELECT t.id tid,t.fworkername,t.fphone,t.fstatus,t.fsex,d.id did,d.fdepartmentname from t_worker t inner join t_department d on d.id=t.fdepartmentid where t.id=?";
		List<Object[]> listObj = workerDao.pageFindBySql(sql, new Object[]{id});
		Worker worker = new Worker();
		if(listObj!=null&&!listObj.isEmpty()){
			Object[] obj = listObj.get(0);
			worker.setId(obj[0]==null?"":(String)obj[0]);
			worker.setWorkerName(obj[1]==null?"":(String)obj[1]);
			worker.setPhone(obj[2]==null?"":(String)obj[2]);
			worker.setStatus(obj[3]==null?"":(String)obj[3]);
			worker.setSex(obj[4]==null?"":(String)obj[4]);
			worker.setDepartmentId(obj[5]==null?"":(String)obj[5]);
			worker.setDepartmentName(obj[6]==null?"":(String)obj[6]);
		}
		return worker;
	}

	@Override
	public List<Worker> showList(Worker target, Operator loginUser)throws BizException {
		 StringBuilder sb = new StringBuilder();
		 List<Object> values = new ArrayList<Object>();
		 sb.append("SELECT t.id,t.workerName,t.phone,t.status,t.username,t.createTime,t.departmentName,t.sex,t.departmentId from (")
		 .append("SELECT w.id id,w.fworkername workerName,w.fphone phone,w.fstatus status,w.fusername username,w.fsex sex,w.fcreatetime createTime,d.fdepartmentname departmentName,d.id departmentId,( ")
		 .append("WITH RECURSIVE r AS (SELECT * FROM t_department WHERE id = d.id union ALL ")
		 .append("SELECT t_department.* FROM t_department, r WHERE t_department.id = r.fparentid) ")
		 .append("SELECT r.id FROM r where r.fparentid is null) topdepartid ")
		 .append("from t_worker w INNER JOIN t_department d on d.id=w.fdepartmentid ")
		 .append("group by w.id,w.fworkername,w.fphone,w.fstatus,w.fusername,w.fcreatetime,d.fdepartmentname,d.id,w.fsex)t ")
		 .append(" INNER JOIN t_department_domain todd on todd.fdepartmentid=t.topdepartid where 1=1 ");
		//普通用户过滤s域
		List<String> domainIds = loginUser.getDomainIds();
		if(!SysStatic.SPECIALADMIN.equals(loginUser.getIsAdmin())&&domainIds!=null&&!domainIds.isEmpty()){
			sb.append(SearchHelper.jointInSqlOrHql(domainIds, " todd.fdomainid "));
			values.add(domainIds);
		}
		if(StringUtils.isNotBlank(target.getWorkerName())){
			sb.append(" and t.workerName like ?");
			values.add("%"+target.getWorkerName()+"%");
		}
		if(StringUtils.isNotBlank(target.getPhone())){
			sb.append(" and t.phone like ?");
			values.add("%"+target.getPhone()+"%");
		}
		if(StringUtils.isNotBlank(target.getStatus())){
			sb.append(" and t.status = ?");
			values.add(target.getStatus());
		}
		if(StringUtils.isNotBlank(target.getDepartmentId())){
			sb.append(" and t.departmentId in (WITH RECURSIVE r AS (SELECT * FROM t_department WHERE id = ? union ALL")
			.append(" SELECT t_department.* FROM t_department, r WHERE t_department.fparentid = r.id) SELECT r.id FROM r) ");
			values.add(target.getDepartmentId());
		}
		 sb.append(" GROUP BY t.id,t.workerName,t.phone,t.status,t.username,t.createTime,t.departmentName,t.sex,t.departmentId,t.topdepartid");
		 OrderHelperUtils.getOrder(sb, target, "t.", "t.createTime");
		 List<Object[]> listObj = workerDao.pageFindBySql(sb.toString(), values.toArray(),target.getPager());
		 List<Worker> list = new ArrayList<Worker>();
		 if(listObj!=null&&!listObj.isEmpty()){
			 for(Object[] obj:listObj){
				 Worker worker = new Worker();
				 worker.setPager(target.getPager());
				 worker.setId(obj[0]==null?"":(String)obj[0]);
				 worker.setWorkerName(obj[1]==null?"":(String)obj[1]);
				 worker.setPhone(obj[2]==null?"":(String)obj[2]);
				 worker.setStatus(obj[3]==null?"":(String)obj[3]);
				 worker.setUsername(obj[4]==null?"":(String)obj[4]);
				 worker.setCreateTime(obj[5]==null?null:(Date)obj[5]);
				 worker.setDepartmentName(obj[6]==null?"":(String)obj[6]);
				 worker.setSex(obj[7]==null?"":(String)obj[7]);
				 list.add(worker);
			 }
		 }
		return list;
	}

	@Override
	public void saveOrUpdate(Worker worker, Operator loginUser) throws BizException, ClientProtocolException, UnsupportedEncodingException, IOException, JsonException {
		if(StringUtils.isBlank(worker.getId())){
			String id = (String) workerDao.add(worker);
			//添加sip账号
			SQLQuery query = workerDao.getCurrSession().createSQLQuery("SELECT '2'||substring('00000000'||nextval('tbl_sipcount_seq'),length(currval('tbl_sipcount_seq')||'')) ");
		    List<String> list =  query.list();
		    String addSip ="insert into users (user_sip,user_password,local_sip,sip_type,fs_ip,fs_port) values(?,?,?,?,?,?)";
		    //使用uuid为sip密码
		    String password = UUID.randomUUID().toString().replace("-", "");
//		    workerDao.executeSql(addSip, new Object[]{Integer.parseInt(list.get(0)),password,id,"3"});//物业sip账号类型为3 
		    CloseableHttpClient httpClient = HttpClients.createDefault();
		    
		    StringBuilder sb= new StringBuilder();
		    sb.append("WITH RECURSIVE r AS (SELECT d.* from t_department d  where d.id=? ")
		    .append(" union ALL SELECT t_department.* FROM t_department, r WHERE t_department.id = r.fparentid )")
		    .append(" SELECT td.fremark from r INNER JOIN t_department_domain tdd  on tdd.fdepartmentid=r.id INNER JOIN t_domain td on td.id=tdd.fdomainid  where r.flayer='0'");
		    List<String> neiborList = workerDao.pageFindBySql(sb.toString(), new Object[]{worker.getDepartmentId()});
		    if(neiborList!=null&&!neiborList.isEmpty()){
		    	//同步数据以及平台
		    	HttpGet get = new HttpGet(SysStatic.FIRSTSERVER+"/users/get_fs_by_nei_name.json?neib_name="+neiborList.get(0));
		    	CloseableHttpResponse execute = httpClient.execute(get);
		    	if(execute.getStatusLine().getStatusCode()==200){
		    		HttpEntity entity_rsp = execute.getEntity();
		    		ResultDTO resultDto = JsonUtils.fromJson(EntityUtils.toString(entity_rsp), ResultDTO.class);
		    		if(resultDto!=null){
		    			if("0".equals(resultDto.getCode())){
		    				Map<String,Object> result = (Map<String, Object>) resultDto.getResult();
		    				if(result!=null&&!result.isEmpty()){
		    					String fs_ip = (String) result.get("fs_ip");
		    					Integer fs_port = (Integer) result.get("fs_port");
		    					workerDao.executeSql(addSip, new Object[]{Integer.parseInt(list.get(0)),password,id,"3",fs_ip,fs_port});//3 物业
		    					//同步sip账号
		    					Synchronization_sip.synchronization_sip(id, list.get(0), password, "3", fs_ip,fs_port);
		    				}else{
								throw new BizException("请联系管理员先在一级平台添加社区ip信息再操作");
							}
		    			}else{
							throw new BizException("接口返回："+resultDto.getMsg());
					  }
		    		}
		    	}
		    	
		    }
		    
		    
		    
		}else{
			String update ="update Worker set workerName=?,departmentId=?,phone=?,sex=?,status=? where id=?";
			workerDao.update(update, new Object[]{worker.getWorkerName(),worker.getDepartmentId(),worker.getPhone(),worker.getSex(),worker.getStatus(),worker.getId()});
		}
		
	}
    /**
     * 通过部门获取员工列表
     * @param departmentId
     * @return
     * @throws BizException 
     * @see com.youlb.biz.management.IWorkerBiz#getWorkerList(java.lang.String)
     */
	@Override
	public List<Worker> getWorkerList(String departmentId) throws BizException {
		 String hql = "from Worker where departmentId=? order by workerName";
		return workerDao.find(hql, new Object[]{departmentId});
	}
    /**
     * 检查手机号码是否被注册
     * @param phone
     * @return
     * @throws BizException 
     * @see com.youlb.biz.management.IWorkerBiz#checkPhoneExist(java.lang.String)
     */
	@Override
	public boolean checkPhoneExist(String phone,String id) throws BizException {
		String hql = "select phone from Worker where phone=? ";
		List<Object> values = new ArrayList<Object>();
	    values.add(phone);
		if(StringUtils.isNotBlank(id)){
			hql+= " and id !=?";
			values.add(id);
		}
		List<String> find = workerDao.find(hql, values.toArray());
		if(find!=null&&!find.isEmpty()){
			return true;
		}
		return false;
	}

}
