<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript">
$(function(){
	
	
	$('#containercolumn').highcharts({ 
		chart: {  borderColor: '#EBBA95',
// 			      inverted: true,//X、Y轴调换
                  borderWidth: 2,
                  type: 'column' 
                  }, 
		title: { text: '柱状图' },
		subtitle: {
            text: '库存统计'
        },
        legend: {
            layout: 'vertical',
            floating: true,
            backgroundColor: '#FFFFFF',
            align: 'right',
            verticalAlign: 'top',
            y: 60,
            x: -60
        },
		xAxis: { categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
			     title:{
			    	  text:"X轴"
			     }
		       }, 
		yAxis: { type: 'logarithmic', 
			     minorTickInterval: 1 ,
			     title:{
			    	 text:"y轴"
			     }
			     },
		//位置显示	     
// 		tooltip: { headerFormat: '<b>{series.name}</b><br />',pointFormat: 'x = {point.x}, y = {point.y}' }, 
		series: [{ name: '入库',data: [1, 2, 4, 8, 16, 32, 64, 128, 256, 512] },{ name: '出库',data: [6, 2, 8, 5, 13, 10, 64, 108, 156, 52] }]
		
		});
	
	
})
</script>
</head>
<body>
   <div id="containercolumn" style="width: 600px;height: 400px"></div>
</body>
</html>    
    
