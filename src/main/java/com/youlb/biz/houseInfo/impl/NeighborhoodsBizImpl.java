package com.youlb.biz.houseInfo.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.youlb.biz.houseInfo.IDomainBiz;
import com.youlb.biz.houseInfo.INeighborhoodsBiz;
import com.youlb.dao.common.BaseDaoBySql;
import com.youlb.entity.common.Domain;
import com.youlb.entity.common.Pager;
import com.youlb.entity.houseInfo.Neighborhoods;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.common.SysStatic;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.helper.DateHelper;
import com.youlb.utils.helper.OrderHelperUtils;
import com.youlb.utils.helper.SearchHelper;

/** 
 * @ClassName: NeighborhoodsBiz.java 
 * @Description: 社区信息业务实现类
 * @author: Pengjy
 * @date: 2015年6月25日
 * 
 */
@Service
@Component("neighborBiz")
public class NeighborhoodsBizImpl implements INeighborhoodsBiz {
	
	@Autowired
	private BaseDaoBySql<Neighborhoods> neighborSqlDao;
	@Autowired
	private BaseDaoBySql<Domain> domainSqlDao;
	public void setDomainSqlDao(BaseDaoBySql<Domain> domainSqlDao) {
		this.domainSqlDao = domainSqlDao;
	}
	public void setNeighborSqlDao(BaseDaoBySql<Neighborhoods> neighborSqlDao) {
		this.neighborSqlDao = neighborSqlDao;
	}
	@Autowired
    private IDomainBiz domainBiz;
	public void setDomainBiz(IDomainBiz domainBiz) {
		this.domainBiz = domainBiz;
	}
	/**
	 * @param target
	 * @return
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#save(java.io.Serializable)
	 */
	@Override
	public String save(Neighborhoods target) throws BizException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param target
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#update(java.io.Serializable)
	 */
	@Override
	public void update(Neighborhoods target) throws BizException {
		// TODO Auto-generated method stub

	}

	/**
	 * @param id
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Serializable id) throws BizException {
		neighborSqlDao.delete(id);
		//删除domain里面的数据
		domainBiz.deleteByEntityId(id);
		//删除sip账号
		String delete ="delete from users where local_sip=?";
		domainSqlDao.executeSql(delete, new Object[]{id});
	}

	/**
	 * @param ids
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) throws BizException {
			if(ids!=null&&ids.length>0){
				//更新社区版本参数
				String update ="update t_staticparam set fvalue=cast(fvalue as int)-1 where fkey=?";
				for(String id:ids){
					delete(id);
					domainSqlDao.updateSQL(update, new Object[]{"neigborVersion"});
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
	public Neighborhoods get(Serializable id) throws BizException {
		
		return neighborSqlDao.get(id);
	}


	/**
	 * @param neighborhoods
	 * @throws BizException 
	 * @see com.youlb.biz.neighborhoods.INeighborhoodsBiz#saveOrUpdate(com.youlb.entity.neighborhoods.Neighborhoods)
	 */
	@Override
	public void saveOrUpdate(Neighborhoods neighborhoods,Operator loginUser) throws BizException {
		neighborhoods.setUseDate(DateHelper.strParseDate(neighborhoods.getUseDateStr(), "yyyy-mm-dd"));
		neighborhoods.setStartBuildDate(DateHelper.strParseDate(neighborhoods.getStartBuildDateStr(), "yyyy-mm-dd"));
		neighborhoods.setEndBuildDate(DateHelper.strParseDate(neighborhoods.getEndBuildDateStr(), "yyyy-mm-dd"));
		//防止前段恶意传参
		if(!"2".equals(neighborhoods.getCreateSipNum())){
			neighborhoods.setCreateSipNum("1");
		}
		//只有当id=null时会添加
		if(StringUtils.isBlank(neighborhoods.getId())){
			String neibId = (String) neighborSqlDao.add(neighborhoods);
			Domain domain = new Domain();
			domain.setEntityId(neibId);
			domain.setLayer(SysStatic.NEIGHBORHOODS);
			domain.setRemark(neighborhoods.getNeibName());
			domain.setParentId(neighborhoods.getParentId());
			String domainId = (String) domainBiz.save(domain);
			//判断是否创建sip账号
			if("2".equals(neighborhoods.getCreateSipNum())){
				createSipNum(neibId);
			}
			
			loginUser.getDomainIds().add(domainId);
			domainSqlDao.getCurrSession().flush();
			//域跟运营商绑定
			String sql ="insert into t_carrier_domain (fdomainid,fcarrierid) values(?,?)";
			domainSqlDao.executeSql(sql, new Object[]{domainId,loginUser.getCarrier().getId()});
			//更新社区版本参数
			String update ="update t_staticparam set fvalue=cast(fvalue as int)+1 where fkey=?";
			domainSqlDao.updateSQL(update, new Object[]{"neigborVersion"});
		}else{
			neighborSqlDao.update(neighborhoods);
			//更新域对象
			domainBiz.update(neighborhoods.getNeibName(),neighborhoods.getId());
			//如果是创建sip账号 判断是否已经存在
			if("2".equals(neighborhoods.getCreateSipNum())){
				String sql = "select user_sip from users where local_sip=?";
				List<Integer> list = domainSqlDao.pageFindBySql(sql, new Object[]{neighborhoods.getId()});
				if(list==null||list.isEmpty()){
					createSipNum(neighborhoods.getId());
				}
			//删除sip账号
			}else if("1".equals(neighborhoods.getCreateSipNum())){
				String delete ="delete from users where local_sip=?";
				domainSqlDao.executeSql(delete, new Object[]{neighborhoods.getId()});
			}
		}
	}
	
