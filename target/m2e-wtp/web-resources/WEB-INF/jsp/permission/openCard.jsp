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
	})
	
	 //保存卡片信息
	 $("#openCardDiv .sure").on("click",function(){
		var data = $("#openCardForm").serialize();
		var cardSn = $("#openCardForm .cardSn").val();
		if(cardSn.length<1){
			hiAlert("提示","请链接发卡器！");
			return false;
		}
		//保存卡片信息
		$.post($path+"/mc/permission/writeCard.do",data,function($data){
			if($data=="1"){
		       hiAlert("提示","该卡片已经被使用，请更换新卡！");
		       return false;
			}else if($data=="0"){
				  hiAlert("提示","开卡成功！");
			}else if($data=="2"){
				hiAlert("提示","开卡失败！");
			}
		});
		
	 });
	 
	 $("#openCardForm .cardType").on("change",function(){
		 var cardType = $(this)[0].options[$(this)[0].selectedIndex].value;
		 var countTypeIntVal = parseInt(cardType);
		 var readonly ='<td><div><input name="cardSn" readonly="readonly" class="form-control cardSn required"/></div></td><td><div class="leftFont"><button type="button" class="btn btn-info btn-sm connectCardMachine">连接发卡器</button></div></td>';
		 var writeonly ='<td><div><input name="cardSn" class="form-control cardSn required"/></div></td><td><div class="leftFont"></div></td>';

		 switch(countTypeIntVal){
		  case 1:
			  $("#openCardForm table tr:eq(0) td:eq(1)").remove();
			  $("#openCardForm table tr:eq(0) td:eq(1)").remove();
			  $("#openCardForm table tr:eq(0)").append(readonly);
			  break;
          case 2:
        	  $("#openCardForm table tr:eq(0) td:eq(1)").remove();
 			  $("#openCardForm table tr:eq(0) td:eq(1)").remove();
        	  $("#openCardForm table tr:eq(0)").append(writeonly);
			  break;
          default:
        	  $("#openCardForm table tr:eq(0) td:eq(1)").remove();
			  $("#openCardForm table tr:eq(0) td:eq(1)").remove();
			  $("#openCardForm table tr:eq(0)").append(readonly);
		     break;
		 }
	 })
	 
})
</script>

</head>
<body>
  <div  id="openCardDiv">
     <form id="openCardForm" action="">
         <input type="hidden" name="roomId" value="${cardInfo.roomId}"/>
         <table>
            <tr>
               <td><div class="firstFont">
               <select name="cardType" class="cardType form-control" style="width: 80px">
                 <option value="1">IC卡</option>
                 <option value="2">身份证</option>
                 <option value="3">银行卡</option>
               </select></div></td>
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