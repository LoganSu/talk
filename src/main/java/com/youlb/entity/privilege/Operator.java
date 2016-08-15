package com.youlb.entity.privilege;


import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.youlb.entity.baseInfo.Carrier;
import com.youlb.entity.common.BaseModel;

/** 
 * @ClassName: User 
 * @Description: TODO 
 * @author: Pengjy
 * @date: 2015年5月27日
 * 
 */
@Entity
@Table(name="t_operator")
//@AttributeOverrides(value={
//		@AttributeOverride(name="id",column=@Column(name="id")),
//		@AttributeOverride(name="delFlag",column=@Column(name="FDELETEFLAG")),//重写删除标识
//		@AttributeOverride(name="createTime",column=@Column(name="FCREATETIME"))//重写创建时间
//		})
public class Operator extends BaseModel {
	/**登入名*/
	@Column(name="FLOGINNAME")
	private String loginName;
	/**密码*/
	@Column(name="FPASSWORD")
	private String password;
	/**真实姓名*/
	@Column(name="FREALNAME")
	private String realName;
	/**电话*/
	@Column(name="FPHONE")
	private String phone;
	/**用户是否在线*/
	@Column(name="FLOGINSTATUS")
	private Integer loginStatus;
	/**用户登入时间*/
	@Column(name="FLOGINTIME")
	private Date loginTime;
	/**用户退出时间*/
	@Column(name="FLOGOUTTIME")
	private Date logOutTime;
	/**是否是管理员*/
	@Column(name="FISADMIN")
	private Integer isAdmin;
	/**登录手机验证码*/
	@Transient
	private String verificationCode;
	
	
	/**操作权限集合*/
	@Transient
	private List<String> privilegeList;
	/**角色id集合*/
	@Transient
	private List<String> roleIds;
	/**域id集合*/
	@Transient
	private List<String> domainIds;
	/**运营商*/
	@Transient
	private Carrier carrier;
	/**运营商用户标识*/
	@Transient
	private String isCarrier;
	/**新密码*/
	@Transient
	private String newPassword;
	/**确认新密码*/
	@Transient
	private String surePassword; 
	 
	public String getVerificationCode() {
		return verificationCode;
	}
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	public String getSurePassword() {
		return surePassword;
	}
	public void setSurePassword(String surePassword) {
		this.surePassword = surePassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getIsCarrier() {
		return isCarrier;
	}
	public void setIsCarrier(String isCarrier) {
		this.isCarrier = isCarrier;
	}
	public Carrier getCarrier() {
		return carrier;
	}
	public void setCarrier(Carrier carrier) {
		this.carrier = carrier;
	}
	public List<String> getDomainIds() {
		return domainIds;
	}
	public void setDomainIds(List<String> domainIds) {
		this.domainIds = domainIds;
	}
	public Integer getLoginStatus() {
		return loginStatus;
	}
	public void setLoginStatus(Integer loginStatus) {
		this.loginStatus = loginStatus;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	public Integer getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}
	public List<String> getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(List<String> roleIds) {
		this.roleIds = roleIds;
	}
	  
	public List<String> getPrivilegeList() {
		return privilegeList;
	}
	public void setPrivilegeList(List<String> privilegeList) {
		this.privilegeList = privilegeList;
	}
	public Date getLogOutTime() {
		return logOutTime;
	}
	public void setLogOutTime(Date logOutTime) {
		this.logOutTime = logOutTime;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}
