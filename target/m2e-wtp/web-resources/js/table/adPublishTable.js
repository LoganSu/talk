//search.jsp 与controller 类@RequestMapping路径一致
//var testseachPath = "/test";
//table js
var adPublishTable ={
		method: 'get',
        url: $path+'/mc/adPublish/showList.do',
        cache: false,
//        height: 600,
//        width:'1000',
        striped: true,
        pagination: true,
        pageSize: 20,
        pageList: [20, 50, 100, 200],
        sidePagination:'server',//设置为服务器分页
        queryParams: phoneQueryParams,//查询参数
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
            field: 'targetDeviceStr',
            title: '终端机类型',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'selfStr',
            title: '是否本运营商发布',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'addOperator',
            title: '添加人',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'createTimeStr',
            title: '添加时间',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'expDateStr',
            title: '有效期',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'statusStr',
            title: '状态',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'publishOperator',
            title: '发布人',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'publishTimeStr',
            title: '发布时间',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'operate',
            title: '操作',
            align: 'center',
            valign: 'middle',
            sortable: false
        }]
}


	function phoneQueryParams(params) {
	var par = initSearchParams("adPublishSearchForm",params)
	   return par;
	}


