//search.jsp 与controller 类@RequestMapping路径一致
//var testseachPath = "/test";
//table js
var deviceTable ={
		method: 'get',
        url: $path+'/mc/device/showList.do',
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
            field: 'id',
            title: '设备SN码',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'deviceStatusStr',
            title: '设备状态',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'liveTimeStr',
            title: '激活时间',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'createTimeStr',
            title: '登记时间',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'deviceNum',
            title: '设备编号',
            align: 'center',
            width: '60',
            valign: 'middle',
            sortable: true
        },{
            field: 'deviceModel',
            title: '设备型号',
            align: 'center',
            width: '60',
            valign: 'middle',
            sortable: true
        },{
            field: 'app_version',
            title: '应用版本',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'memory_size',
            title: '内存大小',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'storage_capacity',
            title: '储存容量',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'system_version',
            title: '系统版本',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'versionNum',
            title: '版本号',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'softwareType',
            title: '软件型号',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'remark',
            title: '备注',
            align: 'center',
            valign: 'middle',
            sortable: true
        }
//        ,{
//            field: 'oprator',
//            title: '操作',
//            align: 'center',
//            valign: 'middle',
//            sortable: false
//        }
        ]
}


	function queryParams(params) {
	   return initSearchParams("deviceSearchForm",params);
	}

