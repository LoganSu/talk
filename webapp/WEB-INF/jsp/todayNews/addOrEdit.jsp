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
		 <form id="todayNewssaveForm" name="todayNewssaveForm" action="${path}/mc/todayNews/saveOrUpdate.do" target="todayNewsSubmitFrame" enctype="multipart/form-data" method="post">
		   <input type="hidden" name="id" value="${todayNews.id}"/>
		   <input type="hidden" id="todayNewssDomainIds" value="${todayNews.treecheckbox}"/>
           <table>
               <tr>
                <td><div class="firstFont"><span class="starColor">*</span>标题名称：</div></td>
                <td><div><input name="title" style="width: 320px" class="form-control required title" title="标题名称不能为空！" value="${todayNews.title}"/></div></td>
              </tr>
              <tr>
                <td><div class="firstFont">上传标题图片：</div></td>
                <td colspan="5"><div><input style="width: 320px" id="todayNewsPicture" name="picture" type="file" class="form-control"/></div></td>
              </tr>
              <c:if test="${todayNews.pictureUrl!=null}">
                 <tr><td><div class="firstFont"></div></td><td colspan="5"><div><img src="${todayNews.pictureUrl}" style="width: 150px;height: 100px"/></div></td></tr>
              </c:if>
              <tr>
                <td><div class="firstFont"><span class="starColor">*</span>内容：</div></td>
                <td colspan="5"><div>
                    <textarea style="width: 680px;height: 400px" id="todayNewsDetail" name="todayNewsDetail" rows="" cols="" class="form-control"><%=htmlspecialchars(htmlData)%></textarea>
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
		               <input type="submit" class="btn btn-primary sure" value="确定"/> 
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
		afterCreate : function() {
			var self = this;
// 			alert(self.toSource());
			KindEditor.ctrl(document, 13, function() {
				self.sync();
				document.forms['todayNewssaveForm'].submit();
			});
			KindEditor.ctrl(self.edit.doc, 13, function() {
				self.sync();
				document.forms['todayNewssaveForm'].submit();
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
				showurl:false
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