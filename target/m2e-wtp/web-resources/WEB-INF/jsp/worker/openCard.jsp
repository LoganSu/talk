<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<object style="width: 0px;height: 0px" codebase="Test_ActiveX.dll" classid="clsid:63EB4027-895A-4895-99C3-F535CEABA046" id="myactivex">
</object>
<script type="text/javascript">

//连接发卡器
var  connectCardMachine= function(){
	   var connectReader;
	   try{
		   connectReader = myactivex.ConnectReader();
		   if(jQuery.parseJSON(connectReader).code!='0'){
			   hiAlert("提示","发卡器连接出错！");
				return false;
		   }
	   }catch(e){
		   hiAlert("提示","发卡器连接出错！");
			return false;
	   }
	   var cardId ;
	  try{
		   cardId = myactivex.GetCardId();
		  if(jQuery.parseJSON(cardId).code!='0'){
			  hiAlert("提示","请放入卡片！");
			  return false;
		  }
	  }catch(e){
		  hiAlert("提示","读取卡片id出错！");
			     return false;
	  }
	  
	  return cardId;
}

 function writeCard(data){
	  $.post($path+"/mc/worker/writeCard.do?a="+Math.random(),data,function($data){
			if($data=="1"){
		       hiAlert("提示","该卡已经绑定该区域，请更换新卡！");
		       return false;
			}else if($data=="0"){
				  hiAlert("提示","开卡成功！");
			}else if($data=="2"){
				hiAlert("提示","开卡失败！");
			}else if($data=="3"){
				hiAlert("提示","此卡已经与该员工绑定，请更换新卡！");
			}
		});
	  
	  
//	  obj = jQuery.parseJSON(cardId);
//	  var card_id = obj.result.card_id;
//	  var domainId = $("#workerOpenCardForm [name='domainId']").val();
//	  if(obj.code=='0'&&card_id){
//		  $.post($path+"/mc/permission/connectCardMachine.do?a="+Math.random(),"cardSn="+card_id+"&domainId="+domainId,function($data){
//			  if(!$data){
//				  hiAlert("提示","该卡已经绑定此房产，请更换新卡！");
//				   return false;
//			   }
//		  })
//	  }else{
//		  hiAlert("提示","请放入卡片！");
//	  }
}



$(function(){
	   //显示树
	  var id = $("#workerShowTree [name='id']").val();
	  var treecheckbox = "${worker.treecheckbox}";
	  zTreeObj = zTree("workerShowTree", ["id","name","level"],["nocheckLevel","0"],$path+"/mc/domain/getNodes.do",true,{"Y": "", "N": ""},null,null, null);
	  
	  
	  
	  $("#workerOpenCardForm .connectCardMachine").on("click",function(){
		  var cardId = connectCardMachine();
		  //设置卡片sn
		   var obj = jQuery.parseJSON(cardId);
		   var card_id = obj.result.card_id;
		  if(card_id){
		     $("#workerOpenCardForm .cardSn").val(card_id);
		  }
		  //设置卡片类型
		  var cardType = jQuery.parseJSON(cardId).result.is_ic;
		  $("#workerOpenCardForm .cardType").val(cardType);
	  });
	  
		  
		 //保存卡片信息
		 $("#workerOpenCardDiv .sure").on("click",function(){
			   var cardId = connectCardMachine();
			   if(zTreeObj){
					 var nodes = zTreeObj.getCheckedNodes(true);
			    	 if(nodes.length!=1){
			    		 hiAlert("提示","请选择一个域！");
							return false;
			    	 }else{
			    		 $("#workerOpenCardForm [name='domainId']").val(nodes[0].id);
			    	 }
			      }
			  var data = $("#workerOpenCardForm").serialize();
			   if(cardId){
				   //判断卡片是否已经初始化秘钥
				   $.post($path+"/mc/permission/isInitKey.do?a="+Math.random(),data,function($a){
					   //没有加载秘钥且是同一社区的的卡
					   if($a=='0'){
						   //加载key
			               $.post($path+"/mc/permission/getKey.do?a="+Math.random(),data,function($data){
//	 		            	   alert($data);
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
									writeCard(data);
			            	   
			               })
					   }else if($a=='1'){
							writeCard(data);
					   }else if($a=='2'){
						   hiAlert("提示","一张卡片只能在一个社区使用！");
					   }else if($a=='3'){
						   hiAlert("提示","卡片号或域不能为空！");
					   }
				   })
			   }else{
				   hiAlert("提示","发卡器连接异常！");
			   }
			 
// 			var cardSn = $("#workerOpenCardForm .cardSn").val();
// 			if(cardSn.length<1){
// 				hiAlert("提示","请链接发卡器！");
// 				return false;
// 			}
			
			
		 });
		 
})
</script>

</head>
<body>
   <div  id="workerOpenCardDiv">
     <form id="workerOpenCardForm" action="">
         <input type="hidden" name="workerId" value="${worker.id}"/>
         <input type="hidden" class="cardType" name="cardType" value="${cardInfo.cardType}"/>
         <input type="hidden" name="domainId" value=""/>
         <table>
            <tr>
               <td><div class="firstFont">卡片ID：
<!--                <select name="cardType" class="cardType form-control" style="width: 80px"> -->
<!--                  <option value="">卡片ID</option> -->
<!--                 <option value="2">身份证</option> --> 
<!--                  <option value="3">银行卡</option> -->
<!--                </select> -->
               </div></td>
               <td><div><input name="cardSn"  class="form-control cardSn"/></div></td>
               <td><div class="leftFont">
                  <button type="button" class="btn btn-info btn-sm connectCardMachine">连接发卡器</button>
               </div></td>
            </tr>
          </table>
          <div class="firstFont">地址选择：</div>
           <div>
             <ul id="workerShowTree" class="ztree" style="width:260px; overflow:auto;"></ul>
             
           </div>  
     </form>
     <div class="modal-footer">
	         <!--操作按钮 -->
	        <button type="button" class="btn btn-primary sure">确定</button>
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	 </div>
  </div>
</body>
<script type="text/javascript">

	//数据回显函数
// 	  function dwrellerDataEcho(id,treecheckbox,parentIds){
// 		  var zTreeOnAsyncSuccess;
// 		  if(id&&treecheckbox&&parentIds){
// 			  zTreeOnAsyncSuccess = function(event, treeId, treeNode, msg) {
// 				//子节点回显
// 				 if(treeNode){
// 					 $.each(treeNode.children,function(i,obj){
// 						 if(parentIds.indexOf(obj.id)>0){
// 						    zTreeObj.reAsyncChildNodes(treeNode.children[i], "refresh");
// 						 }
// 						 if(treecheckbox.indexOf(obj.id)>0){
// 							 zTreeObj.checkNode(treeNode.children[i], true, false);
// 						 }
// 					 })
// 					//第一级节点回显
// 				 }else{
// 				     var nodes = zTreeObj.getNodes();
// 				     $.each(nodes,function(i,obj){
// 						 if(parentIds.indexOf(obj.id)>0){
// 						        zTreeObj.reAsyncChildNodes(nodes[i], "refresh");
// 						 }
// 						 if(treecheckbox.indexOf(obj.id)>0){
// 							 zTreeObj.checkNode(nodes[i], true, false);
// 						 }
// 					 })
// 				 }
// 		     };
// 		  }
		  
// 		  return zTreeOnAsyncSuccess;
// 	  }
</script>
</html>