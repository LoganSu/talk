<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
  <div>
     <div style="text-align: center;">
        <div style="width:100%;height:300px;border:1px solid gray" id="containers"></div>  
     </div>
      <!-- 分割线 -->
      <div><hr noshade="noshade"/></div>
      <div>
		<table id="realTimeMonitorTable"></table>
      </div>
  </div>
<EMBED id="warnAudio" src="" loop="8" width=0 height=0>
</body>
<script type="text/javascript">
  $(function(){
	    //设置页面定时刷新
	    var flushPage = window.setInterval("flushPage()",3000);
		 $("#realTimeMonitorTable").bootstrapTable({
			    method: 'get',
		        url: $path+'/mc/realTimeMonitor/showList.do',
		        cache: false,
		        height: 410,
//		        width:'1000',
		        striped: true,
		        pagination: true,
		        pageSize: 10,
		        pageList: [10, 20, 50],
		        sidePagination:'server',//设置为服务器分页
		        queryParams: function(params){
		     	   return initSearchParams("",params);
		    	},//查询参数
		        minimumCountColumns: 2,
		        rowStyle:function (row, index) {
		        	//这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
		        	var strclass = "";
		        	if(row.statusStr=="未处理"){
		        		strclass='danger';
		        	}
		        	return { classes: strclass }
		        },
		        clickToSelect: true,
		        columns: [ {
		            field: 'index',
		            title: '序号',
		            align: 'center',
		            valign: 'middle',
		            sortable: false
		        },{
		            field: 'address',
		            title: '地址',
		            align: 'center',
		            valign: 'middle',
		            sortable: true
		        },{
		            field: 'warnType',
		            title: '告警类型',
		            align: 'center',
		            valign: 'middle',
		            sortable: true
		        },{
		            field: 'createTimeStr',
		            title: '时间',
		            align: 'center',
		            valign: 'middle',
		            sortable: true
		        },{
		            field: 'statusStr',
		            title: '状态',
		            align: 'center',
		            valign: 'middle',
		            sortable: true
		        },{
		            field: 'operate',
		            title: '操作',
		            align: 'center',
		            valign: 'middle',
		            sortable: true
		        }]		 
	 })
	 //引用百度地图
     var script = document.createElement("script"); 
//      script.src = "http://api.map.baidu.com/api?v=2.0&ak=v5MOhIsz4tPIROKshVmD4WC9UFH5UFcX&callback=initialize";//此为v2.0版本的引用方式  
     script.src = "http://api.map.baidu.com/api?v=1.4&ak=v5MOhIsz4tPIROKshVmD4WC9UFH5UFcX&callback=initialize";//此为v1.4版本的引用方式  
     document.body.appendChild(script);
  })

</script>
</html>
