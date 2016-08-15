//search.jsp 与controller 类@RequestMapping路径一致
//var testseachPath = "/test";
//table js
//保存显示记录id
var arr = new Array();
var dwellerTable ={
		method: 'get',
        url: $path+'/mc/dweller/showList.do',
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
        rowAttributes: function (row, index) {
        	//添加id
        	arr.push(row.id);
            return {};
        },
        onLoadSuccess: function (data) {
        	if(arr.length>0){
        		var newArray = arr.sort();
        		//不显示重复住户的多选框
        		$.each(arr,function(i,obj){
        			if(newArray[i]===newArray[i+1]){
        				$("#"+newArray[i+1]).remove();
        			}
        		})
        		arr = new Array();
        	}
            return false;
        },
        idField:"id",
        columns: [{
            field: 'statu',
            checkbox: true
        }, {
            field: 'index',
            title: '序号',
            width: '30',
            align: 'center',
            valign: 'middle',
            sortable: false
        }, {
            field: 'fname',
            title: '姓名',
            width: '80',
            align: 'center',
            valign: 'middle',
            sortable: true
        }, {
            field: 'idNum',
            title: '身份证号',
            width: '60',
            align: 'center',
            valign: 'middle',
            sortable: true
        }, {
            field: 'phone',
            title: '联系电话',
            width: '60',
            align: 'center',
            valign: 'middle',
            sortable: true,
        }, {
            field: 'dwellerTypeStr',
            title: '住户类型',
            width: '60',
            align: 'center',
            valign: 'middle',
            sortable: true,
        }, {
            field: 'address',
            title: '房屋地址',
            align: 'center',
            valign: 'middle',
            sortable: false
        },{
            field: 'email',
            title: '邮箱',
            width: '60',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'educationStr',
            title: '文化程度',
            align: 'center',
            width: '40',
            valign: 'middle',
            sortable: true
        }, {
            field: 'nativePlace',
            title: '籍贯',
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
	   return initSearchParams("dwellerSearchForm",params);
	}

