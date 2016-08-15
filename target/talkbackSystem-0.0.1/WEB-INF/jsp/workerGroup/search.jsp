<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://www.youlb.com/tags/role" prefix="r"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<script type="text/javascript">
</script>
</head>
<body>
	<!-- 功能按钮 div-->
	<div class="functionBut">
         <ul class="list-unstyled list-inline">
            <!-- 添加saveOrUpdateBtn类 跳转到自己定义的add页面  rel跳转页面url  saveUrl 保存到数据库url -->
            <r:role auth="分组管理/添加">
                <li><button class="btn btn-success btn-sm saveOrUpdateBtn" rel="${path}/mc/workerGroup/toSaveOrUpdate.do" saveUrl="${path}/mc/workerGroup/saveOrUpdate.do">添加</button></li>
            </r:role>
            <r:role auth="分组管理/修改">
                <li><button class="btn btn-warning btn-sm saveOrUpdateBtn" rel="${path}/mc/workerGroup/toSaveOrUpdate.do" saveUrl="${path}/mc/workerGroup/saveOrUpdate.do">修改</button></li>
            </r:role>
            <r:role auth="分组管理/删除">
	            <!--delete类 公共删除  -->
	            <li><button class="btn btn-danger btn-sm delete" rel="${path}/mc/workerGroup/delete.do">删除</button></li>
            </r:role>
         </ul>
	 </div>
	 <!-- 查询form div  --> 
    <div class="searchInfoDiv">
          <form id="workerGroupSearchForm" action="" method="post">
           <input type="hidden" class="hiddenId" name="id" value="${id}"/>
           <table>
              <tr>
                <td><div class="firstFont">组名称：</div></td>
                <td><div><input name="groupName" class="form-control"/></div></td>
                <td><div class="leftFont">组类别：</div></td>
                <td><div>
                     <select class="form-control" name="power">
                        <option value="">全部</option>
                        <option value="1">派单组</option>
                        <option value="2">普通组</option>
                     </select>
                </div></td>
                <td><div class="leftFont"><button class="btn btn-info btn-sm reset">重置</button></div></td>
                <td><div class="leftFont"><button class="btn btn-info btn-sm search" rel="${path}/mc/workerGroup/showList.do">查询</button></div></td>
              </tr>
           </table>
        </form>
       </div>  
</body>
