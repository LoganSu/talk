package com.youlb.controller.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.youlb.utils.common.SysStatic;


/** 
 * @ClassName: LoginCtrl.java 
 * @Description: 登录 
 * @author: Pengjy
 * @date: 2015年9月10日
 * 
 */
@Controller
@RequestMapping("")
@Scope("prototype")
public class LoginCtrl extends BaseCtrl {
	/**
     * 登录页面跳转
     * @return
     */
    @RequestMapping("/*/login.do")
	public String index(HttpSession httpSession,Model model){
    	//获取请求对象
    	HttpServletRequest request = getRequest();
    	  String fromURL = request.getHeader("Referer");
    	System.out.println(fromURL);
//    	Operator user = new Operator();
//    	Carrier carrier = new Carrier();
    	//获取请求uri
    	String uri = request.getRequestURI();
    	//获取中间字符串（运营商代码）
    	String carrierNum = uri.substring(uri.indexOf("/")+1, uri.lastIndexOf("/"));
//    	carrier.setCarrierNum(carrierNum);
//    	user.setCarrier(carrier);
    	//判断是不是登录失败返回到登录页面，直接页面刷新跳转不显示错误信息
    	String from_redirect = (String) httpSession.getAttribute("from_redirect");
    	if(StringUtils.isNotBlank(from_redirect)){
    	    httpSession.removeAttribute("from_redirect");
    	}else{
    		httpSession.removeAttribute("errorMessg");
    	}
//    	httpSession.setAttribute("password", "");
//    	httpSession.setAttribute("verificationCode", "");
    	model.addAttribute("carrierNum", carrierNum);
    	model.addAttribute("version", SysStatic.VERSION);
		return "/login";
	}
}
