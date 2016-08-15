package com.youlb.entity.infoPublish;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import com.youlb.entity.common.BaseModel;
import com.youlb.utils.common.SysStatic;
import com.youlb.utils.helper.DateHelper;

/** 
 * @ClassName: InfoManage.java 
 * @Description:  消息发布
 * @author: Pengjy
 * @date: 2015-12-1
 * 
 */
@Entity
@Table(name="t_infopublish")
public class InfoPublish extends BaseModel {
	/**标题名称*/
	@Column(name="ftitle")
    public String title;
	/**信息类型  公告 通知 消息 新闻 */
	@Column(name="finfotype")
    public String infoType;
	/**终端机类型 门口机 移动端*/
	@Column(name="ftargetdevice")
    public String targetDevice;
	/**通知署名人*/
	@Column(name="finfosign")
    public String infoSign;
	/**通知内容*/
	@Column(name="finfodetail")
    public String infoDetail;
	/**发送范围类型  全部  指定范围*/
	@Column(name="fsendtype")
    public String sendType;
	/**所属运营商*/
	@Column(name="fcarrierid")
    public String carrierId;
	/**发送时间选择类型 1：即时发送，2：定时发送*/
	@Column(name="fsendtimetype")
    public String sendTimeType;
	/**定时发送时间*/
	@Column(name="fsendtime")
    public String sendTime;
	/**离线消息设置类型*/
	@Column(name="foffline")
    public String offLine;
	/**离线消息 天*/
	@Column(name="fdays")
    public Integer days;
	/**离线消息 小时*/
	@Column(name="fhours")
    public Integer hours;
	/**通知样式 */
	@Column(name="fmessagestyle")
    public String messageStyle;
	/**通知操作 1:打开应用，2：打开url */
	@Column(name="fmessageoprate")
    public String messageOprate;
	/**通知操作需要打开的url*/
	@Column(name="fopenurl")
    public String openUrl;
	/**有效期*/
	@Column(name="fexpdate")
    public Date expDate;
	/**状态 0未审核 1已审核 2审核不通过*/
	@Column(name="fstatus")
    public String status;
	/**发布人*/
	@Column(name="fpublish_operator")
    public String publishOperator;
	/**发布时间*/
	@Column(name="fpublish_time")
    public Date publishTime;
	/**创建人*/
	@Column(name="fadd_operator")
    public String addOperator;
	
	/**是否是本运营商发布的公告*/
	@Transient
    public Boolean self;
	
	
	@Transient
	public List<String> treecheckbox;
	/**查询开始时间*/
	@Transient
    public String startTime;
	/**查询结束时间*/
	@Transient
    public String endTime;
	/**操作类型 */
	@Transient
	private String opraterType;
	/**有效期字符串形式*/
	@Transient
    public String expDateStr;
	
	
	public String getSelfStr() {
		String selfStr="";
		if(self!=null){
			if(self){
				selfStr="是";
			}else{
				selfStr="否";
			}
		}
		return selfStr;
	}
	public String getExpDateStr() {
		if(expDate!=null){
			expDateStr=DateHelper.dateFormat(expDate, "yyyy-MM-dd");
		}
		return expDateStr;
	}
	
	public String getPublishTimeStr() {
		String expDateStr="";
		if(publishTime!=null){
			expDateStr=DateHelper.dateFormat(publishTime, "yyyy-MM-dd HH:mm:ss");
		}
		return expDateStr;
	}
	public String getStatusStr() {
		String statusStr="";
		if(StringUtils.isNotBlank(status)){
			if("1".equals(status)){
				statusStr="已发布";
			}
		}else{
			statusStr="未发布";
		}
		return statusStr;
	}
	
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPublishOperator() {
		return publishOperator;
	}
	public void setPublishOperator(String publishOperator) {
		this.publishOperator = publishOperator;
	}
	public String getAddOperator() {
		return addOperator;
	}
	public void setAddOperator(String addOperator) {
		this.addOperator = addOperator;
	}
	public Boolean getSelf() {
		return self;
	}
	public void setSelf(Boolean self) {
		this.self = self;
	}
	public void setExpDateStr(String expDateStr) {
		this.expDateStr = expDateStr;
	}
	public Date getExpDate() {
		return expDate;
	}
	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}
	public String getOpraterType() {
		return opraterType;
	}
	public void setOpraterType(String opraterType) {
		this.opraterType = opraterType;
	}
	
	 public String getOperate() {
	   	StringBuilder sb = new StringBuilder();
	   	sb.append("<a class='infoPublishDetail' rel='"+getId()+"' href='javascript:void(0)'>详细</a>&nbsp;");
//	   	.append("<a class='infoPublishUpdate' rel='"+getId()+"' cardStatus='1' href='javascript:void(0)'>修改</a>&nbsp;")
//	   	.append("<a class='infoPublishDelete' rel='"+getId()+"' cardStatus='3' href='javascript:void(0)'>删除</a>&nbsp;");
	   
			return sb.toString();
		}
	 public String getCreateTimeStr() {
		 if(getCreateTime()!=null){
	    		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	            String createTimeStr = sd.format(getCreateTime());  		
	            return createTimeStr;
	    	}
	    	 return "";
	 }
//	 public String getInfoTypeStr() {
//			String infoTypeStr="";
//			if(infoType!=null){
//				if(SysStatic.one.equals(infoType)){
//					infoTypeStr="公告";
//				}else if(SysStatic.two.equals(infoType)){
//					infoTypeStr="通知";
//				}else if(SysStatic.three.equals(infoType)){
//					infoTypeStr="消息";
//				}
//			}
//			return infoTypeStr;
//	}
	 public String getTargetDeviceStr() {
			String targetDeviceStr="";
			if(targetDevice!=null){
				if(SysStatic.one.equals(targetDevice)){
					targetDeviceStr="门口机";
				}else if(SysStatic.two.equals(targetDevice)){
					targetDeviceStr="移动端";
				}else if(SysStatic.three.equals(targetDevice)){
					targetDeviceStr="管理机";
				}
			}
			return targetDeviceStr;
	}	 
	
	public String getOpenUrl() {
		return openUrl;
	}
	public void setOpenUrl(String openUrl) {
		this.openUrl = openUrl;
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
	public String getOffLine() {
		return offLine;
	}
	public void setOffLine(String offLine) {
		this.offLine = offLine;
	}
	public Integer getDays() {
		return days;
	}
	public void setDays(Integer days) {
		this.days = days;
	}
	public Integer getHours() {
		return hours;
	}
	public void setHours(Integer hours) {
		this.hours = hours;
	}
	public String getMessageStyle() {
		return messageStyle;
	}
	public void setMessageStyle(String messageStyle) {
		this.messageStyle = messageStyle;
	}
	public String getMessageOprate() {
		return messageOprate;
	}
	public void setMessageOprate(String messageOprate) {
		this.messageOprate = messageOprate;
	}
	public String getCarrierId() {
		return carrierId;
	}
	public void setCarrierId(String carrierId) {
		this.carrierId = carrierId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public List<String> getTreecheckbox() {
		return treecheckbox;
	}
	public void setTreecheckbox(List<String> treecheckbox) {
		this.treecheckbox = treecheckbox;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
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
	public String getInfoSign() {
		return infoSign;
	}
	public void setInfoSign(String infoSign) {
		this.infoSign = infoSign;
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
	
	
	
}
