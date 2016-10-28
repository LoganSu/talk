package com.youlb.controller.appManage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

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
import com.youlb.controller.infoPublish.TodayNewsCtrl;
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
    
    @RequestMapping("/downAppPage.do") 
    public String downAppPage(HttpServletRequest request,HttpServletResponse response,Model model) {
		try {
			//拿手机最新版
			AppManage appManage = appManageBiz.lastVersion("2");
			model.addAttribute("appManage", appManage);
			//s生成下载二维码
//			String strBackUrl = appManage.getServerAddr()+appManage.getRelativePath();
//			String path = request.getSession().getServletContext().getRealPath("/");
//			MatrixToImageWriter.createQRCode(strBackUrl,path+"/imgs/","newDownloadApp");
		} catch (BizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return "/appManage/downAppPage";
    }
    
    
}
