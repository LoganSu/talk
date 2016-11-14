<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<object style="width: 0px;height: 0px"  codebase="Test_ActiveX.dll" classid="clsid:63EB4027-895A-4895-99C3-F535CEABA046" id="myactivex">

</object>
</head>
<script type="text/javascript">
$(function(){
	 //时间控件
// 	  $(".datepicker").datepicker();
	 //连接发卡器
// 	  $("#lossAndOpenForm .connectCardMachine").on("click",function(){
// 		   var url =$path+"/mc/permission/connectCardMachine.do";
// 		   $.post(url,function($data){
// 			   if($data){
// 				   if($data.cardSn==0){
// 					   hiAlert("提示","请放入卡片！");
// 				   }else if($data.cardSn==null){
// 				       hiAlert("提示","发卡器连接异常！");
// 				   }else{
// 				      $("#lossAndOpenForm .cardSn").val($data.cardSn);
// 				   }
// 			   }else{
// 				   $("#lossAndOpenForm .cardSn").val($data.cardSn);
// 				   hiAlert("提示","该卡已经被使用，请放入新卡！");
// 			   }
// 		   });
// 	})
	
	//多选框变单选
// 	$('#lossAndOpenForm .lesseeAddress').each(function(){
// 		$(this).click(function(){
// 			if($(this).is(':checked')){
// 				$('#lossAndOpenForm .lesseeAddress').prop('checked',false);
// 				$(this).prop('checked',true);
// 			}
// 		});
// 	});
	 //显示卡片信息
	 $("#lossAndOpenDiv .cardSn").on("change",function(){
		 //编号
		 var cardNo =  $("#lossAndOpenDiv .cardSn option:selected").attr("cardNo");
		 $("#lossAndOpenDiv .cardNo").val(cardNo);
		 //卡片类型
		 var cardTypeStr = $("#lossAndOpenDiv .cardSn option:selected").attr("cardTypeStr");
		 $("#lossAndOpenDiv .cardTypeStr").val(cardTypeStr);
		 //开始结束时间
		 var frDate = $("#lossAndOpenDiv .cardSn option:selected").attr("frDate");
		 $("#lossAndOpenDiv .frDate").val(frDate);
		 var toDate = $("#lossAndOpenDiv .cardSn option:selected").attr("toDate");
		 $("#lossAndOpenDiv .toDate").val(toDate);
		
	 });
	 //确定挂失
	  $("#lossAndOpenDiv .sure").on("click",function(){
		   var data = $("#lossAndOpenForm").serialize();
		   //注销需要清空卡片秘钥
		   var cardStatus = $("#lossAndOpenForm [name='cardStatus']").val();
		   if(cardStatus=='3'){
			   var connectReader;
			   try{
				   connectReader = myactivex.ConnectReader();
			   }catch(e){
				   hiAlert("提示","发卡器连接出错！");
					return false;
			   }
			   //加载key
               $.post($path+"/mc/permission/getKey.do","roomId="+$("#lossAndOpenForm [name='roomId']").val(),function($data){
            	   $.post($path+"/mc/permission/lossUnlossDestroy.do",data,function($data){
        			   if($data){
        			        hiAlert("提示",$data);
        			   }else{
        				   //有秘钥的初始化key
                    	   if($data){
        	            	   var loadKey,RestoreCardKey,obj;
        	            	   var indata = "{\"key\":\""+$data+"\"}";
        	          	       try{ 
        	          		          loadKey = myactivex.LoadKey(indata);
        	          		         obj = jQuery.parseJSON(RestoreCardKey);
        	          		         if(obj.code!='0'){
        						    	  hiAlert("提示","加载密钥出错！");
        			       				     return false;
        						      }
        	            	   }catch(e){
        	            		   hiAlert("提示","加载密钥出错！");
        	       				     return false;
        	            	   }
        	            	 }
        	            	   try {
        	            		    RestoreCardKey = myactivex.RestoreCardKey();  
        	            		    obj = jQuery.parseJSON(RestoreCardKey);
        	            		    if(obj.code!='0'){
        						    	  hiAlert("提示","清空卡片信息出错！");
        			       				     return false;
        						      }
        		       			} catch (e) {
        		       			   hiAlert("提示","清空卡片信息出错！");
        		       			   return false;
        		       			}
        				   
              			   $("#unnormalModal").modal("hide");
              			  $("#tableShowList").bootstrapTable('refresh', {
              				url: $path+'/mc/room/showList.do',
              			});
        			   }
        		   });
            	   
               })
			   
		   }else{
			   $.post($path+"/mc/permission/lossUnlossDestroy.do",data,function($data){
    			   if($data){
    			        hiAlert("提示",$data);
    			   }else{
          			   $("#unnormalModal").modal("hide");
          			  $("#tableShowList").bootstrapTable('refresh', {
          				url: $path+'/mc/room/showList.do',
          			});
    			   }
    		   });
		   }
		   
	  })
	 
})
</script>
<body>
  <div  id="lossAndOpenDiv">
     <form id="lossAndOpenForm" action="">
         <input type="hidden" name="cardStatus" value="${cardInfo.cardStatus}"/>
         <input type="hidden" name="roomId" value="${cardInfo.roomId}"/>
         <table>
            <tr>
               <td><div class="firstFont"><span class="starColor">*</span>卡序列号 ：</div></td>
               <td><div>
                   <select name="cardSn" class="form-control cardSn">
                       <c:forEach items="${cardMap}" var="map">
                            <option cardNo="${map.value.cardNo}" cardTypeStr="${map.value.cardTypeStr}" frDate="${map.value.frDate}" toDate="${map.value.toDate}" value="${map.key}">${map.key}</option>
                       </c:forEach>
                   </select> 
               </div></td>
               <td><div class="leftFont">卡序编号：</div></td>
               <td><div>
                    <c:forEach items="${cardMap}" var="map" varStatus="status">
                          <c:if test="${status.index==0}">
                            <input name="" class="form-control cardNo" readonly="readonly" value="${map.value.cardNo}"/>
                          </c:if>
                    </c:forEach>    
                </div></td>
                <td><div  class="leftFont">卡类型：</div></td>
               <td><div>
                  <c:forEach items="${cardMap}" var="map" varStatus="status">
                          <c:if test="${status.index==0}">
                            <input name="" class="form-control cardTypeStr" readonly="readonly" value="${map.value.cardTypeStr}"/>
                          </c:if>
                    </c:forEach>      
               </div></td>
            </tr>
            <tr>
               <td colspan="1"><div class="firstFont">地址：</div></td>
               <td><div><span class="lefttFont">${address}</span>
               </div></td>
            </tr>
          </table>
<!--           <table> -->
<!--             <tr> -->
<!--                <td><div class="firstFont" style="margin-left: 14px">有效期：</div></td> -->
<!--                <td><div> -->
<%--                    <c:forEach items="${cardMap}" var="map" varStatus="status"> --%>
<%--                           <c:if test="${status.index==0}"> --%>
<%--                             <input name="" readonly="readonly" class="form-control frDate" value="${map.value.frDate}"/> --%>
<%--                           </c:if> --%>
<%--                     </c:forEach>  --%>
<!--                </div></td> -->
<!--                <td><div>至</div></td> -->
<!--                <td><div> -->
<%--                    <c:forEach items="${cardMap}" var="map" varStatus="status"> --%>
<%--                           <c:if test="${status.index==0}"> --%>
<%--                             <input name="" readonly="readonly" class="form-control toDate" value="${map.value.toDate}"/> --%>
<%--                           </c:if> --%>
<%--                     </c:forEach>  --%>
<!--                </div></td> -->
<!--             </tr> -->
<!--           </table> -->
     </form>
     <div class="modal-footer">
	         <!--操作按钮 -->
	        <button type="button" class="btn btn-primary sure">确定</button>
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	 </div>
  </div>
</body>
</html>