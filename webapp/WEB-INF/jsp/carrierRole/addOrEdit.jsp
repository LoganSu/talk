<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<body>
<div>
	   <div>
		 <form id="carrierRolesaveForm" action="">
		   <div>
		    <input type="hidden" name="id" value="${role.id}"/>
		    <input type="hidden" name="carrierId" value="${role.carrierId}"/>
		    <input type="hidden" name="isAdmin" value="${role.isAdmin}"/>
            <table>
<!--               <tr> -->
<!--                  <td><div class="leftFont"><span class="starColor">*</span>选择运营商：</div></td> -->
<!--                  <td><div>  -->
<!--                      <select class="form-control required" name="carrierId" title="请选择运营商"> -->
<!--                         <option value="">--请选择运营商--</option> -->
<%--                         <c:forEach items="${carrierList}" var="carrier"> --%>
<%--                            <option <c:if test="${role.carrierId==carrier.id}">selected="selected"</c:if> value="${carrier.id}">${carrier.carrierName}</option> --%>
<%--                         </c:forEach>     --%>
<!--                      </select> -->
                 
<!--                  </div></td> -->
<!--               </tr> -->
              <tr>
                <td><div class="leftFont"><span class="starColor">*</span>角色名称：</div></td>
                <td><div><input name="roleName" class="form-control required" maxlength="20" title="角色名称不能为空" value="${role.roleName}"/></div></td>
                <td><div class="leftFont"><span class="starColor">*</span>角色描述：</div></td>
                <td><div><input name="description" class="form-control required" maxlength="200" title="角色描述不能为空" value="${role.description}"/></div></td>
              </tr>
           </table>
           </div>
<!--            <div  style="margin-top: 20px;margin-left: 20px;"> -->
<!--                <div><label>选择可操作权限：</label></div> -->
<!--                <div class="showRolesTable" style="height: 500px"> -->
<!--                </div> -->
<!--            </div> -->
           <p id="carrierRolesprivilegeShowTree"></p>
         </form>
	   </div>
 </div>
</body>
<style>
.showRolesTableDiv{
padding-left: 20px
}
</style>
<script type="text/javascript">
  $(function(){
		  var id = $("#carrierRolesaveForm [name='id']").val();
		  var params = "id="+id;
		  //domainTree(id, url, open, checkbox, checkboxLink, showurl, checkboxPartShow, layer, treecheckboxFiledName,params)
			domainTree("carrierRolesprivilegeShowTree", $path+'/mc/role', false, true, true, false,false,null,null,params);
		    // 普通tree
// 			$('#carrierRolesprivilegeShowTree').bstree({
// 					url: $path+'/mc/role',
// 					param :params,
// // 					open: true,
// 					showurl:false,
// 					checkbox:true
// 			});
	  
	  
	  
	  
	  
// 	  var id = $("#carrierRolesaveForm [name='id']").val();
// 	  $.post($path+"/mc/role/privilegeList.do","id="+id,function($data){
// 		  var $div = $(".showRolesTable");
// 		  eachPrivilege($data, $div);
// 	  })
	  
// 	 //选择了子节点默认选中所有的父节点
// 	 $(document).on('click',".showRolesTableDiv [name='privilegeIds']",function(){
// 		var checks =  $(this).parents(".showRolesTableDiv").children("input");
// 		if(checks.prop("checked")){
// 			checks.prop("checked",true);
// 		}
// 	 })	  
// 	 //父节点取消选择，子节点全部取消,全选
// 	 $(document).on('click',".showRolesTableDiv [name='privilegeIds']",function(){
// 		 var checks = $(this).parent().find("input");
// 		 if(checks.prop("checked")){
// 				checks.prop("checked",true);
// 			}else{
// 				checks.prop("checked",false);
// 			}
// 	 })
// 	 //递归显示权限列表
// 	 var eachPrivilege =  function(list,$div){
// 		 $.each(list,function(i,obj){
// 			 var divStr = "";
// 			 if(obj.checked==1){
// 				 divStr = "<div class='showRolesTableDiv'><input name='privilegeIds' checked='checked' value='"+obj.id+"' type='checkbox'/><span>"+obj.name+"</span></div>";
// 			 }else{
// 				 divStr = "<div class='showRolesTableDiv'><input name='privilegeIds' value='"+obj.id+"' type='checkbox'/><span>"+obj.name+"</span></div>";
// 			 }
// 			 $div.append(divStr);
// 			var $subDiv = $("#carrierRolesaveForm [value='"+obj.id+"']").parent();
// 			 if(obj.children){
// 				 eachPrivilege(obj.children,$subDiv);
// 			 }
// 		 });
// 	  }
	
	  
	  
  })
 </script>
