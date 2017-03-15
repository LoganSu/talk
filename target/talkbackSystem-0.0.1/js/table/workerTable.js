//search.jsp 与controller 类@RequestMapping路径一致
//var testseachPath = "/test";
//table js
var workerTable ={
		method: 'get',
        url: $path+'/mc/worker/showList.do',
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
            width: '60',
            valign: 'middle',
            sortable: false
        },{
            field: 'workerName',
            title: '姓名',
            align: 'center',
            width: '60',
            valign: 'middle',
            sortable: true
        },{
            field: 'sexStr',
            title: '性别',
            align: 'center',
            width: '60',
            valign: 'middle',
            
            sortable: true
        },{
            field: 'phone',
            title: '手机号码',
            align: 'center',
            width: '60',
            valign: 'middle',
            sortable: true
        },{
            field: 'departmentName',
            title: '所属部门',
            align: 'center',
            valign: 'middle',
            width: '60',
            sortable: true
        },{
            field: 'username',
            title: '账号',
            align: 'center',
            width: '60',
            valign: 'middle',
            sortable: true
        },{
            field: 'statusStr',
            title: '状态',
            align: 'center',
            width: '60',
            valign: 'middle',
            sortable: true
        },{
            field: 'createTimeStr',
            title: '创建时间',
            align: 'center',
            width: '60',
            valign: 'middle',
            sortable: true
        },{
            field: 'operate',
            title: '操作',
            align: 'center',
            width: '60',
            valign: 'middle',
            sortable: true
        }]
}


	function queryParams(params) {
	   return initSearchParams("workerSearchForm",params);
	}

