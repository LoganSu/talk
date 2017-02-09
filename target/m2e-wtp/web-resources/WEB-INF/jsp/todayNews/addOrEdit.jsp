<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
request.setCharacterEncoding("UTF-8");
String htmlData = (String)request.getAttribute("infoDetail");
%>
<%!
private String htmlspecialchars(String str) {
	if(str!=null){
	str = str.replaceAll("&", "&amp;");
	str = str.replaceAll("<", "&lt;");
	str = str.replaceAll(">", "&gt;");
	str = str.replaceAll("\"", "&quot;");
	return str;
	}
	return "";
}
%>
<head>
	<meta charset="utf-8" />
    <script charset="utf-8" src="${path}/js/editor/kindeditor-all.js"></script>
    <link rel="stylesheet" href="${path}/js/editor/themes/default/default.css" />
	<link rel="stylesheet" href="${path}/js/editor/plugins/code/prettify.css" />
	<script charset="utf-8" src="${path}/js/editor/lang/zh-CN.js"></script>
	<script charset="utf-8" src="${path}/js/editor/plugins/code/prettify.js"></script>
</head>
<body>
<div>
	   <div>
		 <form id="todayNewssaveForm" name="todayNewssaveForm" action="" target="todayNewsSubmitFrame" enctype="multipart/form-data" method="post">
		   <input type="hidden" name="id" value="${todayNews.id}"/>
		   <input type="hidden" id="todayNewssDomainIds" value="${todayNews.treecheckbox}"/>
		   <input type="hidden" name="pictureUrl" value="${todayNews.pictureUrl}"/>
		   <input type="hidden" name="carrierId" value="${todayNews.carrierId}"/>
		   
		   
           <table>
               <tr>
                <td><div class="firstFont"><span class="starColor">*</span>标题名称：</div></td>
                <td><div><input name="title" style="width: 320px" class="form-control required title" title="标题名称不能为空！" value="${todayNews.title}"/></div></td>
              </tr>
              <tr>
                <td><div class="firstFont" id="todayNewsPictureDiv">上传标题图片：</div></td>
                <td colspan="5"><div><input style="width: 320px" id="todayNewsPicture" name="picture" type="file" class="form-control"/></div></td>
              </tr>
              <c:if test="${todayNews.pictureUrl!=null}">
                 <tr><td><div class="firstFont"></div></td><td colspan="5"><div>
                   <c:if test="${todayNews.pictureUrl!=null&&todayNews.pictureUrl!=''}">
                     <img src="${todayNews.pictureUrl}" style="width: 150px;height: 100px"/>
                   </c:if>
                 </div></td></tr>
              </c:if>
              <tr>
                <td><div class="firstFont"><span class="starColor">*</span>内容：</div></td>
                <td colspan="5"><div>
                    <textarea style="width: 680px;height: 400px" id="todayNewsDetail" name="" rows="" cols="" class="form-control"><%=htmlspecialchars(htmlData)%></textarea>
                </div></td>
              </tr>
           </table>
           <div style="margin-top: 10px"><label>选择发送范围</label></div>
	           <div class="choeseArea">
		           <table>
		              <tr>
		                <td><div><input type="radio" checked="checked" name="sendType" value="1" <c:if test="${todayNews.sendType==1}">checked="checked"</c:if>/></div></td>
		                <td><div>全部</div></td>
		              </tr>
		              <tr>
		                <td><div><input type="radio" name="sendType" value="2" <c:if test="${todayNews.sendType==2}">checked="checked"</c:if>/></div></td>
		                <td><div>指定范围</div></td>
		                <td></td>
		              </tr>
		           </table>
	                <div style="width: 500px;height: 300px;overflow: auto;">
	                     <p id="todayNewsShowTree"></p>           
	                </div>
	           </div>
	           <!-- 详情不显示按钮 -->
	           <div class="modal-footer">
		            <!--操作按钮 -->
                    <c:if test="${todayNews.opraterType!=1}">
		               <input type="button" class="btn btn-primary sure" value="确定"/> 
		            </c:if>
		            <input type="button" class="btn btn-default" data-dismiss="modal" value="关闭"/> 
		       </div>
         </form>
	   </div>
 </div>
 <iframe name="todayNewsSubmitFrame" style='display:none'></iframe>
