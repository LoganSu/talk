<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<body>
<script type="text/javascript">
$(function(){
	//修改密码确认
	$("#deviceInfoSetPwsDiv .sure").click(function(){
		//密码验证
		var paswArr = $("#deviceInfoSetPwsForm .updatePassword");
		if($(paswArr[0]).val()&&$(paswArr[1]).val()){
			if($(paswArr[0]).val()!=$(paswArr[1]).val()){
				hiAlert("提示","密码输入不一致！");
				return false;
			}
			var data = $("#deviceInfoSetPwsForm").serialize();
			var url =$path+"/mc/device/deviceInfoSetPws.do";
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
	   <div id="deviceInfoSetPwsDiv">
		 <form id="deviceInfoSetPwsForm" action="">
		   <div>
			    <input type="hidden" name="id" value="${device.id}"/>
	            <table>
	              <tr>
	                <td><div class="leftFont"><span class="starColor">*</span>密码：</div></td>
	                <td><div>
	                   <input type="password" name="" class="form-control updatePassword" title="新密码不能为空"/>
	                </div></td>
	              </tr>
	              <tr>
	                <td><div class="leftFont"><span class="starColor">*</span>确认密码：</div></td>
	                <td><div><input type="password" name="devicePws" class="form-control updatePassword" title="确认新密码不能为空"/></div></td>
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

