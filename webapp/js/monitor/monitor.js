  $(function(){
	  //处理告警事件
	  $(document).on("click",".disposeEvent",function(){
		  var url =$path+"/mc/realTimeMonitor/toDisposeEvent.do";
		  var id =$(this).attr("rel");
		  $.post(url,"id="+id,function(addHtml){
				//设置标题
	            $("#unnormalModalLabel").html("处理备注");
				$("#unnormalModal .modal-body").html(addHtml);
				$("#unnormalModal").modal("show");
			})
	  })
	  //处理公告备注
	  $(document).on("click","#disposeEventForm .sure",function(){
		  var data =$("#disposeEventForm").serialize();
		  var url =$path+"/mc/realTimeMonitor/disposeEvent.do";
		  $.post(url,data,function($data){
			  if($data){
				 hiAlert("提示",$data); 
			  }else{
				  $("#realTimeMonitorTable").bootstrapTable('refresh', {
						url: $path+'/mc/realTimeMonitor/showList.do',
					});
					$("#unnormalModal").modal("hide");
			  }
		  })
	  }) 
	  
  })
    var map;
    //生成地图
    function initialize() {
	 	map = new BMap.Map("containers");  
		map.centerAndZoom(new BMap.Point(113.383748,23.148225), 18);  
		map.enableScrollWheelZoom();
		map.clearOverlays();//清除标注
//			  $.post($path+"/mc/realTimeMonitor/getData.do",function($data){
//				map.clearOverlays();//清除标注
//				marker_function(map,$data);
//			  })
//		  },5000);

			
		
     }
  //标记位置方法
   var marker_function=function(map,JsonStr){
	   var jsonObj = eval(JsonStr);
	   if (jsonObj != null) {
           for (var i = 0; i < jsonObj.length; i++) {  
              (function (x) {
                  var point = new BMap.Point(jsonObj[x].x, jsonObj[x].y);    // 创建点坐标  
                  map.centerAndZoom(point, 18);  
                  var marker = new BMap.Marker(point);  
                  var opts = {  
                      width: 50,     // 信息窗口宽度  
                      height: 30,     // 信息窗口高度  
                      title: "<span style='font-size:16px;color:red'>"+jsonObj[x].name+"</span>"   // 信息窗口标题  
                  }  
//                  var content = "地址："+jsonObj[x].address+"<br/>电话："+jsonObj[x].tel; 
                   var content = "地址："+jsonObj[x].address;  
                  var info_Window = new BMap.InfoWindow(content, opts);  // 创建信息窗口对象  
                  marker.addEventListener("click", function () {  
                      this.openInfoWindow(info_Window);  
                  });  
                  map.addOverlay(marker);  
              })(i);  
           }   
           map.addControl(new BMap.NavigationControl());  
	   }
   }

//定时刷新任务
 var flushPage=function(){
	   $.post($path+'/mc/realTimeMonitor/getRealTimeMonitorId.do',function($data){
// 			alert($data.length);
			if($data.length>0){
				var arr = new Array();
				arr.push("1=1")
				$.each($data,function(index,obj){
					arr.push("&ids="+obj);
				})
//				alert(arr.join("").toString());
				//获取点数据
				$.post($path+'/mc/realTimeMonitor/getData.do',arr.toString(),function($data){
					map.clearOverlays();//清除标注
					marker_function(map,$data);
					//删除作用域里面的id
					$.post($path+'/mc/realTimeMonitor/removeRealTimeMonitorId.do',arr.toString(),function($d){})
				})
		// 		   window.navigate(${path}+"/mc/realTimeMonitor/realTimeMonitor.do");
				$("#realTimeMonitorTable").bootstrapTable('refresh', {
					url: $path+'/mc/realTimeMonitor/showList.do',
				});
			}
	   })
   }