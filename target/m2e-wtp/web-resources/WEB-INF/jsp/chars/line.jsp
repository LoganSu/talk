<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript">
	var series = function(charVo){
		var s = charVo.dataList;
		var arr = new Array();
		$.each(s,function(index,obj){
			var json = {"name":obj.name,"type":obj.type,"data":obj.data};
			arr.push(json);
		  });
		return arr;
	};
$(function(){
// 	$.post("${url}",function(charVo){
		var charVo = jQuery.parseJSON('${charVo}');
		var data  = series(charVo);
		var div = $('#containerline');
		div.width(charVo.width);
		div.height(charVo.height);
		div.highcharts({
			chart: {  borderColor: '#eee',
	                  borderWidth: 2,
	                  type: charVo.type //line、column、bar、pie
	                  },
			title: { text: charVo.title },
			subtitle: {
	            text: charVo.subtitle
	        },
			xAxis: {
				     categories: charVo.categories,
				     title:{
				    	 text: charVo.xAxisTitle
				     }
			       },
			yAxis: {
				     type: 'logarithmic', 
				     title:{
				    	 text:charVo.yAxisTitle
				     }
				     },
			//位置显示	     
	// 		tooltip: { headerFormat: '<b>{series.name}</b><br />',pointFormat: 'x = {point.x}, y = {point.y}' }, 
			series: data
			});
	});
</script>
</head>
<body>
  <div id="containerline"></div>
</body>
</html>    
    
