package com.youlb.biz.staticParam.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youlb.biz.staticParam.IStaticParamBiz;
import com.youlb.dao.common.BaseDaoBySql;
import com.youlb.entity.privilege.Operator;
import com.youlb.entity.staticParam.StaticParam;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.helper.OrderHelperUtils;
@Service("staticParamBiz")
public class StaticParamBizImpl implements IStaticParamBiz {
    @Autowired
    private BaseDaoBySql<StaticParam> staticParamSqlDao;
	public void setStaticParamSqlDao(BaseDaoBySql<StaticParam> staticParamSqlDao) {
		this.staticParamSqlDao = staticParamSqlDao;
	}

	@Override
	public String save(StaticParam target) throws BizException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(StaticParam target) throws BizException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Serializable id) throws BizException {
		staticParamSqlDao.delete(id);

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
	public StaticParam get(Serializable id) throws BizException {
		 
		return staticParamSqlDao.get(id);
	}

	@Override
	public List<StaticParam> showList(StaticParam target, Operator loginUser)throws BizException {
		List<Object> values = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder();
		sb.append("from StaticParam t where 1=1 ");
		 if(StringUtils.isNotBlank(target.getFkey())){
			 sb.append(" and t.fkey like ? ");
			 values.add("%"+target.getFkey()+"%");
		 }
		 if(StringUtils.isNotBlank(target.getFvalue())){
			 sb.append(" and t.fvalue like ? ");
			 values.add("%"+target.getFvalue()+"%");
		 }
		 if(StringUtils.isNotBlank(target.getFdescr())){
			 sb.append(" and t.fdescr like ? ");
			 values.add("%"+target.getFdescr()+"%");
		 }
		 OrderHelperUtils.getOrder(sb, target, "t.", " t.createTime ");
		return staticParamSqlDao.pageFind(sb.toString(), values.toArray(), target.getPager());
	}

	@Override
	public void saveOrUpdate(StaticParam staticParam, Operator loginUser) throws BizException {
		 staticParamSqlDao.saveOrUpdate(staticParam);
	}
    /**
     * 通过key获取参数对象
     * @param fkey
     * @return
     * @throws BizException 
     * @see com.youlb.biz.staticParam.IStaticParamBiz#getParamByKey(java.lang.String)
     */
	@Override
	public StaticParam getParamByKey(String fkey) throws BizException {
		StringBuilder sb = new StringBuilder();
		sb.append("from StaticParam t where t.fkey = ? ");
		return staticParamSqlDao.findObject(sb.toString(), new Object[]{fkey});
	}

	@Override
	public List<StaticParam> getParamByLikeKey(String fkey,Integer lenght) throws BizException {
		StringBuilder sb = new StringBuilder();
		sb.append("from StaticParam t where substr(t.fkey, 0 ,?)=?");
		return staticParamSqlDao.find(sb.toString(), new Object[]{lenght,fkey});
	}

}
