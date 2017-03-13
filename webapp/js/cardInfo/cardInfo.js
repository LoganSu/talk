$(function(){
	//开卡
	$(document).on("click",".cardInfoOpenCard",function(){
		var domainId = $(this).attr("rel");
//		var oprType = $(this).attr("oprType");
		var url =$path+"/mc/permission/toOpenCard.do";
		$.post(url,"domainId="+domainId,function(addHtml){
			//设置标题
            $("#unnormalModalLabel").html("发卡");
			$("#unnormalModal .modal-body").html(addHtml);
			$("#unnormalModal").modal({backdrop: 'static', keyboard: false});
		})
	});
	//挂失
	$(document).on("click",".cardInfoLoss",function(){
		var domainId = $(this).attr("rel");
		var cardStatus = $(this).attr("cardStatus");
		cardInfoLossUnlossDestroy(domainId, cardStatus);
	})
	//解挂
	$(document).on("click",".cardInfoUnloss",function(){
		var domainId = $(this).attr("rel");
		var cardStatus = $(this).attr("cardStatus");
		cardInfoLossUnlossDestroy(domainId, cardStatus);
	})
	//注销
	$(document).on("click",".cardInfoDestroy",function(){
		var domainId = $(this).attr("rel");
		var cardStatus = $(this).attr("cardStatus");
		cardInfoLossUnlossDestroy(domainId, cardStatus);
	})
	
	//卡片授权地址
	$(document).on("click",".cardInfoPermission",function(){
		var cardSn = $(this).attr("rel");
		var url =$path+"/mc/permission/cardInfoPermission.do";
		$.post(url,"cardSn="+cardSn,function(addHtml){
            $("#unnormalModalLabel").html("授权地址");
			$("#unnormalModal .modal-body").html(addHtml);
			$("#unnormalModal").modal({backdrop: 'static', keyboard: false});
		})
	})
})

//挂失、解挂、注销公共方法
function cardInfoLossUnlossDestroy(domainId,cardStatus){
	var url =$path+"/mc/permission/cardInfoLossUnlossDestroy.do";
	$.post(url,"domainId="+domainId+"&cardStatus="+cardStatus,function(addHtml){
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