</body>
<script type="text/javascript">
    //时间控件
    $(".datepicker").datepicker();
    
    
  $(function(){
		  var uploader = Qiniu.uploader({
			    runtimes: 'html5,flash,html4',      // 上传模式，依次退化
			    browse_button: 'todayNewsPicture',         // 上传选择的点选按钮，必需
			    uptoken_url: $path+'/mc/qiniu/token',         // Ajax请求uptoken的Url，强烈建议设置（服务端提供）
			    get_new_uptoken: false,             // 设置上传文件的时候是否每次都重新获取新的uptoken
			    domain: $qiniu,     // bucket域名，下载资源时用到，必需
			    container: 'todayNewsPictureDiv',             // 上传区域DOM ID，默认是browser_button的父元素
			    max_file_size: '2mb',             // 最大文件体积限制
			    flash_swf_url: 'path/of/plupload/Moxie.swf',  //引入flash，相对路径
			    max_retries: 3,                     // 上传失败最大重试次数
			    dragdrop: true,                     // 开启可拖曳上传
			    drop_element: 'todayNewsPictureDiv',        // 拖曳上传区域元素的ID，拖曳文件或文件夹后可触发上传
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
				              $("#todayNewssaveForm [name='pictureUrl']").val(domain+res.key);
			    	    	 var param = $("#todayNewssaveForm").serialize();
			    	    	 //标题非空判断
			    			 var title = $("#todayNewssaveForm .title").val();
			    			   if(!title){
			    				   hiAlert("提示","标题不能为空");
			    			        return false;
			    				  }
			    	    	 //内容非空判断
			  			    if(!editor.text()){
			  				   hiAlert("提示","内容不能为空");
			  			        return false;
			  				   }
			     			 $.post($path+'/mc/todayNews/saveOrUpdate.do',param+"&todayNewsDetailEditor="+editor.html(),function($data){
			     				 if(!$data){
									 window.hideModal("unnormalModal");
									 refresh();
								 }else{
									 hiAlert("提示",$data);
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
	                	var key = "web/todayNewsImg/"+new Date().getTime();
			            return key
			        }
			    }
			});
	  
	  
	  
	  $("#todayNewssaveForm .sure").on("click",function(){
		  var count=uploader.files.length;
		   if(uploader.files[0]){
			    if(count>1){
			        hiAlert("提示","最多只能上传一个图标");
			        return false;
			    }
			    
			     uploader.start();
		   }else{
			   var param = $("#todayNewssaveForm").serialize();
			 //标题非空判断
			 var title = $("#todayNewssaveForm .title").val();
			   if(!title){
				   hiAlert("提示","标题不能为空");
			        return false;
				  }
			   //内容非空判断
			   if(!editor.text()){
				   hiAlert("提示","内容不能为空");
			        return false;
				  }
   			   $.post($path+'/mc/todayNews/saveOrUpdate.do',param+"&todayNewsDetailEditor="+editor.html(),function($data){
   				 if(!$data){
						 window.hideModal("unnormalModal");
						 refresh();
					 }else{
						 hiAlert("提示",$data);
					 }
				 });
		   }
	  })
	  
	  
	  
	  
	  
    var editor = KindEditor.create('#todayNewsDetail', {
		cssPath : $path+'/js/editor/plugins/code/prettify.css',
		uploadJson : $path+'/js/editor/jsp/upload_json.jsp',
		fileManagerJson : $path+'/js/editor/jsp/file_manager_json.jsp',
		allowFileManager : true,
		items : [
		 		'undo', 'redo', '|', 'preview','cut', 'copy', 'paste',
		 		'|', 'justifyleft', 'justifycenter', 'justifyright',
		 		'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
		 		'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', 
		 		'formatblock', 'fontname', 'fontsize', '/', 'forecolor', 'hilitecolor', 'bold',
		 		'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image',// 'multiimage',
		 		'flash', 'media', 'insertfile', 'table', 'hr', 'emoticons', 'baidumap', 'pagebreak',
		 		'anchor', 'link', 'unlink', '|', 'about'
		 	],
		afterBlur: function(){this.sync();},
		afterCreate : function() {
			var self = this;
			KindEditor.ctrl(document, 13, function() {
				self.sync();
// 				document.forms['todayNewssaveForm'].submit();
			});
			KindEditor.ctrl(self.edit.doc, 13, function() {
// 				alert(self.edit.doc);
				self.sync();
// 				document.forms['todayNewssaveForm'].submit();
			});
		}
	});
    
		// 普通tree
		$('#todayNewsShowTree').bstree({
				url: $path+'/mc/carrier',
				height:'auto',
				open: false,
				checkbox:true,
				checkboxLink:false,
				showurl:false,
				checkboxPartShow:true,
				layer:[1,2,3,4]
		});
		//多选框回显
		var treecheckbox = $("#todayNewssDomainIds").val();
		//java代码 treecheckbox==null 则 treecheckbox=[]
		if(treecheckbox.length>2){
			treecheckbox=treecheckbox.substring(1,treecheckbox.length-1);
			var arr= treecheckbox.split(",");
			  $.each(arr,function(index,obj){
				  $("#todayNewsShowTree ."+$.trim(obj)).prop('checked',true);
			  });
		}
		
	    //选择文件之后执行上传  
//  	    $('#todayNewssaveForm .sure').on('click', function() {
//  	    	var title = $('#todayNewssaveForm .title').val();
//  	    	var todayNewsDetail = $('#todayNewsDetail').val();
//  	    	alert($('#todayNewsDetail').val());
// //  	    	alert($("#todayNewssaveForm").serialize());
//  	        $.ajaxFileUpload({  
//  	            url:$path+'/mc/todayNews/saveOrUpdate.do',  
//  	            secureuri:false,  
//  	            fileElementId:'todayNewsPicture',//file标签的id  
//  	            dataType: 'json',//返回数据的类型  
//  	            data:{position:position,targetDevice:targetDevice},//一同上传的数据  
//  	            success: function (data, status) {
 	            	
 	            	
//  	            },  
//  	            error: function (data, status, e) {  
//  	                alert(e);  
//  	            }  
//  	        });  
 	    });
		
		//确定按钮
// 	   $("#todayNewssaveForm .sure").on("click",function(){
// 		   $("#todayNewssaveForm").submit();
// 		   var url=$path+"/mc/todayNews/saveOrUpdate.do";
// 		   var param = $("#todayNewssaveForm").serialize();
// 		   $.post(url,param,function($data){
// 				 if(!$data){
// 					 $("#tableShowList").bootstrapTable('refresh', {
// 							url: $path+"/mc/todayNews/showList.do",
// 						});
// 					$("#unnormalModal").modal("hide");
// 				 }else{
// 					 hiAlert("提示",$data);
// 				 }
// 			 });
// 	   })
//   })
</script>
</html>