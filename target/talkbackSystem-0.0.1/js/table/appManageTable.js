//search.jsp 与controller 类@RequestMapping路径一致
//var testseachPath = "/test";
//table js
var phoneAppManageTable ={
		method: 'get',
        url: $path+'/mc/appManage/showList.do',
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
            field: 'appName',
            title: 'APP名称',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'versionName',
            title: '版本名称',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'versionCode',
            title: '版本号',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'packageName',
            title: '包名',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'appSize',
            title: '软件大小（KB）',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'versionDes',
            title: '版本说明',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'createTimeStr',
            title: '上传时间',
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
	   return initSearchParams("appManageSearchForm",params);
	}


