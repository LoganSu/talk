$(function(){
	//删除
	$(document).on("click",".usersDelete",function(){
		var id = $(this).attr("rel");
		var url =$path+"/mc/users/delete.do";
		if(confirm("确定要删除该数据吗？")){
			//获取已选择记录id
			$.post(url,"ids="+id,function($data){
				if(!$data){
					refresh();
				}else{
					hiAlert("提示",$data);
				}
			})
		}
	 })
	 //暂停使用
	$(document).on("click",".usersStop",function(){
		var id = $(this).attr("rel");
		var url =$path+"/mc/users/usersStop.do";
		if(confirm("确定要暂停该用户使用吗？")){
			//获取已选择记录id
			$.post(url,"id="+id+"&status=0",function($data){
				if(!$data){
					refresh();
				}else{
					hiAlert("提示",$data);
				}
			})
		}
	 })
	 //暂停使用
	$(document).on("click",".usersActivate",function(){
		var id = $(this).attr("rel");
		var url =$path+"/mc/users/usersStop.do";
		if(confirm("确定要启用用户使用吗？")){
			//获取已选择记录id
			$.post(url,"id="+id+"&status=1",function($data){
				if(!$data){
					refresh();
				}else{
					hiAlert("提示",$data);
				}
			})
		}
	 })
})