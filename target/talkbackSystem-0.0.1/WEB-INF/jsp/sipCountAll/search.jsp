<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://www.youlb.com/tags/role" prefix="r"%>
    
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
   html,body{ height:100%; margin:0; padding:0} 
 .mask{height:100%; width:100%; position:fixed; _position:relative; top:0; z-index:1000; } 
 .opacity{ opacity:0.3; filter: alpha(opacity=30); background-color:#000; } 
</style>
</head>
<body>
	 <!-- 查询form div  --> 
    <div class="searchInfoDiv">
          <form id="sipCountAllSearchForm" action="" method="post">
           <input type="hidden" class="hiddenId" name="id" value="${id}"/>
           <table>
              <tr>
                <td><div class="firstFont">sip账号：</div></td>
                <td><div><input name="sipUser" class="form-control"/></div></td>
                <td><div class="leftFont">业务账号：</div></td>
                <td><div><input name="username" class="form-control"/></div></td>
                <td><div class="leftFont">地址：</div></td>
                <td>
		          <div>
		              <input type="hidden" class="form-control domainId" name="domainId" style="width: 260px">
				      <input type="text" class="form-control address" style="width: 260px" readonly="readonly">
				  </div>
				  <div style="position: fixed;z-index:1000;width: 260px;height: 400px;overflow: auto;display: none;background: white;">
				      <ul id="sipCountAllShowTree" class="ztree" style="width:260px; overflow:auto;"></ul>
				      
				  </div>
                </td>
                <td><div class="leftFont"><button class="btn btn-info btn-sm reset">重置</button>
                <button class="btn btn-info btn-sm search" rel="${path}/mc/sipCount/showAllList.do">查询</button></div></td>
              </tr>
           </table>
        </form>
       </div>
       
       
       
       <script type="text/javascript">
       $(function(){
    	   $("#sipCountAllSearchForm .address").on("mousedown",function(){
               var addressDiv = $(this).parent().next("div");
               addressDiv.css("display","inline");
               addressDiv.on("mouseleave",function(){
            	   addressDiv.css("display","none");
               })
    	   })
    	   
    	      zTreeObj = zTree("sipCountAllShowTree", ["id","name","level"],["nocheckLevel","01234"],$path+"/mc/domain/getNodes.do",true,{"Y": "", "N": ""},zTreeOnClick,null,null, null)
       })
       function zTreeOnClick(event, treeId, treeNode) {
// 		   	   if(treeNode.checked){
			   		$("#sipCountAllSearchForm .domainId").val(treeNode.id);
	    			$.post($path+"/mc/sipCount/getAddressByDomainId.do","domainId="+treeNode.id,function($address){
	    				 $("#sipCountAllSearchForm .address").val($address);
	    			}) 
// 		   	   }
           };
       </script>
</body>
