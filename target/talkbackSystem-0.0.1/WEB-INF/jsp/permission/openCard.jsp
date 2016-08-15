<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<object codebase="Test_ActiveX.dll" classid="clsid:63EB4027-895A-4895-99C3-F535CEABA046" id="myactivex">
</object>
<script type="text/javascript">
$(function(){
	 //时间控件
	  $(".datepicker").datepicker();
	 //连接发卡器
	  $("#openCardForm .connectCardMachine").on("click",function(){
		   var connectReader;
		   try{
			   connectReader = myactivex.ConnectReader();
		   }catch(e){
			   hiAlert("提示","发卡器连接出错！");
				return false;
		   }
           var obj = jQuery.parseJSON(connectReader);
		   if(obj.code=='0'){
			   //加载key
               $.post($path+"/mc/permission/getKey.do",function($data){
            	   var loadKey;
            	   try{
            		   loadKey = myactivex.LoadKey($data);
            	   }catch(e){
            		   hiAlert("提示","加载密钥出错！");
       				     return false;
            	   }
            	   obj = jQuery.parseJSON(loadKey);
				   if(obj.code=='0'){
					  var cardId;
					  try{
						  cardId = myactivex.GetCardId();
					  }catch(e){
						  hiAlert("提示","读取卡片id出错！");
	       				     return false;
					  }
					  obj = jQuery.parseJSON(cardId);
					  var card_id = obj.result.card_id;
					  if(obj.code=='0'&&card_id){
					  $("#openCardForm .cardSn").val(card_id);
						  $.post($path+"/mc/permission/connectCardMachine.do","cardSn="+card_id,function($data){
							  if(!$data){
								  hiAlert("提示","该卡已经被使用！");
			 					   return false;
			 				   }
						  })
					  }else{
						  hiAlert("提示","请放入卡片！");
					  }
				   }else{
					   hiAlert("提示","加载密钥出错！");
				   }
               })
		   }else{
			   hiAlert("提示","发卡器连接异常！");
		   }
// 		   var url =$path+"/mc/permission/connectCardMachine.do";
// 		   $.post(url,function($data){
// 			   if($data){
// 				   if($data.cardSn==0){
// 					   hiAlert("提示","请放入卡片！");
// 				   }else if($data.cardSn==null){
// 				       hiAlert("提示","发卡器连接异常！");
// 				   }else{
// 				      $("#openCardForm .cardSn").val($data.cardSn);
// 				   }
// 			   }else{
// 				   hiAlert("提示","该卡已经被使用，请放入新卡！");
// 			   }
// 		   });
	})
	
// 	//多选框变单选
// 	$('#openCardForm .lesseeAddress').each(function(){
// 		$(this).click(function(){
// 			if($(this).is(':checked')){
// 				$('#openCardForm .lesseeAddress').prop('checked',false);
// 				$(this).prop('checked',true);
// 			}
// 		});
// 	});
	 //保存卡片信息
	 $("#openCardDiv .sure").on("click",function(){
		var data = $("#openCardForm").serialize();
		var cardSn = $("#openCardForm .cardSn").val();
		if(cardSn.length<1){
			hiAlert("提示","请链接发卡器！");
			return false;
		}
// 		var frDate = $("#openCardForm .frDate").val();
// 		var toDate = $("#openCardForm .toDate").val();
// 		if(!frDate||!toDate){
// 			hiAlert("提示","请填写有效时间！");
// 			return false;
// 		}
// 		var domainSns = $("#openCardForm input[name='domainSns']:checked").length;
// 		 if(domainSns<1){
// 			 hiAlert("提示","请选择授权门禁！");
// 				return false;
// 		 }
		//保存卡片信息
		$.post($path+"/mc/permission/writeCard.do",data,function($data){
			if($data=="1"){
		       hiAlert("提示","该卡片已经被使用，请更换新卡！");
		       return false;
			}else if($data=="0"){
// 				//写卡信息
// 				var frDateArr = frDate.split("-");
// 				var toDateArr = toDate.split("-");
// 				var basicInfo = "{\"card_no\":"+cardSn+", \"card_type\":64, \"opr_type\":0, \"frDate\":{\"year\":"+frDateArr[0]+", \"month\":"+frDateArr[1]+", \"day\":"+frDateArr[2]+"}, \"toDate\":{\"year\":"+toDateArr[0]+", \"month\":"+toDateArr[1]+", \"day\":"+toDateArr[2]+"}}}";
// 				var accessInfoArr =["{\"access\":["];
// 				var domainSns = $("#openCardForm .domainSns");
// 				$.each(domainSns,function(index,obj){
// 					var domainSn = $(obj).val();
// 					var whilteVersion = $(obj).attr("whilteVersion");
// 					if(index!=domainSns.length-1){
// 						accessInfoArr.push("{\"lid\":"+domainSn+", \"ver\":"+whilteVersion+"},");
// 					}else{
// 						accessInfoArr.push("{\"lid\":"+domainSn+", \"ver\":"+whilteVersion+"}");
// 					}
// 				});
// 				accessInfoArr.push("]}");
// 				var writeBasicInfo,writeAccessInfo;
// 				try{
// 				    writeBasicInfo = myactivex.WriteBasicInfo(basicInfo);
// 				    writeAccessInfo = myactivex.WriteAccessInfo(accessInfoArr.join(""));
// 				}catch(e){
// 					hiAlert("提示","写入卡片信息出错！");
// 					return false;
// 				}
// 				var writeBasicInfoJSON = jQuery.parseJSON(writeBasicInfo);
// 				var writeAccessInfoJSON = jQuery.parseJSON(writeAccessInfo);
// 				if(writeBasicInfoJSON.code=="0"&&writeAccessInfoJSON.code=="0"){
				  hiAlert("提示","开卡成功！");
// 				}
			}else if($data=="2"){
				hiAlert("提示","开卡失败！");
			}
		});
		
	 });
})
</script>

</head>
<body>
  <div  id="openCardDiv">
     <form id="openCardForm" action="">
         <input type="hidden" name="roomId" value="${cardInfo.roomId}"/>
         <table>
            <tr>
               <td><div class="firstFont"><span class="starColor">*</span>卡序列号：</div></td>
               <td><div><input name="cardSn" readonly="readonly" class="form-control cardSn required"/></div></td>
               <td><div class="leftFont">
                  <button type="button" class="btn btn-info btn-sm connectCardMachine">连接发卡器</button>
               </div></td>
            </tr>
             <tr>
               <td colspan="1"><div class="firstFont">发卡地址：</div></td>
               <td><div><span class="lefttFont">${address}</span>
               </div></td>
            </tr>
          </table>
     </form>
     <div class="modal-footer">
	         <!--操作按钮 -->
	        <button type="button" class="btn btn-primary sure">确定</button>
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	 </div>
  </div>
</body>
</html>