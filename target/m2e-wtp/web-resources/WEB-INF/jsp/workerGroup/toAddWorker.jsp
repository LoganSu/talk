<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<body>
<script type="text/javascript">
 $(function(){
	 //员工列表
	 var groupId = $("#workerGroupAddWorkersaveForm .groupId").val();
	 $("#workerGroupAddWorkerShowTable").bootstrapTable({
			    method: 'get',
		        url: $path+'/mc/workerGroup/showgroupWorkerList.do?id='+groupId,
		        cache: false,
		        height: 300,
//		        width:'1000',
		        striped: true,
		        pagination: true,
		        pageSize: 10,
		        pageList: [10, 20, 50],
		        sidePagination:'server',//设置为服务器分页
		        queryParams: queryParams,//查询参数
		        minimumCountColumns: 2,
		        clickToSelect: true,
		        columns: [ {
		            field: 'index',
		            title: '序号',
		            align: 'center',
		            valign: 'middle',
		            sortable: false
		        },{
		            field: 'departmentName',
		            title: '部门',
		            align: 'center',
		            valign: 'middle',
		            sortable: true
		        },{
		            field: 'workerName',
		            title: '姓名',
		            align: 'center',
		            valign: 'middle',
		            sortable: true
		        },{
		            field: 'phone',
		            title: '电话号码',
		            align: 'center',
		            valign: 'middle',
		            sortable: true
		        }]		 
	 }
	 );
	    //显示部门人员tree
       var departmentId = $("#workerGroupAddWorkersaveForm .departmentId").val();
	   if(departmentId){
		   showTree(departmentId);
	   }
// 	   var workerIds = $("#workerGroupAddWorkersaveForm .workerIds").val();
// 		if(workerIds.length>2){
// 			workerIds=workerIds.substring(1,workerIds.length-1);
// 			var arr= workerIds.split(",");
// 			  $.each(arr,function(index,obj){
// 				  $("#workerGroupAddWorkerShowTree ."+$.trim(obj)).prop('checked',true);
// 			  });
// 		}
 })
 function queryParams(params) {
	   return initSearchParams("",params);
	}
 

	 function showTree(departmentId){
		 var id = $("#workerGroupAddWorkersaveForm [name='id']").val();
		 var treecheckbox = "${workerGroup.treecheckbox}";
		 var parentIds = "${parentIds}"

		 zTreeObj = zTree("workerGroupAddWorkerShowTree", ["id","name","level"],["departmentId",departmentId,"nocheckLevel","0123"],$path+"/mc/department/getWorkerNodes.do",true,{"Y": "", "N": ""},null,workerGroupAddWorkerDataEcho(id,treecheckbox,parentIds), null)
	 }
	 
	//数据回显函数
	  function workerGroupAddWorkerDataEcho(id,treecheckbox,parentIds){
		  var zTreeOnAsyncSuccess;
		  if(id&&treecheckbox&&parentIds){
			  zTreeOnAsyncSuccess = function(event, treeId, treeNode, msg) {
				//子节点回显
				 if(treeNode){
					 $.each(treeNode.children,function(i,obj){
						 if(parentIds.indexOf(obj.id)>0){
						    zTreeObj.reAsyncChildNodes(treeNode.children[i], "refresh");
						 }
						 if(treecheckbox.indexOf(obj.id)>0){
							 zTreeObj.checkNode(treeNode.children[i], true, false);
						 }
					 })
					//第一级节点回显
				 }else{
				     var nodes = zTreeObj.getNodes();
				     $.each(nodes,function(i,obj){
						 if(parentIds.indexOf(obj.id)>0){
						        zTreeObj.reAsyncChildNodes(nodes[i], "refresh");
						 }
						 if(treecheckbox.indexOf(obj.id)>0){
							 zTreeObj.checkNode(nodes[i], true, false);
						 }
					 })
				 }
		     };
		  }
		  
		  return zTreeOnAsyncSuccess;
	  }
</script>
<div>
       <div>
		<table id="workerGroupAddWorkerShowTable"></table>
       </div>
	   <div>
		 <form id="workerGroupAddWorkersaveForm" action="">
		   <input type="hidden" name="id" class="groupId" value="${workerGroup.id}"/>
		    <input type="hidden" class="departmentId" value="${workerGroup.departmentId}"/>
<%-- 		    <input type="hidden" class="workerIds" value="${workerGroup.workerIds}"/> --%>
               <div class="firstFont">人员列表：</div>
                 <ul id="workerGroupAddWorkerShowTree" class="ztree" style="width:260px; overflow:auto;"></ul>
               
               <!-- 详情不显示按钮 -->
	           <div class="modal-footer">
		         <!--操作按钮 -->
		           <input type="button" class="btn btn-primary sure" value="确定"/> 
		            <input type="button" class="btn btn-default" data-dismiss="modal" value="关闭"/> 
		       </div>
         </form>
	   </div>
 </div>
</body>
</html>