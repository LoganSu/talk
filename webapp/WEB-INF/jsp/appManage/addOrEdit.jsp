<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<body>
<div>
	   <div>
	   <form id="appManagesaveForm" name="appManagesaveForm" action="" target="appManageSubmitFrame" enctype="multipart/form-data" method="post">
		   <input type="hidden" name="id" value="${appManage.id}"/>
		   <input type="hidden" name="appType" value="${appManage.appType}"/>
		   <input type="hidden" id="appManageDomainIds" value="${appManage.treecheckbox}"/>
		   <input type="hidden" name="relativePath" value="${appManage.relativePath}"/>
		   <c:if test="${(appManage.id ==null&&appManage.appType == 6)||(appManage.appType!=null&&!appManage.threeAppType eq 'IOS')||appManage.appType != 6}">
		      <input type="hidden" name="serverAddr" value="${appManage.serverAddr}"/>
		   </c:if>
		   <input type="hidden" name="md5Value" value="${appManage.md5Value}"/>
		   <input type="hidden" name="appSizeStr" value="${appManage.appSize}"/>
		   <input type="hidden" name="iconUrl" value="${appManage.iconUrl}"/>
		   <input type="hidden" class = "threeAppType" value="${appManage.threeAppType}"/>
		   
		   
		   
           <table class="appManagesaveTable">
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
                <c:choose>
                  <c:when test="${appManage.id ==null}">
		                <td><div class="leftFont">
		                   <c:choose>
		                     <c:when test="${appManage.appType == 6}">
		                        <select class="appManageSeolect form-control" name="threeAppType" style="width: 80px"><option <c:if test="${appManage.threeAppType eq 'andriod'}">selected="selected"</c:if> value="andriod" class="andriod">apk上传</option><option value="IOS"  <c:if test="${appManage.threeAppType eq 'IOS'}">selected="selected"</c:if> class="IOS">IOS地址</option></select>
		                     </c:when>
		                     <c:otherwise>
		                       <span class="starColor">*</span>apk上传：
		                     </c:otherwise>
		                   </c:choose>
		                  </div></td>
		                <td colspan="1"><div class="changeInputDiv" id="uploadFileDiv">
		                    <c:choose>
		                      <c:when test="${appManage.relativePath==null&&appManage.appType == 6&&appManage.id != null}">
		                         <input type="text" value="${appManage.serverAddr}" name="serverAddr" style="width: 300px" class="form-control"/>
		                      </c:when>
		                    <c:otherwise>
		                         <input type="file" id="uploadFile" value="" style="width: 300px" class="form-control"/>
		                    </c:otherwise>
		                    </c:choose>
		                </div></td>
                  </c:when>
                  
                  <c:otherwise>
                       <c:choose>
                          <c:when test="${appManage.threeAppType eq 'IOS'}">
	                  		   <td><div class="leftFont">
			                        <select class="appManageSeolect form-control" name="threeAppType" style="width: 80px"><option value="IOS"  <c:if test="${appManage.threeAppType eq 'IOS'}">selected="selected"</c:if> class="IOS">IOS地址</option></select>
			                  </div></td>
			                <td colspan="1"><div class="changeInputDiv" id="uploadFileDiv">
			                         <input type="text" value="${appManage.serverAddr}" name="serverAddr" style="width: 300px" class="form-control"/>
			                </div></td>
	                    </c:when>
	                    <c:otherwise>
	                        <td></td>
	                        <td></td>
	                    </c:otherwise>
                       </c:choose>
                  </c:otherwise>
                </c:choose>
                
              </tr>
              <c:choose>
                 <c:when test="${appManage.threeAppType != 'IOS'}">
		              <tr class="chooseShowTr">
		                  <td><div class="firstFont"><span class="starColor">*</span>APP名称：</div></td>
		                  <td><div>
		                     <input maxlength="50"  name="appName" value="${appManage.appName}" class="form-control"/>
		                  </div></td>
		                   <td><div class="leftFont"><span class="starColor">*</span>版本名称：</div></td>
		                  <td><div>
		                     <input maxlength="50"  name="versionName" value="${appManage.versionName}" class="form-control"/>
		                  </div></td>
		              </tr>
		               <tr class="chooseShowTr">
		                  <td><div class="firstFont"><span class="starColor">*</span>版本号：</div></td>
		                  <td><div>
		                     <input maxlength="50"  name="versionCode" value="${appManage.versionCode}" class="form-control"/>
		                  </div></td>
		                   <td><div class="leftFont"><span class="starColor">*</span>包名：</div></td>
		                  <td><div>
		                     <input maxlength="50"  name="packageName" value="${appManage.packageName}" class="form-control"/>
		                  </div></td>
		              </tr>
                 </c:when>
                 <c:otherwise>
                     <tr class="chooseShowTr">
		                  <td><div class="firstFont"><span class="starColor">*</span>APP名称：</div></td>
		                  <td><div>
		                     <input maxlength="50"  name="appName" value="${appManage.appName}" class="form-control"/>
		                  </div></td>
		              </tr>
                 </c:otherwise>
              </c:choose>
              
              <c:if test="${appManage.appType == 6}">
                <c:choose>
                   <c:when test="${appManage.opraterType == 1}">
                      <tr>
		                  <td><div class="firstFont"><span class="starColor">*</span>图标：</div></td>
		                  <td colspan="3"><div>
		                     <img alt="" src="${appManage.iconUrl}" style="width: 100px;height: 100px">
		                  </div></td>
		              </tr>
                   </c:when>
                   <c:otherwise>
		              <tr>
		                  <td><div class="firstFont"  id="appIconDiv"><span class="starColor">*</span>图标上传：</div></td>
		                  <td colspan="3"><div>
		                    <input type="file" id="appIcon" name="appIcon" style="width: 300px" class="form-control"/>
		                  </div></td>
		              </tr>
                   </c:otherwise>
                </c:choose>
              </c:if>
             <!-- 门口机 -->
              <c:if test="${appManage.appType == 1}">
                  <td><div class="firstFont">软件型号：</div></td>
                  <td><div>
                    <select class="form-control" name="softwareType">
                      <c:forEach items="${softwareTypeList}" var="softwareType">
                         <option <c:if test="${appManage.softwareType==softwareType}">selected="selected"</c:if>  value="${softwareType}">${softwareType}</option>
                      </c:forEach>
                    </select>
                 </div></td>
              </c:if>
              <tr>
              <td><div class="firstFont"><span class="starColor">*</span>版本说明：</div></td>
                <td colspan="4"><div>
                  <textarea name="versionDes" <c:if test="${appManage.appType == 6}">maxlength="50"</c:if> <c:if test="${appManage.appType != 6}">maxlength="200"</c:if> class="form-control" style="width: 560px" rows="3" cols="5">${appManage.versionDes}</textarea>
