<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
.thumbnail{
 margin-bottom: 0px
}
.caption{
text-align: center;
}
</style>
<body>
<div>
	   <div>
	   <form id="adPublishsaveForm" name="adPublishsaveForm">
		   <input type="hidden" name="id" value="${adPublish.id}"/>
<%-- 		   <input type="hidden" name="carrierId" value="${adPublish.carrierId}"/> --%>
		   <input type="hidden" id="adPublishDomainIds" value="${adPublish.treecheckbox}"/>
              <div class="firstFont"><span class="starColor">*</span>上传媒体文件：</div>
              <div style="padding-left: 5px;color: #33B4EB;">说明：门口机首页请选择1080*1920~720*1280px比例为9:16的图片</div>
              <div style="padding-left: 45px;color: #33B4EB;">门口机拨号页请选择1080*1440~720*960px比例为3:4的图片</div>
              <div style="padding-left: 45px;color: #33B4EB;">手机请选择1080*420~720*280px比例为18:7的图片</div>
              <div style="border: 2px solid #ccc;height: 200px;overflow: auto;">
	                 <div>
		                <table class="uploadFileTable"> 
		                   <tr class="uploadFileTr">
			                <td><input id="fileInput_id" type="file" name="uploadFile" style="width: 300px;" class="form-control fileInput"/></td>
			                <td><input type="button" class="btn btn-sm btn-primary uoloadFileButton" value="上传"/></td>
<!-- 			                <td><a><span class="glyphicon glyphicon-plus-sign subInputPlus" style="cursor:pointer;"></span></a></td> -->
<!-- 			                <td><a><span class="glyphicon glyphicon-minus-sign subInputMinus" style="cursor:pointer;"></span></a></td> -->
                           <td><div class="leftFont">显示位置：</div></td>
                           <td><div>
			                  <select class="form-control position">
			                     <option value="1">首页</option>
			                     <option value="2">通话页</option>
			<%--                       <option <c:if test="${adPublish.position==3}">selected="selected"</c:if> value="3">下方</option> --%>
			<%--                       <option <c:if test="${adPublish.position==4}">selected="selected"</c:if> value="4">左方</option> --%>
			<%--                       <option <c:if test="${adPublish.position==5}">selected="selected"</c:if> value="5">右方</option> --%>
			                  </select>
                           </div></td>
		                   </tr>
	                    </table>
	                 </div>
					<div id="thumbnailDiv" class="row" style="padding-left: 20px">
					<c:if test="${adPublish.adPics!=null}">
					   <c:forEach items="${adPublish.adPics}" var="adPics">
					      <div class="col-sm-6 col-md-3">
<%-- 					         <input type="hidden" name="adPics.serverAddr" value="${adPics.serverAddr}"/> --%>
<%-- 					         <input type="hidden" name="adPics.relativePath" value="${adPics.relativePath}"/> --%>
					         <input type="hidden" name="picId" value="${adPics.id}"/>
					         <a href="#" class="thumbnail">
					           <img src="${adPics.serverAddr}${adPics.relativePath}" alt="通用的占位符缩略图" title="${adPics.positionStr}">
					         </a>
						     <div class="caption">
						       <p>
						         <a href="#" class="btn btn-xs btn-danger" role="button">删除</a>
						       </p>
						     </div>
					     </div>
					  </c:forEach>
					</c:if>
				 <!--    <div class="col-sm-6 col-md-3">
					         <input type="hidden" name="picId" value=""/>
					         <a href="#" class="thumbnail">
					           <img src="${path}/img/qq.jpg" alt="通用的占位符缩略图">
					         </a>
						     <div class="caption">
						       <p>
						         <a href="#" class="btn btn-xs btn-danger" role="button">删除</a>
						       </p>
						     </div>
					     </div>-->
                 	</div>
              </div>
              <table>
              <tr>
                <td><div class="firstFont"><span class="starColor">*</span>有效期：</div></td>
                <td><div><input name="expDateStr" class="form-control datepicker" title="有效期不能为空！" value="${adPublish.expDateStr}"/></div></td>
                <td><div class="leftFont">终端类型：</div></td>
                <td><div>
                   <select name="targetDevice" class="form-control targetDevice">
                      <option <c:if test="${adPublish.targetDevice==1}">selected="selected"</c:if> value="1">门口机</option>
                      <option <c:if test="${adPublish.targetDevice==2}">selected="selected"</c:if> value="2">移动端</option>
                   </select>
                </div></td>
              </tr>
              </table>
	           <div style="margin-top: 10px"><label>选择发送范围</label></div>
	           <div class="choeseArea">
		           <table>
		              <tr>
		                <td><div><input type="radio" checked="checked" name="sendType" value="1" <c:if test="${adPublish.sendType==1}">checked="checked"</c:if>/></div></td>
		                <td><div>全部</div></td>
		              </tr>
		              <tr>
		                <td><div><input type="radio" name="sendType" value="2" <c:if test="${adPublish.sendType==2}">checked="checked"</c:if>/></div></td>
		                <td><div>指定范围</div></td>
		                <td></td>
		              </tr>
		           </table>
	                <div style="width: 500px;height: 300px;overflow: auto;">
	                     <p id="adPublishShowTree"></p>              
	                </div>
	           </div>
	           <!-- 详情不显示按钮 -->
	           <div class="modal-footer">
		         <!--操作按钮 -->
                <c:if test="${adPublish.opraterType!=1}">
		           <input type="button" class="btn btn-primary sure" value="确定"/> 
	            </c:if>
		            <input type="button" class="btn btn-default" data-dismiss="modal" value="关闭"/> 
		       </div>
         </form>
	   </div>
 </div>
 <!-- 隐藏iframe设置表单的目标为这个嵌入框架  使页面效果不会跳转 -->
 <iframe style="width:0; height:0;display: none;" id="adPublishSubmitFrame" name="adPublishSubmitFrame"></iframe>
