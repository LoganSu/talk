package com.youlb.entity.houseInfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import com.youlb.entity.common.BaseModel;

/** 
 * @ClassName: Unit.java 
 * @Description: 单元实体类 
 * @author: Pengjy
 * @date: 2015年7月30日
 * 
 */
@Entity
@Table(name="t_unit")
public class Unit extends BaseModel {
	/**单元编号 2位*/
	@Column(name="FUNITNUM")
    private String unitNum;
	/**单元名称*/
	@Column(name="FUNITNAME")
    private String unitName;
	/**备注*/
	@Column(name="FREMARK")
    private String remark;
    @Transient
    private String parentId;
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
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getUnitNum() {
		return unitNum;
	}
	public void setUnitNum(String unitNum) {
		this.unitNum = unitNum;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
   
   
}
