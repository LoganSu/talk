package com.youlb.entity.countManage;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.youlb.entity.common.Model;

/** 
 * @ClassName: Users.java 
 * @Description: 注册用户 
 * @author: Pengjy
 * @date: 2015-11-23
 * 
 */
@Entity
@Table(name="t_users")
public class Users extends Model{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8652969482644972344L;
	/**id*/
    @Id
    @Column(name="id")
    private Integer id;
	/**用户名*/
    @Column(name="fusername")
    private String username;
    /**手机号*/
    @Column(name="fmobile_phone")
    private String mobilePhone;
    /**密码密文*/
    @Column(name="fcrypted_password")
    private String cryptedPsw;
    /**用户状态*/
    @Column(name="fstatus")
    private String status;
    /**备用字段*/
    @Column(name="fsalt")
    private String salt;
    /**备用字段*/
    @Column(name="fha1")
    private String ha1;
    /**电子邮箱*/
    @Column(name="femail")
    private String email;
    /**电子邮箱状态*/
    @Column(name="femail_status")
    private String emailStatus;
    /**电子邮箱验证码*/
    @Column(name="femail_verification_code")
    private String emailVerificationCode;
    /**真实姓名*/
    @Column(name="frealname")
    private String realName;
    /**固话号码*/
    @Column(name="fphone")
    private String phone;
    /**地址*/
    @Column(name="faddress")
    private String address;
    /**修改时间*/
    @Column(name="fupdated_at")
    private String updatedAt;
    /**创建时间*/
    @Column(name="FCREATETIME")
    private Date createTime;
    
    public String getOprator() {
		return "<a class='usersDelete' rel='"+getId()+"' href='javascript:void(0)'>删除</a>";
	}
    
    public String getCreateTimeStr() {
    	if(getCreateTime()!=null){
    		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String createTimeStr = sd.format(getCreateTime());  		
            return createTimeStr;
    	}
    	 return "";
	}
    
    public String getEmailStatusStr() {
    	String emailStatusStr="";
    	if(StringUtils.isNotBlank(emailStatus)){
    		if("true".equals(emailStatus)){
    			emailStatusStr="已认证";
    		}else if("false".equals(emailStatus)){
    			emailStatusStr="未认证";
    		}
    	}
		return emailStatusStr;
	} 
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCryptedPsw() {
		return cryptedPsw;
	}
	public void setCryptedPsw(String cryptedPsw) {
		this.cryptedPsw = cryptedPsw;
	}
	public String getStatus() {
		return status;
	}
	public String getStatusStr() {
		String statusStr ="";
		if(status!=null&&status.equals("1")){
			 statusStr = "正常";
		}
		return statusStr;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getHa1() {
		return ha1;
	}
	public void setHa1(String ha1) {
		this.ha1 = ha1;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmailStatus() {
		return emailStatus;
	}
	public void setEmailStatus(String emailStatus) {
		this.emailStatus = emailStatus;
	}
	public String getEmailVerificationCode() {
		return emailVerificationCode;
	}
	public void setEmailVerificationCode(String emailVerificationCode) {
		this.emailVerificationCode = emailVerificationCode;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	 
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
   
   
   
}
