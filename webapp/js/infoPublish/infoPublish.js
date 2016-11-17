$(function(){
	//添加、修改
	$(document).on("click",".infoPulishAdd",function(){
		var url =$path+"/mc/infoPublish/toSaveOrUpdate.do";
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
			 if(selects[0].publishOperator){
				 hiAlert("提示","已发布内容不能修改！");
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
	
	
	//发布
	$(document).on("click",".infoPulishPush",function(){
		var url =$path+"/mc/infoPublish/publish.do";
		var but = $(this);
		var title = $(this).html();
		//获取选择的id
		var selects = $("#tableShowList").bootstrapTable('getSelections');
		//ids= 长度小于4说明没有id
		 if(selects.length!=1){
			 hiAlert("提示","请选择一条操作数据！");
			 return false;
		 }
		 var ids = getSelectedIds();
		 but.scojs_confirm({
		        content: "您确定要发布吗？",
		        param:ids,
		        action: function(param) {
		        	alert("发布");
		            $.post(url,param,function($data){
		            	if(!$data){
							refresh();
						}else{
							hiAlert("提示",$data);
						}
		            });
		            
		        }
		      });	
	});
	
	
	//行内详情
	$(document).on("click",".infoPublishDetail",function(){
		var url =$path+"/mc/infoPublish/toSaveOrUpdate.do";
		var id = $(this).attr("rel");
		var data="ids="+id+"&opraterType=1";
		$.post(url,data,function(addHtml){
			//设置标题
            $("#unnormalModalLabel").html("详情");
			$("#unnormalModal .modal-body").html(addHtml);
			$("#unnormalModal").modal("show");
		})
	});
	
	
	//行内修改
	$(document).on("click",".infoPublishUpdate",function(){
		var url =$path+"/mc/infoPublish/toSaveOrUpdate.do";
		var id = $(this).attr("rel");
		var data="ids="+id;
		$.post(url,data,function(addHtml){
			//设置标题
            $("#unnormalModalLabel").html("修改");
			$("#unnormalModal .modal-body").html(addHtml);
			$("#unnormalModal").modal("show");
		})
	});
	
	
	//行内删除
//	$(document).on("click",".infoPublishDelete",function(){
//		var id = $(this).attr("rel");
//		var url =$path+"/mc/infoPublish/delete.do?ids="+id;
//		if(confirm("确定要删除该数据吗？")){
//			//获取已选择记录id
//			$.post(url,function($data){
//				if(!$data){
//					refresh();
//				}else{
//					hiAlert("提示",$data);
//				}
//			})
//		}
//	 })
	 
	 //删除按钮
	 $(document).on("click",".infoPublishDelete",function(){
			 var selects = $("#tableShowList").bootstrapTable('getSelections');
			 var but = $(this);
				//ids= 长度小于4说明没有id
				 if(selects.length<1){
					 hiAlert("提示","请选择一条操作数据！");
					 return false;
				 }
				 for(var i=0;i<selects.length;i++){
					 var item = selects[i];
//					 alert(item.selfStr);
					if(item.selfStr=="否"){
						 hiAlert("提示","请选择本运营商发布的公告删除！");
						 return false;
					}
				 }
				 var ids = getSelectedIds();
				var url = $path+"/mc/infoPublish/delete.do";
				but.scojs_confirm({
			        content: "您确定要删除记录吗？",
			        param:ids,
			        action: function(param) {
			        	alert("删除");
			            $.post(url,param,function($data){
			            	if(!$data){
								refresh();
							}else{
								hiAlert("提示",$data);
							}
			            });
			            
			        }
			      });
		 })
		 
})