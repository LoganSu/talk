//search.jsp 与controller 类@RequestMapping路径一致
//var testseachPath = "/test";
//table js
var staticParamTable ={
		method: 'get',
        url: $path+'/mc/staticParam/showList.do',
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
            field: 'fdescr',
            title: '参数描述',
            align: 'center',
            width: '60',
            valign: 'middle',
            sortable: true
        },{
            field: 'fkey',
            title: '参数唯一key',
            align: 'center',
            width: '60',
            valign: 'middle',
            sortable: true
        },{
            field: 'fvalue',
            title: '参数值',
            align: 'center',
            width: '60',
            valign: 'middle',
            sortable: true
        }]
}


	function queryParams(params) {
	   return initSearchParams("staticParamSearchForm",params);
	}

