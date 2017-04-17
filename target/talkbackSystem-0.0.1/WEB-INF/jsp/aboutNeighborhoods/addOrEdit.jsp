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
<script type="text/javascript">
$(function(){
	var editor = KindEditor.create('#aboutNeighborhoodsDetail', {
		cssPath : $path+'/js/editor/plugins/code/prettify.css',
		uploadJson : $path+'/js/editor/jsp/upload_json.jsp',
		fileManagerJson : $path+'/js/editor/jsp/file_manager_json.jsp',
		allowFileManager : true,
		allowImageRemote:false,
		items : [
		 		'undo', 'redo', '|', 'preview','cut', 'copy', 'paste',
		 		'|', 'justifyleft', 'justifycenter', 'justifyright',
		 		'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
		 		'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', 
		 		'formatblock', 'fontname', 'fontsize', '/', 'forecolor', 'hilitecolor', 'bold',
		 		'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image',// 'multiimage',
		 		 'table', 'hr', 'emoticons','pagebreak',
		 		'anchor', 'link', 'unlink', '|', 'about'
		 	],
		afterCreate : function() {
			var self = this;
// 			alert(self.toSource());
			KindEditor.ctrl(document, 13, function() {
				self.sync();
				document.forms['aboutNeighborhoodssaveForm'].submit();
			});
			KindEditor.ctrl(self.edit.doc, 13, function() {
				self.sync();
				document.forms['aboutNeighborhoodssaveForm'].submit();
			});
		}
	});
})

</script>
<div>
	   <div>
		 <form id="aboutNeighborhoodssaveForm" action="${path}/mc/aboutNeighborhoods/saveOrUpdate.do" target="aboutNeighborhoodsSubmitFrame" enctype="multipart/form-data" method="post">
		   <input type="hidden" name="id" value="${aboutNeighborhoods.id}"/>
		   <input type="hidden" name="neighborDomainId" value="${neighborDomainId}"/>
		   <table>
              <tr>
                <td><div class="firstFont"><span class="starColor">*</span>栏目标题：</div></td>
                <td><div>
                  <input name="headline" class="form-control required" maxlength="4" title="栏目标题不能为空" value="${aboutNeighborhoods.headline}"/>
                </div></td>
               </tr>
           </table>
<!--            <table style="margin-top: 6px"> -->
<!--               <tr> -->
<!--                 <td> -->
<!--                    <div class="firstFont"><span class="starColor">*</span>图标：</div> -->
<!--                 </td> -->
<%--                 <c:forEach items="${iconList}" var="icon"> --%>
<!-- 	                <td> -->
<!-- 	                   <div class="input-group" style="width: 60px"> -->
<%-- 		                   <input name="icon" type="radio" style="width: 20px" class="form-control" <c:if test="${icon.fvalue==aboutNeighborhoods.icon}">checked="checked"</c:if> title="请选择图标" value="${icon.fvalue}"/> --%>
<!-- 					       <span class="input-group-btn"> -->
<%-- 		                   <img style="width: 40px;height: 40px" alt="" src="${icon.fvalue}"> --%>
<!-- 					       </span> -->
<!-- 				       </div> -->
<!-- 				     </td> -->
<%--                 </c:forEach> --%>
<!-- 			   </tr> -->
<!-- 		   </table> -->
           <table style="margin-top: 6px">
               <tr>
                <td><div class="firstFont"><span class="starColor">*</span>内容：</div></td>
                <td colspan="5"><div>
                    <textarea style="width: 680px;height: 400px;overflow-x: auto; " id="aboutNeighborhoodsDetail" name="aboutNeighborhoodsDetail" rows="" cols="" class="form-control"><%=htmlspecialchars(htmlData)%></textarea>
                </div></td>
              </tr>
           </table>
            <!-- 详情不显示按钮 -->
	           <div class="modal-footer">
		            <input type="submit" class="btn btn-primary sure" value="确定"/> 
		            <input type="button" class="btn btn-default" data-dismiss="modal" value="关闭"/> 
		       </div>
         </form>
	   </div>
 </div>
  <iframe name="aboutNeighborhoodsSubmitFrame" style='display:none'></iframe>
</body>
</html>