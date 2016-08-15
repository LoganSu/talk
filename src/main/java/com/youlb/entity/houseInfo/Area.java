package com.youlb.entity.houseInfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.youlb.entity.common.BaseModel;

/** 
 * @ClassName: Area.java 
 * @Description: 地区实体类 
 * @author: Pengjy
 * @date: 2015年6月23日
 * 
 */
@Entity
@Table(name="t_area")
public class Area extends BaseModel{
	 /**省份*/
	 @Column(name="FPROVINCE")
     private String province;
     /**市*/
	 @Column(name="fcity")
     private String city;
     /**区域编号 3位*/
	 @Column(name="FAREANUM")
     private String areaNum;
     /**备注*/
	 @Column(name="FREMARK")
     private String remark;
     
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAreaNum() {
		return areaNum;
	}
	public void setAreaNum(String areaNum) {
		this.areaNum = areaNum;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
      
     
     
}
