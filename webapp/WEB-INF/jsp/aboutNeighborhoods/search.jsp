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
            <r:role auth="关于小区/添加">
                <li><button class="btn btn-primary btn-sm aboutNeighborhoodsSaveOrUpdateBtn">添加</button></li>
            </r:role>
             <r:role auth="关于小区/添加首页">
                <li><button class="btn btn-primary btn-sm aboutNeighborhoodsAddFirstPageBtn">添加首页</button></li>
            </r:role>
            <r:role auth="关于小区/修改">
                <li><button class="btn btn-warning btn-sm aboutNeighborhoodsSaveOrUpdateBtn">修改</button></li>
            </r:role>
            <r:role auth="关于小区/发布">
                <li><button class="btn btn-success btn-sm aboutNeighborhoodsCheckBtn" value="2">发布</button></li>
            </r:role>
             <r:role auth="关于小区/审核通过">
                <li><button class="btn btn-success btn-sm aboutNeighborhoodsCheckBtn" value="3">审核通过</button></li>
            </r:role>
            <r:role auth="关于小区/撤回">
                <li><button class="btn btn-info btn-sm aboutNeighborhoodsCheckBtn" value="4">撤回</button></li>
            </r:role>
            <r:role auth="关于小区/取消发布">
                <li><button class="btn btn-danger btn-sm aboutNeighborhoodsCheckBtn" value="1">取消发布</button></li>
            </r:role>
            <r:role auth="关于小区/删除">
	            <!--delete类 公共删除  -->
	            <li><button class="btn btn-danger btn-sm delete" rel="${path}/mc/aboutNeighborhoods/delete.do">删除</button></li>
            </r:role>
         </ul>
	 </div>
	 <!-- 查询form div  --> 
          <form id="aboutNeighborhoodsSearchForm" action="" method="post">
           <input type="hidden" class="hiddenId" name="neighborDomainId" value="${neighborDomainId}"/>
        </form>
</body>
