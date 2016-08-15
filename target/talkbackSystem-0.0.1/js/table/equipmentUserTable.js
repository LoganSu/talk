//search.jsp 与controller 类@RequestMapping路径一致
//var testseachPath = "/test";
//table js
var equipmentUserTable ={
		method: 'get',
        url: $path+'/mc/equipmentUser/showList.do',
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
            field: 'equipName',
            title: '设备名称',
            align: 'center',
            valign: 'middle',
            sortable: true
        }, {
            field: 'installPosition',
            title: '安装位置',
            align: 'center',
            valign: 'middle',
            sortable: true
        }, {
            field: 'type',
            title: '设备类型',
            align: 'center',
            valign: 'middle',
            sortable: true,
        }, {
            field: 'loginName',
            title: '登录账号',
            align: 'center',
            valign: 'middle',
            sortable: true
        }, {
            field: 'password',
            title: '登录密码',
            align: 'center',
            valign: 'middle',
            sortable: true
        }, {
            field: 'sipAccount',
            title: 'sip账号',
            align: 'center',
            valign: 'middle',
            sortable: true
        }, {
            field: 'sipPassword',
            title: 'sip账号密码',
            align: 'center',
            valign: 'middle',
            sortable: true
        }]
}


	function queryParams(params) {
	   return initSearchParams("equipmentUserSearchForm",params);
	}

