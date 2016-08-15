package com.youlb.entity.infoPublish;

import java.util.Date;
import java.util.List;


public class TodayNewsDto {
	/**标题名称*/
    public String title;
    /**图片url*/
    private String pictureUrl;
    /**内容html地址*/
    private String newsUrl;
    /**标签集合 */
    public List<String> tagList;
    /**终端机类型 门口机 移动端*/
    public String targetDevice;
    /**创建时间*/
    private Date publishTime;
    
    
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	public String getNewsUrl() {
		return newsUrl;
	}
	public void setNewsUrl(String newsUrl) {
		this.newsUrl = newsUrl;
	}
	public List<String> getTagList() {
		return tagList;
	}
	public void setTagList(List<String> tagList) {
		this.tagList = tagList;
	}
	public String getTargetDevice() {
		return targetDevice;
	}
	public void setTargetDevice(String targetDevice) {
		this.targetDevice = targetDevice;
	}
    
    
}
