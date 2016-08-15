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
 * @ClassName: adPublish.java 
 * @Description:  广告发布
 * @author: Pengjy
 * @date: 2015-12-2
 * 
 */
@Entity
@Table(name="t_adpublish")
public class AdPublish extends BaseModel {
	/**标题名称*/
//	@Column(name="ftitle")
//    public String title;
	/**发送时间选择类型 1：即时发送，2：定时发送*/
//	@Column(name="fsendtimetype")
//    public String sendTimeType;
	/**定时发送时间*/
//	@Column(name="fsendtime")
//    public String sendTime;
	/**广告类型  图片 视频*/
	@Column(name="fadtype")
    public String adType;
	/**终端机类型 门口机 移动端*/
	@Column(name="ftargetdevice")
    public String targetDevice;
	/**通知署名人*/
//	@Column(name="fadsign")
//    public String adSign;
	/**上传媒体文件*/
//	@Column(name="fadfilename")
//    public String adFileName;
	/**发送范围类型  全部  指定范围*/
	@Column(name="fsendtype")
    public String sendType;
	/**文件保存服务器地址*/
//	@Column(name="fserveraddr")
//    private String serverAddr;
	/**多媒体文件存储的相对路径*/
//	@Column(name="frelativepath")
//    private String relativePath;
	/**图片显示位置*/
//	@Column(name="fposition")
//    public String position;
	
	/**有效期*/
	@Column(name="fexpdate")
    public Date expDate;
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
	
	/**图片id集合*/
	@Transient
	private List<String> picId;
	/**图片web路径集合*/
	@Transient
	private List<String> picPath;
	@Transient
	private List<AdPublishPicture> adPics;
	/**所属运营商*/
	@Column(name="fcarrierid")
    public String carrierId;
	/**多媒体内容简介*/
//	@Column(name="faddetail")
//    public String adDetail;
	/**有效期字符串形式*/
	@Transient
    public String expDateStr;
	
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
	public void setExpDateStr(String expDateStr) {
		this.expDateStr = expDateStr;
	}
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
	public Date getExpDate() {
		return expDate;
	}
	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}
	public String getCarrierId() {
		return carrierId;
	}
	public void setCarrierId(String carrierId) {
		this.carrierId = carrierId;
	}
	public List<AdPublishPicture> getAdPics() {
		return adPics;
	}
	public void setAdPics(List<AdPublishPicture> adPics) {
		this.adPics = adPics;
	}
	public String getAdType() {
		return adType;
	}
	public void setAdType(String adType) {
		this.adType = adType;
	}
	public List<String> getPicPath() {
		return picPath;
	}
	public void setPicPath(List<String> picPath) {
		this.picPath = picPath;
	}
	public List<String> getPicId() {
		return picId;
	}
	public void setPicId(List<String> picId) {
		this.picId = picId;
	}
	public String getOpraterType() {
		return opraterType;
	}
	public void setOpraterType(String opraterType) {
		this.opraterType = opraterType;
	}
	
	 public String getOperate() {
	   	StringBuilder sb = new StringBuilder();
	   	sb.append("<a class='adPublishDetail' rel='"+getId()+"' href='javascript:void(0)'>详细</a>&nbsp;");
//	   	.append("<a class='adPublishUpdate' rel='"+getId()+"' cardStatus='1' href='javascript:void(0)'>修改</a>&nbsp;")
//	   	.append("<a class='adPublishDelete' rel='"+getId()+"' cardStatus='3' href='javascript:void(0)'>删除</a>&nbsp;");
	   
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
//	 public String getAdTypeStr() {
//			String adTypeStr="";
//			if(adType!=null){
//				if(SysStatic.one.equals(adType)){
//					adTypeStr="图片";
//				}else if(SysStatic.two.equals(adType)){
//					adTypeStr="视频";
//				} 
//			}
//			return adTypeStr;
//	}
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
	 
	 
	 
	
	
}
