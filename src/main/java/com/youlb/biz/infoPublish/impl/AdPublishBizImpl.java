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

import com.youlb.biz.infoPublish.IAdPublishBiz;
import com.youlb.dao.common.BaseDaoBySql;
import com.youlb.entity.common.ResultDTO;
import com.youlb.entity.infoPublish.AdPublish;
import com.youlb.entity.infoPublish.AdPublishDto;
import com.youlb.entity.infoPublish.AdPublishPicture;
import com.youlb.entity.infoPublish.AdPublishPictureDto;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.common.JsonUtils;
import com.youlb.utils.common.SysStatic;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.exception.JsonException;
import com.youlb.utils.helper.DateHelper;
import com.youlb.utils.helper.OrderHelperUtils;
import com.youlb.utils.helper.SearchHelper;

/** 
 * @ClassName: AdPublishBizImpl.java 
 * @Description: 信息发布业务实现类 
 * @author: Pengjy
 * @date: 2015-12-1
 * 
 */
@Service("adPublishBiz")
public class AdPublishBizImpl implements IAdPublishBiz {
	
	
	@Autowired
    private BaseDaoBySql<AdPublish> adPublishSqlDao;
	public void setAdPublishSqlDao(BaseDaoBySql<AdPublish> adPublishSqlDao) {
		this.adPublishSqlDao = adPublishSqlDao;
	}
	@Autowired
    private BaseDaoBySql<AdPublishPicture> adPiclDao;
	public void setAdPiclDao(BaseDaoBySql<AdPublishPicture> adPiclDao) {
		this.adPiclDao = adPiclDao;
	}
	/** 日志输出 */
	private static Logger logger = LoggerFactory.getLogger(AdPublishBizImpl.class);
	/**
	 * @param target
	 * @return
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#save(java.io.Serializable)
	 */
	@Override
	public String save(AdPublish target) throws BizException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param target
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#update(java.io.Serializable)
	 */
	@Override
	public void update(AdPublish target) throws BizException {
		// TODO Auto-generated method stub

	}

