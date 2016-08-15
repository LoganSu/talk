<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://www.youlb.com/tags/role" prefix="r"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%
String  departmentTree= "";
if(request.getAttribute("departmentTree")!=null){
	departmentTree= (String)request.getAttribute("departmentTree");
}
%>
<script type="text/javascript">
$(function(){
	$('#workerSearchShowTree').bstree({
		url: $path+'/mc/departmentTree',
		height:'auto',
		open: false,
		checkbox:true,
		checkboxLink:false,
		treecheckboxFiledName:'departmentIds',
		showurl:false
    }); 
	var departmentId = $("#workerdepartmentId").val();
	if(departmentId){
	 $("#workerShowTree ."+$.trim(departmentId)).prop('checked',true);
	}
	var departmentTree = <%=departmentTree%>
	var select = $("#workerSearchForm .departmentId")
	showDepartmentTree(departmentTree,select);
}); 

// function tree(departmentTree,select){
// 	if(departmentTree){
// 		$.each(departmentTree,function(i,obj){
// //			var select = $("#workerSearchForm .departmentId")
// 			var padding = obj.layer*20;
// 			var option='<option style="padding-left: '+padding+'px;" value="'+obj.id+'">'+obj.departmentName+'</option>';
// 			select.append(option);
// 			var subTree = obj.departmentTree;
// 			tree(subTree,select);
// 		});
// 	}
// }

</script>
</head>
<body>
	<!-- 功能按钮 div-->
	<div class="functionBut">
         <ul class="list-unstyled list-inline">
            <!-- 添加saveOrUpdateBtn类 跳转到自己定义的add页面  rel跳转页面url  saveUrl 保存到数据库url -->
            <r:role auth="员工管理/添加">
                <li><button class="btn btn-success btn-sm saveOrUpdateBtn" rel="${path}/mc/worker/toSaveOrUpdate.do" saveUrl="${path}/mc/worker/saveOrUpdate.do">添加</button></li>
            </r:role>
            <r:role auth="员工管理/修改">
                <li><button class="btn btn-warning btn-sm saveOrUpdateBtn" rel="${path}/mc/worker/toSaveOrUpdate.do" saveUrl="${path}/mc/worker/saveOrUpdate.do">修改</button></li>
            </r:role>
            <r:role auth="员工管理/删除">
	            <!--delete类 公共删除  -->
	            <li><button class="btn btn-danger btn-sm delete" rel="${path}/mc/worker/delete.do">删除</button></li>
            </r:role>
         </ul>
	 </div>
	 <!-- 查询form div  --> 
    <div class="searchInfoDiv">
          <form id="workerSearchForm" action="" method="post">
           <input type="hidden" class="hiddenId" name="id" value="${id}"/>
           <table>
              <tr>
                <td><div class="firstFont">姓名：</div></td>
                <td><div><input name="workerName" class="form-control"/></div></td>
                <td><div class="leftFont">手机号：</div></td>
                <td><div><input name="phone" class="form-control"/></div></td>
                <td><div class="leftFont">开通状态：</div></td>
                <td><div>
                     <select class="form-control" name="status">
                        <option value="">全部</option>
                        <option value="1">开通</option>
                        <option value="2">暂停</option>
                        <option value="3">关闭</option>
                     </select>
                </div></td>
              </tr>
              <tr>
                <td><div class="firstFont">所属部门：</div></td>
                <td><div>
                     <select class="form-control departmentId" name="departmentId">
                       <option value="">--请选择--</option>
                     </select>
                </div></td>
                <td><div class="leftFont"><button class="btn btn-info btn-sm reset">重置</button></div></td>
                <td><div class="leftFont"><button class="btn btn-info btn-sm search" rel="${path}/mc/worker/showList.do">查询</button></div></td>
              </tr>
           </table>
        </form>
       </div>  
</body>
