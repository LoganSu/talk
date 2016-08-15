<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.youlb.com/tags/role" prefix="r"%>
 <script type="text/javascript">
//时间控件
 $(".datepicker").datepicker();
 </script>
<body>
	<!-- 功能按钮 div-->
	<div class="functionBut">
         <ul class="list-unstyled list-inline">
                <!-- 添加saveOrUpdateBtn类 跳转到自己定义的add页面  rel跳转页面url  saveUrl 保存到数据库url -->
                <r:role auth="设备帐号管理/添加">
                    <li><button class="btn btn-success btn-sm saveOrUpdateBtn" rel="${path}/mc/deviceCount/toSaveOrUpdate.do" saveUrl="${path}/mc/deviceCount/saveOrUpdate.do">添加</button></li>
                </r:role>
                <r:role auth="设备帐号管理/修改">
                    <li><button class="btn btn-warning btn-sm saveOrUpdateBtn" rel="${path}/mc/deviceCount/toSaveOrUpdate.do" saveUrl="${path}/mc/deviceCount/saveOrUpdate.do">修改</button></li>
	            </r:role>
	            <r:role auth="设备帐号管理/派单">
                    <li><button class="btn btn-info btn-sm sendOrders">派单</button></li>
	            </r:role>
	            <!--delete类 公共删除  -->
                <r:role auth="设备帐号管理/删除">
	                 <li><button class="btn btn-danger btn-sm delete" rel="${path}/mc/deviceCount/delete.do">删除</button></li>
	            </r:role>
         </ul>
	 </div>	
     <div class="searchInfoDiv">
          <form id="deviceCountSearchForm" action="" method="post">
           <table>
              <tr>
                <td><div class="firstFont">帐号：</div></td>
                <td><div><input name="deviceCount" class="form-control"/></div></td>
                <td><div class="leftFont">帐号类型：</div></td>
                <td><div> 
                      <select name="countType" class="form-control">
                         <option value="">--全部--</option>
                         <option value="1">门口机</option>
                         <option value="2">自助终端</option>
                         <option value="3">管理机</option>
                      </select>
                </div></td>
                <td><div class="leftFont">帐号状态：</div></td>
                <td><div>
                       <select name="countStatus" class="form-control">
                         <option value="">--全部--</option>
                         <option value="1">激活</option>
                         <option value="2">未激活</option>
                      </select>
                 </div></td>
                <td><div class="leftFont"><button class="btn btn-info btn-sm reset">重置</button></div></td>
                <td><div class="leftFont"><button class="btn btn-info btn-sm search" rel="${path}/mc/deviceCount/showList.do">查询</button></div></td>
              </tr>
           </table>
        </form>
       </div>
</body>       