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
@Entity
@Table(name="t_todaynews")
public class TodayNews  extends BaseModel{
    private static final long serialVersionUID = -3480684162570112850L;
    /**标题*/
    @Column(name="ftitle")
    private String title;
    /**图片url*/
    @Column(name="fpictureurl")
    private String pictureUrl;
    /**内容html地址*/
    @Column(name="fnewsurl")
    private String newsUrl;
    /**发布类型 1全部 2指定*/
    @Column(name="fsendtype")
    private String sendType;
    /**编辑框内容*/
    @Transient
    private String todayNewsDetailEditor;
    /**多选框*/
    @Transient
	public List<String> treecheckbox;
    /**终端机类型 门口机 移动端*/
    @Column(name="ftargetdevice")
    public String targetDevice;
    /**查询开始时间*/
    @Transient
    public String startTime;
    /**查询结束时间*/
    @Transient
    public String endTime;
    /**操作类型*/
    @Transient
    public String opraterType;
    /**状态  null未发布 1已发布*/
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
	/**所属运营商*/
	@Column(name="fcarrierid")
    public String carrierId;
	/**是否是本运营商发布的公告*/
	@Transient
    public Boolean self;
	

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
    public String getCarrierId() {
		return carrierId;
	}
	public void setCarrierId(String carrierId) {
		this.carrierId = carrierId;
	}
	public Boolean getSelf() {
		return self;
	}
	public void setSelf(Boolean self) {
		this.self = self;
	}
	public String getOperate() {
	   	StringBuilder sb = new StringBuilder();
	   	sb.append("<a class='todayNewsDetail' rel='"+getId()+"' href='javascript:void(0)'>详细</a>&nbsp;");
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
    
    public String getTargetDeviceStr() {
		String targetDeviceStr="";
		if(targetDevice!=null){
			if(SysStatic.one.equals(targetDevice)){
				targetDeviceStr="门口机";
			}else if(SysStatic.two.equals(targetDevice)){
				targetDeviceStr="移动端";
			}
		}
		return targetDeviceStr;
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
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	public String getAddOperator() {
		return addOperator;
	}
	public void setAddOperator(String addOperator) {
		this.addOperator = addOperator;
	}
	public String getOpraterType() {
		return opraterType;
	}
	public void setOpraterType(String opraterType) {
		this.opraterType = opraterType;
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
	public String getTargetDevice() {
		return targetDevice;
	}
	public void setTargetDevice(String targetDevice) {
		this.targetDevice = targetDevice;
	}
	public String getSendType() {
		return sendType;
	}
	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	public List<String> getTreecheckbox() {
		return treecheckbox;
	}
	public void setTreecheckbox(List<String> treecheckbox) {
		this.treecheckbox = treecheckbox;
	}
	public String getTodayNewsDetailEditor() {
		return todayNewsDetailEditor;
	}
	public void setTodayNewsDetailEditor(String todayNewsDetailEditor) {
		this.todayNewsDetailEditor = todayNewsDetailEditor;
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
	   
}
