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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youlb.biz.monitor.IRealTimeMonitorBiz;
import com.youlb.entity.monitor.RealTimeMonitor;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.sms.SmsUtil;


@Controller
@RequestMapping("/remind")
public class RemindCtrl {
	private static Logger logger = LoggerFactory.getLogger(RemindCtrl.class);

	@Autowired
	private ServletContext servletContext;
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	@Autowired
    private IRealTimeMonitorBiz realTimeMonitorBiz;
	public void setRealTimeMonitorBiz(IRealTimeMonitorBiz realTimeMonitorBiz) {
		this.realTimeMonitorBiz = realTimeMonitorBiz;
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
		//发送短信
		try {
		    RealTimeMonitor realTimeMonitor = realTimeMonitorBiz.getWarnInfoById(id);
		    if(realTimeMonitor!=null){
		    	if(StringUtils.isNotBlank(realTimeMonitor.getWarnPhone())){
		    		SmsUtil.sendSMS(realTimeMonitor.getWarnPhone(), "你好！"+realTimeMonitor.getAddress()+"发生"+realTimeMonitor.getWarnType()+"告警。");
		    	}
		    }
			List<String> list = (List<String>) servletContext.getAttribute("realTimeMonitorIds");
			if(list==null){
				list = new ArrayList<String>();
				list.add(id);
			}else{
				list.add(id);
			}
			servletContext.setAttribute("realTimeMonitorIds", list);
		} catch (BizException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
