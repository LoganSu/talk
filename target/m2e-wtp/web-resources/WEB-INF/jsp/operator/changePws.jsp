<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<style type="text/css">
    .container{
    margin-top: 200px;
    }
</style>
<script type="text/javascript">
   $(function(){
	   $("#changePwsForm .btn-primary").on("click",function(){
		   var url =$path+"/mc/user/changePws.do";
		    var password = $("#changePwsForm .password").val();
		    var newPassword = $("#changePwsForm .newPassword").val();
		    var surePassword = $("#changePwsForm .surePassword").val();
		    if(!password){
		    	hiAlert("提示","原始密码不能为空！");
				return false;
		    }
		    if(!newPassword||!surePassword){
		    	hiAlert("提示","新密码不能为空！");
				return false;
		    }
		    if(newPassword.length<5||surePassword.length<5){
		    	hiAlert("提示","新密码不能少于5个字符！");
				return false;
		    }
		    
		    if(newPassword!=surePassword){
		    	hiAlert("提示","新密码输入不一致！");
				return false;
		    }
		   $.post(url,$("#changePwsForm").serialize(),function($data){
			   if("ok"==$data){
				   //退出当前登录
// 				   hiAlert("提示","修改密码成功，请重新登录！");
				   alert("修改密码成功，请重新登录！");
				   window.location.replace($path+"/mc/user/loginOut.do");
			   }else{
			       hiAlert("提示",$data);
			   }
		   });
	   })
   })
 
</script>
<body>
   <div class="container">
     <div class="row">
         <div class="col-lg-3"></div>
         <div class="col-lg-6">
<%-- 		      <form id="changePwsForm" action="${path}/mc/user/changePws.do" target="changePwsFormFrame"> --%>
              <form id="changePwsForm" action="">
			      <table>
			         <tr>
				         <td><div class="leftFont"><span class="starColor">*</span>原始密码：</div></td>
				         <td><div><input type="password" name="password" class="form-control password"/></div></td>
			         </tr>
			         <tr>
				         <td><div class="leftFont"><span class="starColor">*</span>新密码：</div></td>
				         <td><div><input type="password" name="newPassword" class="form-control newPassword"/></div></td>
			         </tr>
			         <tr>
				         <td><div class="leftFont"><span class="starColor">*</span>确认新密码：</div></td>
				         <td><div><input type="password" name="surePassword" class="form-control surePassword"/></div></td>
			         </tr>
			         <tr>
				         <td><div class="leftFont"></div></td>
				         <td><div><input type="button" class="btn btn-primary btn-sm" value="确定"/></div></td>
			         </tr>
			      </table>
		      </form>
       </div>
       <div class="col-lg-3"></div>
   </div>
 </div>
</body>
</html>