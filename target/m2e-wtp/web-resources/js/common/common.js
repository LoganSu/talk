//公共js方法
$(function(){
 //加载右边页面公共方法
	$(document).on("click",".li_a",function(){
		$("#showRightAreaIframe").remove();
		var showRightArea = $("#showRightArea");
		 if(showRightArea.size()<1){
			  $("#personshowImg").before('<div class="col-md-10" id="showRightArea" ></div>');
		  }
		  //隐藏打开的树状结构
		  $(".treeDiv").hide();
		  //隐藏显示的图片
		  $("#personshowImg").hide();
		  $("#showRightArea").load($(this).attr("rel")+"&aa="+Math.random());
		 //显示tree
		if($(this).hasClass("tree")){
			 var treeId = $(this).attr("tree_id");
			  if(treeId){
				  $("#"+treeId).parent().show();
				  //修改列表div样式
				  $("#showRightArea").removeClass("col-md-12").addClass("col-md-10");
				  if(treeId=="houseInfoTree"){
					  //设置单击事件
						 var zTreeOnClick = function(event, treeId, treeNode){
							 if(treeNode.level==0&&treeNode.isParent){
								 $("#showRightArea").load($path+"/mc/neighborhoods/neighborhoodsListshowPage.do?module=neighborhoodsTable&modulePath=/neighborhoods&parentId="+treeNode.id+"&aa="+new Date().getTime());
							 }else if(treeNode.level==1&&treeNode.isParent){
								 $("#showRightArea").load($path+"/mc/building/buildingListshowPage.do?module=buildingTable&modulePath=/building&parentId="+treeNode.id+"&aa="+new Date().getTime());
							 }else if(treeNode.level==2&&treeNode.isParent){
								 $("#showRightArea").load($path+"/mc/unit/unitListshowPage.do?module=unitTable&modulePath=/unit&parentId="+treeNode.id+"&aa="+new Date().getTime());
							 }else if(treeNode.level==3&&treeNode.isParent){
								 $("#showRightArea").load($path+"/mc/room/roomListshowPage.do?module=roomTable&modulePath=/room&parentId="+treeNode.id+"&aa="+new Date().getTime());
							 }
						 }
						 //初始化顶级节点
//						 var zTreeNodes = {"id":"1", "name":"区域信息", isParent:true,nocheck:true};
						 zTreeObj = zTree("houseInfoTree", ["id","name","level"],["isAll",false],$path+"/mc/domain/getNodes.do",false,{"Y": "ps", "N": "ps"},zTreeOnClick, null)
				  }else if(treeId=="authorityTree"){
					    //设置单击事件
					  var zTreeOnClick =function(event, treeId, treeNode){
							$("#showRightArea").load($path+"/mc/privilege/privilegeListshowPage.do?module=privilegeTable&modulePath=/privilege&parentId="+treeNode.id);
						 }
						 //初始化顶级节点
//						 var zTreeNodes = {"id":"", "name":"权限列表", isParent:true,nocheck:true};
					  zTreeObj = zTree("authorityTree", ["id","name","level"],[],$path+"/mc/privilege/getNodes.do",false,{"Y": "ps", "N": "ps"},zTreeOnClick, null)
				  }else if(treeId=="managementDepartmentTree"){
					     //设置单击事件
					  var zTreeOnClick =  function(event, treeId, treeNode){
							$("#showRightArea").load($path+"/mc/department/departmentshowPage.do?module=departmentTable&modulePath=/department&parentId="+treeNode.id);
						 }
						 //初始化顶级节点
//						 var zTreeNodes = {"id":"", "name":"组织架构", isParent:true,nocheck:true};
					  zTreeObj = zTree("managementDepartmentTree", ["id","name","level"],[],$path+"/mc/department/getNodes.do",false,{"Y": "ps", "N": "ps"},zTreeOnClick, null)
				  }else if(treeId=="neighborhoodsTree"){
					   //设置单击事件
					  var zTreeOnClick =  function(event, treeId, treeNode){
							$("#showRightArea").load($path+"/mc/aboutNeighborhoods/aboutNeighborhoodsshowPage.do?module=aboutNeighborhoodsTable&modulePath=/aboutNeighborhoods&neighborDomainId="+treeNode.id+"&aa="+new Date().getTime());
						 }
						 //初始化顶级节点
//						 var zTreeNodes = {"id":"", "name":"社区列表", isParent:true,nocheck:true};
					  zTreeObj = zTree("neighborhoodsTree", ["id","name","level"],[],$path+"/mc/aboutNeighborhoods/getNodes.do",false,{"Y": "ps", "N": "ps"},zTreeOnClick, null)
				  }else if(treeId=="domainNameTree"){
					  //设置单击事件
					  var zTreeOnClick =  function(event, treeId, treeNode){
							$("#showRightArea").load($path+"/mc/domainName/domainNameListshowPage.do?module=domainNameTable&modulePath=/domainName&parentid="+treeNode.id);
						 }
						 //初始化顶级节点
//						 var zTreeNodes = {"id":"", "name":"社区列表", isParent:true,nocheck:true};
					  zTreeObj = zTree("domainNameTree", ["id","name","level"],[],$path+"/mc/domainName/getNodes.do",false,{"Y": "ps", "N": "ps"},zTreeOnClick, null)
				  }
				   
			  }
		//显示图片	  
		}else if($(this).hasClass("cardInfo")){
			//修改列表div样式
			$("#showRightArea").removeClass("col-md-12").addClass("col-md-10");
			$("#personshowImg").show();
		}else{
			  //修改列表div样式
			  $("#showRightArea").removeClass("col-md-10").addClass("col-md-12");
			if($(this).attr("rel").indexOf("modulePath")>0){
				$("#searchInfoDiv").show();
			}else{
				$("#searchInfoDiv").hide();
			}
		}
  })
  
  
  //头像显示div
//    $(".nav .cardInfo").on("click",function(){
//    	//修改列表div样式
//		$("#showRightArea").removeClass("col-md-12").addClass("col-md-10");
//		$("#personshowImg").show();
//    })
//   $(document).on("click","#personshowImg img",function(){
//    	alert($(this).attr("src"));
//    })
	 //公共修改方法 （跳转到保存页面）
	$(document).on("click",".saveOrUpdateBtn",function(){
		var url = $(this).attr("rel");
		var title = $(this).html();
		var data="";
		//修改保存共用（修改需要传参数）
		if(title.indexOf("修改")!=-1||title=="派单"){
			//获取选择的id
			 data= getSelectedIds();
			var selects = $("#tableShowList").bootstrapTable('getSelections');
			//ids= 长度小于4说明没有id
			 if(selects.length!=1){
				 hiAlert("提示","请选择一条操作数据！");
				 return false;
			 }
		}
		$.post(url,data,function(addHtml){
			//设置标题 modal({backdrop: 'static', keyboard: false});
            $("#myModalLabel").html(title);
			$("#myModal .modal-body").html(addHtml);
			$("#myModal").modal({backdrop: 'static', keyboard: false});
		});
	})
	 //保存方法（ 实际保存，跳转到后台保存到数据库）
	 $("#myModal .modalSave").on("click",function(){
		 var param = $("#myModal").find("form").serialize();
		 //一个树
		 var treecheckbox;
		 if(zTreeObj){
			 var nodes = zTreeObj.getCheckedNodes(true);
	    	  var arr = new Array();
	    	  $.each(nodes,function(i,obj){
	    		  arr.push(obj.id);
	    	  });
	    	  var parr = arr.join("&treecheckbox=");
	    	  treecheckbox = "&treecheckbox="+parr;
	    	  param=param+treecheckbox;
	      }
	      //两个树
	      if(zTreeObjTwo){
              var nodesTwo = zTreeObjTwo.getCheckedNodes(true);
	    	  var arr = new Array();
	    	  $.each(nodesTwo,function(i,obj){
	    		  arr.push(obj.id);
	    	  });
	    	  var parr = arr.join("&domainIds=");
	    	  treecheckbox = "&domainIds="+parr;
	    	  param=param+treecheckbox;
		     }
	         //用完之后清空数据
	         zTreeObj=null;
	         zTreeObjTwo=null;
		     var title = $("#myModalLabel").html();
			 var url;
			 if(title=="重置密码"){
				 url=$(".updatePasw").attr("saveUrl");
			 }else{
				 url=$(".saveOrUpdateBtn").attr("saveUrl");
			 }
			//非空验证
			 var requiredArr = $("#myModal").find("form").find(".required");
			 var requiredFlag = true;
			 if(requiredArr.length>0){
				 requiredFlag = requiredValidata(requiredArr);
				 if(!requiredFlag){
					 return requiredFlag;
				 }
			 }
			//数字验证
			var numberArr = $("#myModal").find("form").find(".number");
			var numberFlag = true;
			if(numberArr.length>0){
				 numberFlag = numberValidata(numberArr);
				if(!numberFlag){
					 return numberFlag;
				 }
			}
			//邮箱验证
			var emailArr = $("#myModal").find("form").find(".email");
			var emailFlag = true;
			if(emailArr.length>0){
				emailFlag = emailValidata(emailArr);
				if(!emailFlag){
					 return emailFlag;
				 }
			}
			
			//密码验证
			var paswArr = $("#myModal").find("form").find(".updatePassword");
			var paswFlag = true;
			if(paswArr.length>0){
				paswFlag = paswValidata(paswArr);
				if(!paswFlag){
					 return paswFlag;
				 }
			}
//			var numberFlag = true;
//			if(numberArr.length>0){
//			   numberFlag = numberValidata(numberArr);
//			}
//			alert(requiredFlag);
//			alert(numberFlag);
//			alert(emailFlag);
			 if(requiredFlag&&numberFlag&&emailFlag&&paswFlag){
				 $.post(url,param,function($data){
					 if(!$data){
						 refresh();
						 //如果显示树状的div不隐藏添加或修改完之后刷新一下树
						 zTreeObj.reAsyncChildNodes(null, "refresh");
					 }else{
						 hiAlert("提示",$data);
					 }
				 });
			 }else{
				 return false;
			 }
	 })
	 
	//公共查询 只要添加search类就可以
	$(document).on("click",".search",function(){
//		refresh(testTable.url);
		refresh();
           return false;
	 })
	 //公共重置添加reset类就可以
	$(document).on("click",".reset",function(){
		 var params = $(this).parentsUntil(".searchInfoDiv").find(".form-control");
		 $.each(params,function(i,obj){
			 $(obj).val("");
		 })
           return false;
	 })
	 //公共删除方法
//	 $(document).on("click",".delete",function(){
//		 var selects = $("#tableShowList").bootstrapTable('getSelections');
//			//ids= 长度小于4说明没有id
//			 if(selects.length<1){
//				 hiAlert("提示","请选择一条操作数据！");
//				 return false;
//			 }
//			 
//		if(confirm("确定要删除该数据吗？")){
//			//获取已选择记录id
//			var ids = getSelectedIds();
//			var url = $(this).attr("rel");
//			$.post(url,ids,function($data){
//				if(!$data){
//					refresh();
//				}else{
//					hiAlert("提示",$data);
//				}
//			})
//		}
//	 })
	 
	 $(document).on("click",".delete",function(){
			 var selects = $("#tableShowList").bootstrapTable('getSelections');
			 var but = $(this);
				//ids= 长度小于4说明没有id
				 if(selects.length<1){
					 hiAlert("提示","请选择一条操作数据！");
					 return false;
				 }
				 var ids = getSelectedIds();
				var url = but.attr("rel");
				
				bootstrapQ.confirm('<span style="color:black;font-size:16px">您确定要删除记录吗？</span>',function(){
					 $.post(url,ids,function($data){
			            	if(!$data){
								refresh();
								 zTreeObj.reAsyncChildNodes(null, "refresh");

							}else{
								hiAlert("提示",$data);
							}
			            });
				},function(){
//					alert('点击了取消');
				});
				
				
//				 but.scojs_confirm({
//			        content: "您确定要删除记录吗？",
//			        param:ids,
//			        action: function(param) {
//			            $.post(url,param,function($data){
//			            	if($data){
//			            		hiAlert("提示",$data);
//							}else{
//								refresh();
//								 flushTree();
//							}
//			            });
//			            
//			        }
//			      });	
		 })
})


