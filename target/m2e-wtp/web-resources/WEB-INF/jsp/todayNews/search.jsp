<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://www.youlb.com/tags/role" prefix="r"%>
    
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
   //时间控件
   $(".datepicker").datepicker();
 </script>
</head>
<body>
	<!-- 功能按钮 div-->
	<div class="functionBut">
         <ul class="list-unstyled list-inline">
            <!-- 添加saveOrUpdateBtn类 跳转到自己定义的add页面  rel跳转页面url  saveUrl 保存到数据库url -->
            <r:role auth="今日头条/添加">
                <li><button class="btn btn-primary btn-sm todayNewsAdd">添加</button></li>
            </r:role>
            <r:role auth="今日头条/修改">
                <li><button class="btn btn-warning btn-sm todayNewsAdd">修改</button></li>
            </r:role>
            <r:role auth="今日头条/发布">
                <li><button class="btn btn-success btn-sm todayNewsPush">发布</button></li>
            </r:role>
            <r:role auth="今日头条/删除">
	            <!--delete类 公共删除  -->
	            <li><button class="btn btn-danger btn-sm delete" rel="${path}/mc/todayNews/delete.do">删除</button></li>
            </r:role>
         </ul>
	 </div>
	 <!-- 查询form div  --> 
    <div class="searchInfoDiv">
          <form id="todayNewsSearchForm" action="" method="post">
<%--            <input type="hidden" class="hiddenId" name="id" value="${id}"/> --%>
          <table>
              <tr>
                <td><div class="firstFont">标题名称：</div></td>
                <td><div><input name="title" class="form-control"/></div></td>
                <td><div class="leftFont">终端类型：</div></td>
                <td><div>
                   <select name="targetDevice" class="form-control">
                      <option value="">--全部--</option>
                      <option value="1">门口机</option>
                      <option value="2">移动端</option>
                   </select>
                </div></td>
              </tr>
           </table>
           <table>
              <tr>
                <td><div class="firstFont">添加时间：</div></td>
                <td><div><input name="startTime" readonly="readonly" class="form-control datepicker"/></div></td>
                <td><div>至</div></td>
                <td><div><input name="endTime" readonly="readonly" class="form-control datepicker"/></div></td>
                <td><div class="leftFont"><button class="btn btn-info btn-sm reset">重置</button></div></td>
                <td><div class="leftFont"><button class="btn btn-info btn-sm search" rel="${path}/mc/todayNews/showList.do">查询</button></div></td>
              </tr>
           </table>
        </form>
       </div>  
<!--     <div class="searchInfoDiv"> -->
<!--       <span>地区列表：</span> -->
<!--     </div> -->
</body>
