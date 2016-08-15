<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<body>
<script type="text/javascript">
$(function(){
	
})

</script>

<div>
	   <div>
		 <form id="departmentsaveForm" class="departmentsaveForm" action="">
		   <input type="hidden" name="id" value="${department.id}"/>
		   <input type="hidden" name="parentId" value="${parentDepartment.id}"/>
		   <input type="hidden" name="layer" value="${parentDepartment.layer}"/>
		   <table>
		      <tr style="font-size: 16px">
                <td><div>上级名称：</div></td>
                <td><div>${parentDepartment.departmentName}</div></td>
              </tr>  
		   </table>
           <table>
              <tr>
                <td><div class="firstFont"><span class="starColor">*</span>部门名称：</div></td>
                <td><div><input name="departmentName" class="form-control required" maxlength="20" value="${department.departmentName}"/></div></td>
<!--               </tr> -->
<!--               <tr style="margin-bottom: 5px"> -->
                <td><div class="leftFont"><span class="starColor">*</span>部门描述：</div></td>
                <td><div><input name="description" class="form-control required" maxlength="100" value="${department.description}"/></div></td>
              </tr>
           </table>
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