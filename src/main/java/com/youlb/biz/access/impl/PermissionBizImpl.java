package com.youlb.biz.access.impl;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.youlb.biz.access.IPermissionBiz;
import com.youlb.dao.common.BaseDaoBySql;
import com.youlb.entity.access.BlackListData;
import com.youlb.entity.access.CardInfo;
import com.youlb.entity.access.CardRecord;
import com.youlb.entity.common.ResultDTO;
import com.youlb.entity.personnel.Dweller;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.common.JsonUtils;
import com.youlb.utils.common.SysStatic;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.exception.JsonException;
import com.youlb.utils.helper.OrderHelperUtils;
import com.youlb.utils.helper.SearchHelper;

/** 
 * @ClassName: PermissionBizImpl.java 
 * @Description: 门禁授权管理业务实现类 
 * @author: Pengjy
 * @date: 2015-11-5
 * 
 */
@Service("permissionBiz")
public class PermissionBizImpl implements IPermissionBiz {
	/** 日志输出 */
	private static Logger logger = LoggerFactory.getLogger(PermissionBizImpl.class);
	@Autowired
    private BaseDaoBySql<CardInfo> cardSqlDao;
	public void setCardSqlDao(BaseDaoBySql<CardInfo> cardSqlDao) {
		this.cardSqlDao = cardSqlDao;
	}
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
	public String save(CardInfo target) throws BizException {
		return null;
	}

	/**
	 * @param target
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#update(java.io.Serializable)
	 */
	@Override
	public void update(CardInfo target) throws BizException {

	}

	/**
	 * @param id
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Serializable id) throws BizException {

	}

	/**
	 * @param ids
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) throws BizException {

	}

	/**
	 * @param id
	 * @return
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#get(java.io.Serializable)
	 */
	@Override
	public CardInfo get(Serializable id) throws BizException {
		
		return cardSqlDao.get(id);
	}

	/**
	 * @param target
	 * @param loginUser
	 * @return
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#showList(java.io.Serializable, com.youlb.entity.privilege.Operator)
	 */
	@Override
	public List<CardInfo> showList(CardInfo target, Operator loginUser)throws BizException {
		StringBuilder sb = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		sb.append("select * from(SELECT c.fcardsn cardSn,c.fcardno cardNo,c.fcardtype cardType,c.fcardstatus cardStatus,c.fdomainid domainId,c.fcreatetime createTime")
		.append(" from t_cardinfo c left join t_domain d on d.id=c.fdomainid where 1=1");
		
		//不是特殊管理员需要过滤域
		List<String> domainIds = loginUser.getDomainIds();
		if(!SysStatic.SPECIALADMIN.equals(loginUser.getIsAdmin())&&domainIds!=null&&!domainIds.isEmpty()){
			sb.append(SearchHelper.jointInSqlOrHql(domainIds,"d.id"));
			values.add(domainIds);
		}
		sb.append(") t where 1=1");
//		sb.append(" GROUP BY c.fcardsn,c.fcardno,c.fcardtype,c.ffrdate,c.ftodate,c.fcardstatus,d.id,d.fname,d.fidnum,d.fphone,d.femail")
//		.append(") t where 1=1");
		if(StringUtils.isNotBlank(target.getCardSn())){
			 sb.append(" and t.cardSn like ?");
			 values.add("%"+target.getCardSn()+"%");
		 }
		if(target.getCardNo()!=null){
			 sb.append(" and to_char(t.cardNo,'999999999999999') like ?");
			 values.add("%"+target.getCardNo()+"%");
		 }
		if(StringUtils.isNotBlank(target.getCardType())){
			 sb.append(" and t.cardType = ?");
			 values.add(target.getCardType());
		 }
		if(StringUtils.isNotBlank(target.getCardStatus())){
			 sb.append(" and t.cardStatus = ?");
			 values.add(target.getCardStatus());
		 }
//		//快到期卡片查询
//		if(StringUtils.isNotBlank(target.getNearEndCard())){
//			Calendar c = Calendar.getInstance();
//			c.add(Calendar.DAY_OF_MONTH, SysStatic.NEARDAY);
//			sb.append(" and to_date(t.toDate,'yyyy-mm-dd') < ?");
//			values.add(c.getTime());
//		}
		 OrderHelperUtils.getOrder(sb, target, "t.", "t.createTime");
		List<Object[]> listObj = dwellerSqlDao.pageFindBySql(sb.toString(), values.toArray(), target.getPager());
		List<CardInfo> list = new ArrayList<CardInfo>();
		if(listObj!=null&&!listObj.isEmpty()){
			for(Object[] obj:listObj){
				CardInfo cardInfo = new CardInfo();
				cardInfo.setPager(target.getPager());
				 cardInfo.setCardSn(obj[0]==null?"":(String)obj[0]);
				 cardInfo.setCardNo(obj[1]==null?null:(Integer)obj[1]);
				 cardInfo.setCardType(obj[2]==null?"":(String)obj[2]);
				 cardInfo.setCardStatus(obj[3]==null?"":(String)obj[3]);
				 if(obj[4]!=null){
					 String address = findAddressByRoomId((String)obj[4]);
					 cardInfo.setAddress(address);
				 }
				 list.add(cardInfo);
			}
		}
        return list;
	}
 
	/**
	 * @param IcCardInfo
	 * @param loginUser
	 * @return
	 * @see com.youlb.biz.access.IPermissionBiz#saveOrUpdate(com.youlb.entity.access.CardInfo, com.youlb.entity.privilege.Operator)
	 */
	@Override
	public String saveOrUpdate(CardInfo IcCardInfo, Operator loginUser) {
		return null;
	}



