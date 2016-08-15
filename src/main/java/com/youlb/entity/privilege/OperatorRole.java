package com.youlb.entity.privilege;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/** 
 * @ClassName: ManageUserRole.java 
 * @Description: 管理用户权限中间表
 * @author: Pengjy
 * @date: 2015年7月7日
 * 
 */
@Entity
@Table(name="t_operator_role")
public class OperatorRole implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6911296889663637308L;
	/**用户id*/
   @Id
   @Column(name="FOPERATORID")
   private String operatorId;
   /**角色id*/
   @Id
   @Column(name="FROLEID")
   private String roleId;
   
	 
	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	   
   
}
