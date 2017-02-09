//search.jsp 与controller 类@RequestMapping路径一致
//var testseachPath = "/test";
//table js
var neighborhoodsTable ={
		method: 'get',
        url: $path+'/mc/neighborhoods/showList.do',
        cache: false,
        striped: true,
        pagination: true,
//        width:'2000',
        pageSize: 20,
        pageList: [20, 50, 100, 200],
        sidePagination:'server',//设置为服务器分页
        queryParams: queryParams,//查询参数
        minimumCountColumns: 2,
        clickToSelect: true,
        //双击事件
//        onDblClickRow: function (row) {
//        	alert(row.toSource());
//        	$("#showRightArea").load($path+"/mc/building/buildingListshowPage.do?module=buildingTable&modulePath=/building&parentId="+row.id);
           
//        },
        columns: [{
            field: 'state',
            checkbox: true
        }, {
            field: 'index',
            title: '序号',
            align: 'center',
            valign: 'middle',
            sortable: false
        },
//        {
//            field: 'province',
//            title: '省份',
//            align: 'center',
//            valign: 'middle',
//            sortable: true
//        }, {
//            field: 'city',
//            title: '城市',
//            align: 'center',
//            valign: 'middle',
//            sortable: true,  
//        }, 
        {
            field: 'neibName',
            title: '社区名称',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'neibNum',
            title: '呼叫号码',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'address',
            title: '社区地址',
            align: 'left',
            valign: 'middle',
            sortable: true
        },{
            field: 'phone',
            title: '电话',
            align: 'left',
            valign: 'middle',
            sortable: true
        },{
            field: 'sipNum',
            title: 'sip账号',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'sipNumPsw',
            title: 'sip账号密码',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'contractor',
            title: '承建商',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'startBuildDateStr',
            title: '开工日期',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'endBuildDateStr',
            title: '竣工日期',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'useDateStr',
            title: '小区使用日期',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'totalArea',
            title: '总占地面积(㎡)',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'totalBuildArea',
            title: '总建筑面积(㎡)',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'totalBussnisArea',
            title: '总商业面积(㎡)',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'greeningRate',
            title: '绿化率（%）',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'plotRatio',
            title: '容积率(%)',
            align: 'center',
            valign: 'middle',
            sortable: true
        }, {
            field: 'remark',
            title: '备注',
            align: 'center',
            valign: 'middle',
            sortable: false
        }]
}
 

	function queryParams(params) {
	   return initSearchParams("neighborhoodsSearchForm",params);
	}

