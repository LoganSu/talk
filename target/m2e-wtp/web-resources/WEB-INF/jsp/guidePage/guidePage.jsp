<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://www.youlb.com/tags/role" prefix="r"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
      <ul class="list-unstyled">
        <r:role auth="运营商管理">
	        <li>
	           <h4>运营商管理：<small>（分配下级运营商账号和使用权限）</small></h4>
		       <h5><a class="panel-heading" data-toggle="collapse"data-parent="#accordion2" href="#collapseTwo">运营商管理</a>
		       >>&nbsp;&nbsp;<a class="li_a" href="javascript:void(0)" rel="${path}/mc/role/roleListshowPage.do?module=carrierRoleTable&modulePath=/carrierRole">运营商角色管理</a>
		       </h5>
	        </li>
        </r:role>
        
        <r:role auth="物业服务">
	        <li>
	           <h4>物业服务：<small>（物业内部组织架构，分组及员工信息管理）</small></h4>
		       <h5><a class="panel-heading" data-toggle="collapse"data-parent="#accordion2" href="#collapseTen">物业服务</a>
		       >>&nbsp;&nbsp;<a class="li_a" href="javascript:void(0)" rel="${path}/mc/department/departmentshowPage.do?module=managementCompanyTable&modulePath=/departmentCompany">组织架构</a>
		       >>&nbsp;&nbsp;<a class="li_a" href="javascript:void(0)" rel="${path}/mc/worker/workershowPage.do?module=workerTable&modulePath=/worker">员工管理</a>
		       >>&nbsp;&nbsp;<a class="li_a" href="javascript:void(0)" rel="${path}/mc/workerGroup/workerGroupshowPage.do?module=workerGroupTable&modulePath=/workerGroup">分组管理</a>
		       </h5>
	        </li>
        </r:role>
      </ul>
</body>
</html>