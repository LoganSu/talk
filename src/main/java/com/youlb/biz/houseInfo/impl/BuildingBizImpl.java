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

import com.youlb.biz.houseInfo.IBuildingBiz;
import com.youlb.biz.houseInfo.IDomainBiz;
import com.youlb.dao.common.BaseDaoBySql;
import com.youlb.entity.common.Domain;
import com.youlb.entity.common.Pager;
import com.youlb.entity.houseInfo.Building;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.common.SysStatic;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.exception.JsonException;
import com.youlb.utils.helper.OrderHelperUtils;
import com.youlb.utils.helper.SearchHelper;

/** 
 * @ClassName: BuildingBizImpl.java 
 * @Description: 楼栋业务实现类
 * @author: Pengjy
 * @date: 2015年6月29日
 * 
 */
@Service
@Component("buildingBiz")
public class BuildingBizImpl implements IBuildingBiz {
    
	
	
	@Autowired
	private BaseDaoBySql<Building> buildingSqlDao;
	@Autowired
	private BaseDaoBySql<Domain> domainSqlDao;
	public void setDomainSqlDao(BaseDaoBySql<Domain> domainSqlDao) {
		this.domainSqlDao = domainSqlDao;
	}
	public void setBuildingSqlDao(BaseDaoBySql<Building> buildingSqlDao) {
		this.buildingSqlDao = buildingSqlDao;
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
	public String save(Building target) throws BizException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param target
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#update(java.io.Serializable)
	 */
	@Override
	public void update(Building target) throws BizException {
		// TODO Auto-generated method stub

	}

	/**
	 * @param id
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Serializable id) throws BizException {
		buildingSqlDao.delete(id);
		//删除domain里面的数据
		domainBiz.deleteByEntityId(id);
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
	public Building get(Serializable id) throws BizException {
		Building b =buildingSqlDao.get(id);
		//获取parentid
		String sql = "select d.fparentid,d.fcreate_sip_num from t_domain d  where d.fentityid=?";
		List<Object[]> list = buildingSqlDao.pageFindBySql(sql, new Object[]{id});
		if(list!=null&&!list.isEmpty()){
			b.setParentId(list.get(0)[0]==null?"":(String)list.get(0)[0]);
			b.setCreateSipNum(list.get(0)[1]==null?"":(String)list.get(0)[1]);
		}
		return b;
	}

	/**
	 * @param building
	 * @throws BizException 
	 * @throws JsonException 
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws UnsupportedEncodingException 
	 * @see com.youlb.biz.building.IBuildingBiz#saveOrUpdate(com.youlb.entity.area.Area)
	 */
	@Override
	public void saveOrUpdate(Building building,Operator loginUser) throws BizException, UnsupportedEncodingException, ClientProtocolException, IOException, JsonException {
		//add
		if(StringUtils.isBlank(building.getId())){
			String buildId = (String) buildingSqlDao.add(building);
			Domain domain = new Domain();
			domain.setEntityId(buildId);
			domain.setLayer(SysStatic.BUILDING);//社区层
			domain.setRemark(building.getBuildingName());
			domain.setParentId(building.getParentId());//domain的parentId
			//防止前段恶意传参
			if("2".equals(building.getCreateSipNum())){
				domain.setCreateSipNum("2");
			}
			String domainId = (String) domainBiz.save(domain);
			loginUser.getDomainIds().add(domainId);
			domainSqlDao.getCurrSession().flush();
			//域跟运营商绑定
			String sql ="insert into t_carrier_domain (fdomainid,fcarrierid) values(?,?)";
			domainSqlDao.executeSql(sql, new Object[]{domainId,loginUser.getCarrier().getId()});
			//创建sip
			domainBiz.createSip(domainId, domainBiz.getNeiborName(buildId));
		}else{
			buildingSqlDao.update(building);
			//更新与对象
			domainBiz.update(building.getBuildingName(),building.getCreateSipNum(),building.getId());
			//老数据没有sip账号需要补上
			if("2".equals(building.getCreateSipNum())){
				//查询是否有sip账号
				List<Object[]> list = domainBiz.getDomainIdAndSipByEntityId(building.getId());
				if(list!=null&&!list.isEmpty()){
					if(list.get(0)[1]==null){
						//创建sip
						domainBiz.createSip((String)list.get(0)[0], domainBiz.getNeiborName(building.getId()));
					}
				}
			}
		}
	}

	/**通过buildId获取neibId
	 * @param parentId
	 * @return
	 * @throws BizException 
	 * @see com.youlb.biz.building.IBuildingBiz#getBuildId(java.lang.String)
	 */
	@Override
	public String getNeibId(String parentId) throws BizException {
		String hql ="select t.neibId from Building t where t.id=?";
		List<String> find = buildingSqlDao.find(hql, new Object[]{parentId});
		if(find!=null&&!find.isEmpty()){
			return find.get(0);
		}else{
			return null;
		}
	}

	/**通过neibId获取楼栋列表
	 * @param neibId
	 * @return
	 * @throws BizException 
	 * @see com.youlb.biz.building.IBuildingBiz#getBuildingListByNeibId(java.lang.String)
	 */
	@Override
	public List<Building> getBuildingListByNeibId(String neibId) throws BizException {
		List<Building> list = new ArrayList<Building>();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT b.id,b.fbuildingnum,b.fbuildingname,b.ftotalFloor,b.fbuildheight,b.fbuildtype,b.fremark,b.fcreatetime ")
		.append(" d.fcreate_sip_num createSipNum,u.user_sip sipNum,u.user_password sipNumPsw ")
		.append("from t_building b INNER JOIN t_domain sd on sd.fentityid=b.id left join users u on u.local_sip=b.id  where sd.fparentid=")
		.append("(SELECT d.id FROM t_domain d where d.fentityid=?) order by b.fcreatetime ");
		List<Object[]> listObj = buildingSqlDao.pageFindBySql(sb.toString(), new Object[]{neibId});
		if(listObj!=null&&!listObj.isEmpty()){
			//设置分页
			 for(Object[] obj:listObj){
				 Building building = new Building();
					building.setId(obj[0]==null?"":(String)obj[0]);
					building.setBuildingNum(obj[1]==null?"":(String)obj[1]);
					building.setBuildingName(obj[2]==null?"":(String)obj[2]);
					building.setTotalFloor(obj[3]==null?"":(String)obj[3]);
					building.setBuildHeight(obj[4]==null?"":(String)obj[4]);
					building.setBuildType(obj[5]==null?"":(String)obj[5]);
					building.setRemark(obj[6]==null?"":(String)obj[6]);
					if("2".equals(obj[8])){
						building.setSipNum(obj[9]==null?null:(Integer)obj[9]+"");
					}
					building.setSipNumPsw(obj[10]==null?"":(String)obj[10]);
					list.add(building);
			 }
		 }
		return list;
	}

	/**
	 * @param target
	 * @param loginUser
	 * @return
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#showList(java.io.Serializable, com.youlb.entity.privilege.Operator)
	 */
	@Override
	public List<Building> showList(Building target, Operator loginUser)throws BizException {
		 List<Building> list = new ArrayList<Building>();
		 StringBuilder sb = new StringBuilder();
		 List<Object> values = new ArrayList<Object>();
		 sb.append("select * from (select b.id id,b.fbuildingnum buildingNum,b.fbuildingname buildingName,b.ftotalFloor totalfloor," )
		 .append(" b.fbuildheight buildHeight,b.fbuildtype buildType,b.fremark remark,b.fcreatetime createTime,")
		 .append(" d.fcreate_sip_num createSipNum,u.user_sip sipNum,u.user_password sipNumPsw ")
		 .append(" from t_building b inner join t_domain d on d.fentityid = b.id left join users u on u.local_sip=d.id where d.fparentid=? ");
		 values.add(target.getParentId());
		  List<String> domainIds = loginUser.getDomainIds();
			if(domainIds!=null&&!domainIds.isEmpty()){
				sb.append(SearchHelper.jointInSqlOrHql(domainIds,"d.id"));
				values.add(domainIds);
			}
			 if(StringUtils.isNotBlank(target.getBuildingName())){
					sb.append("and b.fbuildingname like ?");
					values.add("%"+target.getBuildingName()+"%");
			}
			 if(StringUtils.isNotBlank(target.getBuildingNum())){
					sb.append("and b.fbuildingnum like ?");
					values.add("%"+target.getBuildingNum()+"%");
				}
		 sb.append(") t");
		 OrderHelperUtils.getOrder(sb, target, "t.", "t.createTime");
		 List<Object[]> listObj = buildingSqlDao.pageFindBySql(sb.toString(), values.toArray(), target.getPager());
		 if(listObj!=null&&!listObj.isEmpty()){
			//设置分页
			Pager pager = target.getPager();
			 for(Object[] obj:listObj){
				 Building building = new Building();
					building.setId(obj[0]==null?"":(String)obj[0]);
					building.setBuildingNum(obj[1]==null?"":(String)obj[1]);
					building.setBuildingName(obj[2]==null?"":(String)obj[2]);
					building.setTotalFloor(obj[3]==null?"":(String)obj[3]);
					building.setBuildHeight(obj[4]==null?"":(String)obj[4]);
					building.setBuildType(obj[5]==null?"":(String)obj[5]);
					building.setRemark(obj[6]==null?"":(String)obj[6]);
					if("2".equals(obj[8])){
						building.setSipNum(obj[9]==null?null:(Integer)obj[9]+"");
						building.setSipNumPsw(obj[10]==null?"":(String)obj[10]);
					}
					building.setPager(pager);
					list.add(building);
			 }
		 }
		 return list;
	}
	@Override
	public boolean checkBuildingName(Building building) throws BizException {
		List<Object> values = new ArrayList<Object>();
		 StringBuilder sb = new StringBuilder("SELECT n.fbuildingname from t_building n INNER JOIN t_domain d on n.id=d.fentityid where d.fparentid=? ");
		 values.add(building.getParentId());
		 if(StringUtils.isNotBlank(building.getId())){
			 sb.append(" and n.id!=? ");
			 values.add(building.getId());
		 }
		 List<String> list = buildingSqlDao.pageFindBySql(sb.toString(), values.toArray());
		 if(list!=null&&!list.isEmpty()&&list.contains(building.getBuildingName())){
			 return true;
		 }
		return false;
	}
	@Override
	public boolean checkBuildingNum(Building building) throws BizException {
		List<Object> values = new ArrayList<Object>();
		 StringBuilder sb = new StringBuilder("SELECT n.fbuildingnum from t_domain d INNER JOIN t_building n on n.id=d.fentityid where d.fparentid=? ");
		 values.add(building.getParentId());
		 if(StringUtils.isNotBlank(building.getId())){
			 sb.append(" and n.id!=? ");
			 values.add(building.getId());
		 }
		 List<String> list = buildingSqlDao.pageFindBySql(sb.toString(), values.toArray());
		 if(list!=null&&!list.isEmpty()&&list.contains(building.getBuildingNum())){
			 return true;
		 }
		return false;
	}


}
