<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<body>
<script type="text/javascript">
$(document).ready(function() {
//     $('#example-multiple-selected').multiselect();
});
//    $("#buildingsaveForm .neighbName").on("change",function(){
// 	   var neibNum = $(this).children("option:selected").attr("neibNum");
// 	   $("#buildingsaveForm  .neibNum").val(neibNum);
//    })
</script>
<div>
	   <div>
		 <form id="buildingsaveForm" action="">
		 <input type="hidden" name="id" value="${building.id}"/>
		 <input type="hidden" name=parentId value="${building.parentId}"/>
           <table>
<!--               <tr> -->
<!--                 <td><div class="leftFont">社区名称：</div></td> -->
<!--                 <td><div> -->
<!--                    <select name="neibId" class="form-control neighbName"> -->
<%--                       <c:forEach items="${neighborList}" var="neighbor"> --%>
<%--                          <option neibNum="${neighbor.neibNum}" value="${neighbor.id}">${neighbor.neighbName}</option> --%>
<%--                       </c:forEach> --%>
<!--                    </select> -->
<!--                 </div></td> -->
<!--                 <td><div class="leftFont">社区编号：</div></td> -->
<%--                 <td><div><input value="${neighborList[0].neibNum}" class="form-control neibNum" readonly="readonly"/></div></td> --%>
<!--               </tr> -->
              <tr>
                <td><div class="leftFont"><span class="starColor">*</span>楼栋名称：</div></td>
                <td><div><input name="buildingName" value="${building.buildingName}" maxlength="20" title="楼栋名称不能为空" class="form-control required"/></div></td>
                <td><div class="leftFont"><span class="starColor">*</span>呼叫号码：</div></td>
                <td><div><input name="buildingNum" value="${building.buildingNum}" maxlength="3" title="呼叫号码不能为空且为3位数字" class="form-control required"/></div></td>
                <td><div class="leftFont"><span class="starColor">*</span>层数：</div></td>
                <td><div><input name="totalFloor" value="${building.totalFloor}" title="层数不能为空且为正整数" maxlength="4" class="form-control required"/></div></td>
              </tr>
              <tr>
                <td><div class="leftFont">楼高(m)：</div></td>
                <td><div><input name="buildHeight" value="${building.buildHeight}"  class="form-control number" title="楼高为数字类型"/></div></td>
                <td><div class="leftFont">楼栋类型：</div></td>
                <td><div><select name="buildType" class="form-control">
                    <option <c:if test="${'家用' eq building.buildType}">selected</c:if> value="家用">家用</option>
                    <option <c:if test="${'商业' eq building.buildType}">selected</c:if> value="商业">商业</option>
                    <option <c:if test="${'租赁' eq building.buildType}">selected</c:if> value="租赁">租赁</option>
                    <option <c:if test="${'其他' eq building.buildType}">selected</c:if> value="其他">其他</option>
                </select></div></td>
                <td><div class="leftFont">是否启用网关：</div></td>
                 <td><div>
                    <select name="createSipNum" class="form-control">
                      <option value="" <c:if test="${building.createSipNum==null or building.createSipNum==''}">selected="selected"</c:if> >否</option>
                      <option value="2" <c:if test="${building.createSipNum=='2'}">selected="selected"</c:if>>是</option>
                    </select>
                 </div></td>
<!--                 <td><div class="leftFont">特别授权楼栋：</div></td> -->
<!--                 <td><div> -->
<!--                   <select id="example-multiple-selected" multiple="multiple"> -->
<!-- 					    <option value="1">Option 1</option> -->
<!-- 					    <option value="2" selected="selected">Option 2</option> -->
<!-- 					    <option value="3" selected="selected">Option 3</option> -->
<!-- 					    <option value="4">Option 4</option> -->
<!-- 					    <option value="5">Option 5</option> -->
<!-- 					    <option value="6">Option 6</option> -->
<!-- 					</select> -->
                
<!--                  </div></td> -->
              </tr>
              <tr>
                <td><div class="leftFont">备注：</div></td>
                <td colspan="5"><div><input style="width: 750px" maxlength="200" name="remark" class="form-control" value="${building.remark}"/></div></td>
              </tr>
           </table>
         </form>
	   </div>
 </div>
</body>
</html>