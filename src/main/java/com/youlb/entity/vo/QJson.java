package com.youlb.entity.vo;

/** 
 * @ClassName: QJson.java 
 * @Description: TODO 
 * @author: Pengjy
 * @date: 2015年8月6日
 * 
 */
public class QJson {
	private boolean success = true;
    private String msg;// 消息
    private String type;// 类型
    private Object object;// 对象
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
    
    
}
