<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://www.youlb.com/tags/role" prefix="r"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
    
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
	      <c:choose>
	         <c:when test="${parentId==1}">
	             <r:role auth="物业报修/添加">
	                <li><button class="btn btn-success btn-sm saveOrUpdateBtn" rel="${path}/mc/repairs/toSaveOrUpdate.do" saveUrl="${path}/mc/repairs/saveOrUpdate.do?orderNature=${parentId}">添加</button></li>
	            </r:role>
	            <r:role auth="物业报修/修改">
	                <li><button class="btn btn-warning btn-sm saveOrUpdateBtn" rel="${path}/mc/repairs/toSaveOrUpdate.do" saveUrl="${path}/mc/repairs/saveOrUpdate.do?orderNature=${parentId}">修改</button></li>
	            </r:role>
	         </c:when>
	         <c:otherwise>
		           <r:role auth="客服服务/添加">
		                <li><button class="btn btn-success btn-sm saveOrUpdateBtn" rel="${path}/mc/repairs/toSaveOrUpdate.do" saveUrl="${path}/mc/repairs/saveOrUpdate.do?orderNature=${parentId}">添加</button></li>
		            </r:role>
		            <r:role auth="客服服务/修改">
		                <li><button class="btn btn-warning btn-sm saveOrUpdateBtn" rel="${path}/mc/repairs/toSaveOrUpdate.do" saveUrl="${path}/mc/repairs/saveOrUpdate.do?orderNature=${parentId}">修改</button></li>
		            </r:role>
	         
	         </c:otherwise>
	      </c:choose>
       </ul>     
	 </div>
	 <!-- 查询form div  --> 
    <div class="searchInfoDiv">
          <form id="repairsSearchForm" action="" method="post">
           <input type="hidden" class="hiddenId" name="id" value="${id}"/>
           <input type="hidden" class="hiddenId" name="orderNature" value="${orderNature}"/>
           <table>
              <tr>
                <td><div class="firstFont">工单号：</div></td>
                <td><div><input name="orderNum" class="form-control"/></div></td>
                <td><div class="leftFont">联系人：</div></td>
                <td><div><input name="linkman" class="form-control"/></div></td>
                <td><div class="leftFont">联系电话：</div></td>
                <td><div><input name="phone" class="form-control"/></div></td>
                <td><div class="leftFont">来源：</div></td>
                <td><div><select name="comeFrom" class="form-control">
                    <option value="">全部</option>
                    <option value="1">电话</option>
                    <option value="2">来访</option>
                    <option value="3">终端</option> 
                </select></div></td>
              </tr>
              </table>
              <table>
              <tr>
                <td><div class="firstFont">时间范围：</div></td>
                <td><div><input name="startTime" class="form-control datepicker"/></div></td>
                <td><div class="firstFont">至</div></td>
                <td><div><input name="endTime" class="form-control datepicker"/></div></td>
                <td><div class="leftFont"><button class="btn btn-info btn-sm reset">重置</button></div></td>
                <td><div class="leftFont"><button class="btn btn-info btn-sm search" rel="${path}/mc/repairs/showList.do">查询</button></div></td>
              </tr>
           </table>
        </form>
       </div>  
      <div class="searchInfoDiv tableTitle">
        <table>
             <tr>
                <td><div class="firstFont">工单总数：</div></td>
                <td><div style="color: red;font-weight: bold;">${all}</div></td>
                <td><div class="leftFont">已处理工单：</div></td>
                <td><div class="repairsFinish">${finish}</div></td>
                <td><div class="leftFont">处理中：</div></td>
                <td><div class="repairsFinishing">${finishing}</div></td>
                <td><div class="leftFont">未处理工单：</div></td>
                <td><div class="repairsUnfinish">${unfinish}</div></td>
              </tr>
          </table>
      </div>
</body>
