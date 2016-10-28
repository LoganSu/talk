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
            <r:role auth="区域/添加">
                <li><button class="btn btn-success btn-sm saveOrUpdateBtn" rel="${path}/mc/billManage/toSaveOrUpdate.do" saveUrl="${path}/mc/billManage/saveOrUpdate.do">添加</button></li>
            </r:role>
            <r:role auth="区域/修改">
                <li><button class="btn btn-warning btn-sm saveOrUpdateBtn" rel="${path}/mc/billManage/toSaveOrUpdate.do" saveUrl="${path}/mc/billManage/saveOrUpdate.do">修改</button></li>
            </r:role>
            <r:role auth="区域/删除">
	            <!--delete类 公共删除  -->
	            <li><button class="btn btn-danger btn-sm delete" rel="${path}/mc/billManage/delete.do">删除</button></li>
            </r:role>
         </ul>
	 </div>
	 <!-- 查询form div  --> 
    <div class="searchInfoDiv">
          <form id="billManageSearchForm" action="" method="post">
           <input type="hidden" class="hiddenId" name="id" value="${id}"/>
           <table>
              <tr>
                <td><div class="firstFont">账单号：</div></td>
                <td><div><input name="billNum" class="form-control"/></div></td>
                <td><div class="leftFont">姓名：</div></td>
                <td><div><input name="name" class="form-control"/></div></td>
                <td><div class="leftFont">联系电话：</div></td>
                <td><div><input name="phone" class="form-control"/></div></td>
                <td><div class="leftFont">类型：</div></td>
                <td><div>
                   <select class="form-control" name="type">
                     <option value="1">管理费</option>
                     <option value="2">水费</option>
                     <option value="3">电费</option>
                     <option value="4">燃气费</option>
                     <option value="5">其他</option>
                   </select>
                </div></td>
                <td><div class="leftFont">状态：</div></td>
                <td><div>
                   <select class="form-control" name="status">
                     <option value="1">已缴费</option>
                     <option value="2">未缴费</option>
                   </select>
                </div></td>
              </tr>
           </table>
           <table>
              <tr>
                <td><div class="firstFont">按日期：</div></td>
                <td><div><input name="startTimeStr" class="form-control"/></div></td>
                <td><div class="firstFont">至</div></td>
                <td><div><input name="endTimeStr" class="form-control"/></div></td>
                <td><div class="leftFont"><button class="btn btn-info btn-sm reset">重置</button></div></td>
                <td><div class="leftFont"><button class="btn btn-info btn-sm search" rel="${path}/mc/billManage/showList.do">查询</button></div></td>
              </tr>
           </table>
        </form>
       </div>  
</body>
