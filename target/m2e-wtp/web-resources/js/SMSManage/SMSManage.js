$(function(){
	//添加、修改
	$(document).on("click",".appManageAdd",function(){
		var url =$path+"/mc/appManage/toSaveOrUpdate.do";
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
		}else{
			var appType = $("#appManageAppType").val();
			data="appType="+appType;
		}
		$.post(url,data,function(addHtml){
			//设置标题
            $("#unnormalModalLabel").html(title);
			$("#unnormalModal .modal-body").html(addHtml);
			$("#unnormalModal").modal("show");
		})
	});
	
	//白名单
	$(document).on("click",".SMSManageWhiteList",function(){
	})
	
	
})