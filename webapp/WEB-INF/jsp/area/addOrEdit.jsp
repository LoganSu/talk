<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<body>
<script type="text/javascript">
$(function(){
	$("#areasaveForm .province").on("change",function(){
		  var value = $(this).val();
		  var arr = value.split("_");
		  var id = arr[0];
		  if(id){
		    address($(this),$path+"/mc/area/getAddressByParentId.do?parentId="+id);
		  }
	});
	
	//获取社区编号
	$("#areasaveForm .city").on("change",function(){
		  var city = $(this).val();
		  if(city){
			  $.post($path+"/mc/area/getAreaCode.do","city="+city,function($data){
				   $("#areasaveForm .areaNum").val($data);
				  if($data){
					  $("#areasaveForm .areaNum").attr("readonly","readonly");
				  }else{
					  $("#areasaveForm .areaNum").removeAttr("readonly");
				  }
			  })
			  
		  }
 	});
	
})

var address=function(_this,url){
	 //获取下一个select位置 层级需要固定
    var nextLevel = _this.parent().parent().next().next().find("select");
    nextLevel.children().remove(":gt(0)");
	  $.post(url,function($data){
		  $.each($data,function(i,obj){
			  var option = "<option value='"+obj.fshortname+"'>"+obj.fshortname+"</option>";
		      nextLevel.append(option);
		  })
	  })
}

</script>
<div>
	   <div>
		 <form id="areasaveForm" action="">
		   <input type="hidden" name="id" value="${area.id}"/>
           <table>
              <tr>
                <td><div class="firstFont"><span class="starColor">*</span>省份：</div></td>
                <td><div>
                  <select name="province" class="form-control province">
                     <option value="">请选择省份</option>
                    <c:forEach items="${provinceList}" var="province">
                       <option value="${province.id}_${province.fshortname}" <c:if test="${province.fshortname==area.province}">selected="selected"</c:if>>${province.fshortname}</option>
                    </c:forEach>
                  </select>
                
                </div></td>
                <td><div class="leftFont"><span class="starColor">*</span>城市：</div></td>
                <td><div>
                   <select name="city" class="form-control city">
                    <option value="">请选择城市</option>
                    <c:if test="${area.id!=null}">
                      <option value="${area.city }" selected="selected">${area.city }</option>
                    </c:if>
                  </select>
                </div></td>
                <td><div class="leftFont"><span class="starColor">*</span>编号：</div></td>
                <td><div><input name="areaNum" class="form-control areaNum required number" title="编号不能为空且为3个数字，请填写区号" readonly="readonly" maxlength="3" value="${area.areaNum}"/></div></td>
              </tr>
              <tr>
                <td><div>备注：</div></td>
                <td colspan="5"><div><input style="width: 740px" name="remark" class="form-control" value="${area.remark}"/></div></td>
              </tr>
           </table>
         </form>
	   </div>
 </div>
</body>
</html>