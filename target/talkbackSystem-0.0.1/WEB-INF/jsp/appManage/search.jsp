<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://www.youlb.com/tags/role" prefix="r"%>
    
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
            <!-- 添加saveOrUpdateBtn类 跳转到自己定义的add页面  rel跳转页面url  saveUrl 保存到数据库url -->
            <r:role auth="移动端APP管理/添加">
                <li><button class="btn btn-success btn-sm appManageAdd">添加</button></li>
            </r:role>
            <r:role auth="移动端APP管理/修改">
                <li><button class="btn btn-warning btn-sm appManageAdd">修改</button></li>
            </r:role>
            <r:role auth="移动端APP管理/删除">
	            <!--delete类 公共删除  -->
	            <li><button class="btn btn-danger btn-sm delete" rel="${path}/mc/appManage/delete.do">删除</button></li>
            </r:role>
         </ul>
	 </div>
	 <!-- 查询form div  --> 
    <div class="searchInfoDiv">
          <form id="appManageSearchForm" action="" method="post">
          <!--类型区分   -->
          <input type="hidden" name="appType" value="${appType}" id="appManageAppType"/>
           <table>
              <tr>
                <td><div class="firstFont">版本名称：</div></td>
                <td><div><input name="versionName" class="form-control"/></div></td>
                <td><div class="leftFont">版本号：</div></td>
                <td><div><input name="versionCode" class="form-control"/></div></td>
                <td><div class="leftFont">更新时间：</div></td>
                <td><div><input name="createTimeSearch" class="form-control datepicker"/></div></td>
                <td><div class="leftFont"><button class="btn btn-info btn-sm reset">重置</button></div></td>
                <td><div class="leftFont"><button class="btn btn-info btn-sm search" rel="${path}/mc/appManage/showList.do">查询</button></div></td>
              </tr>
           </table>
        </form>
       </div>  
<!--     <div class="searchInfoDiv"> -->
<!--       <span>地区列表：</span> -->
<!--     </div> -->
</body>
