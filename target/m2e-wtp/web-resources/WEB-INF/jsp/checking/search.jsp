<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://www.youlb.com/tags/role" prefix="r"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
//时间控件
$(".datepicker").datepicker();
</script>
</head>
<body>
	<!-- 功能按钮 div-->
	<div class="functionBut">
         <ul class="list-unstyled list-inline">
<!--             添加saveOrUpdateBtn类 跳转到自己定义的add页面  rel跳转页面url  saveUrl 保存到数据库url -->
            <r:role auth="考勤管理/导出">
                <li><button  class="btn btn-info btn-sm exportChecking">导出</button></li>
            </r:role>
<%--             <r:role auth="区域/修改"> --%>
<%--                 <li><button class="btn btn-warning btn-sm areaSaveOrUpdateBtn" rel="${path}/mc/area/toSaveOrUpdate.do" saveUrl="${path}/mc/area/saveOrUpdate.do">修改</button></li> --%>
<%--             </r:role> --%>
<%--             <r:role auth="区域/删除"> --%>
<!-- 	            delete类 公共删除  --> 
<%-- 	            <li><button class="btn btn-danger btn-sm delete" rel="${path}/mc/area/delete.do">删除</button></li> --%>
<%--             </r:role> --%>
         </ul>
	 </div>
	 <!-- 查询form div  --> 
    <div class="searchInfoDiv">
          <form id="checkingSearchForm" action="" method="post">
           <table>
              <tr>
                <td><div class="firstFont">开始日期：</div></td>
                <td><div><input name="startTime" value="<fmt:formatDate value='${now}' type='date' pattern="yyyy-MM-dd"/>" readonly="readonly" class="form-control datepicker"/></div></td>
                <td><div class="leftFont">结束日期：</div></td>
                <td><div><input name="endTime" value="<fmt:formatDate value='${now}' type='date' pattern="yyyy-MM-dd"/>" readonly="readonly" class="form-control datepicker"/></div></td>
                <td><div class="leftFont">员工编号：</div></td>
                <td><div><input name="workerNum" class="form-control"/></div></td>
<!--                 <td><div class="leftFont"><button class="btn btn-info btn-sm reset">重置</button></div></td> -->
                <td><div class="leftFont"><button class="btn btn-info btn-sm search" rel="${path}/mc/cardRecord/checkingshowList.do">查询</button></div></td>
              </tr>
           </table>
        </form>
       </div>
<iframe style="width:0; height:0;display: none;" id="checkingFrame" name="checkingFrame"></iframe>
</body>
