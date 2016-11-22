package com.youlb.biz.appManage.impl;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
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
import org.springframework.web.multipart.MultipartFile;

import com.youlb.biz.access.impl.PermissionBizImpl;
import com.youlb.biz.appManage.IAppManageBiz;
import com.youlb.dao.common.BaseDaoBySql;
import com.youlb.entity.access.BlackListData;
import com.youlb.entity.appManage.AppManage;
import com.youlb.entity.appManage.VersionInfo;
import com.youlb.entity.common.ResultDTO;
import com.youlb.entity.infoPublish.AdPublish;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.common.JsonUtils;
import com.youlb.utils.common.SysStatic;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.exception.JsonException;
import com.youlb.utils.helper.OrderHelperUtils;

/** 
 * @ClassName: AppManageBiz.java 
 * @Description: app管理业务实现类 
 * @author: Pengjy
 * @date: 2015-11-26
 * 
 */
@Service("appManageBiz")
public class AppManageBiz implements IAppManageBiz {
	
	/** 日志输出 */
	private static Logger logger = LoggerFactory.getLogger(AppManageBiz.class);
	@Autowired
    private BaseDaoBySql<AppManage> appManageSqlDao;
	public void setAppManageSqlDao(BaseDaoBySql<AppManage> appManageSqlDao) {
		this.appManageSqlDao = appManageSqlDao;
	}

	/**
	 * @param target
	 * @return
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#save(java.io.Serializable)
	 */
	@Override
	public String save(AppManage target) throws BizException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param target
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#update(java.io.Serializable)
	 */
	@Override
	public void update(AppManage target) throws BizException {
		StringBuilder sb = new StringBuilder();
		sb.append("update AppManage set autoInstal=?,appName=?,versionName=?,versionCode=?,packageName=?,versionDes=?,iconUrl=?,softwareType=? where id=?");
		appManageSqlDao.update(sb.toString(),new Object[]{target.getAutoInstal(),target.getAppName(),target.getVersionName(),target.getVersionCode(),
			target.getPackageName(),target.getVersionDes(),target.getIconUrl(),target.getSoftwareType(),target.getId()});
	}

	/**
	 * @param id
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Serializable id) throws BizException {
		appManageSqlDao.delete(id);
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
	public AppManage get(Serializable id) throws BizException {
		AppManage appManage = appManageSqlDao.get(id);
		String hql = "select da.fdomainid from t_domain_appmanage da where da.fappmanageid=?";
		List<String> domainIds = appManageSqlDao.pageFindBySql(hql, new Object[]{id});
		appManage.setTreecheckbox(domainIds);
		return appManage;
	}

	/**
	 * @param target
	 * @param loginUser
	 * @return
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#showList(java.io.Serializable, com.youlb.entity.privilege.Operator)
	 */
	@Override
	public List<AppManage> showList(AppManage target, Operator loginUser)throws BizException {
		StringBuilder sb = new StringBuilder();
		List<Object> valuse = new ArrayList<Object>();
		sb.append("from AppManage t where 1=1");
		if(StringUtils.isNotBlank(target.getAppType())){
			sb.append(" and t.appType = ?");
			valuse.add(target.getAppType());
		}else{
			return null;
		}
		if(StringUtils.isNotBlank(target.getVersionName())){
			sb.append(" and t.versionName like ?");
			valuse.add("%"+target.getVersionName()+"%");
		}
		if(StringUtils.isNotBlank(target.getVersionCode())){
			sb.append(" and t.versionCode like ?");
			valuse.add("%"+target.getVersionCode()+"%");
		}
		if(StringUtils.isNotBlank(target.getCreateTimeSearch())){
			sb.append(" and to_char(t.createTime,'yyyy-MM-dd') = ?");
			valuse.add(target.getCreateTimeSearch());
		}
		OrderHelperUtils.getOrder(sb, target, "t.", "t.createTime desc");
		return appManageSqlDao.pageFind(sb.toString(), valuse.toArray(), target.getPager());
	}