//非空数据验证
var requiredValidata=function(array){
	var flag = false;
	$.each(array,function(i,obj){
		if(!$(obj).val()){
			hiAlert("提示",$(obj).attr("title"));
			flag = false;
			return false;
		}else{
			flag = true;
		}
		
	})
	return flag;
}
//数字类型数据验证
var numberValidata=function(array){
	var flag = false;
	$.each(array,function(i,obj){
		if($(obj).val()&&!$(obj).val().match("^[0-9]+(.[0-9])?$")||($(obj).attr("maxlength")&&$(obj).val().length>$(obj).attr("maxlength"))){
			
			hiAlert("提示",$(obj).attr("title"));
			flag = false;
			return false;
		}else{
			flag = true;
		}
		
	})
	return flag;
}
//邮箱格式验证
var emailValidata=function(array){
	var flag = false;
	$.each(array,function(i,obj){
		if($(obj).val()&&!$(obj).val().match("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+$")){
			hiAlert("提示",$(obj).attr("title"));
			flag = false;
			return false;
		}else{
			flag = true;
		}
		
	})
	return flag;
}

//重置密码验证
var paswValidata=function(array){
	var flag = false;
	if(!$(array[0]).val()){
		hiAlert("提示",$(array[0]).attr("title"));
		flag = false;
		return false;
	}
	if(!$(array[1]).val()){
		hiAlert("提示",$(array[1]).attr("title"));
		flag = false;
		return false;
	}
	if($(array[0]).val()!=$(array[1]).val()){
		hiAlert("提示","密码输入不一致！");
		flag = false;
		return false;
	}
	flag = true;
	return flag;
}


