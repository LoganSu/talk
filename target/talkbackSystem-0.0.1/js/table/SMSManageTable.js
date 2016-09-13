//search.jsp 与controller 类@RequestMapping路径一致
//var testseachPath = "/test";
//table js
var SMSManageTable ={
		method: 'get',
        url: $path+'/mc/SMSManage/showList.do',
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
            field: 'ip',
            title: 'IP地址',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'port',
            title: '端口',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'username',
            title: '用户名',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'sign',
            title: '公司签名unicode编码',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'operate',
            title: '操作',
            align: 'center',
            valign: 'middle',
            sortable: true
        }]
}


	function queryParams(params) {
	   return initSearchParams("SMSManageSearchForm",params);
	}

