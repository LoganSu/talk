<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
   <form id="disposeEventForm" action="">
      <input type="hidden" name="id" value="${realTimeMonitor.id}"/>
      <input type="hidden" class="longitude" value="${realTimeMonitor.longitude}"/>
      <input type="hidden" class="latitude" value="${realTimeMonitor.latitude}"/>
      <textarea maxlength="200" style="width: 800px;height: 130px" name="remark" rows="" cols="" title="内容不能为空！" class="form-control required">${realTimeMonitor.remark}</textarea>  
      <div class="modal-footer">
           <!--操作按钮 -->
           <c:if test="${realTimeMonitor.status eq '0'}">
             <input type="button" class="btn btn-primary sure" value="确定"/> 
           </c:if>
           <input type="button" class="btn btn-default" data-dismiss="modal" value="关闭"/> 
	  </div> 
   </form>
</body>
</html>