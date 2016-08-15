package com.youlb.job;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletContext;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
/**
 * 
* @ClassName: ClearExceedSendSmsMap.java 
* @Description: 清理超限发送验证码的用户
* @author: Pengjy
* @date: 2016年7月5日
*
 */
public class ClearExceedSendSmsMapJob implements Job {

	@Override
	public void execute(JobExecutionContext context)throws JobExecutionException {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext.getServletContext();
		Map<String,Map<String,Object>> map = (Map<String, Map<String, Object>>) servletContext.getAttribute("exceedSendSmsMap");
		for(String key:map.keySet()){
			Map<String,Object> subMap = map.get(key);
			Integer count = (Integer) subMap.get("count");
			Date date = (Date) subMap.get("time");
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.HOUR_OF_DAY, 1);//一小时之后恢复
			if(c.getTimeInMillis()>date.getTime()&&count>5){
				map.remove(key);
			}
		}
	}

}