	/**写卡和入库
	 * @param cardInfo
	 * @throws NativeException 
	 * @throws IllegalAccessException 
	 * @throws IOException 
	 * @throws JsonException 
	 * @throws ParseException 
	 * @throws BizException 
	 * @see com.youlb.biz.access.IPermissionBiz#writeCard(com.youlb.entity.access.CardInfo)
	 */
	@Override
	public int writeCard(CardInfo cardInfo) throws ParseException, JsonException, IOException, BizException  {
//		JNativeTest.writeBasicInfo(cardInfo);
//			CardInfo c = getCardInfo(cardInfo);
//			if(c!=null){
//				c.setCardStatus(SysStatic.LIVING);//激活状态
//				c.setRoomId(cardInfo.getRoomId());
//				String update = "update CardInfo set cardStatus=? where cardSn=? and domainId=? ";
//				cardSqlDao.update(update, new Object[]{"1",cardInfo.getCardSn(),cardInfo.getRoomId()});
//			}else{
				//添加ic卡信息
				//激活状态
				cardInfo.setCardStatus(SysStatic.LIVING);
//		设置卡片类型
//		cardInfo.setCardType(SysStatic.ICCARD);
				//生成序号8位数
				Session session = cardSqlDao.getCurrSession();
				SQLQuery query = session.createSQLQuery("SELECT '1'||substring('000000'||nextval('tbl_card_seq'),length(currval('tbl_card_seq')||'')) ");
				List<String> list =  query.list();
				logger.info("生成序号8位数"+list.get(0));
				cardInfo.setCardNo(Integer.parseInt(list.get(0)));
				String id = (String) cardSqlDao.add(cardInfo);
//	    Session currSession = cardSqlDao.getCurrSession();
				session.flush();
//			}
				//修改需要更新白名单标志
			
		//添加所有卡片父类表
//		String sql  ="insert into t_card(fcardsn,fdwellerId,fcardtype) values(?,?,?)";
//		cardSqlDao.executeSql(sql, new Object[]{cardsn,cardInfo.getdwellerId(),SysStatic.ICCARD});
		//添加卡片房子中间表
//		String sql1  ="insert into t_domain_card(fdomainsn,fcardsn) values(?,?)";
//		if(cardInfo.getDomainSns()!=null&&!cardInfo.getDomainSns().isEmpty()){
//			for(Integer domainSn:cardInfo.getDomainSns()){
//				cardSqlDao.executeSql(sql1, new Object[]{domainSn,cardInfo.getCardSn()});
//			}
//		}
//				String deviceCount = "";
		//用户卡
		if("1".equals(cardInfo.getCardBelongs())){
			//更新办卡次数
			String update = "update t_room set fcardcount = CASE WHEN fcardcount is null then 0 ELSE fcardcount END+1 where id=(select d.fentityid from t_domain d where d.id=?)";
			cardSqlDao.updateSQL(update, new Object[]{cardInfo.getDomainId()});
			//推送白名单
//			指定发送设备（找到设备账号）
//			 deviceCount = findDomainSn(cardInfo.getCardSn(),cardInfo.getDomainId());
		//物业卡	
		}else if("2".equals(cardInfo.getCardBelongs())){
//			//查询该物业人员是否已经发过次卡片
//			String find = "select fcardinfo_id from t_cardinfo_worker where fcardinfo_id=? and fworker_id=?";
//			List<String> findList = cardSqlDao.pageFindBySql(find,new Object[]{id,cardInfo.getWorkerId()});
//			if(findList==null||findList.isEmpty()){
				String insert="insert into t_cardinfo_worker(fcardinfo_id,fworker_id) values(?,?)";
				cardSqlDao.executeSql(insert, new Object[]{id,cardInfo.getWorkerId()});
//			}
//			deviceCount=findDeviceCount(cardInfo.getCardSn(),cardInfo.getDomainId());
		}
		pushInfo(cardInfo);
		
//		logger.info("门口机账号："+deviceCount);
//		//没有设备账号 说明没有安装没口机
//		if(StringUtils.isBlank(deviceCount)){
//			logger.info("没有安装门口机，设备账号为空");
//			throw new BizException("没有安装门口机，设备账号为空");
//		}else{
//			CloseableHttpClient httpClient = HttpClients.createDefault();
//			HttpPost request = new HttpPost(SysStatic.HTTP+"/device/web_pull_blacklist.json");
//			List<BasicNameValuePair> formParams = new ArrayList<BasicNameValuePair>();
//			logger.info("白名单推送设备："+deviceCount);
//			formParams.add(new BasicNameValuePair("deviceCount", deviceCount));
//			BlackListData bcl = new BlackListData();
//			bcl.addBc(new BlackListData.BlackCardData(0, cardInfo.getCardSn(),new Date().getTime()+""));
//			logger.info("白名单："+JsonUtils.toJson(bcl));
//			formParams.add(new BasicNameValuePair("content", JsonUtils.toJson(bcl)));
//			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formParams, "UTF-8");
//			request.setEntity(uefEntity);
//			CloseableHttpResponse response = httpClient.execute(request);
//			if(response.getStatusLine().getStatusCode()==200){
//				HttpEntity entity_rsp = response.getEntity();
//				ResultDTO resultDto = JsonUtils.fromJson(EntityUtils.toString(entity_rsp), ResultDTO.class);
//				if(resultDto!=null){
//					if(!"0".equals(resultDto.getCode())&&!"3001".equals(resultDto.getCode())){//设备没有登录也发卡成功
//						logger.error(resultDto.getMsg());
//						throw new BizException(resultDto.getMsg());
//					}
//				}else{
//					logger.info("白名单推送成功！");
//				}
//			} 
//		}
		return 0;
	}
	
