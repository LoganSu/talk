<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://www.youlb.com/tags/role" prefix="r"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>

  <ul class="nav nav-pills">
  <r:role auth="房产管理"> 
		<li class="dropdown">
			<a class="dropdown-toggle" data-toggle="dropdown" href="#">
				房产管理 <span class="caret"></span>
			</a>
			<ul class="dropdown-menu">
			  <r:role auth="房产信息"> 
                  <li><a tree_id="houseInfoTree" class="li_a tree" href="javascript:void(0)" rel="${path}/mc/area/areaListshowPage.do?module=areaTable&modulePath=/area">房产信息</a></li>
			  </r:role>
			</ul>
			
		</li>
	</r:role>
   <r:role auth="物业服务"> 
		<li class="dropdown">
			<a class="dropdown-toggle" data-toggle="dropdown" href="#">
				物业服务 <span class="caret"></span>
			</a>
			<ul class="dropdown-menu">
			   <r:role auth="关于小区">
                   <li><a tree_id="neighborhoodsTree" class="tree li_a" href="javascript:void(0)" rel="${path}/mc/aboutNeighborhoods/aboutNeighborhoodsshowPage.do?1=1">关于小区</a></li>
               </r:role>
               <r:role auth="物业投诉">
                   <li><a class="li_a" href="javascript:void(0)" rel="${path}/mc/repairs/repairsshowPage.do?module=repairsTable&modulePath=/repairs&orderNature=2">物业投诉</a></li>
               </r:role>
               <r:role auth="物业报修">
                   <li><a class="li_a" href="javascript:void(0)" rel="${path}/mc/repairs/repairsshowPage.do?module=repairsTable&modulePath=/repairs&orderNature=1">物业报修</a></li>
               </r:role>
               <r:role auth="组织架构">
                   <li><a tree_id="managementDepartmentTree" class="tree li_a" href="javascript:void(0)" rel="${path}/mc/department/departmentshowPage.do?module=managementCompanyTable&modulePath=/departmentCompany">组织架构</a></li>
               </r:role>
               <r:role auth="员工管理">
                   <li><a class="li_a" href="javascript:void(0)" rel="${path}/mc/worker/workershowPage.do?module=workerTable&modulePath=/worker">员工管理</a></li>
               </r:role>
               <r:role auth="分组管理">
                   <li><a class="li_a" href="javascript:void(0)" rel="${path}/mc/workerGroup/workerGroupshowPage.do?module=workerGroupTable&modulePath=/workerGroup">分组管理</a></li>
               </r:role>

			</ul>
			
		</li>
	</r:role>
	<r:role auth="权限管理"> 
		<li class="dropdown">
			<a class="dropdown-toggle" data-toggle="dropdown" href="#">
				权限管理 <span class="caret"></span>
			</a>
			<ul class="dropdown-menu">
			    <r:role auth="帐户管理"> 
				   <li><a class="li_a" href="javascript:void(0)" rel="${path}/mc/user/userListshowPage.do?module=operatorTable&modulePath=/operator">帐户管理</a></li>
				</r:role>
				<r:role auth="角色管理"> 
				   <li><a class="li_a" href="javascript:void(0)" rel="${path}/mc/role/roleListshowPage.do?module=roleTable&modulePath=/role">角色管理</a></li>
				</r:role>
			</ul>
		</li>
	</r:role>
</ul>

</body>
</html>