<%--                   <input name="versionDes" class="form-control" value="${appManage.versionDes}"/> --%>
                </div></td>
                
              </tr>
           </table>
           <table class="table table-striped table-hover text-left" style="margin-top: 40px;">
			  <thead>
		        <tr>
		          <th class="col-md-4"></th>
		          <th class="col-md-2"></th>
		          <th class="col-md-6"></th>
		        </tr>
		      </thead>
		      <tbody id="fsUploadProgress">
		      </tbody>
			</table>
           <!-- 门口机app和管理机的app可以指定目标 -->
           <c:if test="${(appManage.appType ==1 && appManage.id ==null)||(appManage.appType ==1 && appManage.opraterType==1)}">
              <div style="margin-top: 10px"><label>选择升级范围</label></div>
	           <div class="choeseArea">
		           <table>
		              <tr>
		                <td><div><input type="radio" checked="checked" name="sendType" value="1" <c:if test="${appManage.sendType==1}">checked="checked"</c:if>/></div></td>
		                <td><div>全部</div></td>
		              </tr>
		              <tr>
		                <td><div><input type="radio" name="sendType" value="2" <c:if test="${appManage.sendType==2}">checked="checked"</c:if>/></div></td>
		                <td><div>指定范围</div></td>
		                <td></td>
		              </tr>
		           </table>
	                <div class="showRolesTable" style="height: 500px">
			              <div>
				            <c:forEach items="${ipManageList}" var="ipManage" varStatus="staus">
			                       <!--五个换行-->
		                           <c:if test="${staus.index>0&&staus.index%5==0}">
		                             <br/>
		                           </c:if>
			                     <input name="ipManageIds" value="${ipManage.neiborFlag}" <c:if test="${fn:contains(appManage.ipManageIds, ipManage.neiborFlag) && appManage.sendType==2}">checked="checked"</c:if> type="checkbox"/><span>${ipManage.neibName}</span>&nbsp;&nbsp;&nbsp;&nbsp;
				            </c:forEach>
			              </div>
			                
			           </div>
	            </div>
