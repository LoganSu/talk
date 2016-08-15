package com.youlb.utils.listener;

import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;

import com.youlb.controller.appManage.Progress;

/**
 * 
 * 创建人：fantasy <br>
 * 创建时间：2013-12-6 <br>
 * 功能描述： 文件上传进度<br>
 */
public class FileUploadProgressListener implements ProgressListener {
	
	private HttpSession session;

	public FileUploadProgressListener() {  }  
	
    public FileUploadProgressListener(HttpSession session) {
        this.session=session;
//        System.out.println(session.getId());
        Progress status = new Progress();
        session.setAttribute("upload_ps", status);  
    }  
	
	/**
	 * pBytesRead 到目前为止读取文件的比特数 pContentLength 文件总大小 pItems 目前正在读取第几个文件
	 */
	public void update(long pBytesRead, long pContentLength, int pItems) {
		Progress status = (Progress) session.getAttribute("upload_ps");
		status.itemNum = pItems;  
		status.readSize = pBytesRead;  
		status.totalSize = pContentLength;  
		status.show = pBytesRead+"/"+pContentLength+" byte";  
		status.rate = Math.round(new Float(pBytesRead) / new Float(pContentLength)*100);
		if(pBytesRead==pContentLength){
//			System.out.println(2222);
		}
		session.setAttribute("upload_ps", status);
	}
}
