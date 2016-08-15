<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<body>
<script type="text/javascript">
  $(function(){
	  $("#updatePaswForm .password").on("change",function(){
	      var newPassword = $("#updatePaswForm .newPassword").val();
		  var password = $("#updatePaswForm .password")
		  if(password.length==newPassword.length){
			  if(!password==newPassword){
				  hiAlert("提示","密码输入不一致");
			  }
		  }
	  })
  })
</script>
<div>
	   <div>
		 <form id="updatePaswForm" action="">
		   <div>
			    <input type="hidden" name="id" value="${user.id}"/>
	            <table>
	              <tr>
	                <td><div class="leftFont"><span class="starColor">*</span>新密码：</div></td>
	                <td><div>
	                   <input type="password" name="newPassword" class="form-control updatePassword" title="新密码不能为空"/>
	                </div></td>
	              </tr>
	              <tr>
	                <td><div class="leftFont"><span class="starColor">*</span>确认新密码：</div></td>
	                <td><div><input type="password" name="password" class="form-control updatePassword" title="确认新密码不能为空"/></div></td>
	              </tr>
	           </table>
           </div>
           
         </form>
	   </div>
 </div>
</body>

