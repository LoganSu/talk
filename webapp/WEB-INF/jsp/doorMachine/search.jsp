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
            <r:role auth="门口机型号 /添加">
                <li><button class="btn btn-success btn-sm saveOrUpdateBtn" rel="${path}/mc/doorMachine/toSaveOrUpdate.do" saveUrl="${path}/mc/doorMachine/saveOrUpdate.do">添加</button></li>
            </r:role>
            <r:role auth="门口机型号/修改">
                <li><button class="btn btn-warning btn-sm saveOrUpdateBtn" rel="${path}/mc/doorMachine/toSaveOrUpdate.do" saveUrl="${path}/mc/doorMachine/saveOrUpdate.do">修改</button></li>
            </r:role>
            <r:role auth="门口机型号 /删除">
	            <!--delete类 公共删除  -->
	            <li><button class="btn btn-danger btn-sm delete" rel="${path}/mc/doorMachine/delete.do">删除</button></li>
            </r:role>
         </ul>
	 </div>
	 <!-- 查询form div  --> 
    <div class="searchInfoDiv">
          <form id="doorMachineSearchForm" action="" method="post">
           <input type="hidden" class="hiddenId" name="id" value="${id}"/>
           <table>
              <tr>
                <td><div class="firstFont">型号：</div></td>
                <td><div><input name="machineType" class="form-control"/></div></td>
                <td><div class="leftFont">硬件型号：</div></td>
                <td><div><input name="hardwareModel" class="form-control"/></div></td>
                <td><div class="leftFont"><button class="btn btn-info btn-sm reset">重置</button></div></td>
                <td><div class="leftFont"><button class="btn btn-info btn-sm search" rel="${path}/mc/doorMachine/showList.do">查询</button></div></td>
              </tr>
           </table>
        </form>
       </div>  
</body>
