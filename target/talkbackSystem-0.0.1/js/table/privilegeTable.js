//search.jsp 与controller 类@RequestMapping路径一致
//var testseachPath = "/test";
//table js
var privilegeTable ={
		method: 'get',
        url: $path+'/mc/privilege/showList.do',
        cache: false,
//        height: 600,
        striped: true,
        pagination: true,
        pageSize: 20,
        pageList: [20, 50, 100, 200],
        sidePagination:'server',//设置为服务器分页
        queryParams: queryParams,//查询参数
      //双击事件
        onDblClickRow: function (row) {
//        	没有子节点就不继续跳转页面
//        	$.post($path+"/mc/privilege/hashChildren.do?parentId="+row.id,function($data){
//        		if($data.length>0){
//        			$("#showRightArea").load($path+"/mc/privilege/privilegeListshowPage.do?module=privilegeTable&modulePath=/privilege&parentId="+row.id);
//        		}
//        	});
        },
        minimumCountColumns: 2,
        clickToSelect: true,
        columns: [{
            field: 'state',
            checkbox: true
        }, {
            field: 'index',
            title: '序号',
            align: 'center',
            valign: 'middle',
            sortable: false
        },{
            field: 'name',
            title: '权限名称',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'key',
            title: '权限key值',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'description',
            title: '权限描述',
            align: 'center',
            valign: 'middle',
            sortable: true
        }]
}


	function queryParams(params) {
	   return initSearchParams("authoritySearchForm",params);
	}


