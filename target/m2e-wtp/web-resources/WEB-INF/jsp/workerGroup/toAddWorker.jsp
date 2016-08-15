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
	   var workerIds = $("#workerGroupAddWorkersaveForm .workerIds").val();
		if(workerIds.length>2){
			workerIds=workerIds.substring(1,workerIds.length-1);
			var arr= workerIds.split(",");
			  $.each(arr,function(index,obj){
				  $("#workerGroupAddWorkerShowTree ."+$.trim(obj)).prop('checked',true);
			  });
		}
 })
 function queryParams(params) {
	   return initSearchParams("",params);
	}
	 function showTree(departmentId){
			$('#workerGroupAddWorkerShowTree').bstree({
				url: $path+'/mc/departmentWorkerTree',
				height:'auto',
				param:'departmentId='+departmentId,
				open: false,
				checkbox:true,
				checkboxLink:true,
				treecheckboxFiledName:'workerIds',
				showurl:false
		   })
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
		    <input type="hidden" class="workerIds" value="${workerGroup.workerIds}"/>
               <div class="firstFont">人员列表：</div>
               <p id="workerGroupAddWorkerShowTree"></p>
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