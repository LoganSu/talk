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
                <td><div class="firstFont"><span class="starColor">*</span>访问IP：</div></td>
                <td><div><input name="ip" class="form-control required" title="请填写正确的ip地址"  maxlength="100" value="${iPManage.ip}"/></div></td>
                <td><div class="leftFont"><span class="starColor">*</span>https端口：</div></td>
                <td><div><input name="port" class="form-control required" title="请填写正确的端口" value="${iPManage.port}"/></div></td>
              </tr>
              <tr>
                <td><div class="firstFont"><span class="starColor">*</span>服务器真实IP：</div></td>
                <td><div><input name="httpIp" class="form-control required" title="请填写正确的ip地址"  maxlength="100" value="${iPManage.httpIp}"/></div></td>
                 <td><div class="leftFont"><span class="starColor">*</span>http端口：</div></td>
                <td><div><input name="httpPort" class="form-control required" title="请填写正确的端口" value="${iPManage.httpPort}"/></div></td>
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
                <td><div class="firstFont"><span class="starColor">*</span>FS IP地址：</div></td>
                <td><div><input name="fsIp" class="form-control required" title="FS IP地址不能为空" maxlength="100" value="${iPManage.fsIp}"/></div></td>
                <td><div class="leftFont">FS端口：</div></td>
                <td><div><input name="fsPort" class="form-control required" title="FS端口不能为空" maxlength="100" value="${iPManage.fsPort}"/></div></td>
                <td><div class="leftFont">FS外呼端口：</div></td>
                <td><div><input name="fsExternalPort" class="form-control required" title="FS端口不能为空" maxlength="100" value="${iPManage.fsExternalPort}"/></div></td>
              </tr>
              <tr>
                <td><div class="firstFont"><span class="starColor">*</span>省份：</div></td>
                <td><div>
                  <select name="province" class="form-control province">
                     <option value="">请选择省份</option>
                    <c:forEach items="${provinceList}" var="province">
                       <option value="${province.id}_${province.fshortname}" <c:if test="${province.fshortname==iPManage.province}">selected="selected"</c:if>>${province.fshortname}</option>
                    </c:forEach>
                  </select>
                
                </div></td>
                <td><div class="leftFont"><span class="starColor">*</span>城市：</div></td>
                <td><div>
                   <select name="city" class="form-control city">
                    <option value="">请选择城市</option>
                    <c:if test="${iPManage.id!=null}">
                      <option value="${iPManage.city }" selected="selected">${iPManage.city }</option>
                    </c:if>
                  </select>
                </div></td>
                <td><div class="leftFont"><span class="starColor">*</span>小区名称：</div></td>
                <td><div><input name="neibName" class="form-control required" title="小区名称不能为空" maxlength="100" value="${iPManage.neibName}"/></div></td>
              </tr>
              <tr>
                <td><div class="firstFont"><span class="starColor">*</span>开通物业功能：</div></td>
                <td><div>
                   <select name="management" class="form-control">
                     <option <c:if test="${iPManage.management eq false}">selected="selected"</c:if> value="false">否</option>
                     <option <c:if test="${iPManage.management eq true}">selected="selected"</c:if> value="true">是</option>
                   </select>
                </div></td>
                <td><div class="leftFont">备注：</div></td>
                <td><div><input name="remark" class="form-control" maxlength="100" value="${iPManage.remark}"/></div></td>
              </tr>
           </table>
         </form>
	   </div>
 </div>
</body>
</html>