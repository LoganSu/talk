package com.youlb.biz.privilege.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youlb.biz.privilege.IPrivilegeBiz;
import com.youlb.dao.common.BaseDaoBySql;
import com.youlb.entity.privilege.Operator;
import com.youlb.entity.privilege.Privilege;
import com.youlb.utils.common.SysStatic;
import com.youlb.utils.exception.BizException;

/** 
 * @ClassName: PrivilegeBizImpl.java 
 * @Description: 权限接口实现类
 * @author: Pengjy
 * @date: 2015年8月25日
 * 
 */
@Service("privilegeBiz")
public class PrivilegeBizImpl implements IPrivilegeBiz {
	@Autowired
    private BaseDaoBySql<Privilege> privilegeSqlDao;
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
	public String save(Privilege target) throws BizException {
		privilegeSqlDao.add(target);
		return null;
	}

	/**
	 * @param target
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#update(java.io.Serializable)
	 */
	@Override
	public void update(Privilege target) throws BizException {
		// TODO Auto-generated method stub

	}

	/**
	 * @param id
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Serializable id) throws BizException {
		privilegeSqlDao.delete(id);

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
	public Privilege get(Serializable id) throws BizException {
		 
		return privilegeSqlDao.get(id);
	}


	/**
	 * @param privilege
	 * @throws BizException 
	 * @see com.youlb.biz.privilege.IPrivilegeBiz#saveOrUpdate(com.youlb.entity.privilege.Privilege)
	 */
	@Override
	public void saveOrUpdate(Privilege privilege) throws BizException {
		//add
		if(StringUtils.isBlank(privilege.getId())){
			privilegeSqlDao.add(privilege);
		}else{
			privilegeSqlDao.update(privilege);
		}
		
	}

	/**列表显示
	 * @param privilege
	 * @param loginUser
	 * @return
	 * @throws BizException 
	 * @see com.youlb.biz.privilege.IPrivilegeBiz#showList(com.youlb.entity.privilege.Privilege, com.youlb.entity.privilege.Operator)
	 */
	@Override
	public List<Privilege> showList(Privilege privilege, Operator loginUser) throws BizException {
//		StringBuilder sb = new StringBuilder();
//		List<Object> values = new ArrayList<Object>();
//		sb.append("from Privilege t where 1=1 ");
//		if(StringUtils.isNotBlank(privilege.getParentId())){
//			sb.append(" and t.parentId=?");
//			values.add(privilege.getParentId());
//		}else{
//			sb.append(" and (t.parentId is null or t.parentId = '')");
//		}
//		OrderHelperUtils.getOrder(sb, privilege, "t.", "t.groupOrder");
//		List<Privilege> list = privilegeSqlDao.pageFind(sb.toString(), values.toArray(), privilege.getPager());
//		return list;
		 StringBuilder sb = new StringBuilder();
		 List<Object> values = new ArrayList<Object>();
		 sb.append("SELECT p.id,p.fname,p.fkey,p.fdescription from  t_privilege p ");
		 //除了特殊admin其他用户只显示自己拥有的权限
		 if(!SysStatic.SPECIALADMIN.equals(loginUser.getIsAdmin())){
			 if(StringUtils.isNotBlank(privilege.getParentId())){
					sb.append(" INNER JOIN  t_role_privilege trp on p.id=trp.fprivilegeid INNER JOIN t_operator_role tor ON tor.froleid=trp.froleid ")
					.append("where tor.foperatorid=? and p.FPARENTID=?");
					values.add(loginUser.getId());
					values.add(privilege.getParentId());
				}else{
//					sb.append(" where  (o.parentId is null or o.parentId = '')");
					sb.append(" INNER JOIN  t_role_privilege trp on p.id=trp.fprivilegeid INNER JOIN t_operator_role tor ON tor.froleid=trp.froleid ")
					.append("where tor.foperatorid=? and (p.FPARENTID is null or p.FPARENTID ='')");
					values.add(loginUser.getId());
				}
			 
		 }else{
			 if(StringUtils.isNotBlank(privilege.getParentId())){
					sb.append(" where p.FPARENTID=?");
					values.add(privilege.getParentId());
				}else{
					sb.append(" where (p.FPARENTID is null or p.FPARENTID = '')");
				}
		 }
		 sb.append(" order by p.fgrouporder");
		 List<Object[]> listObj =  privilegeSqlDao.pageFindBySql(sb.toString(), values.toArray(),privilege.getPager());
		 List<Privilege> list = new ArrayList<Privilege>();
		   if(listObj!=null&&!listObj.isEmpty()){
			   for(Object[] obj:listObj){
				   Privilege p = new Privilege();
				   p.setPager(privilege.getPager());
				   p.setId(obj[0]==null?"":(String)obj[0]);
				   p.setName(obj[1]==null?"":(String)obj[1]);
				   p.setKey(obj[2]==null?"":(String)obj[2]);
				   p.setDescription(obj[3]==null?"":(String)obj[3]);
				   list.add(p);
			   }
		   }
//		 List<Privilege> pList = objToModel(listObj);
//		 List<Privilege> list = getPrivilegeList(pList,role,loginUser);
		return list;
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
	 * @param id
	 * @param loginUser
	 * @return
	 * @throws BizException 
	 * @see com.youlb.biz.privilege.IPrivilegeBiz#showListByParentId(java.lang.String, com.youlb.entity.privilege.Operator)
	 */
	@Override
	public List<Privilege> showListByParentId(String id, Operator loginUser) throws BizException {
		if(StringUtils.isNotBlank(id)){
			StringBuilder sb = new StringBuilder();
			List<Object> values = new ArrayList<Object>();
			sb.append("from Privilege t where t.parentId=? order by t.groupOrder");
				values.add(id);
			List<Privilege> list = privilegeSqlDao.find(sb.toString(), values.toArray());
			return list;
		}
		return null;
	}


}
