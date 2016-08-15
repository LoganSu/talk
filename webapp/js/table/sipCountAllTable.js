//search.jsp 与controller 类@RequestMapping路径一致
//var testseachPath = "/test";
//table js
var sipCountAllTable ={
		method: 'get',
        url: $path+'/mc/sipCount/showAllList.do',
        cache: false,
//        height: 800,
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
            field: 'sipUser',
            title: 'sip账号',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'username',
            title: '业务账号',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'countTypeStr',
            title: '业务账号类型',
            align: 'center',
            valign: 'middle',
            sortable: false
        },{
            field: 'address',
            title: '地址',
            align: 'center',
            valign: 'middle',
            sortable: false
        }
//            ,{
//            field: 'networkIp',
//            title: '设备ip',
//            align: 'center',
//            valign: 'middle',
//            sortable: false
//        },{
//            field: 'serverHost',
//            title: '服务器ip',
//            align: 'center',
//            valign: 'middle',
//            sortable: false
//        },{
//            field: 'status',
//            title: '协议',
//            align: 'center',
//            valign: 'middle',
//            sortable: false
//        },{
//            field: 'userAgent',
//            title: '客户端类型',
//            align: 'center',
//            valign: 'middle',
//            sortable: false
//        },{
//            field: 'expiresStr',
//            title: '最后注册时间',
//            align: 'center',
//            valign: 'middle',
//            sortable: true
//        }
            ]
}


	function queryParams(params) {
	   return initSearchParams("sipCountAllSearchForm",params);
	}

