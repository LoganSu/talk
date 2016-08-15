package com.youlb.entity.houseInfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.youlb.entity.common.BaseModel;

/** 
 * @ClassName: Building.java 
 * @Description: 楼栋信息model类 
 * @author: Pengjy
 * @date: 2015年6月29日
 * 
 */
@Entity
@Table(name="t_building")
public class Building extends BaseModel {
	/**楼栋编号 3位*/
	@Column(name="FBUILDINGNUM")
	private String buildingNum;
	/**楼栋名称*/
	@Column(name="FBUILDINGNAME")
	private String buildingName;
	/**一共几层*/
	@Column(name="FTOTALFLOOR")
	private String totalFloor;
	 /**楼高*/
	@Column(name="FBUILDHEIGHT")
	private String  buildHeight;
	/**楼栋类型*/
	@Column(name="FBUILDTYPE")
	private String buildType;
	/** 备注*/
	@Column(name="FREMARK")
	private String remark;
	
	@Transient
	private String parentId;
	
	
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getBuildingNum() {
		return buildingNum;
	}
	public void setBuildingNum(String buildingNum) {
		this.buildingNum = buildingNum;
	}
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	 
	public String getTotalFloor() {
		return totalFloor;
	}
	public void setTotalFloor(String totalFloor) {
		this.totalFloor = totalFloor;
	}
	public String getBuildHeight() {
		return buildHeight;
	}
	public void setBuildHeight(String buildHeight) {
		this.buildHeight = buildHeight;
	}
	public String getBuildType() {
		return buildType;
	}
	public void setBuildType(String buildType) {
		this.buildType = buildType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}
