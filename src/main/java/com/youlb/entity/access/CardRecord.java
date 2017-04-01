/**
 * Copyright 2015 Unionbon' Studio
 * All right Reserved.
 * Created on 2015年9月19日.
 * Created by garyxin.
 */

package com.youlb.entity.access;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.youlb.entity.common.Model;


 
/**
 * 门禁数据表
 * @author garyxin on 2015年9月19日.
 *
 */
@SuppressWarnings("serial")
@Table(name="t_cardrecord")
public class CardRecord extends Model implements Serializable {
	@Id
    private Long id;
    /** 卡片序列号 */
    @Column(name="fcardsn")
    private String cardsn;
    /** 卡片类型 */
    @Column(name="fcardtype")
    private String cardtype;
    /** 房屋ID */
    @Column(name="fhouseid")
    private String houseid;
    /** 设备sn */
    @Column(name="fdevicesn")
    private String devicesn;
    /** 开锁类型 */
    @Column(name="fmode")
    private String mode;
    /** 抓拍文件名 */
    @Column(name="fpath")
    private String imgpath;
    /** 刷卡时间 */
    @Column(name="ftime")
    private Date cardtime;
    /** 登录账号 */
    @Column(name="fcardsn")
    private String username;
    /** 门禁地址 */
    @Transient
    private String address;
    /** 开始时间 */
    @Transient
    private String startTime;
    /** 结束时间 */
    @Transient
    private String endTime;
    /** 日期 */
    @Transient
    private String fdate;
    /** 时间 */
    @Transient
	private String ftime;
    /** 姓名 */
    @Transient
	private String fname;
    /** 工号*/
    @Transient
	private String workerNum;
    
    
    
	public String getFdate() {
		return fdate;
	}
	public void setFdate(String fdate) {
		this.fdate = fdate;
	}
	public String getFtime() {
		return ftime;
	}
	public void setFtime(String ftime) {
		this.ftime = ftime;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getWorkerNum() {
		return workerNum;
	}
	public void setWorkerNum(String workerNum) {
		this.workerNum = workerNum;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCardsn() {
		return cardsn;
	}
	public void setCardsn(String cardsn) {
		this.cardsn = cardsn;
	}
	public String getCardtype() {
		return cardtype;
	}
	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}
	 
	public String getHouseid() {
		return houseid;
	}
	public void setHouseid(String houseid) {
		this.houseid = houseid;
	}
	public String getDevicesn() {
		return devicesn;
	}
	public void setDevicesn(String devicesn) {
		this.devicesn = devicesn;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getImgpath() {
		return imgpath;
	}
	public void setImgpath(String imgpath) {
		this.imgpath = imgpath;
	}
	public Date getCardtime() {
		return cardtime;
	}
	public void setCardtime(Date cardtime) {
		this.cardtime = cardtime;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getCardtimeStr() {
		String timeStr="";
		if(cardtime!=null){
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			timeStr = sd.format(cardtime);
		}
		return timeStr;
	}

}
