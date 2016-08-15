<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<body>
<div>
	   <div>
		 <form id="userssaveForm" action="">
		   <input type="hidden" name="id" value="${users.id}"/>
		   <input type="hidden" id="usersDomainIds" value="${users.treecheckbox}"/>
           <table>
              <tr>
                <td><div class="firstFont"><span class="starColor">*</span>姓名：</div></td>
                <td><div><input name="fname" class="form-control required" title="居住人姓名必填" maxlength="3" value="${users.fname}"/></div></td>
                <td><div class="leftFont">性别：</div></td>
                <td><div><input type="radio" name="sex" <c:if test="${users.sex=='1'}">checked="checked"</c:if> value="1" />男
                <input type="radio" name="sex" <c:if test="${users.sex=='2'}">checked="checked"</c:if> value="2"/>女</div></td>
                <td><div class="leftFont"><span class="starColor">*</span>身份证号码：</div></td>
                <td><div><input name="idNum" class="form-control required" title="身份证号码必填"  value="${users.idNum}"/></div></td>
              </tr>
              <tr>
                <td><div class="firstFont"><span class="starColor">*</span>联系电话：</div></td>
                <td><div><input name="phone" class="form-control required" title="联系电话必填" value="${users.phone}"/></div></td>
                 <td><div class="leftFont">邮箱：</div></td>
                <td><div><input name="email" class="form-control" value="${users.email}"/></div></td>
                <td><div class="leftFont">户主：</div></td>
                <td><div><input type="radio" name="usersType" <c:if test="${users.usersType=='2'}">checked="checked"</c:if> value="2"/>否
                <input type="radio" name="usersType" <c:if test="${users.usersType=='1'}">checked="checked"</c:if> value="1" />是</div></td>
              </tr>
              <tr>
                 <td><div class="firstFont">文化程度：</div></td>
		                <td><div>
		                   <select  class="form-control" name="education">
		                     <option <c:if test="${users.education=='1'}">selected="selected"</c:if>  value="1">小学</option>
		                     <option <c:if test="${users.education=='2'}">selected="selected"</c:if>  value="2">初中</option>
		                     <option <c:if test="${users.education=='3'}">selected="selected"</c:if>  value="3">高中（中专、中技）</option>
		                     <option <c:if test="${users.education=='4'}">selected="selected"</c:if>  value="4">大专</option>
		                     <option <c:if test="${users.education=='5'}">selected="selected"</c:if>  value="5">本科</option>
		                     <option <c:if test="${users.education=='6'}">selected="selected"</c:if>  value="6">研究生</option>
		                     <option <c:if test="${users.education=='7'}">selected="selected"</c:if>  value="7">其他</option>
		                </select></div></td>
                 <td><div class="leftFont">籍贯：</div></td>
                 <td><div><input name="nativePlace" class="form-control" value="${users.nativePlace}"/></div></td>
              </tr>
              <tr>
                 <td><div class="firstFont">工作单位：</div></td>
                 <td colspan="5"><div><input style="width: 500px" name="companyName" class="form-control" value="${users.companyName}"/></div></td>
              </tr>
              <tr>
                 <td><div class="firstFont">备注：</div></td>
                 <td colspan="5"><div><input style="width: 500px" name="remark" class="form-control" value="${users.remark}"/></div></td>
              </tr>
           </table>
         </form>
	   </div>
 </div>
</body>
</html>