<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://www.youlb.com/tags/role" prefix="r"%>
<body>
	<!-- 功能按钮 div-->
	<div class="functionBut">
         <ul class="list-unstyled list-inline">
         <r:role auth="社区/添加">
            <!-- 添加saveOrUpdateBtn类 跳转到自己定义的add页面  rel跳转页面url  saveUrl 保存到数据库url -->
            <li><button class="btn btn-success btn-sm saveOrUpdateBtn" rel="${path}/mc/neighborhoods/toSaveOrUpdate.do?parentId=${parentId}" saveUrl="${path}/mc/neighborhoods/saveOrUpdate.do">添加</button></li>
          </r:role>
          <r:role auth="社区/修改">
            <li><button class="btn btn-warning btn-sm saveOrUpdateBtn" rel="${path}/mc/neighborhoods/toSaveOrUpdate.do?parentId=${parentId}" saveUrl="${path}/mc/neighborhoods/saveOrUpdate.do">修改</button></li>
          </r:role>
          <r:role auth="社区/删除">
            <!--delete类 公共删除  -->
            <li><button class="btn btn-danger btn-sm delete" rel="${path}/mc/neighborhoods/delete.do">删除</button></li>
          </r:role>
         </ul>
	 </div>
	 <!-- 查询form div  --> 
    <div class="searchInfoDiv">
          <form id="neighborhoodsSearchForm" action="" method="post">
          <input type="hidden" value="${parentId}" name="parentId">
           <table>
              <tr>
                <td><div class="firstFont">社区名称：</div></td>
                <td><div><input name="neibName" class="form-control"/></div></td>
                <td><div class="leftFont">社区编号：</div></td>
                <td><div><input name="neibNum" class="form-control"/></div></td>
                <td><div class="leftFont"><button class="btn btn-info btn-sm reset">重置</button></div></td>
                <td><div class="leftFont"><button class="btn btn-info btn-sm search" rel="${path}/mc/neighborhoods/showList.do">查询</button></div></td>
              </tr>
           </table>
        </form>
       </div>
       <div class="searchInfoDiv tableTitle">
        <span>地区>>社区列表：</span>
      </div>  
</body>
