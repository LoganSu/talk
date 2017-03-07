package com.youlb.entity.access;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

import com.sun.org.apache.xml.internal.serialize.BaseMarkupSerializer;
import com.youlb.entity.common.BaseModel;
import com.youlb.entity.common.Model;
import com.youlb.utils.common.SysStatic;

/** 
 * @ClassName: DeviceInfo.java 
 * @Description: 设备信息 
 * @author: Pengjy
 * @date: 2015-11-23
 * 
 */
@Entity
@Table(name="t_deviceinfo")
public class DeviceInfo extends BaseModel{
   /**设备编码*/
   @Column(name="fdevicenum")
   private String deviceNum;
   /**设备名称*/
   @Column(name="fdevicename")
   private String deviceName;
   /**设备型号*/
   @Column(name="fdevicemodel")
   private String deviceModel;
   /**设备厂家*/
   @Column(name="fdevicefactory")
   private String deviceFactory;
   /**设备状态*/
   @Column(name="fdevicestatus")
   private String deviceStatus;
   /**设备出厂日期*/
   @Column(name="fdeviceborn")
   private String deviceBorn;
   /**处理器*/
   @Column(name="fprocessortype")
   private String processor_type;
   /**内存大小*/
   @Column(name="fmemorysize")
   private String memory_size;
   /**存储容量*/
   @Column(name="fstoragecapacity")
   private String storage_capacity;
   /**系统版本*/
   @Column(name="fsystemversion")
   private String system_version;
   /**固件版本*/
   @Column(name="ffirmwareversion")
   private String firmware_version;
   /**内核版本*/
   @Column(name="fkernalversion")
   private String kernal_version;
   /**应用版本*/
   @Column(name="fappversion")
   private String app_version;
   /**设备密码*/
   @Column(name="fdevicepws")
   private String devicePws;
   /**备注*/
   @Column(name="fremark")
   private String remark;
   /**版本号*/
   @Column(name="fversion_num")
   private String versionNum;
   /**软件型号*/
   @Column(name="fsoftware_type")
   private String softwareType;
   /**设备激活时间*/
   @Column(name="flive_time")
   private Date liveTime;
   
   
	public String getOprator() {
		return "<a class='deviceInfoSetPws' rel='"+getId()+"' href='javascript:void(0)'>设置密码</a>";
	}
	
	public String getCreateTimeStr() {
		String timeStr="";
		if(getCreateTime()!=null){
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			timeStr = sd.format(getCreateTime());
		}
		return timeStr;
	}
	public String getLiveTimeStr() {
		String timeStr="";
		if(liveTime!=null){
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			timeStr = sd.format(liveTime);
		}
		return timeStr;
	}
	public Date getLiveTime() {
		return liveTime;
	}

	public void setLiveTime(Date liveTime) {
		this.liveTime = liveTime;
	}
	public String getDeviceNameStr() {
		String deviceNameStr = "";
		if(deviceName!=null){
			if(SysStatic.one.equals(deviceName)){
				deviceNameStr="门口机";
			}else if(SysStatic.two.equals(deviceName)){
				deviceNameStr="自助终端";
			}
		}
		return deviceNameStr;
	}
	
	public String getDeviceFactoryStr() {
		String deviceFactoryStr = "";
		if(deviceFactory!=null){
			if(SysStatic.one.equals(deviceFactory)){
				deviceFactoryStr="中卡";
			}else if(SysStatic.two.equals(deviceFactory)){
				deviceFactoryStr="友邻邦";
			}
		}
		return deviceFactoryStr;
	}
	
	public String getDeviceStatusStr() {
		String deviceStatusStr = "";
		if(StringUtils.isNotBlank(deviceStatus)){
			if(SysStatic.one.equals(deviceStatus)){
				deviceStatusStr="激活";
			}
		}else{
			deviceStatusStr="未激活";
		}
		return deviceStatusStr;
	}
	
	public String getVersionNum() {
		return versionNum;
	}
	public void setVersionNum(String versionNum) {
		this.versionNum = versionNum;
	}
	public String getSoftwareType() {
		return softwareType;
	}
	public void setSoftwareType(String softwareType) {
		this.softwareType = softwareType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDeviceNum() {
		return deviceNum;
	}
	public void setDeviceNum(String deviceNum) {
		this.deviceNum = deviceNum;
	}
	public String getDeviceName() {
		return deviceName;
	}
	
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getDeviceModel() {
		return deviceModel;
	}
	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}
	public String getProcessor_type() {
		return processor_type;
	}
	public void setProcessor_type(String processor_type) {
		this.processor_type = processor_type;
	}
	public String getMemory_size() {
		return memory_size;
	}
	public void setMemory_size(String memory_size) {
		this.memory_size = memory_size;
	}
	public String getStorage_capacity() {
		return storage_capacity;
	}
	public void setStorage_capacity(String storage_capacity) {
		this.storage_capacity = storage_capacity;
	}
	public String getSystem_version() {
		return system_version;
	}
	public void setSystem_version(String system_version) {
		this.system_version = system_version;
	}
	public String getFirmware_version() {
		return firmware_version;
	}
	public void setFirmware_version(String firmware_version) {
		this.firmware_version = firmware_version;
	}
	public String getKernal_version() {
		return kernal_version;
	}
	public void setKernal_version(String kernal_version) {
		this.kernal_version = kernal_version;
	}
	public String getApp_version() {
		return app_version;
	}
	public void setApp_version(String app_version) {
		this.app_version = app_version;
	}
	public String getDeviceFactory() {
		return deviceFactory;
	}
	
	public void setDeviceFactory(String deviceFactory) {
		this.deviceFactory = deviceFactory;
	}
	public String getDeviceStatus() {
		return deviceStatus;
	}
	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}
	public String getDeviceBorn() {
		return deviceBorn;
	}
	public void setDeviceBorn(String deviceBorn) {
		this.deviceBorn = deviceBorn;
	}
	public String getDevicePws() {
		return devicePws;
	}
	public void setDevicePws(String devicePws) {
		this.devicePws = devicePws;
	}
	   
   
}
