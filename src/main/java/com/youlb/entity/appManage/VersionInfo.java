package com.youlb.entity.appManage;

import java.util.List;

/**
 * 
* @ClassName: VersionInfo.java 
* @Description:接口数据返回对象
* @author: Pengjy
* @date: 2016年3月31日
*
 */
public class VersionInfo {
   private String version_name;
   private String version_code;
   private String url;
   private String desc;
   private Long size;
   private String md5_code;
   private Boolean auto_instal;
   private String targetDevive;
   private List<String> tagList;
	 
	
	public List<String> getTagList() {
		return tagList;
	}
	public void setTagList(List<String> tagList) {
		this.tagList = tagList;
	}
  	public String getTargetDevive() {
  		return targetDevive;
  	}
  	public void setTargetDevive(String targetDevive) {
  		this.targetDevive = targetDevive;
  	}
	public String getVersion_name() {
		return version_name;
	}
	public void setVersion_name(String version_name) {
		this.version_name = version_name;
	}
	public String getVersion_code() {
		return version_code;
	}
	public void setVersion_code(String version_code) {
		this.version_code = version_code;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	public String getMd5_code() {
		return md5_code;
	}
	public void setMd5_code(String md5_code) {
		this.md5_code = md5_code;
	}
	public Boolean getAuto_instal() {
		return auto_instal;
	}
	public void setAuto_instal(Boolean auto_instal) {
		this.auto_instal = auto_instal;
	}
	 
	  
  
}
