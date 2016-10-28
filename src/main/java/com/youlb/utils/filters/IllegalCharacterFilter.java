package com.youlb.utils.filters;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class IllegalCharacterFilter implements Filter {
	
	  private String[] characterParams = null;
	  private boolean OK=true;
	

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		 if(filterConfig.getInitParameter("characterParams").length()<1){   
			 OK=false;
		 }
		 else{   
			 this.characterParams = filterConfig.getInitParameter("characterParams").split(",");
		 }
		
	}
	 
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		  HttpServletRequest servletrequest = (HttpServletRequest) request;   
		  String requestURI = servletrequest.getRequestURI();
		  HttpServletResponse servletresponse = (HttpServletResponse) response;    
		  boolean status = false;      
		  Enumeration params = servletrequest.getParameterNames();
//		  boolean isMultipart = ServletFileUpload.isMultipartContent(servletrequest);
//		  if(isMultipart){
//			//为该请求创建一个DiskFileItemFactory对象，通过它来解析请求
//              FileItemFactory factory = new DiskFileItemFactory();
//              ServletFileUpload upload = new ServletFileUpload(factory);
//              upload.setFileItemFactory(factory);
//               //将所有的表单项目都保存到List中
//              try {
//				List<FileItem> items = upload.parseRequest(servletrequest);
//				for(FileItem item:items){
//					String fieldName = item.getFieldName();
////					params.put(item.getFieldName(), item.getString("utf-8"));
//					if(!fieldName.contains("file")){
//						String values = item.getString();
//						for(int i=0;i<characterParams.length;i++){
//							if (values.indexOf(characterParams[i]) >= 0) {
//								status = true;       
//								break;      
//							}     
//						} 
//						if(status){
//							break; 
//						}
//					}
//				}
//			} catch (FileUploadException e) {
//				e.printStackTrace();
//			}
//		  }
//		  System.out.println(isMultipart);
//		  System.out.println("=================请求方式："+servletrequest.getMethod());
//		  String param="";   
		  servletresponse.setContentType("text/html");   
		  servletresponse.setCharacterEncoding("utf-8");    
		  while (params.hasMoreElements()) { 
			  String param = (String) params.nextElement();
			  //编辑器字段跳过 里面有html代码 
			  if(param!=null&&param.endsWith("Editor")){
				  continue;
			  }
			  String  paramValue="";
			  String[] values = request.getParameterValues(param);
			  if(OK){
				  //过滤字符串为0个时 不对字符过滤    
				  for (int i = 0; i < values.length; i++){
					  paramValue=paramValue+values[i];
				  }
				  for(int i=0;i<characterParams.length;i++){
					  if (paramValue.indexOf(characterParams[i]) >= 0) {
						  status = true;       
						  break;      
						  }     
				  } 
				  if(status){
					  break; 
				  }
				}  
			  }
		  ParameterRequestWrapper requestWrapper = new ParameterRequestWrapper((HttpServletRequest)request); 
		     if (status&&!"/mc/user/loginForm.do".equals(requestURI)) {//登入方法不过滤
		    	 PrintWriter out = servletresponse.getWriter();     
		         out.println("<script>");//输出script标签
		         out.println("hiAlert('警告','您提交的相关表单数据字符含有非法字符!');");//js语句：输出alert语句
//		         out.println("history.back();");//js语句：输出网页回退语句
		         out.println("</script>");//输出script结尾标签
		        }else{ 
		        	chain.doFilter(requestWrapper, response);    
		        }
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	

	 

}
