package com.youlb.biz.privilege.impl;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youlb.biz.privilege.IOperatorBiz;
import com.youlb.dao.common.BaseDaoBySql;
import com.youlb.entity.baseInfo.Carrier;
import com.youlb.entity.common.Pager;
import com.youlb.entity.common.ResultDTO;
import com.youlb.entity.privilege.Operator;
import com.youlb.entity.privilege.Role;
import com.youlb.utils.common.JsonUtils;
import com.youlb.utils.common.SHAEncrypt;
import com.youlb.utils.common.SysStatic;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.exception.JsonException;
import com.youlb.utils.helper.OrderHelperUtils;
import com.youlb.utils.helper.SearchHelper;

/** 
 * @ClassName: OperatorBizImpl.java 
 * @Description: web平台用户 
 * @author: Pengjy
 * @date: 2015年8月25日
 * 
 */
@Service("operatorBiz")
public class OperatorBizImpl implements IOperatorBiz {
	@Autowired
	private BaseDaoBySql<Operator> operatorSqlDao;
	@Autowired
	private BaseDaoBySql<Role> roleSqlDao;
	public void setOperatorSqlDao(BaseDaoBySql<Operator> operatorSqlDao) {
		this.operatorSqlDao = operatorSqlDao;
	}

	public void setRoleSqlDao(BaseDaoBySql<Role> roleSqlDao) {
		this.roleSqlDao = roleSqlDao;
	}

	/**
	 * @param target
	 * @return
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#save(java.io.Serializable)
	 */
	@Override
	public String save(Operator target) throws BizException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param target
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#update(java.io.Serializable)
	 */
	@Override
	public void update(Operator target) throws BizException {
		String hql ="update Operator t set t.password = ? where t.id=?";
		operatorSqlDao.update(hql, new Object[]{SHAEncrypt.digestPassword(target.getPassword()),target.getId()});
	}

	/**
	 * @param id
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Serializable id) throws BizException {
		operatorSqlDao.delete(id);

	}

	/**
	 * @param ids
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) throws BizException {
		if(ids!=null){
			 for(String id:ids){
				 delete(id);
			 }
		 }
	}

	/**
	 * @param id
	 * @return
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#get(java.io.Serializable)
	 */
	@Override
	public Operator get(Serializable id) throws BizException {
		Operator operator = operatorSqlDao.get(id);
		//获取角色集合
		String sql = "select m.froleid from t_operator o inner join t_operator_role m on m.foperatorid=o.id where o.id=?";
		List<String> roleIds = operatorSqlDao.pageFindBySql(sql,new Object[]{id});
		operator.setRoleIds(roleIds);
		return operator;
	}

	/**
	 * @param login
	 * @throws BizException 
	 * @see com.youlb.biz.privilege.IOperatorBiz#loginOut(com.youlb.entity.privilege.Operator)
	 */
	@Override
	public void loginOut(Operator login) throws BizException {
		if(login!=null){
			String update = "update Operator t set t.loginStatus=?,t.logOutTime=? where id=?";
//			login.setLoginStatus(0);//离线状态
//			login.setLogOutTime(new Date());
			operatorSqlDao.update(update, new Object[]{0,new Date(),login.getId()});
		}
	}

