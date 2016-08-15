<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.youlb.com/tags/role" prefix="r"%>
<body>
<!-- 功能按钮 div-->
	<div class="functionBut">
         <ul class="list-unstyled list-inline">
                <!-- 添加saveOrUpdateBtn类 跳转到自己定义的add页面  rel跳转页面url  saveUrl 保存到数据库url -->
<%--                 <r:role auth="房屋信息管理/添加"> --%>
<%--                     <li><button class="btn btn-success btn-sm saveOrUpdateBtn" rel="${path}/mc/permission/toSaveOrUpdate.do?parentId=${parentId}" saveUrl="${path}/mc/permission/saveOrUpdate.do">添加</button></li> --%>
<%--                 </r:role> --%>
                <r:role auth="房屋信息管理/修改">
                    <li><button class="btn btn-warning btn-sm saveOrUpdateBtn" rel="${path}/mc/permission/toSaveOrUpdate.do?parentId=${parentId}" saveUrl="${path}/mc/permission/saveOrUpdate.do">修改</button></li>
	            </r:role>
	            <!--delete类 公共删除  -->
                <r:role auth="房屋信息管理/删除">
	                 <li><button class="btn btn-danger btn-sm delete" rel="${path}/mc/permission/delete.do">删除</button></li>
	            </r:role>
         </ul>
	 </div>	
     <div class="searchInfoDiv">
          <form id="permissionSearchForm" action="" method="post">
           <table>
              <tr>
                <td><div class="firstFont">姓名：</div></td>
                <td><div><input name="fname" class="form-control"/></div></td>
                <td><div  class="leftFont">身份证：</div></td>
                <td><div><input name="idnum" class="form-control"/></div></td>
                <td><div class="leftFont">电话：</div></td>
                <td><div><input name="phone" class="form-control"/></div></td>
<!--                 <td><div class="leftFont">人员类型：</div></td> -->
<!--                 <td><div> -->
<!--                     <select class="form-control" name="ftype"> -->
<!--                        <option value="">--全部--</option> -->
<!--                        <option value="1">房东</option> -->
<!--                        <option value="2">租客</option> -->
<!--                     </select> -->
<!--                 </div></td> -->
                <td><div class="leftFont"><button class="btn btn-info btn-sm reset">重置</button></div></td>
                <td><div class="leftFont"><button class="btn btn-info btn-sm search" rel="${path}/mc/permission/showList.do">查询</button></div></td>
              </tr>
           </table>
        </form>
       </div>
</body>       