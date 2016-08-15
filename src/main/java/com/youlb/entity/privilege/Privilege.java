package com.youlb.entity.privilege;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.youlb.entity.common.BaseModel;

/** 
 * @ClassName: Privilege.java 
 * @Description: 权限对象 
 * @author: Pengjy
 * @date: 2015年8月25日
 * 
 */
@Entity
@Table(name="t_privilege")
public class Privilege extends BaseModel {
   /**父节点id*/
   @Column(name="FPARENTID")
   private String parentId;
   /**层级*/
   @Column(name="FLAYER")
   private Integer layer;
   /**名称*/
   @Column(name="FNAME")
   private String name;
   /**权限key值全局唯一*/
   @Column(name="FKEY")
   private String key;
   /**描述*/
   @Column(name="FDESCRIPTION")
   private String description;
   /**操作类型*/
   @Column(name="FTYPE")
   private String type;
   /**组内排序*/
   @Column(name="FGROUPORDER")
   private Integer groupOrder;
   /**子权限*/
   @Transient
   private List<Privilege> children;
   /**被选择的角色=1*/
	@Transient
	private Integer checked;
	 
	public Integer getChecked() {
		return checked;
	}
	public void setChecked(Integer checked) {
		this.checked = checked;
	}
   
   
	 
	public List<Privilege> getChildren() {
		return children;
	}
	public void setChildren(List<Privilege> children) {
		this.children = children;
	}
	public Integer getGroupOrder() {
		return groupOrder;
	}
	public void setGroupOrder(Integer groupOrder) {
		this.groupOrder = groupOrder;
	}
	public String getKey() {
	    return key;
	}
	public void setKey(String key) {
		this.key = key;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	   
   
   
}