	@Override
	public void updateCardInfo(CardInfo cardInfo) throws BizException, ClientProtocolException, ParseException, IOException, JsonException {
		String update = "update CardInfo set cardStatus=? where cardSn=? and domainId=? ";
		cardSqlDao.update(update, new Object[]{SysStatic.LIVING,cardInfo.getCardSn(),cardInfo.getDomainId()});
		pushInfo(cardInfo);
	}
	/**
	 * 推送白名单
	 * @param cardInfo
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws ParseException
	 * @throws JsonException
	 * @throws BizException
	 */
	private void pushInfo(CardInfo cardInfo)throws ClientProtocolException, IOException, ParseException, JsonException, BizException{
		  //指定发送设备（找到设备账号）
		String deviceCount = "";
		  //用户卡
			if("1".equals(cardInfo.getCardBelongs())){
				deviceCount = findDomainSn(cardInfo.getCardSn(),cardInfo.getDomainId());
			//物业卡	
			}else if("2".equals(cardInfo.getCardBelongs())){
				deviceCount=findDeviceCount(cardInfo.getCardSn(),cardInfo.getDomainId());

			}
			logger.info("门口机账号："+deviceCount);
			//没有设备账号 说明没有安装没口机
			if(StringUtils.isBlank(deviceCount)){
				logger.info("没有安装门口机，设备账号为空");
				throw new BizException("没有安装门口机，设备账号为空");
			}else{
				CloseableHttpClient httpClient = HttpClients.createDefault();
				HttpPost request = new HttpPost(SysStatic.HTTP+"/device/web_pull_blacklist.json");
				List<BasicNameValuePair> formParams = new ArrayList<BasicNameValuePair>();
				logger.info("白名单推送设备："+deviceCount);
				formParams.add(new BasicNameValuePair("deviceCount", deviceCount));
				BlackListData bcl = new BlackListData();
				bcl.addBc(new BlackListData.BlackCardData(0, cardInfo.getCardSn(),new Date().getTime()+""));
				logger.info("白名单："+JsonUtils.toJson(bcl));
				formParams.add(new BasicNameValuePair("content", JsonUtils.toJson(bcl)));
				UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formParams, "UTF-8");
				request.setEntity(uefEntity);
				CloseableHttpResponse response = httpClient.execute(request);
				if(response.getStatusLine().getStatusCode()==200){
					HttpEntity entity_rsp = response.getEntity();
					ResultDTO resultDto = JsonUtils.fromJson(EntityUtils.toString(entity_rsp), ResultDTO.class);
					if(resultDto!=null){
						if(!"0".equals(resultDto.getCode())&&!"3001".equals(resultDto.getCode())){//设备没有登录也发卡成功
							logger.error(resultDto.getMsg());
							throw new BizException(resultDto.getMsg());
						}
					}else{
						logger.info("白名单推送成功！");
					}
				} 
			}
	}
    /**
     * 获取卡片信息
     * @param cardInfo
     * @return
     * @throws BizException 
     */
	@Override
	public CardInfo checkCardExist(String cardSn) throws BizException {
		String hql = "from CardInfo where cardSn=? ";  //物业卡只能发一次
		return cardSqlDao.findObject(hql, new Object[]{cardSn});
	}
	/**
	 * @param cardSn
	 * @return
	 * @throws BizException 
	 * @see com.youlb.biz.access.IPermissionBiz#checkCardExist(java.lang.String)
	 */
	@Override
	public CardInfo checkCardExist(CardInfo cardInf) throws BizException {
		String hql = "from CardInfo where cardSn=? and domainId=? ";//注销的可以重新发卡
		return cardSqlDao.findObject(hql, new Object[]{cardInf.getCardSn(),cardInf.getDomainId()});
	}

	/**
	 * @param cardInfo
	 * @return
	 * @throws BizException 
	 * @see com.youlb.biz.access.IPermissionBiz#cardMap(com.youlb.entity.access.CardInfo)
	 */
	@Override
	public Map<String, CardInfo> cardMap(CardInfo cardInfo) throws BizException {
		 StringBuilder sb = new StringBuilder();
		 sb.append("select t.fcardsn,t.fcardno,t.fcardtype,t.ffrdate,t.ftodate,t.fdomainid from t_cardinfo t ");
		 List<Object> values = new ArrayList<Object>();
		 if(StringUtils.isNotBlank(cardInfo.getDomainId())){
			 sb.append(" where t.fdomainid = ? ");
			 values.add(cardInfo.getDomainId());
		 }else if(StringUtils.isNotBlank(cardInfo.getWorkerId())){
			 sb.append(" inner join t_cardinfo_worker cw on cw.fcardinfo_id=t.fcardsn where cw.fworker_id=? ");
			 values.add(cardInfo.getWorkerId());
		 }
		 
		 sb.append(" and t.fcardstatus =?");
		 //需要挂失
		 if(SysStatic.LOSS.equals(cardInfo.getCardStatus())){
			 values.add(SysStatic.LIVING);
	     //需要激活
		 }else if(SysStatic.LIVING.equals(cardInfo.getCardStatus())){
			 values.add(SysStatic.LOSS);
		  //需要注销 注销需要解除关联关系 
		 }else if(SysStatic.CANCEL.equals(cardInfo.getCardStatus())){
			 values.add(SysStatic.LIVING);
		 }
		List<Object[]> list = cardSqlDao.pageFindBySql(sb.toString(), values.toArray());
		Map<String,CardInfo> map = new LinkedHashMap<String, CardInfo>();
		if(list!=null&&!list.isEmpty()){
			for(Object[] obj:list){
				CardInfo card = new CardInfo();
				card.setCardNo(obj[1]==null?null:(Integer)obj[1]);
				card.setCardType(obj[2]==null?null:(String)obj[2]);
				card.setFrDate(obj[3]==null?null:(String)obj[3]);
				card.setToDate(obj[4]==null?null:(String)obj[4]);
				card.setDomainId(obj[5]==null?null:(String)obj[5]);
				map.put(obj[0]==null?null:(String)obj[0], card);
			}
		}
		return map;
	}

	/**
	 * @param cardInfo
	 * @throws BizException
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws JsonException 
	 * @throws ParseException 
	 * @see com.youlb.biz.access.IPermissionBiz#lossUnlossDestroy(com.youlb.entity.access.CardInfo)
	 */
	@Override
	public void lossUnlossDestroy(CardInfo cardInfo) throws BizException, ClientProtocolException, IOException, ParseException, JsonException {
		StringBuilder sb = new StringBuilder();
		sb.append("update CardInfo t set t.cardStatus=?");
		//如果是注销 需要解除卡跟人和卡跟房子的关联关系(注销需要对房间单独操作) 注销不做推送
		if(SysStatic.CANCEL.equals(cardInfo.getCardStatus())){
			//解除卡分人的关联
			sb.append(",t.oprType=null");
			sb.append(" where t.cardSn= ? and domainId=?");
			cardSqlDao.update(sb.toString(),new Object[]{cardInfo.getCardStatus(),cardInfo.getCardSn(),cardInfo.getDomainId()});
			//物业卡注销删除人与卡的关系
			 if(StringUtils.isNotBlank(cardInfo.getWorkerId())){
				 String delete = "delete from t_cardinfo_worker where fcardinfo_id=? and fworker_id=?";
				 cardSqlDao.executeSql(delete, new Object[]{cardInfo.getCardSn(),cardInfo.getWorkerId()});
			 }
		}else{
			 //(对卡操作)
			 sb.append(" where t.cardSn= ? and t.cardStatus!=? ");
			 cardSqlDao.update(sb.toString(),new Object[]{cardInfo.getCardStatus(),cardInfo.getCardSn(),"3"});
			  String deviceCount ="";
			 if(StringUtils.isBlank(cardInfo.getWorkerId())){
				 deviceCount = findDomainSn(cardInfo.getCardSn());
			 }else{
				  deviceCount = findDeviceCount(cardInfo.getCardSn(), cardInfo.getDomainId());
			 }
			 
				if(StringUtils.isBlank(deviceCount)){
					throw new BizException("没有需要推送的设备账号");
				}
				//推送通知
//				if(SysStatic.LOSS.equals(cardInfo.getCardStatus())||SysStatic.LIVING.equals(cardInfo.getCardStatus())){
					//指定发送设备(找到设备账号)
					CloseableHttpClient httpClient = HttpClients.createDefault();
					HttpPost request = new HttpPost(SysStatic.HTTP+"/device/web_pull_blacklist.json");
					List<BasicNameValuePair> formParams = new ArrayList<BasicNameValuePair>();
					formParams.add(new BasicNameValuePair("deviceCount", deviceCount));
					BlackListData bcl = new BlackListData();
					//挂失或者注销
					if(SysStatic.LOSS.equals(cardInfo.getCardStatus())||SysStatic.CANCEL.equals(cardInfo.getCardStatus())){
						bcl.addBc(new BlackListData.BlackCardData(1, cardInfo.getCardSn(),new Date().getTime()+""));
					//解挂
					}else{
						bcl.addBc(new BlackListData.BlackCardData(0, cardInfo.getCardSn(),new Date().getTime()+""));
					}
					formParams.add(new BasicNameValuePair("content", JsonUtils.toJson(bcl)));
					UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formParams, "UTF-8");
					request.setEntity(uefEntity);
					CloseableHttpResponse response = httpClient.execute(request);
					if(response.getStatusLine().getStatusCode()!=200){
						HttpEntity entity_rsp = response.getEntity();
						ResultDTO resultDto = JsonUtils.fromJson(EntityUtils.toString(entity_rsp), ResultDTO.class);
						if(resultDto!=null){
							if(!"0".equals(resultDto.getCode())){
								throw new BizException("信息推送"+resultDto.getMsg());
							}
						}
					}
//				}
		}
	
	}
	
	/**
	 * 获取最近门口机域id
	 * @param cardInfo
	 * @return
	 * @throws BizException
	 */
	private String getNearDevice(CardInfo cardInfo) throws BizException {
		StringBuilder sql = new StringBuilder();
		sql.append("WITH RECURSIVE r AS (SELECT d.* from  t_cardinfo ci INNER JOIN t_domain d ON d.id=ci.fdomainid where ci.fcardsn=? and d.id=? ")
		.append("union SELECT t_domain.* FROM t_domain, r WHERE t_domain.id = r.fparentid) ")
		.append("SELECT r.id FROM r INNER JOIN t_devicecount td on td.fdomainid=r.id where r.fentityid is not null ")
		.append("GROUP BY r.id,r.flayer  ORDER BY r.flayer desc,r.id ");
		List<String> domainIds  = cardSqlDao.pageFindBySql(sql.toString(), new Object[]{cardInfo.getCardSn(),cardInfo.getDomainId()});
		if(domainIds!=null&&!domainIds.isEmpty()){
			return domainIds.get(0);
		}
		return null;
	}
	/**
	 * 获取需要推送的设备账号
	 * @param domainId
	 * @return
	 * @throws BizException 
	 */
	private String getDeviceCount(String domainId) throws BizException {
		String sql ="SELECT t.fdevicecount from t_devicecount t INNER JOIN t_domain dm on dm.id=t.fdomainid where t.fdomainid=? and t.fcounttype='1'";
		List<String> deviceCountList = cardSqlDao.pageFindBySql(sql.toString(), new Object[]{domainId});
		if(deviceCountList!=null&&!deviceCountList.isEmpty()){
			StringBuilder deviceCountStr = new StringBuilder();
			for(String deviceCount:deviceCountList){
				deviceCountStr.append(deviceCount+",");
			}
			deviceCountStr.deleteCharAt(deviceCountStr.length()-1);
			return deviceCountStr.toString();
		}
		return null;
	}
	/**通过卡片序列号获取设备账号   多个使用,分隔
	 * @param cardSn
	 * @return
	 * @throws BizException 
	 */
	private String findDomainSn(String cardSn) throws BizException {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT t.fdevicecount from t_devicecount t INNER JOIN t_domain dm on dm.id=t.fdomainid where t.fdomainid in ( ")
		.append("WITH RECURSIVE r AS ( ")
		.append("SELECT d.* from  t_cardinfo ci INNER JOIN t_domain d ON d.id=ci.fdomainid where ci.fcardsn=? ")
		.append("union SELECT t_domain.* FROM t_domain, r WHERE t_domain.id = r.fparentid) ")
		.append("SELECT r.id FROM r where r.fentityid is not null) and t.fcounttype='1' ");
		List<String> deviceCountList = cardSqlDao.pageFindBySql(sb.toString(), new Object[]{cardSn});
		if(deviceCountList!=null&&!deviceCountList.isEmpty()){
			StringBuilder deviceCountStr = new StringBuilder();
			for(String deviceCount:deviceCountList){
				deviceCountStr.append(deviceCount+",");
			}
			deviceCountStr.deleteCharAt(deviceCountStr.length()-1);
			return deviceCountStr.toString();
		}
		return null;
	}
	
	/**物业员工卡  通过卡片序列号和域id获取设备账号   多个使用,分隔
	 * @param cardSn
	 * @return
	 * @throws BizException 
	 */
	private String findDeviceCount(String cardSn,String domainId) throws BizException {
		//向下查询有没有门口机账号
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT t.fdevicecount from t_devicecount t INNER JOIN t_domain dm on dm.id=t.fdomainid where t.fdomainid in ( ")
		.append("WITH RECURSIVE r AS ( ")
		.append("SELECT d.* from  t_cardinfo ci INNER JOIN t_domain d ON d.id=ci.fdomainid where ci.fcardsn=? and ci.fdomainid=? ")
		.append("union SELECT t_domain.* FROM t_domain, r WHERE t_domain.id = r.fparentid) ")
		.append("SELECT r.id FROM r where r.fentityid is not null) and t.fcounttype='1' ");
		List<String> deviceCountList = cardSqlDao.pageFindBySql(sb.toString(), new Object[]{cardSn,domainId});
		sb = new StringBuilder();
		//向上查询有没有门口机账号
		sb.append("SELECT t.fdevicecount from t_devicecount t INNER JOIN t_domain dm on dm.id=t.fdomainid where t.fdomainid in ( ")
		.append("WITH RECURSIVE r AS ( ")
		.append("SELECT d.* from  t_cardinfo ci INNER JOIN t_domain d ON d.id=ci.fdomainid where ci.fcardsn=? and ci.fdomainid=? ")
		.append("union SELECT t_domain.* FROM t_domain, r WHERE t_domain.fparentid= r.id ) ")
		.append("SELECT r.id FROM r where r.fentityid is not null) and t.fcounttype='1' ");
		List<String> updeviceCountList = cardSqlDao.pageFindBySql(sb.toString(), new Object[]{cardSn,domainId});
		
		
		if(deviceCountList!=null){
			deviceCountList.addAll(updeviceCountList);
			StringBuilder deviceCountStr = new StringBuilder();
			for(String deviceCount:deviceCountList){
				if(deviceCountStr.indexOf(deviceCount)<0){
					deviceCountStr.append(deviceCount+",");
				}
			}
			deviceCountStr.deleteCharAt(deviceCountStr.length()-1);
			return deviceCountStr.toString();
		}
		return null;
	}
	
	/**通过卡片序列号和域id获取设备账号   多个使用,分隔
	 * @param cardSn
	 * @return
	 * @throws BizException 
	 */
	private String findDomainSn(String cardSn,String domainId) throws BizException {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT t.fdevicecount from t_devicecount t INNER JOIN t_domain dm on dm.id=t.fdomainid where t.fdomainid in ( ")
		.append("WITH RECURSIVE r AS ( ")
		.append("SELECT d.* from  t_cardinfo ci INNER JOIN t_domain d ON d.id=ci.fdomainid where ci.fcardsn=? and ci.fdomainid=? ")
		.append("union SELECT t_domain.* FROM t_domain, r WHERE t_domain.id = r.fparentid) ")
		.append("SELECT r.id FROM r where r.fentityid is not null) and t.fcounttype='1' ");
		List<String> deviceCountList = cardSqlDao.pageFindBySql(sb.toString(), new Object[]{cardSn,domainId});
		if(deviceCountList!=null&&!deviceCountList.isEmpty()){
			StringBuilder deviceCountStr = new StringBuilder();
			for(String deviceCount:deviceCountList){
				deviceCountStr.append(deviceCount+",");
			}
			deviceCountStr.deleteCharAt(deviceCountStr.length()-1);
			return deviceCountStr.toString();
		}
		return null;
	}
	/**
	 * @return
	private String getChannels(String cardSn) {
		String sql ="SELECT d.id from  t_domain_card tdc INNER JOIN t_domain d ON d.fdomainsn=tdc.fdomainsn where tdc.fcardsn=?";
		List<String> list = cardSqlDao.pageFindBySql(sql, new Object[]{cardSn});
		if(list!=null&&!list.isEmpty()){
			String domainId = list.get(0);
			//获取上级每层域id集合
			List<String> domainList = getDomainIdList(new ArrayList<String>(),domainId);
			//获取channelid
			if(!domainList.isEmpty()){
				StringBuilder sb = new StringBuilder();
				List<Object> values = new ArrayList<Object>();
				sb.append("SELECT d.devicecount from t_devicecount d where d.fchannelid is not null and d.fdomainid in (");
				for(String domainid:domainList){
					sb.append("?,");
					values.add(domainid);
				}
				//删除最后一个，
				sb.deleteCharAt(sb.length()-1);
				sb.append(")");
				List<String> channelList = cardSqlDao.pageFindBySql(sb.toString(), values.toArray());
				if(channelList!=null&&!channelList.isEmpty()){
					StringBuilder channelStr = new StringBuilder();
					for(String channel:channelList){
						channelStr.append(channel+",");
					}
					channelStr.deleteCharAt(channelStr.length()-1);
					return channelStr.toString();
				}
			}
			
		}
		return null;
	} */
	/**
	 * @param arrayList
	 * @param domainId
	 * @return
	
	private List<String> getDomainIdList(ArrayList<String> arrayList,String domainId) {
		String sql ="select d.fparentid from t_domain d where d.id=?";
		List<String> list = cardSqlDao.pageFindBySql(sql, new Object[]{domainId});
		if(list!=null&&!list.isEmpty()){
			String parentId = list.get(0);
			if(StringUtils.isNotBlank(parentId)){
				arrayList.add(parentId);
				getDomainIdList(arrayList, parentId);
			}
		}
		return arrayList;
	} */
	/**获取地址信息
	 * @param cardInfo
	 * @return
	 * @throws BizException 
	 * @see com.youlb.biz.access.IPermissionBiz#findAddress(com.youlb.entity.access.CardInfo)
	 */
	@Override
	public String findAddressByRoomId(String domainId) throws BizException {
		StringBuffer sb = new StringBuffer();
		sb.append("select array_to_string (ARRAY(WITH RECURSIVE r AS (SELECT * FROM t_domain WHERE id = ?")
		.append(" union ALL SELECT t_domain.* FROM t_domain, r WHERE t_domain.id = r.fparentid) SELECT  fremark FROM r where flayer is not null ORDER BY flayer),'')");
		 List<String> listObj = cardSqlDao.pageFindBySql(sb.toString(), new Object[]{domainId});
		 if(listObj!=null&&!listObj.isEmpty()){
			 return listObj.get(0);
		 }
		return null;
	}
	/**
	 * 获取地址信息
	 */
	@Override
	public String findAddressByWorkerId(String cardSn,String workerId) throws BizException {
		StringBuffer sb = new StringBuffer();
		sb.append("select array_to_string (ARRAY(WITH RECURSIVE r AS (SELECT d.* FROM t_domain d ")
		.append(" INNER JOIN t_cardinfo c on c.fdomainid=d.id INNER JOIN t_cardinfo_worker cw  on cw.fcardinfo_id=c.fcardsn ")
		.append(" WHERE cw.fworker_id =? and cw.fcardinfo_id=?")
		.append(" union ALL SELECT t_domain.* FROM t_domain, r WHERE t_domain.id = r.fparentid) SELECT  fremark FROM r where flayer is not null ORDER BY flayer),'')");
		 List<String> listObj = cardSqlDao.pageFindBySql(sb.toString(), new Object[]{workerId,cardSn});
		 if(listObj!=null&&!listObj.isEmpty()){
			 return listObj.get(0);
		 }
		return null;
	}
    /**
     * 递归获取详细地址集合
     * @param map key=id-domainSn  value= StringBuilder
     * @return
     * @throws BizException 
     */
	private Map<String,StringBuilder> getAddress(Map<String,StringBuilder> map) throws BizException{
		    String sql="SELECT d.fparentid ,d.fremark from t_domain d where d.id=?";
			boolean b=false;
			Map<String,StringBuilder> newMap = new HashMap<String, StringBuilder>();
			for(String key:map.keySet()){
				Object[] obj = (Object[]) cardSqlDao.findObjectBySql(sql, new Object[]{key.substring(0, 32)});
				StringBuilder sb = map.get(key).insert(0, (String)obj[1]);
				//新key domainsn不变
				String newkey=(String)obj[0]+"-"+ key.substring(33);
				newMap.put(newkey,sb);
				//当parentId==1时 已经拿到全部地址
				if("1".equals(obj[0])){
					b=true;
				}
			}
			//到顶级域的时候返回结果
			if(b){
				return newMap;
			}
			return getAddress(newMap);
	}
	/**
	 * @param cardInfo
	 * @return
	 * @throws BizException 
	 * @see com.youlb.biz.access.IPermissionBiz#findAddressByCardSn(com.youlb.entity.access.CardInfo)
	 */
	@Override
	public List<CardInfo> findAddressByCardSn(CardInfo cardInfo) throws BizException {
		String sql="SELECT d.id,d.fdomainsn FROM t_domain_card dc INNER JOIN t_domain d on d.fdomainsn=dc.fdomainsn where dc.fcardsn=? ";
		 List<Object[]> listObj = cardSqlDao.pageFindBySql(sql, new Object[]{cardInfo.getCardSn()});
		 Map<String,StringBuilder> map = new HashMap<String, StringBuilder>();
		 List<CardInfo> list = new ArrayList<CardInfo>();
		 if(listObj!=null&&!listObj.isEmpty()){
			 for(Object[] obj:listObj){
				 map.put(obj[0]+"-"+obj[1], new StringBuilder());
			 }
			 
			 map = getAddress(map);
//			 System.out.println(map);
			 if(map!=null){
				 for(String key:map.keySet()){
					 CardInfo card = new CardInfo();
					 card.setDomainSn(Integer.parseInt(key.split("-")[1]));//domainsn
					 card.setAddress(map.get(key).toString());//完整地址
					 list.add(card);
				 }
			 }
		 }
		return list;
	}
	/**app开锁
	 * @param cardInfo
	 * @param loginUser
	 * @return
	 * @throws BizException 
	 * @see com.youlb.biz.access.IPermissionBiz#appRecordList(com.youlb.entity.access.CardInfo, com.youlb.entity.privilege.Operator)
	 */
	@Override
	public List<CardRecord> appRecordList(CardRecord cardRecord, Operator loginUser) throws BizException {
		cardRecord.setMode("5");//app开锁类型
		List<CardRecord> list = cardRecord(cardRecord, loginUser);
		return list;
	}
	/**开锁方法
	 * @param cardRecord
	 * @param loginUser
	 * @return
	 * @throws BizException 
	 * @see com.youlb.biz.access.IPermissionBiz#cardRecord(com.youlb.entity.access.CardInfo, com.youlb.entity.privilege.Operator)
	 */
	@Override
	public List<CardRecord> cardRecord(CardRecord cardRecord, Operator loginUser) throws BizException {
		StringBuilder sb = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		sb.append("select * from(SELECT cr.fcardsn cardsn,cr.ftime cardtime,cr.fpath imgpath,cr.id id,cr.fusername username,")
		.append("array_to_string (ARRAY(WITH RECURSIVE r AS (SELECT * FROM t_domain WHERE id =dc.fdomainid ")
		.append("union ALL SELECT t_domain.* FROM t_domain, r WHERE t_domain.id = r.fparentid) SELECT  fremark FROM r where flayer is not null ORDER BY flayer),'')||'-'||dc.fdevice_count_desc ")
		.append(" from t_cardrecord cr INNER JOIN t_devicecount dc on dc.fdevicecount=cr.fusername where 1=1 ");
		if("5".equals(cardRecord.getMode())){
			sb.append(" and cr.fmode in('5','7','8','9') ");
		}else{
			sb.append(" and cr.fmode =? ");
			values.add(cardRecord.getMode());//纪录类型
		}
		if(StringUtils.isNotBlank(cardRecord.getCardsn())){
			sb.append(" and cr.fcardsn like ?");
			values.add("%"+cardRecord.getCardsn()+"%");
		}
		//时间过滤
		if(StringUtils.isNotBlank(cardRecord.getStartTime())&&StringUtils.isNotBlank(cardRecord.getEndTime())){
			sb.append(" and to_char(cr.ftime,'yyyy-MM-dd HH24:mi:ss') BETWEEN ? and ?");
			values.add(cardRecord.getStartTime()+" 00:00:00");
			values.add(cardRecord.getEndTime()+" 23:59:59");
		}
		//不是特殊管理员需要过滤域
		List<String> domainIds = loginUser.getDomainIds();
		if(!SysStatic.SPECIALADMIN.equals(loginUser.getIsAdmin())&&domainIds!=null&&!domainIds.isEmpty()){
			sb.append(SearchHelper.jointInSqlOrHql(domainIds,"dc.fdomainid"));
			values.add(domainIds);
		}
		sb.append(") t");
		OrderHelperUtils.getOrder(sb, cardRecord, "t.", " t.cardtime desc ");
		List<Object[]> listObj =  cardSqlDao.pageFindBySql(sb.toString(), values.toArray(), cardRecord.getPager());
		List<CardRecord> list = new ArrayList<CardRecord>();
		if(listObj!=null&&!listObj.isEmpty()){
			String addressSql = "select array_to_string (ARRAY(WITH RECURSIVE r AS (SELECT * FROM t_domain WHERE id =? "
					+"union ALL SELECT t_domain.* FROM t_domain, r WHERE t_domain.id = r.fparentid) SELECT  fremark FROM r where flayer is not null ORDER BY flayer),'')";
			for(Object[] obj:listObj){
				CardRecord info = new CardRecord();
				info.setPager(cardRecord.getPager());
				info.setCardsn(obj[0]==null?"":(String)obj[0]);
				info.setCardtime(obj[1]==null?null:(Date)obj[1]);
				info.setImgpath(obj[2]==null?"":(String)obj[2]);
				info.setId(obj[3]==null?null:((BigInteger)obj[3]).longValue());
				info.setUsername(obj[4]==null?"":(String)obj[4]);
//				if(obj[5]!=null){
//					 List<String> address = cardSqlDao.pageFindBySql(addressSql, new Object[]{(String)obj[5]});
//					 if(address!=null&&!address.isEmpty()){
						 info.setAddress(obj[5]==null?"":(String)obj[5]);
//					 }
//				}
				list.add(info);
			}
		}
		return list;
	}
	/**
	 * @param cardSn
	 * @return
	 * @throws BizException 
	 * @see com.youlb.biz.access.IPermissionBiz#getImg(java.lang.String)
	 */
	@Override
	public CardInfo getImg(Integer id) throws BizException {
		String sql ="select cr.fserveraddr,cr.fpath,cr.ftime from t_cardrecord cr where cr.id=?";
		List<Object[]> listObj = cardSqlDao.pageFindBySql(sql, new Object[]{id});
		if(listObj!=null&&!listObj.isEmpty()){
			Object[] obj = listObj.get(0);
			CardInfo info = new CardInfo();
			info.setServeraddr(obj[0]==null?"":(String)obj[0]);
			info.setPath(obj[1]==null?"":(String)obj[1]);
			info.setFtime(obj[2]==null?null:(Date)obj[2]);
			return info;
		}
		return null;
	}
//  public static void main(String[] args){
//	  BlackListData blc = new BlackListData();
//	  BlackCardData card = new BlackCardData(1, "111111");
//	  BlackCardData card1 = new BlackCardData(2, "22222");
//	  blc.addBc(card);
//	  blc.addBc(card1);
//	  String json = JsonUtils.toJson(blc);
//	  System.out.println(json);
//	  try{
//	    BlackListData fromJson = JsonUtils.fromJson(json, BlackListData.class);
//	    System.out.println(fromJson);
//	  }catch(JsonException e){
//		  e.printStackTrace();
//	  }
//}
	/**
	 * 判断卡片是否已经初始化秘钥
	 */
	@Override
	public String isInitKey(String cardSn,String domainId) throws BizException { 
		String sql ="SELECT fdomainid from t_cardinfo where fcardsn=? and fcardstatus!=?";
		List<String> list = cardSqlDao.pageFindBySql(sql, new Object[]{cardSn,SysStatic.CANCEL});
		//获取房间的社区域id
		String neibId = getNeibDomainId(domainId);
		if(list!=null&&!list.isEmpty()){
			//获取已经绑定房间的社区域id
			String alreadyNeibId = getNeibDomainId(list.get(0));
			if(alreadyNeibId!=null&&alreadyNeibId.equals(neibId)){
				return "1";
			}else{
				return "2";
			}
		}
		return "0";
	}
	/**
	 * 判断是不是最后注销的卡
	 */
	@Override
	public String isLastCard(String cardSn) throws BizException {
		String sql ="SELECT fcardsn from t_cardinfo where fcardsn=? and fcardstatus!=?";
		List<String> list = cardSqlDao.pageFindBySql(sql, new Object[]{cardSn,SysStatic.CANCEL});
		if(list!=null&&list.size()==1){
			return "0";
		}
		return "1";
	}
	
	/**
	 * 通过房间id获取社区域id
	 * @param domainId
	 * @return
	 * @throws BizException
	 */
	public String getNeibDomainId(String domainId) throws BizException{
		StringBuffer sb = new StringBuffer();
		sb.append("WITH RECURSIVE r AS (SELECT * FROM t_domain WHERE id = ?")
		.append(" union ALL SELECT t_domain.* FROM t_domain, r WHERE t_domain.id = r.fparentid) SELECT  r.id FROM r where flayer=1");
		 List<String> listObj = cardSqlDao.pageFindBySql(sb.toString(), new Object[]{domainId});
		 if(listObj!=null&&!listObj.isEmpty()){
			 return listObj.get(0);
		 }
		return "";
	}
	/**
	 * 检查人和卡片的关系是否已经存在
	 */
	@Override
	public Boolean checkWorkerCardExist(CardInfo cardInfo) throws BizException {
		String find = "select fcardinfo_id from t_cardinfo_worker where fcardinfo_id=? and fworker_id=?";
		List<String> findList = cardSqlDao.pageFindBySql(find,new Object[]{cardInfo.getCardSn(),cardInfo.getWorkerId()});
		if(findList!=null&&!findList.isEmpty()){
			return true;
		}
		return false;
	}
	
	/**物业更新注销卡片  同时更新地址 物业卡不能一卡多发
	 * @throws BizException 
	 * @throws JsonException 
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws ClientProtocolException 
	 * 
	 */
	@Override
	public void workerUpdateCardInfo(CardInfo cardInfo) throws BizException, ClientProtocolException, ParseException, IOException, JsonException {
		String update = "update CardInfo set cardStatus=?,domainId=? where cardSn=? ";
		cardSqlDao.update(update, new Object[]{SysStatic.LIVING,cardInfo.getDomainId(),cardInfo.getCardSn()});
		//插入中间表
		String insert="insert into t_cardinfo_worker(fcardinfo_id,fworker_id) values(?,?)";
		cardSqlDao.executeSql(insert, new Object[]{cardInfo.getCardSn(),cardInfo.getWorkerId()});
		pushInfo(cardInfo);
		
	}
	/**
	 * 考勤管理列表显示
	 * @throws BizException 
	 */
	@Override
	public List<CardRecord> checkingshowList(CardRecord cardRecord,Operator loginUser) throws BizException {
		StringBuilder sb = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		 List<CardRecord> returnData = new ArrayList<CardRecord>();
		sb.append("SELECT * from (select * from(select *,row_number() over(partition by t.workerNum, t.fdate ORDER BY t.fdate,t.ftime desc)rn from ")
		.append("(SELECT to_char(cd.ftime,'yyyy-MM-dd') fdate,to_char(cd.ftime,'HH24:mi:ss') ftime,d.fremark workerNum,")
		.append("td.fname fname from t_cardrecord cd INNER JOIN t_cardinfo c on c.fcardsn=cd.fcardsn ")
		.append("INNER JOIN t_domain d on d.id=c.fdomainid INNER JOIN t_domain_dweller tdd on tdd.fdomainid=d.id ")
		.append("INNER JOIN t_dweller td on td.id=tdd.fdwellerid where fcardstatus='1' GROUP BY cd.ftime,d.fremark,td.fname )t )a where rn=1")
		.append(" UNION ")
		.append("select * from(select *,row_number() over(partition by t.workerNum, t.fdate ORDER BY t.fdate,t.ftime asc)rn from ")
		.append("(SELECT to_char(cd.ftime,'yyyy-MM-dd') fdate,to_char(cd.ftime,'HH24:mi:ss') ftime,d.fremark workerNum,")
		.append("td.fname fname from t_cardrecord cd INNER JOIN t_cardinfo c on c.fcardsn=cd.fcardsn ")
		.append("INNER JOIN t_domain d on d.id=c.fdomainid INNER JOIN t_domain_dweller tdd on tdd.fdomainid=d.id ")
		.append("INNER JOIN t_dweller td on td.id=tdd.fdwellerid where fcardstatus='1' GROUP BY cd.ftime,d.fremark,td.fname )t )a where rn=1 )o where 1=1 ");
		if(StringUtils.isNotBlank(cardRecord.getStartTime())&&StringUtils.isNotBlank(cardRecord.getEndTime())){
			sb.append(" and o.fdate>=? and o.fdate<=? ");
			values.add(cardRecord.getStartTime());
			values.add(cardRecord.getEndTime());
		}
		if(StringUtils.isNotBlank(cardRecord.getWorkerNum())){
			sb.append(" and o.workerNum like ? ");
			values.add("%"+cardRecord.getWorkerNum()+"%");
		}
		sb.append("order by to_number(o.workerNum, '999999999999'),o.fdate,o.ftime,o.workerNum");
		List<Object[]> list = cardSqlDao.pageFindBySql(sb.toString(), values.toArray(), cardRecord.getPager());
		if(list!=null&&!list.isEmpty()){
			for(Object[] obj:list){
				CardRecord c = new CardRecord();
				c.setPager(cardRecord.getPager());
				c.setFdate(obj[0]==null?"":(String)obj[0]);
				c.setFtime(obj[1]==null?"":(String)obj[1]);
				c.setWorkerNum(obj[2]==null?"":(String)obj[2]);
				c.setFname(obj[3]==null?"":(String)obj[3]);
				returnData.add(c);
			}
		}
		return returnData;
	}
	
}
