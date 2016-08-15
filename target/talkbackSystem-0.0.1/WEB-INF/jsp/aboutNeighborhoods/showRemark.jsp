<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
$(function(){
	 //记录id
	 var id = $(".aboutNeighborhoodsId").val();
	 $("#aboutNeighborhoodsRemarkShowTable").bootstrapTable({
			    method: 'get',
		        url: $path+'/mc/aboutNeighborhoods/showRemarkList.do?id='+id,
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
		            field: 'statusStr',
		            title: '状态',
		            align: 'center',
		            valign: 'middle',
		            sortable: false
		        },{
		            field: 'operator',
		            title: '操作人',
		            align: 'center',
		            valign: 'middle',
		            sortable: false
		        },{
		            field: 'createTimeStr',
		            title: '操作时间',
		            align: 'center',
		            valign: 'middle',
		            sortable: false
		        },{
		            field: 'remark',
		            title: '备注',
		            align: 'center',
		            valign: 'middle',
		            sortable: false
		        }]		 
	 })
})
  function queryParams(params) {
	   return initSearchParams("",params);
	}
</script>
</head>
<body>
      <div>
		<table id="aboutNeighborhoodsRemarkShowTable"></table>
       </div>
        <input type="hidden" name="id" class="aboutNeighborhoodsId" value="${aboutNeighborhoods.id}"/>
         <!-- 详情不显示按钮 -->
      <div class="modal-footer">
          <input type="button" class="btn btn-default" data-dismiss="modal" value="关闭"/> 
      </div>
</body>
</html>