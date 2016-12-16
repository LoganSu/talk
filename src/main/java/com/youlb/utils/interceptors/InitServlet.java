package com.youlb.utils.interceptors;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.lang.StringUtils;
import org.quartz.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.youlb.biz.staticParam.IStaticParamBiz;
import com.youlb.entity.staticParam.StaticParam;
import com.youlb.utils.common.DES3;
import com.youlb.utils.common.QuartzService;
import com.youlb.utils.common.SysStatic;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.helper.DateHelper;




/**
 * 
 * @Title:初始化 
 * @Desription:web系统初始化，初始化前台系统所需的常量，放入Application中
 * @ClassName:InitServlet.java
 * @CreateDate:2013-6-7 下午5:57:06  
 * @Version:0.1
 */
public class InitServlet extends HttpServlet {
	private static Logger logger = LoggerFactory.getLogger(InitServlet.class);

	/**
	 * 系统初始化参数
	 * @param config 配置对象
	 */
	private static void initSysParam(ServletConfig config) throws BizException{
		ServletContext context = config.getServletContext();
		//web根路径
		String path = context.getContextPath();
		SysStatic.PATH=path;
		context.setAttribute("path",path);
		String ic_cardKey =  "{\"key\":\"ffffffffffffffffffffffffffffffff\"}";
		context.setAttribute("ic_cardKey",ic_cardKey);
		//初始化被锁用户名单容器
		context.setAttribute("loockList", new ArrayList<String>());
		//初始化发送短信超过指定次数集合用户
		context.setAttribute("exceedSendSmsMap", new HashMap<String,Map<String,Object>>());
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		ServletContext context = config.getServletContext();
		ApplicationContext app = WebApplicationContextUtils.getWebApplicationContext(context);
		IStaticParamBiz staticParamBiz = (IStaticParamBiz) app.getBean("staticParamBiz");
		try{
			initSysParam(config);
		}catch(BizException e){
			logger.error("初始化参数出错");
		}
		try{
		    initSysProperties(config);
		}catch(BizException | IOException e){
			logger.error("初始化配置文件出错");
			e.printStackTrace();
		}
		try{
			StaticParam paramByKey = staticParamBiz.getParamByKey("fileUploadIp");
			if(paramByKey!=null){
				SysStatic.FILEUPLOADIP = paramByKey.getFvalue();
			}
		}catch(BizException e){
			logger.error("初始化文件上传地址");
			e.printStackTrace();
		}
		try {
			initQuartzService(config);
		} catch (ClassNotFoundException e) {
			logger.error("初始定时器出错");
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("初始定时器出错");
			e.printStackTrace();
		}
		
 	}
	
	private static void initQuartzService(ServletConfig config) throws IOException, ClassNotFoundException{
		// 定时器任务
		QuartzService.start();
		// 加载任务配置文件
		String path = InitServlet.class.getClassLoader().getResource("").getPath(); 
		Properties proper=new Properties();
		proper.load(new FileInputStream(path+"quart.properties"));
		// 遍历配置文件中的信息：key：时间；value：类文件全路径
		Enumeration en = proper.propertyNames();
		while (en.hasMoreElements()) {
			String key = (String) en.nextElement();
			String classPath = proper.getProperty(key);
			QuartzService.delayedRunJob(key, (Class<? extends Job>) Class.forName(classPath));
		}
	}
	/**
	 * 读取配置文件信息
	 * @param config
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static void initSysProperties(ServletConfig config) throws FileNotFoundException, IOException, BizException{
		String path = InitServlet.class.getClassLoader().getResource("").getPath(); 
		Properties proper=new Properties();
		proper.load(new FileInputStream(path+"config.properties"));
		//接口服务地址
		String http = (String) proper.get("YLBServer.http");
		SysStatic.HTTP=http;
		logger.info("接口服务地址YLBServer.http::"+http);
		//3des密码
		String key = (String) proper.get("key");
		SysStatic.KEYBYTES=DES3.hexStringToBytes(key);
		logger.info("加载私钥");
		//爱社区3des秘钥
		String asqkey = (String) proper.get("asqkey");
		SysStatic.ASQKEYBYTES=DES3.hexStringToBytes(asqkey);
		logger.info("加载爱社区3des私钥");
		//爱社区秘钥
		String asqpsw = (String) proper.get("asqpsw");
		SysStatic.ASQPSW=asqpsw;
		logger.info("加载爱社区私钥");
		//爱社区ip
		String asqips = (String) proper.get("asqips");
		List<String> ips = new ArrayList<String>();
		if(StringUtils.isNotBlank(asqips)){
			if(asqips.contains(",")){
				String[] asqipsArr = asqips.split(",");
				for(String ip:asqipsArr){
					ips.add(ip);
				}
				SysStatic.ASQIPSLIST=ips;
				logger.info("加载爱社区ip地址");
			}else{
				ips.add(asqips);
			}
		}else{
			logger.error("请配置爱社区ip地址");
		}
		
		//web版本
		String version = (String) proper.get("version");
		if(StringUtils.isBlank(version)){
			version = DateHelper.dateFormat(new Date(), "yy.MM.dd.HH");
		}
		
		SysStatic.VERSION=version;
		logger.info("发布web version::"+version);
		//七牛备份地址
		String qiniubackup = (String) proper.get("qiniubackup.dir");
		SysStatic.QINIUBACKUP=qiniubackup;
		logger.info("七牛备份地址 qiniubackup::"+qiniubackup);
		//七牛备份地址
		String firstServer = (String) proper.get("firstServer.http");
		SysStatic.FIRSTSERVER=firstServer;
		logger.info("一级平台地址firstServer::"+firstServer);
		//平台级别
		String platformLevel = (String) proper.get("platform.level");
		SysStatic.PLATFORMLEVEL=platformLevel;
		logger.info("平台级别::"+platformLevel);
		
		//特殊字符
		String specialString = (String) proper.get("special.string");
		if(StringUtils.isNotBlank(specialString)){
			String[] arr = specialString.split(",");
			SysStatic.SPECIALSTRING=arr;
			logger.info("过滤的特殊字符::"+specialString);
		}else{
			logger.error("没有过滤特殊字符");
		}
	}

	 
	
}
