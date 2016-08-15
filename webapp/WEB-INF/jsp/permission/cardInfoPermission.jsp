<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
      <table>   
            <tr>
               <td><div style="border: 1px solid #6C6C6C;width: 400px;height: 200px;padding-left: 10PX;overflow: auto;">
                  <c:forEach var="address" items="${addressList}">
                     <div><input name="domainSns" type="checkbox" checked="checked" value="${address.domainSn}"/>&nbsp;&nbsp;<span>${address.address}</span></div>
                  </c:forEach>
               </div></td>
            </tr>
         </table>  
   <div class="modal-footer">
	         <!--操作按钮 -->
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	 </div>
</body>
</html>