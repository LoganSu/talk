<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://www.youlb.com/tags/role" prefix="r"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
$(function(){
	$(document).on("click",".roomImport",function(){
		 $("#importRoomfoForm").submit();
	})
})
</script>
</head>
<body>
	<!-- 功能按钮 div-->
	<div class="functionBut">
         <ul class="list-unstyled list-inline">
         <r:role auth="房间/添加">
            <!-- 添加saveOrUpdateBtn类 跳转到自己定义的add页面  rel跳转页面url  saveUrl 保存到数据库url -->
            <li><button class="btn btn-success btn-sm saveOrUpdateBtn" rel="${path}/mc/room/toSaveOrUpdate.do?parentId=${parentId}" saveUrl="${path}/mc/room/saveOrUpdate.do">添加</button></li>
          </r:role>
          <r:role auth="房间/修改">  
            <li><button class="btn btn-warning btn-sm saveOrUpdateBtn" rel="${path}/mc/room/toSaveOrUpdate.do?parentId=${parentId}" saveUrl="${path}/mc/room/saveOrUpdate.do">修改</button></li>
           </r:role>
<%--           <r:role auth="户主解绑"> --%>
<!--              <li><button class="btn btn-danger btn-sm unbindingHomeHost">户主解绑</button></li> -->
<%--           </r:role> --%>
          <r:role auth="房间/删除">  
            <!--delete类 公共删除  -->
             <li><button class="btn btn-danger btn-sm delete" rel="${path}/mc/room/delete.do">删除</button></li>
          </r:role>
<%--           <r:role auth="房间/导入">   --%>
            <!--delete类 公共删除  -->
              <li><a href="${path}/mc/room/singleDownModel.do"><button class="btn btn-primary btn-sm">模板下载</button></a></li>
<%--           </r:role>   --%>
<%--           <r:role auth="房间/导出">   --%>
            <!--delete类 公共删除  -->
             <li><a href="${path}/mc/room/singleDownfile.do?parentId=${parentId}"><button class="btn btn-info btn-sm">导出</button></a></li>
             <li><button class="btn btn-info btn-sm roomImport" rel="${path}/mc/room/singleDownfile.do">导入</button></li>
              <li style="padding-left: 0px;">
                     <form id="importRoomfoForm" action="${path}/mc/room/importRoomInfo.do" target="roomInfoSubmitFrame" enctype="multipart/form-data" method="post">
                       <input type="hidden" name="parentId" value="${parentId}" />
                       <input type="file" name="roomInfo" class="file"/>
                     </form>
                   </li>
<%--           </r:role>   --%>
         </ul>
	 </div>
	 <!-- 查询form div-->     
    <div class="searchInfoDiv">
          <form id="roomSearchForm" action="" method="post">
          <input type="hidden" value="${parentId}" name="parentId">
           <table>
              <tr>
                <td><div class="firstFont">房号：</div></td>
                <td><div><input name="roomNum" class="form-control"/></div></td>
                <td><div class="leftFont">楼层：</div></td>
                <td><div><input name="roomFloor" class="form-control"/></div></td>
                <td><div class="leftFont">房产证号：</div></td>
                <td><div><input name="certificateNum" class="form-control"/></div></td>
                <td><div class="leftFont"><button class="btn btn-info btn-sm reset">重置</button></div></td>
                <td><div class="leftFont"><button class="btn btn-info btn-sm search" rel="${path}/mc/room/showList.do">查询</button></div></td>
              </tr>
           </table>
        </form>
       </div>
       <div class="searchInfoDiv tableTitle">
        <span>地区>>社区>>楼栋>>单元>>房间列表：</span>
      </div>
          <!-- 隐藏iframe设置表单的目标为这个嵌入框架  使页面效果不会跳转 -->
 <iframe style="width:0; height:0;display: none;" id="roomInfoSubmitFrame" name="roomInfoSubmitFrame"></iframe>
</body>
