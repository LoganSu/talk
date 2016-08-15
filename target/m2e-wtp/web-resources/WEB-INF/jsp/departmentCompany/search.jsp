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
            <r:role auth="组织架构/添加公司">
                <li><button class="btn btn-success btn-sm departmentAdd" rel="${path}/mc/department/toCompanySaveOrUpdate.do" saveUrl="${path}/mc/department/companySaveOrUpdate.do">添加公司</button></li>
            </r:role>
            <r:role auth="组织架构/修改公司">
                <li><button class="btn btn-warning btn-sm departmentAdd" rel="${path}/mc/department/toCompanySaveOrUpdate.do" saveUrl="${path}/mc/department/companySaveOrUpdate.do">修改公司</button></li>
            </r:role>
            <r:role auth="组织架构/删除公司">
	            <!--delete类 公共删除  -->
	            <li><button class="btn btn-danger btn-sm delete" rel="${path}/mc/department/delete.do">删除公司</button></li>
            </r:role>
         </ul>
	 </div>
         <!-- 查询form div   -->
<!--     <div class="searchInfoDiv"> -->
          <form id="managementCompanySearchForm" action="" method="post">
             <input type="hidden" class="hiddenId" name="parentId" value="${parentId}"/>
           </form>
<!--        </div>    -->
</body>