	/**
	 * 创建社区的sip账号
	 * @param domainId
	 * @throws BizException 
	 * @throws NumberFormatException 
	 */
	private void createSipNum(String neibId) throws NumberFormatException, BizException{
		Session session = domainSqlDao.getCurrSession();
		SQLQuery query = session.createSQLQuery("SELECT '1'||substring('00000000'||nextval('tbl_sipcount_seq'),length(currval('tbl_sipcount_seq')||'')) ");
	    List<String> list =  query.list();
	    String addSip ="insert into users (user_sip,user_password,local_sip,sip_type) values(?,?,?,?)";
	    //使用uuid为sip密码
	    String password = UUID.randomUUID().toString().replace("-", "");
		domainSqlDao.executeSql(addSip, new Object[]{Integer.parseInt(list.get(0)),password,neibId,"4"});//社区sip账号4
	}
	/**
	 * @return
	 * @see com.youlb.biz.neighborhoods.INeighborhoodsBiz#initArea()
	 
	@Override
	public List<Area> initArea() {
		// TODO Auto-generated method stub
		return null;
	}*/

//	/**获取社区基本信息
//	 * @return
//	 * @see com.youlb.biz.neighborhoods.INeighborhoodsBiz#getNeighborList()
//	 */
//	@Override
//	public List<Neighborhoods> getNeighborList() {
//		String hql="from Neighborhoods t order by t.createTime";
//		return neighborSqlDao.find(hql);
//	}
	/**通过neibId 获取areaId
	 * @param parentId
	 * @return
	 * @throws BizException 
	 * @see com.youlb.biz.building.IBuildingBiz#getAreaId(java.lang.String)
	 */
	@Override
	public String getAreaId(String parentId) throws BizException {
		String hql ="select t.areaId from Neighborhoods t where t.id=?";
		List<String> find = neighborSqlDao.find(hql, new Object[]{parentId});
		if(find!=null&&!find.isEmpty()){
			return find.get(0);
		}else{
			return null;
		}
	}

	/**通过areaId获取社区列表
	 * @param areaId
	 * @return
	 * @throws BizException 
	 * @see com.youlb.biz.neighborhoods.INeighborhoodsBiz#getNeiborListByAreaId(java.lang.String)
	 */
	@Override
	public List<Neighborhoods> getNeiborListByAreaId(String areaId) throws BizException {
		String hql = "from Neighborhoods t where t.areaId=? order by t.createTime";
		return neighborSqlDao.find(hql, new Object[]{areaId});
	}

