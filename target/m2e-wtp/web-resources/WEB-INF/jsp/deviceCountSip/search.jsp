<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://www.youlb.com/tags/role" prefix="r"%>
    
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
   html,body{ height:100%; margin:0; padding:0} 
 .mask{height:100%; width:100%; position:fixed; _position:relative; top:0; z-index:1000; } 
 .opacity{ opacity:0.3; filter: alpha(opacity=30); background-color:#000; } 
</style>
</head>
<body>
<!-- 功能按钮 div-->
<!-- 	<div class="functionBut"> -->
<!--          <ul class="list-unstyled list-inline"> -->
<%--          <r:role auth="门口机在线状态查询/重启"> --%>
<!--             重启 -->
<%--             <li><button class="btn btn-success btn-sm resetDevice" rel="${path}/mc/sipCount/resetDevice.do?">重启</button></li> --%>
<%--           </r:role> --%>
<!--          </ul> -->
<!-- 	 </div> -->
	 <!-- 查询form div  --> 
    <div class="searchInfoDiv">
          <form id="deviceCountSipSearchForm" action="" method="post">
           <input type="hidden" class="hiddenId" name="id" value="${id}"/>
           <table>
              <tr>
                <td><div class="firstFont">sip账号：</div></td>
                <td><div><input name="sipUser" class="form-control"/></div></td>
                <td><div class="leftFont">门口机账号：</div></td>
                <td><div><input name="username" class="form-control"/></div></td>
                <td><div class="leftFont">地址：</div></td>
                <td>
		          <div>
		              <input type="hidden" class="form-control domainId" name="domainId" style="width: 260px">
				      <input type="text" class="form-control address" style="width: 260px" readonly="readonly">
				  </div>
				  <div style="position: fixed;z-index:1000;width: 260px;height: 400px;overflow: auto;display: none">
				      <p id="deviceCountSipShowTree"></p>
				  </div>
                </td>
                <td><div class="leftFont"><button class="btn btn-info btn-sm reset">重置</button>
                <button class="btn btn-info btn-sm search" rel="${path}/mc/sipCount/deviceCountSipShowList.do">查询</button></div></td>
              </tr>
           </table>
        </form>
       </div>
       
       
       
       
       
       <script type="text/javascript">
       $(function(){
    	   //重启
    	   $(".resetDevice").on("click",function(){
  			 var selects = $("#tableShowList").bootstrapTable('getSelections');
  			//ids= 长度小于4说明没有id
			 if(selects.length!=1){
				 hiAlert("提示","请选择一条操作数据！");
				 return false;
			 }
			 var ids = $.map(selects, function (row) {
			        return row.username;
			    }).join("&ids=");
		     $.post($path+"/mc/sipCount/toRestDevice.do","ids="+ids,function(addHtml){
					//设置标题
		            $("#myModalLabel").html("重启");
					$("#myModal .modal-body").html(addHtml);
					$("#myModal").modal("show");
				});
    	   })
    	   
    	   $("#deviceCountSipSearchForm .address").on("mousedown",function(){
               var addressDiv = $(this).parent().next("div");
               addressDiv.css("display","inline");
               addressDiv.on("mouseleave",function(){
            	   addressDiv.css("display","none");
               })
    	   })
    	   $(document).on("click","#deviceCountSipShowTree .treecheckbox",function(){
    		   $("#deviceCountSipShowTree .treecheckbox").prop('checked',false);
    			$(this).prop('checked',true);
    			var domainId = $(this).val();
    			 $("#deviceCountSipSearchForm .domainId").val(domainId);
    			$.post($path+"/mc/sipCount/getAddressByDomainId.do","domainId="+domainId,function($address){
    				 $("#deviceCountSipSearchForm .address").val($address);
    			})
    				
    			
    	   })
    	   
    		   $('#deviceCountSipShowTree').bstree({
	   				url: $path+'/mc/carrier',
	   				height:'auto',
	   				open: false,
	   				checkbox:true,
	   				checkboxLink:false,//是否联动多选框
	   				checkboxPartShow:true,//显示部分多选框
	   				layer: [1,2,3,4],
	   				showurl:false
   		     });
		
       })
       </script>
</body>