<!-- 	           <div style="margin-top: 10px"><label>选择升级目标</label></div> -->
<!-- 	           <div class="choeseArea"> -->
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
<!-- 		           <table> -->
<!-- 		              <tr> -->
<%-- 		                <td><div><input type="radio" checked="checked" name="upgradeType" value="1" <c:if test="${appManage.upgradeType==1}">checked="checked"</c:if>/></div></td> --%>
<!-- 		                <td><div>全部</div></td> -->
<!-- 		              </tr> -->
<!-- 		              <tr> -->
<%-- 		                <td><div><input type="radio" name="upgradeType" value="2" <c:if test="${appManage.upgradeType==2}">checked="checked"</c:if>/></div></td> --%>
<!-- 		                <td><div>指定范围</div></td> -->
<!-- 		                <td></td> -->
<!-- 		              </tr> -->
<!-- 		           </table> -->
<!-- 	                <div style="width: 500px;height: 400px;overflow: auto;"> -->
<!-- 	                     <p id="appManageShowTree"></p>               -->
<!-- 	                </div> -->
<!-- 	           </div> -->
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

 <!-- 隐藏iframe设置表单的目标为这个嵌入框架  使页面效果不会跳转 -->
 <iframe style="width:0; height:0;display: none;" id="appManageSubmitFrame" name="appManageSubmitFrame"></iframe>
</body>
<script type="text/javascript">

