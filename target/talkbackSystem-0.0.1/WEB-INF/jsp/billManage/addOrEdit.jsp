<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<body>
<script type="text/javascript">
//时间控件
$(".datepicker").datepicker();
$(function(){
		domainTree("billManageShowTree", $path+'/mc/carrier', false, true, false, false,true, [4],"domainId");
// 		$('.treeid_' + $(this).parents('a').qdata().id).collapse('show');
// 		$(this).removeClass('glyphicon-plus').addClass('glyphicon-minus');
})
</script>
<div>
	   <div>
		 <form id="billManagesaveForm" action="">
		   <input type="hidden" name="id" value="${billManage.id}"/>
           <table>
              <tr>
                <td><div class="firstFont"><span class="starColor">*</span>姓名：</div></td>
                <td><div><input name="name" class="form-control required" title="姓名不能为空" maxlength="10" value="${billManage.name}"/></div></td>
                <td><div class="leftFont"><span class="starColor">*</span>联系电话：</div></td>
                <td><div><input name="phone" class="form-control required" title="联系电话不能为空" maxlength="11" value="${billManage.phone}"/></div></td>
              <tr/>
              <tr>  
                <td><div class="firstFont"><span class="starColor">*</span>类型：</div></td>
                <td><div>
                   <select name="type" class="form-control">
                     <option value="1">管理费</option>
                     <option value="2">水费</option>
                     <option value="3">电费</option>
                     <option value="4">燃气费</option>
                     <option value="5">其他</option>
                  </select>
                </div></td>
                <td><div class="leftFont"><span class="starColor">*</span>金额：</div></td>
                <td><div><input name="money" class="form-control required" title="金额不能为空" value="${billManage.money}"/></div></td>
              </tr>
              <tr>
                <td><div class="firstFont"><span class="starColor">*</span>计费开始日期：</div></td>
                <td><div><input name="startTimeStr" class="form-control required datepicker" readonly="readonly" title="计费开始日期不能为空" maxlength="10" value="${billManage.startTimeStr}"/></div></td>
                <td><div class="leftFont"><span class="starColor">*</span>计费结束日期：</div></td>
                <td><div><input name="endTimeStr" class="form-control required datepicker" readonly="readonly" title="计费结束日期不能为空" maxlength="11" value="${billManage.endTimeStr}"/></div></td>
              <tr/>
              
              <tr>
                <td><div class="firstFont">费用说明：</div></td>
                <td colspan="5"><div><input style="width: 450px" name="desc" class="form-control" value="${billManage.desc}"/></div></td>
              </tr>
           </table>
<!--            <div class="choeseArea"> -->
               <div style="width: 500px;height: 300px;overflow: auto;">
                    <p id="billManageShowTree"></p>              
               </div>
<!-- 	       </div> -->
         </form>
	   </div>
 </div>
</body>
</html>