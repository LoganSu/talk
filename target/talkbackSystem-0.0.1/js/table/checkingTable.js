//search.jsp 与controller 类@RequestMapping路径一致
//var testseachPath = "/test";
//table js
var checkingTable ={
		method: 'get',
        url: $path+'/mc/cardRecord/checkingshowList.do',
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
        onClickRow: function (item, $element) {
//        	var url = $path;
        	$.post($path+"/mc/appRecord/getImg.do","id="+item.id,function($data){
//        		   url += $data.path;
        		   //清空历史图片
        		   $("#personshowImg").html("");
        		   $("#personshowImg").append('<img alt="" style="margin-top: 0px;margin-left: 0px;" height="200px" src="'+$data.serveraddr+$data.path+'">');
        		})
//        	if(item.id==1){
//        		url=$path+"/img/qq.jpg"
//        	}
//        	if(item.id==2){
//        		url=$path+"/img/11.jpeg"
//        	}
//        	if(item.id==4){
//        		url=$path+"/img/22.png"
//        	}
            return false;
        },
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
            width: '10',
            valign: 'middle',
            sortable: false
        },{
            field: 'fdate',
            title: '日期',
            align: 'center',
            valign: 'middle',
            sortable: false
        },{
            field: 'ftime',
            title: '时间',
            align: 'center',
            valign: 'middle',
            sortable: false
        },{
            field: 'fname',
            title: '姓名',
            align: 'center',
            valign: 'middle',
            sortable: true
        },{
            field: 'workerNum',
            title: '工号',
            align: 'center',
            valign: 'middle',
            sortable: true
        }]
}


	function queryParams(params) {
	   return initSearchParams("checkingSearchForm",params);
	}

