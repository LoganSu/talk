<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<object style="width: 0px;height: 0px" codebase="Test_ActiveX.dll" classid="clsid:63EB4027-895A-4895-99C3-F535CEABA046" id="myactivex">
</object>
<script type="text/javascript">
$(function(){
	   var  connectCardMachine= function(){
		   var connectReader;
		   try{
			   connectReader = myactivex.ConnectReader();
		   }catch(e){
			   hiAlert("提示","发卡器连接出错！");
				return false;
		   }
		   var cardId;
			  try{
				  cardId = myactivex.GetCardId();
			  }catch(e){
				  hiAlert("提示","读取卡片id出错！");
					     return false;
			  }
			  if(jQuery.parseJSON(cardId).code!='0'){
				  hiAlert("提示","请放入卡片！");
				  return false;
			  }
			  //设置卡片类型
			  var cardType = jQuery.parseJSON(cardId).result.is_ic;
			  $("#openCardForm .cardType").val(cardType);
           var obj = jQuery.parseJSON(connectReader);
		   if(obj.code=='0'){
			   //判断卡片是否已经初始化秘钥
			   $.post($path+"/mc/permission/isInitKey.do?a="+Math.random(),"cardSn="+jQuery.parseJSON(cardId).result.card_id+"&domainId="+$("#openCardForm [name='domainId']").val(),function($a){
// 				   alert($a);
				   //没有加载秘钥且是同一社区的的卡
				   if($a=='0'){
					   //加载key
		               $.post($path+"/mc/permission/getKey.do?a="+Math.random(),"domainId="+$("#openCardForm [name='domainId']").val(),function($data){
// 		            	   alert($data);
		            	   //有秘钥的初始化key
		            	   if($data){
			            	   var loadKey;
			            	   var indata = "{\"key\":\""+$data+"\"}";
			          	       try{ 
			          		          loadKey = myactivex.LoadKey(indata);
			            	   }catch(e){
			            		   hiAlert("提示","加载密钥出错！");
			       				     return false;
			            	   }
		//             	       obj = jQuery.parseJSON(loadKey);
							   //验证卡片是否合法
							   obj = jQuery.parseJSON(myactivex.IsValidCard());
		// 					   alert(myactivex.IsValidCard());
							   if(obj.code!='0'){
								   try{
		// 							   alert(myactivex.InitCardKey_1());
									   obj = jQuery.parseJSON(myactivex.InitCardKey_1());
								      if(obj.code!='0'){
								    	  hiAlert("提示","此为非法卡片，请更换卡片！");
					       				     return false;
								      }
								   }catch(e){
				            		   hiAlert("提示","初始化卡片出错！");
				       				     return false;
				            	   }
							   }
		            	   }
							   //如果不合法初始化卡片
								writeCard(cardId);
		            	   
		               })
				   }else if($a=='1'){
						writeCard(cardId);
				   }else if($a=='2'){
					   hiAlert("提示","一张卡片只能在一个社区使用！");
				   }else if($a=='3'){
					   hiAlert("提示","卡片号或房间不能为空！");
				   }
			   })
		   }else{
			   hiAlert("提示","发卡器连接异常！");
		   } 
	   }
		  $("#openCardForm .connectCardMachine").on("click",connectCardMachine);

	 //连接发卡器
// 	  $(document).on("click","#openCardForm .connectCardMachine",function(){
// 		   var connectReader;
// 		   try{
// 			   connectReader = myactivex.ConnectReader();
// 		   }catch(e){
// 			   hiAlert("提示","发卡器连接出错！");
// 				return false;
// 		   }
// 		   var cardId;
// 			  try{
// 				  cardId = myactivex.GetCardId();
// 			  }catch(e){
// 				  hiAlert("提示","读取卡片id出错！");
// 					     return false;
// 			  }
// 			  if(jQuery.parseJSON(cardId).code!='0'){
// 				  hiAlert("提示","请放入卡片！");
// 				  return false;
// 			  }
//            var obj = jQuery.parseJSON(connectReader);
// 		   if(obj.code=='0'){
// 			   //加载key
//                $.post($path+"/mc/permission/getKey.do","domainId="+$("#openCardForm [name='domainId']").val(),function($data){
//             	   //有秘钥的初始化key
//             	   if($data){
// 	            	   var loadKey;
// 	            	   var indata = "{\"key\":\""+$data+"\"}";
// 	          	       try{ 
// 	          		          loadKey = myactivex.LoadKey(indata);
// 	            	   }catch(e){
// 	            		   hiAlert("提示","加载密钥出错！");
// 	       				     return false;
// 	            	   }
// //             	       obj = jQuery.parseJSON(loadKey);
// 					   //验证卡片是否合法
// 					   obj = jQuery.parseJSON(myactivex.IsValidCard());
// 					   if(obj.code!='0'){
// 						   try{
// 							   obj = jQuery.parseJSON(myactivex.InitCardKey_1());
// 						      if(obj.code!='0'){
// 						    	  hiAlert("提示","此为非法卡片，请更换卡片！");
// 			       				     return false;
// 						      }
// 						   }catch(e){
// 		            		   hiAlert("提示","初始化卡片出错！");
// 		       				     return false;
// 		            	   }
// 					   }
//             	   }
// 					   //如果不合法初始化卡片
// 						writeCard(cardId);
            	   
//                })
// 		   }else{
// 			   hiAlert("提示","发卡器连接异常！");
// 		   }
// 	})
	var writeCard=function(cardId){
		  obj = jQuery.parseJSON(cardId);
		  var card_id = obj.result.card_id;
		  var domainId = $("#openCardForm [name='domainId']").val();
		  if(obj.code=='0'&&card_id){
		  $("#openCardForm .cardSn").val(card_id);
			  $.post($path+"/mc/permission/connectCardMachine.do?a="+Math.random(),"cardSn="+card_id+"&domainId="+domainId,function($data){
				  if(!$data){
					  hiAlert("提示","该卡已经绑定此房产，请更换新卡！");
 					   return false;
 				   }
			  })
		  }else{
			  hiAlert("提示","请放入卡片！");
		  }
	 }
	 //保存卡片信息
	 $("#openCardDiv .sure").on("click",function(){
		var data = $("#openCardForm").serialize();
		var cardSn = $("#openCardForm .cardSn").val();
		if(cardSn.length<1){
			hiAlert("提示","请链接发卡器！");
			return false;
		}
		//保存卡片信息
		$.post($path+"/mc/permission/writeCard.do?a="+Math.random(),data,function($data){
			if($data=="1"){
		       hiAlert("提示","该卡已经绑定此房产，请更换新卡！");
		       return false;
			}else if($data=="0"){
				  hiAlert("提示","开卡成功！");
			}else if($data=="2"){
				hiAlert("提示","开卡失败！");
			}
		});
		
	 });
	 
// 	 $("#openCardForm .cardType").on("change",function(){
// 		 var cardType = $(this)[0].options[$(this)[0].selectedIndex].value;
// 		 var countTypeIntVal = parseInt(cardType);
// 		 var readonly ='<td><div><input name="cardSn" readonly="readonly" class="form-control cardSn required"/></div></td><td><div class="leftFont"><button type="button" class="btn btn-info btn-sm connectCardMachine">连接发卡器</button></div></td>';
// 		 var writeonly ='<td><div><input name="cardSn" class="form-control cardSn required"/></div></td><td><div class="leftFont"></div></td>';

// 		 switch(countTypeIntVal){
// 		  case 1:
// 			  $("#openCardForm table tr:eq(0) td:eq(1)").remove();
// 			  $("#openCardForm table tr:eq(0) td:eq(1)").remove();
// 			  $("#openCardForm table tr:eq(0)").append(readonly);
// 			  $("#openCardForm .connectCardMachine").on("click",connectCardMachine);
// 			  break;
//           case 2:
//         	  $("#openCardForm table tr:eq(0) td:eq(1)").remove();
//  			  $("#openCardForm table tr:eq(0) td:eq(1)").remove();
//         	  $("#openCardForm table tr:eq(0)").append(writeonly);
// 			  break;
//           default:
//         	  $("#openCardForm table tr:eq(0) td:eq(1)").remove();
// 			  $("#openCardForm table tr:eq(0) td:eq(1)").remove();
// 			  $("#openCardForm table tr:eq(0)").append(readonly);
// 			  $("#openCardForm .connectCardMachine").on("click",connectCardMachine);
// 		     break;
// 		 }
// 	 })
	 
})
</script>

</head>
<body>
  <div  id="openCardDiv">
     <form id="openCardForm" action="">
         <input type="hidden" name="domainId" value="${cardInfo.domainId}"/>
         <input type="hidden" class="cardType" name="cardType" value="${cardInfo.cardType}"/>
         <table>
            <tr>
               <td><div class="firstFont">卡片ID
<!--                <select name="cardType" class="cardType form-control" style="width: 80px"> -->
<!--                  <option value="">卡片ID</option> -->
<!--                 <option value="2">身份证</option> --> 
<!--                  <option value="3">银行卡</option> -->
<!--                </select> -->
               </div></td>
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