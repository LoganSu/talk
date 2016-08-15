<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <script type="text/javascript">
 
 </script>
<body>
<div>
	   <div>
		 <form id="authoritysaveForm" action="">
		   <div>
		    <input type="hidden" name="id" value="${privilege.id}"/>
		    <input type="hidden" name="parentId" value="${privilege.parentId}"/>
            <table>
              <tr>
                <td><div class="leftFont"><span class="starColor">*</span>权限名称：</div></td>
                <td><div><input name="name" class="form-control required" title="权限名称不能为空" value="${privilege.name}"/></div></td>
                <td><div class="leftFont"><span class="starColor">*</span>权限key值：</div></td>
                <td><div><input name="key" class="form-control required" title="权限key值不能为空" value="${privilege.key}"/></div></td>
                <td><div class="leftFont"><span class="starColor">*</span>权限描述：</div></td>
                <td><div><input name="description" class="form-control required" title="权限描述不能为空" value="${privilege.description}"/></div></td>
              </tr>
	               <tr>
	                <td><div class="leftFont"><span class="starColor">*</span>组内排序：</div></td>
                    <td><div><input name="groupOrder" class="form-control required number" title="组内排序不能为空且为数字" value="${privilege.groupOrder}"/></div></td>
	              </tr>
           </table>
           </div>
         </form>
	   </div>
 </div>
</body>
