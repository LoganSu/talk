package com.youlb.entity.management;

import java.util.List;

import javax.persistence.Column;


import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.youlb.entity.common.BaseModel;
import com.youlb.utils.helper.DateHelper;

/**
 * 
* @ClassName: Department.java 
* @Description: 部门model 
* @author: Pengjy
* @date: 2016年6月14日
*
 */
@Entity
@Table(name="t_department")
public class Department extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2456514686491039653L;
	/**名称*/
	@Column(name="fdepartmentname")
    private String departmentName;
	/**描述*/
	@Column(name="fdescription")
    private String description;
	/**电话*/
	@Column(name="ftel")
    private String tel;
	/**地址*/
	@Column(name="faddress")
    private String address;
	/**父id*/
	@Column(name="fparentid")
    private String parentId;
	/**组内排序*/
	@Column(name="flayer")
    private Integer layer;
	/**父部门名称*/
	@Transient
	private String parentName;
	/**域集合*/
	@Transient
	private List<String> domainIds;
	
	public String getCreateTimeStr(){
		 if(getCreateTime()!=null){
	            String createTimeStr = DateHelper.dateFormat(getCreateTime(), "yyyy-MM-dd HH:mm:ss");		
	            return createTimeStr;
	    	}
	    	 return "";
	}
	
	
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public List<String> getDomainIds() {
		return domainIds;
	}
	public void setDomainIds(List<String> domainIds) {
		this.domainIds = domainIds;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public Integer getLayer() {
		return layer;
	}
	public void setLayer(Integer layer) {
		this.layer = layer;
	}
	 
	 
	
	
}