	/**
	 * @param appManage
	 * @param loginUser
	 * @throws JsonException 
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws BizException 
	 * @throws IllegalStateException 
	 * @see com.youlb.biz.appManage.IAppManageBiz#saveOrUpdate(com.youlb.entity.appManage.AppManage, com.youlb.entity.privilege.Operator)
	 */
	@Override
	public void saveOrUpdate(AppManage appManage, Operator loginUser) throws ParseException, JsonException, IOException, BizException {
//		String sql="insert into t_domain_appmanage(fappmanageid,fdomainid) values (?,?)";
		 //add
		 if(StringUtils.isBlank(appManage.getId())){
			 appManage.setId(null);
			 //门口机app
//			 if(SysStatic.one.equals(appManage.getAppType())){
//				 //按区域升级	把按版本的设置null
//				 if(SysStatic.two.equals(appManage.getUpgradeType())){
//					 appManage.setTargetVersion(null);
//				 }
//			 }
			 String id = (String) appManageSqlDao.add(appManage);
			 //手机app做推送
			 if(SysStatic.two.equals(appManage.getAppType())){
				 //	门口机和管理机 需要操作中间表
//				 if(SysStatic.one.equals(appManage.getAppType())||SysStatic.three.equals(appManage.getAppType())){
//					 if(appManage.getTreecheckbox()!=null){
//						 for(String domainId:appManage.getTreecheckbox()){
//							 appManageSqlDao.executeSql(sql, new Object[]{id,domainId});
//						 }
//					 }
//				 }
//				 获取taglist
//				 List<String> tagList = getTagList(appManage, loginUser);
				 List<String> tagList = new ArrayList<String>();
				 tagList.add("4028816755c94fe50155c96c0ec70005");
//				 if(tagList==null){
//					 throw new BizException("未找到目标设备");
//				 }
//				 推送升级信息
				 VersionInfo info = new VersionInfo();
				 info.setTagList(tagList);//目标推送标签
				 info.setDesc(appManage.getVersionDes());//说明
				 info.setMd5_code(appManage.getMd5Value());//app的md5值
				 info.setSize(appManage.getAppSize());//app大小
				 info.setUrl(appManage.getServerAddr()+appManage.getRelativePath());//下载路径
				 info.setVersion_code(appManage.getVersionCode());//版本号
				 info.setVersion_name(appManage.getVersionName());//版本名称
				 info.setTargetDevive(appManage.getAppType());//app类型 1手机 2门口机
//				 是否强制升级
				 if("1".equals(appManage.getAutoInstal())){
					 info.setAuto_instal(false);
				 }else if("2".equals(appManage.getAutoInstal())){
					 info.setAuto_instal(true);
				 }
				 String infoJson = JsonUtils.toJson(info);
				 
				 CloseableHttpClient httpClient = HttpClients.createDefault();
				 HttpPost request = new HttpPost(SysStatic.HTTP+"/publish/pushAppVersionInfo.json");
				 List<BasicNameValuePair> formParams = new ArrayList<BasicNameValuePair>();
				 logger.info("版本信息："+infoJson);
				 formParams.add(new BasicNameValuePair("infoJson", infoJson));
				 UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formParams, "UTF-8");
				 request.setEntity(uefEntity);
				 CloseableHttpResponse response = httpClient.execute(request);
				 if(response.getStatusLine().getStatusCode()==200){
					 HttpEntity entity_rsp = response.getEntity();
					 ResultDTO resultDto = JsonUtils.fromJson(EntityUtils.toString(entity_rsp), ResultDTO.class);
					 if(resultDto!=null){
						 if(!"0".equals(resultDto.getCode())){
							 logger.error("信息推送出错！");
							 throw new BizException("信息推送出错");
						 }
					 }
				 } 
			 }
			 
	     //update		 
		 }else{
			 //门口机app
//			 if(SysStatic.one.equals(appManage.getAppType())){
//				 //删掉历史关联
//				 String delete = "delete from t_domain_appmanage t where t.fappmanageid=?";
//				 appManageSqlDao.executeSql(delete, new Object[]{appManage.getId()});
				 //按区域	 需要操作中间表
//				 if(SysStatic.one.equals(appManage.getUpgradeType())){
//					 appManage.setTargetVersion(null);//把目标版本id置空
//					 if(appManage.getTreecheckbox()!=null){
//						 for(String entityId:appManage.getTreecheckbox()){
//							 appManageSqlDao.executeSql(sql, new Object[]{appManage.getId(),entityId});
//						 }
//						 
//					 }
//				 }
//			 }
			  update(appManage);
		 }
		
		
	}

	/**获取需要升级的版本
	 * @return
	 * @throws BizException 
	 * @see com.youlb.biz.appManage.IAppManageBiz#getOldVersion()
	 */
	@Override
	public List<AppManage> getOldVersion() throws BizException {
		String hql = "from AppManage t where t.appType = ? order by t.createTime";
		return appManageSqlDao.find(hql, new Object[]{SysStatic.two});
	}

	/**
	 * @param appManage
	 * @return
	 * @throws BizException 
	 * @see com.youlb.biz.appManage.IAppManageBiz#checkVersion(com.youlb.entity.appManage.AppManage)
	 */
	@Override
	public boolean checkVersion(String md5Value) throws BizException {
		String hql="from AppManage a where a.md5Value=?";
		List<AppManage> list = appManageSqlDao.find(hql, new Object[]{md5Value});
		if(list!=null&&!list.isEmpty()){
			return true;
		}
		return false;
	}

	/**获取最新的app包
	 * @return
	 * @throws BizException 
	 * @see com.youlb.biz.appManage.IAppManageBiz#lastVersion()
	 */
	@Override
	public AppManage lastVersion(String type) throws BizException {
		String sql ="from AppManage t where t.appType= ? ORDER BY t.createTime DESC";
		List<AppManage> list = appManageSqlDao.find(sql, new Object[]{type});
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 获取推送标签集合
	 * @param appManage
	 * @param loginUser 
	 * @return
	 * @throws BizException 
	 */
	 
	private List<String> getTagList(AppManage appManage,Operator loginUser) throws BizException {
		 //手机端按社区id  tag推送   门口机和管理机的全部按社区id推送
		 if("2".equals(appManage.getAppType())||"5".equals(appManage.getAppType())||"1".equals(appManage.getUpgradeType())){
			 StringBuilder sb = new StringBuilder();
			 List<Object> values = new ArrayList<Object>();
//			 //特殊admin 全部的域
//			 if(SysStatic.SPECIALADMIN.equals(loginUser.getIsAdmin())){
//				 sb.append("select t.id from t_domain t where t.flayer='1'");
//			 }else{
				 List<String> domainIds = loginUser.getDomainIds();
				 sb.append("select t.id from t_domain t where t.id in(");
				 for(String domainId:domainIds){
					 sb.append("?,");
					 values.add(domainId);
				 }
				 sb.deleteCharAt(sb.length()-1);
				 sb.append(") and t.flayer='1'");
//			 }
			 List<String> tagList = appManageSqlDao.pageFindBySql(sb.toString(), values.toArray());
			 return tagList;
	     //选择一个域对象直接就是标签		 
		 }else if(("1".equals(appManage.getAppType())||"3".equals(appManage.getAppType()))&&"2".equals(appManage.getUpgradeType())){
			 List<String> treecheckbox = appManage.getTreecheckbox();
			 return treecheckbox;
		 }
		return null;
	}
	

}
