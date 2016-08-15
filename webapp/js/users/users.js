$(function(){
	//删除
	$(document).on("click",".usersDelete",function(){
		var id = $(this).attr("rel");
		var url =$path+"/mc/users/delete.do?ids="+id;
		if(confirm("确定要删除该数据吗？")){
			//获取已选择记录id
			$.post(url,function($data){
				if(!$data){
					refresh();
				}else{
					hiAlert("提示",$data);
				}
			})
		}
	 })
})