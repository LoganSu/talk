$(function(){
	//开卡
	$(document).on("click",".workerOpenCard",function(){
		var workerId = $(this).attr("rel");
		var url =$path+"/mc/worker/toOpenCard.do";
		$.post(url,"workerId="+workerId,function(addHtml){
			//设置标题
            $("#unnormalModalLabel").html("发卡");
			$("#unnormalModal .modal-body").html(addHtml);
			$("#unnormalModal").modal({backdrop: 'static', keyboard: false});
		})
	});
	
	
	//保存卡片信息
//	 $(document).on("click","#workerOpenCardDiv .sure",function(){
//		var param = $("#workerOpenCardForm").serialize();
//		var cardSn = $("#workerOpenCardForm .cardSn").val();
//		if(cardSn.length<1){
//			hiAlert("提示","请链接发卡器！");
//			return false;
//		}
//		if(zTreeObj){
//			 var nodes = zTreeObj.getCheckedNodes(true);
//			 if(nodes.length!=1){
//				 hiAlert("提示","请选择一个域！");
//					return false;
//			 }
//				 
//			 param=param+"&domainId="+nodes[0].id;
//	      }
//		//保存卡片信息
//		$.post($path+"/mc/worker/writeCard.do?a="+Math.random(),param,function($data){
//			if($data=="1"){
//		       hiAlert("提示","该卡已经在该区域授权，请更换新卡！");
//		       return false;
//			}else if($data=="0"){
//				  hiAlert("提示","开卡成功！");
//			}else if($data=="2"){
//				hiAlert("提示","开卡失败！");
//			}else if($data=="3"){
//				hiAlert("提示","此卡已经与该员工绑定，请更换新卡！");
//			}
//		});
//		
//	 });
//	
	
	
	
	
	
	//挂失
	$(document).on("click",".workerLoss",function(){
		var workerId = $(this).attr("rel");
		var cardStatus = $(this).attr("cardStatus");
		workerLossUnlossDestroy(workerId, cardStatus);
	})
	//解挂
	$(document).on("click",".workerUnloss",function(){
		var workerId = $(this).attr("rel");
		var cardStatus = $(this).attr("cardStatus");
		workerLossUnlossDestroy(workerId, cardStatus);
	})
	//注销
	$(document).on("click",".workerDestroy",function(){
		var workerId = $(this).attr("rel");
		var cardStatus = $(this).attr("cardStatus");
		workerLossUnlossDestroy(workerId, cardStatus);
	})
	
})

//挂失、解挂、注销公共方法
function workerLossUnlossDestroy(workerId,cardStatus){
	var url =$path+"/mc/worker/workerLossUnlossDestroy.do";
	$.post(url,"workerId="+workerId+"&cardStatus="+cardStatus,function(addHtml){
		var title = "";
		//设置标题
        if(cardStatus=="2"){
        	title="挂失";
        }else if(cardStatus=="1"){
        	title="解挂";
        }else if(cardStatus=="3"){
        	title="注销";
        }
        $("#unnormalModalLabel").html(title);
		$("#unnormalModal .modal-body").html(addHtml);
		$("#unnormalModal").modal({backdrop: 'static', keyboard: false});
	})
}