function calculate(file,callBack){
// 	alert(file);
	var blobSlice = File.prototype.slice || File.prototype.mozSlice || File.prototype.webkitSlice,
	        running = false;
		if (running) {
		    return;
		}
		var fileReader = new FileReader();
		fileReader.onload = function (e) {
		    running = false;
		
		    if (file.size != e.target.result.byteLength) {
		    	alert("获取文件MD5出错");
		    } else {
		    	 callBack(SparkMD5.ArrayBuffer.hash(e.target.result).toUpperCase());  
// 		        return SparkMD5.ArrayBuffer.hash(e.target.result).toUpperCase(); // compute hash
		    }
		};
	
		fileReader.onerror = function () {
		    running = false;
		};
	
		running = true;
		fileReader.readAsArrayBuffer(file);
	
}




 
  $(function(){
	  var uploader = Qiniu.uploader({
		    runtimes: 'html5,flash,html4',      // 上传模式，依次退化
		    browse_button: 'uploadFile',         // 上传选择的点选按钮，必需
		    // 在初始化时，uptoken，uptoken_url，uptoken_func三个参数中必须有一个被设置
		    // 切如果提供了多个，其优先级为uptoken > uptoken_url > uptoken_func
		    // 其中uptoken是直接提供上传凭证，uptoken_url是提供了获取上传凭证的地址，如果需要定制获取uptoken的过程则可以设置uptoken_func
		    // uptoken : '<Your upload token>', // uptoken是上传凭证，由其他程序生成
		    uptoken_url: $path+'/mc/qiniu/token',         // Ajax请求uptoken的Url，强烈建议设置（服务端提供）
// 		    uptoken_func: function(file){    // 在需要获取uptoken时，该方法会被调用
// 		       // do something
// 		       return uptoken;
// 		    },
		    get_new_uptoken: false,             // 设置上传文件的时候是否每次都重新获取新的uptoken
		    // downtoken_url: '/downtoken',
		    // Ajax请求downToken的Url，私有空间时使用，JS-SDK将向该地址POST文件的key和domain，服务端返回的JSON必须包含url字段，url值为该文件的下载地址
// 		    unique_names: true,              // 默认false，key为文件名。若开启该选项，JS-SDK会为每个文件自动生成key（文件名）
// 		    save_key: true,                  // 默认false。若在服务端生成uptoken的上传策略中指定了sava_key，则开启，SDK在前端将不对key进行任何处理
		    domain: $qiniu,     // bucket域名，下载资源时用到，必需
		    container: 'uploadFileDiv',             // 上传区域DOM ID，默认是browser_button的父元素
		    max_file_size: '100mb',             // 最大文件体积限制
		    flash_swf_url: 'path/of/plupload/Moxie.swf',  //引入flash，相对路径
		    max_retries: 3,                     // 上传失败最大重试次数
		    dragdrop: true,                     // 开启可拖曳上传
		    drop_element: 'uploadFileDiv',          // 拖曳上传区域元素的ID，拖曳文件或文件夹后可触发上传
		    chunk_size: '4mb',                  // 分块上传时，每块的体积
		    auto_start: false,                   // 选择文件后自动上传，若关闭需要自己绑定事件触发上传
		    multi_selection: false,
		    filters : {
		        max_file_size : '100mb',
		        prevent_duplicates: true,
		        // Specify what files to browse for
		        mime_types: [
		            {title : "apk files", extensions : "apk"} // 限定zip后缀上传
		        ]
		    },
		    //x_vars : {
		    //    查看自定义变量
		    //    'time' : function(up,file) {
		    //        var time = (new Date()).getTime();
		              // do something with 'time'
		    //        return time;
		    //    },
		    //    'size' : function(up,file) {
		    //        var size = file.size;
		              // do something with 'size'
		    //        return size;
		    //    }
		    //},
		    init: {
		        'FilesAdded': function(up, files) {
	                plupload.each(files, function(file) {
	                    var progress = new FileProgress(file, 'fsUploadProgress');
	                    progress.setStatus("等待...");
	                });
		        },
		        'BeforeUpload': function(up, file) {
		               // 每个文件上传前，处理相关的事情
		        },
		        'UploadProgress': function(up, file) {
		               var progress = new FileProgress(file, 'fsUploadProgress');        
		               var chunk_size = plupload.parseSize(this.getOption('chunk_size'));         
		               progress.setProgress(file.percent + "%", file.speed, chunk_size); 
		        },
		        'FileUploaded': function(up, file, info) {
		               // 每个文件上传成功后，处理相关的事情
		               // 其中info是文件上传成功后，服务端返回的json，形式如：
		               // {
		               //    "hash": "Fh8xVqod2MQ1mocfI4S4KpRL6D98",
		               //    "key": "gogopher.jpg"
		               //  }
			             
			             $("#appManagesaveForm [name='appSizeStr']").val(file.size/1024);
		               // 查看简单反馈
		               var domain = up.getOption('domain');
		               var res = jQuery.parseJSON(info);
		               $("#appManagesaveForm [name='serverAddr']").val(domain);
// 		               var sourceLink = domain + res.key; //获取上传成功后的文件的Url
		               $("#appManagesaveForm [name='relativePath']").val(res.key);
		               calculate(uploader.files[0].getNative(), function(md5){
		                 $("#appManagesaveForm [name='md5Value']").val(md5);
		               })
		     		  if(res.key){
	                    var param = $('#appManagesaveForm').serialize();
		     			 $.post($path+"/mc/appManage/saveOrUpdate.do",param,function($data){
							 if(!$data){
								 window.hideModal("unnormalModal");
								 refresh();
 							 }else{
								 hiAlert("提示",$data);
							 }
						 });
		     		     
		     		     
		     		  }
		        },
		        'Error': function(up, err, errTip) {
		               //上传出错时，处理相关的事情
		        },
		        'UploadComplete': function() {
		               //队列文件处理完毕后，处理相关的事情
		        },
		        'Key': function(up, file) {
		            // 若想在前端对每个文件的key进行个性化处理，可以配置该函数
		            // 该配置必须要在unique_names: false，save_key: false时才生效
                  	var key = "web/appManage/"+new Date().getTime()+".apk";
		            // do something with key here
		            return key
		        }
		    }
		});
	  //上传图标
	  var uploader1 = Qiniu.uploader({
		    runtimes: 'html5,flash,html4',      // 上传模式，依次退化
		    browse_button: 'appIcon',         // 上传选择的点选按钮，必需
		    // 在初始化时，uptoken，uptoken_url，uptoken_func三个参数中必须有一个被设置
		    // 切如果提供了多个，其优先级为uptoken > uptoken_url > uptoken_func
		    // 其中uptoken是直接提供上传凭证，uptoken_url是提供了获取上传凭证的地址，如果需要定制获取uptoken的过程则可以设置uptoken_func
		    // uptoken : '<Your upload token>', // uptoken是上传凭证，由其他程序生成
		    uptoken_url: $path+'/mc/qiniu/token',         // Ajax请求uptoken的Url，强烈建议设置（服务端提供）
//		    uptoken_func: function(file){    // 在需要获取uptoken时，该方法会被调用
//		       // do something
//		       return uptoken;
//		    },
		    get_new_uptoken: false,             // 设置上传文件的时候是否每次都重新获取新的uptoken
		    // downtoken_url: '/downtoken',
		    // Ajax请求downToken的Url，私有空间时使用，JS-SDK将向该地址POST文件的key和domain，服务端返回的JSON必须包含url字段，url值为该文件的下载地址
//		    unique_names: true,              // 默认false，key为文件名。若开启该选项，JS-SDK会为每个文件自动生成key（文件名）
//		    save_key: true,                  // 默认false。若在服务端生成uptoken的上传策略中指定了sava_key，则开启，SDK在前端将不对key进行任何处理
		    domain: $qiniu,     // bucket域名，下载资源时用到，必需
		    container: 'appIconDiv',             // 上传区域DOM ID，默认是browser_button的父元素
		    max_file_size: '2mb',             // 最大文件体积限制
		    flash_swf_url: 'path/of/plupload/Moxie.swf',  //引入flash，相对路径
		    max_retries: 3,                     // 上传失败最大重试次数
		    dragdrop: true,                     // 开启可拖曳上传
		    drop_element: 'appIconDiv',          // 拖曳上传区域元素的ID，拖曳文件或文件夹后可触发上传
		    chunk_size: '4mb',                  // 分块上传时，每块的体积
		    auto_start: false,                   // 选择文件后自动上传，若关闭需要自己绑定事件触发上传
		    multi_selection: false,
		    filters : {
		        max_file_size : '4mb',
		        prevent_duplicates: true,
		        // Specify what files to browse for
		        mime_types: [
		            {title : "Image files", extensions : "jpg,gif,png"} // 限定jpg,gif,png后
		        ]
		    },
		    //x_vars : {
		    //    查看自定义变量
		    //    'time' : function(up,file) {
		    //        var time = (new Date()).getTime();
		              // do something with 'time'
		    //        return time;
		    //    },
		    //    'size' : function(up,file) {
		    //        var size = file.size;
		              // do something with 'size'
		    //        return size;
		    //    }
		    //},
		    init: {
		        'FilesAdded': function(up, files) {
	                plupload.each(files, function(file) {
	                    var progress = new FileProgress(file, 'fsUploadProgress');
	                    progress.setStatus("等待...");
	                });
		        },
		        'BeforeUpload': function(up, file) {
		               // 每个文件上传前，处理相关的事情
		        },
		        'UploadProgress': function(up, file) {
		               var progress = new FileProgress(file, 'fsUploadProgress');        
		               var chunk_size = plupload.parseSize(this.getOption('chunk_size'));         
		               progress.setProgress(file.percent + "%", file.speed, chunk_size); 
		        },
		        'FileUploaded': function(up, file, info) {
		               // 查看简单反馈
		               var domain = up.getOption('domain');
		               var res = jQuery.parseJSON(info);
//		               var sourceLink = domain + res.key; //获取上传成功后的文件的Url
		               $("#appManagesaveForm [name='iconUrl']").val(domain+res.key);
		     		  if(!uploader.files[0]&&res.key){
	                    var param = $('#appManagesaveForm').serialize();
		     			 $.post($path+"/mc/appManage/saveOrUpdate.do",param,function($data){
							 if(!$data){
								 window.hideModal("unnormalModal");
								 refresh();
							 }else{
								 hiAlert("提示",$data);
							 }
						 });
		     		     
		     		     
		     		  }
		        },
		        'Error': function(up, err, errTip) {
		               //上传出错时，处理相关的事情
		        },
		        'UploadComplete': function() {
		               //队列文件处理完毕后，处理相关的事情
		        },
		        'Key': function(up, file) {
		            // 若想在前端对每个文件的key进行个性化处理，可以配置该函数
		            // 该配置必须要在unique_names: false，save_key: false时才生效
                	var key = "web/appManage/icon/"+new Date().getTime()+".apk";
		            // do something with key here
		            return key
		        }
		    }
		});
	  

		// domain为七牛空间对应的域名，选择某个空间后，可通过 空间设置->基本设置->域名设置 查看获取

		// uploader为一个plupload对象，继承了所有plupload的方法
	  
	  
	  
// 	  $(document).on("click","#fsUploadProgress .progressCancel",function(){
// 		  $(this).parent().parent().parent().remove();
// 	  })
	  
  
	  
	  
	  
	  
	  
	  $("#appManagesaveForm .btn-primary").on('click',function(){
		  var appManageSeolect = $("#appManagesaveForm .appManageSeolect").val();
		  var threeAppType = $("#appManagesaveForm .threeAppType").val();
		  var id = $("#appManagesaveForm [name='id']").val();
		  if(appManageSeolect!='IOS'&&threeAppType!='IOS'){
			  //添加判断app不能为空
			  if(!id){
			   var count=uploader.files.length;
			    if(count>1){
			        hiAlert("提示","最多只能上传一个app");
			        return false;
			    }else if(count==0){
			    	hiAlert("提示","没有要上传的app文件");
			    	return false;
			    }
			  }
			  
			  
				 var appName = $("#appManagesaveForm [name='appName']").val();
				 if(!appName){
					 hiAlert("提示","app名称不能为空");
					 return false;
				 }
				 var versionName = $("#appManagesaveForm [name='versionName']").val();
				 if(!versionName){
					 hiAlert("提示","版本名称不能为空");
					 return false;
				 }
				 var versionCode = $("#appManagesaveForm [name='versionCode']").val();
				 if(!versionCode){
					 hiAlert("提示","版本号不能为空");
					 return false;
				 }
				 var packageName = $("#appManagesaveForm [name='packageName']").val();
				 if(!packageName){
					 hiAlert("提示","包名不能为空");
					 return false;
				 }
		  }else{
			  var serverAddr = $("#appManagesaveForm [name='serverAddr']").val();
				 if(!serverAddr){
					 hiAlert("提示","IOS地址不能为空");
					 return false;
				 }
				 
				 var appName = $("#appManagesaveForm [name='appName']").val();
				 if(!appName){
					 hiAlert("提示","app名称不能为空");
					 return false;
				 } 
		  }
		 var versionDes = $("#appManagesaveForm [name='versionDes']").val();
		 if(!versionDes){
			 hiAlert("提示","版本说明不能为空");
			 return false;
		 }
		
		//添加  文件必须  id为空
		 if(uploader.files[0]&&!id){
			 calculate(uploader.files[0].getNative(), function(md5){
				 $.post($path+"/mc/appManage/checkVersion.do","md5Value="+md5,function($data){
					 if($data){
						 hiAlert("提示",$data);
						 return false;
					 }else{
	        	         $("#appManagesaveForm [name='md5Value']").val(md5);
	        	         var count=uploader.files.length;
	        			    if(count>1){
	        			        hiAlert("提示","最多只能上传一个app");
	        			        return false;
	        			    }else if(count==0){
	        			    	hiAlert("提示","没有要上传的app文件");
	        			    	return false;
	        			    }
	        			    if($("#appManagesaveForm [name='appType']").val()=='6'){
	        					 var id = $("#appManagesaveForm [name='id']").val();
	        				    	var count=uploader1.files.length;
	        		 			    if(count>1){
	        		 			        hiAlert("提示","最多只能上传一个图标");
	        		 			       return false;
	        		 			    }else if(count==0&&!id){
	        		 			    	hiAlert("提示","没有要上传的图标");
	        		 			    	return false;
	        		 			    }else{
	        		 			     uploader1.start();
	        		 			    }
	        				    }
	        			    uploader.start();
					 }
				 });
	         });
		 //更新	  
		 }else if(!uploader1.files[0]&&id){
			 var param = $('#appManagesaveForm').serialize();
 			 $.post($path+"/mc/appManage/saveOrUpdate.do",param,function($data){
				 if(!$data){
					 window.hideModal("unnormalModal");
					 refresh();
					 return false;
				 }else{
					 hiAlert("提示",$data);
					 return false;
				 }
			 });
 			uploader.start();
 			if($("#appManagesaveForm [name='appType']").val()=='6'){
				 var id = $("#appManagesaveForm [name='id']").val();
			    	var count=uploader1.files.length;
	 			    if(count>1){
	 			        hiAlert("提示","最多只能上传一个图标");
	 			       return false;
	 			    }else if(count==0&&!id){
	 			    	hiAlert("提示","没有要上传的图标");
	 			    	return false;
	 			    }else{
	 			     uploader1.start();
	 			    }
			    }
		 }else if($("#appManagesaveForm [name='appType']").val()=='6'){
				 var sel = $("#appManagesaveForm .appManageSeolect");
				 if(sel.length>0){
					 var className = sel[0].options[sel[0].selectedIndex].className;
					 if(className=='andriod'){
						 var count=uploader.files.length;
	     			    if(count>1){
	     			        hiAlert("提示","最多只能上传一个app");
	     			        return false;
	     			    }else if(count==0){
	     			    	hiAlert("提示","没有要上传的app文件");
	     			    	return false;
	     			    }
					 }
				 }
				 var id = $("#appManagesaveForm [name='id']").val();
			    	var count=uploader1.files.length;
	 			    if(count>1){
	 			        hiAlert("提示","最多只能上传一个图标");
	 			       return false;
	 			    }else if(count==0&&!id){
	 			    	hiAlert("提示","没有要上传的图标");
	 			    	return false;
	 			    }else{
	 			     uploader1.start();
	 			    }
		 }
		 
		  //显示进度条
// 		  var timer = setInterval(function(){
// 			  $.ajax({
//                   type: 'GET',  
//                   url: $path+'/mc/appManage/progress.do?aa='+ Math.random(),  
//                   data: {},  
//                   dataType: 'json',  
//                   success : function(data){
// 	                  $("#appManageProgress .progress-bar").attr('data-transitiongoal',data.rate).progressbar({ display_text: 'fill' });
// 				      if (data.rate>=100) {
// 				          clearInterval(timer);
// 				      }
//                }}); 
// 		  }, 100);
		  
	  })
	  
	  
	  
		//
		$("#appManagesaveForm .appManageSeolect").on("change",function(){
			var className = $(this)[0].options[$(this)[0].selectedIndex].className;
			if(className=="andriod"){
				$("#appManagesaveForm .changeInputDiv input").remove();
				$("#appManagesaveForm .changeInputDiv").append(function(){
					return '<input type="file" id="uploadFile" style="width: 300px" class="form-control"/>';
				});
				//重新加载事件
				uploader = Qiniu.uploader({
				    runtimes: 'html5,flash,html4',      // 上传模式，依次退化
				    browse_button: 'uploadFile',         // 上传选择的点选按钮，必需
				    // 在初始化时，uptoken，uptoken_url，uptoken_func三个参数中必须有一个被设置
				    // 切如果提供了多个，其优先级为uptoken > uptoken_url > uptoken_func
				    // 其中uptoken是直接提供上传凭证，uptoken_url是提供了获取上传凭证的地址，如果需要定制获取uptoken的过程则可以设置uptoken_func
				    // uptoken : '<Your upload token>', // uptoken是上传凭证，由其他程序生成
				    uptoken_url: $path+'/mc/qiniu/token',         // Ajax请求uptoken的Url，强烈建议设置（服务端提供）
//		 		    uptoken_func: function(file){    // 在需要获取uptoken时，该方法会被调用
//		 		       // do something
//		 		       return uptoken;
//		 		    },
				    get_new_uptoken: false,             // 设置上传文件的时候是否每次都重新获取新的uptoken
				    // downtoken_url: '/downtoken',
				    // Ajax请求downToken的Url，私有空间时使用，JS-SDK将向该地址POST文件的key和domain，服务端返回的JSON必须包含url字段，url值为该文件的下载地址
//		 		    unique_names: true,              // 默认false，key为文件名。若开启该选项，JS-SDK会为每个文件自动生成key（文件名）
//		 		    save_key: true,                  // 默认false。若在服务端生成uptoken的上传策略中指定了sava_key，则开启，SDK在前端将不对key进行任何处理
				    domain: $qiniu,     // bucket域名，下载资源时用到，必需
				    container: 'uploadFileDiv',             // 上传区域DOM ID，默认是browser_button的父元素
				    max_file_size: '100mb',             // 最大文件体积限制
				    flash_swf_url: 'path/of/plupload/Moxie.swf',  //引入flash，相对路径
				    max_retries: 3,                     // 上传失败最大重试次数
				    dragdrop: true,                     // 开启可拖曳上传
				    drop_element: 'uploadFileDiv',          // 拖曳上传区域元素的ID，拖曳文件或文件夹后可触发上传
				    chunk_size: '4mb',                  // 分块上传时，每块的体积
				    auto_start: false,                   // 选择文件后自动上传，若关闭需要自己绑定事件触发上传
				    multi_selection: false,
				    filters : {
				        max_file_size : '100mb',
				        prevent_duplicates: true,
				        // Specify what files to browse for
				        mime_types: [
				            {title : "apk files", extensions : "apk"} // 限定zip后缀上传
				        ]
				    },
				    //x_vars : {
				    //    查看自定义变量
				    //    'time' : function(up,file) {
				    //        var time = (new Date()).getTime();
				              // do something with 'time'
				    //        return time;
				    //    },
				    //    'size' : function(up,file) {
				    //        var size = file.size;
				              // do something with 'size'
				    //        return size;
				    //    }
				    //},
				    init: {
				        'FilesAdded': function(up, files) {
			                plupload.each(files, function(file) {
			                    var progress = new FileProgress(file, 'fsUploadProgress');
			                    progress.setStatus("等待...");
			                });
				        },
				        'BeforeUpload': function(up, file) {
				               // 每个文件上传前，处理相关的事情
				        },
				        'UploadProgress': function(up, file) {
				               var progress = new FileProgress(file, 'fsUploadProgress');        
				               var chunk_size = plupload.parseSize(this.getOption('chunk_size'));         
				               progress.setProgress(file.percent + "%", file.speed, chunk_size); 
				        },
				        'FileUploaded': function(up, file, info) {
				               // 每个文件上传成功后，处理相关的事情
				               // 其中info是文件上传成功后，服务端返回的json，形式如：
				               // {
				               //    "hash": "Fh8xVqod2MQ1mocfI4S4KpRL6D98",
				               //    "key": "gogopher.jpg"
				               //  }
					             
					             $("#appManagesaveForm [name='appSizeStr']").val(file.size/1024);
				               // 查看简单反馈
				               var domain = up.getOption('domain');
				               var res = jQuery.parseJSON(info);
				               $("#appManagesaveForm [name='serverAddr']").val(domain);
//		 		               var sourceLink = domain + res.key; //获取上传成功后的文件的Url
				               $("#appManagesaveForm [name='relativePath']").val(res.key);
				               calculate(uploader.files[0].getNative(), function(md5){
				                 $("#appManagesaveForm [name='md5Value']").val(md5);
				               })
				     		  if(res.key){
			                    var param = $('#appManagesaveForm').serialize();
				     			 $.post($path+"/mc/appManage/saveOrUpdate.do",param,function($data){
									 if(!$data){
										 window.hideModal("unnormalModal");
										 refresh();
		 							 }else{
										 hiAlert("提示",$data);
									 }
								 });
				     		     
				     		     
				     		  }
				        },
				        'Error': function(up, err, errTip) {
				               //上传出错时，处理相关的事情
				        },
				        'UploadComplete': function() {
				               //队列文件处理完毕后，处理相关的事情
				        },
				        'Key': function(up, file) {
				            // 若想在前端对每个文件的key进行个性化处理，可以配置该函数
				            // 该配置必须要在unique_names: false，save_key: false时才生效
		                  	var key = "web/appManage/"+new Date().getTime()+".apk";
				            // do something with key here
				            return key
				        }
				    }
				});
				
				var tr = 
					'<tr class="chooseShowTr">'+
	                '<td><div class="firstFont"><span class="starColor">*</span>APP名称：</div></td>'+
	                '<td><div>'+
	                   '<input maxlength="50"  name="appName" value="${appManage.appName}" class="form-control"/>'+
	                '</div></td>'+
	                 '<td><div class="leftFont"><span class="starColor">*</span>版本名称：</div></td>'+
	                '<td><div>'+
	                   '<input maxlength="50"  name="versionName" value="${appManage.versionName}" class="form-control"/>'+
	                '</div></td>'+
	               '</tr>'+
	               '<tr class="chooseShowTr">'+
	                '<td><div class="firstFont"><span class="starColor">*</span>版本号：</div></td>'+
	                '<td><div>'+
	                   '<input maxlength="50"  name="versionCode" value="${appManage.versionCode}" class="form-control"/>'+
	                '</div></td>'+
	                 '<td><div class="leftFont"><span class="starColor">*</span>包名：</div></td>'+
	                '<td><div>'+
	                   '<input maxlength="50"  name="packageName" value="${appManage.packageName}" class="form-control"/>'+
	                '</div></td>'
	               '</tr>';
	            $("#appManagesaveForm .chooseShowIosTr").remove();
				$("#appManagesaveForm .appManagesaveTable tr:eq(0)").after(tr);
				$("#appManagesaveForm").append('<input type="hidden" name="serverAddr" value="${appManage.serverAddr}"/>');
			}else{
				//删除七牛插件js
				$("#uploadFileDiv").children().remove();
				$("#appManagesaveForm [name='serverAddr']").remove();
				$("#appManagesaveForm .changeInputDiv input").remove();
				$("#appManagesaveForm .changeInputDiv").append('<input type="text" name="serverAddr" style="width: 300px" class="form-control"/>');
				var iosTr='<tr class="chooseShowIosTr">'+
	                '<td><div class="firstFont"><span class="starColor">*</span>APP名称：</div></td>'+
	                '<td><div>'+
	                   '<input maxlength="50"  name="appName" value="${appManage.appName}" class="form-control"/>'+
	                '</div></td>'+
	               '</tr>'
				$("#appManagesaveForm .chooseShowTr").remove();
				$("#appManagesaveForm .appManagesaveTable tr:eq(0)").after(iosTr);
			}
		})
  })
</script>
</html>