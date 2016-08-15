<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<body>
<div>
	   <div id="deviceCountSendOrders">
		 <form id="deviceCountSendOrdersForm" action="">
		     <input type="hidden" name="id" value="${deviceCount.id}"/>
		     <input type="hidden" class="deviceCount" value="${deviceCount.deviceCount}"/>
		     <input type="hidden" class="printPerson" value="${deviceCount.printPerson}"/>
		     <input type="hidden" class="printTime" value="${deviceCount.printTime}"/>
		     <input type="hidden" class="countType" value="${deviceCount.countType}"/>
		     <input type="hidden" name="orderNum" class="orderNum" value="${deviceCount.orderNum}"/>
		   <div>
		     <table>
              <tr>
                     <td><div class="firstFont">安装地址：</div></td> 
                     <td colspan="3"><div><input class="form-control address" style="width: 300px" readonly="readonly" value="${deviceCount.address}"/></div></td>
              </tr>
              <tr>
	                <td><div class="firstFont">截止日期：</div></td>
	                <td><div><input name="endTime" class="form-control datepicker" readonly="readonly"/></div></td>
              </tr>
              </table>
           </div>
         </form>
         <!-- 二维码图片位置 -->
         <div id="showQRcode" style="height: 300px">
         </div>
         <div class="modal-footer">
	         <!--操作按钮 -->
	        <button type="button" class="btn btn-primary sure">点击生成二维码</button>
	        <button id="deviceCountPrint" type="button" class="btn btn-success">打印工单</button>
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	     </div>
	   </div>
 </div>
</body>
<script type="text/javascript">
//时间控件
$(".datepicker").datepicker();
$("#deviceCountSendOrders .sure").on('click',function(){
	$("#showQRcode").html("");
	var endTime = $("#deviceCountSendOrders .datepicker").val();
	if(!endTime){
		hiAlert("提示","请选择截止日期");
		return false;
	}
	var data = $("#deviceCountSendOrdersForm").serialize();
	$.ajax({
		  type: "post",
		  url: $path+"/mc/deviceCount/sendOrders.do",
		  data:data,
		  cache: false,
		  success: function(msg){
				  if(msg=="1"){
					  hiAlert("提示","请先设置密码");
					  return false;
				  }else if(msg=="2"){
					  hiAlert("提示","截止日期不能在今天之前");
					  return false;
				  }else if(msg=="3"){
					  hiAlert("提示","账号未激活");
					  return false;
				  }
				 $("#showQRcode").append('<img alt="" src="'+msg+'" height="300px"/>');
			   }

		});
	
})
 
</script>
 
