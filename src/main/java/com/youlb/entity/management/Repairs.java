package com.youlb.entity.management;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import com.youlb.entity.common.BaseModel;
import com.youlb.utils.helper.DateHelper;
/**
* @ClassName: CustomerService.java 
* @Description: 工单服务model 
* @author: Pengjy
* @date: 2016年6月13日
*
 */
@Entity
@Table(name="t_repairs")
public class Repairs extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4792136852831103605L;
	/**编号*/
	@Column(name="fordernum")
    private String orderNum;
	/**客户id*/
	@Column(name="fuserid")
    private Integer userId;
	/**房间地址*/
	@Column(name="froom_id")
    private String roomId;
	/**类型*/
	@Column(name="fordertype")
    private String orderType;
	/**来源*/
	@Column(name="fcomefrom")
    private String comeFrom;
	/**工单内容*/
	@Column(name="fservicecontent")
    private String serviceContent;
	/**处理状态*/
	@Column(name="fstatus")
    private String status;
	/**处理人*/
	@Column(name="fworkerid")
    private String workerId;
	/**处理时间*/
	@Column(name="fdealtime")
    private Date dealTime;
	/**处理结果*/
	@Column(name="fdealresult")
    private String dealResult;
	/**催单次数*/
	@Column(name="fremindercount")
    private Integer reminderCount;
	/**最好催单时间*/
	@Column(name="flastremindertime")
    private Date lastReminderTime;
	/**属性 1工单  2服务*/
	@Column(name="forder_nature")
    private Integer orderNature;
	/**联系人*/
	@Column(name="flinkman")
    private String linkman;
	/**联系方式*/
	@Column(name="fphone")
    private String phone;
	
	/**物业公司id*/
	@Column(name="fdepartment_id")
    private String departmentId;
	
	/**查询开始时间*/
	@Transient
    public String startTime;
	/**查询结束时间*/
	@Transient
    public String endTime;
	/**房间的域id*/
	@Transient
	private String domainId;
	/**房间地址*/
	@Transient
	private String address;
	/**处理人姓名*/
	@Transient
	private String workerName;
	
	public String getComeFromStr(){
		String comefromStr = "";
		if(StringUtils.isNotBlank(comeFrom)){
			if("1".equals(comeFrom)){
				comefromStr="电话";
			}else if("2".equals(comeFrom)){
				comefromStr="来访";
			}else if("3".equals(comeFrom)){
				comefromStr="终端";
			}
		}
		return comefromStr;
	}
	public String getStatusStr(){
		String comefromStr = "";
		if(StringUtils.isNotBlank(status)){
			if("1".equals(status)){
				comefromStr="未处理";
			}else if("2".equals(status)){
				comefromStr="待接单";
			}else if("3".equals(status)){
				comefromStr="正在处理";
			}else if("4".equals(status)){
				comefromStr="处理完成";
			}else if("5".equals(status)){
				comefromStr="结束";
			}else if("6".equals(status)){
				comefromStr="已评价";
			}
		}
		return comefromStr;
	}
	
	public String getCreateTimeStr(){
		 if(getCreateTime()!=null){
	            String createTimeStr = DateHelper.dateFormat(getCreateTime(), "yyyy-MM-dd HH:mm:ss");		
	            return createTimeStr;
	    	}
	    	 return "";
	}
	public String getDealTimeStr(){
		 if(dealTime!=null){
	            String dealTimeStr = DateHelper.dateFormat(dealTime, "yyyy-MM-dd HH:mm:ss");		
	            return dealTimeStr;
	    	}
	    	 return "";
	}
	
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getWorkerName() {
		return workerName;
	}
	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDomainId() {
		return domainId;
	}
	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}
	public Integer getOrderNature() {
		return orderNature;
	}
	public void setOrderNature(Integer orderNature) {
		this.orderNature = orderNature;
	}
	public Integer getReminderCount() {
		return reminderCount;
	}
	public void setReminderCount(Integer reminderCount) {
		this.reminderCount = reminderCount;
	}
	public Date getLastReminderTime() {
		return lastReminderTime;
	}
	public void setLastReminderTime(Date lastReminderTime) {
		this.lastReminderTime = lastReminderTime;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getServiceContent() {
		return serviceContent;
	}
	public void setServiceContent(String serviceContent) {
		this.serviceContent = serviceContent;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getComeFrom() {
		return comeFrom;
	}
	public void setComeFrom(String comeFrom) {
		this.comeFrom = comeFrom;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getWorkerId() {
		return workerId;
	}
	public void setWorkerId(String workerId) {
		this.workerId = workerId;
	}
	public Date getDealTime() {
		return dealTime;
	}
	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}
	public String getDealResult() {
		return dealResult;
	}
	public void setDealResult(String dealResult) {
		this.dealResult = dealResult;
	}
	
	
}