	/**
	 * @param id
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Serializable id) throws BizException {
		adPublishSqlDao.delete(id);
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
	public AdPublish get(Serializable id) throws BizException {
		AdPublish adPublish = adPublishSqlDao.get(id);
		//选择区域数据
		String hql = "select da.fdomainid from t_domain_adpublish da where da.fadpublishid=?";
		List<String> domainIds = adPublishSqlDao.pageFindBySql(hql, new Object[]{id});
		adPublish.setTreecheckbox(domainIds);
		//图片列表数据
//		String find = "from AdPublishPicture t where t.adpublishId=?";
//		List<AdPublishPicture> adPics = adPiclDao. find(find, new Object[]{id});
		adPublish.setAdPics(getPicByAdpublishId((String) id));
		return adPublish;
	}

	/**
	 * @param target
	 * @param loginUser
	 * @return
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#showList(java.io.Serializable, com.youlb.entity.privilege.Operator)
	 */
	@Override
	public List<AdPublish> showList(AdPublish target, Operator loginUser)throws BizException {
		StringBuilder sb = new StringBuilder();
		List<Object> valuse = new ArrayList<Object>();
		sb.append("SELECT * from (SELECT ad.id id,ad.fadtype adType,ad.ftargetdevice targetDevice,ad.fcreatetime createTime,ad.fsendtype sendType,ad.fexpdate expDate,")
	    .append("ad.fstatus status,ad.fpublish_time publishTime,ad.fpublish_operator publishOperator,ad.fadd_operator addOperator,case when ad.fcarrierid=? then true else false end self  ")
		.append("from t_adpublish ad INNER JOIN t_domain_adpublish tda on tda.fadpublishid=ad.id where 1=1");
		valuse.add(loginUser.getCarrier().getId());

		//普通用户过滤运营商
		List<String> domainIds = loginUser.getDomainIds();
		if(!SysStatic.SPECIALADMIN.equals(loginUser.getIsAdmin())&&domainIds!=null&&!domainIds.isEmpty()){
			sb.append(SearchHelper.jointInSqlOrHql(domainIds, " tda.fdomainid "));
			valuse.add(domainIds);
		}
//		if(StringUtils.isNotBlank(target.getTitle())){
//			sb.append(" and t.title like ?");
//			valuse.add("%"+target.getTitle()+"%");
//		}
//		if(StringUtils.isNotBlank(target.getAdType())){
//			sb.append(" and t.adType = ?");
//			valuse.add(target.getAdType());
//		}
		if(StringUtils.isNotBlank(target.getTargetDevice())){
			sb.append(" and ad.ftargetdevice = ?");
			valuse.add(target.getTargetDevice());
		}
//		if(StringUtils.isNotBlank(target.getAdSign())){
//			sb.append(" and t.adSign like ?");
//			valuse.add("%"+target.getAdSign()+"%");
//		}
		if(StringUtils.isNotBlank(target.getStartTime())){
			sb.append(" and to_char(ad.fcreatetime,'yyyy-MM-dd') >= ?");
			valuse.add(target.getStartTime());
		}
		if(StringUtils.isNotBlank(target.getEndTime())){
			sb.append(" and to_char(ad.fcreatetime,'yyyy-MM-dd') <= ?");
			valuse.add(target.getEndTime());
		}
		sb.append(" GROUP BY ad.id,ad.fadtype,ad.ftargetdevice,ad.fcreatetime,ad.fsendtype,ad.fexpdate,ad.fstatus,ad.fpublish_time,ad.fpublish_operator,ad.fadd_operator ");
		sb.append(")t");
		OrderHelperUtils.getOrder(sb, target, "t.", "t.createTime desc");
		List<AdPublish> list = new ArrayList<AdPublish>();
		List<Object[]> listObj = adPublishSqlDao.pageFindBySql(sb.toString(), valuse.toArray(), target.getPager());
		if(listObj!=null&&!listObj.isEmpty()){
			for(Object[] obj :listObj){
				AdPublish ad = new AdPublish();
				ad.setPager(target.getPager());
				ad.setId(obj[0]==null?"":(String)obj[0]);
				ad.setAdType(obj[1]==null?"":(String)obj[1]);
				ad.setTargetDevice(obj[2]==null?"":(String)obj[2]);
				ad.setCreateTime(obj[3]==null?null:(Date)obj[3]);
				ad.setSendType(obj[4]==null?"":(String)obj[4]);
				ad.setExpDate(obj[5]==null?null:(Date)obj[5]);
				ad.setStatus(obj[6]==null?"":(String)obj[6]);
				ad.setPublishTime(obj[7]==null?null:(Date)obj[7]);
				ad.setPublishOperator(obj[8]==null?"":(String)obj[8]);
				ad.setAddOperator(obj[9]==null?"":(String)obj[9]);
				ad.setSelf(obj[10]==null?null:(Boolean)obj[10]);
				list.add(ad);
			}
		}
		return list;
	}
	/**
	 * @param adPublish
	 * @param loginUser
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IOException 
	 * @throws JsonException 
	 * @throws ParseException 
	 * @throws BizException 
	 * @see com.youlb.biz.adPublish.IAdPublishBiz#saveOrUpdate(com.youlb.entity.adPublish.adPublish, com.youlb.entity.privilege.Operator)
	 */
	@Override
	public void saveOrUpdate(AdPublish adPublish, Operator loginUser) throws IllegalAccessException, InvocationTargetException, ParseException, JsonException, IOException, BizException {
		//设置图片对象
		if(adPublish.getPicId()!=null){
			StringBuffer sb = new StringBuffer();
			List<Object> values = new ArrayList<Object>();
			sb.append("from AdPublishPicture t where t.id in(");
			for(String picId:adPublish.getPicId()){
				sb.append("?,");
				values.add(picId);
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append(")");
			List<AdPublishPicture> adPics = adPiclDao.find(sb.toString(), values.toArray());
			adPublish.setAdPics(adPics);
		}
		if(StringUtils.isNotBlank(adPublish.getExpDateStr())){
			Date expDate = DateHelper.strParseDate(adPublish.getExpDateStr(), "yyyy-MM-dd");
			adPublish.setExpDate(expDate);
		}
		 //设置添加人
		String operator = "";
		//普通管理员 的真实姓名已经包含 admin
		if(SysStatic.NORMALADMIN.equals(loginUser.getIsAdmin())){
			operator= loginUser.getRealName();
		}else{
			operator = loginUser.getRealName()+"("+loginUser.getLoginName()+")";
		}		
		//设置添加人
		adPublish.setAddOperator(operator);
		//获取推送标签(标签就是domainid)
		List<String> tagList = getTagList(adPublish,loginUser);
		//add
		if(StringUtils.isBlank(adPublish.getId())){
			//设置运营商
			adPublish.setCarrierId(loginUser.getCarrier().getId());
			adPublish.setAdType("1");//图片类型
			String id = (String) adPublishSqlDao.add(adPublish);
			List<String> adPics = adPublish.getPicId();
			//更新图片的adPulibshId
			if(adPics!=null&&!adPics.isEmpty()){
				String update = "update t_adpublis_picture set fadpublishid=? where id=?";
				for(String adPic :adPics){
					adPiclDao.updateSQL(update, new Object[]{id,adPic});
				}
			}
			if(tagList!=null&&!tagList.isEmpty()){
				//把需要指定域保存中间表 全部推送保存的是社区级doaminid 指定范围的保存的是指定domainid
				String sql="insert into t_domain_adpublish(fadpublishid,fdomainid) values (?,?)";
				for(String domainid:tagList){
					adPublishSqlDao.executeSql(sql, new Object[]{id,domainid});
				}
			}
		}else{
		    //update	
			adPublishSqlDao.update(adPublish);
			//更新图片的adPulibshId
			List<String> adPics = adPublish.getPicId();
			if(adPics!=null&&!adPics.isEmpty()){
				String update = "update t_adpublis_picture set fadpublishid=? where id=?";
				for(String adPic :adPics){
					adPiclDao.updateSQL(update, new Object[]{adPublish.getId(),adPic});
				}
			}
			//删除中间表纪录
			String delete = "delete from t_domain_adpublish where fadpublishid=?";
			adPublishSqlDao.executeSql(delete, new Object[]{adPublish.getId()});
			if(tagList!=null&&!tagList.isEmpty()){
				//把需要指定域保存中间表 全部推送保存的是社区级doaminid 指定范围的保存的是指定domainid
				String sql="insert into t_domain_adpublish(fadpublishid,fdomainid) values (?,?)";
				for(String domainid:tagList){
					adPublishSqlDao.executeSql(sql, new Object[]{adPublish.getId(),domainid});
				}
			}
//			//指定区域发布消息
//			if(SysStatic.two.equals(adPublish.getSendType())){
//				if(adPublish.getTreecheckbox()!=null){
//					String sql="insert into t_domain_adpublish(fadpublishid,fdomainid) values (?,?)";
//					for(String entityId:adPublish.getTreecheckbox()){
//						adPublishSqlDao.executeSql(sql, new Object[]{adPublish.getId(),entityId});
//					}
//				}
//			}else if(SysStatic.one.equals(adPublish.getSendType())){
//				
//				
//			}
		}
		
	}
	
	@Override
	public void publish(String[] ids, Operator loginUser)throws IllegalAccessException, InvocationTargetException,UnsupportedEncodingException, ClientProtocolException, IOException,ParseException, JsonException, BizException {
		if(ids!=null){
			for(String id:ids){
				AdPublish adPublish = get(id);
				//重新封装dto
				List<AdPublishPictureDto> picDtoList = new ArrayList<AdPublishPictureDto>();
				if(adPublish.getAdPics()!=null&&!adPublish.getAdPics().isEmpty()){
					for(AdPublishPicture pic:adPublish.getAdPics()){
						AdPublishPictureDto picDto = new AdPublishPictureDto();
						BeanUtils.copyProperties(picDto,pic);
						picDtoList.add(picDto);
					}
				}
				AdPublishDto dto = new AdPublishDto();
				Date publishTime = new Date();
				adPublish.setPublishTime(publishTime);
				//封装参数对象
				BeanUtils.copyProperties(dto, adPublish);
				dto.setAdPics(picDtoList);
				//获取推送标签(标签就是domainid)
				List<String> tagList = adPublish.getTreecheckbox();
				if(tagList==null||tagList.isEmpty()){
					throw new BizException("未找到任何标签");
				}
				logger.info("tagList："+Arrays.toString(tagList.toArray()));
				logger.info("adPublish:"+JsonUtils.toJson(dto));
				
				dto.setTagList(tagList);
//				//调用信息推送接口
				CloseableHttpClient httpClient = HttpClients.createDefault();
				//公告 通知需要透传消息
				HttpPost request = new HttpPost(SysStatic.HTTP+"/publish/web_pushAdMess.json");
				List<BasicNameValuePair> formParams = new ArrayList<BasicNameValuePair>();
				//获取服务器ip
				formParams.add(new BasicNameValuePair("adPublish", JsonUtils.toJson(dto)));
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
					//设置添加人
					String operator = "";
					//普通管理员 的真实姓名已经包含 admin
					if(SysStatic.NORMALADMIN.equals(loginUser.getIsAdmin())){
						operator= loginUser.getRealName();
					}else{
						operator = loginUser.getRealName()+"("+loginUser.getLoginName()+")";
					}
					String update ="update AdPublish set publishOperator=?,publishTime=?,status=? where id=?";
					adPublishSqlDao.update(update, new Object[]{operator,publishTime,"1",id});
				}else{
					throw new BizException("信息推送失败！");
				}
			}
		}
	}

	 
	/**
	 * 获取门口机channelid
	 * @param treecheckbox
	 * @param loginUser 全部时过滤运营商
	 * @return
	 * @throws BizException 
	 */
	 
