<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<body>
<div>
	   <div>
	   <form id="appManagesaveForm" name="appManagesaveForm" action="${path}/mc/appManage/saveOrUpdate.do" target="appManageSubmitFrame" enctype="multipart/form-data" method="post">
		   <input type="hidden" name="id" value="${appManage.id}"/>
		   <input type="hidden" name="appType" value="${appManage.appType}"/>
		   <input type="hidden" id="appManageDomainIds" value="${appManage.treecheckbox}"/>
           <table>
              <tr>
<!--                 <td><div class="firstFont"><span class="starColor">*</span>软件名称：</div></td> -->
<%--                 <td><div><input name="versionName" class="form-control" value="${appManage.versionName}"/></div></td> --%>
<!--                 <td><div class="leftFont"><span class="starColor">*</span>软件版本：</div></td> -->
<%--                 <td><div><input name="versionNum" class="form-control" value="${appManage.versionNum}"/></div></td> --%>
                
                <td><div class="firstFont">是否强制升级：</div></td>
                <td><div>
                    <select class="form-control" name="autoInstal">
                      <option <c:if test="${appManage.autoInstal=='2'}">selected="selected"</c:if> value="2">否</option>
                      <option <c:if test="${appManage.autoInstal=='1'}">selected="selected"</c:if> value="1">是</option>
                    </select>
                </div></td>
                <td><div class="leftFont"><span class="starColor">*</span>
                   <c:choose>
                     <c:when test="${appManage.appType == 6}">
                        <select class="appManageSeolect"><option class="andriod">apk上传</option><option <c:if test="${appManage.relativePath==null&&appManage.appType == 6&&appManage.id != null}">selected="selected"</c:if> class="IOS">IOS地址</option></select>
                     </c:when>
                     <c:otherwise>
                       apk上传
                     </c:otherwise>
                   </c:choose>
                                               ：</div></td>
                <td colspan="1"><div class="changeInputDiv">
                    <c:choose>
                      <c:when test="${appManage.relativePath==null&&appManage.appType == 6&&appManage.id != null}">
                         <input type="text" value="${appManage.serverAddr}" name="serverAddr" style="width: 300px" class="form-control"/>
                      </c:when>
                    <c:otherwise>
                         <input type="file" id="uploadFile" name="uploadFile" style="width: 300px" class="form-control"/>
                    </c:otherwise>
                    </c:choose>
                </div></td>
              </tr>
              <tr>
                  <td><div class="leftFont"><span class="starColor">*</span>APP名称：</div></td>
                  <td><div>
                     <input maxlength="50"  name="appName" value="${appManage.appName}" class="form-control"/>
                  </div></td>
              </tr>
              <c:if test="${appManage.appType == 6}">
                <c:choose>
                   <c:when test="${appManage.opraterType == 1}">
                      <tr>
		                  <td><div class="leftFont"><span class="starColor">*</span>图标：</div></td>
		                  <td colspan="3"><div>
		                     <img alt="" src="${appManage.iconUrl}" style="width: 100px;height: 100px">
		                  </div></td>
		              </tr>
                   </c:when>
                   <c:otherwise>
		              <tr>
		                  <td><div class="leftFont"><span class="starColor">*</span>图标上传：</div></td>
		                  <td colspan="3"><div>
		                    <input type="file" id="appIcon" name="appIcon" style="width: 300px" class="form-control"/>
		                  </div></td>
		              </tr>
                   </c:otherwise>
                </c:choose>
              </c:if>
              <tr>
              <td><div class="firstFont"><span class="starColor">*</span>版本说明：</div></td>
                <td colspan="4"><div>
                  <textarea name="versionDes" maxlength="20" class="form-control" style="width: 560px" rows="3" cols="5">${appManage.versionDes}</textarea>
<%--                   <input name="versionDes" class="form-control" value="${appManage.versionDes}"/> --%>
                </div></td>
                
              </tr>
           </table>
           <!-- 门口机app和管理机的app可以指定目标 -->
           <c:if test="${appManage.appType ==1||appManage.appType ==3}">
<!-- 	           <div style="margin-top: 10px"><label>选择升级目标</label></div> -->
	           <div class="choeseArea">
