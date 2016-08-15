<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.youlb.com/tags/role" prefix="r"%>

<body>
	<!-- 功能按钮 div-->
	<div class="functionBut">
         <ul class="list-unstyled list-inline">
                <!-- 添加saveOrUpdateBtn类 跳转到自己定义的add页面  rel跳转页面url  saveUrl 保存到数据库url -->
                <r:role auth="操作权限/添加">
                    <li><button class="btn btn-success btn-sm saveOrUpdateBtn" rel="${path}/mc/privilege/toSaveOrUpdate.do?parentId=${parentId}" saveUrl="${path}/mc/privilege/saveOrUpdate.do">添加</button></li>
                </r:role>
                <r:role auth="操作权限/修改">
                    <li><button class="btn btn-warning btn-sm saveOrUpdateBtn" rel="${path}/mc/privilege/toSaveOrUpdate.do?parentId=${parentId}" saveUrl="${path}/mc/privilege/saveOrUpdate.do">修改</button></li>
	            </r:role>
	            <!--delete类 公共删除  -->
                <r:role auth="操作权限/删除">
	                 <li><button class="btn btn-danger btn-sm delete" rel="${path}/mc/privilege/delete.do">删除</button></li>
	            </r:role>
         </ul>
	 </div>	
          <form id="authoritySearchForm" action="" method="post">
             <input type="hidden" class="hiddenId" name="parentId" value="${parentId}"/>
          </form>
</body>
