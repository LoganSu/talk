//table js
var carrierTable ={
		method: 'get',
        url: $path+'/mc/carrier/showList.do',
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
            field: 'carrierName',
            title: '名称',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'carrierNum',
            title: '运营商简称',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'tel',
            title: '手机号码',
            align: 'center',
            valign: 'middle',
            sortable: true
        }
//        ,{
//            field: 'postcode',
//            title: '邮编',
//            align: 'center',
//            valign: 'middle',
//            sortable: true
//        }
        ,{
            field: 'fax',
            title: '固定电话',
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
            field: 'remark',
            title: '备注',
            align: 'center',
            valign: 'middle',
            sortable: false
        }]
}


	function queryParams(params) {
	   return initSearchParams("carrierSearchForm",params);
	}

