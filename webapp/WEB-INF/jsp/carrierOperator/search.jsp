<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib uri="http://www.youlb.com/tags/role" prefix="r"%>
<body>
	<!-- 功能按钮 div-->
	<div class="functionBut">
         <ul class="list-unstyled list-inline">
                 <!-- 添加saveOrUpdateBtn类 跳转到自己定义的add页面  rel跳转页面url  saveUrl 保存到数据库url -->
<%--                  <r:role auth="帐号管理/添加"> --%>
<%--                      <li><button class="btn btn-success btn-sm saveOrUpdateBtn" rel="${path}/mc/user/toSaveOrUpdate.do" saveUrl="${path}/mc/user/saveOrUpdate.do">添加</button></li> --%>
<%--                 </r:role> --%>
<%--                 <r:role auth="运营商帐号管理/修改"> --%>
<%--                      <li><button class="btn btn-warning btn-sm saveOrUpdateBtn" rel="${path}/mc/user/toSaveOrUpdate.do?isCarrier=a" saveUrl="${path}/mc/user/saveOrUpdate.do">修改</button></li> --%>
<%--                 </r:role> --%>
                <!--修改密码  -->
                <r:role auth="运营商帐号管理/重置密码">
                     <li><button class="btn btn-warning btn-sm updatePasw" rel="${path}/mc/user/toUpdatePasw.do" saveUrl="${path}/mc/user/updatePasw.do">重置密码</button></li>
                </r:role>
                <!--delete类 公共删除  
                <r:role auth="运营商帐号管理/删除">
                     <li><button class="btn btn-danger btn-sm delete" rel="${path}/mc/user/delete.do">删除</button></li>
                </r:role>-->
         </ul>
	 </div>
	 <!-- 查询form div-->     
    <div class="searchInfoDiv">
          <form id="carrierOperatorSearchForm" action="" method="post">
           <table>
              <tr>
                <td><div class="firstFont">登入名：</div></td>
                <td><div><input name="loginName" class="form-control"/></div></td>
                <td><div class="leftFont">姓名：</div></td>
                <td><div><input name="realName" class="form-control"/></div></td>
                <td><div class="leftFont"><button class="btn btn-info btn-sm reset">重置</button></div></td>
                <td><div class="leftFont"><button class="btn btn-info btn-sm search" rel="${path}/mc/user/carrierShowList.do">查询</button></div></td>
              </tr>
           </table>
        </form>
       </div>
<script type="text/javascript">
    $(function(){
    	$(".updatePasw").click(function(){
	    	var url = $(this).attr("rel");
			//获取选择的id
			var data= getSelectedIds();
			var selects = $("#tableShowList").bootstrapTable('getSelections');
			//ids= 长度小于4说明没有id
			 if(selects.length!=1){
				 hiAlert("提示","请选择一条操作数据！");
				 return false;
			 }
			$.post(url,data,function(addHtml){
				//设置标题
	            $("#myModalLabel").html("重置密码");
				$("#myModal .modal-body").html(addHtml);
				$("#myModal").modal("show");
			})
	    	})
    })

</script>    
</body>
