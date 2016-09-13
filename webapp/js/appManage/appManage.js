$(function(){
	//添加、修改
	$(document).on("click",".SMSManageAdd",function(){
		var url =$path+"/mc/SMSManage/toSaveOrUpdate.do";
		var title = $(this).html();
		var data="";
		//修改保存共用（修改需要传参数）
		if(title=="修改"){
			//获取选择的id
			 data= getSelectedIds();
			var selects = $("#tableShowList").bootstrapTable('getSelections');
			//ids= 长度小于4说明没有id
			 if(selects.length!=1){
				 hiAlert("提示","请选择一条操作数据！");
				 return false;
			 }
		}
		$.post(url,data,function(addHtml){
			//设置标题
            $("#unnormalModalLabel").html(title);
			$("#unnormalModal .modal-body").html(addHtml);
			$("#unnormalModal").modal("show");
		})
	});
	
	
	//行内修改
	$(document).on("click",".appmanageUpdate",function(){
		var url =$path+"/mc/appManage/toSaveOrUpdate.do";
		var id = $(this).attr("rel");
		var appType = $("#appManageAppType").val();
		var data="ids="+id+"&appType="+appType;
		$.post(url,data,function(addHtml){
			//设置标题
            $("#unnormalModalLabel").html("修改");
			$("#unnormalModal .modal-body").html(addHtml);
			$("#unnormalModal").modal("show");
		})
	});
	//行内详情
	$(document).on("click",".appmanageDetail",function(){
		var url =$path+"/mc/appManage/toSaveOrUpdate.do";
		var id = $(this).attr("rel");
		var appType = $("#appManageAppType").val();
		var data="ids="+id+"&appType="+appType+"&opraterType=1";
		$.post(url,data,function(addHtml){
			//设置标题
            $("#unnormalModalLabel").html("详情");
			$("#unnormalModal .modal-body").html(addHtml);
			$("#unnormalModal").modal("show");
		})
	});
	
	//行内删除
	$(document).on("click",".appmanageDelete",function(){
		var id = $(this).attr("rel");
		var url =$path+"/mc/appManage/delete.do";
		if(confirm("确定要删除该数据吗？")){
			//获取已选择记录id
			$.post(url,"ids="+id,function($data){
				if(!$data){
					refresh();
				}else{
					hiAlert("提示",$data);
				}
			})
		}
	 })
	 
	 //行内删除
//	$(document).on("click",".appmanageSingleDown",function(){
//		var id = $(this).attr("rel");
//		var url =$path+"/mc/appManage/singleDown.do?id="+id;
//		$.ajax(url);
//	 })
})