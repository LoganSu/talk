//search.jsp 与controller 类@RequestMapping路径一致
//var testseachPath = "/test";
//table js
var billManageTable ={
		method: 'get',
        url: $path+'/mc/billManage/showList.do',
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
            field: 'billNum',
            title: '账单号',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'fname',
            title: '姓名',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'address',
            title: '地址',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'phone',
            title: '联系电话',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'typeStr',
            title: '类型',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'money',
            title: '金额',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'startTimeStr',
            title: '计费开始时间',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'endTimeStr',
            title: '计费结束时间',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'status',
            title: '状态',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'payPlatform',
            title: '缴费平台',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'payTimeStr',
            title: '缴费时间',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'desc',
            title: '说明',
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
	   return initSearchParams("billManageSearchForm",params);
	}

