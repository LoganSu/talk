//search.jsp 与controller 类@RequestMapping路径一致
//var testseachPath = "/test";
//table js
var workerGroupTable ={
		method: 'get',
        url: $path+'/mc/workerGroup/showList.do',
        cache: false,
//        height: 600,
//        width:'1000',
        striped: true,
        pagination: true,
        pageSize: 20,
        pageList: [20, 50, 100, 200],
        sidePagination:'server',//设置为服务器分页
        queryParams: queryParams,//查询参数
        minimumCountColumns: 2,
        //双击事件
//        onDblClickRow: function (row) {
//        	$("#showRightArea").load($path+"/mc/neighborhoods/neighborhoodsListshowPage.do?module=neighborhoodsTable&modulePath=/neighborhoods&parentId="+row.parentId);
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
            field: 'groupName',
            title: '组名称',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'powerStr',
            title: '组类别',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'remark',
            title: '备注',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'createTimeStr',
            title: '创建时间',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'departmentName',
            title: '所属公司',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'operator',
            title: '操作',
            align: 'center',
            valign: 'middle',
            sortable: true
        }]
}


	function queryParams(params) {
	   return initSearchParams("workerGroupSearchForm",params);
	}

