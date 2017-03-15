package com.youlb.biz.personnel.impl;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youlb.biz.personnel.IDwellerBiz;
import com.youlb.dao.common.BaseDaoBySql;
import com.youlb.entity.common.ResultDTO;
import com.youlb.entity.personnel.Dweller;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.common.JsonUtils;
import com.youlb.utils.common.MobileLocationUtil;
import com.youlb.utils.common.SysStatic;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.exception.JsonException;
import com.youlb.utils.helper.DateHelper;
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
	 * @throws JsonException 
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 * @throws ClientProtocolException 
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
			String delete = "delete from t_domain_dweller where fdwellerid=? ";
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
					String updateCallNum= "update t_room set fcallednumber=?,fphone_city=? where id=(select d.fentityid from t_domain d where d.id=?)";
					dwellerSqlDao.executeSql(updateCallNum, new Object[]{dweller.getPhone(),dweller.getPhoneCity(),domainid});
				}
			}
		   //判断用户是否已经有sip
		   String findSip = "select u.user_sip from users u inner join t_users tu on tu.id=to_number(u.local_sip, '9999999999999') inner join t_dweller d on d.fphone=tu.fusername where d.id=?";
		   List<String> sipList = dwellerSqlDao.pageFindBySql(findSip, new Object[]{dweller.getId()});
		   if(sipList==null||sipList.isEmpty()){
			   String neibName = getNeibName(dweller.getTreecheckbox().get(0));
			   //创建sip
			   if(StringUtils.isNotBlank(dweller.getPhone())){
				   try {
					create_sip(dweller.getPhone(), "6", neibName);//用户sip 6
				} catch (IOException | JsonException e) {
					e.printStackTrace();
				}
			   }
		   }
		}
	}
	/**
	 * @param target
	 * @throws BizException
	 * @throws JsonException 
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 * @throws ClientProtocolException 
	 * @see com.youlb.biz.common.IBaseBiz#update(java.io.Serializable)
	 */
	public void update(Dweller dweller,Operator loginUser) throws BizException {
		String insert = "insert into t_domain_dweller(fdomainid,fdwellerid,fdwellertype) values (?,?,?)";
		dwellerSqlDao.update(dweller);
//		//修改互联网注册用户表的电话号码
//		String update ="update t_users set fusername=?,fmobile_phone=? where fusername=?";
//		dwellerSqlDao.updateSQL(update, new Object[]{});
		//把被叫手机号码置null
		updateCallednumberById(dweller.getId());
		if(dweller.getTreecheckbox()!=null&&!dweller.getTreecheckbox().isEmpty()){
			//先删除本运营商下的旧纪录
			StringBuilder delete = new StringBuilder("delete from t_domain_dweller where fdwellerid=? ");
			//不是特殊管理员只能修改自己权限下的房产
			if(!SysStatic.SPECIALADMIN.equals(loginUser.getIsAdmin())&&loginUser.getDomainIds()!=null&&!loginUser.getDomainIds().isEmpty()){
				delete.append(SearchHelper.jointInSqlOrHql(loginUser.getDomainIds(), " fdomainid "));
				dwellerSqlDao.executeSql(delete.toString(), new Object[]{dweller.getId(),loginUser.getDomainIds()});
			}else{
				dwellerSqlDao.executeSql(delete.toString(), new Object[]{dweller.getId()});

			}
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
					String updateCallNum= "update t_room set fcallednumber=?,fphone_city=? where id=(select d.fentityid from t_domain d where d.id=?)";
					dwellerSqlDao.executeSql(updateCallNum, new Object[]{dweller.getPhone(),dweller.getPhoneCity(),domainid});
				}
			}
		   //判断用户是否已经有sip
		   String findSip = "select u.user_sip from users u inner join t_users tu on tu.id=to_number(u.local_sip, '9999999999999') inner join t_dweller d on d.fphone=tu.fusername where d.id=?";
		   List<String> sipList = dwellerSqlDao.pageFindBySql(findSip, new Object[]{dweller.getId()});
		   if(sipList==null||sipList.isEmpty()){
			   String neibName = getNeibName(dweller.getTreecheckbox().get(0));
			   //创建sip
			   if(StringUtils.isNotBlank(dweller.getPhone())){
				   try {
					create_sip(dweller.getPhone(), "6", neibName);//用户sip 6
				}catch (IOException | JsonException e) {
					e.printStackTrace();
				}
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
	 * @throws BizException 
	 */
	  private void updateCallednumberById(String dwellerId) throws BizException{
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
		StringBuilder del = new StringBuilder("DELETE from t_users u where u.fusername in (SELECT d.fphone from t_dweller d where 1=1 ");
		List<String> idList = new ArrayList<String>();
		if(ids!=null){
			for(String id:ids){
				if(!idList.contains(id)){
					idList.add(id);
					delete(id);
				}
			}
			del.append(SearchHelper.jointInSqlOrHql(idList, " d.id "));
			del.append(")");
			//删除互联网用户
			dwellerSqlDao.executeSql(del.toString(), new Object[]{idList});
		}

	}
	
	@Override
	public void delete(String[] ids, Operator loginUser)throws BizException {
		StringBuilder del = new StringBuilder("DELETE from t_users u where u.fusername in (SELECT d.fphone from t_dweller d where 1=1 ");
		List<String> idList = new ArrayList<String>();
		StringBuilder delete =new StringBuilder("DELETE from t_domain_dweller where fdwellerid=? ");
		delete.append(SearchHelper.jointInSqlOrHql(loginUser.getDomainIds(), " fdomainid "));
		if(ids!=null){
			for(String id:ids){
				if(!idList.contains(id)){
					idList.add(id);
					//只删除中间表即可
					dwellerSqlDao.executeSql(delete.toString(),  new Object[]{id,loginUser.getDomainIds()});
				}
			}
			del.append(SearchHelper.jointInSqlOrHql(idList, " d.id "));
			del.append(")");
			//删除互联网用户
			dwellerSqlDao.executeSql(del.toString(), new Object[]{idList});
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
		List<String> domainIds = loginUser.getDomainIds();
		if(!SysStatic.SPECIALADMIN.equals(loginUser.getIsAdmin())&&domainIds!=null&&!domainIds.isEmpty()){
			sb.append(SearchHelper.jointInSqlOrHql(domainIds, " d.id "));
			values.add(domainIds);
		}
		
//		if(!SysStatic.SPECIALADMIN.equals(loginUser.getIsAdmin())){
//			sb.append(" and w.fcarrier_id=? ");
//			values.add(loginUser.getCarrier().getId());
//		}
		sb.append(")t where 1=1 ");//不显示子账号，子账号没有身份证号码
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
	 * @throws BizException 
	 * @see com.youlb.biz.access.IPermissionBiz#findAddress(com.youlb.entity.access.CardInfo)
	 */
	private String findAddressByRoomId(String roomId) throws BizException {
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
	 * @throws BizException 
	 * @throws JsonException 
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 * @throws ClientProtocolException 
	 * @throws Exception 
	 * @see com.youlb.biz.personnel.IDwellerBiz#saveOrUpdate(com.youlb.entity.personnel.HostInfo)
	 */
	@Override
	public void saveOrUpdate(Dweller dweller,Operator loginUser) throws BizException, ClientProtocolException, UnsupportedEncodingException, IOException, JsonException   {
//		String insert = "insert into t_domain_dweller(fdomainid,fdwellerid,fdwellertype) values ((select d.id from t_domain d where d.fentityid=?),?,?)";
		String insert = "insert into t_domain_dweller(fdomainid,fdwellerid,fdwellertype) values (?,?,?)";
		if(StringUtils.isNotBlank(dweller.getPhone())){
			String mobileLocation = MobileLocationUtil.getFixPhoneLocation(dweller.getPhone());
			if(mobileLocation!=null){
				mobileLocation = mobileLocation.split(",")[0];
				dweller.setPhoneCity(mobileLocation);
			}
		}
		if(StringUtils.isBlank(dweller.getId())){
			//检查手机号码是否已经注册
//			String id = checkPhoneExist(dweller.getPhone(),loginUser);
//			if(StringUtils.isNotBlank(id)){
//				dweller.setId(id);
//				//已经注册就更新记录
//				dwellerSqlDao.update(dweller);
//			}else{
				dweller.setId(null);
				//判断电话号码是否已经存在
				String dwellerId="";
				String checkPhone = "select id from t_dweller where fphone=? ";
				List<String> ids= dwellerSqlDao.pageFindBySql(checkPhone, new Object[]{dweller.getPhone()});
			    if(ids!=null&&!ids.isEmpty()){
			    	dwellerId=ids.get(0);
			    	dweller.setId(dwellerId);
			    	dwellerSqlDao.update(dweller);
				}else{
					 dwellerId = (String) dwellerSqlDao.add(dweller);
				}
				dwellerSqlDao.getCurrSession().flush();
				if(dweller.getTreecheckbox()!=null&&!dweller.getTreecheckbox().isEmpty()){
					for(String domainid:dweller.getTreecheckbox()){
						String find="select d.id from t_domain_dweller dd inner join t_domain d on d.id=dd.fdomainid where d.id=? and dd.fdwellertype='1'";
						List<String> list = dwellerSqlDao.pageFindBySql(find, new Object[]{domainid});
						//说明该房屋已经绑定人的信息 此人是非户主
						if(list!=null&&!list.isEmpty()){
							dwellerSqlDao.executeSql(insert, new Object[]{domainid,dwellerId,"0"});//非户主
						}else{
							dwellerSqlDao.executeSql(insert, new Object[]{domainid,dwellerId,"1"});//户主
							//设置房间的被叫号码默认是户主
							
							String updateCallNum= "update t_room set fcallednumber=?,fphone_city=? where id=(select d.fentityid from t_domain d where d.id=?)";
							dwellerSqlDao.executeSql(updateCallNum, new Object[]{dweller.getPhone(),dweller.getPhoneCity(),domainid});
						}
					}
					   //判断用户是否已经有sip
//					   String findSip = "select u.user_sip from users u inner join t_users tu on tu.id=to_number(u.local_sip, '9999999999999') inner join t_dweller d on d.fphone=tu.fusername where d.id=?";
//					   List<String> sipList = dwellerSqlDao.pageFindBySql(findSip, new Object[]{dweller.getId()});
//					   if(sipList==null||sipList.isEmpty()){
						   String neibName = getNeibName(dweller.getTreecheckbox().get(0));
							//创建sip
							if(StringUtils.isNotBlank(dweller.getPhone())){
								create_sip(dweller.getPhone(), "6", neibName);//用户sip 6
							}
//					   }
				}
//			}
			
		}else{
			update(dweller,loginUser);
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
	/**
	 * 获取社区名称
	 * @param domainid
	 * @return
	 * @throws BizException 
	 */
	private String getNeibName(String domainid) throws BizException {
		String sql = "WITH RECURSIVE r AS (SELECT * FROM t_domain WHERE id =? union ALL SELECT t_domain.* FROM t_domain, r WHERE t_domain.id = r.fparentid) SELECT  fremark FROM r where flayer = '1'";
		List<String> list = dwellerSqlDao.pageFindBySql(sql, new Object[]{domainid});
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}

	/**
	    * 创建sip账号
	    * @param sipType
	    * @param neiborName
	    * @throws IOException
	    * @throws ClientProtocolException
	    * @throws JsonException
	    * @throws UnsupportedEncodingException
	    * @throws BizException
	    */
		public void create_sip(String local_sip,String sipType, String neibName)
				throws IOException, ClientProtocolException, JsonException,
				UnsupportedEncodingException, BizException {
			CloseableHttpClient httpClient = HttpClients.createDefault();
				//同步数据以及平台
				HttpPost request = new HttpPost(SysStatic.FIRSTSERVER+"/fir_platform/create_sip_num");
				List<BasicNameValuePair> formParams = new ArrayList<BasicNameValuePair>();
				formParams.add(new BasicNameValuePair("local_sip", local_sip));
				formParams.add(new BasicNameValuePair("sip_type", sipType));
				formParams.add(new BasicNameValuePair("neibName", neibName));
				UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formParams, "UTF-8");
				request.setEntity(uefEntity);
				CloseableHttpResponse response = httpClient.execute(request);
				if(response.getStatusLine().getStatusCode()==200){
					HttpEntity entity_rsp = response.getEntity();
					ResultDTO resultDto = JsonUtils.fromJson(EntityUtils.toString(entity_rsp), ResultDTO.class);
					if(resultDto!=null){
						if(!"0".equals(resultDto.getCode())){
							throw new BizException(resultDto.getMsg());
						}else{
							Map<String,Object> map = (Map<String, Object>) resultDto.getResult();
							if(map!=null&&!map.isEmpty()){
								Map<String,Object> user_sipMap = (Map<String, Object>) map.get("user_sip");
								Map<String,Object> userMap = (Map<String, Object>) map.get("user");
								//判断是否已经有sip
								String checkUsers = "select id from t_users where id=?";
								List<String> usersId = dwellerSqlDao.pageFindBySql(checkUsers, new Object[]{userMap.get("id")});
								if(usersId==null||usersId.isEmpty()){
	                                //{id=87, username=15974102603, mobilePhone=15974102603, cryptedPsw=e10adc3949ba59abbe56e057f20f883e, status=3, createTime=2016-11-14 16:01:39, carrier=中国移动, attribution=湖南长沙}
									String t_user ="insert into t_users (id,fusername,fmobile_phone,fcrypted_password,fstatus,FCREATETIME,fcarrier,fattribution) values(?,?,?,?,?,?,?,?)";
									dwellerSqlDao.executeSql(t_user,new Object[]{userMap.get("id"),userMap.get("username"),userMap.get("mobilePhone"),userMap.get("cryptedPsw"),userMap.get("status"),
											DateHelper.strParseDate((String)userMap.get("createTime"), "yyyy-MM-dd HH:mm:ss"),userMap.get("carrier"),userMap.get("attribution")});
								}
								String checkSip = "select local_sip from users where local_sip=?";
								List<String> localSip = dwellerSqlDao.pageFindBySql(checkSip, new Object[]{user_sipMap.get("linkId")});
								if(localSip==null||localSip.isEmpty()){
									String addSip ="insert into users (user_sip,user_password,local_sip,sip_type,fs_ip,fs_port) values(?,?,?,?,?,?)";
									//{user_sip=2000000338, userPassword=63d7b817141c4d30840cd24e16200859, sipType=6, linkId=87, fs_ip=192.168.1.222, fs_post=35162}
									dwellerSqlDao.executeSql(addSip, new Object[]{user_sipMap.get("user_sip"),user_sipMap.get("userPassword"),
											user_sipMap.get("linkId"),user_sipMap.get("sipType"),user_sipMap.get("fs_ip"),user_sipMap.get("fs_post")});//住户sip 6
								}
								
							}
					     }
					}else{
						throw new BizException("创建sip账号出错！");
					}
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
	 * @throws BizException 
	 */
	private String checkDetail(Dweller dweller) throws BizException {
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
     * 检查手机号是否已经帮定所选择的房产
     * @param phone
     * @return
     * @throws BizException 
     * @see com.youlb.biz.personnel.IDwellerBiz#checkPhoneExist(java.lang.String)
     */
	@Override
	public String checkPhoneExistWebShow(Dweller dweller) throws BizException {
		StringBuilder sb = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		sb.append("SELECT w.id from t_dweller w where w.fphone=?");
		values.add(dweller.getPhone());
//		values.add(dweller.getCarrierId());
		if(StringUtils.isNotBlank(dweller.getId())){
			sb.append(" and w.id!=? ");
			values.add(dweller.getId());
		}
		List<String> find = dwellerSqlDao.pageFindBySql(sb.toString(), values.toArray());
		if(find!=null&&!find.isEmpty()){
			String fdwellerid=find.get(0);
			if(dweller.getTreecheckbox()!=null&&!dweller.getTreecheckbox().isEmpty()&&StringUtils.isNotBlank(fdwellerid)){
				String sql = "select d.fremark from t_domain_dweller dd inner join t_domain d on d.id=dd.fdomainid where fdwellerid=? and fdomainid=? ";
				for(String domainid:dweller.getTreecheckbox()){
					List<String> fdomainids  = dwellerSqlDao.pageFindBySql(sql, new Object[]{fdwellerid,domainid});
					if(fdomainids!=null&&!fdomainids.isEmpty()){
						return fdomainids.get(0);
					}
				}
				
			}
		}
		
		return null;
	}
	
	/**
     * 检查手机号在本运营商下是否已经被注册过滤  检查全部用户
     * @param phone
     * @return
	 * @throws BizException 
     * @see com.youlb.biz.personnel.IDwellerBiz#checkPhoneExist(java.lang.String)
     */
	private String checkPhoneExist(String phone,Operator loginUser) throws BizException {
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
	public List<String> getCarrierByDomainId(List<String> treecheckbox) throws BizException {
		StringBuilder sb =new StringBuilder();
		sb.append("SELECT fcarrierid from t_carrier_domain where 1=1");
		sb.append(SearchHelper.jointInSqlOrHql(treecheckbox, " fdomainid "));
		sb.append(" group by fcarrierid");
		return dwellerSqlDao.pageFindBySql(sb.toString(), new Object[]{treecheckbox});
	}
    /**
     * 查询所有的父节点
     * @throws BizException 
     */
	@Override
	public List<String> getParentIds(String id) throws BizException {
		StringBuilder sb =new StringBuilder();
		sb.append("WITH RECURSIVE r AS (SELECT d.* from t_domain d INNER JOIN t_domain_dweller tdd on tdd.fdomainid=d.id where tdd.fdwellerid = ?")
		.append(" union ALL SELECT t_domain.* FROM t_domain, r WHERE t_domain.id = r.fparentid)")
		.append(" SELECT r.id  FROM r where r.fparentid is not null GROUP BY r.id");
		return dwellerSqlDao.pageFindBySql(sb.toString(), new Object[]{id});
	}


}
