package com.youlb.biz.privilege.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;
import com.youlb.biz.privilege.IRoleBiz;
import com.youlb.dao.common.BaseDaoBySql;
import com.youlb.entity.common.Pager;
import com.youlb.entity.privilege.Operator;
import com.youlb.entity.privilege.Privilege;
import com.youlb.entity.privilege.Role;
import com.youlb.utils.common.SysStatic;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.helper.OrderHelperUtils;
import com.youlb.utils.helper.SearchHelper;

/** 
 * @ClassName: RoleBizImpl.java 
 * @Description:角色业务实现类 
 * @author: Pengjy
 * @date: 2015年7月7日
 * 
 */
@Service
@Component("roleBiz")
public class RoleBizImpl implements IRoleBiz {
	@Autowired
	private BaseDaoBySql<Role> roleSqlDao;
	@Autowired
	private BaseDaoBySql<Privilege> privilegeSqlDao;

	public void setRoleSqlDao(BaseDaoBySql<Role> roleSqlDao) {
		this.roleSqlDao = roleSqlDao;
	}
	public void setPrivilegeSqlDao(BaseDaoBySql<Privilege> privilegeSqlDao) {
		this.privilegeSqlDao = privilegeSqlDao;
	}
	/**
	 * @param target
	 * @return
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#save(java.io.Serializable)
	 */
	@Override
	public String save(Role target) throws BizException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param target
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#update(java.io.Serializable)
	 */
	@Override
	public void update(Role target) throws BizException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param id
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Serializable id) throws BizException {
		roleSqlDao.delete(id);
		
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
	public Role get(Serializable id) throws BizException {
		Role role = roleSqlDao.get(id);
//		if(role.getRoleName().contains("_")){
//			role.setRoleName(role.getRoleName().substring(role.getRoleName().indexOf("_")+1));
//		}
		//获取权限集合
		String sql ="select m.fprivilegeid from t_role r INNER JOIN t_role_privilege m on m.FROLEID=r.id where r.id=?";
		List<String> privilegeIds = roleSqlDao.pageFindBySql(sql,new Object[]{id});
		role.setPrivilegeIds(privilegeIds);
		
		//获取域集合
		String role_domain ="select m.fdomainid from t_role r inner join t_role_domain m on m.froleid=r.id where r.id=?";
		List<String> domainIds = roleSqlDao.pageFindBySql(role_domain,new Object[]{id});
		role.setDomainIds(domainIds);

		return role;
	}


	/**
	 * @param role
	 * @throws BizException 
	 * @see com.youlb.biz.privilege.IRoleBiz#saveOrUpdate(com.youlb.entity.privilege.Role)
	 */
	@Override
	public void saveOrUpdate(Role role,Operator loginUser) throws BizException {
//		roleSqlDao.saveOrUpdate(role);
		String role_privilege ="insert into t_role_privilege (froleid,fprivilegeid) values(?,?)";
		String role_domain ="insert into t_role_domain (froleid,fdomainid) values(?,?)";
		List<String> domainIds = role.getDomainIds();
		//add
		if(StringUtils.isBlank(role.getId())){
			String roleId = (String) roleSqlDao.add(role);
			roleSqlDao.getCurrSession().flush();
			List<String> privilegeIds = role.getTreecheckbox();
			if(privilegeIds!=null&&!privilegeIds.isEmpty()){
				//插入角色权限中间表
				for(String privilegeId:privilegeIds){
					roleSqlDao.executeSql(role_privilege, new Object[]{roleId,privilegeId});
				}
			}
			
			if(domainIds!=null&&!domainIds.isEmpty()){
				//插入角色域中间表
				for(String domainId:domainIds){
					roleSqlDao.executeSql(role_domain, new Object[]{roleId,domainId});
				}
			}
		//update	
		}else{
			//更新用户表
			 String hql = "update Role set roleName= ?,description=? where id=?";
			 roleSqlDao.update(hql,new Object[]{role.getRoleName(),role.getDescription(),role.getId()});
			 //如果有就先根据用户删掉中间表 
			 String dele_role_privilege = "delete from t_role_privilege where froleid=?";
			 roleSqlDao.executeSql(dele_role_privilege, new Object[]{role.getId()});
			 //更新用户角色中间表
			 List<String> privilegeIds = role.getTreecheckbox();
				if(privilegeIds!=null&&!privilegeIds.isEmpty()){
					//插入新关系
					for(String privilegeId:privilegeIds){
						roleSqlDao.executeSql(role_privilege, new Object[]{role.getId(),privilegeId});
					}
				}
				
			 //如果有就先根据用户删掉中间表 
			 String dele_role_domain = "delete from t_role_domain where froleid=?";
			 roleSqlDao.executeSql(dele_role_domain, new Object[]{role.getId()});
			 //更新用户角色中间表
//			 List<String> domainIds = role.getDomainIds();
				if(domainIds!=null&&!domainIds.isEmpty()){
					///插入角色域中间表
					for(String domainId:domainIds){
						roleSqlDao.executeSql(role_domain, new Object[]{role.getId(),domainId});
					}
				}
				//角色权限有 费用管理 功能的用户都要同步数据到支付平台（cheked=1）
				if(new Integer(1).equals(role.getChecked())){
					String carrier = loginUser.getCarrier().getCarrierNum();
					//找出拥有此角色的所有用户做域数据同步
					StringBuilder sb = new StringBuilder();
					sb.append("select a.floginname, array_to_string (ARRAY(SELECT trd.fdomainid from t_role r INNER JOIN ")
					.append("t_operator_role top on top.froleid=r.id INNER JOIN t_role_domain trd on trd.froleid=r.id ")
					.append(" where top.foperatorid=a.oid ),',') from (SELECT o.floginname floginname,o.id oid from t_role r  ")
					.append(" INNER JOIN t_operator_role top on top.froleid=r.id INNER JOIN t_operator o on o.id=top.foperatorid")
					.append(" where r.id=? GROUP BY o.floginname,o.id) a");
					List<Object[]> listObj = roleSqlDao.pageFindBySql(sb.toString(), new Object[]{role.getId()});
//					List<Map<String,List<String>>> domainList= new ArrayList<Map<String,List<String>>>();
					Map<String,List<String>> map = new HashMap<String,List<String>>();
					if(listObj!=null&&!listObj.isEmpty()){
						for(Object[] obj:listObj){
							String domains =  obj[1]==null?"":(String)obj[1];
							if(obj[0]!=null&&StringUtils.isNotBlank(domains)){
								String[] domainArr= domains.split(",");
								map.put(carrier+"-"+(String) obj[0], Arrays.asList(domainArr));
							}
						}
					}
					System.out.println(map);
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
	public List<Role> showList(Role target, Operator loginUser)throws BizException {
		StringBuilder sb = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		List<Role> roleList = new ArrayList<Role>();
		sb.append("select * from (select r.id id,r.frolename rolename,r.fdescription description,r.fcarrierid carrierid,r.fcreatetime createTime")
		.append(" FROM t_role r INNER JOIN t_carrier_domain tcd on tcd.fcarrierid=r.fcarrierid  where 1=1 ");
		//普通运营商只能看到自己所属角色
//		 if(SysStatic.NORMALCARRIER.equals(loginUser.getCarrier().getIsNormal())){
			 sb.append(" and r.fcarrierid =? and r.fisadmin is null");
			 values.add(loginUser.getCarrier().getId());
		 //特殊运营商能看到其他运营商的admin角色 和自己所属运营商的角色
//		 }else{
//			 sb.append(" and (r.fisadmin =? or (r.fcarrierid =? and r.fisadmin is null))");
//			 values.add(SysStatic.NORMALADMIN);
//			 values.add(loginUser.getCarrier().getId());
//		 }
		 // 普通admin不显示自己的角色
//		 if(SysStatic.NORMALADMIN.equals(loginUser.getIsAdmin())){
//			 sb.append(" and r.fisadmin is null");
//		 }
		 
		List<String> domainIds = loginUser.getDomainIds();
		if(domainIds!=null&&!domainIds.isEmpty()){
			sb.append(SearchHelper.jointInSqlOrHql(domainIds,"tcd.fdomainid"));
			values.add(domainIds);
		}
		//对接平台用户数据过滤
//		 if(loginUser.getLoginName().contains("_")){
//			 sb.append(" and substr(r.frolename, 0 ,length('"+loginUser.getLoginName().substring(0, loginUser.getLoginName().indexOf("_"))+"')+1)=?");
//			 values.add(loginUser.getLoginName().substring(0, loginUser.getLoginName().indexOf("_")));
//		 }
		
		sb.append(" group by r.id,r.frolename,r.fdescription,r.fcarrierid,r.fcreatetime");
		sb.append(")t where 1=1 ");
		//过滤名称
	    if(StringUtils.isNotBlank(target.getRoleName())){
	    	sb.append(" and t.roleName=?");
	    	values.add(target.getRoleName());
	    }
		OrderHelperUtils.getOrder(sb, target, "t.", "t.createTime");
		List<Object[]> listObj = roleSqlDao.pageFindBySql(sb.toString(), values.toArray(), target.getPager());
		if(listObj!=null&&!listObj.isEmpty()){
			Pager pager = target.getPager();
			for(Object[] obj:listObj){
				Role role = new Role();
				role.setId(obj[0]==null?"":(String)obj[0]);
				role.setRoleName(obj[1]==null?"":((String)obj[1]));
//				role.setRoleName(obj[1]==null?"":((String)obj[1]).contains("_")?((String)obj[1]).substring(((String)obj[1]).indexOf("_")+1):(String)obj[1]);
				role.setDescription(obj[2]==null?"":(String)obj[2]);
				role.setCarrierId(obj[3]==null?"":(String)obj[3]);
				role.setPager(pager);
				roleList.add(role);
			}
		}
		return roleList;
	}

	/**获取权限列表
	 * @param loginUser
	 * @return
	 * @throws BizException 
	 * @see com.youlb.biz.privilege.IRoleBiz#getPrivilegeList(com.youlb.entity.privilege.Operator)
	 */
	@Override
	public List<Privilege> getPrivilegeList(Operator loginUser,Role role) throws BizException {
		 StringBuilder sb = new StringBuilder();
		 List<Object> values = new ArrayList<Object>();
		 sb.append("SELECT p.id,p.fname from  t_privilege p ");
		 //除了特殊admin其他用户只显示自己拥有的权限
		 if(!SysStatic.SPECIALADMIN.equals(loginUser.getIsAdmin())){
			 sb.append(" INNER JOIN  t_role_privilege trp on p.id=trp.fprivilegeid INNER JOIN t_operator_role tor ON tor.froleid=trp.froleid ")
		     .append("where tor.foperatorid=? and (p.fparentid is null or p.fparentid ='')");
			 values.add(loginUser.getId());
		 }else{
			 sb.append(" where p.fparentid is null or p.fparentid =''");
		 }
		 sb.append(" order by p.fgrouporder");
		 List<Object[]> listObj =  privilegeSqlDao.pageFindBySql(sb.toString(), values.toArray());
		 List<Privilege> pList = objToModel(listObj);
		 List<Privilege> list = getPrivilegeList(pList,role,loginUser);
		return list;
	}
	/**
	 * 递归获取权限列表
	 * @param pList
	 * @return
	 * @throws BizException 
	 */
	private List<Privilege> getPrivilegeList(List<Privilege> pList,Role role,Operator loginUser) throws BizException{
		if(pList!=null){
//			String hql = "from Privilege t where t.parentId=?";
			 StringBuilder sb = new StringBuilder();
			 List<Object> values = new ArrayList<Object>();
			 sb.append("SELECT p.id,p.fname from  t_privilege p ");
			 //除了特殊admin其他用户只显示自己拥有的权限
			 if(!SysStatic.SPECIALADMIN.equals(loginUser.getIsAdmin())){
				 sb.append(" INNER JOIN  t_role_privilege trp on p.id=trp.fprivilegeid INNER JOIN t_operator_role tor ON tor.froleid=trp.froleid ")
				 .append("where tor.foperatorid=? and p.fparentid=?");
				 values.add(loginUser.getId());
			 }else{
				 sb.append(" where p.fparentid=?");
			 }
			 sb.append(" order by p.fgrouporder");
			for(Privilege privilege:pList){
				values.add(privilege.getId());
				List<Object[]> listObj = privilegeSqlDao.pageFindBySql(sb.toString(),values.toArray());
				//每次查询完都做参数删除
				values.remove(privilege.getId());
				List<Privilege> list = objToModel(listObj);
				privilege.setChildren(list);
				//回显已有权限
				List<String> privilegeIds = role.getPrivilegeIds();
				if(privilegeIds!=null&&!privilegeIds.isEmpty()&&privilegeIds.contains(privilege.getId())){
					privilege.setChecked(SysStatic.CHECKED);
				}
				getPrivilegeList(list,role,loginUser);
			}
		}
		return pList;
	}
	/**
	 * 权限数组转对象
	 * @param listObj
	 * @return
	 */
   private List<Privilege> objToModel(List<Object[]> listObj){
	   List<Privilege> list = new ArrayList<Privilege>();
	   if(listObj!=null&&!listObj.isEmpty()){
		   for(Object[] obj:listObj){
			   Privilege privilege = new Privilege();
			   privilege.setId(obj[0]==null?"":(String)obj[0]);
			   privilege.setName(obj[1]==null?"":(String)obj[1]);
			   list.add(privilege);
		   }
	   }
	   return list;
   }
	/**
	 * @param role
	 * @param loginUser
	 * @return
	 * @throws BizException 
	 * @see com.youlb.biz.privilege.IRoleBiz#carrierShowList(com.youlb.entity.privilege.Role, com.youlb.entity.privilege.Operator)
	 */
	@Override
	public List<Role> carrierShowList(Role target, Operator loginUser) throws BizException {
		 StringBuilder sb = new StringBuilder();
		 List<Object> values = new ArrayList<Object>();
		 List<Role> roleList = new ArrayList<Role>();
		 sb.append("select * from (select r.id id,r.frolename rolename,r.fdescription description,r.fcarrierid carrierid,r.fcreatetime createTime")
			.append(" FROM t_role r INNER JOIN t_carrier_domain tcd on tcd.fcarrierid=r.fcarrierid  where r.fisadmin=? ");
		 values.add(SysStatic.NORMALADMIN);
		 if(SysStatic.NORMALCARRIER.equals(loginUser.getCarrier().getIsNormal())){
			 sb.append(" and r.fcarrierid =? ");
			 values.add(loginUser.getCarrier().getId());
		 }
		 
		 List<String> domainIds = loginUser.getDomainIds();
			if(domainIds!=null&&!domainIds.isEmpty()){
				sb.append(SearchHelper.jointInSqlOrHql(domainIds,"tcd.fdomainid"));
				values.add(domainIds);
			}
			sb.append(" group by r.id,r.frolename,r.fdescription,r.fcarrierid,r.fcreatetime");
			sb.append(")t where 1=1 ");
			//过滤名称
		    if(StringUtils.isNotBlank(target.getRoleName())){
		    	sb.append(" and t.roleName like ?");
		    	values.add("%"+target.getRoleName()+"%");
		    }
			OrderHelperUtils.getOrder(sb, target, "t.", "t.createTime");
			List<Object[]> listObj = roleSqlDao.pageFindBySql(sb.toString(), values.toArray(), target.getPager());
			if(listObj!=null&&!listObj.isEmpty()){
				Pager pager = target.getPager();
				pager.setTotalRows(listObj.size());
				for(Object[] obj:listObj){
					Role role = new Role();
					role.setId(obj[0]==null?"":(String)obj[0]);
					role.setRoleName(obj[1]==null?"":(String)obj[1]);
					role.setDescription(obj[2]==null?"":(String)obj[2]);
					role.setCarrierId(obj[3]==null?"":(String)obj[3]);
					role.setPager(pager);
					roleList.add(role);
				}
			}
			return roleList;
	}
	@Override
	public boolean checkEmptyRole(Role role, Operator loginUser) throws BizException {
		 StringBuilder sql = new StringBuilder();
		 sql.append("SELECT r.id from t_role r INNER JOIN t_carrier c on c.id=r.fcarrierid ");
		 sql.append(" where c.fcarriernum=? and r.id not in(SELECT sr.froleid from t_role_domain sr) and r.id not in(SELECT rp.froleid from t_role_privilege rp)");
		 List<String> list = roleSqlDao.pageFindBySql(sql.toString(), new Object[]{loginUser.getCarrier().getCarrierNum()});
		 if(list!=null&&!list.isEmpty()){
			 return true;
		 }
		 return false;
	}
}
