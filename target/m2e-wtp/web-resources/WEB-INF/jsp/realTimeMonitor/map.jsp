<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
<title>异步加载</title>  
<script type="text/javascript">  
function initialize() { 
  var mp = new BMap.Map('map');
  mp.centerAndZoom(new BMap.Point(121.491, 31.233), 11);  
}  
   
 $(function(){
  var script = document.createElement("script");  
//   script.src = "http://api.map.baidu.com/api?v=2.0&ak=v5MOhIsz4tPIROKshVmD4WC9UFH5UFcX&callback=initialize";//此为v2.0版本的引用方式  
    script.src = "http://api.map.baidu.com/api?v=1.4&ak=v5MOhIsz4tPIROKshVmD4WC9UFH5UFcX&callback=initialize";//此为v1.4版本的引用方式  
  document.body.appendChild(script);  
	 
 })  
</script>  
</head>  
<body>  
  <div id="map" style="width:500px;height:320px"></div>  
</body>  
</html>