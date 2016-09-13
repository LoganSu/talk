package com.youlb.biz.houseInfo.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youlb.biz.houseInfo.IDomainBiz;
import com.youlb.dao.common.BaseDaoBySql;
import com.youlb.entity.baseInfo.Carrier;
import com.youlb.entity.common.Domain;
import com.youlb.entity.houseInfo.Room;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.common.SysStatic;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.helper.OrderHelperUtils;
import com.youlb.utils.helper.SearchHelper;

/** 
 * @ClassName: DomainBizImpl.java 
 * @Description: 域对象接口实现类 
 * @author: Pengjy
 * @date: 2015年8月31日
 * 
 */
@Service("domainBiz")
public class DomainBizImpl implements IDomainBiz {
    
	@Autowired
	private BaseDaoBySql<Domain> domainSqlDao;
	public void setDomainSqlDao(BaseDaoBySql<Domain> domainSqlDao) {
		this.domainSqlDao = domainSqlDao;
	}

	/**
	 * @param target
	 * @return
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#save(java.io.Serializable)
	 */
	@Override
	public String save(Domain target) throws BizException {
		Session session = domainSqlDao.getCurrSession();
		   SQLQuery query = session.createSQLQuery("SELECT nextval('tbl_seq')");
		   List<BigInteger> list =  query.list();
		   target.setDomainSn(list.get(0).intValue());
		return (String) domainSqlDao.add(target);
	}

	/**
	 * @param target
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#update(java.io.Serializable)
	 */
	@Override
	public void update(Domain target) throws BizException {

	}
	/**
	 * @param entityId
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#update(java.io.Serializable)
	 */
	@Override
	public void update(String remark,String entityId) throws BizException {
		String update = "update Domain set remark=? where entityId=?";
		domainSqlDao.update(update, new Object[]{remark,entityId});
	}
	/**
	 * @param id
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Serializable id) throws BizException {
		// TODO Auto-generated method stub

	}

	/**
	 * @param ids
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) throws BizException {
		// TODO Auto-generated method stub

	}

	/**
	 * @param id
	 * @return
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#get(java.io.Serializable)
	 */
	@Override
	public Domain get(Serializable id) throws BizException {
		 
		return domainSqlDao.get(id);
	}

	/**
	 * @param target
	 * @param loginUser
	 * @return
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#showList(java.io.Serializable, com.youlb.entity.privilege.Operator)
	 */
	@Override
	public List<Domain> showList(Domain target, Operator loginUser)
			throws BizException {
		StringBuilder sb = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		sb.append("from Domain t where 1=1 ");
		
		if(StringUtils.isNotBlank(target.getParentId())){
			sb.append(" and t.parentId=?");
			values.add(target.getParentId());
		}else{
			sb.append(" and t.parentId=?");
			values.add(SysStatic.HOUSEINFO);
		}
		List<String> domainIds = loginUser.getDomainIds();
		//不是特殊管理员需要过滤域
		if(!SysStatic.SPECIALADMIN.equals(loginUser.getIsAdmin())&&domainIds!=null&&!domainIds.isEmpty()){
			sb.append(SearchHelper.jointInSqlOrHql(domainIds,"t.id"));
			values.add(domainIds);
		}
		OrderHelperUtils.getOrder(sb, target, "t.", "t.createTime");
		List<Domain> list = domainSqlDao.pageFind(sb.toString(), values.toArray(), target.getPager());
		return list;
	}

	/**获取子节点
	 * @param id
	 * @return
	 * @throws BizException 
	 * @see com.youlb.biz.houseInfo.IDomainBiz#getDomainByParentId(java.lang.String)
	 */
	@Override
	public List<Domain> getDomainByParentId(String id,Operator loginUser,String dwellerId) throws BizException {
		List<Domain> list = new ArrayList<Domain>();
		StringBuilder sb = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		sb.append("select d.id,d.fparentid,d.fremark,d.flayer,d.fentityid,d.fcreatetime from t_domain d where d.fparentid = ? ");
		values.add(id);
		//过滤已经被选中的房间
//		if(StringUtils.isNotBlank(dwellerId)){
//			sb.append(" and d.id = (SELECT tdd.fdomainid from t_domain_dweller tdd where tdd.fdwellerid=?)");
//			values.add(dwellerId);
//		}
		List<String> domainIds = loginUser.getDomainIds();
		//不是特殊管理员需要过滤域
		if(!SysStatic.SPECIALADMIN.equals(loginUser.getIsAdmin())&&domainIds!=null&&!domainIds.isEmpty()){
			sb.append(SearchHelper.jointInSqlOrHql(domainIds,"d.id"));
			values.add(domainIds);
		}
		sb.append(" order by d.fcreatetime");
		List<Object[]> listObj = domainSqlDao.pageFindBySql(sb.toString(), values.toArray());
		if(listObj!=null&&!listObj.isEmpty()){
			for(Object[] obj:listObj){
				Domain domain = new Domain();
				domain.setId(obj[0]==null?null:(String)obj[0]);
				domain.setParentId(obj[1]==null?null:(String)obj[1]);
				domain.setRemark(obj[2]==null?null:(String)obj[2]);
				domain.setLayer(obj[3]==null?null:(Integer)obj[3]);
				domain.setEntityId(obj[4]==null?null:(String)obj[4]);
				domain.setCreateTime(obj[5]==null?null:(Date)obj[5]);
				list.add(domain);
			}
		}
		return list;
	}

