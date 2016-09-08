//search.jsp 与controller 类@RequestMapping路径一致
//var testseachPath = "/test";
//table js
var usersTable ={
		method: 'get',
        url: $path+'/mc/users/showList.do',
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
            field: 'username',
            title: '用户名',
            align: 'center',
            width: '60',
            valign: 'middle',
            sortable: true
        },{
            field: 'mobilePhone',
            title: '手机号',
            align: 'center',
            width: '60',
            valign: 'middle',
            sortable: true
        },{
            field: 'statusStr',
            title: '用户状态',
            align: 'center',
            width: '60',
            valign: 'middle',
            sortable: true
        },{
            field: 'email',
            title: '电子邮箱',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'emailStatusStr',
            title: '电子邮箱状态',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'realName',
            title: '真实姓名',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'createTimeStr',
            title: '注册时间',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'oprator',
            title: '操作',
            align: 'center',
            valign: 'middle',
            sortable: false
        }]
}


	function queryParams(params) {
	   return initSearchParams("usersSearchForm",params);
	}

