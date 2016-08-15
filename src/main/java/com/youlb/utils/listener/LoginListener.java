package com.youlb.utils.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.youlb.biz.privilege.IOperatorBiz;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.common.SysStatic;


/** 
 * @ClassName: loginListener.java 
 * @Description: session监听器 
 * @author: Pengjy
 * @date: 2015年7月6日
 * 
 */
public class LoginListener implements ServletContextListener,HttpSessionListener {
	private ServletContext application;
	private WebApplicationContext context;
	
	/**
	 * @param sce
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//获取作用域
		application = sce.getServletContext();
		//从作用域中获取spring对象
		context = WebApplicationContextUtils.getWebApplicationContext(application);
		
	}

	/**
	 * @param sce
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * @param se
	 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
	 */
	@Override
	public void sessionCreated(HttpSessionEvent se) {
//		BaseDaoBySql<ManageUser> userSqlDao =  (BaseDaoBySql<ManageUser>) context.getBean("userSqlDao");
//	System.out.println(userSqlDao);
		
	}

	/**session失效执行方法
	 * @param se
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		HttpSession session =arg0.getSession();
		Operator login = (Operator) session.getAttribute(SysStatic.LOGINUSER);
		IOperatorBiz operatorBiz = (IOperatorBiz) context.getBean("operatorBiz");
		operatorBiz.loginOut(login);//退出登入
		
	}

	 
}