	/**
	 * @param target
	 * @param loginUser
	 * @return
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#showList(java.io.Serializable, com.youlb.entity.privilege.Operator)
	 */
	@Override
	public List<Neighborhoods> showList(Neighborhoods target, Operator loginUser)throws BizException {
		 StringBuilder sb = new StringBuilder();
		 List<Object> values = new ArrayList<Object>();
		 sb.append("select * from (select n.id id,n.FNEIBNAME neibName,n.FNEIBNUM neibNum,n.FCONTRACTOR contractor," )
		 .append(" n.FADDRESS address,n.FSTARTBUILDDATE startBuildDate,n.FENDBUILDDATE endBuildDate,")
		 .append("n.FUSEDATE useDate,n.FTOTALAREA totalArea,n.FTOTALBUILDAREA totalBuildArea,n.FTOTALBUSSNISAREA totalBussnisArea,")
		 .append("n.FGREENINGRATE greeningRate,n.FPLOTRATIO plotRatio,n.FREMARK remark,n.fcreatetime createTime,u.user_sip sipNum ")
		 .append(" from t_neighborhoods n inner join t_domain d on d.fentityid = n.id left join users u on u.local_sip=n.id where d.fparentid=? ");
		 values.add(target.getParentId());
		 
		 if(StringUtils.isNotBlank(target.getNeibName())){
				sb.append("and n.FNEIBNAME like ?");
				values.add("%"+target.getNeibName()+"%");
			}
		 if(StringUtils.isNotBlank(target.getNeibNum())){
				sb.append("and n.FNEIBNUM like ?");
				values.add("%"+target.getNeibNum()+"%");
			}
		  List<String> domainIds = loginUser.getDomainIds();
			if(domainIds!=null&&!domainIds.isEmpty()){
				sb.append(SearchHelper.jointInSqlOrHql(domainIds,"d.id"));
				values.add(domainIds);
			}
		 sb.append(") t");
		 OrderHelperUtils.getOrder(sb, target, "t.", "t.createTime");
		 List<Object[]> listObj = neighborSqlDao.pageFindBySql(sb.toString(), values.toArray(), target.getPager());
		 List<Neighborhoods> neiborList = new ArrayList<Neighborhoods>();
			if(listObj!=null&&!listObj.isEmpty()){
				//设置分页
				Pager pager = target.getPager();
				for(Object[] obj:listObj){
					Neighborhoods neibor = new Neighborhoods();
					neibor.setId(obj[0]==null?"":(String)obj[0]);
					neibor.setNeibName(obj[1]==null?"":(String)obj[1]);
					neibor.setNeibNum(obj[2]==null?"":(String)obj[2]);
					neibor.setContractor(obj[3]==null?"":(String)obj[3]);
					neibor.setAddress(obj[4]==null?"":(String)obj[4]);
					neibor.setStartBuildDate(obj[5]==null?null:(Date)obj[5]);
					neibor.setEndBuildDate(obj[6]==null?null:(Date)obj[6]);
					neibor.setUseDate(obj[7]==null?null:(Date)obj[7]);
					neibor.setTotalArea(obj[8]==null?"":(String)obj[8]);
					neibor.setTotalBuildArea(obj[9]==null?"":(String)obj[9]);
					neibor.setTotalBussnisArea(obj[10]==null?"":(String)obj[10]);
					neibor.setGreeningRate(obj[11]==null?"":(String)obj[11]);
					neibor.setPlotRatio(obj[12]==null?"":(String)obj[12]);
					neibor.setRemark(obj[13]==null?"":(String)obj[13]);
					neibor.setCreateTime(obj[14]==null?null:(Date)obj[14]);
					neibor.setSipNum(obj[15]==null?null:(Integer)obj[15]+"");
					neibor.setPager(pager);
					neiborList.add(neibor);
				}
			}
		return neiborList;
	}

