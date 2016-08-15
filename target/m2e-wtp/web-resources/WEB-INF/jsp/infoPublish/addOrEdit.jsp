<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@  taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<body>
<div>
	   <div>
	   <form id="infoPublishsaveForm" name="infoPublishsaveForm" enctype="multipart/form-data" method="post">
		   <input type="hidden" name="id" value="${infoPublish.id}"/>
		   <input type="hidden" name="carrierId" value="${infoPublish.carrierId}"/>
		   <input type="hidden" id="infoPublishDomainIds" value="${infoPublish.treecheckbox}"/>
           <table>
              <tr>
                <td><div class="firstFont"><span class="starColor">*</span>标题名称：</div></td>
                <td><div><input name="title" style="width: 320px" class="form-control required" maxlength="20" title="标题名称不能为空！" value="${infoPublish.title}"/></div></td>
              </tr>
              <tr>
                <td><div class="firstFont"><span class="starColor">*</span>内容：</div></td>
                <td colspan="5"><div><span style="font-size: 12px"></span><textarea maxlength="200" style="width: 630px;height: 200px" name="infoDetail" rows="" cols="" title="内容不能为空！" class="form-control required">${infoPublish.infoDetail}</textarea></div></td>
              </tr>
           </table>
<!--            <table> -->
<!--               <tr> -->
<!--                 <td><div class="firstFont"><span class="starColor">*</span>发送时间：</div></td> -->
<%--                 <td><div><input class="sendTimeDisabled" type="radio" checked="checked" name="sendTimeType" value="1" <c:if test="${infoPublish.sendTimeType==1}">checked="checked"</c:if>/></div></td> --%>
<!-- 		        <td><div>即时发送</div></td> -->
<%-- 		        <td><div class="leftFont"><input class="sendTimeAbled" type="radio" name="sendTimeType" value="2" <c:if test="${infoPublish.sendTimeType==2}">checked="checked"</c:if>/></div></td> --%>
<!-- 		        <td><div>定时发送</div></td> -->
<%--                 <td><div><input name="sendTime" class="form-control datepicker" readonly="readonly" disabled="disabled"  value="${infoPublish.sendTime}"/></div></td> --%>
<!--               </tr> -->
<!--            </table> -->
           <table>
              <tr>
                <td><div class="firstFont"><span class="starColor">*</span>有效期：&nbsp;&nbsp;&nbsp;&nbsp;</div></td>
                <td><div><input name="expDateStr" class="form-control datepicker" title="有效期不能为空！" value="${infoPublish.expDateStr}"/></div></td>
<!--                 <td><div>至</div></td> -->
<%--                 <td><div><input name="endTime" class="form-control datepicker" title="结束时间不能为空！" value="${infoPublish.infoSign}"/></div></td> --%>
<!--                 <td><div class="firstFont"><span class="starColor">*</span>离线消息：</div></td> -->
<%--                 <td><div><input type="radio" class="offLineAbled" checked="checked" name="offLine" value="1" <c:if test="${infoPublish.offLine==1}">checked="checked"</c:if>/></div></td> --%>
<!-- 		        <td><div>保存</div></td> -->
<!-- 		        <td><div><input name="days" class="form-control days" style="width: 20px" value="0"/></div></td> -->
<!-- 		        <td><div>天</div></td> -->
<!-- 		        <td><div><input name="hours" class="form-control hours" style="width: 20px" value="5"/></div></td> -->
<!-- 		        <td><div>小时，</div></td> -->
<!-- 		        <td><div>终端上线后可收到消息（最长保存7天）</div></td> -->
<%--                 <td><div class="leftFont"><input class="offLineDisabled" type="radio" name="offLine" value="2" <c:if test="${infoPublish.offLine==2}">checked="checked"</c:if>/></div></td> --%>
<!-- 		        <td><div>不保存，终端离线时的消息将被忽略 </div></td> -->
              </tr>
           </table>
           <table>
              <tr>
                <td><div class="firstFont">署名：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div></td>
                <td><div><input name="infoSign" class="form-control" title="署名不能为空！" value="${infoPublish.infoSign}"/></div></td>
