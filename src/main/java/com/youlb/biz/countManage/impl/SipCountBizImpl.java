package com.youlb.biz.countManage.impl;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youlb.biz.access.impl.PermissionBizImpl;
import com.youlb.biz.countManage.ISipCountBiz;
import com.youlb.dao.common.BaseDaoBySql;
import com.youlb.entity.common.ResultDTO;
import com.youlb.entity.countManage.SipCount;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.common.JsonUtils;
import com.youlb.utils.common.SysStatic;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.exception.JsonException;
import com.youlb.utils.helper.OrderHelperUtils;

@Service("sipCountBiz")
public class SipCountBizImpl implements ISipCountBiz {
	/** 日志输出 */
	private static Logger logger = LoggerFactory.getLogger(SipCountBizImpl.class);
	@Autowired
	private BaseDaoBySql<SipCount> sipCountlDao;
	public void setSipCountlDao(BaseDaoBySql<SipCount> sipCountlDao) {
		this.sipCountlDao = sipCountlDao;
	}

	@Override
	public String save(SipCount target) throws BizException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(SipCount target) throws BizException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Serializable id) throws BizException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(String[] ids) throws BizException {
		// TODO Auto-generated method stub

	}

	@Override
	public SipCount get(Serializable id) throws BizException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SipCount> showList(SipCount target, Operator loginUser)throws BizException {
		 StringBuilder sb = new StringBuilder();
		 List<Object> values = new ArrayList<Object>();
		 sb.append("SELECT * from (SELECT sr.reg_user sipUser,sr.realm sip_host,sr.network_proto status,sr.expires expires,")
//		 .append(" sr.user_agent user_agent,sr.network_ip network_ip,sr.network_port network_port,u.sip_type sip_type,ddw.fdomainid domainId,r.fusername username")
		 .append(" sr.network_ip network_ip,sr.network_port network_port,u.sip_type sip_type,ddw.fdomainid domainId,r.fusername username")
		 .append(" from registrations sr INNER JOIN users u on u.user_sip=to_number(sr.reg_user, '9999999999999999999999999')")
		 .append(" left JOIN t_users r on r.id = to_number(u.local_sip, '9999999999999999') LEFT JOIN t_dweller dw on r.fmobile_phone=dw.fphone ")
		 .append(" LEFT JOIN t_domain_dweller ddw on dw.id=ddw.fdwellerid WHERE u.sip_type = '6'")
		 .append(" UNION ")
		 .append(" SELECT sr.reg_user sipUser,sr.realm sip_host,sr.network_proto status,sr.expires expires,")
		 .append(" sr.network_ip network_ip,sr.network_port network_port,u.sip_type sip_type,case when r.fdevice_count_desc is null or r.fdevice_count_desc='' then r.fdomainid else r.fdomainid ||'-'||r.fdevice_count_desc end domainId,r.fdevicecount username")
		 .append(" from registrations sr INNER JOIN users u on u.user_sip=to_number(sr.reg_user, '9999999999999999999999999')")
		 .append(" left JOIN t_devicecount r on r.fsipnum=u.local_sip where u.sip_type in ('2','5') OR u.sip_type is null ")
		 .append(" UNION ")
		 .append(" SELECT sr.reg_user sipUser,sr.realm sip_host,sr.network_proto status,sr.expires expires,")
		 .append(" sr.network_ip network_ip,sr.network_port network_port,u.sip_type sip_type,tdd.fdomainid domainid,w.fphone username ")
		 .append(" from registrations sr INNER JOIN users u on u.user_sip=to_number(sr.reg_user, '9999999999999999999999999') ")
		 .append(" left JOIN t_worker w on w.id=u.local_sip INNER JOIN t_department_domain tdd on ")
		 .append(" (WITH RECURSIVE r AS (SELECT * FROM t_department WHERE id = w.fdepartmentid union ALL ")
		 .append(" SELECT t_department.* FROM t_department, r WHERE t_department.id = r.fparentid) SELECT r.id FROM r where r.fparentid is null ")
		 .append(" )=tdd.fdepartmentid  where u.sip_type ='3' ")
		 .append(" UNION ")
		 .append(" SELECT sr.reg_user sipUser,sr.realm sip_host,sr.network_proto status,sr.expires expires,")
		 .append(" sr.network_ip network_ip,sr.network_port network_port,u.sip_type sip_type,d.id domainid,n.fneibname username")
		 .append(" from registrations sr INNER JOIN users u on u.user_sip=to_number(sr.reg_user, '9999999999999999999999999')")
		 .append(" left JOIN t_neighborhoods n on n.id=u.local_sip INNER JOIN t_domain d on d.fentityid=n.id  where u.sip_type ='4' ) t where 1=1");
		 if(StringUtils.isNotBlank(target.getSipUser())){
			 sb.append(" and t.sipUser like ? ");
			 values.add("%"+target.getSipUser()+"%");
		 }
		 if(StringUtils.isNotBlank(target.getUsername())){
			 sb.append(" and t.username like ? ");
			 values.add("%"+target.getUsername()+"%");
		 }
		 if(StringUtils.isNotBlank(target.getDomainId())){
			 sb.append(" and t.domainId like ? ");
			 values.add(target.getDomainId()+"%");
		 }
		 OrderHelperUtils.getOrder(sb, target, "t.", "t.expires");
		 
		 List<Object[]> listObj = sipCountlDao.pageFindBySql(sb.toString(), values.toArray(), target.getPager());
		 List<SipCount> list = new ArrayList<SipCount>();
		 if(listObj!=null&&!listObj.isEmpty()){
			 for(Object[] obj:listObj){
				 SipCount sip = new SipCount();
				 sip.setPager(target.getPager());
				 sip.setSipUser(obj[0]==null?"":(String)obj[0]);
				 sip.setServerHost(obj[1]==null?"":(String)obj[1]);
				 sip.setStatus(obj[2]==null?"":(String)obj[2]);
				 sip.setExpires(obj[3]==null?0:((Integer)obj[3]).longValue());
//				 sip.setUserAgent(obj[4]==null?"":(String)obj[4]);
				 sip.setNetworkIp(obj[4]==null?"":(String)obj[4]);
				 sip.setCountType(obj[6]==null?"":(String)obj[6]);
				 String domainId = (String)obj[7];
				 if(domainId!=null){
					 //设备账号需要显示别名
					 if(domainId.contains("-")){
						 sip.setAddress(getAddressByDomainId(domainId.substring(0,domainId.indexOf("-")))+"-"+domainId.substring(domainId.indexOf("-")+1));
					 }else{
						 sip.setAddress(getAddressByDomainId(domainId));
					 }
				 }
				 sip.setUsername(obj[8]==null?"":(String)obj[8]);
				 list.add(sip);
			 }
		 }
		return list;
	}
	/**获取地址
	 * @throws BizException */
	@Override
	public String getAddressByDomainId(String domainId) throws BizException {
		StringBuffer sb = new StringBuffer();
		sb.append("select array_to_string (ARRAY(WITH RECURSIVE r AS (SELECT * FROM t_domain WHERE id = ?")
		.append(" union ALL SELECT t_domain.* FROM t_domain, r WHERE t_domain.id = r.fparentid) SELECT  fremark FROM r where flayer is not null ORDER BY flayer),'')");
		 List<String> listObj = sipCountlDao.pageFindBySql(sb.toString(), new Object[]{domainId});
		 if(listObj!=null&&!listObj.isEmpty()){
			 return listObj.get(0);
		 }
		return null;
	}
    
	@Override
	public List<SipCount> showAllList(SipCount target, Operator loginUser) throws BizException {
		 StringBuilder sb = new StringBuilder();
		 List<Object> values = new ArrayList<Object>();
		 sb.append("select * from (SELECT to_char(u.user_sip,'999999999999999') sipUser,r.fusername username,ddw.fdomainid domainId,u.sip_type sip_type")
		 .append(" FROM users u INNER JOIN t_users r ON r.id = to_number(u.local_sip, '9999999999999999') LEFT JOIN t_dweller dw on r.fmobile_phone=dw.fphone ")
		 .append(" LEFT JOIN t_domain_dweller ddw on dw.id=ddw.fdwellerid WHERE u.sip_type = '6' GROUP BY sipUser,username,domainId,sip_type")
		 .append(" UNION ")
		 .append(" SELECT to_char(u.user_sip,'999999999999999') sipUser,r.fdevicecount username,case when r.fdevice_count_desc is null or r.fdevice_count_desc='' then r.fdomainid else r.fdomainid ||'-'||r.fdevice_count_desc end domainId,u.sip_type sip_type ")
		 .append(" from users u INNER JOIN t_devicecount r on r.fsipnum=u.local_sip where u.sip_type in ('2','5') OR u.sip_type is null ")
		 .append(" UNION ")
		 .append(" SELECT to_char(u.user_sip,'999999999999999') sipUser,w.fphone username,tdd.fdomainid domainId,u.sip_type sip_type  ")
		 .append(" from users u INNER JOIN t_worker w on w.id=u.local_sip INNER JOIN t_department_domain tdd on  ")
		 .append(" (WITH RECURSIVE r AS (SELECT * FROM t_department WHERE id = w.fdepartmentid union ALL SELECT t_department.* FROM t_department, r WHERE t_department.id = r.fparentid) ")
		 .append(" SELECT r.id FROM r where r.fparentid is null )=tdd.fdepartmentid  where u.sip_type ='3'")
		 .append(" UNION ")
		 .append(" SELECT to_char(u.user_sip,'999999999999999') sipUser,n.fneibname username, d.id domainId,u.sip_type sip_type ")
		 .append(" from users u INNER JOIN t_neighborhoods n on n.id=u.local_sip INNER JOIN t_domain d on d.fentityid=n.id  where u.sip_type ='4')t where 1=1");
		 if(StringUtils.isNotBlank(target.getSipUser())){
			 sb.append(" and t.sipUser like ? ");
			 values.add("%"+target.getSipUser()+"%");
		 }
		 if(StringUtils.isNotBlank(target.getUsername())){
			 sb.append(" and t.username like ? ");
			 values.add("%"+target.getUsername()+"%");
		 }
		 if(StringUtils.isNotBlank(target.getDomainId())){
			 sb.append(" and t.domainId like ? ");
			 values.add(target.getDomainId()+"%");
		 }
		 OrderHelperUtils.getOrder(sb, target, "t.", "t.sipUser");
		 List<Object[]> listObj = sipCountlDao.pageFindBySql(sb.toString(), values.toArray(), target.getPager());
		 List<SipCount> list = new ArrayList<SipCount>();
		 if(listObj!=null&&!listObj.isEmpty()){
			 for(Object[] obj:listObj){
				 SipCount sip = new SipCount();
				 sip.setPager(target.getPager());
				 sip.setSipUser(obj[0]==null?"":(String)obj[0]);
				 sip.setUsername(obj[1]==null?"":(String)obj[1]);
				 String domainId = (String)obj[2];
				 if(domainId!=null){
					 //设备账号需要显示别名
					 if(domainId.contains("-")){
						 sip.setAddress(getAddressByDomainId(domainId.substring(0,domainId.indexOf("-")))+"-"+domainId.substring(domainId.indexOf("-")+1));
					 }else{
						 sip.setAddress(getAddressByDomainId(domainId));
					 }
				 }
				 sip.setCountType(obj[3]==null?"":(String)obj[3]);
				 list.add(sip);
			 }
		 }
		return list;
	}

	@Override
	public List<SipCount> deviceCountSipShowList(SipCount target,Operator loginUser) throws BizException {
		 StringBuilder sb = new StringBuilder();
		 List<Object> values = new ArrayList<Object>();
//		 sb.append("SELECT * from (SELECT sr.sip_user sipUser,sr.sip_host sip_host,sr.status status,sr.expires expires,")
//		 .append(" sr.user_agent user_agent,sr.network_ip network_ip,sr.network_port network_port,u.sip_type sip_type,d.id domainId,tu.fusername username")
//		 .append(" from sip_registrations sr INNER JOIN users u on u.user_sip=to_number(sr.sip_user, '9999999999999999999999999')")
//		 .append(" left JOIN t_room r on r.fsipnum =u.local_sip left JOIN t_domain d on d.fentityid=r.id left JOIN t_domain_dweller tdd on tdd.fdomainid=d.id ")
//		 .append(" left JOIN t_dweller dw on dw.id=tdd.fdwellerid LEFT JOIN t_users tu on dw.fphone=tu.fmobile_phone where u.sip_type ='1'")
//		 .append(" UNION ")
		 sb.append(" SELECT * from ( SELECT to_char(u.user_sip,'999999999999999') sipUser,sr.realm sip_host,sr.network_proto status,sr.expires expires,")
		 .append(" sr.network_ip network_ip,sr.network_port network_port,u.sip_type sip_type,case when r.fdevice_count_desc is null or r.fdevice_count_desc='' then r.fdomainid else r.fdomainid ||'-'||r.fdevice_count_desc end domainId,r.fdevicecount username")
		 .append(" from users u inner JOIN t_devicecount r on r.fsipnum=u.local_sip ")
		 .append(" left JOIN registrations sr on u.user_sip=to_number(sr.reg_user, '9999999999999999999999999') where u.sip_type ='2' ) t where 1=1 ");
//		 .append(" UNION ")
//		 .append(" SELECT sr.sip_user sip_user,sr.sip_host sip_host,sr.status status,sr.expires expires,")
//		 .append(" sr.user_agent user_agent,sr.network_ip network_ip,sr.network_port network_port,u.sip_type sip_type,tdd.fdomainid domainid,w.fphone fusername ")
//		 .append(" from sip_registrations sr INNER JOIN users u on u.user_sip=to_number(sr.sip_user, '9999999999999999999999999') ")
//		 .append(" left JOIN t_worker w on w.id=u.local_sip INNER JOIN t_department_domain tdd on ")
//		 .append(" (WITH RECURSIVE r AS (SELECT * FROM t_department WHERE id = w.fdepartmentid union ALL ")
//		 .append(" SELECT t_department.* FROM t_department, r WHERE t_department.id = r.fparentid) SELECT r.id FROM r where r.fparentid is null ")
//		 .append(" )=tdd.fdepartmentid  where u.sip_type ='3' ")
//		 .append(" UNION ")
//		 .append(" SELECT sr.sip_user sipUser,sr.sip_host sip_host,sr.status status,sr.expires expires,")
//		 .append(" sr.user_agent user_agent,sr.network_ip network_ip,sr.network_port network_port,u.sip_type sip_type,d.id domainid,n.fneibname fusername")
//		 .append(" from sip_registrations sr INNER JOIN users u on u.user_sip=to_number(sr.sip_user, '9999999999999999999999999')")
//		 .append(" left JOIN t_neighborhoods n on n.id=u.local_sip INNER JOIN t_domain d on d.fentityid=n.id  where u.sip_type ='4' ) t where 1=1");
		 if(StringUtils.isNotBlank(target.getSipUser())){
			 sb.append(" and t.sipUser like ? ");
			 values.add("%"+target.getSipUser()+"%");
		 }
		 if(StringUtils.isNotBlank(target.getUsername())){
			 sb.append(" and t.username like ? ");
			 values.add("%"+target.getUsername()+"%");
		 }
		 if(StringUtils.isNotBlank(target.getDomainId())){
			 sb.append(" and t.domainId like ? ");
			 values.add(target.getDomainId()+"%");
		 }
		 OrderHelperUtils.getOrder(sb, target, "t.", "t.expires");
		 
		 List<Object[]> listObj = sipCountlDao.pageFindBySql(sb.toString(), values.toArray(), target.getPager());
		 List<SipCount> list = new ArrayList<SipCount>();
		 if(listObj!=null&&!listObj.isEmpty()){
			 for(Object[] obj:listObj){
				 SipCount sip = new SipCount();
				 sip.setPager(target.getPager());
				 sip.setSipUser(obj[0]==null?"":(String)obj[0]);
				 sip.setServerHost(obj[1]==null?"":(String)obj[1]);
				 sip.setStatus(obj[2]==null?"":(String)obj[2]);
				 if(obj[3]!=null){
					 sip.setExpires(((Integer)obj[3]).longValue());
				 }
//				 sip.setUserAgent(obj[4]==null?"":(String)obj[4]);
				 sip.setNetworkIp(obj[4]==null?"":(String)obj[4]);
				 sip.setCountType(obj[6]==null?"":(String)obj[6]);
				 String domainId = (String)obj[7];
				 if(StringUtils.isNotBlank(domainId)){
					 //设备账号需要显示别名
					 if(domainId.contains("-")){
						 sip.setAddress(getAddressByDomainId(domainId.substring(0,domainId.indexOf("-")))+"-"+domainId.substring(domainId.indexOf("-")+1));
					 }else{
						 sip.setAddress(getAddressByDomainId(domainId));
					 }
				 }
				 sip.setUsername(obj[8]==null?"":(String)obj[8]);
				 list.add(sip);
			 }
		 }
		return list;
	}
    /**
     * 重启
     * @throws IOException 
     * @throws ClientProtocolException 
     * @throws JsonException 
     * @throws ParseException 
     */
	@Override
	public void restDevice(String username, String reset_time, String reset_type)
			throws BizException, ClientProtocolException, IOException, ParseException, JsonException {
		 HttpGet request = new HttpGet(SysStatic.HTTP+"/device/device_remote_reset.json?username="+username+"&reset_time="+reset_time+"&reset_type="+reset_type);
		 CloseableHttpClient httpClient = HttpClients.createDefault();
		 CloseableHttpResponse response = httpClient.execute(request);
			if(response.getStatusLine().getStatusCode()==200){
				HttpEntity entity_rsp = response.getEntity();
				ResultDTO resultDto = JsonUtils.fromJson(EntityUtils.toString(entity_rsp), ResultDTO.class);
				if(resultDto!=null){
					if(!"0".equals(resultDto.getCode())){
						logger.error(resultDto.getMsg());
						throw new BizException(resultDto.getMsg());
					}
				}else{
					logger.info("门口机重启推送成功！");
				}
			} 
		
	}
	
}
