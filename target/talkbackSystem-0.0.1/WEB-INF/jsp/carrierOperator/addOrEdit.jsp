<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<body>
<div>
	   <div>
		 <form id="carrierOperatorsaveForm" action="">
		   <div>
			    <input type="hidden" name="id" value="${user.id}"/>
	            <table>
	              <tr>
	                <td><div class="leftFont"><span class="starColor">*</span>登入名：</div></td>
	                <td><div>
	                   <input name="loginName" class="form-control required" maxlength="20" title="登入名不能为空" value="${user.loginName}"/>
	                </div></td>
	                <td><div class="leftFont"><span class="starColor">*</span>姓名：</div></td>
	                <td><div><input name="realName" class="form-control required" maxlength="20" title="姓名不能为空" value="${user.realName}"/></div></td>
	                <td><div class="leftFont">电话：</div></td>
	                <td><div><input name="phone" class="form-control" maxlength="11" title="电话不能为空" value="${user.phone}"/></div></td>
	              </tr>
<!-- 	              <tr> -->
<!-- 	                 <td><div class="leftFont"><span class="starColor">*</span>系统管理员：</div></td> -->
<!--                      <td><div>  -->
<!--                      <select class="form-control required" name="carrierId" title="请选择运营商"> -->
<!--                         <option value="">否</option> -->
<!--                         <option value="1">是</option> -->
<!--                      </select> -->
                 
<!--                  </div></td> -->
<!-- 	              </tr> -->
	           </table>
           </div>
<!--            <div style="margin-top: 20px;margin-left: 20px"> -->
<!-- 	           <label>选择角色:</label> -->
<!-- 	           <div class="showRolesTable" style="height: 500px"> -->
<!-- 	              <div> -->
<%-- 		            <c:forEach items="${roleList}" var="role" varStatus="staus"> --%>
<!-- 	                       五个换行 -->
<%--                            <c:if test="${staus.index>0&&staus.index%5==0}"> --%>
<!--                              <br/> -->
<%--                            </c:if> --%>
<%-- 	                     <input name="roleIds" value="${role.id}" <c:if test="${role.checked==1}">checked="checked"</c:if> type="checkbox"/><span>${role.roleName}</span> --%>
<%-- 		            </c:forEach> --%>
<!-- 	              </div> -->
	                
<!-- 	           </div> -->
<!--            </div> -->
           
         </form>
	   </div>
 </div>
</body>
