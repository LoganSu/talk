package com.youlb.entity.common;

import java.io.File;
import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
/**
 * 
 * @Title:上传文件的模型  
 * @Desription:上传文件的模型
 * @ClassName:FileModel.java
 * @Author:pengjy
 * @CreateDate:2013-6-7 下午5:28:15  
 * @Version:0.1
 */
public class FileModel implements Serializable{	
	
	private static final long serialVersionUID = -2741328464829371381L;
	private File file;
	private String fileFileName;	
	private String fileContentType;
	/**
	 * 获取文件对象
	 * @return
	 */
	public File getFile() {
		return file;
	}
	
	public void setFile(File file) {
		this.file = file;
	}
	/**
	 * 获取文件的名称
	 * @return
	 */
	public String getFileFileName() {
		//如果没有指定新的文件名，则用初始文件名
		if(file != null && StringUtils.isBlank(fileFileName)) {
			fileFileName = file.getName();
		}
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	/**
	 * 获取文件的contentType
	 * @return
	 */
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
}
