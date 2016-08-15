<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://www.youlb.com/tags/role" prefix="r"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<script type="text/javascript">
  $(function(){
	  $("#roomPermissionSearchForm .neighbor").on("change",function(){
		  var id = $(this).val();
		   //获取下一个select位置 层级需要固定
	      var nextLevel = $(this).parent().parent().next().next().find("select");
	      nextLevel.children().remove(":gt(0)");
		  $.post($path+"/mc/room/getBuildingListByNeibId.do","neighborId="+id,function($data){
			  $.each($data,function(i,obj){
				  var option = "<option value='"+obj.id+"'>"+obj.buildingName+"</option>";
			      nextLevel.append(option);
			  })
		  })
	  });
	   $("#roomPermissionSearchForm .building").on("change",function(){
		   var id = $(this).val();
		   //获取下一个select位置 层级需要固定
	      var nextLevel = $(this).parent().parent().next().next().find("select");
	      nextLevel.children().remove(":gt(0)");
		  $.post($path+"/mc/room/getUnitListBybuildingId.do","buildingId="+id,function($data){
			  $.each($data,function(i,obj){
				  var option = "<option value='"+obj.id+"'>"+obj.unitName+"</option>";
			      nextLevel.append(option);
			  })
		  })
	  });
	  
  });
</script>
<body>
	 <!-- 查询form div  --> 
    <div class="searchInfoDiv">
          <form id="roomPermissionSearchForm" action="" method="post">
           <input type="hidden" class="hiddenId" name="id" value="${id}"/>
           <table>
              <tr>
                <td><div class="firstFont">社区：</div></td>
                <td><div>
                   <select name="" class="form-control neighbor">
                     <option value="">请选择社区</option>
                     <c:forEach items="${neighborList}" var="neighbor">
                        <option value="${neighbor.id}">${neighbor.neibName}</option>
                     </c:forEach>
                   </select>
                </div></td>
                <td><div class="leftFont">楼栋：</div></td>
                <td><div>
                    <select name="" class="form-control building">
                    <option value="">请选择楼栋</option>
                     <c:forEach items="${buildingList}" var="building">
                        <option value="${building.id}">${building.buildingName}</option>
                     </c:forEach>
                   </select>
                </div></td>
                <td><div class="leftFont">单元：</div></td>
                <td><div>
                   <select name="unitId" class="form-control unit">
                     <option value="">请选择单元</option>
                     <c:forEach items="${unitList}" var="unit">
                        <option value="${unit.id}">${unit.unitName}</option>
                     </c:forEach>
                   </select>
                </div></td>
                <td><div class="leftFont"><button class="btn btn-info btn-sm reset">重置</button></div></td>
                <td><div class="leftFont"><button class="btn btn-info btn-sm search" rel="${path}/mc/room/showList.do">查询</button></div></td>
              </tr>
           </table>
        </form>
       </div>  
</body>
