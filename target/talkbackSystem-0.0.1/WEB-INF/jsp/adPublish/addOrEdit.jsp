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
.video-js{
width: 222px
}
</style>
<body>
<div>
	   <div>
	   <form id="adPublishsaveForm" name="adPublishsaveForm">
		   <input type="hidden" name="id" value="${adPublish.id}"/>
		   <input type="hidden" name="adType" value="${adPublish.adType}"/>
<%-- 		   <input type="hidden" id="adPublishDomainIds" value="${adPublish.treecheckbox}"/> --%>
		   <input type="hidden" name="carrierId" value="${adPublish.carrierId}"/>
              <div class="firstFont"><span class="starColor">*</span>上传媒体文件：</div>
<!--               <div style="padding-left: 5px;color: #33B4EB;">说明：门口机首页请选择1080*1920~720*1280px比例为9:16的图片</div> -->
<!--               <div style="padding-left: 45px;color: #33B4EB;">门口机拨号页请选择1080*1440~720*960px比例为3:4的图片</div> -->
<!--               <div style="padding-left: 45px;color: #33B4EB;">手机请选择1080*420~720*280px比例为18:7的图片</div> -->
              <div style="border: 2px solid #ccc;height: 200px;overflow: auto;">
	                 <div>
		                <table class="uploadFileTable"> 
		                   <tr class="uploadFileTr">
			                <td><div id="fileInput_idDiv"><input id="fileInput_id" type="file" name="uploadFile" style="width: 300px;" class="form-control fileInput"/></div></td>
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
					           <video id="adVideo" autoplay="autoplay" class="video-js vjs-default-skin" controls preload="none" width="640" height="264">
								    <source src="${adPics.serverAddr}${adPics.relativePath}" type='video/mp4' />
								</video>
					            
<%-- 					           <img src="${adPics.serverAddr}${adPics.relativePath}" alt="通用的占位符缩略图" title="${adPics.positionStr}"> --%>
					         </a>
						     <div class="caption">
						     <c:if test="${adPublish.opraterType!=1}">
						       <p>
						         <a href="#" class="btn btn-xs btn-danger" role="button">删除</a>
						       </p>
						       </c:if>
						     </div>
					     </div>
					  </c:forEach>
					</c:if>
                 	</div>
              </div>
              <table>
              <tr>
                <td><div class="firstFont"><span class="starColor">*</span>有效期：</div></td>
                <td><div><input name="expDateStr" class="form-control datepicker" readonly="readonly" title="有效期不能为空！" value="${adPublish.expDateStr}"/></div></td>
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
	                     <ul id="adPublishShowTree" class="ztree" style="width:260px; overflow:auto;"></ul>
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

