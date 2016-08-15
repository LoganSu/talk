//search.jsp 与controller 类@RequestMapping路径一致
//var testseachPath = "/test";
//table js
var roomPermissionTable ={
		method: 'get',
        url: $path+'/mc/room/showList.do',
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
            field: 'roomNum',
            title: '房号',
            align: 'center',
            valign: 'middle',
            sortable: true
        }, {
            field: 'roomFloor',
            title: '楼层',
            align: 'center',
            valign: 'middle',
            sortable: true,
        },
//        }, {
//            field: 'roomType',
//            title: '房间归属',
//            align: 'center',
//            valign: 'middle',
//            sortable: true
//        }, {
//            field: 'certificateNum',
//            title: '房产证号',
//            align: 'center',
//            valign: 'middle',
//            sortable: true
//        },{
//            field: 'purpose',
//            title: '用途',
//            align: 'center',
//            valign: 'middle',
//            sortable: true
//        }, {
//            field: 'orientation',
//            title: '朝向',
//            align: 'center',
//            valign: 'middle',
//            sortable: true
//        }, {
//            field: 'decorationStatus',
//            title: '装修情况',
//            align: 'center',
//            valign: 'middle',
//            sortable: true
//        }, {
//            field: 'roomArea',
//            title: '建筑面积',
//            align: 'center',
//            valign: 'middle',
//            sortable: true
//        }, {
//            field: 'useArea',
//            title: '使用面',
//            align: 'center',
//            valign: 'middle',
//            sortable: true
//        }, {
//            field: 'gardenArea',
//            title: '花园面积',
//            align: 'center',
//            valign: 'middle',
//            sortable: true
//        }, 
        {
            field: 'cardCount',
            title: '开卡次数',
            align: 'center',
            valign: 'middle',
            sortable: true
        }, {
            field: 'operate',
            title: '备注',
            align: 'center',
            valign: 'middle',
            sortable: false
        }]
}


	function queryParams(params) {
	   return initSearchParams("roomPermissionSearchForm",params);
	}

