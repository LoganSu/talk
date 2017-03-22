package com.youlb.biz.houseInfo.impl;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
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
import com.youlb.utils.exception.JsonException;
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
		Unit u= unitSqlDao.get(id);
		//获取parentid
		String sql = "select d.fparentid,d.fcreate_sip_num from t_domain d  where d.fentityid=?";
		List<Object[]> list = unitSqlDao.pageFindBySql(sql, new Object[]{id});
		if(list!=null&&!list.isEmpty()){
			u.setParentId(list.get(0)[0]==null?"":(String)list.get(0)[0]);
			u.setCreateSipNum(list.get(0)[1]==null?"":(String)list.get(0)[1]);
		}
		return u;
	}


	/**
	 * @param unit
	 * @throws BizException 
	 * @throws JsonException 
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws UnsupportedEncodingException 
	 * @see com.youlb.biz.unit.IUnitBiz#saveOrUpdate(com.youlb.entity.unit.Unit)
	 */
	@Override
	public void saveOrUpdate(Unit unit,Operator loginUser) throws BizException, UnsupportedEncodingException, ClientProtocolException, IOException, JsonException {
		//add
		if(StringUtils.isBlank(unit.getId())){
			String unitId = (String) unitSqlDao.add(unit);
			Domain domain = new Domain();
			domain.setEntityId(unitId);
			domain.setLayer(SysStatic.UNIT);//单元层
			domain.setRemark(unit.getUnitName());
			domain.setParentId(unit.getParentId());//domain的parentId
			//防止前段恶意传参
			if("2".equals(unit.getCreateSipNum())){
				domain.setCreateSipNum("2");
			}
			String domainId = (String) domainBiz.save(domain);
			loginUser.getDomainIds().add(domainId);
			domainSqlDao.getCurrSession().flush();
			//域跟运营商绑定
			String sql ="insert into t_carrier_domain (fdomainid,fcarrierid) values(?,?)";
			domainSqlDao.executeSql(sql, new Object[]{domainId,loginUser.getCarrier().getId()});
			
			//创建sip
			domainBiz.createSip(domainId, domainBiz.getNeiborName(unitId));
		}else{
			unitSqlDao.update(unit);
			//更新与对象
			domainBiz.update(unit.getUnitName(),unit.getCreateSipNum(),unit.getId());
			
			//老数据没有sip账号需要补上
			if("2".equals(unit.getCreateSipNum())){
				//查询是否有sip账号
				List<Object[]> list = domainBiz.getDomainIdAndSipByEntityId(unit.getId());
				if(list!=null&&!list.isEmpty()){
					if(list.get(0)[1]==null){
						//创建sip
						domainBiz.createSip((String)list.get(0)[0], domainBiz.getNeiborName(unit.getId()));
					}
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
	public List<Unit> showList(Unit target, Operator loginUser)throws BizException {
		 List<Unit> list = new ArrayList<Unit>();
		 StringBuilder sb = new StringBuilder();
		 List<Object> values = new ArrayList<Object>();
		 sb.append("select * from (select u.id id,u.FUNITNUM unitNum,u.FUNITNAME unitName,u.FREMARK remark,u.fcreatetime createTime," )
		 .append(" d.fcreate_sip_num createSipNum,us.user_sip sipNum,us.user_password sipNumPsw ")
		 .append(" from t_unit u inner join t_domain d on d.fentityid = u.id left join users us on us.local_sip=d.id where d.fparentid=? ");
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
					if("2".equals(obj[5])){
						unit.setSipNum(obj[6]==null?null:(Integer)obj[6]+"");
					}
					unit.setSipNumPsw(obj[7]==null?"":(String)obj[7]);
					unit.setPager(pager);
					list.add(unit);
			 }
		 }
		 return list;
	}
	/**
	 * @param id
	 * @return
	 * @throws BizException 
	 * @see com.youlb.biz.houseInfo.IUnitBiz#getUnitListBybuildingId(java.lang.String)
	 */
	@Override
	public List<Unit> getUnitListBybuildingId(String buildingId) throws BizException {
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
	@Override
	public boolean checkUnitNum(Unit unit) throws BizException {
		List<Object> values = new ArrayList<Object>();
		 StringBuilder sb = new StringBuilder("SELECT n.FUNITNUM from t_domain d INNER JOIN t_unit n on n.id=d.fentityid where d.fparentid=? ");
		 values.add(unit.getParentId());
		 if(StringUtils.isNotBlank(unit.getId())){
			 sb.append(" and n.id!=? ");
			 values.add(unit.getId());
		 }
		 List<String> list = unitSqlDao.pageFindBySql(sb.toString(), values.toArray());
		 if(list!=null&&!list.isEmpty()&&list.contains(unit.getUnitNum())){
			 return true;
		 }
		return false;
	}
	@Override
	public boolean checkUnitName(Unit unit) throws BizException {
		List<Object> values = new ArrayList<Object>();
		 StringBuilder sb = new StringBuilder("SELECT n.FUNITNAME from t_unit n INNER JOIN t_domain d on n.id=d.fentityid where d.fparentid=?  ");
		 values.add(unit.getParentId());
		 if(StringUtils.isNotBlank(unit.getId())){
			 sb.append(" and n.id!=? ");
			 values.add(unit.getId());
		 }
		 List<String> list = unitSqlDao.pageFindBySql(sb.toString(), values.toArray());
		 if(list!=null&&!list.isEmpty()&&list.contains(unit.getUnitName())){
			 return true;
		 }
		return false;
	}

}
