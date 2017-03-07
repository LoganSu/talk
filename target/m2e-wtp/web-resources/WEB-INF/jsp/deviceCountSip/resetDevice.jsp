<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
 <script type="text/javascript">
   $(function(){
	   $("#resetDeviceDiv .sure").on("click",function(){
		   var url =$path+"/mc/sipCount/restDevice.do";
		   var param = $("#resetDeviceForm").serialize();
		   $.post(url,param,function($data){
			   if($data){
				   hiAlert("提示",$data);
			   }else{
				   $("#unnormalModal").modal("hide");
			   }
		   })
	   })
   })
 </script>
</head>
<body>
  <div>
	   <div id="resetDeviceDiv">
		 <form id="resetDeviceForm" action="">
		 <input name="username" type="hidden" value="${username}"/>
		   <div>
            <table>
              <tr>
                <td><div class="firstFont">重启时间：</div></td>
                <td><div>
                   <select name="reset_time" class="form-control">
                     <option value="0">立即重启</option>
                     <option value="10">10分钟后重启</option>
                     <option value="30">30分钟后重启</option>
                   </select>
                </div></td>
                <td><div class="leftFont">重启类型：</div></td>
                <td><div>
                     <select name="reset_type" class="form-control">
                     <option value="0">app重启</option>
                     <option value="1">整机断电重启</option>
                   </select>
                </div></td>
              </tr>
           </table>
           </div>
         </form>
          <!-- 详情不显示按钮 -->
          <div class="modal-footer">
            <!--操作按钮 -->
            <input type="button" class="btn btn-primary sure" value="确定"/> 
            <input type="button" class="btn btn-default" data-dismiss="modal" value="关闭"/> 
          </div>
         
	   </div>
 </div>
</body>
</html>