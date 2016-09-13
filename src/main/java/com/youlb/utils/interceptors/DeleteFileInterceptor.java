package com.youlb.utils.interceptors;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class DeleteFileInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
//		 System.out.println("preHandlepreHandlepreHandlepreHandlepreHandlepreHandle");
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception {
//		 System.out.println("postHandlepostHandlepostHandlepostHandlepostHandle");
		//删除临时文件
		 Map<String, Object> model = modelAndView.getModel();
		 File file =  (File) model.get("file");
		 file.delete();
		 modelAndView.clear();
//		 System.out.println(model);
//		 modelAndView=null;

	}

	@Override
	public void afterCompletion(HttpServletRequest request,HttpServletResponse response, Object handler, Exception ex)throws Exception {
	}

}
