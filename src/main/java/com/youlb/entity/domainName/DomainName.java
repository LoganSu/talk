package com.youlb.entity.domainName;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.youlb.entity.common.BaseModel;

@Entity
@Table(name="t_domain_name")
public class DomainName extends BaseModel {

	private static final long serialVersionUID = -5337033104929978187L;
	/**平台*/
	@Column(name="fpkatform")
    private String platform;
	/**名称*/
	@Column(name="fname")
    private String fname;
	/**域名*/
	@Column(name="fdomain")
    private String domain;
	/**备注*/
	@Column(name="fremark")
    private String remark;
	/**父id*/
	@Column(name="fparentid")
    private String parentid;
	/**层级*/
	@Column(name="flayer")
    private Integer layer;
	
	
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public Integer getLayer() {
		return layer;
	}
	public void setLayer(Integer layer) {
		this.layer = layer;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	
}
