//search.jsp 与controller 类@RequestMapping路径一致
//var testseachPath = "/test";
//table js
var domainNameTable ={
		method: 'get',
        url: $path+'/mc/domainName/showList.do',
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
            field: 'platform',
            title: '平台',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'fname',
            title: '名称',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'domainStr',
            title: '域名',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'remark',
            title: '备注',
            align: 'center',
            valign: 'middle',
            sortable: true
        }]
}


	function queryParams(params) {
	   return initSearchParams("domainNameSearchForm",params);
	}

