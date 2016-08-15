<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<body>
<script type="text/javascript">
$(function(){
	// 普通tree
	$('#repairsShowTree').bstree({
			url: $path+'/mc/carrier',
			height:'600',
			open: true,
			checkbox:true,
			checkboxLink:false,
			checkboxPartShow:true,//显示部分多选框
			layer: [4],
			showurl:false
	});
	//多选框回显
	var domainId = $("#repairssaveForm .domainId").val();
	if(domainId){
		$("#repairsShowTree ."+$.trim(domainId)).prop('checked',true);
	}
	
	$("#repairsShowTree .treecheckbox").on("click",function(){
		$("#repairsShowTree .treecheckbox").prop('checked',false);
		$(this).prop('checked',true);
		var domainId = $(this).val();
		$("#repairssaveForm .domainId").val(domainId);
		$.post($path+"/mc/room/getAddressByDomainId.do","domainId="+domainId,function($address){
		   $("#repairssaveForm .address").html($address);
		})
			
		
	})
})

</script>
<!-- <div>    -->

<div class="row">
  <div class="col-md-4">
        <p id="repairsShowTree"></p> 
  </div>
  <div class="col-md-1"></div>
  <div class="col-md-7">
      <form id="repairssaveForm" action="">
		   <input type="hidden" name="id" value="${repairs.id}"/>
           <table>
              <tr>
                <td><div class="firstFont"><span class="starColor">*</span>联系人：</div></td>
                <td><div><input name="linkman" class="form-control required" title="姓名不能为空" value="${repairs.linkman}"/></div></td>
              </tr>
               <tr>
                <td><div class="firstFont"><span class="starColor">*</span>联系电话：</div></td>
                <td><div><input name="phone" class="form-control required number" title="请填写11位手机号码" maxlength="11" value="${repairs.phone}"/></div></td>
              </tr>
              <tr>
                <td><div class="firstFont">来源：</div></td>
                <td><div>
	                 <select name="comeFrom" class="form-control">
	                    <option value="1" <c:if test="${repairs.comeFrom==1}">selected="selected"</c:if>>电话</option>
	                    <option value="2" <c:if test="${repairs.comeFrom==2}">selected="selected"</c:if>>来访</option>
	                    <option value="3" <c:if test="${repairs.comeFrom==3}">selected="selected"</c:if>>终端</option>
	                 </select>
                </div></td>
               </tr>
               <tr>
                <td><div class="firstFont">地址：</div></td>
                <td><div><input type="hidden" name="domainId" class="form-control domainId" value="${repairs.domainId}"/>
                         <span class="address">${repairs.address}</span></div></td>
              </tr>
               <tr>
                <td><div class="firstFont">内容：</div></td>
                <td><div>
                      <textarea name="serviceContent" class="form-control" rows="6" cols="10" style="width: 300px" maxlength="200">${repairs.serviceContent}</textarea>
                </div></td>
               </tr>
           </table>
         </form>
   </div>
</div>
</body>
</html>