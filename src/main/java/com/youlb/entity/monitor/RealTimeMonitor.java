package com.youlb.entity.monitor;

import java.text.SimpleDateFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import com.youlb.entity.common.BaseModel;
@Entity
@Table(name="t_real_time_monitor")
public class RealTimeMonitor extends BaseModel {
	private static final long serialVersionUID = -954868684797618115L;
	/**门口机*账号*/
	@Column(name="fdevicecount")
    private String deviceCount;
    /**警告类型*/
	@Column(name="fwarn_type")
    private String warnType;
    /**状态*/
	@Column(name="fstatus")
    private String status;
    /**处理备注*/
	@Column(name="fremark")
    private String remark;
    
    
    /**地址*/
    @Transient
    private String address;
    /**经度*/
    @Transient
	private Double longitude;
	/**纬度*/
    @Transient
	private Double latitude;
    /**告警电话*/
    @Transient
	private String warnPhone;
    
	public String getWarnPhone() {
		return warnPhone;
	}
	public void setWarnPhone(String warnPhone) {
		this.warnPhone = warnPhone;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public String getDeviceCount() {
		return deviceCount;
	}
	public void setDeviceCount(String deviceCount) {
		this.deviceCount = deviceCount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getWarnType() {
		return warnType;
	}
	public void setWarnType(String warnType) {
		this.warnType = warnType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	   	sb.append("<a class='disposeEvent' rel='"+getId()+"' href='javascript:void(0)'>处理</a>&nbsp;");
	   
			return sb.toString();
	}
	 
	 public String getStatusStr() {
		 String statusStr = "";
		 if(StringUtils.isNotBlank(status)){
			if("0".equals(status)){
				statusStr="未处理";
			 }else if("1".equals(status)){
				 statusStr="已处理";
			 }
	     }
			return statusStr;
		}
}