//弹出提示框
var hiAlert=function(title,massege){
	$("#alertModalLabel").html(title);
	$("#alertModal .modal-body").html(massege);
	$("#alertModal").modal({backdrop: 'static', keyboard: false});
}
//选择弹出框
var hiConfirm=function(title,massege){
	$("#confirmModalLabel").html(title);
	$("#confirmModal .modal-body").html(massege);
	$("#confirmModal").modal({backdrop: 'static', keyboard: false});
//	var confirmV = false;
//	$(document).one("click","#confirmModal .sureBut",function(){
//		confirmV=true;
//	})
//	return confirmV;
}
//刷新列表公共方法
var refresh = function(){
	var url =$(".search").attr("rel");
	$("#tableShowList").bootstrapTable('refresh', {
		url: url,
	});
	$("#myModal").modal("hide");
}

//获取已选择记录的id
var getSelectedIds = function(){
	var selects = $("#tableShowList").bootstrapTable('getSelections');
    var ids = $.map(selects, function (row) {
        return row.id;
    }).join("&ids=");
    return ids = "ids="+ids;//拼接参数
}

var hideModal = function(id){
	$("#"+id).modal("hide");
}
//显示树状结构
//var domainTree= function(id,url,open,checkbox,checkboxLink,showurl,checkboxPartShow,layer,treecheckboxFiledName,params){
//	// 普通tree
//	$('#'+id).bstree({
//			url: url,
//			height:'auto',
//			open: open,
//			param :params,
//			checkbox:checkbox,
//			checkboxLink:checkboxLink,
//			checkboxPartShow:checkboxPartShow,
//			layer:layer,
//			treecheckboxFiledName:treecheckboxFiledName,
//			showurl:showurl
//	});
//}

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

