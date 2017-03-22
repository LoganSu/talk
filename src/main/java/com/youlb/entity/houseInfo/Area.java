package com.youlb.entity.houseInfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

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
	 /**
	 * 
	 */
	private static final long serialVersionUID = -6247409781464527864L;
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
	 /**是否创建sip账号1否   2是*/
	@Transient
	private String createSipNum;
	@Transient
	private String sipNum;
	@Transient
	private String sipNumPsw;
	
	
	
	public String getSipNum() {
		return sipNum;
	}
	public void setSipNum(String sipNum) {
		this.sipNum = sipNum;
	}
	public String getSipNumPsw() {
		if(StringUtils.isNotBlank(sipNumPsw)){
			StringBuilder sb = new StringBuilder();
			sb.append("<a href =\"javascript:alert('");
			sb.append(sipNumPsw);
			sb.append("')\">查看</a>");
			return sb.toString();
		}else{
			return "";
		}
	}
	public void setSipNumPsw(String sipNumPsw) {
		this.sipNumPsw = sipNumPsw;
	}
	public String getCreateSipNum() {
		return createSipNum;
	}
	public void setCreateSipNum(String createSipNum) {
		this.createSipNum = createSipNum;
	}
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
