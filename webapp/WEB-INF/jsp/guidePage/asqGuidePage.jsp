<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://www.youlb.com/tags/role" prefix="r"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
      <ul class="list-unstyled">
        <r:role auth="物业服务">
	        <li>
		       <h4>
		       &nbsp;&nbsp;<a class="li_a" href="javascript:void(0)" rel="${path}/mc/department/departmentshowPage.do?module=managementCompanyTable&modulePath=/departmentCompany">组织架构</a>
		       &nbsp;&nbsp;<a class="li_a" href="javascript:void(0)" rel="${path}/mc/worker/workershowPage.do?module=workerTable&modulePath=/worker">员工管理</a>
		       &nbsp;&nbsp;<a class="li_a" href="javascript:void(0)" rel="${path}/mc/workerGroup/workerGroupshowPage.do?module=workerGroupTable&modulePath=/workerGroup">分组管理</a>
		       </h4>
	        </li>
        </r:role>
      </ul>
</body>
</html>