	/**
	 * @param user
	 * @return
	 * @throws BizException 
	 * @throws JsonException 
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws ClientProtocolException 
	 * @see com.youlb.biz.privilege.IOperatorBiz#login(com.youlb.entity.privilege.Operator)
	 */
	@Override
	public Operator login(Operator user,String code) throws BizException{
		if(user!=null&&StringUtils.isNotBlank(user.getLoginName())&&StringUtils.isNotBlank(user.getPassword())
				&&user.getCarrier()!=null&&StringUtils.isNotBlank(user.getCarrier().getCarrierNum())){
			
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT o.id,o.floginname,o.frealname,o.fphone,o.flogintime,o.flogouttime,o.floginstatus,o.fisadmin,o.fpassword")
			.append(" from t_operator o INNER JOIN t_operator_role tor on tor.foperatorid=o.id INNER JOIN t_role r on")
			.append(" r.id =tor.froleid INNER JOIN t_carrier c ON c.id=r.fcarrierid where o.floginname=? and c.fcarriernum=?");
//			String pasw = SHAEncrypt.digestPassword(user.getPassword());
			List<Object[]> operatorObj = operatorSqlDao.pageFindBySql(sb.toString(), new Object[]{user.getLoginName(),user.getCarrier().getCarrierNum()});
			List<Operator> list = objToOperator(operatorObj);
			if(!list.isEmpty()){
			   Operator loginUser = list.get(0);
			   //绑定运营商信息
			    sb = new StringBuilder();
			    sb.append("select c.id,c.fcarriername,c.fisnormal,c.fcarriernum")
			   .append(" from t_carrier c INNER JOIN t_role r on r.fcarrierid=c.id INNER JOIN t_operator_role tor")
			    .append(" on tor.froleid=r.id where tor.foperatorid=? GROUP BY c.id,c.fcarriername,c.fisnormal,c.fcarriernum");
			    Object[] obj =  (Object[]) operatorSqlDao.findObjectBySql(sb.toString(), new Object[]{loginUser.getId()});
			    Carrier carrier = new Carrier();
			    if(obj!=null){
			    	carrier.setId(obj[0]==null?null:(String)obj[0]);
			    	carrier.setCarrierName(obj[1]==null?null:(String)obj[1]);
			    	carrier.setIsNormal(obj[2]==null?null:(String)obj[2]);
			    	carrier.setCarrierNum(obj[3]==null?null:(String)obj[3]);
			    }
			    loginUser.setCarrier(carrier);
			    //判断密码是否正确
				 //加密匹配
				 String pasw = SHAEncrypt.digestPassword(carrier.getCarrierNum()+loginUser.getLoginName()+loginUser.getPassword()+code);
			     if(!pasw.equals(user.getPassword())){
			    	 return null;
			     }
			   
		    	//更改状态
		    	loginUser.setLoginStatus(1);
	    		loginUser.setLoginTime(new Date());//登入时间
	    		operatorSqlDao.update(loginUser);
	    		//设置操作权限
		    	 sb = new StringBuilder();
		    	sb.append("SELECT p.fkey from  t_role r INNER JOIN t_role_privilege rp ON r.id=rp.froleId INNER JOIN t_privilege p ")
		    	.append(" ON p.id=rp.fprivilegeId WHERE r.id IN (SELECT tor.froleid from t_operator_role tor where tor.foperatorId =?)")
		    	.append(" group by p.fkey");
		    	List<String> listObj = operatorSqlDao.pageFindBySql(sb.toString(), new Object[]{loginUser.getId()});
		    	loginUser.setPrivilegeList(listObj);
		         //设置用户角色
	    	    String rolesHql = "SELECT tor.froleId from t_operator o INNER JOIN t_operator_role tor ON tor.foperatorId=o.id where o.id=?";
	    	    List<String> roles = operatorSqlDao.pageFindBySql(rolesHql, new Object[]{loginUser.getId()});
			    loginUser.setRoleIds(roles);
			    //设置用户域
			     sb = new StringBuilder();
			     List<Object> values = new ArrayList<Object>();
			    //admin拥有全部域权限 isAdmin为空是普通用户
			    if(loginUser.getIsAdmin()!=null){
			    	sb.append("SELECT tcd.fdomainid from t_carrier c INNER JOIN t_role r ON r.fcarrierid=c.id")
			    	.append(" INNER JOIN t_carrier_domain tcd ON tcd.fcarrierid=c.id")
			    	.append(" INNER JOIN t_operator_role tor on r.id=tor.froleid where tor.foperatorid=?");
			    }else{
			    	sb.append("SELECT trd.fdomainid from t_operator_role tor INNER JOIN t_role r on r.id=tor.froleid")
			        .append(" INNER JOIN t_role_domain trd on trd.froleid=r.id where tor.foperatorid=?");
			    }
			    values.add(loginUser.getId());
			    List<String> domainIds = operatorSqlDao.pageFindBySql(sb.toString(),values.toArray());
			    //防止nullPointException
			    if(domainIds==null){
			    	domainIds = new ArrayList<String>();
			    }
			    loginUser.setDomainIds(domainIds);
			    return loginUser;
		    }else{
			    return null;
		    }
	    }else{
		    return null;
	    }
	}

