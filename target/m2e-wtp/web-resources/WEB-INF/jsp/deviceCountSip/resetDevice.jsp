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
                <td><div>
                   <select>
                     <option value="0">立即重启</option>
                     <option value="10">10分钟后重启</option>
                     <option value="30">30分钟后重启</option>
                   </select>
                </div></td>
                <td><div class="leftFont">重启类型：</div></td>
                <td><div>
                     <select>
                     <option value="0">app重启</option>
                     <option value="1">整机断电重启</option>
                   </select>
                </div></td>
              </tr>
           </table>
           </div>
         </form>
	   </div>
 </div>
</body>
</html>