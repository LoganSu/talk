package com.youlb.biz.houseInfo.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.youlb.biz.houseInfo.IDomainBiz;
import com.youlb.biz.houseInfo.IUnitBiz;
import com.youlb.dao.common.BaseDaoBySql;
import com.youlb.entity.common.Domain;
import com.youlb.entity.common.Pager;
import com.youlb.entity.houseInfo.Unit;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.common.SysStatic;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.helper.OrderHelperUtils;
import com.youlb.utils.helper.SearchHelper;

/** 
 * @ClassName: UnitBizImpl.java 
 * @Description: 单元信息业务实现类
 * @author: Pengjy
 * @date: 2015年7月25日
 * 
 */
@Service
@Component("unitBiz")
public class UnitBizImpl implements IUnitBiz {
	
	@Autowired
	private BaseDaoBySql<Unit> unitSqlDao;
	@Autowired
	private BaseDaoBySql<Domain> domainSqlDao;
	public void setDomainSqlDao(BaseDaoBySql<Domain> domainSqlDao) {
		this.domainSqlDao = domainSqlDao;
	}
	public void setUnitSqlDao(BaseDaoBySql<Unit> unitSqlDao) {
		this.unitSqlDao = unitSqlDao;
	}
	@Autowired
    private IDomainBiz domainBiz;
	public void setDomainBiz(IDomainBiz domainBiz) {
		this.domainBiz = domainBiz;
	}
	 
	/**
	 * @param id
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Serializable id) throws BizException {
		unitSqlDao.delete(id);
		//删除domain里面的数据
		domainBiz.deleteByEntityId(id);
	}


	/**
	 * @param target
	 * @return
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#save(java.io.Serializable)
	 */
	@Override
	public String save(Unit target) throws BizException {
		// TODO Auto-generated method stub
		return null;
	}


	/**
	 * @param target
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#update(java.io.Serializable)
	 */
	@Override
	public void update(Unit target) throws BizException {
		// TODO Auto-generated method stub
		
	}


	/**
	 * @param ids
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) throws BizException {
		if(ids!=null&&ids.length>0){
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
	public Unit get(Serializable id) throws BizException {
		return unitSqlDao.get(id);
	}


	/**
	 * @param unit
	 * @see com.youlb.biz.unit.IUnitBiz#saveOrUpdate(com.youlb.entity.unit.Unit)
	 */
	@Override
	public void saveOrUpdate(Unit unit,Operator loginUser) {
		//add
		if(StringUtils.isBlank(unit.getId())){
			String unitId = (String) unitSqlDao.add(unit);
			Domain domain = new Domain();
			domain.setEntityId(unitId);
			domain.setLayer(SysStatic.UNIT);//单元层
			domain.setRemark(unit.getUnitName());
			domain.setParentId(unit.getParentId());//domain的parentId
			String domainId = (String) domainBiz.save(domain);
			loginUser.getDomainIds().add(domainId);
			domainSqlDao.getCurrSession().flush();
			//域跟运营商绑定
			String sql ="insert into t_carrier_domain (fdomainid,fcarrierid) values(?,?)";
			domainSqlDao.executeSql(sql, new Object[]{domainId,loginUser.getCarrier().getId()});
		}else{
			unitSqlDao.update(unit);
			//更新与对象
			domainBiz.update(unit.getUnitName(),unit.getId());
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
	public List<Unit> showList(Unit target, Operator loginUser)throws BizException {
		 List<Unit> list = new ArrayList<Unit>();
		 StringBuilder sb = new StringBuilder();
		 List<Object> values = new ArrayList<Object>();
		 sb.append("select * from (select u.id id,u.FUNITNUM unitNum,u.FUNITNAME unitName,u.FREMARK remark,u.fcreatetime createTime" )
		 .append(" from t_unit u inner join t_domain d on d.fentityid = u.id where d.fparentid=? ");
		 values.add(target.getParentId());
		 List<String> domainIds = loginUser.getDomainIds();
			if(domainIds!=null&&!domainIds.isEmpty()){
				sb.append(SearchHelper.jointInSqlOrHql(domainIds,"d.id"));
				values.add(domainIds);
			}
			if(StringUtils.isNotBlank(target.getUnitNum())){
				sb.append("and u.FUNITNUM like ?");
				values.add("%"+target.getUnitNum()+"%");
			}
			if(StringUtils.isNotBlank(target.getUnitName())){
				sb.append("and u.FUNITNAME like ?");
				values.add("%"+target.getUnitName()+"%");
			}
		 sb.append(") t");
		 OrderHelperUtils.getOrder(sb, target, "t.", "t.createTime");
		 List<Object[]> listObj = unitSqlDao.pageFindBySql(sb.toString(), values.toArray(), target.getPager());
		 if(listObj!=null&&!listObj.isEmpty()){
			//设置分页
			Pager pager = target.getPager();
			 for(Object[] obj:listObj){
				 Unit unit = new Unit();
				    unit.setId(obj[0]==null?"":(String)obj[0]);
					unit.setUnitNum(obj[1]==null?"":(String)obj[1]);
					unit.setUnitName(obj[2]==null?"":(String)obj[2]);
					unit.setRemark(obj[3]==null?"":(String)obj[3]);
					unit.setPager(pager);
					list.add(unit);
			 }
		 }
		 return list;
	}
	/**
	 * @param id
	 * @return
	 * @see com.youlb.biz.houseInfo.IUnitBiz#getUnitListBybuildingId(java.lang.String)
	 */
	@Override
	public List<Unit> getUnitListBybuildingId(String buildingId) {
		List<Unit> list = new ArrayList<Unit>();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT u.id,u.FUNITNUM,u.FUNITNAME,u.FREMARK,u.fcreatetime ")
		.append("from t_unit u INNER JOIN t_domain sd on sd.fentityid=u.id where sd.fparentid=")
		.append("(SELECT d.id FROM t_domain d where d.fentityid=?) order by u.fcreatetime");
		List<Object[]> listObj = unitSqlDao.pageFindBySql(sb.toString(), new Object[]{buildingId});
		 if(listObj!=null&&!listObj.isEmpty()){
				//设置分页
				 for(Object[] obj:listObj){
					 Unit unit = new Unit();
					    unit.setId(obj[0]==null?"":(String)obj[0]);
						unit.setUnitNum(obj[1]==null?"":(String)obj[1]);
						unit.setUnitName(obj[2]==null?"":(String)obj[2]);
						unit.setRemark(obj[3]==null?"":(String)obj[3]);
						list.add(unit);
				 }
			 }
			 return list;
	}

}
