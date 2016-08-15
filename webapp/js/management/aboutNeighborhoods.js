$(function(){
	//添加、修改
	$(document).on("click",".aboutNeighborhoodsSaveOrUpdateBtn",function(){
		var url =$path+"/mc/aboutNeighborhoods/toSaveOrUpdate.do";
		var neighborDomainId=$("#aboutNeighborhoodsSearchForm .hiddenId").val();
		var title = $(this).html();
		var data="neighborDomainId="+neighborDomainId;
		//修改保存共用（修改需要传参数）
		if(title=="修改"){
			data= getSelectedIds();
			//获取选择的id
			var selects = $("#tableShowList").bootstrapTable('getSelections');
//			alert(selects.toSource());
//			alert(selects[0].publishOperator);
			//ids= 长度小于4说明没有id
			 if(selects.length!=1){
				 hiAlert("提示","请选择一条操作数据！");
				 return false;
			 }
			 if(selects[0].status=='3'){
				 hiAlert("提示","已发布内容不能修改！");
				 return false;
			 }
//			 var getdata = $("#tableShowList").bootstrapTable('getSelections');
//			 alert(getdata[0].toSource());
//			 alert(getdata[0].addOperator);
//			 if(getdata[0].addOperator){
//				 hiAlert("提示","已发布内容不能修改！");
//				 return false;
//			 }
		}
		$.post(url,data,function(addHtml){
			//设置标题
            $("#unnormalModalLabel").html(title);
			$("#unnormalModal .modal-body").html(addHtml);
			$("#unnormalModal").modal("show");
		})
	});
	
	//上移箭头
	$(document).on("click",".aboutNeighborhoodsUp",function(){
		var url =$path+"/mc/aboutNeighborhoods/orderUp.do";
		var id =$(this).attr("rel");
		var forder = $(this).attr("forder");
		var neighborDomainId=$("#aboutNeighborhoodsSearchForm .hiddenId").val();
		var data = "id="+id+"&forder="+forder+"&neighborDomainId="+neighborDomainId;
		$.post(url,data,function($msg){
			 if($msg){
				 hiAlert("提示",$msg);
			 }else{
				 $("#tableShowList").bootstrapTable('refresh', {
					 url: $path+"/mc/aboutNeighborhoods/showList.do",
				 });
			 }
		})
	})
	
	//上移箭头
	$(document).on("click",".aboutNeighborhoodsDown",function(){
		var url =$path+"/mc/aboutNeighborhoods/orderDown.do";
		var id =$(this).attr("rel");
		var forder = $(this).attr("forder");
		var neighborDomainId=$("#aboutNeighborhoodsSearchForm .hiddenId").val();
		var data = "id="+id+"&forder="+forder+"&neighborDomainId="+neighborDomainId;
		$.post(url,data,function($msg){
			 if($msg){
				 hiAlert("提示",$msg);
			 }else{
				 $("#tableShowList").bootstrapTable('refresh', {
					 url: $path+"/mc/aboutNeighborhoods/showList.do",
				 });
			 }
		})
	})
	
	//跳转到备注按钮
	$(document).on("click",".aboutNeighborhoodsCheckBtn",function(){
		//获取选择的id
		var selects = $("#tableShowList").bootstrapTable('getSelections');
		var status= $(this).val();
		//ids= 长度小于4说明没有id
		 if(selects.length!=1){
			 hiAlert("提示","请选择一条操作数据！");
			 return false;
		 }
		var url =$path+"/mc/aboutNeighborhoods/toRemarkPage.do";
		var title="";
		var id =selects[0].id;
		var oldStatus =selects[0].status;
		//取消发布
		 if("1"==status&&oldStatus!="3"){
			 hiAlert("提示","已发布状态才能取消发布");
			 return false;
			//待审核
		 }else if("2"==status&&oldStatus!='1'&&oldStatus!='4'){
			 hiAlert("提示","未发布或者撤回状态才能发布");
			 return false;
			//审核通过	
		 }else if("3"==status&&oldStatus!='2'){
			 hiAlert("提示","未审核状态才能审核通过");
			 return false;
			//撤回	
		 }else if("4"==status&&oldStatus!='2'){
			 hiAlert("提示","未审核状态才能撤回");
			 return false;
		 }
		 if("1"==status){
			 title="取消发布"; 
		 }else if("2"==status){
			 title="发布"; 
		 }else if("3"==status){
			 title="审核通过"; 
		 }else if("4"==status){
			 title="撤回"; 
		 }
		$.post(url,"id="+id+"&status="+status,function(remarkPage){
			//设置标题
            $("#unnormalModalLabel").html(title);
			$("#unnormalModal .modal-body").html(remarkPage);
			$("#unnormalModal").modal("show");
		})
		
	})
	//备注页面确定按钮
	$(document).on("click","#aboutNeighborhoodsRemarkDiv .sure",function(){
		var data = $("#aboutNeighborhoodsRemarkForm").serialize();
		var url =$path+"/mc/aboutNeighborhoods/updateCheck.do";
		$.post(url,data,function($msg){
			if($msg){
				hiAlert("提示",$msg);
			}else{
				$("#unnormalModal").modal("hide");
				$("#tableShowList").bootstrapTable('refresh', {
					 url: $path+"/mc/aboutNeighborhoods/showList.do",
				 });
			}
		})
	})
	
	//预览
	$(document).on("click",".aboutNeighborhoodsDetail",function(){
		var id = $(this).attr("rel");
		var url =$path+"/mc/aboutNeighborhoods/showHtml.do";
		$.post(url,"id="+id,function(html){
			//设置标题
            $("#unnormalModalLabel").html("预览");
			$("#unnormalModal .modal-body").html(html);
			$("#unnormalModal").modal("show");
		})
	})
	//查看审核记录
	$(document).on("click",".aboutNeighborhoodsRemark",function(){
		var id = $(this).attr("rel");
		var url =$path+"/mc/aboutNeighborhoods/showRemark.do";
		$.post(url,"id="+id,function(html){
			//设置标题
            $("#unnormalModalLabel").html("审核记录");
			$("#unnormalModal .modal-body").html(html);
			$("#unnormalModal").modal("show");
		})
	})
	
	//添加首页图片
	$(document).on("click",".aboutNeighborhoodsAddFirstPageBtn",function(){
		var data = $("#aboutNeighborhoodsSearchForm").serialize();
		var url =$path+"/mc/aboutNeighborhoods/toAddFirstPage.do";
		$.post(url,data,function(html){
			//设置标题
            $("#unnormalModalLabel").html("添加首页图片");
			$("#unnormalModal .modal-body").html(html);
			$("#unnormalModal").modal("show");
		})
		
	})
	//删除
	 $(document).on("click","#firstPageShowDiv .btn-danger",function(){
 		var $this=$(this);
 		var picId = $this.parent().parent().parent().children("input[name='picId']").val();
 		if(picId){
 			$.post($path+'/mc/adPublish/deletePic.do','picId='+picId,function($data){
 				if($data){
 					hiAlert("提示",$data);
 				}else{
 					$this.parent().parent().parent().remove();
 				}
 				
 			});
 		}
 	 })
 	 
// 	  $(document).on("click","#addNeighborhoodsFirstPageDiv .sure",function(){
// 	    	var data = $("#addNeighborhoodsFirstPageForm").serialize();
// 	    	$.post($path+"/mc/aboutNeighborhoods/updateFirstPic.do",data,function($data){
// 	    		if($data){
// 	    			hiAlert("提示",$data);
// 	    			return false;
// 	    		}
// 	    		var url =$(".search").attr("rel");
// 	    		$("#tableShowList").bootstrapTable('refresh', {
// 	    			url: url,
// 	    		});
// 	    		$("#unnormalModal").modal("hide");
// 	    	});
// 	    })
	
})