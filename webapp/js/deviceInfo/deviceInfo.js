$(function(){
	//设置密码
	$(document).on("click",".deviceInfoSetPws",function(){
		var id = $(this).attr("rel");
		var url =$path+"/mc/device/toDeviceInfoSetPws.do";
		$.post(url,"id="+id,function(addHtml){
			//设置标题
            $("#unnormalModalLabel").html("设置密码");
			$("#unnormalModal .modal-body").html(addHtml);
			$("#unnormalModal").modal({backdrop: 'static', keyboard: false});
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
	
	
	
	  //激活按钮
	 $(document).on("click",".setLive",function(){
			 var selects = $("#tableShowList").bootstrapTable('getSelections');
			 var but = $(this);
				//ids= 长度小于4说明没有id
				 if(selects.length<1){
					 hiAlert("提示","请选择操作数据！");
					 return false;
				 }
//				 for(var i=0;i<selects.length;i++){
//					 var item = selects[i];
//					 alert(item.selfStr);
//					if(item.selfStr=="否"){
//						 hiAlert("提示","请选择本运营商发布的广告删除！");
//						 return false;
//					}
//				 }
				 var ids = getSelectedIds();
				var url = $path+"/mc/device/setLive.do";
				bootstrapQ.confirm('<span style="color:black;font-size:16px">您确定要激活设备吗？</span>',function(){
					 $.post(url,ids,function($data){
			            	if(!$data){
								refresh();
							}else{
								hiAlert("提示",$data);
							}
			            });
				},function(){
//					alert('点击了取消');
				});
				
		 })
	
})