//刷新树
//var flushTree = function(){
//    //如果显示树状的div不隐藏添加或修改完之后刷新一下树
//	var a = $("#houseInfoTree").parent().is(":visible");
//	if(a){
////		var parentId=$(".searchInfoDiv [name='parentId']");
////		var node = zTreeObj.getNodeByTId(parentId.val());
////		alert(node);
//		zTreeObj.reAsyncChildNodes(node, "refresh");
//	}
//	var b = $("#authorityTree").parent().is(":visible");
//	if(b){
//		   zTreeObj.reAsyncChildNodes(null, "refresh");
//	}
//	var c = $("#managementDepartmentTree").parent().is(":visible");
//	if(c){
//		   zTreeObj.reAsyncChildNodes(null, "refresh");
//
//	}
//	var d = $("#neighborhoodsTree").parent().is(":visible");
//	 if(d){
//		   zTreeObj.reAsyncChildNodes(null, "refresh");
//
//	  }
//	 var d = $("#domainNameTree").parent().is(":visible");
//	 if(d){
//		   zTreeObj.reAsyncChildNodes(null, "refresh");
//	  }
// 
//}

/**获取查询参数字符串*/
var initSearchParams = function(formId,params){
    var formValue = $("#"+formId).serialize();
    //每页数
    var pageSize = params.limit;
    //页数
    var pageNumber=params.offset/pageSize+1;
    //排序字段
    var sort = params.sort;
    //不传undefined到后台
    if(!sort){
    	sort="";
    }
    //排序方式 desc、asc
    var order = params.order;
    if(!order){
    	order="";
    }
    var baseParam = "pager.pageSize="+pageSize+"&pager.pageNumber="+pageNumber+"&sort="+sort+"&order="+order;
    if(formValue){
    	baseParam = baseParam+"&"+formValue;
    }
    return baseParam;
}




 
