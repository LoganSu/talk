package com.youlb.biz.countManage.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youlb.biz.countManage.ISipCountBiz;
import com.youlb.dao.common.BaseDaoBySql;
import com.youlb.entity.countManage.SipCount;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.helper.OrderHelperUtils;

@Service("sipCountBiz")
public class SipCountBizImpl implements ISipCountBiz {
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
		 sb.append("SELECT * from (SELECT sr.sip_user sipUser,sr.sip_host sip_host,sr.status status,sr.expires expires,")
		 .append(" sr.user_agent user_agent,sr.network_ip network_ip,sr.network_port network_port,u.sip_type sip_type,d.id domainId,tu.fusername username")
		 .append(" from sip_registrations sr INNER JOIN users u on u.user_sip=to_number(sr.sip_user, '9999999999999999999999999')")
		 .append(" left JOIN t_room r on r.fsipnum =u.local_sip left JOIN t_domain d on d.fentityid=r.id left JOIN t_domain_dweller tdd on tdd.fdomainid=d.id ")
		 .append(" left JOIN t_dweller dw on dw.id=tdd.fdwellerid LEFT JOIN t_users tu on dw.fphone=tu.fmobile_phone where u.sip_type ='1'")
		 .append(" UNION ")
		 .append(" SELECT sr.sip_user sipUser,sr.sip_host sip_host,sr.status status,sr.expires expires,")
		 .append(" sr.user_agent user_agent,sr.network_ip network_ip,sr.network_port network_port,u.sip_type sip_type,r.fdomainid domainId,r.fdevicecount username")
		 .append(" from sip_registrations sr INNER JOIN users u on u.user_sip=to_number(sr.sip_user, '9999999999999999999999999')")
		 .append(" left JOIN t_devicecount r on r.fsipnum=u.local_sip where u.sip_type in ('2','5') OR u.sip_type is null ")
		 .append(" UNION ")
		 .append(" SELECT sr.sip_user sip_user,sr.sip_host sip_host,sr.status status,sr.expires expires,")
		 .append(" sr.user_agent user_agent,sr.network_ip network_ip,sr.network_port network_port,u.sip_type sip_type,tdd.fdomainid domainid,w.fphone username ")
		 .append(" from sip_registrations sr INNER JOIN users u on u.user_sip=to_number(sr.sip_user, '9999999999999999999999999') ")
		 .append(" left JOIN t_worker w on w.id=u.local_sip INNER JOIN t_department_domain tdd on ")
		 .append(" (WITH RECURSIVE r AS (SELECT * FROM t_department WHERE id = w.fdepartmentid union ALL ")
		 .append(" SELECT t_department.* FROM t_department, r WHERE t_department.id = r.fparentid) SELECT r.id FROM r where r.fparentid is null ")
		 .append(" )=tdd.fdepartmentid  where u.sip_type ='3' ")
		 .append(" UNION ")
		 .append(" SELECT sr.sip_user sipUser,sr.sip_host sip_host,sr.status status,sr.expires expires,")
		 .append(" sr.user_agent user_agent,sr.network_ip network_ip,sr.network_port network_port,u.sip_type sip_type,d.id domainid,n.fneibname username")
		 .append(" from sip_registrations sr INNER JOIN users u on u.user_sip=to_number(sr.sip_user, '9999999999999999999999999')")
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
			 sb.append(" and t.domainId = ? ");
			 values.add(target.getDomainId());
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
				 sip.setExpires(obj[3]==null?0:((BigInteger)obj[3]).longValue());
				 sip.setUserAgent(obj[4]==null?"":(String)obj[4]);
				 sip.setNetworkIp(obj[5]==null?"":(String)obj[5]);
				 sip.setCountType(obj[7]==null?"":(String)obj[7]);
				 if(obj[8]!=null){
					 sip.setAddress(getAddressByDomainId((String)obj[8]));
				 }
				 sip.setUsername(obj[9]==null?"":(String)obj[9]);
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
		 sb.append("select * from ( SELECT to_char(u.user_sip,'999999999999999') sipUser,tu.fusername username,d.id domainId,u.sip_type sip_type")
		 .append(" from users u INNER JOIN t_room r on r.fsipnum=u.local_sip left JOIN t_domain d on d.fentityid=r.id left JOIN t_domain_dweller tdd on tdd.fdomainid=d.id ")
		 .append(" left JOIN t_dweller dw on dw.id=tdd.fdwellerid LEFT JOIN t_users tu on dw.fphone=tu.fmobile_phone  where u.sip_type ='1'")
		 .append(" UNION ")
		 .append(" SELECT to_char(u.user_sip,'999999999999999') sipUser,r.fdevicecount username,r.fdomainid domainId,u.sip_type sip_type ")
		 .append(" from users u INNER JOIN t_devicecount r on r.fsipnum=u.local_sip where u.sip_type in ('2','5') OR u.sip_type is null ")
		 .append(" UNION ")
		 .append(" SELECT to_char(u.user_sip,'999999999999999'),w.fphone username,tdd.fdomainid domainId,u.sip_type sip_type  ")
		 .append(" from users u INNER JOIN t_worker w on w.id=u.local_sip INNER JOIN t_department_domain tdd on  ")
		 .append(" (WITH RECURSIVE r AS (SELECT * FROM t_department WHERE id = w.fdepartmentid union ALL SELECT t_department.* FROM t_department, r WHERE t_department.id = r.fparentid) ")
		 .append(" SELECT r.id FROM r where r.fparentid is null )=tdd.fdepartmentid  where u.sip_type ='3' ")
		 .append(" UNION ")
		 .append(" SELECT to_char(u.user_sip,'999999999999999'),n.fneibname username, d.id domainId,u.sip_type sip_type ")
		 .append(" from users u INNER JOIN t_neighborhoods n on n.id=u.local_sip INNER JOIN t_domain d on d.fentityid=n.id  where u.sip_type ='4')t where 1=1 ");
		 if(StringUtils.isNotBlank(target.getSipUser())){
			 sb.append(" and t.sipUser like ? ");
			 values.add("%"+target.getSipUser()+"%");
		 }
		 if(StringUtils.isNotBlank(target.getUsername())){
			 sb.append(" and t.username like ? ");
			 values.add("%"+target.getUsername()+"%");
		 }
		 if(StringUtils.isNotBlank(target.getDomainId())){
			 sb.append(" and t.domainId = ? ");
			 values.add(target.getDomainId());
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
				 if(obj[2]!=null){
					 sip.setAddress(getAddressByDomainId((String)obj[2]));
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
		 sb.append(" SELECT * from ( SELECT to_char(u.user_sip,'999999999999999') sipUser,sr.sip_host sip_host,sr.status status,sr.expires expires,")
		 .append(" sr.user_agent user_agent,sr.network_ip network_ip,sr.network_port network_port,u.sip_type sip_type,r.fdomainid domainId,r.fdevicecount username")
		 .append(" from users u inner JOIN t_devicecount r on r.fsipnum=u.local_sip ")
		 .append(" left JOIN sip_registrations sr on u.user_sip=to_number(sr.sip_user, '9999999999999999999999999') where u.sip_type ='2' ) t where 1=1 ");
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
			 sb.append(" and t.domainId = ? ");
			 values.add(target.getDomainId());
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
					 sip.setExpires(((BigInteger)obj[3]).longValue());
				 }
				 sip.setUserAgent(obj[4]==null?"":(String)obj[4]);
				 sip.setNetworkIp(obj[5]==null?"":(String)obj[5]);
				 sip.setCountType(obj[7]==null?"":(String)obj[7]);
				 if(obj[8]!=null){
					 sip.setAddress(getAddressByDomainId((String)obj[8]));
				 }
				 sip.setUsername(obj[9]==null?"":(String)obj[9]);
				 list.add(sip);
			 }
		 }
		return list;
	}
}
