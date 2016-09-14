//search.jsp 与controller 类@RequestMapping路径一致
//var testseachPath = "/test";
//table js
var deviceCountTable ={
		method: 'get',
        url: $path+'/mc/deviceCount/showList.do',
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
            field: 'deviceCount',
            title: '账号',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'countTypeStr',
            title: '账户类型',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'countStatusStr',
            title: '账户状态',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'address',
            title: '地址',
            align: 'center',
            valign: 'middle',
            sortable: false
        },{
            field: 'longitude',
            title: '经度',
            align: 'center',
            valign: 'middle',
            sortable: false
        },{
            field: 'latitude',
            title: '纬度',
            align: 'center',
            valign: 'middle',
            sortable: false
        },{
            field: 'warnPhone',
            title: '告警电话',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'warnEmail',
            title: '告警邮箱',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'endTime',
            title: '部署截止时间',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'orderNumStr',
            title: '最新工单号',
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
	   return initSearchParams("deviceCountSearchForm",params);
	}

