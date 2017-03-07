	var zTreeObj,zTreeObjTwo;
	//配置参数
	function setting(treeId,autoParam,otherParam,asyncUrl,checkEnable,chkboxType,zTreeOnClick,zTreeOnAsyncSuccess,zTreeOnCheck){
		treeId : treeId,
		treeId = {
				async : {
					autoParam : autoParam,
					dataFilter : filter,
					dataType : "text",
					enable : true,
					otherParam : otherParam,
					type : "post",
					url : asyncUrl
					
				},
				check : {
					autoCheckTrigger : false,
					chkboxType : chkboxType,
					chkStyle : "checkbox",
					enable : checkEnable,
					nocheckInherit : false,
					radioType : "level"
						
				},
				view: {
					fontCss : {color:"#2a6496"},
					showIcon: false
				},
				callback: {
					onClick: zTreeOnClick,
					onCheck: zTreeOnCheck,
				    onAsyncSuccess: zTreeOnAsyncSuccess
				},
//				data: {
//					simpleData: {
//						enable: true,
//						idKey: "id",
//						pIdKey: "pId",
//						rootPId: 1,
//					}
//				}
				
		}
		return treeId;
	}
	//过滤参数
	 function filter(treeId, parentNode, childNodes) {
        if (!childNodes) return null;
        for (var i=0, l=childNodes.length; i<l; i++) {
            childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
        }
        return childNodes;
    }
	 //数据回显函数
	  var dataEcho = function(id,treecheckbox){
		  var zTreeOnAsyncSuccess;
		  if(id&&treecheckbox){
			  zTreeOnAsyncSuccess = function(event, treeId, treeNode, msg) {
				//子节点回显
				 if(treeNode){
					 $.each(treeNode.children,function(i,obj){
						 if(treecheckbox.indexOf(obj.id)>0){
							 zTreeObj.checkNode(treeNode.children[i], true, false);
							 zTreeObj.reAsyncChildNodes(treeNode.children[i], "refresh");
						 }
					 })
					//第一级节点回显
				 }else{
				     var nodes = zTreeObj.getNodes();
				     $.each(nodes,function(i,obj){
						 if(treecheckbox.indexOf(obj.id)>0){
							 zTreeObj.checkNode(nodes[i], true, false);
							 zTreeObj.reAsyncChildNodes(nodes[i], "refresh");
						 }
					 })
				 }
		     };
		  }
		  
		  return zTreeOnAsyncSuccess;
	  }
	 
	 function zTree(treeId,autoParam,otherParam,asyncUrl,checkEnable,chkboxType,zTreeOnClick,zTreeOnAsyncSuccess,zTreeOnCheck,zTreeNodes){
//		 $.fn.zTree.init($("#"+treeId), setting(treeId,autoParam,otherParam,asyncUrl,checkEnable,chkboxType,zTreeOnClick,zTreeOnAsyncSuccess,zTreeOnCheck), zTreeNodes);
		 return $.fn.zTree.init($("#"+treeId), setting(treeId,autoParam,otherParam,asyncUrl,checkEnable,chkboxType,zTreeOnClick,zTreeOnAsyncSuccess,zTreeOnCheck), zTreeNodes);

	 }
	
	