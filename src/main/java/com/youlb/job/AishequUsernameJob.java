package com.youlb.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class AishequUsernameJob implements Job{

	@Override
	public void execute(JobExecutionContext context)throws JobExecutionException {
		 //同步用户账号 用户名以asq_开头
	}

}
