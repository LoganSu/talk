<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<body>
<script type="text/javascript">
$(function(){
   $("#workerGroupsaveForm .department").on("change",function(){
	   var departmentId = $(this).val();
	   if(departmentId){
		   showTree(departmentId);
	   }
   })
   var update_departmentId = $("#workerGroupsaveForm .department").val();
   if(update_departmentId){
	   showTree(update_departmentId);
   }
})
	function showTree(departmentId){
	   var id = $("#workerGroupsaveForm [name='id']").val();
	   var treecheckbox = "${workerGroup.treecheckbox}";
	   zTreeObj = zTree("workerGroupShowTree", ["id","name","level"],["departmentId",departmentId,"nocheckLevel","0123"],$path+"/mc/department/getWorkerNodes.do",true,{"Y": "", "N": ""},null,dataEcho(id,treecheckbox), null)
	
// 		$('#workerGroupShowTree').bstree({
// 			url: $path+'/mc/departmentWorkerTree',
// 			height:'auto',
// 			param:'departmentId='+departmentId,
// 			open: false,
// 			checkbox:true,
// 			checkboxLink:true,
// 			treecheckboxFiledName:'workerIds',
// 			showurl:false
// 	   })
	}
	
</script>
<div>
	   <div>
		 <form id="workerGroupsaveForm" action="">
		   <input type="hidden" name="id" value="${workerGroup.id}"/>
<%-- 		   <input type="hidden" class="workerIds" value="${workerGroup.workerIds}"/> --%>
           <table>
              <tr>
                <td><div class="firstFont"><span class="starColor">*</span>所属公司：</div></td>
                <td colspan="3"><div>
                    <select name="departmentId" class="form-control department required" title="请选择所属公司">
                       <option value="">--请选择--</option>
                      <c:forEach items="${companyList}" var="company">
                         <option value="${company.id}" <c:if test="${company.id==workerGroup.departmentId}">selected="selected"</c:if>>${company.departmentName}</option>
                      </c:forEach>
                    </select>
                </div></td>
               </tr>
              <tr>
                <td><div class="firstFont"><span class="starColor">*</span>组名称：</div></td>
                <td><div><input name="groupName" class="form-control required" title="组名称不能为空" value="${workerGroup.groupName}"/></div></td>
                <td><div class="leftFont"><span class="starColor">*</span>组类别：</div></td>
                <td ><div>
                   <select name="power" class="form-control">
                     <option <c:if test="${workerGroup.power eq '1'}">selected="selected"</c:if> value="1">派单组</option>
                     <option <c:if test="${workerGroup.power eq '2'}">selected="selected"</c:if> value="2">普通组</option>
                   </select>
                 </div></td>
              </tr>
              <tr>
                  <td><div class="firstFont">备注：</div></td>
                  <td colspan="3"><div><input name="remark" style="width: 360px" class="form-control" value="${workerGroup.remark}"/></div></td>
              </tr>
           </table>
               <div class="firstFont">选择人员：</div>
               <ul id="workerGroupShowTree" class="ztree" style="width:260px; overflow:auto;"></ul>
               
         </form>
	   </div>
 </div>
</body>
</html>