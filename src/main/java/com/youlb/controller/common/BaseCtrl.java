package com.youlb.controller.common;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.youlb.entity.common.Model;
import com.youlb.entity.common.Pager;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.common.SysStatic;
/**
 * 
* @ClassName: BaseCtrl 
* @Description: controller基类 
* @author: Pengjy
* @date: 2015年6月3日
*
 */
public abstract class BaseCtrl extends Observable{
	public static final String SUCCESS = "success";
    public static final String NONE = "none";
    public static final String ERROR = "error";
    public static final String INPUT = "/common/input";
    public static final String SHOWLIST = "/common/showList";
    /**返回到页面的z状态*/
    protected String status;
    
    /**返回到页面的提示信息*/
    protected String message;
    
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	/**显示登录页*/
    public static final String LOGIN = "redirect:/login.jsp";
    /**显示首页*/
	public static final String INDEX = "redirect:/mc/user/index.do";
	
	public  HttpSession getSession() {
		HttpSession session = null;
		try {
			session = getRequest().getSession();
		} catch (Exception e) {
		}
		return session;
	}

	public  HttpServletRequest getRequest() {
		ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		return attrs.getRequest();
	}
	/**
	 * 公共显示右边页面
	 * @return
	 */
	@RequestMapping("/*showPage.do")
	public String showPage(org.springframework.ui.Model model){
		HttpServletRequest request = getRequest();
		Enumeration parameterNames = request.getParameterNames();
		while(parameterNames.hasMoreElements()){
			String parameterName = (String) parameterNames.nextElement();
			System.out.println("showPage:"+parameterName+"=="+request.getParameter(parameterName));
			model.addAttribute(parameterName, request.getParameter(parameterName));
		}
		//获取controller配置的url
//		String servletPath = request.getServletPath();
//		model.addAttribute("controllerMapPath", servletPath.substring(0, servletPath.lastIndexOf("/")));
		return SHOWLIST;
	}
	/**
	 * 加载search.jsp页面
	 * @return
	 */
	@RequestMapping("*/search.do")
	public String search(String modulePath,org.springframework.ui.Model model){
		HttpServletRequest request = getRequest();
		Enumeration parameterNames = request.getParameterNames();
		while(parameterNames.hasMoreElements()){
			String parameterName = (String) parameterNames.nextElement();
			System.out.println("search:"+parameterName+"=="+request.getParameter(parameterName));
			model.addAttribute(parameterName, request.getParameter(parameterName));
		}
		return modulePath+"/search";
	}
	/**
	 * 封装结果集
	 * @param rows
	 * @return
	 */
	public Map<String, Object> setRows(List<? extends Model> rows){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(rows != null && !rows.isEmpty()){
			Pager pager = rows.get(0).getPager();
			resultMap.put("rows", rows); //设置结果集
			if(pager != null) {
				//设置序号
				int start=pager.getStartRow()+1;
				for (Model item : rows) {
					item.setIndex(start);
					start++;
				}
				
				int totalRows = pager.getTotalRows();
				resultMap.put("total", totalRows); //设置总记录数
			}
	    //设置空对象
		}else{
			resultMap.put("rows", new ArrayList<Model>());
		}
		return resultMap;
	}
	
	/**
	 * 封装结果集
	 * @param rows
	 * @return
	 
	public Map<String, Object> setRows(List rows, Pager pager){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(rows != null && !rows.isEmpty()){
			resultMap.put("rows", rows); //设置结果集
			if(pager != null) {
				//设置序号
				
				int totalRows = pager.getTotalRows();
				resultMap.put("total", totalRows); //设置总记录数
			}
		}
		return resultMap;
	}
	*/
	/**
	 * 获取当前登录用户
	 * @return
	 */
	public Operator getLoginUser(){
		try{
		Operator operator = (Operator) getSession().getAttribute(SysStatic.LOGINUSER);
		return operator;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
