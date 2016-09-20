<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
   <script type="text/javascript">
 $(function(){
	 var groupId = $(".whiteListSMSManageId").val();
	 $("#whiteListShowTable").bootstrapTable({
			    method: 'get',
		        url: $path+'/mc/SMSManage/showWhiteList.do?id='+SMSManageId,
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
		            field: 'ip',
		            title: 'IP地址',
		            align: 'center',
		            valign: 'middle',
		            sortable: true
		        },{
		            field: 'port',
		            title: '端口',
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
	 });
 })
 function queryParams(params) {
	   return initSearchParams("",params);
	}
</script>
       <input type="hidden" class="whiteListSMSManageId" value="${SMSManage.id}"/>
       <div>
		<table id="whiteListShowTable"></table>
       </div>
  
</body>
</html>