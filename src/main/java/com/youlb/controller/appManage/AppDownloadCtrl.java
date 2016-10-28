package com.youlb.controller.appManage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.youlb.biz.appManage.IAppManageBiz;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.entity.appManage.AppManage;
import com.youlb.utils.common.MatrixToImageWriter;
import com.youlb.utils.common.SysStatic;
import com.youlb.utils.exception.BizException;

/** 
 * @ClassName: AppDownloadCtrl.java 
 * @Description: app下载 
 * @author: Pengjy
 * @date: 2016-3-3
 * 
 */
@Controller
@Scope("prototype")
@RequestMapping("/appManage")
public class AppDownloadCtrl extends BaseCtrl {
	@Autowired
    private IAppManageBiz appManageBiz;
	public void setAppManageBiz(IAppManageBiz appManageBiz) {
		this.appManageBiz = appManageBiz;
	}
	 /**
     * 
     * @param fileName
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/singleDownPhone.do")    
    public ModelAndView download(HttpServletRequest request,HttpServletResponse response,String type) {
    	//获取项目根目录
//    	String rootPath = request.getSession().getServletContext().getRealPath("/");
    	String appType="";
    	if(StringUtils.isNotBlank(type)){
    		if("android".equals(type)){
    			appType="2";
    		}else if("doormachine".equals(type)){
    			appType="1";
    		}
    		BufferedInputStream bis = null;
    		BufferedOutputStream out = null;
    		try {
    		AppManage appManage = appManageBiz.lastVersion(appType);//拿手机最新版
    		String fileName=appManage.getVersionName()+".apk";
    		File file = new File(SysStatic.APPDIR.substring(0,SysStatic.APPDIR.indexOf("appDir")-1)+appManage.getRelativePath());
    		long fileLength = file.length();  
    			response = setFileDownloadHeader(request,response, fileName,fileLength);
    			bis = new BufferedInputStream(new FileInputStream(file));
    			out = new BufferedOutputStream(response.getOutputStream());
    			byte[] buff = new byte[3072];
    			int bytesRead;
    			while ((bytesRead = bis.read(buff, 0, buff.length))!=-1) {
    				out.write(buff, 0, bytesRead);
    			}
    		} catch (FileNotFoundException e1) {
    			e1.printStackTrace();
    		} catch (IOException e) {
    			e.printStackTrace();
    		} catch (BizException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
    			try {
    				if(bis != null){
    					bis.close();
    				}
    				if(out != null){
    					out.flush();
    					out.close();
    				}
    			}
    			catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}
		return null;
     
    }   
    
    @RequestMapping("/downAppPage.do") 
    public String downAppPage(HttpServletRequest request,HttpServletResponse response,Model model) {
		try {
			//拿手机最新版
			AppManage appManage = appManageBiz.lastVersion("2");
			model.addAttribute("appManage", appManage);
			//生成二维码
			//服务器地址
			String strBackUrl = "http://" +  SysStatic.FILEUPLOADIP           //端口号  
					+ request.getContextPath();
			String content = strBackUrl+"/appManage/singleDownPhone.do?type=android";
			String path = request.getSession().getServletContext().getRealPath("/");
			MatrixToImageWriter.createQRCode(content,path+"/imgs/","newDownloadApp");
		} catch (BizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return "/appManage/downAppPage";
    }
    
    
}
