package com.youlb.entity.privilege;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.ser.std.SerializableSerializer;

/** 
 * @ClassName: RoleAuthority.java 
 * @Description: 角色权限中间表 
 * @author: Pengjy
 * @date: 2015年7月8日
 * 
 */
@Entity
@Table(name="t_role_privilege")
public class RolePrivilege implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7040747654065163799L;
	/**角色ID*/
   @Column(name="FROLEID")
   @Id
   private String roleId;
   /**权限ID*/
   @Column(name="FPRIVILEGEID")
   @Id
   private String privilegeId;
   
   
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getPrivilegeId() {
		return privilegeId;
	}
	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
	}
	 
   
   
   
}