<!--                 <td><div class="leftFont">信息类型：</div></td> -->
<!--                 <td><div> -->
<!--                    <select name="infoType" class="form-control"> -->
<%--                       <option <c:if test="${infoPublish.infoType==1}">selected="selected"</c:if> value="1">公告</option> --%>
<%--                       <option <c:if test="${infoPublish.infoType==2}">selected="selected"</c:if> value="2">通知</option> --%>
<%--                       <option <c:if test="${infoPublish.infoType==3}">selected="selected"</c:if> value="3">消息</option> --%>
<%--                       <option <c:if test="${infoPublish.infoType==4}">selected="selected"</c:if> value="4">新闻</option> --%>
<!--                    </select> -->
<!--                 </div></td> -->
                <td><div class="leftFont">终端类型：</div></td>
                <td><div>
                   <select name="targetDevice" class="form-control">
                      <option <c:if test="${infoPublish.targetDevice==1}">selected="selected"</c:if> value="1">门口机</option>
                      <option <c:if test="${infoPublish.targetDevice==2}">selected="selected"</c:if> value="2">移动端</option>
                      <option <c:if test="${infoPublish.targetDevice==3}">selected="selected"</c:if> value="3">管理机</option>
                   </select>
                </div></td>
              </tr>
           </table>
<!--            <table> -->
<!--               <tr> -->
<!--                 <td><div class="firstFont">通知样式：</div></td> -->
<%--                 <td><div><input type="checkbox" checked="checked" name="messageStyle" value="1" <c:if test="${infoPublish.messageStyle==1}">checked="checked"</c:if>/></div></td> --%>
<!-- 		        <td><div>收到通知后响铃</div></td> -->
<%-- 		        <td><div class="leftFont"><input type="checkbox" checked="checked" name="messageStyle" value="2" <c:if test="${infoPublish.messageStyle==2}">checked="checked"</c:if>/></div></td> --%>
<!-- 		        <td><div>收到通知后振动</div></td> -->
<%--                 <td><div class="leftFont"><input type="checkbox" checked="checked" name="messageStyle" value="3" <c:if test="${infoPublish.messageStyle==3}">checked="checked"</c:if>/></div></td> --%>
<!--                 <td><div>收到通知后移除</div></td> -->
<!--               </tr> -->
<!--               <tr> -->
<!--                 <td><div class="firstFont">通知操作：</div></td> -->
<%--                 <td><div><input type="radio" class="messageOprateDisabled" checked="checked" name="messageOprate" value="1" <c:if test="${infoPublish.messageOprate==1}">checked="checked"</c:if>/></div></td> --%>
<!-- 		        <td><div>直接打开应用 </div></td> -->
<%-- 		        <td><div class="leftFont"><input class="messageOprateAbled" type="radio" name="messageOprate" value="2" <c:if test="${infoPublish.messageOprate==2}">checked="checked"</c:if>/></div></td> --%>
<!-- 		        <td><div>打开网页</div></td> -->
<%--                 <td colspan="2"><div><input name="openUrl" class="form-control openUrl" disabled="disabled" value="${infoPublish.openUrl}"/></div></td> --%>
<!--               </tr> -->
<!--            </table> -->
	           <div style="margin-top: 10px"><label>选择发送范围</label></div>
	           <div class="choeseArea">
		           <table>
		              <tr>
		                <td><div><input type="radio" checked="checked" name="sendType" value="1" <c:if test="${infoPublish.sendType==1}">checked="checked"</c:if>/></div></td>
		                <td><div>全部</div></td>
		              </tr>
		              <tr>
		                <td><div><input type="radio" name="sendType" value="2" <c:if test="${infoPublish.sendType==2}">checked="checked"</c:if>/></div></td>
		                <td><div>指定范围</div></td>
		                <td></td>
		              </tr>
		           </table>
	                <div style="width: 500px;height: 300px;overflow: auto;">
	                     <p id="infoPublishShowTree"></p>              
	                </div>
	           </div>
	           <!-- 详情不显示按钮 -->
	           <div class="modal-footer">
		         <!--操作按钮 -->
                <c:if test="${infoPublish.opraterType!=1}">
		           <input type="button" class="btn btn-primary sure" value="确定"/> 
	            </c:if>
		            <input type="button" class="btn btn-default" data-dismiss="modal" value="关闭"/> 
		       </div>
         </form>
	   </div>
 </div>