	/**deleteByEntityId
	 * @param id
	 * @throws BizException 
	 * @see com.youlb.biz.houseInfo.IDomainBiz#deleteByEntityId(java.io.Serializable)
	 */
	@Override
	public void deleteByEntityId(Serializable id) throws BizException {
		String sql ="delete from t_domain t where t.fentityid = ?";
		domainSqlDao.executeSql(sql, new Object[]{id});
	}

	/**
	 * @param carrier
	 * @param loginUser
	 * @return
	 * @throws BizException 
	 * @see com.youlb.biz.houseInfo.IDomainBiz#getDomainList(com.youlb.entity.baseInfo.Carrier, com.youlb.entity.privilege.Operator)
	 */
	@Override
	public List<Domain> getDomainList(Carrier carrier, Operator loginUser) throws BizException {
		 StringBuilder sb = new StringBuilder();
		 List<Object> values = new ArrayList<Object>();
		 sb.append("from Domain t where t.parentId=? ");
		 values.add(SysStatic.HOUSEINFO);
		 //过滤域 数据
		 List<String> domainIds = loginUser.getDomainIds();
			if(domainIds!=null&&!domainIds.isEmpty()){
				sb.append(SearchHelper.jointInSqlOrHql(domainIds,"t.id"));
				values.add(domainIds);
			}
			sb.append(" order by t.remark ");
		 List<Domain> dList = domainSqlDao.find(sb.toString(), values.toArray());
		 List<Domain> list = getDomainList(dList, carrier,loginUser);
		 //去掉没有被分配的子域
		 if(list!=null&&!list.isEmpty()){
			 Domain domain = list.get(0);
			 List<Domain> subList = domain.getChildren();
			 List<Domain> newSubList = new ArrayList<Domain>();
			 if(subList!=null&&!subList.isEmpty()){
				 for(Domain subDomain:subList){
					 if(domainIds.contains(subDomain.getId())){
						 newSubList.add(subDomain);
					 }
				 }
				 domain.setChildren(newSubList);
			 }
		 }
		 return list;
	}
	
	/**
	 * 递归获取权限列表
	 * @param pList
	 * @return
	 * @throws BizException 
	 */
	private List<Domain> getDomainList(List<Domain> dList,Carrier carrier,Operator loginUser) throws BizException{
		if(dList!=null){
			for(Domain domain:dList){
				StringBuilder sb = new StringBuilder();
				 sb.append("from Domain t where t.parentId=?");
				 //房间按房间号怕排序
				if(new Integer(3).equals(domain.getLayer())){
					sb.append(" order by to_number(t.remark,'999999999999999')");
				}else{
					sb.append(" order by t.createTime");
				}
//				System.out.println(sb.toString());
				List<Domain> list = domainSqlDao.find(sb.toString(), new Object[]{domain.getId()});
				domain.setChildren(list);
				//回显已有域对象
				List<String> domainIds = carrier.getTreecheckbox();
				if(domainIds!=null&&!domainIds.isEmpty()&&domainIds.contains(domain.getId())){
					domain.setChecked(SysStatic.CHECKED);
				}
				//如果是特殊运营商 社区下面的数据不显示
//				if(SysStatic.SPECIALCARRIER.equals(loginUser.getCarrier().getIsNormal())&&SysStatic.NEIGHBORHOODS.equals(domain.getLayer())){
//					domain.setChildren(null);
//				} 
				getDomainList(list,carrier,loginUser);
			}
		}
		return dList;
	}
	
	
	@Override
	public String getDomainIdByEntityId(String entityid) throws BizException {
		String sql="select d.id from t_domain d where d.fentityid=?";
		List<String> list = domainSqlDao.pageFindBySql(sql, new Object[]{entityid});
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	/**
	 * 获取单元下面的房间号，判断房号是否已经存在
	 * @param room
	 * @return
	 * @throws BizException 
	 */
	@Override
	public String getDomainByParentId(Room room) throws BizException {
		String sql="select d.fremark from t_domain d where d.fparentid=? and d.fremark=? ";
		List<Object> values = new ArrayList<Object>();
		values.add(room.getParentId());
		values.add(room.getRoomNum());
		if(StringUtils.isNotBlank(room.getId())){
			sql+=" and d.fentityid != ? ";
			values.add(room.getId());
		}
		List<String> list = domainSqlDao.pageFindBySql(sql, values.toArray());
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	/**
	 * 通过entityid 获取有子节点的域名称
	 * @param ids
	 * @return
	 * @throws BizException 
	 */
	@Override
	public String hasChild(String[] ids) throws BizException {
		String sql="WITH RECURSIVE r AS (SELECT d.* FROM t_domain d WHERE d.fentityid =? union all SELECT t_domain.* FROM t_domain, r WHERE t_domain.fparentid = r.id)"
				  + " SELECT r.id FROM r where r.fentityid!=?";
		if(ids!=null){
			for(String entityId:ids){
				List<String> list = domainSqlDao.executeQuerySql(sql, new Object[]{entityId,entityId});
				//有子节点 返回名称
				if(list!=null&&!list.isEmpty()){
					String findRemark = "select fremark from t_domain where fentityid=?";
					List<String> remarkList = domainSqlDao.pageFindBySql(findRemark, new Object[]{entityId});
					if(remarkList!=null&&!remarkList.isEmpty()){
						return remarkList.get(0);
					}
				}
			}
		}
		
		return null;
	}

	@Override
	public String getParentIdByEntityId(String entityid) throws BizException {
		String sql="select d.fparentid from t_domain d where d.fentityid=?";
		List<String> list = domainSqlDao.pageFindBySql(sql, new Object[]{entityid});
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
}
