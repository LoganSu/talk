<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<body>
<div>
	   <div>
		 <form id="dwellersaveForm" action="">
		   <input type="hidden" name="id" value="${dweller.id}"/>
		   <input type="hidden" id="dwellerDomainIds" value="${dweller.treecheckbox}"/>
           <table>
              <tr>
                <td><div class="firstFont"><span class="starColor">*</span>姓名：</div></td>
                <td><div><input name="fname" class="form-control required" title="居住人姓名必填" value="${dweller.fname}"/></div></td>
                <td><div class="leftFont">性别：</div></td>
                <td><div><input type="radio" name="sex" <c:if test="${dweller.sex=='1'}">checked="checked"</c:if> value="1" />男
                <input type="radio" name="sex" <c:if test="${dweller.sex=='2'}">checked="checked"</c:if> value="2"/>女</div></td>
                <td><div class="leftFont"><span class="starColor">*</span>身份证号码：</div></td>
                <td><div><input name="idNum" class="form-control required" title="身份证号码必填" maxlength="18" value="${dweller.idNum}"/></div></td>
              </tr>
              <tr>
                <td><div class="firstFont">联系电话：</div></td>
                <td><div><input name="phone" class="form-control" title="联系电话必填" value="${dweller.phone}" placeholder="建议使用手机号码"/></div></td>
                 <td><div class="leftFont">邮箱：</div></td>
                <td><div><input name="email" class="form-control" value="${dweller.email}"/></div></td>
<!--                 <td><div class="leftFont">户主：</div></td> -->
<%--                 <td><div><input type="radio" name="dwellerType" <c:if test="${dweller.dwellerType=='2'}">checked="checked"</c:if> value="0"/>否 --%>
<%--                 <input type="radio" name="dwellerType" <c:if test="${dweller.dwellerType=='1'}">checked="checked"</c:if> value="1" />是</div></td> --%>
              </tr>
              <tr>
                 <td><div class="firstFont">文化程度：</div></td>
		                <td><div>
		                   <select  class="form-control" name="education">
		                     <option <c:if test="${dweller.education=='1'}">selected="selected"</c:if>  value="1">小学</option>
		                     <option <c:if test="${dweller.education=='2'}">selected="selected"</c:if>  value="2">初中</option>
		                     <option <c:if test="${dweller.education=='3'}">selected="selected"</c:if>  value="3">高中（中专、中技）</option>
		                     <option <c:if test="${dweller.education=='4'}">selected="selected"</c:if>  value="4">大专</option>
		                     <option <c:if test="${dweller.education=='5'}">selected="selected"</c:if>  value="5">本科</option>
		                     <option <c:if test="${dweller.education=='6'}">selected="selected"</c:if>  value="6">研究生</option>
		                     <option <c:if test="${dweller.education=='7'}">selected="selected"</c:if>  value="7">其他</option>
		                </select></div></td>
                 <td><div class="leftFont">籍贯：</div></td>
                 <td><div><input name="nativePlace" class="form-control" value="${dweller.nativePlace}"/></div></td>
              </tr>
              <tr>
                 <td><div class="firstFont">工作单位：</div></td>
                 <td colspan="5"><div><input style="width: 500px" name="companyName" maxlength="100" class="form-control" value="${dweller.companyName}"/></div></td>
              </tr>
              <tr>
                 <td><div class="firstFont">备注：</div></td>
                 <td colspan="5"><div><input style="width: 500px" name="remark" maxlength="200" class="form-control" value="${dweller.remark}"/></div></td>
              </tr>
           </table>
           <div class="firstFont">地址选择：</div>
           <div>
             <ul id="dwellerShowTree" class="ztree" style="width:260px; overflow:auto;"></ul>
             
           </div>  
         </form>
	   </div>
 </div>
</body>
<script type="text/javascript">
  $(function(){
	  
	  var id = $("#dwellersaveForm [name='id']").val();
	  var treecheckbox = "${dweller.treecheckbox}";
	  var parentIds = "${parentIds}"
	  zTreeObj = zTree("dwellerShowTree", ["id","name","level"],["nocheckLevel","0123"],$path+"/mc/domain/getNodes.do",true,{"Y": "ps", "N": "ps"},null,dwrellerDataEcho(id,treecheckbox,parentIds), null)

  })
	//数据回显函数
	  function dwrellerDataEcho(id,treecheckbox,parentIds){
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
</script>
</html>