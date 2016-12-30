package com.youlb.entity.houseInfo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.youlb.entity.common.BaseModel;
import com.youlb.utils.helper.DateHelper;

/** 
 * @ClassName: Neighborhoods.java 
 * @Description: 社区信息entity
 * @author: Pengjy
 * @date: 2015年6月25日
 * 
 */
@Entity
@Table(name="t_neighborhoods")
public class Neighborhoods extends BaseModel {
	/**社区名称*/
	@Column(name="FNEIBNAME")
	private String neibName;
	/**社区编号 5位*/
	@Column(name="FNEIBNUM")
	private String neibNum;
	/**承建商*/
	@Column(name="FCONTRACTOR")
	private String contractor;
	/**详细地址*/
	@Column(name="FADDRESS")
	private String address;
	/**开工日期*/
	@Column(name="FSTARTBUILDDATE")
	private Date startBuildDate;
	/**竣工日期*/
	@Column(name="FENDBUILDDATE")
	private Date endBuildDate;
	/**小区使用日期*/
	@Column(name="FUSEDATE")
	private Date useDate;
	/**总占地面积*/
	@Column(name="FTOTALAREA")
	private String totalArea;
	/**总建筑面积*/
	@Column(name="FTOTALBUILDAREA")
	private String totalBuildArea;
	/**总商业面积*/
	@Column(name="FTOTALBUSSNISAREA")
	private String totalBussnisArea;
	/**绿化率（%）*/
	@Column(name="FGREENINGRATE")
	private String greeningRate;
	/**容积率(%)*/
	@Column(name="FPLOTRATIO")
	private String plotRatio;
	/**备注*/
	@Column(name="FREMARK")
	private String remark;
	/**是否创建sip账号1否   2是*/
	@Column(name="fcreate_sip_num")
	private String createSipNum;
	/**是否使用秘钥 1否   2是*/
	@Column(name="fuse_key")
	private String useKey;
	/**16位随机数加密结果*/
	@Column(name="fencode_key",updatable=false)
	private String encodeKey;
	
	/**时间字符串（前台传入）*/
	@Transient
	private String startBuildDateStr;
	@Transient
	private String endBuildDateStr;
	@Transient
	private String useDateStr;
	@Transient
	private String parentId;
	@Transient
	private String sipNum;
	@Transient
	private String sipNumPsw;
	
	
	
	public String getSipNumPsw() {
		StringBuilder sb = new StringBuilder();
		sb.append("<a href =\"javascript:alert('");
		sb.append(sipNumPsw);
		sb.append("')\">查看</a>");
		return sb.toString();
	}
	public void setSipNumPsw(String sipNumPsw) {
		this.sipNumPsw = sipNumPsw;
	}
	public String getEncodeKey() {
		return encodeKey;
	}
	public void setEncodeKey(String encodeKey) {
		this.encodeKey = encodeKey;
	}
	public String getUseKey() {
		return useKey;
	}
	public void setUseKey(String useKey) {
		this.useKey = useKey;
	}
	public String getSipNum() {
		return sipNum;
	}
	public void setSipNum(String sipNum) {
		this.sipNum = sipNum;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getStartBuildDateStr() {
		if(this.startBuildDate!=null){
			startBuildDateStr = DateHelper.dateFormat(this.startBuildDate, "yyyy-MM-dd");
		}
		return startBuildDateStr;
	}
	public void setStartBuildDateStr(String startBuildDateStr) {
		this.startBuildDateStr = startBuildDateStr;
	}
	public String getEndBuildDateStr() {
		if(this.endBuildDate!=null){
			endBuildDateStr = DateHelper.dateFormat(this.endBuildDate, "yyyy-MM-dd");
		}
		return endBuildDateStr;
	}
	public void setEndBuildDateStr(String endBuildDateStr) {
		this.endBuildDateStr = endBuildDateStr;
	}
	public String getUseDateStr() {
		if(this.useDate!=null){
			useDateStr = DateHelper.dateFormat(this.useDate, "yyyy-MM-dd");
		}
		return useDateStr;
	}
	
	public String getCreateSipNum() {
		return createSipNum;
	}
	public void setCreateSipNum(String createSipNum) {
		this.createSipNum = createSipNum;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getNeibName() {
		return neibName;
	}
	public void setNeibName(String neibName) {
		this.neibName = neibName;
	}
	public void setUseDateStr(String useDateStr) {
		this.useDateStr = useDateStr;
	}
	public String getNeibNum() {
		return neibNum;
	}
	public void setNeibNum(String neibNum) {
		this.neibNum = neibNum;
	}
	public String getContractor() {
		return contractor;
	}
	public void setContractor(String contractor) {
		this.contractor = contractor;
	}
	public Date getStartBuildDate() {
		return startBuildDate;
	}
	public void setStartBuildDate(Date startBuildDate) {
		this.startBuildDate = startBuildDate;
	}
	public Date getEndBuildDate() {
		return endBuildDate;
	}
	public void setEndBuildDate(Date endBuildDate) {
		this.endBuildDate = endBuildDate;
	}
	public Date getUseDate() {
		return useDate;
	}
	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}
	public String getTotalArea() {
		return totalArea;
	}
	public void setTotalArea(String totalArea) {
		this.totalArea = totalArea;
	}
	public String getTotalBuildArea() {
		return totalBuildArea;
	}
	public void setTotalBuildArea(String totalBuildArea) {
		this.totalBuildArea = totalBuildArea;
	}
	public String getTotalBussnisArea() {
		return totalBussnisArea;
	}
	public void setTotalBussnisArea(String totalBussnisArea) {
		this.totalBussnisArea = totalBussnisArea;
	}
	public String getGreeningRate() {
		return greeningRate;
	}
	public void setGreeningRate(String greeningRate) {
		this.greeningRate = greeningRate;
	}
	public String getPlotRatio() {
		return plotRatio;
	}
	public void setPlotRatio(String plotRatio) {
		this.plotRatio = plotRatio;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}
