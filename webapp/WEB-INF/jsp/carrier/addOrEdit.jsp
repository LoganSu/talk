<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <script type="text/javascript">
 
 </script>
<body>
<div>
	   <div>
		 <form id="carriersaveForm" action="">
		   <div>
		    <input type="hidden" name="id" value="${carrier.id}"/>
		    <input type="hidden" name="isNormal" value="${carrier.isNormal}"/>
		    <input type="hidden" name="domainNameId" value="${carrier.domainNameId}"/>
		    <span style="color: red;">说明：登录密码与手机号码一致</span>
            <table>
              <tr>
	                <td><div class="firstFont"><span class="starColor">*</span>运营商名称：</div></td>
	                <td><div><input name="carrierName" class="form-control required" title="运营商名称不能为空" maxlength="10" value="${carrier.carrierName}"/></div></td>
	                <td><div class="leftFont"><span class="starColor">*</span>平台名称：</div></td>
	                <td><div><input name="platformName" class="form-control required" title="平台名称不能为空" maxlength="10" value="${carrier.platformName}"/></div></td>
                    <td></td>  
                   	 <td></td>   
               </tr>
               <tr>
                    <td><div class="firstFont"><span class="starColor">*</span>二级域名：</div></td>
                    <td><div>
	                  <select name="domainNameParentId" class="form-control province">
	                    <c:forEach var="domainName" items="${domainList}">
           	                <option value="${domainName.id}" <c:if test="${domainName.id eq carrier.domainNameParentId}">selected="selected"</c:if> >${domainName.domain}</option>
	                    </c:forEach>
	                  </select>
	                </div></td>
                    <td><div class="firstFont"><span class="starColor">*</span>运营商简称：</div></td>
                    <td><div><input name="carrierNum" class="form-control required" title="运营商简称不能为空且最多为10个字母" maxlength="10" value="${carrier.carrierNum}"/></div></td>
	                <td><div class="firstFont"><span class="starColor number">*</span>手机号码：</div></td>
	                <td><div><input name="tel" class="form-control required" title="手机号码不能为空" maxlength="11" value="${carrier.tel}"/></div></td>
              </tr>
               <tr> 
	                <td><div class="firstFont"><span class="starColor">*</span>固定电话：</div></td>
	                <td><div><input name="fax" class="form-control required" title="固定电话不能为空" maxlength="15" placeholder="格式：020-88888888"   value="${carrier.fax}"/></div></td>
	                <td><div class="leftFont"><span class="starColor">*</span>地址：</div></td>
	                <td colspan="3"><div><input name="address" class="form-control required" maxlength="100" style="width:350px" title="地址不能为空" value="${carrier.address}"/></div></td>
<!-- 	                <td><div class="leftFont">邮编：</div></td> -->
<%-- 	                <td><div><input name="postcode" class="form-control"  value="${carrier.postcode}"/></div></td> --%>
              </tr>
               <tr>
	                <td><div class="firstFont">备注：</div></td>
	                <td colspan="5"><div><input name="remark" class="form-control" maxlength="200" style="width: 650px" value="${carrier.remark}"/></div></td>
              </tr>
           </table>
           </div>
<!-- 		   <div  style="margin-top: 20px;margin-left: 20px;"> -->
<!-- 	               <div><label>选择区域：</label></div> -->
<!-- 	               <div class="showDomainTable" style="height: 500px"> -->
	                 <p id="carrierShowTree"></p>
<!-- 	               </div> -->
<!-- 	       </div>         -->
         </form>
	   </div>
 </div>
</body>
<style>
.showDomainTableDiv{
padding-left: 20px
}
</style>
<script type="text/javascript">
  $(function(){
	  
	  var id = $("#carriersaveForm [name='id']").val();
	  var params = "id="+id;
	  //显示多选框的级别
	    // 普通tree
		$('#carrierShowTree').bstree({
				url: $path+'/mc/carrier',
				param :params,
// 				open:false,
				showurl:false,
				checkboxPartShow:true,
				layer:[0,1,2,3,4],
				checkbox:true
		});
	    
// 	  var id = $("#carriersaveForm [name='id']").val();
// 	  $.post($path+"/mc/carrier/domainList.do","id="+id,function($data){
// 		  var $div = $(".showDomainTable");
// 		  eachPrivilege($data, $div);
// 	  })
	  
// 	 //选择了子节点默认选中所有的父节点
// 	 $(document).on('click',".showDomainTableDiv [name='domainIds']",function(){
// 		var checks =  $(this).parents(".showDomainTableDiv").children("input");
// 		if(checks.prop("checked")){
// 			checks.prop("checked",true);
// 		}
// 	 })	  
// 	 //父节点取消选择，子节点全部取消,全选
// 	 $(document).on('click',".showDomainTableDiv [name='domainIds']",function(){
// 		 var checks = $(this).parent().find("input");
// 		 if(checks.prop("checked")){
// 				checks.prop("checked",true);
// 			}else{
// 				checks.prop("checked",false);
// 			}
// 	 })
// 	 //递归显示权限列表
// 	 var eachPrivilege =  function(list,$div){
// 		 $.each(list,function(i,obj){
// 			 var divStr = "";
// 			 if(obj.checked==1){
// 				 divStr = "<div class='showDomainTableDiv'><input name='domainIds' checked='checked' value='"+obj.id+"' type='checkbox'/><span>"+obj.remark+"</span></div>";
// 			 }else{
// 				 divStr = "<div class='showDomainTableDiv'><input name='domainIds' value='"+obj.id+"' type='checkbox'/><span>"+obj.remark+"</span></div>";
// 			 }
// 			 $div.append(divStr);
// 			var $subDiv = $(".showDomainTableDiv [value='"+obj.id+"']").parent();
// 			 if(obj.children){
// 				 eachPrivilege(obj.children,$subDiv);
// 			 }
// 		 });
// 	  }
	
	  
	  
  })
 </script>
