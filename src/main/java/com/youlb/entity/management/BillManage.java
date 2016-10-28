package com.youlb.entity.management;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.youlb.entity.common.BaseModel;
@Entity
@Table(name="t_billmanage")
public class BillManage extends BaseModel {

	private static final long serialVersionUID = 8457588968433666796L;
	/**账单号*/
	@Column(name="fbill_num")
	private String billNum;
	/**姓名*/
	@Column(name="fname")
	private String fname;
	/**域id*/
	@Column(name="fdomain_id")
	private String domainId;
	/**联系电话*/
	@Column(name="fphone")
	private String phone;
	/**类型 1 管理费 2水费 3电费 4燃气费 5其他*/
	@Column(name="ftype")
	private Integer ftype;
	/**金额*/
	@Column(name="fmoney")
	private Double money;
	/**计费开始时间*/
	@Column(name="fstart_time")
	private Date startTime;
	/**计费结束时间*/
	@Column(name="fend_time")
	private Date endTime;
	/**状态*/
	@Column(name="fstatus")
	private Integer status;
	/**缴费平台 1 微信  2 支付宝*/
	@Column(name="fpay_platform")
	private Integer payPlatform ;
	/**缴费时间*/
	@Column(name="fpay_time")
	private Date payTime;
	/**说明*/
	@Column(name="fdesc")
	private String desc;
	/**地址*/
	@Transient
	private String address;
	/**缴费开始时间字符串*/
	@Transient
	private String startTimeStr;
	/**缴费结束时间字符串*/
	@Transient
	private String endTimeStr;
	/**缴费时间字符串*/
	@Transient
	private String payTimeStr;
	
	
	public String getPayTimeStr() {
		return payTimeStr;
	}
	public void setPayTimeStr(String payTimeStr) {
		this.payTimeStr = payTimeStr;
	}
	public String getStartTimeStr() {
		return startTimeStr;
	}
	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}
	public String getEndTimeStr() {
		return endTimeStr;
	}
	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBillNum() {
		return billNum;
	}
	public void setBillNum(String billNum) {
		this.billNum = billNum;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getDomainId() {
		return domainId;
	}
	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public Integer getFtype() {
		return ftype;
	}
	public void setFtype(Integer ftype) {
		this.ftype = ftype;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getPayPlatform() {
		return payPlatform;
	}
	public void setPayPlatform(Integer payPlatform) {
		this.payPlatform = payPlatform;
	}
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
    
	
	public Integer getFtypeStr() {
		return ftype;
	}
}
