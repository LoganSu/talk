package com.youlb.entity.appManage;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.youlb.entity.common.BaseModel;
import com.youlb.utils.common.SysStatic;
import com.youlb.utils.helper.DateHelper;

/** 
 * @ClassName: AppManage.java 
 * @Description: app管理model 
 * @author: Pengjy
 * @date: 2015-11-26
 * 
 */
@Entity
@Table(name="t_appmanage")
public class AppManage extends BaseModel {
	/**版本名称*/
	@Column(name="fversionname")
    private String versionName;
	/**软件号*/
	@Column(name="fversioncode")
    private String versionCode;
	/**包名*/
	@Column(name="fpackagename")
    private String packageName;
	/**app大小*/
	@Column(name="fappsize")
    private Long appSize;
	/**版本说明*/
	@Column(name="fversiondes")
    private String versionDes;
	/**app类型  手机 门口机*/
	@Column(name="fapptype")
    private String appType;
	/**门口机升级类型  按版本升级  按区域升级  手机端=null*/
	@Column(name="fupgradetype")
    private String upgradeType;
	/**升级的目标版本id 指定版本升级 */
	@Column(name="ftargetversion")
    private String targetVersion;
	/**文件MD5校验值 */
	@Column(name="fmd5value")
    private String md5Value;
	/**app文件存储的名称 */
	@Column(name="fappsavename")
    private String appSaveName;
	/**文件保存服务器地址*/
	@Column(name="fserveraddr")
    private String serverAddr;
	/**app文件存储的相对路径*/
	@Column(name="frelativepath")
    private String relativePath;
	/**强制升级否  1：强制升级，2：不强制升级*/
	@Column(name="fautoinstal")
    private String autoInstal;
	/**第三方app图标*/
	@Column(name="ficon_url")
    private String iconUrl;
	/**日期搜索条件*/
	@Transient
	private String createTimeSearch;
	@Transient
	private List<String> treecheckbox;
	/**操作类型 */
	@Transient
	private String opraterType;
	
	public double getAppSizeM() {
		if(appSize!=null){
			BigDecimal b = new BigDecimal((double)appSize/1024);  
    		double f = b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();  
    		return f;
		}
		return 0;
	}
	 
	public String getPublishTime() {
		String publishTime = "";
		if(getCreateTime()!=null){
			publishTime = DateHelper.dateFormat(getCreateTime(), "yyyy-MM-dd");
		}
		return publishTime;
	}
	
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public String getAutoInstal() {
		return autoInstal;
	}
	public void setAutoInstal(String autoInstal) {
		this.autoInstal = autoInstal;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getServerAddr() {
		return serverAddr;
	}
	public void setServerAddr(String serverAddr) {
		this.serverAddr = serverAddr;
	}
	public String getRelativePath() {
		return relativePath;
	}
	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}
	public String getAppSaveName() {
		return appSaveName;
	}
	public void setAppSaveName(String appSaveName) {
		this.appSaveName = appSaveName;
	}
	public String getMd5Value() {
		return md5Value;
	}
	public void setMd5Value(String md5Value) {
		this.md5Value = md5Value;
	}
	public String getOpraterType() {
		return opraterType;
	}
	public void setOpraterType(String opraterType) {
		this.opraterType = opraterType;
	}
	public List<String> getTreecheckbox() {
		return treecheckbox;
	}
	public void setTreecheckbox(List<String> treecheckbox) {
		this.treecheckbox = treecheckbox;
	}
	public String getTargetVersion() {
		return targetVersion;
	}
	public void setTargetVersion(String targetVersion) {
		this.targetVersion = targetVersion;
	}
	public String getCreateTimeSearch() {
		return createTimeSearch;
	}
	public void setCreateTimeSearch(String createTimeSearch) {
		this.createTimeSearch = createTimeSearch;
	}
	public String getUpgradeType() {
		return upgradeType;
	}
	public void setUpgradeType(String upgradeType) {
		this.upgradeType = upgradeType;
	}
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	 
	public String getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}
	public Long getAppSize() {
		return appSize;
	}
	public void setAppSize(Long appSize) {
		this.appSize = appSize;
	}
	public String getVersionDes() {
		return versionDes;
	}
	public void setVersionDes(String versionDes) {
		this.versionDes = versionDes;
	}
	
	 public String getCreateTimeStr() {
		 if(getCreateTime()!=null){
	    		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	            String createTimeStr = sd.format(getCreateTime());  		
	            return createTimeStr;
	    	}
	    	 return "";
	 }
	 
	 public String getOperate() {
	   	StringBuilder sb = new StringBuilder();
	   	sb.append("<a class='appmanageDetail' rel='"+getId()+"' href='javascript:void(0)'>详细</a>&nbsp;")
	   	.append("<a class='appmanageSingleDown' rel='"+getId()+"' href='"+SysStatic.PATH+"/mc/appManage/singleDown.do?id="+getId()+"'>APP下载</a>&nbsp;");
//	   	.append("<a class='appmanageUpdate' rel='"+getId()+"' cardStatus='1' href='javascript:void(0)'>修改</a>&nbsp;")
//	   	.append("<a class='appmanageDelete' rel='"+getId()+"' cardStatus='3' href='javascript:void(0)'>删除</a>&nbsp;");
	   
			return sb.toString();
		}
			
}
