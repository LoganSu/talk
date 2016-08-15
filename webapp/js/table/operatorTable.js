//search.jsp 与controller 类@RequestMapping路径一致
//var testseachPath = "/test";
//table js
var operatorTable ={
		method: 'get',
        url: $path+'/mc/user/showList.do',
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
        },{
            field: 'loginName',
            title: '登入名',
            align: 'center',
            valign: 'middle',
            sortable: true
        }, {
            field: 'realName',
            title: '姓名',
            align: 'center',
            valign: 'middle',
            sortable: true,
        }, {
            field: 'phone',
            title: '手机号码',
            align: 'center',
            valign: 'middle',
            sortable: true
        }]
}


	function queryParams(params) {
	   return initSearchParams("manageUserSearchForm",params);
	}


