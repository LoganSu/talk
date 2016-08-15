//search.jsp 与controller 类@RequestMapping路径一致
//var testseachPath = "/test";
//table js
var registUserTable ={
		method: 'get',
        url: $path+'/mc/registUser/showList.do',
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
            field: 'username',
            title: '用户名',
            align: 'center',
            valign: 'middle',
            sortable: true
        }, {
            field: 'phone_number',
            title: '手机号',
            align: 'center',
            valign: 'middle',
            sortable: true
        }, {
            field: 'email',
            title: '邮箱',
            align: 'center',
            valign: 'middle',
            sortable: true,
        }, {
            field: 'statusStr',
            title: '邮箱激活状态',
            align: 'center',
            valign: 'middle',
            sortable: true
        }]
}


	function queryParams(params) {
	   return initSearchParams("registUserSearchForm",params);
	}

