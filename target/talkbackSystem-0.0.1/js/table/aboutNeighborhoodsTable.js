//search.jsp 与controller 类@RequestMapping路径一致
//var testseachPath = "/test";
//table js
var aboutNeighborhoodsTable ={
		method: 'get',
        url: $path+'/mc/aboutNeighborhoods/showList.do',
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
            field: 'headline',
            title: '栏目标题',
            align: 'center',
            valign: 'middle',
            sortable: false
        },{
            field: 'order',
            title: '排序',
            align: 'center',
            valign: 'middle',
            sortable: false
        },{
            field: 'createTimeStr',
            title: '创建时间',
            align: 'center',
            valign: 'middle',
            sortable: false
        },{
            field: 'updateTimeStr',
            title: '更新时间',
            align: 'center',
            valign: 'middle',
            sortable: false
        },{
            field: 'statusStr',
            title: '状态',
            align: 'center',
            valign: 'middle',
            sortable: false
        },{
            field: 'version',
            title: '版本',
            align: 'center',
            valign: 'middle',
            sortable: false
        },{
            field: 'operate',
            title: '操作',
            align: 'center',
            valign: 'middle',
            sortable: false
        }]
}


	function queryParams(params) {
	   return initSearchParams("aboutNeighborhoodsSearchForm",params);
	}