	/**保存、更新
	 * @param user
	 * @param loginUser
	 * @throws BizException 
	 * @see com.youlb.biz.privilege.IOperatorBiz#saveOrUpdate(com.youlb.entity.privilege.Operator, com.youlb.entity.privilege.Operator)
	 */
	@Override
	public void saveOrUpdate(Operator user) throws BizException {
		String sql = "insert into T_OPERATOR_ROLE (FOPERATORID,FROLEID) values (?,?)";
		//add
		if(StringUtils.isBlank(user.getId())){
			user.setPassword(SHAEncrypt.digestPassword(user.getLoginName()));
			String operatorId = (String) operatorSqlDao.add(user);
			operatorSqlDao.getCurrSession().flush();
			List<String> roleIds = user.getRoleIds();
			if(roleIds!=null&&!roleIds.isEmpty()){
				//选择了角色插入中间表
				for(String roleId:roleIds){
					operatorSqlDao.executeSql(sql, new Object[]{operatorId,roleId});
				}
			}
		//update
		}else{
			//更新用户表
			 String hql = "update Operator set loginName= ?,realName=?,phone=? where id=?";
			 operatorSqlDao.update(hql, new Object[]{user.getLoginName(),user.getRealName(),user.getPhone(),user.getId()});
			 //如果有就先根据用户删掉中间表 
			 String dele = "delete from T_OPERATOR_ROLE where FOPERATORID=?";
			 operatorSqlDao.executeSql(dele, new Object[]{user.getId()});
			 //更新用户角色中间表
			 List<String> roleIds = user.getRoleIds();
				if(roleIds!=null&&!roleIds.isEmpty()){
					//插入新关系
					for(String roleId:roleIds){
						operatorSqlDao.executeSql(sql, new Object[]{user.getId(),roleId});
					}
				}
		}
	}

	/**
	 * @param target
	 * @param loginUser
	 * @return
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#showList(java.io.Serializable, com.youlb.entity.privilege.Operator)
	 */
	@Override
	public List<Operator> showList(Operator target, Operator loginUser)throws BizException {
		StringBuilder sb = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		sb.append("select * from (select o.id id,o.floginname loginName,o.frealname realName,o.fphone phone,o.flogintime loginTime,o.flogouttime logoutTime,")
		.append(" o.floginstatus loginStatus,o.fisadmin isAdmin,o.fpassword,o.fcreatetime createTime from t_operator o INNER JOIN t_operator_role tor ON o.id=tor.foperatorid ")
		.append(" INNER JOIN t_role r ON r.id=tor.froleid INNER JOIN t_carrier c ON c.id=r.fcarrierid")
		.append(" INNER JOIN t_carrier_domain  tcd on tcd.fcarrierid=c.id  where 1=1 ");//admin用户显示列表
		
		List<String> domainIds = loginUser.getDomainIds();
		if(domainIds!=null&&!domainIds.isEmpty()){
			sb.append(SearchHelper.jointInSqlOrHql(domainIds,"tcd.fdomainid"));
			values.add(domainIds);
		}
		//普通运营商只能看到自己所属运营商的用户 
//		if(SysStatic.NORMALCARRIER.equals(loginUser.getCarrier().getIsNormal())){ 
			sb.append(" and c.id =? and o.fisadmin is null"); 
			values.add(loginUser.getCarrier().getId());
//		}else{
//			
//		}
		//特殊管理员
//		if(SysStatic.SPECIALADMIN.equals(loginUser.getIsAdmin())){
//			sb.append(" or o.fisadmin=? ");
//			values.add(SysStatic.NORMALADMIN);
//		}
		//过滤用户名
		if(StringUtils.isNotBlank(target.getLoginName())){
			sb.append(" and o.floginname = ?");
			values.add(target.getLoginName());
		}
		//过滤用姓名
		if(StringUtils.isNotBlank(target.getRealName())){
			sb.append(" and o.frealname = ?");
			values.add(target.getRealName());
		}
		sb.append(" GROUP BY o.id,o.floginname,o.frealname,o.fphone,o.flogintime,o.flogouttime,o.floginstatus,o.fisadmin,o.fpassword,o.fcreatetime)t ");
		OrderHelperUtils.getOrder(sb, target, "t.", "t.createTime");
		List<Object[]> listObj = operatorSqlDao.pageFindBySql(sb.toString(), values.toArray(), target.getPager());
		List<Operator> list = objToOperator(listObj);
//		Pager pager = target.getPager();
//		pager.setTotalRows(listObj.size());
		if(listObj!=null&&!listObj.isEmpty()){
			list.get(0).setPager(target.getPager());
		}
		return list;
	}
	/**
	 * 数组集合转换成对象集合
	 * @param listObj
	 * @return
	 */
	private List<Operator> objToOperator(List<Object[]> listObj){
		List<Operator> list = new ArrayList<Operator>();
		if(listObj!=null&&!listObj.isEmpty()){
			for(Object[] obj:listObj){
				Operator operator = new Operator();
				operator.setId(obj[0]==null?"":(String)obj[0]);
				operator.setLoginName(obj[1]==null?"":(String)obj[1]);
				operator.setRealName(obj[2]==null?"":(String)obj[2]);
				operator.setPhone(obj[3]==null?"":(String)obj[3]);
				operator.setLoginTime(obj[4]==null?null:(Date)obj[4]);
				operator.setLogOutTime(obj[5]==null?null:(Date)obj[5]);
				operator.setLoginStatus(obj[6]==null?null:((Short)obj[6]).intValue());
				operator.setIsAdmin(obj[7]==null?null:((Short)obj[7]).intValue());
				operator.setPassword(obj[8]==null?null:(String)obj[8]);
				list.add(operator);
			}
			
		}
		return list;
	}

