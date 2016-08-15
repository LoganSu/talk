package com.youlb.job;

import java.util.ArrayList;

import javax.servlet.ServletContext;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
/**
* @ClassName: ClearLoockUserJob.java 
* @Description: 清除密码错误被锁的用户 
* @author: Pengjy
* @date: 2016年7月5日
*
 */
public class ClearLoockUserJob implements Job{
	@Override
	public void execute(JobExecutionContext context)throws JobExecutionException {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext.getServletContext();
		//清空被锁账号集合
		servletContext.setAttribute("loockList",new ArrayList<String>());
		 
		
	}

	 
}
