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
            <r:role auth="广告发布/添加">
                <li><button class="btn btn-primary btn-sm adPulishAdd">添加</button></li>
            </r:role>
            <r:role auth="广告发布/修改">
                <li><button class="btn btn-warning btn-sm adPulishAdd">修改</button></li>
            </r:role>
            <r:role auth="广告发布/发布">
                <li><button class="btn btn-success btn-sm adPulishPush">发布</button></li>
            </r:role>
            <r:role auth="广告发布/删除">
	            <!--delete类 公共删除  -->
	            <li><button class="btn btn-danger btn-sm adPulishPushDelete">删除</button></li>
            </r:role>
         </ul>
	 </div>
	 <!-- 查询form div  --> 
    <div class="searchInfoDiv">
          <form id="adPublishSearchForm" action="" method="post">
<!--           类型区分   -->
<%--           <input type="hidden" value="${parentId}" id="adPublishAppType"/> --%>
           <table>
              <tr>
<!--                 <td><div class="firstFont">标题名称：</div></td> -->
<!--                 <td><div><input name="title" class="form-control"/></div></td> -->
<!--                 <td><div class="leftFont">信息类型：</div></td> -->
<!--                 <td><div> -->
<!--                    <select name="adType" class="form-control"> -->
<!--                       <option value="">--全部--</option> -->
<!--                       <option value="1">图片</option> -->
<!--                       <option value="2">视频</option> -->
<!--                    </select> -->
<!--                 </div></td> -->
                <td><div class="firstFont">终端类型：</div></td>
                <td><div>
                   <select name="targetDevice" class="form-control">
                      <option value="">--全部--</option>
                      <option value="1">门口机</option>
                      <option value="2">移动端</option>
                   </select>
                </div></td>
<!--                 <td><div class="leftFont">显示位置：</div></td> -->
<!--                 <td><div><input name="position" class="form-control"/></div></td> -->
              </tr>
           </table>
           <table>
               <tr>
                <td><div class="firstFont">添加时间：</div></td>
                <td><div><input name="startTime" readonly="readonly" class="form-control datepicker"/></div></td>
                <td><div>至</div></td>
                <td><div><input name="endTime" readonly="readonly" class="form-control datepicker"/></div></td>
                <td><div class="leftFont"><button class="btn btn-info btn-sm reset">重置</button></div></td>
                <td><div class="leftFont"><button class="btn btn-info btn-sm search" rel="${path}/mc/adPublish/showList.do">查询</button></div></td>
              </tr>
           </table>
        </form>
       </div>  
<!--     <div class="searchadDiv"> -->
<!--       <span>地区列表：</span> -->
<!--     </div> -->
</body>
