package com.youlb.entity.countManage;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import com.youlb.entity.common.BaseModel;
import com.youlb.utils.helper.DateHelper;
@Entity
@Table(name="sip_registrations")
public class SipCount extends BaseModel {
	private static final long serialVersionUID = -5426530583993318568L;
	/**sip账号*/
	@Column(name="sip_user")
    private String sipUser;
	/**设备ip*/
	@Column(name="network_ip")
    private String networkIp;
	/**服务器ip*/
	@Column(name="server_host")
    private String serverHost;
	/**设备类型*/
	@Column(name="user_agent")
    private String userAgent;
	/**注册时间*/
	@Column(name="expires")
    private Long expires;
	/**协议tcp or udp*/
	@Column(name="status")
    private String status;
	/**地址*/
	@Transient
    private String address;
	/**账号*/
	@Transient
    private String username;
	/**账号类型*/
	@Transient
    private String countType;
	/**域id*/
	@Transient
    private String domainId;
	
	
	public String getExpiresStr() {
		String expiresStr = "";
		if(expires!=null){
			expiresStr = DateHelper.dateFormat(new Date(expires*1000), "yyyy-MM-dd HH:mm:ss");
		}
		return expiresStr;
	}
	
	public String getCountTypeStr() {
		String countTypeStr="";
		if(StringUtils.isNotBlank(countType)){
			if("1".equals(countType)){
				countTypeStr="移动端";
			}else if("2".equals(countType)){
				countTypeStr="门口机";
			}else if("3".equals(countType)){
				countTypeStr="物业员工sip";
			}else if("4".equals(countType)){
				countTypeStr="社区账号sip";
			}else if("5".equals(countType)){
				countTypeStr="管理机";
			}
		}
		return countTypeStr;
	}
	
	public String getDomainId() {
		return domainId;
	}
	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}
	public String getCountType() {
		return countType;
	}
	public void setCountType(String countType) {
		this.countType = countType;
	}
	public Long getExpires() {
		return expires;
	}
	public void setExpires(Long expires) {
		this.expires = expires;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSipUser() {
		return sipUser;
	}
	public void setSipUser(String sipUser) {
		this.sipUser = sipUser;
	}
	public String getNetworkIp() {
		return networkIp;
	}
	public void setNetworkIp(String networkIp) {
		this.networkIp = networkIp;
	}
	public String getServerHost() {
		return serverHost;
	}
	public void setServerHost(String serverHost) {
		this.serverHost = serverHost;
	}
	public String getUserAgent() {
		return userAgent;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	
}
