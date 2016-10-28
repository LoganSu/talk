<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
$(function(){
		  var uploader = Qiniu.uploader({
			    runtimes: 'html5,flash,html4',      // 上传模式，依次退化
			    browse_button: 'firstPage_fileInput_id',         // 上传选择的点选按钮，必需
			    uptoken_url: $path+'/mc/qiniu/token',         // Ajax请求uptoken的Url，强烈建议设置（服务端提供）
			    get_new_uptoken: false,             // 设置上传文件的时候是否每次都重新获取新的uptoken
			    domain: 'http://upload.gdsayee.com.cn/',     // bucket域名，下载资源时用到，必需
			    container: 'firstPage_fileInput_idDiv',             // 上传区域DOM ID，默认是browser_button的父元素
			    max_file_size: '2mb',             // 最大文件体积限制
			    flash_swf_url: 'path/of/plupload/Moxie.swf',  //引入flash，相对路径
			    max_retries: 3,                     // 上传失败最大重试次数
			    dragdrop: true,                     // 开启可拖曳上传
			    drop_element: 'firstPage_fileInput_idDiv',        // 拖曳上传区域元素的ID，拖曳文件或文件夹后可触发上传
			    chunk_size: '2mb',                  // 分块上传时，每块的体积
			    auto_start: false,                   // 选择文件后自动上传，若关闭需要自己绑定事件触发上传
			    multi_selection: false,
			    filters : {
			        max_file_size : '2mb',
			        prevent_duplicates: true,
			        mime_types: [
			 		      {title : "Image files", extensions : "jpg,gif,png"} // 限定jpg,gif,png后
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
			    	    	 var param = "serverAddr="+domain+"&relativePath="+res.key+"&adpublishId="+$("#aboutNeighborhoodsSearchForm .hiddenId").val();
			     			 $.post($path+'/mc/aboutNeighborhoods/uploadFile.do',param,function(data){
			     				var imgPath = data.serverAddr+data.relativePath;
			  	            	if(!data.message&&imgPath){
			 	 	            	var img = '<div class="col-sm-6 col-md-3"><a href="#" class="thumbnail"><img src="'+imgPath+'" alt="通用的占位符缩略图" title="'+data.positionStr+'"></a>'+
			 	 	            	'<input type="hidden" name="picId" value="'+data.id+'"/>'+
			 	 	            	'<div class="caption"><p><a href="javascript:void(0)" class="btn btn-xs btn-danger" role="button">删除</a></p></div></div></div> ';
			 	 	            	$("#firstPageShowDiv").append(img);
			 	 	            	//清空输入框
			 	 	            	$("#firstPage_fileInput_id").val("");
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
	                	var key = "web/aboutNeigh/firstPage/"+new Date().getTime();
			            return key
			        }
			    }
			});
		  
	
	
	
	
	
	
	
	
// 	    //选择文件之后执行上传  
	    $('#addNeighborhoodsFirstPageForm .uoloadFileButton').on('click', function() {
			 var count=uploader.files.length;
			 alert(count);
// 	    	 if(uploader.files[0]){
// 				    if(count>1){
// 				        hiAlert("提示","最多只能上传一个图标");
// 				        return false;
// 				    }
				     uploader.start();
// 			   }
// 	    var id =$("#aboutNeighborhoodsSearchForm .hiddenId").val();
// 	        $.ajaxFileUpload({  
// 	            url:$path+'/mc/aboutNeighborhoods/uploadFile.do',  
// 	            secureuri:false,  
// 	            fileElementId:'firstPage_fileInput_id',//file标签的id  
// 	            dataType: 'json',//返回数据的类型  
// 	            data:{adpublishId:id},//一同上传的数据  
// 	            success: function (data, status) {
// 	            	var imgPath = data.serverAddr+data.relativePath;
// 	            	if(!data.message&&imgPath){
//  	            	var img = '<div class="col-sm-6 col-md-3"><a href="#" class="thumbnail"><img src="'+imgPath+'" alt="通用的占位符缩略图" title="'+data.positionStr+'"></a>'+
//  	            	'<input type="hidden" name="picId" value="'+data.id+'"/>'+
//  	            	'<div class="caption"><p><a href="javascript:void(0)" class="btn btn-xs btn-danger" role="button">删除</a></p></div></div></div> ';
//  	            	$("#firstPageShowDiv").append(img);
//  	            	//清空输入框
//  	            	$("#firstPage_fileInput_id").val("");
// 	            	}else{
// 	            		if(data.message){
// 	            			hiAlert("提示",data.message);
// 	            		}else{
// 	            		  hiAlert("提示","请选择需要上传的图片！");
// 	            		}
// 	            	}
// 	            },  
// 	            error: function (data, status, e) {  
// 	                alert(e);  
// 	            }  
// 	        });  
	    });
	  
})
</script>
</head>
<body>
    <div id="addNeighborhoodsFirstPageDiv">
      <form id="addNeighborhoodsFirstPageForm" name="addNeighborhoodsFirstPageForm">
          <input type="hidden" class="hiddenId" value=""/>
          <div>
           <table class="uploadFileTable"> 
              <tr class="uploadFileTr">
	            <td><div id="firstPage_fileInput_idDiv"><input id="firstPage_fileInput_id" type="file" style="width: 300px;" class="form-control fileInput"/></div></td>
	            <td><input type="button" class="btn btn-sm btn-primary uoloadFileButton" value="点击上传"/></td>
              </tr>
              </table>
           </div>
           
           <div id="firstPageShowDiv" class="row" style="padding-left: 20px">
			<c:if test="${picList!=null}">
			   <c:forEach items="${picList}" var="adPics">
			      <div class="col-sm-6 col-md-3">
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
          </div>
     </form>
      <!-- 详情不显示按钮 -->
      <div class="modal-footer">
          <!--操作按钮 -->
<!--           <input type="button" class="btn btn-primary sure" value="确定"/>  -->
          <input type="button" class="btn btn-default" data-dismiss="modal" value="关闭"/> 
      </div>
    </div>  
</body>
</html>