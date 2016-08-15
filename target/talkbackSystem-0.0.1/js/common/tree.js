//$(function(){
//	  //房产管理加载树状结构
//	  $(".nav .tree").on("click",function(){
//		  var treeId = $(this).attr("tree_id");
//		  if(treeId){
//			  $("#"+treeId).parent().show();
//			  //修改列表div样式
//			  $("#showRightArea").removeClass("col-md-12").addClass("col-md-10");
//			  var url,param,open;
//			  if(treeId=="houseInfoTree"){
//				  param="disRoom=true";
//				  open=true;
//				  url= $path+'/mc/houseInfoTree';//房产信息数
//			  }else if(treeId=="authorityTree"){
//				  url= $path+'/mc/privilegeTree';//操作权限加载树状结构
//				  open=false;
//			  }else if(treeId=="managementDepartmentTree"){
//				  url= $path+'/mc/departmentTree';//物业组织架构加载树状结构
//				  open=true;
//			  }else if(treeId=="neighborhoodsTree"){
//				  url= $path+'/mc/aboutNeighborhoodsTree';//社区树
//				  open=true;
//			  }
//			  tree(treeId, url,open,param);
//			  $(document).on("click","#"+treeId+" li a",function(){
//				  var data = $(this).attr("data");
//				  if(data){
//					  var arr= data.split(";")
//					  var id=arr[0].substring(3);
//					  if(arr[1].substring(4)){
//						  $("#showRightArea").load(arr[1].substring(4));
//					  }
//				  }
//				  
//			  })
//		  }
//			
//	})
//	
//
//})
//var tree = function(id,url,open,param){
//	// 普通tree
//	$('#'+id).bstree({
//			url: url,
//			param:param,
//			height:'700px',
//			open: open,
//			showurl:false
//	});
//}

//var houseInfoTree = function(){
//	// 普通tree
//	$('#houseInfoTree').bstree({
//			url: $path+'/mc/houseInfoTree',
//			param:'disRoom=true',
//			height:'auto',
//			open: true,
//			showurl:false
//	});
//}

//var authorityTree = function(){
//	// 普通tree
//	$('#authorityTree').bstree({
//			url: $path+'/mc/privilegeTree',
//			height:'auto',
//			open: false,
//			showurl:false
//	});
//}
//var managementDepartmentTree = function(){
//	// 普通tree
//	$('#managementDepartmentTree').bstree({
//		url: $path+'/mc/departmentTree',
//		height:'auto',
//		open: false,
//		showurl:false
//	});
//}


//var neighborhoodsTree = function(){
//	// 普通tree
//	$('#neighborhoodsTree').bstree({
//			url: $path+'/mc/aboutNeighborhoods',
//			height:'auto',
//			open: true,
//			showurl:false
//	});
//}