$(function(){
	$(document).on("click",".IPManageDetail",function(){
		var id = $(this).attr("rel");
		$.post($path+"/mc/IPManage/toSaveOrUpdate.do","ids="+id,function(addHtml){
			//设置标题
            $("#noSureModallLabel").html("详情");
			$("#noSureModal .modal-body").html(addHtml);
			$("#noSureModal").modal("show");
		});
	})
})