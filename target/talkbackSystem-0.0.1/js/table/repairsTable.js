//search.jsp 与controller 类@RequestMapping路径一致
//var testseachPath = "/test";
//table js
var repairsTable ={
		method: 'get',
        url: $path+'/mc/repairs/showList.do',
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
            field: 'orderNum',
            title: '编号',
            align: 'center',
            width: '60',
            valign: 'middle',
            sortable: true
        },{
            field: 'linkman',
            title: '联系人',
            align: 'center',
            width: '60',
            valign: 'middle',
            sortable: true
        },{
            field: 'phone',
            title: '联系电话',
            align: 'center',
            width: '60',
            valign: 'middle',
            sortable: true
        },{
            field: 'address',
            title: '客户地址',
            align: 'center',
            valign: 'middle',
            sortable: false
        },{
            field: 'comeFromStr',
            title: '来源',
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
            field: 'createTimeStr',
            title: '提交时间',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'serviceContent',
            title: '服务内容',
            align: 'center',
            valign: 'middle',
            sortable: false
        },{
            field: 'workerName',
            title: '处理人',
            align: 'center',
            valign: 'middle',
            sortable: false
        },{
            field: 'dealTimeStr',
            title: '处理时间',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'dealResult',
            title: '处理结果',
            align: 'center',
            valign: 'middle',
            sortable: false
        }]
}


	function queryParams(params) {
	   return initSearchParams("repairsSearchForm",params);
	}

