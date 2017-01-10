<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<body>
<div>
	   <div>
		 <form id="deviceCountsaveForm" action="">
		   <div>
		    <input type="hidden" name="id" value="${deviceCount.id}"/>
		    <input type="hidden" name="domainId" id="deviceCountDomainIds" value="${deviceCount.domainId}"/>
		    <input type="hidden" name="sipNum" value="${deviceCount.sipNum}"/>
		    <input type="hidden" name="orderNum" value="${deviceCount.orderNum}"/>
		    
            <table>
              <tr>
                    <td><div class="firstFont">账号：</div></td>
                    <td><div><input name="deviceCount" readonly="readonly" class="form-control"  value="${deviceCount.deviceCount}"/></div></td>
                    <td><div class="leftFont">别名：</div></td>
                    <td><div><input name="deviceCountDesc" class="form-control" maxlength="20" value="${deviceCount.deviceCountDesc}"/></div></td>
              </tr>
              <tr>
	                <td><div class="firstFont">账号类型：</div></td>
	                <td><div>
	                   <select name="countType" class="form-control countType">
	                    <c:choose>
	                      <c:when test="${deviceCount.id!=null}">
	                         <c:if test="${deviceCount.countType=='1'}">
	                           <option <c:if test="${deviceCount.countType=='1'}">selected="selected"</c:if>  value="1">门口机</option>
	                         </c:if>
	                         <c:if test="${deviceCount.countType=='3'}">
                               <option <c:if test="${deviceCount.countType=='3'}">selected="selected"</c:if>  value="3">管理机</option>
                              </c:if>
	                      </c:when>
	                      <c:otherwise>
			                 <option <c:if test="${deviceCount.countType=='1'}">selected="selected"</c:if>  value="1">门口机</option>
	                         <option <c:if test="${deviceCount.countType=='3'}">selected="selected"</c:if>  value="3">管理机</option>
	                      </c:otherwise>
	                    </c:choose>
                      </select>
	                </div></td>
	                <td><div class="firstFont">账号状态：</div></td>
	                <td><div>
	                    <select name="countStatus" class="form-control">
                         <option <c:if test="${deviceCount.countStatus=='1'}">selected="selected"</c:if> value="1">激活</option>
                         <option <c:if test="${deviceCount.countStatus=='2'}">selected="selected"</c:if> value="2">未激活</option>
                        </select>
	                </div></td>
              </tr>
<!--               <tr> -->
<!--                     <td><div class="firstFont"><span class="starColor">*</span>被叫号码：</div></td> -->
<%--                     <td><div><input name="callNum" class="form-control" maxlength="20" placeholder="格式：020-88888888" value="${deviceCount.callNum}"/></div></td> --%>
<!--                     <td></td> -->
<!--                     <td></td> -->
<!--               </tr> -->
              <tr>
                    <td><div class="firstFont">经度：</div></td>
                    <td><div><input name="longitude" class="form-control" maxlength="20" value="${deviceCount.longitude}"/></div></td>
                    <td><div class="leftFont">纬度：</div></td>
                    <td><div><input name="latitude" class="form-control" maxlength="20" value="${deviceCount.latitude}"/></div></td>
              </tr>
               <tr>
                    <td><div class="firstFont">告警电话：</div></td>
                    <td><div><input name="warnPhone" class="form-control"  value="${deviceCount.warnPhone}"/></div></td>
                    <td><div class="leftFont">告警邮箱：</div></td>
                    <td><div><input name="warnEmail" class="form-control"  value="${deviceCount.warnEmail}"/></div></td>
              </tr>
           </table>
           </div>
           <c:if test="${deviceCount.id == null}">
	           <div class="firstFont">地址选择：</div>
	           <div>
	             <p id="deviceCountShowTree"></p>
	           </div> 
           </c:if>
         </form>
	   </div>
 </div>
</body>
<script type="text/javascript">
var showAddress = function(){
// 	 var countType = $("#deviceCountsaveForm .countType").val();
// 	 var countTypeIntVal = parseInt(countType);
// 	  switch(countTypeIntVal){
// 	  case 3:
	    // 显示 1级 社区tree
		$('#deviceCountShowTree').bstree({
				url: $path+'/mc/carrier',
				height:'auto',
				open: false,
				checkbox:true,
				checkboxLink:false,//是否联动多选框
				checkboxPartShow:true,//显示部分多选框
				layer: [1,2,3,4],
				showurl:false
		});
// 	    break;
// 	  default:
		// 显示 34级tree
// 		$('#deviceCountShowTree').bstree({
// 				url: $path+'/mc/carrier',
// 				height:'auto',
// 				open: false,
// 				checkbox:true,
// 				checkboxLink:false,//是否联动多选框
// 				checkboxPartShow:true,//显示部分多选框
// 				layer: [1,2,3,4],
// 				showurl:false
// 		});
	     
// 	  }
}

// var showCallNum = function(){
// 	 var countType = $("#deviceCountsaveForm .countType").val();
// 	 var countTypeIntVal = parseInt(countType);
// 	  switch(countTypeIntVal){
// 	  case 3:
		  
// 	    break;
// 	  default:
	     
// 	  }
// }



  $(function(){
	  showAddress();
// 	  $("#deviceCountsaveForm .countType").on('change',function(){
// 		  showCallNum();
// 	  })
  
	//多选框回显
	var treecheckbox = $("#deviceCountDomainIds").val();
	if(treecheckbox){
	    $("#deviceCountShowTree ."+treecheckbox).prop('checked',true);
	}
  })
</script>
 
