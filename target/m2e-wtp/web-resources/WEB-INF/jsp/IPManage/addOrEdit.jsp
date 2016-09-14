<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<body>
<div>
	   <div>
		 <form id="IPManagesaveForm" action="">
		   <input type="hidden" name="id" value="${iPManage.id}"/>
           <table>
              <tr>
                <td><div class="firstFont"><span class="starColor">*</span>服务器IP：</div></td>
                <td><div><input name="ip" class="form-control required" title="请填写正确的ip地址"  maxlength="100" value="${iPManage.ip}"/></div></td>
                <td><div class="leftFont"><span class="starColor">*</span>端口：</div></td>
                <td><div><input name="port" class="form-control number" title="请填写正确的端口" maxlength="5" value="${iPManage.port}"/></div></td>
              </tr>
              <tr>
                <td><div class="firstFont"><span class="starColor">*</span>平台名称：</div></td>
                <td><div><input name="platformName" class="form-control required" title="平台名称不能为空" maxlength="100" value="${iPManage.platformName}"/></div></td>
                <td><div class="leftFont">平台类型：</div></td>
                <td><div>
                   <select name="platformType" class="form-control city">
                      <option value="1" <c:if test="${iPManage.platformType eq '1'}">selected="selected"</c:if>>二级平台</option>
                      <option value="2" <c:if test="${iPManage.platformType eq '2'}">selected="selected"</c:if>>云平台</option>
                  </select>
                </div></td>
              </tr>
              <tr>
                <td><div class="firstFont"><span class="starColor">*</span>小区名称：</div></td>
                <td><div><input name="neibName" class="form-control required" title="小区名称不能为空" maxlength="100" value="${iPManage.neibName}"/></div></td>
                <td><div class="leftFont">备注：</div></td>
                <td><div><input name="remark" class="form-control" maxlength="100" value="${iPManage.remark}"/></div></td>
              </tr>
              <tr>
                <td><div class="firstFont"><span class="starColor">*</span>开通物业功能：</div></td>
                <td><div>
                   <select name="management" class="form-control">
                     <option <c:if test="${iPManage.management eq false}">selected="selected"</c:if> value="false">否</option>
                     <option <c:if test="${iPManage.management eq true}">selected="selected"</c:if> value="true">是</option>
                   </select>
                </div></td>
              </tr>
           </table>
         </form>
	   </div>
 </div>
</body>
</html>