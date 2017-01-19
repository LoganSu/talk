package com.youlb.entity.baseInfo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.youlb.entity.common.BaseModel;

/** 
 * @ClassName: Carrier.java 
 * @Description: 运营商信息model
 * @author: Pengjy
 * @date: 2015年8月28日
 * 
 */
@Entity
@Table(name="t_carrier")
public class Carrier extends BaseModel {
   /**运营商名称*/
   @Column(name="fcarrierName")
   private String carrierName;
   /**电话*/
   @Column(name="ftel")
   private String tel;
   /**邮编*/
   @Column(name="fpostcode")
   private String postcode;
   /**传真*/
   @Column(name="ffax")
   private String fax;
   /**地址*/
   @Column(name="faddress")
   private String address;
   /**备注*/
   @Column(name="fremark")
   private String remark;
   /**运营商类型1：普通 2：特殊 */
   @Column(name="fisnormal")
   private String isNormal;
   /**运营商简称 */
   @Column(name="fcarrierNum")
   private String carrierNum;
   /**关联域名id */
   @Column(name="fdomain_name_id")
   private String domainNameId;
   /**平台名称 */
   @Column(name="fplatform_name")
   private String platformName;
   /**二级域名id */
   @Transient
   private String domainNameParentId;
   
//   @Transient
//   private List<String> domainIds;
   /**选择的域id*/
	@Transient
	private List<String> treecheckbox;
   
   
	 
	 
	public String getPlatformName() {
		return platformName;
	}
	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}
	public String getDomainNameParentId() {
		return domainNameParentId;
	}
	public void setDomainNameParentId(String domainNameParentId) {
		this.domainNameParentId = domainNameParentId;
	}
	public String getDomainNameId() {
		return domainNameId;
	}
	public void setDomainNameId(String domainNameId) {
		this.domainNameId = domainNameId;
	}
	public String getCarrierNum() {
		return carrierNum;
	}
	public void setCarrierNum(String carrierNum) {
		this.carrierNum = carrierNum;
	}
	public String getIsNormal() {
		return isNormal;
	}
	public void setIsNormal(String isNormal) {
		this.isNormal = isNormal;
	}
	public List<String> getTreecheckbox() {
		return treecheckbox;
	}
	public void setTreecheckbox(List<String> treecheckbox) {
		this.treecheckbox = treecheckbox;
	}
	public String getCarrierName() {
		return carrierName;
	}
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
   
   
}
