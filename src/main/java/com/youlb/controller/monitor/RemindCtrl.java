package com.youlb.controller.monitor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;
import com.youlb.utils.common.SysStatic;

@Controller
@Scope("prototype")
@RequestMapping("/remind")
public class RemindCtrl {
	private static Logger logger = LoggerFactory.getLogger(RemindCtrl.class);

	@Autowired
	private ServletContext servletContext;
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	/**
	 * 接口通知有警告消息
	 * @return
	 */
	@RequestMapping("/remind.do")
	@ResponseBody
	public String remind(String id,HttpServletRequest request,HttpServletResponse response,Model model){
		logger.info("ID："+id);
		
		if(StringUtils.isBlank(id)){
			logger.error("ID 不能为空");
			return "ID 不能为空";
		}
//		String host = request.getRemoteHost();
//		if(SysStatic.HTTP.indexOf(host)<1){
//			logger.error("非指定ip");
//			return "非指定ip";
//		}
		List<String> list = (List<String>) servletContext.getAttribute("realTimeMonitorIds");
		if(list==null){
			list = new ArrayList<String>();
			list.add(id);
		}else{
			list.add(id);
		}
		servletContext.setAttribute("realTimeMonitorIds", list);
		return null;
	}
	
}