<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<body>
<div>
	   <div>
		 <form id="unitsaveForm" action="">
		   <input type="hidden" name="id" value="${unit.id}"/>
		   <input type="hidden" name="parentId" value="${unit.parentId}"/>
           <table>
              <tr>
                <td><div class="firstFont"><span class="starColor">*</span>单元名称：</div></td>
                <td><div><input name="unitName" class="form-control required" title="单元名称不能为空" value="${unit.unitName}"/></div></td>
                <td><div class="leftFont"><span class="starColor">*</span>单元编号：</div></td>
                <td><div><input name="unitNum" class="form-control required number" maxlength="2" title="单元编号不能为空且为2位数字" value="${unit.unitNum}"/></div></td>
              </tr>
              <tr>
                <td><div>备注：</div></td>
                <td colspan="3"><div><input style="width: 740px" name="remark" class="form-control" value="${unit.remark}"/></div></td>
              </tr>
           </table>
         </form>
	   </div>
 </div>
</body>
</html>