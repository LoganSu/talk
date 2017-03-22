package com.youlb.biz.houseInfo.impl;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youlb.biz.houseInfo.IAreaBiz;
import com.youlb.biz.houseInfo.IDomainBiz;
import com.youlb.dao.common.BaseDaoBySql;
import com.youlb.entity.common.Domain;
import com.youlb.entity.houseInfo.Address;
import com.youlb.entity.houseInfo.Area;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.common.SysStatic;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.exception.JsonException;
import com.youlb.utils.helper.OrderHelperUtils;
import com.youlb.utils.helper.SearchHelper;

/** 
 * @ClassName: AreaBizImpl.java 
 * @Description: 区域信息实现类 
 * @author: Pengjy
 * @date: 2015年8月31日
 * 
 */
@Service("areaBiz")
public class AreaBizImpl implements IAreaBiz {
     @Autowired
	private BaseDaoBySql<Area> areaSqlDao;
     @Autowired
 	private BaseDaoBySql<Domain> domainSqlDao;
 	public void setDomainSqlDao(BaseDaoBySql<Domain> domainSqlDao) {
 		this.domainSqlDao = domainSqlDao;
 	}
	public void setAreaSqlDao(BaseDaoBySql<Area> areaSqlDao) {
		this.areaSqlDao = areaSqlDao;
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
	public String save(Area target) throws BizException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param target
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#update(java.io.Serializable)
	 */
	@Override
	public void update(Area target) throws BizException {
		// TODO Auto-generated method stub

	}

	/**
	 * @param id
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Serializable id) throws BizException {
		areaSqlDao.delete(id);
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
		if(ids!=null){
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
	public Area get(Serializable id) throws BizException {
		Area a = areaSqlDao.get(id);
		//获取parentid
		String sql = "select d.fcreate_sip_num from t_domain d  where d.fentityid=?";
		List<String> list = areaSqlDao.pageFindBySql(sql, new Object[]{id});
		if(list!=null&&!list.isEmpty()){
			a.setCreateSipNum(list.get(0));
		}
		return a;
	}

	/**
	 * @param target
	 * @param loginUser
	 * @return
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#showList(java.io.Serializable, com.youlb.entity.privilege.Operator)
	 */
	@Override
	public List<Area> showList(Area target, Operator loginUser)throws BizException {
		List<Area> list = new ArrayList<Area>();
		StringBuilder sb =new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		sb.append("select * from (select a.FPROVINCE province,a.fcity city,a.FAREANUM areaNum,a.FREMARK remark,a.id id,a.FCREATETIME createTime,")
		.append(" d.fcreate_sip_num createSipNum,u.user_sip sipNum,u.user_password sipNumPsw ")
		.append(" from t_area a inner join t_domain d on d.fentityid=a.id left join users u on u.local_sip=d.id where 1=1 ");
		if(StringUtils.isNotBlank(target.getProvince())){
			sb.append("and a.fprovince like ?");
			values.add("%"+target.getProvince()+"%");
		}
		if(StringUtils.isNotBlank(target.getCity())){
			sb.append("and a.fcity like ?");
			values.add("%"+target.getCity()+"%");
		}
		if(StringUtils.isNotBlank(target.getAreaNum())){
			sb.append("and a.fareanum like ?");
			values.add("%"+target.getAreaNum()+"%");
		}
		
		//拼接in 语句查询参数
		List<String> domainIds = loginUser.getDomainIds();
		if(domainIds!=null&&!domainIds.isEmpty()){
			sb.append(SearchHelper.jointInSqlOrHql(domainIds,"d.id"));
			values.add(domainIds);
		}
		sb.append(") t");
		OrderHelperUtils.getOrder(sb, target, "t.", "t.createTime");
		List<Object[]> listObj= areaSqlDao.pageFindBySql(sb.toString(),values.toArray(), target.getPager());
		if(listObj!=null&&!listObj.isEmpty()){
			for(Object[] obj:listObj){
				Area area = new Area();
				area.setProvince(obj[0]==null?"":(String)obj[0]);
				area.setCity(obj[1]==null?"":(String)obj[1]);
				area.setAreaNum(obj[2]==null?"":(String)obj[2]);
				area.setRemark(obj[3]==null?"":(String)obj[3]);
				area.setId(obj[4]==null?"":(String)obj[4]);
				if("2".equals(obj[6])){
					area.setSipNum(obj[7]==null?null:(Integer)obj[7]+"");
					area.setSipNumPsw(obj[8]==null?"":(String)obj[8]);
				}
				area.setPager(target.getPager());
				list.add(area);
			}
		}
		return list;
	}
	 
	/**
	 * @param area
	 * @throws JsonException 
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws UnsupportedEncodingException 
	 * @see com.youlb.biz.houseInfo.IAreaBiz#saveOrUpdate(com.youlb.entity.houseInfo.Area)
	 */
	@Override
	public void saveOrUpdate(Area area,Operator loginUser) throws BizException, UnsupportedEncodingException, ClientProtocolException, IOException, JsonException{
		//只有当id=null时会添加
		if(StringUtils.isBlank(area.getId())){
			String areaId = (String) areaSqlDao.add(area);
			//添加到域
			Domain domain = new Domain();
			domain.setEntityId(areaId);
			domain.setParentId(SysStatic.HOUSEINFO);//房产信息域 TODO 需要考虑怎么初始化数据
			domain.setLayer(SysStatic.AREA);//区域级
			domain.setRemark(area.getProvince()+area.getCity());//备注
			//新添加地区没有社区所有不需要设置
//			if("2".equals(area.getCreateSipNum())){
//				domain.setCreateSipNum("2");
//			}
			String domainId = (String) domainBiz.save(domain);
			domainSqlDao.getCurrSession().flush();
			//域跟运营商绑定
			String sql ="insert into t_carrier_domain (fdomainid,fcarrierid) values(?,?)";
			
			domainSqlDao.executeSql(sql, new Object[]{domainId,loginUser.getCarrier().getId()});
			//添加到用户域集合
			loginUser.getDomainIds().add(domainId);
//			List<String> neiborNames = domainBiz.getNeiborNames(areaId);
//			if(neiborNames!=null&&!neiborNames.isEmpty()){
//				//创建sip
//				domainBiz.createSip(domainId, neiborNames.get(0));
//			}
		}else{
			areaSqlDao.update(area);
			
			//老数据没有sip账号需要补上
			if("2".equals(area.getCreateSipNum())){
				//查询是否有sip账号
				List<Object[]> list = domainBiz.getDomainIdAndSipByEntityId(area.getId());
				if(list!=null&&!list.isEmpty()){
					if(list.get(0)[1]==null){
						List<String> neiborNames = domainBiz.getNeiborNames(area.getId());
						if(neiborNames!=null&&!neiborNames.isEmpty()){
						   //创建sip
						  domainBiz.createSip((String)list.get(0)[0], neiborNames.get(0));
						}else{
							//下面没有社区不能启用网关
							area.setCreateSipNum(null);
						}
					}
				}
			}
			//更新与对象
			domainBiz.update(area.getProvince()+area.getCity(),area.getCreateSipNum(),area.getId());
		}
		
	}

	/**
	 * @param province
	 * @return
	 * @see com.youlb.biz.houseInfo.IAreaBiz#getAreaList(java.lang.String)
	 */
	@Override
	public List<Area> getAreaList(String province) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param areaId
	 * @return
	 * @see com.youlb.biz.houseInfo.IAreaBiz#getProvinceList(java.lang.String)
	 */
	@Override
	public List<Area> getProvinceList(String areaId) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return
	 * @see com.youlb.biz.houseInfo.IAreaBiz#getAreaList()
	 */
	@Override
	public List<Area> getAreaList() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param neibId
	 * @return
	 * @see com.youlb.biz.houseInfo.IAreaBiz#getAreaByNeibId(java.lang.String)
	 */
	@Override
	public Area getAreaByNeibId(String neibId) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 通过父id获取地址
	 * @param parentId
	 * @return
	 * @throws BizException 
	 * @see com.youlb.biz.houseInfo.IAreaBiz#getAddressByParentId(java.lang.String)
	 */
	@Override
	public List<Address> getAddressByParentId(String parentId) throws BizException {
		 String sql = "select id, fshortname from t_address where fparentid=? ";
		 //城市级
		 if(StringUtils.isBlank(parentId)){
			 parentId="0";
		 }
		 List<Object[]> listObj = areaSqlDao.pageFindBySql(sql, new Object[]{parentId});
		 List<Address> list = new ArrayList<Address>();
		 if(listObj!=null&&!listObj.isEmpty()){
			 for(Object[] obj:listObj){
				 Address address = new Address();
				 address.setId(obj[0]==null?"":(String)obj[0]);
				 address.setFshortname(obj[1]==null?"":(String)obj[1]);
				 list.add(address);
			 }
		 }
		return list;
	}
	/**
	 * 获取社区编号
	 * @param city
	 * @return
	 * @throws BizException 
	 * @see com.youlb.biz.houseInfo.IAreaBiz#getAreaCode(java.lang.String)
	 */
	@Override
	public String getAreaCode(String city) throws BizException {
		String sql = "select num from t_areacode where address=?";
		List<String> list = areaSqlDao.pageFindBySql(sql, new Object[]{city});
		if(list!=null&&!list.isEmpty()){
			String areaCode = list.get(0);
			if(areaCode.length()>3){
				areaCode=areaCode.substring(1);
			}
			return areaCode;
		}
		return null;
	}
	
	@Override
	public List<Area> getAreaList(Area area) throws BizException {
		 StringBuilder sb = new StringBuilder("from Area where province=? and city=? and areaNum=? ");
		 List<Object> values = new ArrayList<Object>();
			values.add(area.getProvince());
			values.add(area.getCity());
			values.add(area.getAreaNum());
		    //更新判断排除自己
			if(StringUtils.isNotBlank(area.getId())){
				sb.append(" and id != ?");
				values.add(area.getId());
			}
		return  areaSqlDao.find(sb.toString(), values.toArray());
	}
	/**
	 * 查询编号是否存在
	 * @param areaNum
	 * @return
	 * @throws BizException 
	 * @see com.youlb.biz.houseInfo.IAreaBiz#checkAreaNum(java.lang.String)
	 */
	@Override
	public String checkAreaNum(String areaNum) throws BizException {
		String sql = "select case when length(num)>3 then substr(num, 2,4) else num end  from t_areacode where num like ?";
		List<String> list = areaSqlDao.pageFindBySql(sql, new Object[]{"%"+areaNum});
		if(list!=null&&!list.isEmpty()){
			String areaCode = list.get(0);
			if(areaCode.length()>3){
				areaCode=areaCode.substring(1);
			}
			return areaCode;
		}
		return null;
	}
	@Override
	public boolean checkAreaNumFromArea(Area area) throws BizException {
		 StringBuilder sb = new StringBuilder("select areaNum from Area where areaNum=? ");
		 List<Object> values = new ArrayList<Object>();
		 values.add(area.getAreaNum());
		//更新判断排除自己
		if(StringUtils.isNotBlank(area.getId())){
			sb.append(" and id != ?");
			values.add(area.getId());
		}
		List<Object[]> listObj = areaSqlDao.find(sb.toString(), values.toArray());
		if(listObj!=null&&!listObj.isEmpty()){
			return true;
		}
		return false;
	}

}
