<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://www.youlb.com/tags/role" prefix="r"%>
    
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	<!-- 功能按钮 div-->
	<div class="functionBut">
         <ul class="list-unstyled list-inline">
            <!-- 添加saveOrUpdateBtn类 跳转到自己定义的add页面  rel跳转页面url  saveUrl 保存到数据库url -->
            <r:role auth="住户信息/添加">
                <li><button class="btn btn-success btn-sm saveOrUpdateBtn" rel="${path}/mc/dweller/toSaveOrUpdate.do" saveUrl="${path}/mc/dweller/saveOrUpdate.do">添加</button></li>
            </r:role>
            <r:role auth="住户信息/修改">
                <li><button class="btn btn-warning btn-sm saveOrUpdateBtn" rel="${path}/mc/dweller/toSaveOrUpdate.do" saveUrl="${path}/mc/dweller/saveOrUpdate.do">修改</button></li>
            </r:role>
            <r:role auth="住户信息/删除">
	            <!--delete类 公共删除  -->
	            <li><button class="btn btn-danger btn-sm delete" rel="${path}/mc/dweller/delete.do">删除</button></li>
            </r:role>
         </ul>
	 </div>
	 <!-- 查询form div  --> 
    <div class="searchInfoDiv">
          <form id="dwellerSearchForm" action="" method="post">
           <input type="hidden" class="hiddenId" name="id" value="${id}"/>
           <table>
              <tr>
<!--                 <td><div class="firstFont">出租人编号：</div></td> -->
<!--                 <td><div><input name="hostNum" class="form-control"/></div></td> -->
                <td><div class="firstFont">姓名：</div></td>
                <td><div><input name="fname" class="form-control"/></div></td>
                <td><div class="leftFont">联系电话：</div></td>
                <td><div><input name="phone" class="form-control"/></div></td>
                <td><div class="leftFont">证件号码：</div></td>
                <td><div><input name="idNum" class="form-control"/></div></td>
                <td><div class="leftFont"><button class="btn btn-info btn-sm reset">重置</button></div></td>
                <td><div class="leftFont"><button class="btn btn-info btn-sm search" rel="${path}/mc/dweller/showList.do">查询</button></div></td>
              </tr>
           </table>
        </form>
       </div>  
</body>
