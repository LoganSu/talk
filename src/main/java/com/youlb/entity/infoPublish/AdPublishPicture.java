package com.youlb.entity.infoPublish;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import com.youlb.entity.common.Model;

/** 
 * @ClassName: AdpublishPicture.java 
 * @Description: 广告图片和物业首页图片 
 * @author: Pengjy
 * @date: 2016-3-10
 * 
 */
@Entity
@Table(name="t_adpublis_picture")
public class AdPublishPicture extends Model {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = -1314292868879213147L;
	 @Id
	 @GenericGenerator(name="uuid",strategy="uuid")
	 @GeneratedValue(generator="uuid")
	 @Column(length=32,name="id")
	 private String id;
	 /**t_adpublish表主键  关于小区id*/
	 @Column(name="fadpublishid")
	 private String adpublishId;
     /**文件保存服务器地址*/
     @Column(name="fserveraddr")
	 private String serverAddr;
     /**多媒体文件存储的相对路径*/
     @Column(name="frelativepath")
	 private String relativePath;
     /**图片显示位置*/
     @Column(name="fposition")
     public String position;
     @Transient
     private String message;
     /**设备类型*/
     @Transient
     private String targetDevice;
     
     
     public String getPositionStr() {
    	 String positionStr = "";
    	 if(StringUtils.isNotBlank(position)){
    		 if("1".equals(position)){
    			 positionStr="首页";
    		 }else if("2".equals(position)){
    			 positionStr="通话页";
    		 }
    	 }
 		return positionStr;
 	}
     
	public String getTargetDevice() {
		return targetDevice;
	}

	public void setTargetDevice(String targetDevice) {
		this.targetDevice = targetDevice;
	}

	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAdpublishId() {
		return adpublishId;
	}
	public void setAdpublishId(String adpublishId) {
		this.adpublishId = adpublishId;
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
