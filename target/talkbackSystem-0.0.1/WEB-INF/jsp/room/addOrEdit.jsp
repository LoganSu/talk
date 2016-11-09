<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<body>
<div>
	   <div>
		 <form id="roomsaveForm" action="">
		 <input type="hidden" name="id" value="${room.id}"/>
		 <input type="hidden" name="parentId" value="${room.parentId}"/>
           <table>
              <tr>
                <td><div class="leftFont"><span class="starColor">*</span>房号：</div></td>
                <td><div><input name="roomNum" class="form-control required" maxlength="5" title="房号不能为空且不大于5个数字" value="${room.roomNum}"/></div></td>
                <td><div class="leftFont"><span class="starColor">*</span>楼层：</div></td>
                <td><div><input name="roomFloor" class="form-control required number" title="楼层不能为空且为数字" value="${room.roomFloor}"/></div></td>
                <td><div class="leftFont"><span class="starColor">*</span>房产证号：</div></td>
                <td><div><input name="certificateNum" class="form-control required" title="房产证号不能为空" value="${room.certificateNum}"/></div></td>
              </tr>
              <tr>
                <td><div class="leftFont">房间归属：</div></td>
                <td><div><select name="roomType" class="form-control">
                             <option value="业主" <c:if test="${room.roomType eq '业主'}">selected="selected"</c:if>>业主</option>
                             <option value="开发商" <c:if test="${room.roomType eq '开发商'}">selected="selected"</c:if>>开发商</option>
                             <option value="物业" <c:if test="${room.roomType eq '物业'}">selected="selected"</c:if>>物业</option>
                        </select></div></td>
                <td><div class="leftFont">用途：</div></td>
                <td><div><select name="purpose" class="form-control">
                             <option value="住宅" <c:if test="${room.purpose eq '住宅'}">selected="selected"</c:if>>住宅</option>
                             <option value="商用" <c:if test="${room.purpose eq '商用'}">selected="selected"</c:if>>商用</option>
                             <option value="酒店" <c:if test="${room.purpose eq '酒店'}">selected="selected"</c:if>>酒店</option>
                        </select></div></td>
                <td><div class="leftFont">朝向：</div></td>
                <td><div><input name="orientation" class="form-control" value="${room.orientation}"/></div></td>
              </tr>
              <tr>
                <td><div class="leftFont">装修情况：</div></td>
                <td><div><input name="decorationStatus" class="form-control" value="${room.decorationStatus}"/></div></td>
                <td><div class="leftFont">建筑面积(㎡)：</div></td>
                <td><div><input name="roomArea" class="form-control number" title="建筑面积为数字类型" value="${room.roomArea}"/></div></td>
                <td><div class="leftFont">使用面积(㎡)：</div></td>
                <td><div><input name="useArea" class="form-control number" title="使用面积为数字类型"  value="${room.useArea}"/></div></td>
              </tr>
              <tr>
                <td><div class="leftFont">花园面积(㎡)：</div></td>
                <td><div><input name="gardenArea" class="form-control number" title="花园面积为数字类型"  value="${room.gardenArea}"/></div></td>
                <td><div class="leftFont">是否空置：</div></td>
                <td><div><select name="useStatus" class="form-control">
                             <option value="是" <c:if test="${room.useStatus eq '是'}">selected="selected"</c:if>>是</option>
                             <option value="否" <c:if test="${room.useStatus eq '否'}">selected="selected"</c:if>>否</option>
                        </select></div></td>
              </tr>
              <tr>
                <td><div class="leftFont">备注：</div></td>
                <td colspan="5"><div><input style="width: 740px" name="remark" class="form-control" value="${room.remark}"/></div></td>
              </tr>
           </table>
         </form>
	   </div>
 </div>
</body>
</html>