</body>
<script type="text/javascript">
 //时间控件
 $(".datepicker").datepicker();
  $(function(){
		// 普通tree
		$('#adPublishShowTree').bstree({
				url: $path+'/mc/carrier',
				height:'auto',
				open: false,
				checkbox:true,
				checkboxLink:false,
				showurl:false
		});
		//多选框回显
 		var treecheckbox = $("#adPublishDomainIds").val();
 		//java代码 treecheckbox==null 则 treecheckbox=[]
 		if(treecheckbox.length>2){
 			treecheckbox=treecheckbox.substring(1,treecheckbox.length-1);
 			var arr= treecheckbox.split(",");
			  $.each(arr,function(index,obj){
 				  $("#adPublishShowTree ."+$.trim(obj)).prop('checked',true);
 			  });
 		}
 		 //切换单选框
 	   $("#adPublishsaveForm .sendTimeAbled").on("click",function(){
 		   $("#adPublishsaveForm .datepicker").attr("disabled",false);
 	   })
 	   $("#adPublishsaveForm .sendTimeDisabled").on("click",function(){
 		   $("#adPublishsaveForm .datepicker").attr("disabled",true);
 	   })
 	   
 		 
 	    //选择文件之后执行上传  
 	    $('#adPublishsaveForm .uoloadFileButton').on('click', function() {
 	    	var position = $('.uploadFileTable .position').val();
 	    	var targetDevice = $('#adPublishsaveForm .targetDevice').val();
 	        $.ajaxFileUpload({  
 	            url:$path+'/mc/adPublish/uploadFile.do',  
 	            secureuri:false,  
 	            fileElementId:'fileInput_id',//file标签的id  
 	            dataType: 'json',//返回数据的类型  
 	            data:{position:position,targetDevice:targetDevice},//一同上传的数据  
 	            success: function (data, status) {
 	            	var imgPath = data.serverAddr+data.relativePath;
 	            	if(!data.message&&imgPath){
	 	            	var img = '<div class="col-sm-6 col-md-3"><a href="#" class="thumbnail"><img src="'+imgPath+'" alt="通用的占位符缩略图" title="'+data.positionStr+'"></a>'+
	 	            	'<input type="hidden" name="picId" value="'+data.id+'"/>'+
	 	            	'<div class="caption"><p><a href="javascript:void(0)" class="btn btn-xs btn-danger" role="button">删除</a></p></div></div></div> ';
	 	            	$("#thumbnailDiv").append(img);
	 	            	//清空输入框
	 	            	$("#fileInput_id").val("");
 	            	}else{
 	            		if(data.message){
 	            			hiAlert("提示",data.message);
 	            		}else{
 	            		  hiAlert("提示","请选择需要上传的图片！");
 	            		}
 	            	}
 	                //把图片替换  
//  	                var obj = jQuery.parseJSON(data);  
//  	                $("#upload").attr("src", "../image/"+obj.fileName);  
 	      
//  	                if(typeof(data.error) != 'undefined') {  
//  	                    if(data.error != '') {  
//  	                        alert(data.error);  
//  	                    } else {  
//  	                        alert(data.msg);  
//  	                    }  
//  	                }  
 	            },  
 	            error: function (data, status, e) {  
 	                alert(e);  
 	            }  
 	        });  
 	    });
 	    
 	    
 	    $("#adPublishsaveForm .sure").on("click",function(){
 	    	var data = $("#adPublishsaveForm").serialize();
 	    	$.post($path+"/mc/adPublish/saveOrUpdate.do",data,function($data){
 	    		if($data){
 	    			hiAlert("提示",$data);
 	    			return false;
 	    		}
 	    		var url =$(".search").attr("rel");
 	    		$("#tableShowList").bootstrapTable('refresh', {
 	    			url: url,
 	    		});
 	    		$("#unnormalModal").modal("hide");
 	    	});
 	    })
 	
  })
</script>
</html>