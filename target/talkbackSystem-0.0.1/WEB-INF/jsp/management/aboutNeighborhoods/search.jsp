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
                <li><button class="btn btn-primary btn-sm saveOrUpdateBtn" rel="${path}/mc/aboutNeighborhoods/toSaveOrUpdate.do" saveUrl="${path}/mc/aboutNeighborhoods/saveOrUpdate.do">添加</button></li>
            </r:role>
            <r:role auth="关于小区/修改">
                <li><button class="btn btn-warning btn-sm saveOrUpdateBtn" rel="${path}/mc/aboutNeighborhoods/toSaveOrUpdate.do" saveUrl="${path}/mc/aboutNeighborhoods/saveOrUpdate.do">修改</button></li>
            </r:role>
             <r:role auth="关于小区/审核通过">
                <li><button class="btn btn-success btn-sm saveOrUpdateBtn" rel="${path}/mc/aboutNeighborhoods/toSaveOrUpdate.do" saveUrl="${path}/mc/aboutNeighborhoods/saveOrUpdate.do">审核通过</button></li>
            </r:role>
             <r:role auth="关于小区/撤回">
                <li><button class="btn btn-info btn-sm saveOrUpdateBtn" rel="${path}/mc/aboutNeighborhoods/toSaveOrUpdate.do" saveUrl="${path}/mc/aboutNeighborhoods/saveOrUpdate.do">撤回</button></li>
            </r:role>
             <r:role auth="关于小区/取消发布">
                <li><button class="btn btn-default btn-sm saveOrUpdateBtn" rel="${path}/mc/aboutNeighborhoods/toSaveOrUpdate.do" saveUrl="${path}/mc/aboutNeighborhoods/saveOrUpdate.do">取消发布</button></li>
            </r:role>
            <r:role auth="关于小区/删除">
	            <!--delete类 公共删除  -->
	            <li><button class="btn btn-danger btn-sm delete" rel="${path}/mc/aboutNeighborhoods/delete.do">删除</button></li>
            </r:role>
         </ul>
	 </div>
	 <!-- 查询form div  --> 
<!--     <div class="searchInfoDiv"> -->
          <form id="aboutNeighborhoodsSearchForm" action="" method="post">
           <input type="hidden" class="hiddenId" name="neighborDomainId" value="${neighborDomainId}"/>
<!--            <table> -->
<!--               <tr> -->
<!--                 <td><div class="firstFont">省份：</div></td> -->
<!--                 <td><div><input name="province" class="form-control"/></div></td> -->
<!--                 <td><div class="leftFont">城市：</div></td> -->
<!--                 <td><div><input name="city" class="form-control"/></div></td> -->
<!--                 <td><div class="leftFont">编号：</div></td> -->
<!--                 <td><div><input name="aboutNeighborhoodsNum" class="form-control"/></div></td> -->
<!--                 <td><div class="leftFont"><button class="btn btn-info btn-sm reset">重置</button></div></td> -->
<%--                 <td><div class="leftFont"><button class="btn btn-info btn-sm search" rel="${path}/mc/aboutNeighborhoods/showList.do">查询</button></div></td> --%>
<!--               </tr> -->
<!--            </table> -->
        </form>
<!--        </div>   -->
</body>
