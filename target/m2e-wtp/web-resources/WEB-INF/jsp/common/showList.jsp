<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
String  module= "";
if(request.getAttribute("module")!=null){
	  module= (String)request.getAttribute("module");
}
%>
<script type="text/javascript">
$(function(){
var module = <%=module%>
		//加载列表
		$("#tableShowList").bootstrapTable(module);
})
</script> 
<body>
    <div class="row" id="searchInfoDiv">
       <c:if test="${modulePath!=null}">
          <c:import url="${path}${modulePath}/search.do"></c:import>
       </c:if>
      <div class="col-md-6" style="overflow: auto"></div>
   </div>
   <div class="showDataDiv">
      <!-- 分割线 -->
      <hr noshade="noshade"/>
      <!-- 数据展现div -->
      <div id="tableShowListDiv">
        <!-- 数据列表显示div -->
        <table id="tableShowList"></table>
        <!-- 图标显示div -->
        <div id="showChars"></div>
      </div>
   </div>
</body>
</html>