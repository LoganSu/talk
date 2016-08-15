package com.youlb.utils.common;

import org.quartz.CronScheduleBuilder;
import org.quartz.DateBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Quartz定时器服务
 * @author garyxin on 2016年3月31日.
 */
//Cron表达式范例：
//每隔5秒执行一次：*/5 * * * * ?
//每隔1分钟执行一次：0 */1 * * * ?
//每天23点执行一次：0 0 23 * * ?
//每天的0点执行一次：0 0 0 * * ?
//每天凌晨1点执行一次：0 0 1 * * ?
//每月1号凌晨1点执行一次：0 0 1 1 * ?
//每月最后一天23点执行一次：0 0 23 L * ?
//每周星期天凌晨1点实行一次：0 0 1 ? * L
//在26分、29分、33分执行一次：0 26,29,33 * * * ?
//每天的0点、13点、18点、21点都执行一次：0 0 0,13,18,21 * * ?
public class QuartzService {
	
	static Scheduler scheduler;

	public static boolean start() {
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();
			return true;
		} catch (SchedulerException se) {
            se.printStackTrace();
        }
		return false;
	}
	
	/**
	 * 立刻运行一个定时任务
	 * @param cronExpression	CRON表达式，运行时间
	 * @param job	要运行的定时任务
	 * @return	是否运行成功
	 */
	public static boolean immediateRunJob(String cronExpression, Class<? extends Job> job) {
		try {
			if (scheduler != null && scheduler.isStarted()) {
				JobDetail jobDetail = JobBuilder.newJob(job)
			            	.withIdentity(job.getSimpleName(), job.getPackage().getName())
			                .build();
				
				Trigger trigger = TriggerBuilder.newTrigger()
		                 .withIdentity(job.getSimpleName(), job.getPackage().getName())
		                 .startNow()
		                 .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))            
		                 .build();
				
				scheduler.scheduleJob(jobDetail, trigger);
				return true;
			}
		} catch (SchedulerException se) {
			se.printStackTrace();
		}
		return  false;
	}
	
	/**
	 * 延时运行一个定时任务
	 * @param cronExpression	CRON表达式，运行时间
	 * @param job	要运行的定时任务
	 * @return	是否运行成功
	 */
	public static boolean delayedRunJob(String cronExpression, Class<? extends Job> job) {
		try {
			if (scheduler != null && scheduler.isStarted()) {
				JobDetail jobDetail = JobBuilder.newJob(job)
			            	.withIdentity(job.getSimpleName(), job.getPackage().getName())
			                .build();
				
				CronScheduleBuilder csb = CronScheduleBuilder.cronSchedule(cronExpression);
				Trigger trigger = TriggerBuilder.newTrigger()
		                 .withIdentity(job.getSimpleName(), job.getPackage().getName())
		                 .startAt(csb.build().getFireTimeAfter(DateBuilder.newDate().build()))
		                 .withSchedule(csb)            
		                 .build();
				
				scheduler.scheduleJob(jobDetail, trigger);
				return true;
			}
		} catch (SchedulerException se) {
			se.printStackTrace();
		}
		return  false;
	}
	
	public static void stop(){
		try {
			scheduler.shutdown(true);
		} catch (SchedulerException se) {
			se.printStackTrace();
		}
	}
}
