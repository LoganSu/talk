//search.jsp 与controller 类@RequestMapping路径一致
//var testseachPath = "/test";
//table js
var IPManageTable ={
		method: 'get',
        url: $path+'/mc/IPManage/showList.do',
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
            field: 'platformName',
            title: '平台名称',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'neibName',
            title: '社区名称',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'platformTypeStr',
            title: '平台类型',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'ip',
            title: '访问IP地址',
            align: 'center',
            valign: 'middle',
            sortable: false
        },{
            field: 'port',
            title: 'https端口',
            align: 'center',
            valign: 'middle',
            sortable: false
        },{
            field: 'httpIp',
            title: 'http真实ip',
            align: 'center',
            valign: 'middle',
            sortable: false
        },{
            field: 'httpPort',
            title: 'http端口',
            align: 'center',
            valign: 'middle',
            sortable: false
        },{
            field: 'managementStr',
            title: '开通物业功能',
            align: 'center',
            valign: 'middle',
            sortable: false
        },{
            field: 'fsIp',
            title: 'FS IP地址',
            align: 'center',
            valign: 'middle',
            sortable: false
        },{
            field: 'fsPort',
            title: 'FS端口',
            align: 'center',
            valign: 'middle',
            sortable: false
        },{
            field: 'neiborFlag',
            title: '社区唯一标志',
            align: 'center',
            valign: 'middle',
            sortable: false
        },{
            field: 'fsExternalPort',
            title: 'fs外呼端口',
            align: 'center',
            valign: 'middle',
            sortable: false
        },{
            field: 'remark',
            title: '备注',
            align: 'center',
            valign: 'middle',
            sortable: false
        },{
            field: 'operate',
            title: '操作',
            align: 'center',
            valign: 'middle',
            sortable: true
        }]
}


	function queryParams(params) {
	   return initSearchParams("",params);
	}

