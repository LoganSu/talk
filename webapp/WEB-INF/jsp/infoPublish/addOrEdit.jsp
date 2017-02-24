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
<%-- 		   <input type="hidden" id="infoPublishDomainIds" value="${infoPublish.treecheckbox}"/> --%>
		   <input type="hidden" name="infoType" value="${infoPublish.infoType}"/>
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
           <table>
              <tr>
                <td><div class="firstFont"><span class="starColor">*</span>有效期：&nbsp;&nbsp;&nbsp;&nbsp;</div></td>
                <td><div><input name="expDateStr" readonly="readonly" class="form-control datepicker" title="有效期不能为空！" value="${infoPublish.expDateStr}"/></div></td>
              </tr>
           </table>
           <table>
              <tr>
                <td><div class="firstFont"><span class="starColor">*</span>署名：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div></td>
                <td><div><input name="infoSign" class="form-control required" title="署名不能为空！" value="${infoPublish.infoSign}"/></div></td>
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
	                    <ul id="infoPublishShowTree" class="ztree" style="width:260px; overflow:auto;"></ul>
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

	  var id = $("#infoPublishsaveForm [name='id']").val();
	  var treecheckbox = "${infoPublish.treecheckbox}";
	  zTreeObj = zTree("infoPublishShowTree", ["id","name","level"],["nocheckLevel","0"],$path+"/mc/domain/getNodes.do",true,{"Y": "", "N": ""},null,dataEcho(id,treecheckbox), null)
	  //domainTree(id, url, open, checkbox, checkboxLink, showurl, checkboxPartShow, layer, treecheckboxFiledName)
// 		domainTree("infoPublishShowTree", $path+'/mc/carrier', false, true, false, false,true,[1,2,3,4]);

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
// 		var treecheckbox = $("#infoPublishDomainIds").val();
// 		//java代码 treecheckbox==null 则 treecheckbox=[]
// 		if(treecheckbox.length>2){
// 			treecheckbox=treecheckbox.substring(1,treecheckbox.length-1);
// 			var arr= treecheckbox.split(",");
// 			  $.each(arr,function(index,obj){
// 				  $("#infoPublishShowTree ."+$.trim(obj)).prop('checked',true);
// 			  });
// 		}
		//确定按钮
	   $("#infoPublishsaveForm .sure").on("click",function(){
		   var url=$path+"/mc/infoPublish/saveOrUpdate.do";
		   var param = $("#infoPublishsaveForm").serialize();
		   var nodes = zTreeObj.getCheckedNodes(true);
		   var treecheckbox;
		      if(nodes){
		    	  var arr = new Array();
		    	  $.each(nodes,function(i,obj){
		    		  arr.push(obj.id);
		    	  });
		    	  var parr = arr.join("&treecheckbox=");
		    	  treecheckbox = "&treecheckbox="+parr;
		    	  param=param+treecheckbox;
		      }
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