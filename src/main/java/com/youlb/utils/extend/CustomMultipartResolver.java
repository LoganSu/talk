package com.youlb.utils.extend;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.youlb.utils.exception.BizException;
import com.youlb.utils.listener.FileUploadProgressListener;

/**
 * 
 * 创建人：fantasy <br>
 * 创建时间：2013-12-6 <br>
 * 功能描述：文件上传Resolver <br>
 *
 */
public class CustomMultipartResolver extends CommonsMultipartResolver {
	
	private HttpServletRequest request;
    protected FileUpload newFileUpload(FileItemFactory fileItemFactory) {
    	ServletFileUpload upload = new ServletFileUpload(fileItemFactory);
    		upload.setSizeMax(-1);  
    		if (request != null) {
//    			try {
//    				List<FileItem> parseRequest = upload.parseRequest(request);
//    				//过滤multipart/form-data 表单参数
//    	    		String chars = "<,>,#,%,=,+,',\",;,),(";
//    	    		String[] characterParams= chars.split(",");
//    	    		for(FileItem item:parseRequest){
//    	    			String fieldName = item.getFieldName();
//    	    			String values = item.getString();
//    	    			for(String s:characterParams){
//    	    				if(values.contains(s)){
//    	    					throw new BizException("提交的相关表单数据字符含有非法字符!");
//    	    				}
//    	    				HttpSession session = request.getSession();
//    	    				session.setAttribute(fieldName, values);
//    	    			}
//    	    		}
//    				
//    			} catch (FileUploadException e) {
//    				e.printStackTrace();
//    			}
    			
    			HttpSession session = request.getSession();
    			FileUploadProgressListener progressListener = new FileUploadProgressListener(session);
    			upload.setProgressListener(progressListener);  
    		}  
//    	} 
        return upload;  
    }  
    
    public MultipartHttpServletRequest resolveMultipart(HttpServletRequest request) throws MultipartException {  
    	// 获取到request,要用到session  
        this.request = request;
        return super.resolveMultipart(request);  
    }  
	
	@Override
	public MultipartParsingResult parseRequest(HttpServletRequest request) throws MultipartException {
		HttpSession session = request.getSession(); 
		String encoding = determineEncoding(request);
		FileUpload fileUpload = prepareFileUpload(encoding);
		FileUploadProgressListener progressListener = new FileUploadProgressListener(session);
		fileUpload.setProgressListener(progressListener);  
		try {
			List<FileItem> fileItems = ((ServletFileUpload) fileUpload).parseRequest(request);
			return parseFileItems(fileItems, encoding);
		} catch (FileUploadBase.SizeLimitExceededException ex) {
			throw new MaxUploadSizeExceededException(fileUpload.getSizeMax(), ex);
		} catch (FileUploadException ex) {
			throw new MultipartException("Could not parse multipart servlet request", ex);
		}
	}
}