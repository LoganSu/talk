//search.jsp 与controller 类@RequestMapping路径一致
//var testseachPath = "/test";
//table js
var managementCompanyTable ={
		method: 'get',
        url: $path+'/mc/department/showList.do',
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
            width: '60',
            valign: 'middle',
            sortable: false
        },{
            field: 'departmentName',
            title: '公司名称',
            align: 'center',
            width: '60',
            valign: 'middle',
            sortable: false
        },{
            field: 'tel',
            title: '公司电话',
            align: 'center',
            width: '60',
            valign: 'middle',
            sortable: false
        },{
            field: 'address',
            title: '公司地址',
            align: 'center',
            width: '60',
            valign: 'middle',
            sortable: false
        },{
            field: 'description',
            title: '公司描述',
            align: 'center',
            width: '60',
            valign: 'middle',
            sortable: false
        },{
            field: 'createTimeStr',
            title: '创建时间',
            align: 'center',
            width: '60',
            valign: 'middle',
            sortable: true
        }]
}


	function queryParams(params) {
	   return initSearchParams("managementCompanySearchForm",params);
	}

