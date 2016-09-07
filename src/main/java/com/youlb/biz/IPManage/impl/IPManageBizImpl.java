package com.youlb.biz.IPManage.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
	public void saveOrUpdate(IPManage iPManage, Operator loginUser) {
		 if(StringUtils.isBlank(iPManage.getId())){
			 iPManageDao.add(iPManage);
		 }else{
			 iPManageDao.update(iPManage);
		 }
		
	}

}
