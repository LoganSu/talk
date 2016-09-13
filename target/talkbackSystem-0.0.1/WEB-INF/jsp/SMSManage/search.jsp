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
            <r:role auth="短信网关配置/添加">
                <li><button class="btn btn-success btn-sm SMSManageAdd">添加</button></li>
            </r:role>
            <r:role auth="短信网关配置/修改">
                <li><button class="btn btn-warning btn-sm SMSManageAdd">修改</button></li>
            </r:role>
            <r:role auth="短信网关配置/删除">
	            <!--delete类 公共删除  -->
	            <li><button class="btn btn-danger btn-sm delete" rel="${path}/mc/SMSManage/delete.do">删除</button></li>
            </r:role>
         </ul>
	 </div>
</body>
