package com.youlb.entity.infoPublish;

import java.util.Date;
import java.util.List;


/** 
 * @ClassName: InfoManage.java 
 * @Description:  消息发布
 * @author: Pengjy
 * @date: 2015-12-1
 * 
 */
public class InfoPublishDto{
	/**id*/
    public String id;
	/**标题名称*/
    public String title;
	/**通知内容*/
    public String infoDetail;
	/**发送范围类型  全部  指定范围*/
    public String sendType;
	/**发送时间选择类型 1：即时发送，2：定时发送*/
    public String sendTimeType;
	/**定时发送时间*/
    public String sendTime;
	/**离线消息设置类型*/
//    public String offLine;
	/**离线消息 天*/
//    public Integer days;
	/**离线消息 小时*/
//    public Integer hours;
	/**通知样式 */
//    public String messageStyle;
	/**通知操作 1:打开应用，2：打开url */
//    public String messageOprate;
	/**通知操作需要打开的url*/
//    public String openUrl;
    /**标签集合 */
    public List<String> tagList;
    /**终端机类型 门口机 移动端*/
    public String targetDevice;
    /**信息类型  公告 通知 消息 新闻 */
    public String infoType;
    /**图片url */
//    public String iconUrl;
    /**署名人 */
    public String infoSign;
    /**有效期*/
    public Date expDate;
    /**发布时间*/
    public Date publishTime;
    /**社区标签集合 */
    public List<String> neibTagList;
	
    
	public List<String> getNeibTagList() {
		return neibTagList;
	}
	public void setNeibTagList(List<String> neibTagList) {
		this.neibTagList = neibTagList;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	 
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	 
	public Date getExpDate() {
		return expDate;
	}
	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}
	public String getInfoSign() {
		return infoSign;
	}
	public void setInfoSign(String infoSign) {
		this.infoSign = infoSign;
	}
	//	public String getIconUrl() {
//		return iconUrl;
//	}
//	public void setIconUrl(String iconUrl) {
//		this.iconUrl = iconUrl;
//	}
	public String getInfoType() {
		return infoType;
	}
	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getInfoDetail() {
		return infoDetail;
	}
	public void setInfoDetail(String infoDetail) {
		this.infoDetail = infoDetail;
	}
	public String getSendType() {
		return sendType;
	}
	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	public String getSendTimeType() {
		return sendTimeType;
	}
	public void setSendTimeType(String sendTimeType) {
		this.sendTimeType = sendTimeType;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	 
	
}
