package com.youlb.biz.IPManage.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youlb.biz.IPManage.IIPManageBiz;
import com.youlb.dao.common.BaseDaoBySql;
import com.youlb.entity.IPManage.IPManage;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.exception.BizException;
@Service
public class IPManageBizImpl implements IIPManageBiz {
	@Autowired
    private BaseDaoBySql<IPManage> iPManageDao;
	public void setiPManageDao(BaseDaoBySql<IPManage> iPManageDao) {
		this.iPManageDao = iPManageDao;
	}

	@Override
	public String save(IPManage target) throws BizException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(IPManage target) throws BizException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Serializable id) throws BizException {
		iPManageDao.delete(id);

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
	public IPManage get(Serializable id) throws BizException {
		 
		return iPManageDao.get(id);
	}

	@Override
	public List<IPManage> showList(IPManage target, Operator loginUser)throws BizException {
		String hql = "from IPManage";
		
		return iPManageDao.pageFind(hql,target.getPager());
	}

	@Override
	public void saveOrUpdate(IPManage iPManage, Operator loginUser) throws BizException {
		 if(StringUtils.isBlank(iPManage.getId())){
			//获取分组号
			 Session session = iPManageDao.getCurrSession();
			SQLQuery group = session.createSQLQuery("SELECT '1'||substring('0000'||nextval('t_neiber_order_seq'),length(currval('t_neiber_order_seq')||'')) ");
			List<String> groupList = group.list();
			iPManage.setNeiborFlag(Integer.parseInt(groupList.get(0)));
			 iPManageDao.add(iPManage);
		 }else{
			 iPManageDao.update(iPManage);
		 }
		
	}
    /**
     * 判断名称是否存在
     * @throws BizException 
     */
	@Override
	public boolean checkNeibName(IPManage iPManage) throws BizException {
		 StringBuilder sb = new StringBuilder();
		 List<Object> values = new ArrayList<Object>();
		 sb.append("select id from t_ip_manage where fneib_name=?");
		 values.add(iPManage.getNeibName());
		 if(StringUtils.isNotBlank(iPManage.getId())){
			 sb.append(" and id != ?");
			 values.add(iPManage.getId());
		 }
		 List<String> list = iPManageDao.pageFindBySql(sb.toString(), values.toArray());
	     if(list!=null&&!list.isEmpty()){
			 return true;
		 }
		return false;
	}

}