	private List<String> getTagList(AdPublish adPublish,Operator loginUser) throws BizException {
		 //全部推送==是按该运营商下面的所以社区tag
		 if("1".equals(adPublish.getSendType())){
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
			 List<String> tagList = adPublishSqlDao.pageFindBySql(sb.toString(), values.toArray());
			 return tagList;
	     //选择一个域对象直接就是标签		 
		 }else if("2".equals(adPublish.getSendType())){
			 List<String> treecheckbox = adPublish.getTreecheckbox();
			 return treecheckbox;
		 }
		return null;
	}

	/**添加图片记录
	 * @param adPic
	 * @return
	 * @throws BizException 
	 * @see com.youlb.biz.infoPublish.IAdPublishBiz#addPicture(com.youlb.entity.infoPublish.AdPublishPicture)
	 */
	@Override
	public String addPicture(AdPublishPicture adPic) throws BizException {
		String id = (String) adPiclDao.add(adPic);
		return id;
	}

	/**删除图片
	 * @param picId
	 * @throws BizException 
	 * @see com.youlb.biz.infoPublish.IAdPublishBiz#deletePicture(java.lang.String)
	 */
	@Override
	public void deletePicture(String picId) throws BizException {
		adPiclDao.delete(picId);
	}

	@Override
	public List<AdPublishPicture> getPicByAdpublishId(String id) throws BizException {
		//图片列表数据
		String find = "from AdPublishPicture t where t.adpublishId=?";
		List<AdPublishPicture> adPics = adPiclDao.find(find, new Object[]{id});
		return adPics;
	}

}
