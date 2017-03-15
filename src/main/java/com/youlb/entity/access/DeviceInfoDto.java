package com.youlb.entity.access;


/** 
 * @ClassName: DeviceInfoDto.java 
 * @Description: 设备信息生成excel 
 * @author: Pengjy
 * @date: 2016-9-19
 * 
 */
public class DeviceInfoDto{
	/**设备sn*/
   private String id;
   /**设备状态*/
   private String deviceStatus;
   /**激活时间*/
   private String liveTime;
   /**登记时间*/
   private String createTime;
   /**设备编码*/
   private String deviceNum;
   /**设备型号*/
   private String deviceModel;
   /**应用版本*/
   private String app_version;
   /**内存大小*/
   private String memory_size;
   /**存储容量*/
   private String storage_capacity;
   /**系统版本*/
   private String system_version;
   /**版本号*/
   private String versionNum;
   /**软件型号*/
   private String softwareType;
   /**备注*/
   private String remark;
   /**处理器*/
   private String processor_type;
   /**固件版本*/
   private String firmware_version;
   /**内核版本*/
   private String kernal_version;
   /**设备厂家*/
   private String deviceFactory;
   /**出厂日期*/
   private String deviceBorn;
   

 
public String getLiveTime() {
	return liveTime;
}
public void setLiveTime(String liveTime) {
	this.liveTime = liveTime;
}
public String getCreateTime() {
	return createTime;
}
public void setCreateTime(String createTime) {
	this.createTime = createTime;
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
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getDeviceNum() {
	return deviceNum;
}
public void setDeviceNum(String deviceNum) {
	this.deviceNum = deviceNum;
}
public String getDeviceModel() {
	return deviceModel;
}
public void setDeviceModel(String deviceModel) {
	this.deviceModel = deviceModel;
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
public String getRemark() {
	return remark;
}
public void setRemark(String remark) {
	this.remark = remark;
}
	
   
	
	 
	   
   
}
