package com.youlb.biz.domainName.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.youlb.biz.domainName.IDomainNameBiz;
import com.youlb.dao.common.BaseDaoBySql;
import com.youlb.entity.domainName.DomainName;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.exception.BizException;
@Service("domainNameBiz")
public class DomainNameBizImpl implements IDomainNameBiz {
	@Autowired
	private BaseDaoBySql<DomainName> domainNameSqlDao;
	public void setDomainNameSqlDao(BaseDaoBySql<DomainName> domainNameSqlDao) {
		this.domainNameSqlDao = domainNameSqlDao;
	}

	@Override
	public String save(DomainName target) throws BizException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(DomainName target) throws BizException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Serializable id) throws BizException {
		 domainNameSqlDao.deleteLogic(id);
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
	public DomainName get(Serializable id) throws BizException {
		return  domainNameSqlDao.get(id);
	}

	@Override
	public List<DomainName> showList(DomainName target, Operator loginUser)throws BizException {
		 StringBuilder sb = new StringBuilder();
		 List<Object> values = new ArrayList<Object>();
		 sb.append("from DomainName where delFlag=1 ");//逻辑删除
		 if(StringUtils.isNotBlank(target.getParentid())){
			 sb.append(" and parentid=? ");
			 values.add(target.getParentid());
		 }else{
			 sb.append(" and (parentid is null or parentid='') ");
		 }
		return domainNameSqlDao.pageFind(sb.toString(), values.toArray(),target.getPager());
	}

	@Override
	public void saveOrUpdate(DomainName domainName, Operator loginUser) throws BizException {
		if(StringUtils.isBlank(domainName.getId())){
			domainName.setId(null);
			domainNameSqlDao.add(domainName);
		}else{
			domainNameSqlDao.update(domainName);
		}
		
	}

	@Override
	public List<DomainName> showListByParentId(String id, Operator loginUser) {
		// TODO Auto-generated method stub
		return null;
	}
    /**
     * 获取二级域名列表
     * @return
     * @throws BizException 
     * @see com.youlb.biz.domainName.IDomainNameBiz#getTwoDomainName()
     */
	@Override
	public List<DomainName> getTwoDomainName() throws BizException {
		 String hql = "from DomainName where parentid='14'";
		 List<DomainName> list = domainNameSqlDao.find(hql);
		return list;
	}

}
