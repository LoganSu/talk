<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.youlb.com/tags/role" prefix="r"%>
 <script type="text/javascript">
//时间控件
 $(".datepicker").datepicker();
//  $('#importDeviceInfoForm input').fileinput("upload");
 </script>
<body>
	<!-- 功能按钮 div-->
	<div class="functionBut">
         <ul class="list-unstyled list-inline">
                <!-- 添加saveOrUpdateBtn类 跳转到自己定义的add页面  rel跳转页面url  saveUrl 保存到数据库url -->
<%--                 <r:role auth="门禁设备管理/添加"> --%>
<%--                     <li><button class="btn btn-success btn-sm saveOrUpdateBtn" rel="${path}/mc/device/toSaveOrUpdate.do?parentId=${parentId}" saveUrl="${path}/mc/device/saveOrUpdate.do">添加</button></li> --%>
<%--                 </r:role> --%>
                <r:role auth="门禁设备管理/修改">
                    <li><button class="btn btn-warning btn-sm saveOrUpdateBtn" rel="${path}/mc/device/toSaveOrUpdate.do?parentId=${parentId}" saveUrl="${path}/mc/device/saveOrUpdate.do">修改</button></li>
	            </r:role>
	            <!--delete类 公共删除  -->
                <r:role auth="门禁设备管理/删除">
	                 <li><button class="btn btn-danger btn-sm delete" rel="${path}/mc/device/delete.do">删除</button></li>
	            </r:role>
	            <r:role auth="门禁设备管理/模板下载">
                    <li><a href="${path}/mc/device/singleDownModel.do"><button class="btn btn-primary btn-sm">模板下载</button></a></li>
	            </r:role>
	            <r:role auth="门禁设备管理/导出">
                    <li><button class="btn btn-info btn-sm exportDeviceInfo">导出</button></li>
	            </r:role>
	             <r:role auth="门禁设备管理/导入">
                    <li style="padding-right: 0px"><button class="btn btn-success btn-sm importDeviceInfo">导入</button></li>
                    <li style="padding-left: 0px;">
                     <form id="importDeviceInfoForm" action="${path}/mc/device/importDeviceInfo.do" target="deviceInfoSubmitFrame" enctype="multipart/form-data" method="post">
                       <input type="file" name="deviceInfo" class="file"/>
                     </form>
                   </li>
	            </r:role>
         </ul>
	 </div>	
     <div class="searchInfoDiv">
          <form id="deviceSearchForm" action="" method="post">
           <table>
              <tr>
                <td><div class="firstFont">设备SN码：</div></td>
                <td><div><input name="id" class="form-control"/></div></td>
                <td><div class="leftFont">设备编号：</div></td>
                <td><div><input name="deviceNum" class="form-control"/></div></td>
                <td><div class="leftFont">设备型号：</div></td>
                <td><div><input name="deviceModel" class="form-control"/></div></td>
              
              </tr>
               <tr>
<!--                <td><div class="firstFont">设备厂家：</div></td> -->
<!--                 <td><div> -->
<!--                        <select name="deviceFactory" class="form-control"> -->
<!--                          <option value="">--全部--</option> -->
<!--                          <option value="1">中卡</option> -->
<!--                          <option value="2">友邻邦</option> -->
<!--                       </select> -->
<!--                  </div></td> -->
                <td><div class="firstFont">设备状态：</div></td>
                <td><div>
                     <select name="deviceStatus" class="form-control">
                         <option value="">--全部--</option>
                         <option value="1">激活</option>
                         <option value="2">未激活</option>
                      </select>
                </div></td>
                <td><div class="leftFont">出厂日期：</div></td>
                <td><div><input name="deviceBorn" class="form-control datepicker"/></div></td>
                <td><div class="leftFont"><button class="btn btn-info btn-sm reset">重置</button></div></td>
                <td><div class="leftFont"><button class="btn btn-info btn-sm search" rel="${path}/mc/device/showList.do">查询</button></div></td>
              </tr>
           </table>
        </form>
       </div>
       <!-- 隐藏iframe设置表单的目标为这个嵌入框架  使页面效果不会跳转 -->
 <iframe style="width:0; height:0;display: none;" id="deviceInfoSubmitFrame" name="deviceInfoSubmitFrame"></iframe>
</body>       