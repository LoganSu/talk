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
	 //确定挂失
	  $("#workerLossAndOpenDiv .sure").on("click",function(){
		   var data = $("#workerLossAndOpenForm").serialize();
		   //注销需要清空卡片秘钥
		   var cardStatus = $("#workerLossAndOpenDiv [name='cardStatus']").val();
			   //最后一张卡注销时还原卡片
			   $.post($path+"/mc/permission/isLastCard.do?a="+Math.random(),data,function($a){
					   if(cardStatus=='3'&&$a=="0"){
						   var connectReader;
						   try{
							   connectReader = myactivex.ConnectReader();
						   }catch(e){
							   hiAlert("提示","发卡器连接出错！");
								return false;
						   }
						   //域id
						   var param = "domainId="+$("#workerLossAndOpenForm [name='domainId']").val();
						   //加载key
			               $.post($path+"/mc/permission/getKey.do?a="+Math.random(),param,function($key){
			            	   if($key){
				            	   $.post($path+"/mc/permission/lossUnlossDestroy.do",data,function($data){
				        			   if($data){
				        			        hiAlert("提示",$data);
				        			   }else{
				        				   //有秘钥的初始化key
				                    	   if($key){
				        	            	   var loadKey,RestoreCardKey,obj;
			// 	        	            	   alert($key);
				        	            	   var indata = "{\"key\":\""+$key+"\"}";
				        	          	       try{
				        	          		          loadKey = myactivex.LoadKey(indata);
				        	          		         obj = jQuery.parseJSON(loadKey);
				        	          		         if(obj.code!='0'){
				        						    	  hiAlert("提示","秘钥不正确！");
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
			// 	        	            		    alert(obj.toSource());
				        	            		    if(obj.code!='0'){
				        						    	  hiAlert("提示","注销卡片失败！");
				        			       				     return false;
				        						      }
				        		       			} catch (e) {
				        		       			   hiAlert("提示","卡片秘钥还原失败！");
				        		       			   return false;
				        		       			}
				        				   
				              			   $("#unnormalModal").modal("hide");
				              			  $("#tableShowList").bootstrapTable('refresh', {
				              				url: $path+'/mc/worker/showList.do',
				              			});
				        			   }
				        		   });
			            	   }else{
			            		   $.post($path+"/mc/permission/lossUnlossDestroy.do?a="+Math.random(),data,function($data){
				        			   if($data){
				        			        hiAlert("提示",$data);
				        			   }else{
				              			    $("#unnormalModal").modal("hide");
				              			   $("#tableShowList").bootstrapTable('refresh', {
				              				url: $path+'/mc/worker/showList.do',
				              			});
				        			   }
				        		   });
			            		   
			            	   }
			            	   
			               })
						   
					   }else{
						   $.post($path+"/mc/permission/lossUnlossDestroy.do?a="+Math.random(),data,function($data){
			    			   if($data){
			    			        hiAlert("提示",$data);
			    			   }else{
			          			   $("#unnormalModal").modal("hide");
			          			  $("#tableShowList").bootstrapTable('refresh', {
			          				url: $path+'/mc/worker/showList.do',
			          			});
			    			   }
			    		   });
					   }
					   
			   })
		   
	  })
	  
	  
	  $("#workerLossAndOpenDiv .cardSn").on("change",function(){
		  var cardSn = $(this).val();
		  $("#workerLossAndOpenDiv .address").hide();
		  $("#workerLossAndOpenDiv ."+cardSn).show();
		  //设置domainId
		 initDomainId()
	  })
	  initDomainId()
})
function initDomainId(){
	  var domainId = $("#workerLossAndOpenDiv .cardSn option:selected").attr("domainId");
	  $("#workerLossAndOpenDiv [name='domainId']").val(domainId);
}
</script>
<body>
  <div  id="workerLossAndOpenDiv">
     <form id="workerLossAndOpenForm" action="">
         <input type="hidden" name="cardStatus" value="${cardInfo.cardStatus}"/>
         <input type="hidden" name="workerId" value="${cardInfo.workerId}"/>
         <input type="hidden" name="domainId" value=""/>
         <table>
            <tr>
               <td><div class="firstFont"><span class="starColor">*</span>卡序列号 ：</div></td>
               <td><div>
                   <select name="cardSn" class="form-control cardSn">
                       <c:forEach items="${cardMap}" var="map">
                            <option cardNo="${map.value.cardNo}" cardTypeStr="${map.value.cardTypeStr}" domainId="${map.value.domainId}" frDate="${map.value.frDate}" toDate="${map.value.toDate}" value="${map.key}">${map.key}</option>
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
               <td><div> 
<!--                  <select name="cardSn" class="form-control cardSn"> -->
                   <c:forEach items="${cardMap}" var="map" varStatus="status">
                       <c:choose>
                          <c:when test="${status.index==0}">
                              <div class="lefttFont address ${map.key}"><span>${map.value.address}</span></div>
                          </c:when>
                          <c:otherwise>
                             <div style="display: none" class="lefttFont address ${map.key}"><span>${map.value.address}</span></div>
                          </c:otherwise>
                       </c:choose>
                   </c:forEach>
<!--                </select> -->
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