// var myPlayer = videojs('adVideo');
// videojs("adVideo").ready(function(){
//     var myPlayer = this;
//     myPlayer.play();
// }); 
//时间控件
 $(".datepicker").datepicker();
  $(function(){
	  var uploader = Qiniu.uploader({
		    runtimes: 'html5,flash,html4',      // 上传模式，依次退化
		    browse_button: 'fileInput_id',         // 上传选择的点选按钮，必需
		    uptoken_url: $path+'/mc/qiniu/token',         // Ajax请求uptoken的Url，强烈建议设置（服务端提供）
		    get_new_uptoken: false,             // 设置上传文件的时候是否每次都重新获取新的uptoken
		    domain: $qiniu,     // bucket域名，下载资源时用到，必需
		    container: 'fileInput_idDiv',             // 上传区域DOM ID，默认是browser_button的父元素
		    max_file_size: '2mb',             // 最大文件体积限制
		    flash_swf_url: 'path/of/plupload/Moxie.swf',  //引入flash，相对路径
		    max_retries: 3,                     // 上传失败最大重试次数
		    dragdrop: true,                     // 开启可拖曳上传
		    drop_element: 'fileInput_idDiv',        // 拖曳上传区域元素的ID，拖曳文件或文件夹后可触发上传
		    chunk_size: '2mb',                  // 分块上传时，每块的体积
		    auto_start: false,                   // 选择文件后自动上传，若关闭需要自己绑定事件触发上传
		    multi_selection: false,
		    filters : {
		        max_file_size : '2mb',
		        prevent_duplicates: true,
		        mime_types: [
		 		      {title : "Image files", extensions : "jpg,gif,png,mp4"} // 限定jpg,gif,png后
		        ]
		    },
		    init: {
		        'FilesAdded': function(up, files) {
		        },
		        'BeforeUpload': function(up, file) {
		               // 每个文件上传前，处理相关的事情
		        },
		        'UploadProgress': function(up, file) {
		        },
		        'FileUploaded': function(up, file, info) {
		               // 查看简单反馈
		               var domain = up.getOption('domain');
		               var res = jQuery.parseJSON(info);
		     		  if(res.key){
		    	    	 var position = $('.uploadFileTable .position').val();
		    	    	 var targetDevice = $('#adPublishsaveForm .targetDevice').val();
		    	    	 var param = "position="+position+"&targetDevice="+targetDevice+"&serverAddr="+domain+"&relativePath="+res.key;
		     			 $.post($path+'/mc/adPublish/uploadFile.do',param,function(data){
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
						 });
		     		  }
		        },
		        'Error': function(up, err, errTip) {
		               //上传出错时，处理相关的事情
		        },
		        'UploadComplete': function() {
		               //队列文件处理完毕后，处理相关的事情
		        },
		        'Key': function(up, file) {
		            // 若想在前端对每个文件的key进行个性化处理，可以配置该函数
		            // 该配置必须要在unique_names: false，save_key: false时才生效
                	var key = "web/adImg/"+new Date().getTime();
		            return key
		        }
		    }
		});
	  
	  var id = $("#adPublishsaveForm [name='id']").val();
	  var treecheckbox = "${adPublish.treecheckbox}";
	  var parentIds = "${parentIds}"
	  zTreeObj = zTree("adPublishShowTree", ["id","name","level"],["nocheckLevel","0"],$path+"/mc/domain/getNodes.do",true,{"Y": "", "N": ""},null,adPublishDataEcho(id,treecheckbox,parentIds), null)
		//数据回显函数
	  function adPublishDataEcho(id,treecheckbox,parentIds){
		  var zTreeOnAsyncSuccess;
		  if(id&&treecheckbox&&parentIds){
			  zTreeOnAsyncSuccess = function(event, treeId, treeNode, msg) {
				//子节点回显
				 if(treeNode){
					 $.each(treeNode.children,function(i,obj){
						 if(parentIds.indexOf(obj.id)>0){
						    zTreeObj.reAsyncChildNodes(treeNode.children[i], "refresh");
						 }
						 if(treecheckbox.indexOf(obj.id)>0){
							 zTreeObj.checkNode(treeNode.children[i], true, false);
						 }
					 })
					//第一级节点回显
				 }else{
				     var nodes = zTreeObj.getNodes();
				     $.each(nodes,function(i,obj){
						 if(parentIds.indexOf(obj.id)>0){
						        zTreeObj.reAsyncChildNodes(nodes[i], "refresh");
						 }
						 if(treecheckbox.indexOf(obj.id)>0){
							 zTreeObj.checkNode(nodes[i], true, false);
						 }
					 })
				 }
		     };
		  }
		  
		  return zTreeOnAsyncSuccess;
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
 	    	   uploader.start();
//  	    	var position = $('.uploadFileTable .position').val();
//  	    	var targetDevice = $('#adPublishsaveForm .targetDevice').val();
//  	        $.ajaxFileUpload({  
//  	            url:$path+'/mc/adPublish/uploadFile.do',  
//  	            secureuri:false,  
//  	            fileElementId:'fileInput_id',//file标签的id  
//  	            dataType: 'json',//返回数据的类型  
//  	            data:{position:position,targetDevice:targetDevice},//一同上传的数据  
//  	            success: function (data, status) {
//  	            	var imgPath = data.serverAddr+data.relativePath;
//  	            	if(!data.message&&imgPath){
// 	 	            	var img = '<div class="col-sm-6 col-md-3"><a href="#" class="thumbnail"><img src="'+imgPath+'" alt="通用的占位符缩略图" title="'+data.positionStr+'"></a>'+
// 	 	            	'<input type="hidden" name="picId" value="'+data.id+'"/>'+
// 	 	            	'<div class="caption"><p><a href="javascript:void(0)" class="btn btn-xs btn-danger" role="button">删除</a></p></div></div></div> ';
// 	 	            	$("#thumbnailDiv").append(img);
// 	 	            	//清空输入框
// 	 	            	$("#fileInput_id").val("");
//  	            	}else{
//  	            		if(data.message){
//  	            			hiAlert("提示",data.message);
//  	            		}else{
//  	            		  hiAlert("提示","请选择需要上传的图片！");
//  	            		}
//  	            	}
//  	            },  
//  	            error: function (data, status, e) {  
//  	                alert(e);  
//  	            }  
//  	        });  
 	    });
 	    
 	    
 	    $("#adPublishsaveForm .sure").on("click",function(){
 	    	var param = $("#adPublishsaveForm").serialize();
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
 	    	
 	    	$.post($path+"/mc/adPublish/saveOrUpdate.do",param,function($data){
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