<!-- 		           <table> -->
<!-- 		              <tr> -->
<%-- 		                <td><div><input type="radio" name="upgradeType" value="1" <c:if test="${appManage.upgradeType==1}">checked="checked"</c:if>/></div></td> --%>
<!-- 		                <td><div>按APP版本名称升级：</div></td> -->
<!-- 		                <td><div> -->
<!-- 		                    <select class="form-control" name="targetVersion"> -->
<%-- 		                      <c:forEach items="${appList}" var="app"> --%>
<%--      		                       <option <c:if test="${app.id==appManage.targetVersion}">selected="selected"</c:if> value="${app.id}">${app.versionName}</option> --%>
<%-- 		                      </c:forEach> --%>
<!-- 		                    </select>                 -->
<!-- 		                </div></td> -->
<!-- 		              </tr> -->
<!-- 		              <tr> -->
<%-- 		                <td><div><input type="radio" name="upgradeType" value="2" <c:if test="${appManage.upgradeType==2}">checked="checked"</c:if>/></div></td> --%>
<!-- 		                <td><div>按区域选择：</div></td> -->
<!-- 		                <td></td> -->
<!-- 		              </tr> -->
<!-- 		           </table> -->
		           <table>
		              <tr>
		                <td><div><input type="radio" checked="checked" name="upgradeType" value="1" <c:if test="${appManage.upgradeType==1}">checked="checked"</c:if>/></div></td>
		                <td><div>全部</div></td>
		              </tr>
		              <tr>
		                <td><div><input type="radio" name="upgradeType" value="2" <c:if test="${appManage.upgradeType==2}">checked="checked"</c:if>/></div></td>
		                <td><div>指定范围</div></td>
		                <td></td>
		              </tr>
		           </table>
	                <div style="width: 500px;height: 400px;overflow: auto;">
	                     <p id="appManageShowTree"></p>              
	                </div>
	           </div>
           </c:if>
           <!-- 详情不显示按钮 -->
	           <div class="modal-footer">
		         <!--操作按钮 -->
                <c:if test="${appManage.opraterType!=1}">
		           <input type="button" class="btn btn-primary" value="确定"/> 
	            </c:if>
		            <input type="button" class="btn btn-default" data-dismiss="modal" value="关闭"/> 
		       </div>
         </form>
	   </div>
 </div>
<!-- 	<div class="progress"> -->
<!-- 	    <div class="progress-bar" role="progressbar" data-transitiongoal="100"></div> -->
<!-- 	</div> -->

 <!-- 隐藏iframe设置表单的目标为这个嵌入框架  使页面效果不会跳转 -->
 <iframe style="width:0; height:0;display: none;" id="appManageSubmitFrame" name="appManageSubmitFrame"></iframe>
</body>
<script type="text/javascript">
  $(function(){
	  $("#appManagesaveForm .btn-primary").on('click',function(){
		  
		  $("#appManagesaveForm").submit();
		 //文件大小
// 		 var file = document.getElementById("uploadFile");  
// 		 var size = file.files[0].size;
// 		 alert(size);
// 		    $('.progress .progress-bar').progressbar({
// 		    	transition_delay: 300,//延时
// 		    	refresh_speed: 200,
// 		    	display_text: 'center', 
// 		    	use_percentage: false,
// 		    	update: function(current_percentage, $this) {
// // 		    		alert("update");
// 		    		var eventFun = function(){  
// 		                $.ajax({  
// 		                    type: 'GET',  
// 		                    url: $path+'/mc/appManage/progress.do',  
// 		                    data: {},  
// 		                    dataType: 'json',  
// 		                    success : function(data){
// // 		                    	   alert(data.toSource())
// //                                     current_percentage=data.rate
// 		                    	    $this.attr('data-transitiongoal',data.rate+' %');  
// 		                    	    $this.empty();  
// 		                    	    $this.append(data.show);   
// 		                            if(data.rate == 100){
// 		                                window.clearInterval(intId);  
// 		                            }     
// 		            }});};  
// 		            var intId = window.setInterval(eventFun,500);
// 		        },
// 		        done:function(){
// // 		    		alert("done");
// 		    	},
// 		        fail: function(){
// 		    		alert("fail");
// 		    	}
// 		    	});
	  })
	  
	  
	  
		// 普通tree
		$('#appManageShowTree').bstree({
				url: $path+'/mc/carrier',
				height:'auto',
				open: false,
				checkbox:true,
				checkboxLink:false,//是否联动
				showurl:false
		});
		//多选框回显
		var treecheckbox = $("#appManageDomainIds").val();
		//java代码 treecheckbox==null 则 treecheckbox=[]
		if(treecheckbox.length>2){
			treecheckbox=treecheckbox.substring(1,treecheckbox.length-1);
			var arr= treecheckbox.split(",");
			  $.each(arr,function(index,obj){
				  $("#appManageShowTree ."+obj.trim()).prop('checked',true);
			  });
		}
		//
		$("#appManagesaveForm .appManageSeolect").on("change",function(){
			var className = $(this)[0].options[$(this)[0].selectedIndex].className;
			if(className=="andriod"){
				$("#appManagesaveForm .changeInputDiv input").remove();
				$("#appManagesaveForm .changeInputDiv").append('<input type="file" id="uploadFile" name="uploadFile" style="width: 300px" class="form-control"/>');
			}else{
				$("#appManagesaveForm .changeInputDiv input").remove();
				$("#appManagesaveForm .changeInputDiv").append('<input type="text" name="serverAddr" style="width: 300px" class="form-control"/>');
			}
		})
  })
</script>
</html>