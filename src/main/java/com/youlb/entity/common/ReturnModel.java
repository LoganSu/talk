package com.youlb.entity.common;


import com.fasterxml.jackson.annotation.JsonInclude;


/** 
 * @ClassName: ReturnModel.java 
 * @Description: 接口返回数据对象 
 * @author: Pengjy
 * @date: 2015年7月20日
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)//设置返回json不返回null字段
public class ReturnModel {
	/**返回信息类型*/
	private String code;
	/**返回字符串结果*/
	private String msg;
	/**返回字对象结果*/
	private Object result;
	/**返回字符服务器验证值*/
	private String token;
	
	
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	
	 
}
