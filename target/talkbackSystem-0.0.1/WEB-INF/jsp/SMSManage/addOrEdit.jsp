<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<body>
<script type="text/javascript">
$(function(){
	 
	
})

</script>
<div>
	   <div>
		 <form id="SMSManagesaveForm" action="">
		   <input type="hidden" name="id" value="${SMSManage.id}"/>
           <table>
              <tr>
                <td><div class="firstFont"><span class="starColor">*</span>服务器IP：</div></td>
                <td><div><input name="ip" class="form-control required" title="请填写正确的ip地址"  maxlength="100" value="${SMSManage.ip}"/></div></td>
                <td><div class="leftFont"><span class="starColor">*</span>端口：</div></td>
                <td><div><input name="port" class="form-control required" title="端口不能为空" maxlength="5" value="${SMSManage.port}"/></div></td>
              </tr>
              <tr>
                <td><div class="firstFont"><span class="starColor">*</span>用户名：</div></td>
                <td><div><input name="platformName" class="form-control required" title="用户名不能为空" maxlength="100" value="${SMSManage.username}"/></div></td>
                <td><div class="leftFont">密码：</div></td>
                <td><div><input name="pwd" class="form-control required" title="密码不能为空" maxlength="100" value="${SMSManage.pwd}"/></div></td>
              </tr>
              <tr>
                <td><div class="firstFont"><span class="starColor">*</span>公司签名：</div></td>
                <td><div><input name="sign" class="form-control required" title="公司签名不能为空" maxlength="200" value="${SMSManage.sign}"/></div></td>
              </tr>
              <tr>
                <td><div><input type="button" class="form-control" value="导出模板"/></div></td>
                <td><div class="leftFont">导入白名单：</div></td>
                <td><div><input name="remark" class="form-control" maxlength="100" value="${iPManage.remark}"/></div></td>
              </tr>
           </table>
         </form>
	   </div>
 </div>
</body>
</html>