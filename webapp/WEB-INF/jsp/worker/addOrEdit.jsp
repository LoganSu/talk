<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%
String  departmentTree= "";
if(request.getAttribute("departmentTree")!=null){
	departmentTree= (String)request.getAttribute("departmentTree");
}
%>
<body>
<script type="text/javascript">
$(function(){
// 	$('#workerShowTree').bstree({
// 		url: $path+'/mc/departmentTree',
// 		height:'auto',
// 		open: false,
// 		checkbox:true,
// 		checkboxLink:false,
// 		treecheckboxFiledName:'departmentId',
// 		showurl:false
// }); 
// 	var departmentId = $("#workerdepartmentId").val();
// 	if(departmentId){
// 	 $("#workerShowTree ."+$.trim(departmentId)).prop('checked',true);
// 	}
	
	var departmentTree = <%=departmentTree%>
	var select = $("#workersaveForm .departmentId")
	showDepartmentTree(departmentTree,select);
})

</script>
<div>
	   <div>
		 <form id="workersaveForm" action="">
		   <input type="hidden" name="id" value="${worker.id}"/>
<%-- 		   <input type="hidden" class="departmentTree" value="${departmentTree}"/> --%>
            <span style="color: red;">说明：app登录密码与手机号码一致</span>
           <table>
              <tr>
                <td><div class="firstFont"><span class="starColor">*</span>姓名：</div></td>
                <td><div><input name="workerName" class="form-control required" title="姓名不能为空" value="${worker.workerName}"/></div></td>
              </tr>
               <tr>
                <td><div class="firstFont"><span class="starColor">*</span>手机号码：</div></td>
                <td><div><input name="phone" class="form-control required number" title="请填写11位手机号码" maxlength="11" value="${worker.phone}"/></div></td>
              </tr>
              <tr>
                <td><div class="firstFont">性别：</div></td>
                <td><div>
                   <c:choose>
                     <c:when test="${worker.sex==null}">
                        <input type="radio" value="1" name="sex" checked="checked"/>男
                        <input type="radio" value="2" name="sex" />女
                     </c:when>
                     <c:otherwise>
	                    <input type="radio" value="1" name="sex" <c:if test="${worker.sex==1}">checked="checked"</c:if>/>男
	                    <input type="radio" value="2" name="sex" <c:if test="${worker.sex==2}">checked="checked"</c:if>/>女
                     </c:otherwise>
                   </c:choose>
                </div></td>
               </tr>
               <tr>
                <td><div class="firstFont">所属部门：</div></td>
                <td><div>
                     <select class="form-control departmentId" name="departmentId">
                       <c:choose>
                          <c:when test="${worker.departmentId!=null}">
                             <option value="${worker.departmentId}">${worker.departmentName}</option>
                          </c:when>
                          <c:otherwise>
                             <option value="">--请选择--</option>
                          </c:otherwise>
                       </c:choose>
                     </select>
                </div></td>
               </tr>
             <tr>
                <td><div class="firstFont">状态：</div></td>
                <td><div>
                   <select name="status" class="form-control city">
                    <option value="1" <c:if test="${worker.status==1}">selected="selected"</c:if>>开通</option>
                    <option value="2" <c:if test="${worker.status==2}">selected="selected"</c:if>>暂停</option>
                    <option value="3" <c:if test="${worker.status==3}">selected="selected"</c:if>>关闭</option>
                  </select>
                </div></td>
               </tr>
           </table>
<!--                <div class="firstFont">选择部门：</div> -->
<!--                <p id="workerShowTree"></p> -->
         </form>
	   </div>
 </div>
</body>
</html>