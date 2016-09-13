package com.youlb.biz.infoPublish.impl;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youlb.biz.infoPublish.IInfoPublishBiz;
import com.youlb.dao.common.BaseDaoBySql;
import com.youlb.entity.common.ResultDTO;
import com.youlb.entity.infoPublish.InfoPublish;
import com.youlb.entity.infoPublish.InfoPublishDto;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.common.JsonUtils;
import com.youlb.utils.common.SysStatic;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.exception.JsonException;
import com.youlb.utils.helper.DateHelper;
import com.youlb.utils.helper.OrderHelperUtils;
import com.youlb.utils.helper.SearchHelper;

/** 
 * @ClassName: InfoPublishBiz.java 
 * @Description: 信息发布业务实现类 
 * @author: Pengjy
 * @date: 2015-12-1
 * 
 */
@Service("infoPublishBiz")
public class InfoPublishBizImpl implements IInfoPublishBiz {
	@Autowired
    private BaseDaoBySql<InfoPublish> infoPublishSqlDao;
	public void setInfoPublishSqlDao(BaseDaoBySql<InfoPublish> infoPublishSqlDao) {
		this.infoPublishSqlDao = infoPublishSqlDao;
	}
	/** 日志输出 */
	private static Logger logger = LoggerFactory.getLogger(InfoPublishBizImpl.class);
	/**
	 * @param target
	 * @return
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#save(java.io.Serializable)
	 */
	@Override
	public String save(InfoPublish target) throws BizException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param target
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#update(java.io.Serializable)
	 */
	@Override
	public void update(InfoPublish target) throws BizException {
		// TODO Auto-generated method stub

	}

