$(function(){
	//设置密码
	$(document).on("click",".deviceInfoSetPws",function(){
		var id = $(this).attr("rel");
		var url =$path+"/mc/device/toDeviceInfoSetPws.do";
		$.post(url,"id="+id,function(addHtml){
			//设置标题
            $("#unnormalModalLabel").html("设置密码");
			$("#unnormalModal .modal-body").html(addHtml);
			$("#unnormalModal").modal("show");
		})
	});
     //导入
	$(document).on("click",".importDeviceInfo",function(){
		 $("#importDeviceInfoForm").submit();
		 $(this).attr("disabled", true);
	})
	
	//导出
	$(document).on("click",".exportDeviceInfo",function(){
		var ids = getSelectedIds();
		if(ids.length<5){
			hiAlert("提示","请选择需要导出的数据");
			return false;
		}
		window.location.href=$path+"/mc/device/singleDownfile.do?"+ids;
	})
})