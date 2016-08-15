package com.youlb.entity.management;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import com.youlb.entity.common.BaseModel;
import com.youlb.utils.helper.DateHelper;
@Entity
@Table(name="t_worker")
public class Worker extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4016781425743981230L;
    /**姓名*/
	@Column(name="fworkername")
    private String workerName;
	/**部门id*/
	@Column(name="fdepartmentid")
    private String departmentId;
	 /**电话*/
	@Column(name="fphone")
    private String phone;
	 /**性别*/
	@Column(name="fsex")
    private String sex;
	/**员工状态*/
	@Column(name="fstatus")
    private String status;
	/**账号*/
	@Column(name="fusername")
    private String username;
	/**密码*/
	@Column(name="fpassword")
    private String password;
	/**部门名称*/
	@Transient
	private String departmentName;
	/**部门id集合*/
	@Transient
	private String departmentIds;
	public String getSexStr(){
		String sexStr = "";
		if(StringUtils.isNotBlank(sex)){
			if("1".equals(sex)){
				sexStr="男";
			}else if("2".equals(sex)){
				sexStr="女";
			}
		}
		return sexStr;
	}
	
	public String getStatusStr(){
		String comefromStr = "";
		if(StringUtils.isNotBlank(status)){
			if("1".equals(status)){
				comefromStr="开通";
			}else if("2".equals(status)){
				comefromStr="暂停";
			}else if("3".equals(status)){
				comefromStr="关闭";
			}
		}
		return comefromStr;
	}
	
	
	public String getCreateTimeStr(){
		 if(getCreateTime()!=null){
	            String createTimeStr = DateHelper.dateFormat(getCreateTime(), "yyyy-MM-dd HH:mm:ss");		
	            return createTimeStr;
	    	}
	    	 return "";
	}
	
	
	public String getDepartmentIds() {
		return departmentIds;
	}

	public void setDepartmentIds(String departmentIds) {
		this.departmentIds = departmentIds;
	}

	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public String getWorkerName() {
		return workerName;
	}
	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}

	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
}
