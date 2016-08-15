//search.jsp 与controller 类@RequestMapping路径一致
//var testseachPath = "/test";
//table js
var groupWorkerShowWorkerTable ={
		method: 'get',
        url: $path+'/mc/workerGroup/showgroupWorkerList.do',
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


	function queryParams(params) {
	   return initSearchParams("areaSearchForm",params);
	}

