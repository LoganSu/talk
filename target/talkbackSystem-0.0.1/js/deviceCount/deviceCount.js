$(function(){
	//设置密码
	$(document).on("click",".deviceCountSetPsw",function(){
		var id = $(this).attr("rel");
		var url =$path+"/mc/deviceCount/toSetPsw.do";
		$.post(url,"id="+id,function(addHtml){
			//设置标题
            $("#unnormalModalLabel").html("设置密码");
			$("#unnormalModal .modal-body").html(addHtml);
			$("#unnormalModal").modal("show");
		})
	 })
	//修改
	$(document).on("click",".deviceCountUpdate",function(){
		var id = $(this).attr("rel");
		var url =$path+"/mc/deviceCount/toSaveOrUpdate.do";
		//获取已选择记录id
		$.post(url,"ids="+id,function($data){
			//设置标题
            $("#myModalLabel").html("修改");
			$("#myModal .modal-body").html($data);
			$("#myModal").modal("show");
		})
	 })
		//开卡
		$(document).on("click",".sendOrders",function(){
//			var deviceCountId = $(this).attr("rel");
			//获取选择的id
			 data= getSelectedIds();
			var selects = $("#tableShowList").bootstrapTable('getSelections');
			//ids= 长度小于4说明没有id
			 if(selects.length!=1){
				 hiAlert("提示","请选择一条操作数据！");
				 return false;
			 }
			var url =$path+"/mc/deviceCount/toSendOrders.do";
			$.post(url,data,function(addHtml){
				//设置标题
	            $("#unnormalModalLabel").html("派单");
				$("#unnormalModal .modal-body").html(addHtml);
				$("#unnormalModal").modal("show");
			})
		});
	$(document).on("click","#deviceCountPrint",function(){
		   var img = $("#showQRcode img").attr("src")
		   if(!img){
			   hiAlert("提示","请先生成二维码再打印");
			   return false;
		   }
		   var datepicker =$("#deviceCountSendOrdersForm .datepicker").val();
		   var address = $("#deviceCountSendOrdersForm .address").val();
		   var deviceCount = $("#deviceCountSendOrdersForm .deviceCount").val();
		   var printPerson = $("#deviceCountSendOrdersForm .printPerson").val();
		   var printTime = $("#deviceCountSendOrdersForm .printTime").val();
		   var countType = $("#deviceCountSendOrdersForm .countType").val();
		   var orderNum = $("#deviceCountSendOrdersForm .orderNum").val();
		   var countTypeStr,remark;
		   if(countType=="1"){
			   countTypeStr="门口机";
			   remark="进入开发人员模式，在界面输入*231*852*798**后再按拨号键进入"
		   }else if(countType=="3"){
			   countTypeStr="管理机";
			   remark="";
		   }
		    var oPop = window.open('','oPop');
		    var str = '<!DOCTYPE html>'  
		        str +='<html>'  
		        str +='<head>'  
		        str +='<meta charset="utf-8">'  
		        str +='<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">'  
		        str+='<style>';  
		        str+='body{text-align: center;}';
		        str+='.outDiv{margin:0 auto; width:800px; height:900px;border:1px solid #000;padding-left: 10px}';
		        str+='.innerDiv{text-align: left;padding-top: 6px}';
		        str+='</style>';  
		        str +='</head>'  
		        str +='<body>'  
		        str +="<div class='outDiv'>";
		        str +='<h1>'+countTypeStr+'装机工单</h1>';
		        str +='<div class=innerDiv>工单号：'+orderNum+'</div>';
		        str +='<div class="innerDiv">安装地址：'+address+'</div>';
		        str +='<div class="innerDiv">安装有效期：'+datepicker+'</div>';
		        str +='<div class="innerDiv">打印日期：'+printTime+'</div>';
		        str +='<div class="innerDiv">打印人：'+printPerson+'</div>';
		        str +='<div class="innerDiv">派单人签名：</div>';
		        str +='<div class="innerDiv">备注：'+remark+'</div>';
		        str +='<div style="text-align: center;padding-top: 20px">二维码登录设备</div>';
		        str +='<div style="text-align: center;"><img alt="" src="'+img+'"></div>';
		        str +='<div class="innerDiv">安装人员签名：<span style="padding-left: 400px">安装日期：</span></div>';
		        str +='</div>';
		        str +='</body>'  
		        str +='</html>'

		    oPop.document.write(str);  
		    oPop.print();  
		    oPop.close();  
	});  


})

