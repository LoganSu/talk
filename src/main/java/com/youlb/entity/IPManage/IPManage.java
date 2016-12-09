package com.youlb.entity.IPManage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

import com.youlb.entity.common.BaseModel;
/**
* @ClassName: IPManage.java 
* @Description: 运营商ip管理实体类 
* @author: Pengjy
* @date: 2016年9月1日
*
 */
@Entity
@Table(name="t_ip_manage")
public class IPManage extends BaseModel {
	private static final long serialVersionUID = 8185483391379268906L;
	/**ip地址*/
   @Column(name="fip")
   private String ip;
   /**端口*/
   @Column(name="fport")
   private Integer port;
   /**平台名称*/
   @Column(name="fplatform_name")
   private String platformName;
   /**备注*/
   @Column(name="fremark")
   private String remark;
   /**平台类型*/
   @Column(name="fplatform_type")
   private String platformType;
   /**社区名称*/
   @Column(name="fneib_name")
   private String neibName;
   /**是否开通物业功能*/
   @Column(name="fmanagement")
   private Boolean management;
   /**fs ip*/
   @Column(name="ffs_ip")
   private String fsIp;
   /**fs端口*/
   @Column(name="ffs_port")
   private Integer fsPort;
   /**社区唯一编号*/
   @Column(name="fneibor_flag")
   private Integer neiborFlag;
   
   
	 
	public Integer getNeiborFlag() {
		return neiborFlag;
	}
	public void setNeiborFlag(Integer neiborFlag) {
		this.neiborFlag = neiborFlag;
	}
	public String getFsIp() {
		return fsIp;
	}
	public void setFsIp(String fsIp) {
		this.fsIp = fsIp;
	}
	public Integer getFsPort() {
		return fsPort;
	}
	public void setFsPort(Integer fsPort) {
		this.fsPort = fsPort;
	}
	public Boolean getManagement() {
		return management;
	}
	public void setManagement(Boolean management) {
		this.management = management;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public String getPlatformName() {
		return platformName;
	}
	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPlatformType() {
		return platformType;
	}
	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}
	public String getNeibName() {
		return neibName;
	}
	public void setNeibName(String neibName) {
		this.neibName = neibName;
	}
	
	public String getPlatformTypeStr() {
		 String platformTypeStr="";
		 if(StringUtils.isNotBlank(platformType)){
			 if("1".equals(platformType)){
				 platformTypeStr="二级平台";
			 }else if("2".equals(platformType)){
				 platformTypeStr="云平台";
			 }
		 }
		 return platformTypeStr;
	}
	 public String getOperate() {
		   	StringBuilder sb = new StringBuilder();
		   	sb.append("<a class='IPManageDetail' rel='"+getId()+"' href='javascript:void(0)'>详情</a>&nbsp;");
				return sb.toString();
		}   
	 public String getManagementStr() {
		 String managementStr = "";
		 if(management!=null){
			 if(management){
				 managementStr="是";
			 }else{
				 managementStr="否";
			 }
		 }
			return managementStr;
		}
}
