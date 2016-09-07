package com.youlb.entity.personnel;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import com.youlb.entity.common.BaseModel;
import com.youlb.utils.common.SysStatic;

/** 
 * @ClassName: DwellerInfo.java 
 * @Description: 居民信息 
 * @author: Pengjy
 * @date: 2015年9月14日
 * 
 */
@Entity
@Table( name="t_dweller")
public class Dweller extends BaseModel {
    /**姓名*/
	@Column(name="fname")
	private String fname;
	/**性别*/
	@Column(name="fsex")
	private String sex;
//	/**年龄*/
//	@Column(name="fage")
//	private Integer age;
	/**身份证号码*/
	@Column(name="fidnum")
	private String idNum;
	/**电话*/
	@Column(name="fphone")
	private String phone;
	/**邮箱*/
	@Column(name="femail")
	private String email;
	/**文化程度 小学、初中、高中（中专、中技）、大专、本科、研究生、其他*/
	@Column(name="feducation")
	private String education;
	/**单位名称*/
	@Column(name="fcompanyname")
	private String companyName;
	/**籍贯*/
	@Column(name="fnativeplace")
	private String nativePlace;
	/**备注*/
	@Column(name="fremark")
	private String remark;
	/**运营商id*/
	@Column(name="fcarrier_id")
	private String carrierId;
	
	/**选择的域id*/
	@Transient
	private List<String> treecheckbox;
	@Transient
	private String address;
	@Transient
	private String phoneCity;
	/**住户类型 1:户主；0：非户主*/
//	@Column(name="fdwellertype")
	@Transient
	private String dwellerType;
	 public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEducationStr() {
		 String educationStr = "";
		 if(StringUtils.isNotBlank(this.education)){
			 if(SysStatic.one.equals(this.education)){
				 educationStr="小学";
			 }else if(SysStatic.two.equals(this.education)){
				 educationStr="初中";
			 }else if(SysStatic.three.equals(this.education)){
				 educationStr="高中（中专、中技）";
			 }else if(SysStatic.four.equals(this.education)){
				 educationStr="大专";
			 }else if(SysStatic.five.equals(this.education)){
				 educationStr="本科";
			 }else if(SysStatic.six.equals(this.education)){
				 educationStr="研究生";
			 }else if(SysStatic.seven.equals(this.education)){
				 educationStr="其他";
			 } 
			 
		 }
		 
		 return educationStr;
	 }
	 
	    
	public String getPhoneCity() {
		return phoneCity;
	}
	public void setPhoneCity(String phoneCity) {
		this.phoneCity = phoneCity;
	}
	public String getCarrierId() {
		return carrierId;
	}
	public void setCarrierId(String carrierId) {
		this.carrierId = carrierId;
	}
	public List<String> getTreecheckbox() {
		return treecheckbox;
	}
	public void setTreecheckbox(List<String> treecheckbox) {
		this.treecheckbox = treecheckbox;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getNativePlace() {
		return nativePlace;
	}
	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}
	 
	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getIdNum() {
		return idNum;
	}
	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	public String getDwellerType() {
		return dwellerType;
	}
	public String getDwellerTypeStr() {
		String dwellerTypeStr ="";
		if(StringUtils.isNotBlank(dwellerType)){
			if("0".equals(dwellerType)){
				dwellerTypeStr="非户主";
			}else if("1".equals(dwellerType)){
				dwellerTypeStr="户主";
			}
		}
		return dwellerTypeStr;
	}
	public void setDwellerType(String dwellerType) {
		this.dwellerType = dwellerType;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
}