	/**获取角色列表
	 * @param loginUser
	 * @param user
	 * @return
	 * @throws BizException 
	 * @see com.youlb.biz.privilege.IOperatorBiz#getRoleList(com.youlb.entity.privilege.Operator, com.youlb.entity.privilege.Operator)
	 */
	@Override
	public List<Role> getRoleList(Operator loginUser, Operator user) throws BizException {
		 StringBuilder sb = new StringBuilder();
		 List<Object> values = new ArrayList<Object>();
		 List<Role> roleList = new ArrayList<Role>();
		 sb.append("select r.id id,r.frolename rolename from t_role r ")
		 .append("   INNER JOIN t_carrier_domain tcd on tcd.fcarrierid=r.fcarrierid  where 1=1");
		//普通运营商只能看到自己所属运营商
//		 if(SysStatic.NORMALCARRIER.equals(loginUser.getCarrier().getIsNormal())){
			 sb.append(" and r.fcarrierid =?");
			 values.add(loginUser.getCarrier().getId());
//		 }
			// 普通admin不显示自己的角色
//			 if(SysStatic.NORMALADMIN.equals(loginUser.getIsAdmin())){
				 sb.append(" and r.fisadmin is null");
//			 }
		 List<String> domainIds = loginUser.getDomainIds();
			if(domainIds!=null&&!domainIds.isEmpty()){
				sb.append(SearchHelper.jointInSqlOrHql(domainIds,"tcd.fdomainid"));
				values.add(domainIds);
			}
		 sb.append(" group by r.id order by r.fcreatetime");
		 List<Object[]> list = roleSqlDao.pageFindBySql(sb.toString(),values.toArray());
		//回显已有角色
		 if(list!=null&&!list.isEmpty()){
			 List<String> roleIds = user.getRoleIds();
			 for(Object[] obj:list){
				 Role role = new Role();
				 role.setId(obj[0]==null?null:(String)obj[0]);
				 role.setRoleName(obj[1]==null?null:(String)obj[1]);
				 if(roleIds!=null&&!roleIds.isEmpty()&&roleIds.contains(role.getId())){
					 role.setChecked(SysStatic.CHECKED);
				 }
				 roleList.add(role);
			 }
		 }
		return roleList;
	}

