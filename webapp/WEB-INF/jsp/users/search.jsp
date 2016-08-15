<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://www.youlb.com/tags/role" prefix="r"%>
    
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	<!-- 功能按钮 div-->
<!-- 	<div class="functionBut"> -->
<!--          <ul class="list-unstyled list-inline"> -->
            <!-- 添加saveOrUpdateBtn类 跳转到自己定义的add页面  rel跳转页面url  saveUrl 保存到数据库url -->
<%--             <r:role auth="住户信息/添加"> --%>
<%--                 <li><button class="btn btn-success btn-sm saveOrUpdateBtn" rel="${path}/mc/users/toSaveOrUpdate.do" saveUrl="${path}/mc/users/saveOrUpdate.do">添加</button></li> --%>
<%--             </r:role> --%>
<%--             <r:role auth="住户信息/修改"> --%>
<%--                 <li><button class="btn btn-warning btn-sm saveOrUpdateBtn" rel="${path}/mc/users/toSaveOrUpdate.do" saveUrl="${path}/mc/users/saveOrUpdate.do">修改</button></li> --%>
<%--             </r:role> --%>
<!--          </ul> -->
<!-- 	 </div> -->
	 <!-- 查询form div  --> 
    <div class="searchInfoDiv">
          <form id="usersSearchForm" action="" method="post">
           <input type="hidden" class="hiddenId" name="id" value="${id}"/>
           <table>
              <tr>
<!--                 <td><div class="firstFont">出租人编号：</div></td> -->
<!--                 <td><div><input name="hostNum" class="form-control"/></div></td> -->
                <td><div class="firstFont">用户名：</div></td>
                <td><div><input name="username" class="form-control"/></div></td>
                <td><div class="leftFont">手机号：</div></td>
                <td><div><input name="phone" class="form-control"/></div></td>
<!--                 <td><div class="leftFont">用户状态：</div></td> -->
<!--                 <td><div><input name="status" class="form-control"/></div></td> -->
                <td><div class="leftFont"><button class="btn btn-info btn-sm reset">重置</button></div></td>
                <td><div class="leftFont"><button class="btn btn-info btn-sm search" rel="${path}/mc/users/showList.do">查询</button></div></td>
              </tr>
           </table>
        </form>
       </div>  
</body>
