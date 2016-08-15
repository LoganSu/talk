package com.youlb.entity.infoPublish;


public class AdPublishPictureDto {
	private String id;
    /**文件保存服务器地址*/
	 private String serverAddr;
    /**多媒体文件存储的相对路径*/
	 private String relativePath;
	 public String position;
	 private int index;
	 public String positionStr;
	 
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getPositionStr() {
		return positionStr;
	}
	public void setPositionStr(String positionStr) {
		this.positionStr = positionStr;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getServerAddr() {
		return serverAddr;
	}
	public void setServerAddr(String serverAddr) {
		this.serverAddr = serverAddr;
	}
	public String getRelativePath() {
		return relativePath;
	}
	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}
	 
	 
}
