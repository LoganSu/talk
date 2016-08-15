<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://www.youlb.com/tags/role" prefix="r"%>
    
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
//时间控件
$(".datepicker").datepicker();
</script>
</head>
<body>
	<!-- 功能按钮 div-->
	 <!-- 查询form div  --> 
    <div class="searchInfoDiv">
          <form id="cardRecordSearchForm" action="" method="post">
           <input type="hidden" class="hiddenId" name="id" value="${id}"/>
           <input type="hidden" class="hiddenId" name="mode" value="1"/>
           <table>
              <tr>
                <td><div class="firstFont">时间范围：</div></td>
                <td><div><input name="startTime" class="form-control datepicker"/></div></td>
                <td><div class="firstFont">至</div></td>
                <td><div><input name="endTime" class="form-control datepicker"/></div></td>
                <td><div class="leftFont">卡号：</div></td>
                <td><div><input name="cardsn" class="form-control"/></div></td>
                <td><div class="leftFont"><button class="btn btn-info btn-sm reset">重置</button></div></td>
                <td><div class="leftFont"><button class="btn btn-info btn-sm search" rel="${path}/mc/cardRecord/showList.do">查询</button></div></td>
              </tr>
           </table>
        </form>
       </div>  
<!--     <div class="searchInfoDiv"> -->
<!--       <span>地区列表：</span> -->
<!--     </div> -->
</body>
