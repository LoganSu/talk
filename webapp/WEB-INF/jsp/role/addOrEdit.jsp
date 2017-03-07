<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<body>
<div>
	   <div>
		 <form id="rolesaveForm" action="">
		   <div>
		    <input type="hidden" name="id" value="${role.id}"/>
		    <input type="hidden" name="checked" value=""/>
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
                <td><div><input name="roleName" class="form-control required" title="角色名称不能为空" value="${role.roleName}"/></div></td>
                <td><div class="leftFont"><span class="starColor">*</span>角色描述：</div></td>
                <td><div><input name="description" class="form-control required" title="角色描述不能为空" value="${role.description}"/></div></td>
              </tr>
           </table>
           </div>
<!--            <div  style="margin-top: 20px;margin-left: 20px;"> -->
               <div><label>选择可操作权限：</label></div>
<!--                <div class="showRolesTable" style="height: 500px"> -->
               <ul id="privilegeShowTree" class="ztree" style="width:260px; overflow:auto;"></ul>
           	    <div><label>选择域权限：</label></div>
                <ul id="domainShowTree" class="ztree" style="width:260px; overflow:auto;"></ul>
                   
<!--                </div> -->
<!--            </div> -->
           
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
	  var id = $("#rolesaveForm [name='id']").val();
	     var privilegeIds = "${role.privilegeIds}";
		 zTreeObj = zTree("privilegeShowTree", ["id","name","level"],["roleId",id],$path+"/mc/privilege/getNodes.do",true,{"Y": "ps", "N": "ps"},null,dataEcho(id,privilegeIds), zTreeOnCheck)
		 var nodes = zTreeObj.getSelectedNodes();
			if (nodes.length>0) {
				$.each(nodes,function(i,obj){
					zTreeObj.reAsyncChildNodes(obj, "refresh");
				})
			}
		 
		 
		 var domainIds = "${role.domainIds}";
		 zTreeObjTwo = zTree("domainShowTree", ["id","name","level"],[],$path+"/mc/domain/getNodes.do",true,{"Y": "ps", "N": "ps"},null,dataEchoTwo(id,domainIds), zTreeOnCheck)
		
		 
		 
		 
  })     
        //设置有费用管理权限的角色标记 后台做数据同步
        function zTreeOnCheck(event, treeId, treeNode) {
	          $("#rolesaveForm [name='checked']").val("");
	          var nodes = zTreeObj.getCheckedNodes(true);
	          $.each(nodes,function(i,obj){
				  if(obj.name=="费用管理"&&obj.checked){
					   $("#rolesaveForm [name='checked']").val("1");
				  }
	          })
          };
  
        //加载树成功之后数据回显函数
		  function dataEchoTwo(id,treecheckbox){
			  var zTreeOnAsyncSuccess;
			  if(id&&treecheckbox){
				  zTreeOnAsyncSuccess = function(event, treeId, treeNode, msg) {
					//子节点回显
					 if(treeNode){
						 $.each(treeNode.children,function(i,obj){
							 if(treecheckbox.indexOf(obj.id)>0){
								 zTreeObjTwo.checkNode(treeNode.children[i], true, false);
								 zTreeObjTwo.reAsyncChildNodes(treeNode.children[i], "refresh");
//                                  var node = zTreeObjTwo.getNodeByTId(treeNode.children[i].id);
//                                  zTreeObjTwo.expandNode(node, true, false);
							 }
						 })
						//第一级节点回显
					 }else{
					     var nodes = zTreeObjTwo.getNodes();
					     $.each(nodes,function(i,obj){
							 if(treecheckbox.indexOf(obj.id)>0){
								 zTreeObjTwo.checkNode(nodes[i], true, false);
								 zTreeObjTwo.reAsyncChildNodes(nodes[i], "refresh");
								 
// 								 var node = zTreeObjTwo.getNodeByTId(nodes[i].id);
//                                  zTreeObjTwo.expandNode(node, true, false);
							 }
						 })
					 }
			     };
			  }
			  
			  return zTreeOnAsyncSuccess;
		  }
 </script>
