package com.youlb.entity.infoPublish;

import java.util.List;

/** 
 * @ClassName: adPublish.java 
 * @Description:  广告发布
 * @author: Pengjy
 * @date: 2015-12-2
 * 
 */
public class AdPublishDto {
	/**终端机类型 门口机 移动端*/
    public String targetDevice;
    /**标签集合 */
    public List<String> tagList;
    /**图片web路径集合*/
	private List<String> picPath;
//	/**图片显示位置*/
//	public String position;
	/**类型*/
	public String adType;
	/**图片对象集合*/
	public List<AdPublishPictureDto> adPics;
	
	 
	public List<AdPublishPictureDto> getAdPics() {
		return adPics;
	}
	public void setAdPics(List<AdPublishPictureDto> adPics) {
		this.adPics = adPics;
	}
	public String getAdType() {
		return adType;
	}
	public void setAdType(String adType) {
		this.adType = adType;
	}
//	public String getPosition() {
//		return position;
//	}
//	public void setPosition(String position) {
//		this.position = position;
//	}
	public String getTargetDevice() {
		return targetDevice;
	}
	public void setTargetDevice(String targetDevice) {
		this.targetDevice = targetDevice;
	}
	public List<String> getTagList() {
		return tagList;
	}
	public void setTagList(List<String> tagList) {
		this.tagList = tagList;
	}
	public List<String> getPicPath() {
		return picPath;
	}
	public void setPicPath(List<String> picPath) {
		this.picPath = picPath;
	}
    
    
	 
	 
	
	 
}