	/**显示运营商用户table数据
	 * @param user
	 * @param loginUser
	 * @return
	 * @throws BizException 
	 * @see com.youlb.biz.privilege.IOperatorBiz#carrierShowList(com.youlb.entity.privilege.Operator, com.youlb.entity.privilege.Operator)
	 */
	@Override
	public List<Operator> carrierShowList(Operator target, Operator loginUser) throws BizException {
		StringBuilder sb = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		List<Operator> list = new ArrayList<Operator>();
		sb.append("select * from (select o.id id,o.floginname loginname,o.frealname realname,o.fphone phone,o.flogintime logintime,o.flogouttime logouttime,")
		.append(" o.fcreatetime createtime from t_operator o INNER JOIN t_operator_role tor ON o.id=tor.foperatorid ")
		.append(" INNER JOIN t_role r ON r.id=tor.froleid INNER JOIN t_carrier c ON c.id=r.fcarrierid")
		.append(" INNER JOIN t_carrier_domain  tcd on tcd.fcarrierid=c.id  where o.fisadmin=? ");//admin用户显示列表
		values.add(SysStatic.NORMALADMIN);
		List<String> domainIds = loginUser.getDomainIds();
		if(domainIds!=null&&!domainIds.isEmpty()){
			sb.append(SearchHelper.jointInSqlOrHql(domainIds,"tcd.fdomainid"));
			values.add(domainIds);
		}
		//普通运营商只能看到自己所属运营商的用户 
		if(SysStatic.NORMALCARRIER.equals(loginUser.getCarrier().getIsNormal())){ 
			sb.append(" and c.id =?"); 
			values.add(loginUser.getCarrier().getId());
		}
//		else{
//			
//		}
		//特殊管理员
//		if(SysStatic.SPECIALADMIN.equals(loginUser.getIsAdmin())){
//			sb.append(" or o.fisadmin=? ");
//			values.add(SysStatic.NORMALADMIN);
//		}
		//过滤用户名
		if(StringUtils.isNotBlank(target.getLoginName())){
			sb.append(" and o.floginname = ?");
			values.add(target.getLoginName());
		}
		//过滤用姓名
		if(StringUtils.isNotBlank(target.getRealName())){
			sb.append(" and o.frealname like ?");
			values.add("%"+target.getRealName()+"%");
		}
		sb.append(" GROUP BY o.id,o.floginname,o.frealname,o.fphone,o.flogintime,o.flogouttime,o.fcreatetime)t ");
		OrderHelperUtils.getOrder(sb, target, "t.", "t.createtime");
		List<Object[]> listObj = operatorSqlDao.pageFindBySql(sb.toString(), values.toArray(), target.getPager());
		if(listObj!=null&&!listObj.isEmpty()){
			Pager pager = target.getPager();
			pager.setTotalRows(listObj.size());
			for(Object[] obj:listObj){
				Operator operator = new Operator();
				operator.setId(obj[0]==null?"":(String)obj[0]);
				operator.setLoginName(obj[1]==null?"":(String)obj[1]);
				operator.setRealName(obj[2]==null?"":(String)obj[2]);
				operator.setPhone(obj[3]==null?"":(String)obj[3]);
				operator.setLoginTime(obj[4]==null?null:(Date)obj[4]);
				operator.setLogOutTime(obj[5]==null?null:(Date)obj[5]);
				operator.setPager(pager);
				list.add(operator);
			}
			
		}
		return list;
	}

	/**检查用户名是否存在
	 * @param user
	 * @param loginUser
	 * @return
	 * @throws BizException 
	 * @see com.youlb.biz.privilege.IOperatorBiz#chickLoginNameExist(com.youlb.entity.privilege.Operator, com.youlb.entity.privilege.Operator)
	 */
	@Override
	public boolean chickLoginNameExist(Operator user, Operator loginUser) throws BizException {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT o.floginname from t_operator o INNER JOIN t_operator_role tor on tor.foperatorid=o.id INNER JOIN t_role r on")
		.append(" r.id =tor.froleid INNER JOIN t_carrier c ON c.id=r.fcarrierid where o.floginname=? and c.fcarriernum=?");
		List<String> list = operatorSqlDao.pageFindBySql(sb.toString(), new Object[]{user.getLoginName(),loginUser.getCarrier().getCarrierNum()});
		//存在返回true
		if(list!=null&&!list.isEmpty()){
			return true;
		}
		return false;
	}

	/**修改密码
	 * @param user
	 * @param loginUser
	 * @throws BizException 
	 * @see com.youlb.biz.privilege.IOperatorBiz#update(com.youlb.entity.privilege.Operator, com.youlb.entity.privilege.Operator)
	 */
	@Override
	public int update(Operator user, String id) throws BizException {
		String pasw = SHAEncrypt.digestPassword(user.getNewPassword());
		String update ="update Operator set password =? where id=?";
		int i =operatorSqlDao.update(update, new Object[]{pasw,id});
//		operatorSqlDao.getCurrSession().getTransaction().commit();
		return i;
	}

