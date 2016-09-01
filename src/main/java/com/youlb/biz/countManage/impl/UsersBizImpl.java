package com.youlb.biz.countManage.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youlb.biz.countManage.IUsersBiz;
import com.youlb.dao.common.BaseDaoBySql;
import com.youlb.entity.countManage.Users;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.common.SysStatic;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.helper.OrderHelperUtils;
import com.youlb.utils.helper.SearchHelper;

/** 
 * @ClassName: UsersBiz.java 
 * @Description: 注册用户业务实现类 
 * @author: Pengjy
 * @date: 2015-11-24
 * 
 */
@Service("usersBiz")
public class UsersBizImpl implements IUsersBiz {
	@Autowired
    private BaseDaoBySql<Users> usersSqlDao;
	public void setUsersSqlDao(BaseDaoBySql<Users> usersSqlDao) {
		this.usersSqlDao = usersSqlDao;
	}

	/** 
	 * @param target
	 * @return
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#save(java.io.Serializable)
	 */
	@Override
	public String save(Users target) throws BizException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param target
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#update(java.io.Serializable)
	 */
	@Override
	public void update(Users target) throws BizException {
		// TODO Auto-generated method stub

	}

	/**
	 * @param id
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Serializable id) throws BizException {
		String delete = "delete from t_users where id=?";
		usersSqlDao.executeSql(delete, new Object[]{id});

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
				delete(Integer.parseInt(id));
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
	public Users get(Serializable id) throws BizException {
		// TODO Auto-generated method stub
		return null;
	}

	/**admin可以查看所有注册用户，运营商只能查看注册且登记为住户的用户
	 * @param target
	 * @param loginUser
	 * @return
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#showList(java.io.Serializable, com.youlb.entity.privilege.Operator)
	 */
	@Override
	public List<Users> showList(Users target, Operator loginUser)throws BizException {
		StringBuilder sb = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		List<Users> list = new ArrayList<Users>();
		sb.append("select * from (SELECT u.id id,u.fusername username,u.fmobile_phone mobilePhone,u.femail email,u.frealname realName,u.fcreatetime createTime,u.femail_status emailStatus,u.fstatus statusStr")
//		.append(" from t_users u INNER JOIN t_dweller dw on dw.fphone=u.fmobile_phone INNER JOIN t_domain_dweller tdd on tdd.fdwellerid=dw.id where 1=1");
		.append(" from t_users u left JOIN (SELECT dw.fphone fphone,tdd.fdomainid fdomainid  from t_dweller dw INNER  JOIN t_domain_dweller tdd on tdd.fdwellerid=dw.id ) t ")
		.append("  on t.fphone=u.fmobile_phone where 1=1");

		if(StringUtils.isNotBlank(target.getUsername())){
			sb.append(" and u.fusername like ?");
			values.add("%"+target.getUsername()+"%");
		}
		if(StringUtils.isNotBlank(target.getPhone())){
			sb.append(" and u.fmobile_phone like ?");
			values.add("%"+target.getPhone()+"%");
		}
		List<String> domainIds = loginUser.getDomainIds();
		if(!SysStatic.SPECIALADMIN.equals(loginUser.getIsAdmin())&&domainIds!=null&&!domainIds.isEmpty()){
			sb.append(SearchHelper.jointInSqlOrHql(domainIds,"t.fdomainid"));
			values.add(domainIds);
		}
		sb.append(" group by u.id,u.fusername,u.fmobile_phone,u.femail,u.frealname,u.fcreatetime) o");
		OrderHelperUtils.getOrder(sb, target, "o.", "o.createTime");
		List<Object[]> listObj = usersSqlDao.pageFindBySql(sb.toString(), values.toArray(), target.getPager());
		if(listObj!=null&&!listObj.isEmpty()){
			for(Object[] obj:listObj){
				Users user = new Users();
				user.setPager(target.getPager());
				user.setId(obj[0]==null?null:(Integer)obj[0]);
				user.setUsername(obj[1]==null?"":(String)obj[1]);
				user.setMobilePhone(obj[2]==null?"":(String)obj[2]);
				user.setEmail(obj[3]==null?"":(String)obj[3]);
				user.setRealName(obj[4]==null?"":(String)obj[4]);
				user.setCreateTime(obj[5]==null?null:(Date)obj[5]);
				user.setEmailStatus(obj[6]==null?"":(String)obj[6]);
				user.setStatus(obj[7]==null?"":(String)obj[7]);
				list.add(user);
			}
		}
		 return list;
	}

	/**
	 * @param users
	 * @see com.youlb.biz.countManage.IUsersBiz#saveOrUpdate(com.youlb.entity.countManage.Users)
	 */
	@Override
	public void saveOrUpdate(Users users) {
		// TODO Auto-generated method stub
		
	}
    /**
     * 暂停用户使用
     * @param id
     * @see com.youlb.biz.countManage.IUsersBiz#update(java.lang.String)
     */
	@Override
	public void update(Integer id,String status) {
		String hql ="update Users set status = ? where id=?";
		usersSqlDao.update(hql, new Object[]{status,id});
	}

}
