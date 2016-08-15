package com.youlb.biz.access.impl;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
		sb.append("select * from(SELECT c.fcardsn cardSn,c.fcardno cardNo,c.fcardtype cardType,c.fcardstatus cardStatus,c.froomid roomId,c.fcreatetime createTime")
		.append(" from t_cardinfo c left join t_domain d on d.fentityid=c.froomid where 1=1");
		
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


	/**根据人员信息查询地址信息 可能多条地址
	 * @param cardInfo
	 * @return
	 * @see com.youlb.biz.access.IPermissionBiz#findAddress(com.youlb.entity.access.CardInfo)
	 */
	@Override
	public List<CardInfo> findAddress(CardInfo cardInfo,Map<String, String> domainMap) {
//		StringBuilder sb = new StringBuilder();
//		List<Object> values = new ArrayList<Object>();
//		sb.append("SELECT d.fdomainsn domainsn, h.fcity,h.farea,h.ftown,h.fstreet,h.fstreetnum,r.froomnum from t_person p1 INNER JOIN");
//		if(SysStatic.one.equals(cardInfo.getOprType())){
//			sb.append(" t_hostinfo hs ON hs.id=p1.fentityid INNER JOIN t_roommanage r on r.fhostid=hs.id ");
//		}else if(SysStatic.two.equals(cardInfo.getOprType())){
//			sb.append(" t_lesseeinfo l on l.id=p1.fentityid INNER JOIN t_room_lessee rl on rl.flesseeid=l.id ")
//		     .append(" INNER JOIN t_roommanage r on r.id=rl.froomid ");
//		}
//			sb.append(" INNER JOIN t_domain d on d.fentityid=r.id INNER JOIN t_housemanage h on h.id=r.fhouseid where p1.id =?");	
//			values.add(cardInfo.getdwellerId());
//			List<Object[]> listObj = cardSqlDao.pageFindBySql(sb.toString(), values.toArray());
//			List<CardInfo> list = new ArrayList<CardInfo>();
//			if(listObj!=null&&!listObj.isEmpty()){
//				for(Object[] obj:listObj){
//					CardInfo icCard = new CardInfo();
//					icCard.setDomainSn(obj[0]==null?null:(Integer)obj[0]);//房间id
//					sb = new StringBuilder();
//					sb.append(domainMap.get(obj[1]))
//					 .append(domainMap.get(obj[2]))
//					 .append(domainMap.get(obj[3]))
//					 .append(domainMap.get(obj[4]))
//					 .append(obj[5]+"号")
//					 .append(obj[6]+"室");
//					  icCard.setAddress(sb.toString());//显示地址
//					  list.add(icCard);
//				}
//			}
		   return null;
	}

	/**写卡和入库
	 * @param cardInfo
	 * @throws NativeException 
	 * @throws IllegalAccessException 
	 * @throws IOException 
	 * @throws JsonException 
	 * @throws ParseException 
	 * @see com.youlb.biz.access.IPermissionBiz#writeCard(com.youlb.entity.access.CardInfo)
	 */
	@Override
	public int writeCard(CardInfo cardInfo) throws ParseException, JsonException, IOException,BizException  {
//		JNativeTest.writeBasicInfo(cardInfo);
		//添加ic卡信息
		//激活状态
		cardInfo.setCardStatus(SysStatic.LIVING);
		//TODO 设置卡片类型
		cardInfo.setCardType("1");
		//生成序号8位数
	    Session session = cardSqlDao.getCurrSession();
	    SQLQuery query = session.createSQLQuery("SELECT '1'||substring('000000'||nextval('tbl_card_seq'),length(currval('tbl_card_seq')||'')) ");
	    List<String> list =  query.list();
	    logger.info("生成序号8位数"+list.get(0));
	    cardInfo.setCardNo(Integer.parseInt(list.get(0)));
	    cardSqlDao.saveOrUpdate(cardInfo);
//	    Session currSession = cardSqlDao.getCurrSession();
	    session.flush();
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
		//更新办卡次数
		String update = "update t_room set fcardcount = CASE WHEN fcardcount is null then 0 ELSE fcardcount END+1 where id=?";
		cardSqlDao.updateSQL(update, new Object[]{cardInfo.getRoomId()});
		//推送白名单
		
		//指定发送设备（找到设备账号）
		String deviceCount = findDomainSn(cardInfo.getCardSn());
		logger.info("门口机账号："+deviceCount);
		//没有设备账号 说明没有安装没口机
		if(StringUtils.isBlank(deviceCount)){
			logger.info("没有安装门口机，设备账号为空");
		}else{
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost request = new HttpPost(SysStatic.HTTP+"/device/web_pull_blacklist.json");
			List<BasicNameValuePair> formParams = new ArrayList<BasicNameValuePair>();
			logger.info("白名单推送设备："+deviceCount);
			formParams.add(new BasicNameValuePair("deviceCount", deviceCount));
			BlackListData bcl = new BlackListData();
			bcl.addBc(new BlackListData.BlackCardData(0, cardInfo.getCardSn()));
			logger.info("白名单："+JsonUtils.toJson(bcl));
			formParams.add(new BasicNameValuePair("content", JsonUtils.toJson(bcl)));
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formParams, "UTF-8");
			request.setEntity(uefEntity);
			CloseableHttpResponse response = httpClient.execute(request);
			if(response.getStatusLine().getStatusCode()==200){
				HttpEntity entity_rsp = response.getEntity();
				ResultDTO resultDto = JsonUtils.fromJson(EntityUtils.toString(entity_rsp), ResultDTO.class);
				if(resultDto!=null){
					if(!"0".equals(resultDto.getCode())){
						logger.error(resultDto.getMsg());
						throw new BizException(resultDto.getMsg());
					}
				}else{
					logger.info("白名单推送成功！");
				}
			} 
		}
		return 0;
	}

	/**
	 * @param cardSn
	 * @return
	 * @see com.youlb.biz.access.IPermissionBiz#checkCardExist(java.lang.String)
	 */
	@Override
	public boolean checkCardExist(String cardSn) {
		String hql = "from CardInfo where cardSn=? and cardStatus !='3' ";//注销的可以重新发卡
		List<CardInfo> cardList = cardSqlDao.find(hql, new Object[]{cardSn});
		if(cardList.size()>0){
			return true;
		}
		return false;
	}

	/**
	 * @param cardInfo
	 * @return
	 * @see com.youlb.biz.access.IPermissionBiz#cardMap(com.youlb.entity.access.CardInfo)
	 */
	@Override
	public Map<String, CardInfo> cardMap(CardInfo cardInfo) {
		 StringBuilder sb = new StringBuilder();
		 List<Object> values = new ArrayList<Object>();
		 sb.append("from CardInfo t where t.roomId = ? ");
		 values.add(cardInfo.getRoomId());
		 sb.append(" and t.cardStatus =?");
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
		List<CardInfo> list = cardSqlDao.find(sb.toString(), values.toArray());
		Map<String,CardInfo> map = new HashMap<String, CardInfo>();
		if(list!=null&&!list.isEmpty()){
			for(CardInfo icCardInfo:list){
				map.put(icCardInfo.getCardSn(), icCardInfo);
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
		//如果是注销 需要解除卡跟人和卡跟房子的关联关系
		if(SysStatic.CANCEL.equals(cardInfo.getCardStatus())){
			//解除卡分人的关联
			sb.append(",t.oprType=null");
			//解除卡跟 房子的关联
			String delete ="delete from t_domain_card t where t.fcardsn=?";
			cardSqlDao.executeSql(delete, new Object[]{cardInfo.getCardSn()});
			//更新发卡数量-1
//			String update = "update t_dweller set fcardcount = CASE WHEN fcardcount is null or fcardcount<0 then 0 ELSE fcardcount END-1 where id=?";
//			cardSqlDao.updateSQL(update, new Object[]{cardInfo.getDwellerId()});
		}
		sb.append(" where t.cardSn= ?");
		cardSqlDao.update(sb.toString(),new Object[]{cardInfo.getCardStatus(),cardInfo.getCardSn()});
		
		//调用接口下发黑名单
		//挂失或者解挂需要推送黑名单
		if(SysStatic.LOSS.equals(cardInfo.getCardStatus())||SysStatic.LIVING.equals(cardInfo.getCardStatus())){
			//指定发送设备（找到设备账号）
			String deviceCount = findDomainSn(cardInfo.getCardSn());
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost request = new HttpPost(SysStatic.HTTP+"/device/web_pull_blacklist.json");
			List<BasicNameValuePair> formParams = new ArrayList<BasicNameValuePair>();
			formParams.add(new BasicNameValuePair("deviceCount", deviceCount));
			BlackListData bcl = new BlackListData();
			//挂失
			if(SysStatic.LOSS.equals(cardInfo.getCardStatus())){
				bcl.addBc(new BlackListData.BlackCardData(1, cardInfo.getCardSn()));
			//解挂		
			}else{
				bcl.addBc(new BlackListData.BlackCardData(0, cardInfo.getCardSn()));
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
		}
	}
	
	
	/**通过卡片序列号找到该卡拥有权限的domainsn 多个使用,分隔
	 * @param cardSn
	 * @return
	 */
	private String findDomainSn(String cardSn) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT dm.fdomainsn from t_devicecount t INNER JOIN t_domain dm on dm.id=t.fdomainid where t.fdomainid in ( ")
		.append("WITH RECURSIVE r AS ( ")
		.append("SELECT d.* from  t_cardinfo ci INNER JOIN t_domain d ON d.fentityid=ci.froomid where ci.fcardsn=? ")
		.append("union SELECT t_domain.* FROM t_domain, r WHERE t_domain.id = r.fparentid) ")
		.append("SELECT r.id FROM r where r.fentityid is not null) and t.fcounttype='1' ");
		List<Integer> deviceCountList = cardSqlDao.pageFindBySql(sb.toString(), new Object[]{cardSn});
		if(deviceCountList!=null&&!deviceCountList.isEmpty()){
			StringBuilder deviceCountStr = new StringBuilder();
			for(Integer deviceCount:deviceCountList){
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
	 * @see com.youlb.biz.access.IPermissionBiz#findAddress(com.youlb.entity.access.CardInfo)
	 */
	@Override
	public String findAddressByRoomId(String roomId) {
		StringBuffer sb = new StringBuffer();
		sb.append("select array_to_string (ARRAY(WITH RECURSIVE r AS (SELECT * FROM t_domain WHERE fentityid = ?")
		.append(" union ALL SELECT t_domain.* FROM t_domain, r WHERE t_domain.id = r.fparentid) SELECT  fremark FROM r where flayer is not null ORDER BY flayer),'')");
		 List<String> listObj = cardSqlDao.pageFindBySql(sb.toString(), new Object[]{roomId});
		 if(listObj!=null&&!listObj.isEmpty()){
			 return listObj.get(0);
		 }
		return null;
	}
    /**
     * 递归获取详细地址集合
     * @param map key=id-domainSn  value= StringBuilder
     * @return
     */
	private Map<String,StringBuilder> getAddress(Map<String,StringBuilder> map){
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
	 * @see com.youlb.biz.access.IPermissionBiz#findAddressByCardSn(com.youlb.entity.access.CardInfo)
	 */
	@Override
	public List<CardInfo> findAddressByCardSn(CardInfo cardInfo) {
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
	 * @see com.youlb.biz.access.IPermissionBiz#appRecordList(com.youlb.entity.access.CardInfo, com.youlb.entity.privilege.Operator)
	 */
	@Override
	public List<CardRecord> appRecordList(CardRecord cardRecord, Operator loginUser) {
		cardRecord.setMode("5");//app开锁类型
		List<CardRecord> list = cardRecord(cardRecord, loginUser);
		return list;
	}
	/**开锁方法
	 * @param cardRecord
	 * @param loginUser
	 * @return
	 * @see com.youlb.biz.access.IPermissionBiz#cardRecord(com.youlb.entity.access.CardInfo, com.youlb.entity.privilege.Operator)
	 */
	@Override
	public List<CardRecord> cardRecord(CardRecord cardRecord, Operator loginUser) {
		StringBuilder sb = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		sb.append("select * from(SELECT cr.fcardsn cardsn,cr.ftime cardtime,cr.fpath imgpath,cr.id id,cr.fusername username,dc.fdomainid domainid")
		.append(" from t_cardrecord cr INNER JOIN t_devicecount dc on dc.fdevicecount=cr.fusername where cr.fmode=?");
		values.add(cardRecord.getMode());//纪录类型
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
				if(obj[5]!=null){
					 List<String> address = cardSqlDao.pageFindBySql(addressSql, new Object[]{(String)obj[5]});
					 if(address!=null&&!address.isEmpty()){
						 info.setAddress(address.get(0));
					 }
				}
				list.add(info);
			}
		}
		return list;
	}
	/**
	 * @param cardSn
	 * @return
	 * @see com.youlb.biz.access.IPermissionBiz#getImg(java.lang.String)
	 */
	@Override
	public CardInfo getImg(Integer id) {
		String sql ="select cr.fpath from t_cardrecord cr where cr.id=?";
		List<String> path = cardSqlDao.pageFindBySql(sql, new Object[]{id});
		CardInfo info = new CardInfo();
		info.setPath(path.get(0));
		return info;
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
}
