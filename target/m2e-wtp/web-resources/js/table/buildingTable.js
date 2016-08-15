//search.jsp 与controller 类@RequestMapping路径一致
//var testseachPath = "/test";
//table js
var buildingTable ={
		method: 'get',
        url: $path+'/mc/building/showList.do',
        cache: false,
//        height: 600,
        striped: true,
        pagination: true,
        pageSize: 20,
        pageList: [20, 50, 100, 200],
        sidePagination:'server',//设置为服务器分页
        queryParams: queryParams,//查询参数
        minimumCountColumns: 2,
        clickToSelect: true,
        //双击事件
//        onDblClickRow: function (row) {
//        	$("#showRightArea").load($path+"/mc/unit/unitListshowPage.do?module=unitTable&modulePath=/unit&parentId="+row.parentId);
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
       }
//       ,{
//            field: 'neighbName',
//            title: '社区名称',
//            align: 'center',
//            valign: 'middle',
//            sortable: true
//        }, {
//            field: 'neibNum',
//            title: '社区编号',
//            align: 'center',
//            valign: 'middle',
//            sortable: true,
//        }
        , {
            field: 'buildingName',
            title: '楼栋名称',
            align: 'center',
            valign: 'middle',
            sortable: true
        }, {
            field: 'buildingNum',
            title: '楼栋编号',
            align: 'center',
            valign: 'middle',
            sortable: true
        }, {
            field: 'totalFloor',
            title: '层数',
            align: 'center',
            valign: 'middle',
            sortable: true
        }, {
            field: 'buildHeight',
            title: '楼高',
            align: 'center',
            valign: 'middle',
            sortable: true
        }, {
            field: 'buildType',
            title: '楼栋类型',
            align: 'center',
            valign: 'middle',
            sortable: true
        }, {
            field: 'remark',
            title: '备注',
            align: 'center',
            valign: 'middle',
            sortable: true
        }]
}


	function queryParams(params) {
//	alert(initSearchParams("buildingSearchForm",params));
	   return initSearchParams("buildingSearchForm",params);
	}

