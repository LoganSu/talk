package com.youlb.entity.common;

/** 
 * @ClassName: Params.java 
 * @Description: 封装页面参数对象
 * @author: Pengjy
 * @date: 2015年7月2日
 * 
 */
public class SearchParams {
	private String parentId;
	private String modulePath;
	private String roomId;
	private String module;
	
	
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getModulePath() {
		return modulePath;
	}
	public void setModulePath(String modulePath) {
		this.modulePath = modulePath;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	
	
}
