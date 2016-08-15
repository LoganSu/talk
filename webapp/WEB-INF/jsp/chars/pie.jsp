<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript">
$(function(){
	$.post("${url}",function(charVo){
		$('#containerpie').highcharts({
			 chart: { plotBackgroundColor: null, 
				      plotBorderWidth: null, 
				      plotShadow: false }, 
			title: { text: charVo.title }, 
			tooltip: { pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>' }, 
			plotOptions: { pie: { allowPointSelect: true, 
				                  cursor: 'pointer', 
				                  dataLabels: { enabled: true, 
				                	            color: '#000000', 
				                	            connectorColor: '#000000', 
				                	            format: '<b>{point.name}</b>: {point.percentage:.1f} %' } } }, 
			series: [{ type: 'pie', name: 'Browser share', 
				       data: [ ['Firefox', 45.0], ['IE', 26.8], { name: 'Chrome', y: 12.8, sliced: true, selected: true }, ['Safari', 8.5], ['Opera', 6.2], ['Others', 0.7] ] }]
	     
		
		})
	})
})
</script>
</head>
<body>
   <div id="containerpie" style="width: 600px;height: 400px"></div>
</body>
</html>    
    
