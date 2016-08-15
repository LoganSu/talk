package com.youlb.entity.privilege;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.youlb.entity.common.BaseModel;

/** 
 * @ClassName: Role.java 
 * @Description: 角色model
 * @author: Pengjy
 * @date: 2015年7月7日
 * 
 */
@Entity
@Table(name="t_role")
public class Role extends BaseModel {
	/**角色名称*/
	@Column(name="FROLENAME")
	private String roleName;
	/**角色描述*/
	@Column(name="FDESCRIPTION")
	private String description;
//	/**父角色id*/
//	@Column(name="fparentRoleId")
//	private String parentRoleId;
	/**运营商id*/
	@Column(name="FCARRIERID")
	private String carrierId;
	/**是否是管理员角色*/
	@Column(name="FISADMIN")
	private Integer isAdmin;
	/**权限集合*/
	@Transient
	private List<String> privilegeIds;
	/**被选择的角色=1*/
	@Transient
	private Integer checked;
	/**运营商角色标识*/
	@Transient
	private String isCarrier;
	/**操作权限多选框的值*/
	@Transient
	private List<String> treecheckbox; 
	/**域多选框的值*/
	@Transient
	private List<String> domainIds; 
	 
	
	public List<String> getDomainIds() {
		return domainIds;
	}
	public void setDomainIds(List<String> domainIds) {
		this.domainIds = domainIds;
	}
	public List<String> getTreecheckbox() {
		return treecheckbox;
	}
	public void setTreecheckbox(List<String> treecheckbox) {
		this.treecheckbox = treecheckbox;
	}
	public String getIsCarrier() {
		return isCarrier;
	}
	public void setIsCarrier(String isCarrier) {
		this.isCarrier = isCarrier;
	}
	
	
	
	public Integer getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}
	public Integer getChecked() {
		return checked;
	}
	public void setChecked(Integer checked) {
		this.checked = checked;
	}
	public List<String> getPrivilegeIds() {
		return privilegeIds;
	}
	public void setPrivilegeIds(List<String> privilegeIds) {
		this.privilegeIds = privilegeIds;
	}
	public String getCarrierId() {
		return carrierId;
	}
	public void setCarrierId(String carrierId) {
		this.carrierId = carrierId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
//	public String getParentRoleId() {
//		return parentRoleId;
//	}
//	public void setParentRoleId(String parentRoleId) {
//		this.parentRoleId = parentRoleId;
//	}
	
	
}
