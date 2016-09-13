package com.youlb.entity.SMSManage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.youlb.entity.common.BaseModel;
import com.youlb.utils.common.SysStatic;
/**
 * 
* @ClassName: SMSManage.java 
* @Description: 短信网关配置实体类 
* @author: Pengjy
* @date: 2016年9月9日
*
 */
@Table(name="t_sms_manage")
@Entity
public class SMSManage extends BaseModel {
	private static final long serialVersionUID = -9028295458104402013L;
	/**ip*/
	@Column(name="fip")
    private String ip;
	/**端口*/
	@Column(name="fport")
    private Integer port;
	/**用户名*/
	@Column(name="fusername")
    private String username;
	/**密码*/
	@Column(name="fpwd")
    private String pwd;
	/**公司签名(【赛翼智能】unicode编码)*/
	@Column(name="fsign")
    private String sign;
	
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	 public String getOperate() {
		   	StringBuilder sb = new StringBuilder();
		   	sb.append("<a class='SMSManageWhiteList' rel='"+getId()+"' href='javascript:void(0)'>白名单</a>&nbsp;");
		   	sb.append("<a class='SMSManageWhiteListDownload'  href='"+SysStatic.PATH+"/mc/SMSManage/singleDownfile.do?id="+getId()+"'>导出白名单</a>&nbsp;");
				return sb.toString();
		}   
}
