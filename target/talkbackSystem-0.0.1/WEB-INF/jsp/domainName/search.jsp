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
            <r:role auth="域名管理/添加">
                <li><button class="btn btn-success btn-sm saveOrUpdateBtn" rel="${path}/mc/domainName/toSaveOrUpdate.do?parentid=${id}" saveUrl="${path}/mc/domainName/saveOrUpdate.do">添加</button></li>
            </r:role>
            <r:role auth="域名管理/修改">
                <li><button class="btn btn-warning btn-sm saveOrUpdateBtn" rel="${path}/mc/domainName/toSaveOrUpdate.do?parentid=${id}" saveUrl="${path}/mc/domainName/saveOrUpdate.do">修改</button></li>
            </r:role>
            <r:role auth="域名管理/删除">
	            <!--delete类 公共删除  -->
	            <li><button class="btn btn-danger btn-sm delete" rel="${path}/mc/domainName/delete.do">删除</button></li>
            </r:role>
         </ul>
	 </div>
	 <!-- 查询form div  --> 
    <div class="searchInfoDiv">
          <form id="domainNameSearchForm" action="" method="post">
            <input type="hidden" class="hiddenId" name="id" value="${id}"/>
        </form>
       </div>  
</body>