</body>
<script type="text/javascript">
 //时间控件
   $(".datepicker").datepicker();
//  $("#infoPublishsaveForm .datepicker").datetimepicker(
// 		 {
// 			format: 'yyyy-mm-dd hh:ii',
// 			language: 'cn', 
// 		 });
  $(function(){
	  //domainTree(id, url, open, checkbox, checkboxLink, showurl, checkboxPartShow, layer, treecheckboxFiledName)
		domainTree("infoPublishShowTree", $path+'/mc/carrier', false, true, false, false);

		// 普通tree
// 		$('#infoPublishShowTree').bstree({
// 				url: $path+'/mc/carrier',
// 				height:'auto',
// 				open: false,
// 				checkbox:true,
// 				checkboxLink:false,
// 				showurl:false
// 		});
		//多选框回显
		var treecheckbox = $("#infoPublishDomainIds").val();
		//java代码 treecheckbox==null 则 treecheckbox=[]
		if(treecheckbox.length>2){
			treecheckbox=treecheckbox.substring(1,treecheckbox.length-1);
			var arr= treecheckbox.split(",");
			  $.each(arr,function(index,obj){
				  $("#infoPublishShowTree ."+$.trim(obj)).prop('checked',true);
			  });
		}
		//确定按钮
	   $("#infoPublishsaveForm .sure").on("click",function(){
		   var url=$path+"/mc/infoPublish/saveOrUpdate.do";
		   var param = $("#infoPublishsaveForm").serialize();
		   $.post(url,param,function($data){
				 if(!$data){
					 $("#tableShowList").bootstrapTable('refresh', {
							url: $path+"/mc/infoPublish/showList.do",
						});
						$("#unnormalModal").modal("hide");
				 }else{
					 hiAlert("提示",$data);
				 }
			 });
	   })
	   //切换单选框
	   $("#infoPublishsaveForm .sendTimeAbled").on("click",function(){
		   $("#infoPublishsaveForm .datepicker").attr("disabled",false);
	   })
	   $("#infoPublishsaveForm .sendTimeDisabled").on("click",function(){
		   $("#infoPublishsaveForm .datepicker").attr("disabled",true);
	   })
	   
	   
// 	    $("#infoPublishsaveForm .messageOprateAbled").on("click",function(){
// 		   $("#infoPublishsaveForm .openUrl").attr("disabled",false);
// 	   })
// 	   $("#infoPublishsaveForm .messageOprateDisabled").on("click",function(){
// 		   $("#infoPublishsaveForm .openUrl").attr("disabled",true);
// 	   })
	   
	   
// 	    $("#infoPublishsaveForm .offLineAbled").on("click",function(){
// 		   $("#infoPublishsaveForm .days").attr("disabled",false);
// 		   $("#infoPublishsaveForm .hours").attr("disabled",false);
// 	   })
// 	   $("#infoPublishsaveForm .offLineDisabled").on("click",function(){
// 		   $("#infoPublishsaveForm .days").attr("disabled",true);
// 		   $("#infoPublishsaveForm .hours").attr("disabled",true);
// 	   })
  })
</script>
</html>