	/**
	 * @param id
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Serializable id) throws BizException {
		infoPublishSqlDao.delete(id);
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
	 * 只能删除本运营商的公告
	 * @param ids
	 * @param loginUser
	 * @throws BizException 
	 * @see com.youlb.biz.infoPublish.IInfoPublishBiz#delete(java.lang.String[], com.youlb.entity.privilege.Operator)
	 */
	@Override
	public void delete(String[] ids, Operator loginUser) throws BizException {
		 String delete ="delete from t_infopublish where id=? and fcarrierid=?";
		 if(ids!=null){
				for(String id:ids){
					infoPublishSqlDao.executeSql(delete, new Object[]{id,loginUser.getCarrier().getId()});
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
	public InfoPublish get(Serializable id) throws BizException {
		InfoPublish infoPublish = infoPublishSqlDao.get(id);
		String hql = "select da.fdomainid from t_domain_infopublish da where da.finfopublishid=?";
		List<String> domainIds = infoPublishSqlDao.pageFindBySql(hql, new Object[]{id});
		infoPublish.setTreecheckbox(domainIds);
		return infoPublish;
	}

	/**
	 * @param target
	 * @param loginUser
	 * @return
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#showList(java.io.Serializable, com.youlb.entity.privilege.Operator)
	 */
	@Override
	public List<InfoPublish> showList(InfoPublish target, Operator loginUser)throws BizException {
		StringBuilder sb = new StringBuilder();
		List<Object> valuse = new ArrayList<Object>();
		sb.append("select * from (SELECT i.id id,i.ftitle title,i.finfotype infoType,i.ftargetdevice targetDevice,i.finfosign infoSign,i.finfodetail infoDetail,i.fsendtype sendType,")
		.append("i.fcreatetime createTime,i.fexpdate expDate,case when i.fcarrierid=? then true else false end self,i.fstatus status,i.fpublish_time publishTime,")
		.append("i.fpublish_operator publishOperator,i.fadd_operator addOperator ")
		.append("from t_infopublish i INNER JOIN t_domain_infopublish tdi on tdi.finfopublishid=i.id where 1=1 ");
		valuse.add(loginUser.getCarrier().getId());
		//普通用户过滤运营商
		List<String> domainIds = loginUser.getDomainIds();
		if(!SysStatic.SPECIALADMIN.equals(loginUser.getIsAdmin())&&domainIds!=null&&!domainIds.isEmpty()){
			sb.append(SearchHelper.jointInSqlOrHql(domainIds, " tdi.fdomainid "));
			valuse.add(domainIds);
		}
		sb.append(" group by i.id,i.ftitle,i.finfotype,i.ftargetdevice,i.finfosign,i.finfodetail,i.fsendtype,i.fcreatetime,i.fexpdate)t where 1=1");
		if(StringUtils.isNotBlank(target.getTitle())){
			sb.append(" and t.title like ?");
			valuse.add("%"+target.getTitle()+"%");
		}
		if(StringUtils.isNotBlank(target.getInfoType())){
			sb.append(" and t.infoType = ?");
			valuse.add(target.getInfoType());
		}
		if(StringUtils.isNotBlank(target.getTargetDevice())){
			sb.append(" and targetDevice = ?");
			valuse.add(target.getTargetDevice());
		}
		if(StringUtils.isNotBlank(target.getInfoSign())){
			sb.append(" and infoSign like ?");
			valuse.add("%"+target.getInfoSign()+"%");
		}
		if(StringUtils.isNotBlank(target.getStartTime())){
			sb.append(" and to_char(createTime,'yyyy-MM-dd') >= ?");
			valuse.add(target.getStartTime());
		}
		if(StringUtils.isNotBlank(target.getEndTime())){
			sb.append(" and to_char(createTime,'yyyy-MM-dd') <= ?");
			valuse.add(target.getEndTime());
		}
		OrderHelperUtils.getOrder(sb, target, "t.", "t.createTime desc");
		List<Object[]> listObj = infoPublishSqlDao.pageFindBySql(sb.toString(), valuse.toArray(), target.getPager());
		List<InfoPublish> list = new ArrayList<InfoPublish>();
		if(listObj!= null&&!listObj.isEmpty()){
			for(Object[] obj:listObj){
				InfoPublish info = new InfoPublish();
				info.setPager(target.getPager());
				info.setId(obj[0]==null?"":(String)obj[0]);
				info.setTitle(obj[1]==null?"":(String)obj[1]);
				info.setInfoType(obj[2]==null?"":(String)obj[2]);
				info.setTargetDevice(obj[3]==null?"":(String)obj[3]);
				info.setInfoSign(obj[4]==null?"":(String)obj[4]);
				info.setInfoDetail(obj[5]==null?"":(String)obj[5]);
				info.setSendType(obj[6]==null?"":(String)obj[6]);
				info.setCreateTime(obj[7]==null?null:(Date)obj[7]);
				info.setExpDate(obj[8]==null?null:(Date)obj[8]);
				info.setSelf(obj[9]==null?null:(Boolean)obj[9]);
				info.setStatus(obj[10]==null?"":(String)obj[10]);
				info.setPublishTime(obj[11]==null?null:(Date)obj[11]);
				info.setPublishOperator(obj[12]==null?"":(String)obj[12]);
				info.setAddOperator(obj[13]==null?"":(String)obj[13]);
				list.add(info);
			}
		}
		return list;
	}

	/**（推送相关的修改功能暂时没用）
	 * @param infoPublish
	 * @param loginUser
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws JsonException 
	 * @throws ParseException 
	 * @throws BizException 
	 * @see com.youlb.biz.infoPublish.IInfoPublishBiz#saveOrUpdate(com.youlb.entity.infoPublish.InfoPublish, com.youlb.entity.privilege.Operator)
	 */
	@Override
	public void saveOrUpdate(InfoPublish infoPublish, Operator loginUser) throws ClientProtocolException, IOException, IllegalAccessException, InvocationTargetException, ParseException, JsonException, BizException {
		
//		设置运营商
		infoPublish.setCarrierId(loginUser.getCarrier().getId());
		//日期转换
		String expDateStr = infoPublish.getExpDateStr();
		if(StringUtils.isNotBlank(expDateStr)){
			Date expDate = DateHelper.strParseDate(expDateStr, "yyyy-MM-dd");
			infoPublish.setExpDate(expDate);
		}
		 //设置添加人
		String operator = "";
		//普通管理员 的真实姓名已经包含 admin
		if(SysStatic.NORMALADMIN.equals(loginUser.getIsAdmin())){
			operator= loginUser.getRealName();
		}else{
			operator = loginUser.getRealName()+"("+loginUser.getLoginName()+")";
		}
		 infoPublish.setAddOperator(operator);
		 //获取推送标签(标签就是domainid)
		 List<String> tagList = getTagList(infoPublish,loginUser);
		//add
		if(StringUtils.isBlank(infoPublish.getId())){
			//全部发送
			 if("1".equals(infoPublish.getSendType())){
				 infoPublish.setInfoType("2");//社区消息
			 }else if("2".equals(infoPublish.getSendType())){
				 //判断信息类型 个人、社区、系统
				 List<String> treecheckbox = infoPublish.getTreecheckbox();
				 if(treecheckbox!=null&&!treecheckbox.isEmpty()){
					 for(String domainId :treecheckbox){
						 //获取域的级别
						 Integer layer = getDomainLayerById(domainId);
						 if(layer!=null){
							 if(layer>=2){
								 infoPublish.setInfoType("1");//个人消息
							 }else{
								 infoPublish.setInfoType("2");//社区消息
							 }
						 }
					 }
				 }
				 
			 }
			String id = (String) infoPublishSqlDao.add(infoPublish);
			if(tagList!=null&&!tagList.isEmpty()){
				//把需要指定域保存中间表 全部推送保存的是社区级doaminid 指定范围的保存的是指定domainid
				String sql="insert into t_domain_infopublish(finfopublishid,fdomainid) values (?,?)";
				for(String domainid:tagList){
					infoPublishSqlDao.executeSql(sql, new Object[]{id,domainid});
				}
			}
		}else{
		    //update
			infoPublishSqlDao.update(infoPublish);
			
			//删除中间表纪录
			String delete = "delete from t_domain_infopublish where finfopublishid=?";
			infoPublishSqlDao.executeSql(delete, new Object[]{infoPublish.getId()});
			
			if(tagList!=null&&!tagList.isEmpty()){
				//把需要指定域保存中间表 全部推送保存的是社区级doaminid 指定范围的保存的是指定domainid
				String sql="insert into t_domain_infopublish(finfopublishid,fdomainid) values (?,?)";
				for(String domainid:tagList){
					infoPublishSqlDao.executeSql(sql, new Object[]{infoPublish.getId(),domainid});
				}
			}
		}
		
	}
	
	
	/**
     * 发布
     * @param ids
     * @param loginUser
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws JsonException 
	 * @throws ParseException 
	 * @throws BizException 
     */
	@Override
	public void publish(String[] ids, Operator loginUser) throws IllegalAccessException, InvocationTargetException, ClientProtocolException, IOException, ParseException, JsonException, BizException {
		if(ids!=null){
			for(String id:ids){
				InfoPublish infoPublish = get(id);
				InfoPublishDto dto = new InfoPublishDto();
				Date publishTime = new Date();
				infoPublish.setPublishTime(publishTime);
				//封装参数对象
				BeanUtils.copyProperties(dto, infoPublish);
				dto.setId(id);
				//获取推送标签(标签就是domainid)
				List<String> tagList = infoPublish.getTreecheckbox();
				if(tagList==null||tagList.isEmpty()){
					throw new BizException("未找到任何标签");
				}
				dto.setTagList(tagList);
				logger.info("tagList："+Arrays.toString(infoPublish.getTreecheckbox().toArray()));
				logger.info("infoPublish:"+JsonUtils.toJson(dto));
				//调用信息推送接口
				CloseableHttpClient httpClient = HttpClients.createDefault();
				//公告 通知需要透传消息
				HttpPost request = new HttpPost(SysStatic.HTTP+"/publish/web_pushMess.json");
				List<BasicNameValuePair> formParams = new ArrayList<BasicNameValuePair>();
				//获取服务器ip
				formParams.add(new BasicNameValuePair("infoPublish", JsonUtils.toJson(dto)));
				UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formParams, "UTF-8");
				request.setEntity(uefEntity);
				CloseableHttpResponse response = httpClient.execute(request);
				if(response.getStatusLine().getStatusCode()==200){
					HttpEntity entity_rsp = response.getEntity();
					ResultDTO resultDto = JsonUtils.fromJson(EntityUtils.toString(entity_rsp), ResultDTO.class);
					if(resultDto!=null){
						if(!"0".equals(resultDto.getCode())){
							throw new BizException("信息推送"+resultDto.getMsg());
						}
					}
					String update ="update InfoPublish set publishOperator=?,publishTime=?,status=? where id=?";
					//设置添加人
					String operator = "";
					//普通管理员 的真实姓名已经包含 admin
					if(SysStatic.NORMALADMIN.equals(loginUser.getIsAdmin())){
						operator= loginUser.getRealName();
					}else{
						operator = loginUser.getRealName()+"("+loginUser.getLoginName()+")";
					}
					infoPublishSqlDao.update(update, new Object[]{operator,publishTime,"1",id});
				}else{
					throw new BizException("信息推送失败！");
				}
			}
			
		}
	}
    /**
     * 获取域的级别
     * @param domainId
     * @return
     * @throws BizException 
     */
	private Integer getDomainLayerById(String domainId) throws BizException {
		String sql ="select t.flayer from t_domain t where t.id=?";
		List<Integer> list = infoPublishSqlDao.pageFindBySql(sql, new Object[]{domainId});
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}

	/**获取域下面的用户channelId
	 * @param object
	 * @param loginUser
	 * @return

	private String getDwellerChannelIds(InfoPublish infoPublish, Operator loginUser) {
		List<String> domainIds = loginUser.getDomainIds();
		List<String> treecheckbox = infoPublish.getTreecheckbox();
		StringBuilder sb = new StringBuilder();
		 List<Object> values = new ArrayList<Object>();
		 sb.append("SELECT t.fchannelid from t_users t where t.fchannelid is not null ");
		 if("1".equals(infoPublish.getSendType())){
			 if(SysStatic.SPECIALADMIN.equals(loginUser.getIsAdmin())){
				 return "";
			 }else{
				 sb.append(SearchHelper.filterDomain(domainIds,"t.faddress"));
				 values.add(domainIds);
			 }
		 }else if("2".equals(infoPublish.getSendType())){
			 sb.append("and t.faddress in(");
			 for(String domainId:treecheckbox){
				 values.add(domainId);
				 sb.append("?,");
			 }
			 sb.deleteCharAt(sb.length()-1);
			 sb.append(")");
		 }
		 List<String> channelIds = infoPublishSqlDao.pageFindBySql(sb.toString(), values.toArray());
		 if(channelIds!=null&&!channelIds.isEmpty()){
			 StringBuilder channelStr = new StringBuilder();
				for(String channel:channelIds){
					channelStr.append(channel+",");
				}
				channelStr.deleteCharAt(channelStr.length()-1);
				return channelStr.toString();
		 }
		return "";
	}
	 */
	/**
	 * 获取门口机channelid
	 * @param treecheckbox
	 * @param loginUser 全部时过滤运营商
	 * @return
	 * @throws BizException 
	 */
	 
	private List<String> getTagList(InfoPublish infoPublish,Operator loginUser) throws BizException {
		 //全部推送==是按该运营商下面的所以社区tag
		 if("1".equals(infoPublish.getSendType())){
			 StringBuilder sb = new StringBuilder();
			 List<Object> values = new ArrayList<Object>();
			 //特殊admin 全部的域
			 if(SysStatic.SPECIALADMIN.equals(loginUser.getIsAdmin())){
				 sb.append("select t.id from t_domain t where t.flayer='1'");
			 }else{
				 List<String> domainIds = loginUser.getDomainIds();
				 sb.append("select t.id from t_domain t where t.id in(");
				 for(String domainId:domainIds){
					 sb.append("?,");
					 values.add(domainId);
				 }
				 sb.deleteCharAt(sb.length()-1);
				 sb.append(") and t.flayer='1'");
			 }
			 List<String> tagList = infoPublishSqlDao.pageFindBySql(sb.toString(), values.toArray());
			 return tagList;
	     //选择一个域对象直接就是标签		 
		 }else if("2".equals(infoPublish.getSendType())){
			 List<String> treecheckbox = infoPublish.getTreecheckbox();
			 return treecheckbox;
		 }
		return null;
	}


}
