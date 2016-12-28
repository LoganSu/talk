package com.youlb.entity.countManage;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import com.youlb.entity.common.BaseModel;
import com.youlb.utils.common.SysStatic;

/** 
 * @ClassName: DeviceCount.java 
 * @Description: 设备账号 
 * @author: Pengjy
 * @date: 2015-11-24
 * 
 */
@Entity
@Table(name="t_devicecount")
public class DeviceCount extends BaseModel {
	/**账号*/
	@Column(name="fdevicecount")
    private String deviceCount;
	/**帐号类型 1门口机 2自助终端 3管理机*/
	@Column(name="fcounttype")
    private String countType;
	/**帐号状态*/
	@Column(name="fcountstatus")
    private String countStatus;
	/**帐号密码*/
	@Column(name="fcountpsw")
    private String countPsw;
	/**域id*/
	@Column(name="fdomainid")
    private String domainId;
	/**sip账号*/
	@Column(name="fsipnum")
    private String sipNum;
	/**截止日期*/
	@Column(name="fendtime")
    private String endTime;
	/**派单随机数*/
	@Column(name="framdon_code")
    private String ramdonCode;
	/**账号描述*/
	@Column(name="fdevice_count_desc")
    private String deviceCountDesc;
	/**派单编号*/
	@Column(name="forder_num")
	private String orderNum;
	/**二维码路径*/
	@Column(name="fqr_path")
	private String qrPath;
	/**经度*/
	@Column(name="flongitude")
	private String longitude;
	/**纬度*/
	@Column(name="flatitude")
	private String latitude;
	
	/**告警电话*/
	@Column(name="fwarn_phone")
	private String warnPhone;
	/**告警邮箱*/
	@Column(name="fwarn_email")
	private String warnEmail;
	
	
	@Transient
	private String address;
	@Transient
	private List<String> treecheckbox;
	/**打印人*/
	@Transient
	private String printPerson;
	/**打印日期*/
	@Transient
	private String printTime;
	
	public String getOprator() {
		return "<a class='deviceCountSetPsw' rel='"+getId()+"' href='javascript:void(0)'>设置密码</a>&nbsp;" +
				"<a class='deviceCountUpdate' rel='"+getId()+"' href='javascript:void(0)'>修改</a>";
	}
	
	public String getCountTypeStr() {
		String countTypeStr="";
		if(countType!=null){
			if(SysStatic.one.equals(countType)){
				countTypeStr="门口机";
			}else if(SysStatic.two.equals(countType)){
				countTypeStr="自助终端";
			}else if(SysStatic.three.equals(countType)){
				countTypeStr="管理机";
			}
		}
		return countTypeStr;
	}
	
	public String getCountStatusStr() {
		String countStatusStr="";
		if(countStatus!=null){
			if(SysStatic.one.equals(countStatus)){
				countStatusStr="激活";
			}else if(SysStatic.two.equals(countStatus)){
				countStatusStr="未激活";
			}
		}
		return countStatusStr;
	}
	
	public String getWarnPhone() {
		return warnPhone;
	}

	public void setWarnPhone(String warnPhone) {
		this.warnPhone = warnPhone;
	}

	public String getWarnEmail() {
		return warnEmail;
	}

	public void setWarnEmail(String warnEmail) {
		this.warnEmail = warnEmail;
	}

	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getQrPath() {
		return qrPath;
	}
	public void setQrPath(String qrPath) {
		this.qrPath = qrPath;
	}
	public String getPrintTime() {
		return printTime;
	}
	public void setPrintTime(String printTime) {
		this.printTime = printTime;
	}
	public String getPrintPerson() {
		return printPerson;
	}
	public void setPrintPerson(String printPerson) {
		this.printPerson = printPerson;
	}
	 
	public String getOrderNum() {
		return orderNum;
	}
	public String getOrderNumStr() {
		if(StringUtils.isNotBlank(orderNum)){
			return "<a class='deviceCountPrintById' rel='"+getId()+"' href='javascript:void(0)'>"+orderNum+"</a>";
		}else{
			return "";
		}
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getRamdonCode() {
		return ramdonCode;
	}

	public void setRamdonCode(String ramdonCode) {
		this.ramdonCode = ramdonCode;
	}

	public String getDeviceCountDesc() {
		return deviceCountDesc;
	}

	public void setDeviceCountDesc(String deviceCountDesc) {
		this.deviceCountDesc = deviceCountDesc;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getSipNum() {
		return sipNum;
	}
	public void setSipNum(String sipNum) {
		this.sipNum = sipNum;
	}
	public String getDomainId() {
		return domainId;
	}
	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}
	public List<String> getTreecheckbox() {
		return treecheckbox;
	}
	public void setTreecheckbox(List<String> treecheckbox) {
		this.treecheckbox = treecheckbox;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDeviceCount() {
		return deviceCount;
	}

	public void setDeviceCount(String deviceCount) {
		this.deviceCount = deviceCount;
	}

	public String getCountType() {
		return countType;
	}

	public void setCountType(String countType) {
		this.countType = countType;
	}

	public String getCountStatus() {
		return countStatus;
	}

	public void setCountStatus(String countStatus) {
		this.countStatus = countStatus;
	}

	public String getCountPsw() {
		return countPsw;
	}

	public void setCountPsw(String countPsw) {
		this.countPsw = countPsw;
	}
	
	
	
}
