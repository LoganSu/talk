$(function(){
	$(document).on("change","#areasaveForm .province",function(){
		  var value = $(this).val();
		  var arr = value.split("_");
		  var id = arr[0];
		  if(id){
		    address($(this),$path+"/mc/area/getAddressByParentId.do?parentId="+id);
		  }
	});
	
	//获取社区编号
	$(document).on("change","#areasaveForm .city",function(){
		  var city = $(this).val();
		  if(city){
			  $.post($path+"/mc/area/getAreaCode.do","city="+city,function($data){
				   $("#areasaveForm .areaNum").val($data);
				  if($data){
					  $("#areasaveForm .areaNum").attr("readonly","readonly");
				  }else{
					  $("#areasaveForm .areaNum").removeAttr("readonly");
				  }
			  })
			  
		  }
 	});
	
//	//判断输入的编号是否已经存在
//	$(document).on("focusout","#areasaveForm .areaNum",function(){
//		var areaNum = $(this).val();
//		if(!areaNum){
//			hiAlert("提示","编号不能为空！");
//			return false;
//		}
//		var $this = $(this);
//		$.post($path+"/mc/area/checkAreaNum.do","areaNum="+areaNum,function($data){
//			if($data){
//				$this.val("");
//				hiAlert("提示",$data+"编号已经存在！");
//				return false;
//			}else{
//				hiAlert("提示","编号不能为空！");
//				return false;
//			}
//		});
//	})
	
})
 //公共修改方法 （跳转到保存页面）
	$(document).on("click",".areaSaveOrUpdateBtn",function(){
		var url = $(this).attr("rel");
		var title = $(this).html();
		var data="";
		//修改保存共用（修改需要传参数）
		if(title.indexOf("修改")!=-1){
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
			//设置标题
            $("#unnormalModalLabel").html(title);
			$("#unnormalModal .modal-body").html(addHtml);
			$("#unnormalModal").modal({backdrop: 'static', keyboard: false});
		});
	})
	 //保存方法（ 实际保存，跳转到后台保存到数据库）
	 $(document).on("click","#areasaveFormSubmit .sure",function(){
		 var province = $("#areasaveForm [name='province']").val();
		 if(!province){
			 hiAlert("提示","省份不能为空！");
				return false;
		 }
		 var city = $("#areasaveForm [name='city']").val();
		 if(!city){
			 hiAlert("提示","城市不能为空！");
				return false;
		 }
		 
	     var title = $("#unnormalModal").html();
		 var param = $("#unnormalModal").find("form").serialize();
		 var  url=$(".areaSaveOrUpdateBtn").attr("saveUrl");
		 var areaNum = $("#areasaveForm .areaNum").val();
		 if(areaNum){
			if(areaNum.length!=3){
				hiAlert("提示","编号为三个字符！");
				return false;
			} 
		 }else{
			 hiAlert("提示","编号不能为空！");
				return false;
		 }
		 var readonly = $("#areasaveForm .areaNum").attr("readonly");
		 //输入的时候做重复判断
		 if(!readonly){
			 $.post($path+"/mc/area/checkAreaNum.do","areaNum="+areaNum,function($data){
				 if($data){
					 hiAlert("提示",$data);
					 return false;
				 }
				 $.post(url,param,function($data){
					 if(!$data){
						 $("#unnormalModal").modal("hide");
						 refresh();
						 zTreeObj.reAsyncChildNodes(null, "refresh");
					 }else{
						 hiAlert("提示",$data);
					 }
				 });
			 });
		  //不是输入框直接提交
		 }else{
			 $.post(url,param,function($data){
				 if(!$data){
					 $("#unnormalModal").modal("hide");
					 refresh();
					 zTreeObj.reAsyncChildNodes(null, "refresh");
				 }else{
					 hiAlert("提示",$data);
				 }
			 });
		 }
		 
	 })
var address=function(_this,url){
	 //获取下一个select位置 层级需要固定
    var nextLevel = _this.parent().parent().next().next().find("select");
    nextLevel.children().remove(":gt(0)");
	  $.post(url,function($data){
		  $.each($data,function(i,obj){
			  var option = "<option value='"+obj.fshortname+"'>"+obj.fshortname+"</option>";
		      nextLevel.append(option);
		  })
	  })
}