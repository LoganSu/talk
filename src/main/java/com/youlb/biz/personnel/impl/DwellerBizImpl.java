package com.youlb.biz.personnel.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youlb.biz.personnel.IDwellerBiz;
import com.youlb.dao.common.BaseDaoBySql;
import com.youlb.entity.personnel.Dweller;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.common.SysStatic;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.helper.OrderHelperUtils;
import com.youlb.utils.helper.SearchHelper;

/** 
 * @ClassName: HostInfoBiz.java 
 * @Description: 住户信息管理业务实现类 
 * @author: Pengjy
 * @date: 2015-10-26
 * 
 */
@Service("dwellerBiz")
public class DwellerBizImpl implements IDwellerBiz {
    
	@Autowired
    private BaseDaoBySql<Dweller> dwellerSqlDao;
	public void setDwellerSqlDao(BaseDaoBySql<Dweller> dwellerSqlDao) {
		this.dwellerSqlDao = dwellerSqlDao;
	}

	/**
	 * @param target
	 * @return
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#save(java.io.Serializable)
	 */
	@Override
	public String save(Dweller dweller) throws BizException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param target
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#update(java.io.Serializable)
	 */
	@Override
	public void update(Dweller dweller) throws BizException {
		String insert = "insert into t_domain_dweller(fdomainid,fdwellerid,fdwellertype) values (?,?,?)";
		dwellerSqlDao.update(dweller);
		//把被叫手机号码置null
		updateCallednumberById(dweller.getId());
		if(dweller.getTreecheckbox()!=null&&!dweller.getTreecheckbox().isEmpty()){
			//先删除旧纪录
			String delete = "delete from t_domain_dweller where fdwellerid=?";
			dwellerSqlDao.executeSql(delete, new Object[]{dweller.getId()});
			dwellerSqlDao.getCurrSession().flush();
		   for(String domainid:dweller.getTreecheckbox()){
				String find="select d.id from t_domain_dweller dd inner join t_domain d on d.id=dd.fdomainid where d.id=? and dd.fdwellertype='1'";
				List<String> list = dwellerSqlDao.pageFindBySql(find, new Object[]{domainid});
				//说明该房屋已经绑定人的信息 此人是非户主
				if(list!=null&&!list.isEmpty()){
					dwellerSqlDao.executeSql(insert, new Object[]{domainid,dweller.getId(),"0"});//非户主
				}else{
					dwellerSqlDao.executeSql(insert, new Object[]{domainid,dweller.getId(),"1"});//户主
					//设置房间的被叫号码默认是户主
					String updateCallNum= "update t_room set fcallednumber=? where id=(select d.fentityid from t_domain d where d.id=?)";
					dwellerSqlDao.executeSql(updateCallNum, new Object[]{dweller.getPhone(),domainid});
				}
			}
		}
	}