	/**
	 * @param loginUser
	 * @return
	 * @throws BizException 
	 * @see com.youlb.biz.houseInfo.INeighborhoodsBiz#getNeighborList(com.youlb.entity.privilege.Operator)
	 */
	@Override
	public List<Neighborhoods> getNeighborList(Operator loginUser) throws BizException {
		 StringBuilder sb = new StringBuilder();
		 sb.append("select * from (select n.id id,n.FNEIBNAME neibName,n.FNEIBNUM neibNum,n.FCONTRACTOR contractor," )
		 .append(" n.FADDRESS address,n.FSTARTBUILDDATE startBuildDate,n.FENDBUILDDATE endBuildDate,")
		 .append("n.FUSEDATE useDate,n.FTOTALAREA totalArea,n.FTOTALBUILDAREA totalBuildArea,n.FTOTALBUSSNISAREA totalBussnisArea,")
		 .append("n.FGREENINGRATE greeningRate,n.FPLOTRATIO plotRatio,n.FREMARK remark,n.fcreatetime createTime")
		 .append(" from t_neighborhoods n inner join t_domain d on d.fentityid = n.id ");
		  List<String> domainIds = loginUser.getDomainIds();
			if(domainIds!=null&&!domainIds.isEmpty()){
				sb.append(SearchHelper.jointInSqlOrHql(domainIds,"d.id"));
			}
		 sb.append(") t order by t.createTime ");
		 List<Object[]> listObj = neighborSqlDao.pageFindBySql(sb.toString(), new Object[]{domainIds});
		 List<Neighborhoods> neiborList = new ArrayList<Neighborhoods>();
			if(listObj!=null&&!listObj.isEmpty()){
				for(Object[] obj:listObj){
					Neighborhoods neibor = new Neighborhoods();
					neibor.setId(obj[0]==null?"":(String)obj[0]);
					neibor.setNeibName(obj[1]==null?"":(String)obj[1]);
					neibor.setNeibNum(obj[2]==null?"":(String)obj[2]);
					neibor.setContractor(obj[3]==null?"":(String)obj[3]);
					neibor.setAddress(obj[4]==null?"":(String)obj[4]);
					neibor.setStartBuildDate(obj[5]==null?null:(Date)obj[5]);
					neibor.setEndBuildDate(obj[6]==null?null:(Date)obj[6]);
					neibor.setUseDate(obj[7]==null?null:(Date)obj[7]);
					neibor.setTotalArea(obj[8]==null?"":(String)obj[8]);
					neibor.setTotalBuildArea(obj[9]==null?"":(String)obj[9]);
					neibor.setTotalBussnisArea(obj[10]==null?"":(String)obj[10]);
					neibor.setGreeningRate(obj[11]==null?"":(String)obj[11]);
					neibor.setPlotRatio(obj[12]==null?"":(String)obj[12]);
					neibor.setRemark(obj[13]==null?"":(String)obj[13]);
					neibor.setCreateTime(obj[14]==null?null:(Date)obj[14]);
					neiborList.add(neibor);
				}
			}
		return neiborList;
	}
	/**
	 * 判断是否有重复的编号
	 * @param neighborhoods
	 * @return
	 * @throws BizException 
	 * @see com.youlb.biz.houseInfo.INeighborhoodsBiz#checkNeighborNum(com.youlb.entity.houseInfo.Neighborhoods)
	 */
	@Override
	public boolean checkNeighborNum(Neighborhoods neighborhoods) throws BizException {
		 String sql = "SELECT n.fneibnum from t_domain d INNER JOIN t_neighborhoods n on n.id=d.fentityid where d.fparentid=? ";
		 List<String> list = neighborSqlDao.pageFindBySql(sql, new Object[]{neighborhoods.getParentId()});
		 if(list!=null&&!list.isEmpty()&&list.contains(neighborhoods.getNeibNum())){
			 return true;
		 }
		return false;
	}

	/**根据所属小区查询社区列表
	 * @param loginUser
	 * @return
	 * @see com.youlb.biz.neighborhoods.INeighborhoodsBiz#getNeighborList(com.youlb.entity.manageUser.ManageUser)
	
	@Override
	public List<Neighborhoods> getNeighborList(ManageUser loginUser) {
		StringBuilder sb = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		sb.append("from Neighborhoods t where 1=1 ");
		//如果是超级管理员不过滤
		if(loginUser!=null&&SysStatic.ADMIN.equals(loginUser.getLoginName())){
//			 
		}else{
			sb.append(" and t.id = ?");
			values.add(loginUser.getNeibId());
		}
		sb.append(" order by t.createTime");
		
		return neighborSqlDao.find(sb.toString(), values.toArray());
	} */
}
