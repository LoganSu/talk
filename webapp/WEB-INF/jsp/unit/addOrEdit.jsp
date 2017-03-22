<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
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
                <td><div class="leftFont"><span class="starColor">*</span>呼叫号码：</div></td>
                <td><div><input name="unitNum" class="form-control required number" maxlength="5" title="呼叫号码不能为空且小于5位数字" value="${unit.unitNum}"/></div></td>
                 <td><div class="leftFont">是否启用网关：</div></td>
                 <td><div>
                    <select name="createSipNum" class="form-control">
                      <option value="" <c:if test="${unit.createSipNum==null or unit.createSipNum==''}">selected="selected"</c:if>>否</option>
                      <option value="2" <c:if test="${unit.createSipNum=='2'}">selected="selected"</c:if>>是</option>
                    </select>
                 </div></td>
              
              </tr>
              <tr>
                <td><div>备注：</div></td>
                <td colspan="5"><div><input style="width: 740px" name="remark" class="form-control" value="${unit.remark}"/></div></td>
              </tr>
           </table>
         </form>
	   </div>
 </div>
</body>
</html>