	/**
	 * @param user
	 * @param loginUser
	 * @return
	 * @throws BizException 
	 * @see com.youlb.biz.privilege.IOperatorBiz#getUser(com.youlb.entity.privilege.Operator, com.youlb.entity.privilege.Operator)
	 */
	@Override
	public String getUser(Operator user, Operator loginUser) throws BizException {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT o.id from t_operator o INNER JOIN t_operator_role tor on tor.foperatorid=o.id INNER JOIN t_role r on")
		.append(" r.id =tor.froleid INNER JOIN t_carrier c ON c.id=r.fcarrierid where o.floginname=? and c.fcarriernum=? and o.fpassword=?");
		String pasw = SHAEncrypt.digestPassword(user.getPassword());
		List<String> list = operatorSqlDao.pageFindBySql(sb.toString(), new Object[]{loginUser.getLoginName(),loginUser.getCarrier().getCarrierNum(),pasw});
		//存在返回true
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return "";
	}
    /**
     * 获取手机验证码
     * @param user
     * @throws IOException 
     * @throws ClientProtocolException 
     * @throws JsonException 
     * @throws ParseException 
     * @throws BizException 
     * @see com.youlb.biz.privilege.IOperatorBiz#getVerificationCode(com.youlb.entity.privilege.Operator)
     */
	@Override
	public String getVerificationCode(Operator user,String expireTime) throws ClientProtocolException, IOException, ParseException, JsonException, BizException {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT o.fphone from t_operator o INNER JOIN t_operator_role tor on tor.foperatorid=o.id INNER JOIN t_role r on")
		.append(" r.id =tor.froleid INNER JOIN t_carrier c ON c.id=r.fcarrierid where o.floginname=? and c.fcarriernum=?");
		List<String> list = operatorSqlDao.pageFindBySql(sb.toString(), new Object[]{user.getLoginName(),user.getCarrier().getCarrierNum()});
		if(list!=null&&!list.isEmpty()){
			String phone = list.get(0);
			Map<String,String> values = new HashMap<String,String>();
			values.put("phone", phone);
			values.put("expireTime", expireTime);
			return getVerificationCode(values);
		}
		return null;
	}
	
	/**
	 * 设置获取接口验证码
	 * @param values
	 * @return
	 * @throws ParseException
	 * @throws JsonException
	 * @throws IOException
	 * @throws BizException 
	 */
	private String getVerificationCode(Map<String,String> values) throws ParseException, JsonException, IOException, BizException{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost request = new HttpPost(SysStatic.HTTP+"/publish/getVerificationCode.json");
		List<BasicNameValuePair> formParams = new ArrayList<BasicNameValuePair>();
		formParams.add(new BasicNameValuePair("values", JsonUtils.toJson(values)));
		UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formParams, "UTF-8");
		request.setEntity(uefEntity);
		CloseableHttpResponse response = httpClient.execute(request);
		HttpEntity entity_rsp = response.getEntity();
		ResultDTO resultDto = JsonUtils.fromJson(EntityUtils.toString(entity_rsp), ResultDTO.class);
		if(resultDto!=null){
			if(!"0".equals(resultDto.getCode())){
				throw new BizException(resultDto.getMsg());
			}else{
				return (String) resultDto.getResult();
			}
		}
		return null;
	}

	@Override
	public boolean chickLoginNameExist(Operator user) throws BizException {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT o.floginname from t_operator o INNER JOIN t_operator_role tor on tor.foperatorid=o.id INNER JOIN t_role r on")
		.append(" r.id =tor.froleid INNER JOIN t_carrier c ON c.id=r.fcarrierid where o.floginname=? and c.fcarriernum=?");
		List<String> list = operatorSqlDao.pageFindBySql(sb.toString(), new Object[]{user.getLoginName(),user.getCarrier().getCarrierNum()});
		//存在返回true
		if(list!=null&&!list.isEmpty()){
			return true;
		}
		return false;
	}
   
}
