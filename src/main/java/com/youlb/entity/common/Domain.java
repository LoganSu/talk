package com.youlb.entity.common;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/** 
 * @ClassName: Domain.java 
 * @Description: 域对象 
 * @author: Pengjy
 * @date: 2015年8月28日
 * 
 */
@Entity
@Table(name="t_domain")
public class Domain extends BaseModel {
	/**父域id*/
	@Column(name="FPARENTID")
    private String parentId;
	/**序列*/
	@Column(name="FDOMAINSN")
    private Integer domainSn;
	/**备注*/
	@Column(name="FREMARK")
    private String remark;
	/**层级，（每个域一个标号）*/
	@Column(name="FLAYER")
    private Integer layer;
	/**域内的实体id*/
	@Column(name="FENTITYID")
    private String entityId;
	/**子域集合*/
	@Transient
	private List<Domain> children;
	/**被选择的角色=1*/
	@Transient
	private Integer checked;
	
	public Integer getDomainSn() {
		return domainSn;
	}
	public void setDomainSn(Integer domainSn) {
		this.domainSn = domainSn;
	}
	public Integer getChecked() {
		return checked;
	}
	public void setChecked(Integer checked) {
		this.checked = checked;
	}
	public List<Domain> getChildren() {
		return children;
	}
	public void setChildren(List<Domain> children) {
		this.children = children;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getLayer() {
		return layer;
	}
	public void setLayer(Integer layer) {
		this.layer = layer;
	}
	public String getEntityId() {
		return entityId;
	}
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	
	
	
}
