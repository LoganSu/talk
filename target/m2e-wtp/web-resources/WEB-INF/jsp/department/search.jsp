<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://www.youlb.com/tags/role" prefix="r"%>
    
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	<!-- 功能按钮 div-->
	<div class="functionBut">
         <ul class="list-unstyled list-inline">
            <!-- 添加saveOrUpdateBtn类 跳转到自己定义的add页面  rel跳转页面url  saveUrl 保存到数据库url -->
            <r:role auth="组织架构/添加部门">
                <li><button class="btn btn-success btn-sm departmentAdd" rel="${path}/mc/department/toSaveOrUpdate.do?parentId=${parentId}" saveUrl="${path}/mc/department/saveOrUpdate.do">添加部门</button></li>
            </r:role>
            <r:role auth="组织架构/修改部门">
                <li><button class="btn btn-warning btn-sm departmentAdd" rel="${path}/mc/department/toSaveOrUpdate.do?parentId=${parentId}" saveUrl="${path}/mc/department/saveOrUpdate.do">修改部门</button></li>
            </r:role>
            <r:role auth="组织架构/删除部门">
	            <!--delete类 公共删除  -->
	            <li><button class="btn btn-danger btn-sm delete" rel="${path}/mc/department/delete.do">删除部门</button></li>
            </r:role>
         </ul>
	 </div>
          <form id="departmentSearchForm" action="" method="post">
           <input type="hidden" class="hiddenId" name="parentId" value="${parentId}"/>
          </form>
</body>
