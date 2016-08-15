//search.jsp 与controller 类@RequestMapping路径一致
//var testseachPath = "/test";
//table js
var cardTable ={
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
            field: 'cardSn',
            title: '卡片序列',
            align: 'center',
            valign: 'middle',
            sortable: true
        }, {
            field: 'cardNo',
            title: '卡片编号',
            align: 'center',
            valign: 'middle',
            sortable: true
        }, 
//        {
//            field: 'frDate',
//            title: '生效日期',
//            align: 'center',
//            valign: 'middle',
//            sortable: true,
//        },{
//            field: 'toDate',
//            title: '截至日期',
//            align: 'center',
//            valign: 'middle',
//            sortable: true
//        },
        {
            field: 'cardTypeStr',
            title: '卡片类型',
            align: 'center',
            valign: 'middle',
            sortable: true
        }, {
            field: 'cardStatusStr',
            title: '卡片状态',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'address',
            title: '授权地址',
            align: 'center',
            valign: 'middle',
            sortable: false
        },
//        {
//            field: 'idNum',
//            title: '持卡人身份证',
//            align: 'center',
//            valign: 'middle',
//            sortable: false
//        },{
//            field: 'phone',
//            title: '持卡人电话',
//            align: 'center',
//            valign: 'middle',
//            sortable: false
//        },{
//            field: 'detail',
//            title: '操作',
//            align: 'center',
//            valign: 'middle',
//            sortable: false
//        }]
        ]
}
	function queryParams(params) {
	   return initSearchParams("cardSearchForm",params);
	}

