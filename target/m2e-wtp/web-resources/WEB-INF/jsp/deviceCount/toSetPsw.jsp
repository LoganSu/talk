<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<body>
<script type="text/javascript">
$(function(){
	//修改密码确认
	$("#deviceCountSetPwsDiv .sure").click(function(){
		//密码验证
		var paswArr = $("#deviceCountSetPwsForm .updatePassword");
		if($(paswArr[0]).val()&&$(paswArr[1]).val()){
			if(!$(paswArr[0]).val().match("^[0-9]+(.[0-9])?$")||$(paswArr[0]).val().length!=8){
				hiAlert("提示","密码必须是8位数字！");
				return false;
			}
			if($(paswArr[0]).val()!=$(paswArr[1]).val()){
				hiAlert("提示","密码输入不一致！");
				return false;
			}
			var data = $("#deviceCountSetPwsForm").serialize();
			var url =$path+"/mc/deviceCount/setPsw.do";
			$.post(url,data,function($data){
				if(!$data){
					$("#unnormalModal").modal("hide");
				}else{
					hiAlert("提示",$data);
				}
			})
		}else{
			hiAlert("提示","请输入密码！");
			return false;
		}
	})
	
})
</script>
<div>
	   <div id="deviceCountSetPwsDiv">
		 <form id="deviceCountSetPwsForm" action="">
		   <div>
			    <input type="hidden" name="id" value="${deviceCount.id}"/>
	            <table>
	              <tr>
	                <td><div class="leftFont"><span class="starColor">*</span>密码：</div></td>
	                <td><div>
	                   <input type="password" name="" class="form-control updatePassword" maxlength="8" title="密码必须是8位数字"/>
	                </div></td>
	              </tr>
	              <tr>
	                <td><div class="leftFont"><span class="starColor">*</span>确认密码：</div></td>
	                <td><div><input type="password" name="countPsw" class="form-control updatePassword" maxlength="8" title="密码必须是8位数字"/></div></td>
	              </tr>
	           </table>
           </div>
           
         </form>
	     <div class="modal-footer">
	         <!--操作按钮 -->
	        <button type="button" class="btn btn-primary sure">确定</button>
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	     </div>
	</div>
 </div>
</body>

