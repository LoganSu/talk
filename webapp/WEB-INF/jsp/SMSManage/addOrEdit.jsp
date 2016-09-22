<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<body>
<script type="text/javascript">
$(function(){
	  $("#SMSManagesaveForm .btn-primary").on('click',function(){
		  $("#SMSManagesaveForm").submit();
	  })
// 	  $("#SMSManagesaveForm .SMSManageModel").on('click',function(){
// 		  alert(3333);
// 	  })
})

</script>
<div>
	   <div>
		 <form id="SMSManagesaveForm" action="${path}/mc/SMSManage/saveOrUpdate.do" target="SMSManageSubmitFrame" enctype="multipart/form-data" method="post">
		   <input type="hidden" name="id" value="${SMSManage.id}"/>
           <table>
              <tr>
                <td><div class="firstFont"><span class="starColor">*</span>服务器IP：</div></td>
                <td><div><input name="ip" class="form-control required" title="请填写正确的ip地址"  maxlength="100" value="${SMSManage.ip}"/></div></td>
                <td><div class="leftFont"><span class="starColor">*</span>端口：</div></td>
                <td><div><input name="port" class="form-control required" title="端口不能为空"  value="${SMSManage.port}"/></div></td>
              </tr>
              <tr>
                <td><div class="firstFont"><span class="starColor">*</span>用户名：</div></td>
                <td><div><input name="username" class="form-control required" title="用户名不能为空" maxlength="100" value="${SMSManage.username}"/></div></td>
                <td><div class="leftFont"><span class="starColor">*</span>密码：</div></td>
                <td><div><input name="pwd" type="password" class="form-control required" title="密码不能为空" maxlength="100" value="${SMSManage.pwd}"/></div></td>
              </tr>
              <tr>
                <td><div class="firstFont"><span class="starColor">*</span>公司签名：</div></td>
                <td><div><input name="sign" class="form-control required" title="公司签名不能为空" maxlength="200" value="${SMSManage.sign}"/></div></td>
              </tr>
              <tr>
                <td><div class="firstFont">导入白名单：</div></td>
                <td colspan="2"><div><input style="width: 200px" type="file" name="SMSManageWhiteFile" class="form-control"/></div></td>
                <td><div><a href="${path}/mc/SMSManage/singleDownPhone.do"><input class="btn btn-success btn-sm SMSManageModel" type="button"  value="导出模板"/></a></div></td>
              </tr>
           </table>
            <!-- 详情不显示按钮 -->
	           <div class="modal-footer">
		           <input type="button" class="btn btn-primary" value="确定"/> 
		           <input type="button" class="btn btn-default" data-dismiss="modal" value="关闭"/> 
		       </div>
         </form>
	   </div>
 </div>
 <!-- 隐藏iframe设置表单的目标为这个嵌入框架  使页面效果不会跳转 -->
 <iframe style="width:0; height:0;display: none;" id="SMSManageSubmitFrame" name="SMSManageSubmitFrame"></iframe>
</body>
</html>