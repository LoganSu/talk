//search.jsp 与controller 类@RequestMapping路径一致
//var testseachPath = "/test";
//table js
var permissionTable ={
		method: 'get',
        url: $path+'/mc/permission/showList.do',
        cache: false,
//        height: 600,
        striped: true,
        pagination: true,
        pageSize: 20,
        pageList: [20, 50, 100, 200],
        sidePagination:'server',//设置为服务器分页
        queryParams: queryParams,//查询参数
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
        }, {
            field: 'fname',
            title: '姓名',
            align: 'center',
            valign: 'middle',
            sortable: true
        }, {
            field: 'sexStr',
            title: '性别',
            align: 'center',
            valign: 'middle',
            sortable: true
        }, {
            field: 'idnum',
            title: '身份证号码',
            align: 'center',
            valign: 'middle',
            sortable: true,
        },{
            field: 'phone',
            title: '联系电话',
            align: 'center',
            valign: 'middle',
            sortable: true
        }, {
            field: 'oprTypeStr',
            title: '人员类型',
            align: 'center',
            valign: 'middle',
            sortable: true
        }, {
            field: 'cardcount',
            title: '办卡次数',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'operate',
            title: '操作',
            align: 'center',
            valign: 'middle',
            sortable: false
        } ]
}
	function queryParams(params) {
	   return initSearchParams("permissionSearchForm",params);
	}

