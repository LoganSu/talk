<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<body>
<script type="text/javascript">
$(function(){
	
	  var id = $("#companyDepartmentsaveForm [name='id']").val();
	  var treecheckbox = "${department.domainIds}";
	  zTree("companyDepartmentShowTree", ["id","name","level"],["nocheckLevel","0234"],$path+"/mc/domain/getNodes.do",true,{"Y": "", "N": ""},null,dataEcho(id,treecheckbox), null)
	
// 	domainTree("companyDepartmentShowTree", $path+'/mc/carrier', false, true, false, false, true, [1], 'domainIds');
	// 域tree
// 	$('#companyDepartmentShowTree').bstree({
// 			url: $path+'/mc/carrier',
// 			height:'auto',
// 			open: false,
// 			checkbox:true,
// 			checkboxPartShow:true,//显示部分多选框
// 			layer: [1],
// 			treecheckboxFiledName:'domainIds',
// 			showurl:false
// 	});
	//域数据回显
// 	var domainIds = $("#companyDepartmentdomainIds").val();
	//java代码 treecheckbox==null 则 treecheckbox=[]
// 	alert(domainIds.toSource());
// 	if(domainIds.length>2){
// 		domainIds=domainIds.substring(1,domainIds.length-1);
// 		var arr= domainIds.split(",");
// 		  $.each(arr,function(index,obj){
// 			  $("#companyDepartmentShowTree ."+$.trim(obj)).prop('checked',true);
// 		  });
// 	}else{
// 		var checkboxArr = $("#companyDepartmentShowTree .treecheckbox")
// 		$.each(checkboxArr,function(index,obj){
// 			  $(this).prop('checked',true);
// 		  });
// 	}
	
})

</script>

<div>
	   <div>
		 <form id="companyDepartmentsaveForm" class="departmentsaveForm" action="">
		   <input type="hidden" name="id" value="${department.id}"/>
<%-- 		   <input type="hidden" id="companyDepartmentdomainIds" value="${department.domainIds}"/> --%>
           <table>
              <tr style="">
                <td><div class="firstFont"><span class="starColor">*</span>公司名称：</div></td>
                <td><div><input name="departmentName" class="form-control required" maxlength="20" title="公司名称不能为空" value="${department.departmentName}"/></div></td>
                <td><div class="leftFont"><span class="starColor">*</span>公司电话：</div></td>
                <td><div><input name="tel" class="form-control required" title="公司电话不能为空" maxlength="15" placeholder="手机或座机" value="${department.tel}"/></div></td>
                <td><div class="leftFont"><span class="starColor">*</span>公司地址：</div></td>
                <td><div><input name="address" style="width: 300px" class="form-control required" maxlength="100" title="公司地址不能为空" value="${department.address}"/></div></td>
              </tr>
              <tr style="margin-bottom: 5px">
                <td><div class="firstFont">公司描述：</div></td>
                <td colspan="3"><div><input name="description" style="width: 400px" maxlength="100" class="form-control" value="${department.description}"/></div></td>
              </tr>
           </table>
           	  <ul id="companyDepartmentShowTree" class="ztree" style="width:260px; overflow:auto;"></ul>
           
           <!-- 详情不显示按钮 -->
	      <div class="modal-footer">
               <input type="button" class="btn btn-primary sure" value="确定"/> 
		       <input type="button" class="btn btn-default" data-dismiss="modal" value="关闭"/> 
           </div>
         </form>
	   </div>
 </div>
</body>
</html>