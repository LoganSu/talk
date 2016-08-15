<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
$(function(){
	    var id =$("#aboutNeighborhoodsSearchForm .hiddenId").val();
	    //选择文件之后执行上传  
	    $('#addNeighborhoodsFirstPageForm .uoloadFileButton').on('click', function() {
	        $.ajaxFileUpload({  
	            url:$path+'/mc/aboutNeighborhoods/uploadFile.do',  
	            secureuri:false,  
	            fileElementId:'firstPage_fileInput_id',//file标签的id  
	            dataType: 'json',//返回数据的类型  
	            data:{adpublishId:id},//一同上传的数据  
	            success: function (data, status) {
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
	            },  
	            error: function (data, status, e) {  
	                alert(e);  
	            }  
	        });  
	    });
	  
})
</script>
</head>
<body>
    <div id="addNeighborhoodsFirstPageDiv">
      <form id="addNeighborhoodsFirstPageForm" name="addNeighborhoodsFirstPageForm">
          <div>
           <table class="uploadFileTable"> 
              <tr class="uploadFileTr">
            <td><input id="firstPage_fileInput_id" type="file" name="uploadFile" style="width: 300px;" class="form-control fileInput"/></td>
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