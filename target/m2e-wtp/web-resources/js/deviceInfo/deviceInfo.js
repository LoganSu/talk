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
//		 $("#tableShowList").bootstrapTable('refresh', {
//			 url: $path+"/mc/device/showList.do",
//		 });
	})
})