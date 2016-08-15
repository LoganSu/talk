//search.jsp 与controller 类@RequestMapping路径一致
//var testseachPath = "/test";
//table js
var unitTable ={
		method: 'get',
        url: $path+'/mc/unit/showList.do',
        cache: false,
//        height: 600,
        striped: true,
        pagination: true,
        pageSize: 20,
        pageList: [20, 50, 100, 200],
        sidePagination:'server',//设置为服务器分页
        queryParams: queryParams,//查询参数
        minimumCountColumns: 2,
        //双击事件
//        onDblClickRow: function (row) {
//        	$("#showRightArea").load($path+"/mc/room/roomListshowPage.do?module=roomTable&modulePath=/room&parentId="+row.parentId);
//        },
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
            field: 'unitNum',
            title: '单元编号',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'unitName',
            title: '单元名称',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'remark',
            title: '备注',
            align: 'center',
            valign: 'middle',
            sortable: false
        }]
}


	function queryParams(params) {
	   return initSearchParams("unitSearchForm",params);
	}

