package com.youlb.utils.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.common.SysStatic;


/** 
 * @ClassName: LoginFilter.java 
 * @Description: TODO 
 * @author: Pengjy
 * @date: 2015年7月3日
 * 
 */
public class LoginFilter implements Filter {

	/**
	 * @param filterConfig
	 * @throws ServletException
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	/**
	 * @param request
	 * @param response
	 * @param chain
	 * @throws IOException
	 * @throws ServletException
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		//获取项目根路径
		String contextPath = httpRequest.getContextPath();
		//判断请求路径
		String requestURI = httpRequest.getRequestURI();
		//放行登录请求
		 if(requestURI.equals(contextPath+"/mc/user/loginForm.do")||requestURI.equals(contextPath+"/mc/user/hideLogin.do")||requestURI.equals(contextPath+"/mc/user/getVerificationCode.do")){
			 chain.doFilter(request, response);
			 return;
		 }
		//获取客户的请求IP地址
//		String remoteAddr =  request.getRemoteAddr();
		Operator user = (Operator) httpRequest.getSession().getAttribute(SysStatic.LOGINUSER);
		//不是登录用户 跳转到登入页面
		String carrierNum = requestURI.substring(requestURI.indexOf("/")+1, requestURI.lastIndexOf("/"));
		if(user==null){
			//session失效时候到这里
			if(carrierNum.contains("/")){
				httpRequest.getSession().setAttribute("errorMessg", "登录超时，请重新登录！");
				httpResponse.sendRedirect(contextPath+"/error.jsp");
			}else{
				httpResponse.sendRedirect(contextPath+"/"+carrierNum+"/login.do");
			}
		}else{
			chain.doFilter(request, response);
		}

	}

	/**
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

}