	/**
	 * @param id
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Serializable id) throws BizException {
		dwellerSqlDao.delete(id);
		//人被删除把被叫号码置null
		updateCallednumberById((String)id);
	}
	/**
	 * 把被叫号码置null
	 * @param dwellerId
	 */
	  private void updateCallednumberById(String dwellerId){
		  String updateCallNum= "update t_room set fcallednumber=null where id in (SELECT d.fentityid from t_domain_dweller tdd INNER JOIN t_domain d on d.id=tdd.fdomainid  where tdd.fdwellerid=? )";
		  int i=dwellerSqlDao.updateSQL(updateCallNum, new Object[]{dwellerId});
//		  System.out.println(i);
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
	public Dweller get(Serializable id) throws BizException {
		Dweller dweller = dwellerSqlDao.get(id);
		String hql = "select dd.fdomainid from t_domain_dweller dd inner join t_domain d on d.id=dd.fdomainid where dd.fdwellerid=?";
		List<String> domainids = dwellerSqlDao.pageFindBySql(hql, new Object[]{id});
		dweller.setTreecheckbox(domainids);
		return dweller;
	}
 
	/**
	 * @param target
	 * @param loginUser
	 * @return
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#showList(java.io.Serializable, com.youlb.entity.privilege.Operator)
	 */
	@Override
	public List<Dweller> showList(Dweller target, Operator loginUser)throws BizException {
		StringBuilder sb = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		sb.append("select * from (select w.id id,w.fname fname,w.fsex sex,w.fidnum idNum,w.fphone phone,w.femail email,w.feducation education,")
		.append("w.fnativeplace nativePlace,w.fcompanyname companyName,w.fremark remark,w.fcreatetime createTime,dd.fdwellertype dwellerType,d.fentityid entityid")
		.append(" from t_dweller w left join t_domain_dweller dd on dd.fdwellerid=w.id left join t_domain d on d.id=dd.fdomainid where 1=1 ");
		//不是特殊管理员需要过滤域
//		List<String> domainIds = loginUser.getDomainIds();
		if(!SysStatic.SPECIALADMIN.equals(loginUser.getIsAdmin())){
			sb.append(" and w.fcarrier_id=? ");
			values.add(loginUser.getCarrier().getId());
		}
		sb.append(")t where t.idNum is not null ");//不显示子账号，子账号没有身份证号码
		if(StringUtils.isNotBlank(target.getFname())){
			sb.append(" and t.fname like ?");
			values.add("%"+target.getFname()+"%");
		}
		if(StringUtils.isNotBlank(target.getPhone())){
			sb.append(" and t.phone like ?");
			values.add("%"+target.getPhone()+"%");
		}
		if(StringUtils.isNotBlank(target.getIdNum())){
			sb.append(" and t.idNum like ?");
			values.add("%"+target.getIdNum()+"%");
		}
		 sb.append(" group by t.id,t.fname,t.sex,t.idNum,t.phone,t.email,t.education,t.nativePlace,t.companyName,t.remark,t.createTime,t.dwellerType,t.entityid");
		 OrderHelperUtils.getOrder(sb, target, "t.", "t.createTime");
		List<Object[]> objList = dwellerSqlDao.pageFindBySql(sb.toString(), values.toArray(), target.getPager());
		List<Dweller> list = new ArrayList<Dweller>();
		if(objList!=null&&!objList.isEmpty()){
			for(Object[] obj:objList){
				Dweller dweller = new Dweller();
				dweller.setPager(target.getPager());
				dweller.setId(obj[0]==null?"":(String)obj[0]);
				dweller.setFname(obj[1]==null?"":(String)obj[1]);
				dweller.setSex(obj[2]==null?"":(String)obj[2]);
				dweller.setIdNum(obj[3]==null?"":(String)obj[3]);
				dweller.setPhone(obj[4]==null?"":(String)obj[4]);
				dweller.setEmail(obj[5]==null?"":(String)obj[5]);
				dweller.setEducation(obj[6]==null?"":(String)obj[6]);
				dweller.setNativePlace(obj[7]==null?"":(String)obj[7]);
                dweller.setCompanyName(obj[8]==null?"":(String)obj[8]);
                dweller.setRemark(obj[9]==null?"":(String)obj[9]);
//                dweller.setCardCount(obj[10]==null?0:(Integer)obj[10]);
                dweller.setDwellerType(obj[11]==null?"":(String)obj[11]);
                if(obj[12]!=null){
                	//获取地址
                	String address = findAddressByRoomId((String)obj[12]);
                	dweller.setAddress(address);
                }
                list.add(dweller);
			}
		}
		return list;
	}
	
	/**获取地址信息
	 * @param cardInfo
	 * @return
	 * @see com.youlb.biz.access.IPermissionBiz#findAddress(com.youlb.entity.access.CardInfo)
	 */
	private String findAddressByRoomId(String roomId) {
		StringBuffer sb = new StringBuffer();
		sb.append("select array_to_string (ARRAY(WITH RECURSIVE r AS (SELECT * FROM t_domain WHERE fentityid = ?")
		.append(" union ALL SELECT t_domain.* FROM t_domain, r WHERE t_domain.id = r.fparentid) SELECT  fremark FROM r where flayer is not null ORDER BY flayer),'')");
		 List<String> listObj = dwellerSqlDao.pageFindBySql(sb.toString(), new Object[]{roomId});
		 if(listObj!=null&&!listObj.isEmpty()){
			 return listObj.get(0);
		 }
		return null;
	}

	/**
	 * @param hostInfo
	 * @see com.youlb.biz.personnel.IDwellerBiz#saveOrUpdate(com.youlb.entity.personnel.HostInfo)
	 */
	@Override
	public void saveOrUpdate(Dweller dweller,Operator loginUser) {
//		String insert = "insert into t_domain_dweller(fdomainid,fdwellerid,fdwellertype) values ((select d.id from t_domain d where d.fentityid=?),?,?)";
		String insert = "insert into t_domain_dweller(fdomainid,fdwellerid,fdwellertype) values (?,?,?)";
		
		if(StringUtils.isBlank(dweller.getId())){
			//检查手机号码是否已经注册
//			String id = checkPhoneExist(dweller.getPhone(),loginUser);
//			if(StringUtils.isNotBlank(id)){
//				dweller.setId(id);
//				//已经注册就更新记录
//				dwellerSqlDao.update(dweller);
//			}else{
				dweller.setId(null);
				String dwellerId = (String) dwellerSqlDao.add(dweller);
				dwellerSqlDao.getCurrSession().flush();
				if(dweller.getTreecheckbox()!=null){
					for(String domainid:dweller.getTreecheckbox()){
						String find="select d.id from t_domain_dweller dd inner join t_domain d on d.id=dd.fdomainid where d.id=? and dd.fdwellertype='1'";
						List<String> list = dwellerSqlDao.pageFindBySql(find, new Object[]{domainid});
						//说明该房屋已经绑定人的信息 此人是非户主
						if(list!=null&&!list.isEmpty()){
							dwellerSqlDao.executeSql(insert, new Object[]{domainid,dwellerId,"0"});//非户主
						}else{
							dwellerSqlDao.executeSql(insert, new Object[]{domainid,dwellerId,"1"});//户主
							//设置房间的被叫号码默认是户主
							String updateCallNum= "update t_room set fcallednumber=? where id=(select d.fentityid from t_domain d where d.id=?)";
							dwellerSqlDao.executeSql(updateCallNum, new Object[]{dweller.getPhone(),domainid});
						}
					}
				}
//			}
			
		}else{
			update(dweller);
//			dwellerSqlDao.update(dweller);
//			//把被叫手机号码置null
//			updateCallednumberById(dweller.getId());
//			//先删除旧纪录
//			String delete = "delete from t_domain_dweller where fdwellerid=?";
//			dwellerSqlDao.executeSql(delete, new Object[]{dweller.getId()});
//			dwellerSqlDao.getCurrSession().flush();
//			if(dweller.getTreecheckbox()!=null){
//			  for(String domainid:dweller.getTreecheckbox()){
//					String find="select d.id from t_domain_dweller dd inner join t_domain d on d.id=dd.fdomainid where d.id=? and dd.fdwellertype='1'";
//					List<String> list = dwellerSqlDao.pageFindBySql(find, new Object[]{domainid});
//					//说明该房屋已经绑定人的信息 此人是非户主
//					if(list!=null&&!list.isEmpty()){
//						dwellerSqlDao.executeSql(insert, new Object[]{domainid,dweller.getId(),"0"});//非户主
//					}else{
//						dwellerSqlDao.executeSql(insert, new Object[]{domainid,dweller.getId(),"1"});//户主
//						//设置房间的被叫号码默认是户主
//						String updateCallNum= "update t_room set fcallednumber=? where id=(select d.id from t_domain d where d.id=?)";
//						dwellerSqlDao.executeSql(updateCallNum, new Object[]{dweller.getPhone(),domainid});
//					}
//				}
//			}
		}
		
		
	}

	/**判断房子是否已经被别人选择过
	 * @param dweller
	 * @return
	 * @see com.youlb.biz.personnel.IDwellerBiz#checkRoomChoosed(com.youlb.entity.personnel.Dweller)
	 */
	@Override
	public String checkRoomChoosed(Dweller dweller) {
//		检查房间是否已经被绑定，返回已经绑定的房间id
//		String entityId = checkDetail(dweller);
//		if(StringUtils.isNotBlank(entityId)){
//			String sql = "select t.froomnum from t_room t where t.id=?";
//			List<String> list = dwellerSqlDao.pageFindBySql(sql, new Object[]{entityId});
//			return list.get(0);
//		}
		
		
		return "";
	}

	/**
	 * @param dweller
	 * @return
	 */
	private String checkDetail(Dweller dweller) {
		List<String> entityIds = dweller.getTreecheckbox();
//		StringBuilder sb = new StringBuilder();
//		sb.append("select * from t_domain_dweller t where t.fdomainin=?");
		//新增时只有中间表有纪录说明已经被选择
		 if(StringUtils.isBlank(dweller.getId())){
			 String sql = "select t.fdomainid,t.fdwellerid from t_domain_dweller t where t.fdomainid=(select d.id from t_domain d where d.fentityid=?)";
			 if(entityIds!=null){
				 for(String entityId:entityIds){
					 List<Object[]> list = dwellerSqlDao.pageFindBySql(sql, new Object[]{entityId});
					 if(list!=null&&!list.isEmpty()){
						 return entityId;
					 }
				 }
			 }
		 //修改 1：未修改域，2：修改域
		 }else{
//			 String sql = "select * from t_domain_dweller t where t.fdomainin=? and t.fdwellerid=?";
//			 List<Object[]> list = dwellerSqlDao.pageFindBySql(sql, new Object[]{dweller.getTreecheckbox().get(0),dweller.getId()});
			 //没有找到纪录 说明不是添加原来的域 做了修改
//			 if(list==null||list.isEmpty()){
				 String sql1 = "select t.fdomainid,t.fdwellerid from t_domain_dweller t where t.fdomainid=(select d.id from t_domain d where d.fentityid=?)";
				 if(entityIds!=null){
					 for(String entityId:entityIds){
						 List<Object[]> list = dwellerSqlDao.pageFindBySql(sql1, new Object[]{entityId});
						 if(list!=null&&!list.isEmpty()){
							 for(Object[] obj:list){
								 if(!dweller.getId().equals(obj[1])){
									 return entityId;
								 }
							 }
						 }
					 }
				 }
//			 }
		 }
		return null;
	}
    /**
     * 检查手机号是否已经被注册过滤 检查web页面需要显示的住户
     * @param phone
     * @return
     * @see com.youlb.biz.personnel.IDwellerBiz#checkPhoneExist(java.lang.String)
     */
	@Override
	public boolean checkPhoneExistWebShow(String phone,String carrierId) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT w.fphone from t_dweller w INNER JOIN t_carrier c on c.id=w.fcarrier_id where w.fphone=? and c.id=?");
		List<Object> values = new ArrayList<Object>();
		values.add(phone);
		values.add(carrierId);
		List<String> find = dwellerSqlDao.pageFindBySql(sb.toString(), values.toArray());
		if(find!=null&&!find.isEmpty()){
			return true;
		}
		return false;
	}
	
	/**
     * 检查手机号在本运营商下是否已经被注册过滤  检查全部用户
     * @param phone
     * @return
     * @see com.youlb.biz.personnel.IDwellerBiz#checkPhoneExist(java.lang.String)
     */
	private String checkPhoneExist(String phone,Operator loginUser) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT w.fphone from t_dweller w INNER JOIN t_carrier c on c.id=w.fcarrier_id where w.fphone=? and c.id=?");
		List<Object> values = new ArrayList<Object>();
		values.add(phone);
		values.add(loginUser.getCarrier().getId());
		List<String> find = dwellerSqlDao.pageFindBySql(sb.toString(), values.toArray());
		if(find!=null&&!find.isEmpty()){
			return find.get(0);
		}
		return null;
	}

	@Override
	public List<String> getCarrierByDomainId(List<String> treecheckbox) {
		StringBuilder sb =new StringBuilder();
		sb.append("SELECT fcarrierid from t_carrier_domain where 1=1");
		sb.append(SearchHelper.jointInSqlOrHql(treecheckbox, " fdomainid "));
		sb.append(" group by fcarrierid");
		return dwellerSqlDao.pageFindBySql(sb.toString(), new Object[]{treecheckbox});
	}

}
