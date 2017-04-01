<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
   <script src="${path}/js/common/jquery-1.12.3.js"></script>
     <link href="${path}/css/common/bootstrap/bootstrap.css" rel="stylesheet">
     <link href="${path}/css/common/bootstrap/bootstrap-table.css" rel="stylesheet">
         <script src="${path}/js/common/bootstrap/bootstrap-table-all.js"></script>
    <script src="${path}/js/common/bootstrap/bootstrap-table-locale-all.js"></script>
    <script src="${path}/js/common/bootstrap/bootstrap-table-zh-CN.min.js"></script>
</head>
<script type="text/javascript">
   function a(b){
	  var message= document.getElementById("message").innerHTML;
	  //情空message内容
// 	  var status= document.getElementById("status").innerHTML;
	  if(message){
        window.parent.window.hiAlert("提示",message);
	  }else{
        window.parent.window.refresh();
		window.parent.window.hideModal("unnormalModal");
	  }
	  
   }
</script>
<body onLoad="a(this)">
<%--     <div id="status">${status}</div> --%>
    <div id="message">${message}</div>
</body>
</html>