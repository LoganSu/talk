<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<script type="text/javascript">
   function a(b){
	  var message= document.getElementById("message").innerHTML;
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