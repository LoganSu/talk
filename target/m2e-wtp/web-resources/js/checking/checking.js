$(function(){
	//导出
	$(document).on("click",".exportChecking",function(){
		var param = $("#checkingSearchForm").serialize();
//		window.location.href=$path+"/mc/cardRecord/singleDownfile.do?"+param;
		$("#checkingFrame").attr("src",$path+"/mc/cardRecord/singleDownfile.do?"+param);
	})
});