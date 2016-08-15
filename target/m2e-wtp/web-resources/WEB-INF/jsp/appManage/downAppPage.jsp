<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Download</title>
     <link href="${path}/css/common/bootstrap/bootstrap.css" rel="stylesheet">
          <script src="${path}/js/common/jquery-1.12.3.js"></script>
              <script src="${path}/js/common/bootstrap/bootstrap.js"></script>
          
               <script src="${path}/js/common/sco.modal.js" type="text/javascript"></script>
          
     
<style>
body{TEXT-ALIGN: center;}
.center{ MARGIN-RIGHT: auto;
MARGIN-LEFT: auto;
/* height:200px; */
width:700px;
vertical-align:middle;
line-height:200px;
margin-top: 100px
}

 html,body{ height:100%; margin:0; padding:0} 
 .mask{height:100%; width:100%; position:fixed; _position:absolute; top:0; z-index:1000; } 
 .opacity{ opacity:0.3; filter: alpha(opacity=30); background-color:#000; } 
</style>
</head>
<body>
    <div class="center" id="downAppPage"> 
     <div class=""></div> 
     <div class="">
        <div class="row"  style="text-align: center;padding-top: 100px">
           <div class="col-sm-4 col-md-4" >
 	        <img alt="" src="${path}/imgs/appLogo.png" class=""> 
           </div>
 	      <div class="col-sm-4 col-md-4" style="padding: 0px;text-align: left;">
 	        <ul class="list-unstyled" style="margin-top: -85px;margin-left: -30px">
               <li style="font-size: 28px;height: 30px">友邻邦</li>
               <li style="height: 30px">大小:${appManage.appSizeM}M</li>
               <li style="height: 30px">版本:${appManage.versionName}</li>
               <li style="height: 30px">更新时间:${appManage.publishTime}</li>
            </ul>
 	     </div>
 	     <div class="col-sm-4 col-md-4"> 
 	        <div>
 		       <a href="${path}/appManage/singleDownPhone.do?type=android"><button type="button" class="btn btn-info btn-lg Android" style="width: 150px">Android下载</button></a>
 	        </div>
 	        <div style="margin-top: 25px"> 
 		       <a href="https://itunes.apple.com/cn/app/you-lin-bang/id1127316147?mt=8"><button type="button" class="btn btn-info btn-lg IOS" style="width: 150px">IOS下载</button></a>
 	        </div>
 	     </div>
        </div>
<!--         <div style="text-align: center;">  -->
<%--            <img width="200px" height="200px" alt="" src="${path}/imgs/newDownloadApp.jpg" class=""> --%>
            
<%--            <img width="200px" height="200px" alt="" src="${path}/imgs/newDownloadApp.jpg" class="">  --%>
<!--         </div> -->
<!--         <div style="text-align: center;"> -->
<!--            <label>Android</label> -->
<!--            <label>IOS</label> -->
<!--         </div> -->
      </div>
      <div class="" style="font-size: 30px">
                       广东赛翼智能科技股份有限公司
      </div> 
    </div> 



<div class="mask opacity" style="display: none;" id="downloadmodal">
         <div style="font-size: 40px;color: #fff;margin-top: 10px;z-index: 1000;opacity:10; filter: alpha(opacity=100); background-color:#000; ">点击右上角，选择‘在浏览器中打开’下载app</div>  
</div>

</body>
<script type="text/javascript">
$(function(){
	
// 	$("#downAppPageiframe").load("${path}/appManage/singleDownPhone.do?type=android")
	
   $("#downAppPage .Android").on("click",function(){
	   $(".mask").show();
// 	   $.ajax({
// 		   type: "GET",
// 		   url: "${path}/appManage/singleDownPhone.do?type=android",
// 		   dataType: "script"
// 		 });
   })
	
})
</script>
</html>
