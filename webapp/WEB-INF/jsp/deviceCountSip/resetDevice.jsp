<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
  <div>
	   <div>
		 <form id="resetDeviceForm" action="">
		   <div>
            <table>
              <tr>
                <td><div class="firstFont">重启时间：</div></td>
                <td><div><input name="deviceNum" class="form-control" value="${device.deviceNum}"/></div></td>
                <td><div class="leftFont">重启类型：</div></td>
                <td><div><input name="deviceModel" class="form-control" value="${device.deviceModel}"/></div></td>
              </tr>
           </table>
           </div>
         </form>
	   </div>
 </div>
</body>
</html>