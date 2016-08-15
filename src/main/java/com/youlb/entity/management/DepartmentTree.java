package com.youlb.entity.management;

import java.util.List;


public class DepartmentTree {
	private String id;
	/**名称*/
    private String departmentName;
	/**子部门*/
	private List<DepartmentTree> departmentTree;
	private String parentId;
	private Integer layer;
	public String getId() {
		return id;
	}
	
	
	public Integer getLayer() {
		return layer;
	}
	public void setLayer(Integer layer) {
		this.layer = layer;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public List<DepartmentTree> getDepartmentTree() {
		return departmentTree;
	}
	public void setDepartmentTree(List<DepartmentTree> departmentTree) {
		this.departmentTree = departmentTree;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
}
