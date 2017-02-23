<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<script type="text/javascript">
	var zTreeObj,
	setting = {
		view: {
			selectedMulti: false
		}
	},
	zTreeNodes = [
		{"name":"网站导航", open:true, children: [
			{ "name":"google", "url":"http://g.cn", "target":"_blank"},
			{ "name":"baidu", "url":"http://baidu.com", "target":"_blank"},
			{ "name":"sina", "url":"http://www.sina.com.cn", "target":"_blank"}
			]
		}
	];
	
	
	$(document).ready(function(){
	zTreeObj = $.fn.zTree.init($("#tree"), setting, zTreeNodes);
	
	});
	
	$(function(){
		var t = $("#tree");
		t = $.fn.zTree.init(t, setting, zNodes);
		var zTree = $.fn.zTree.getZTreeObj("tree");
// 		zTree.selectNode(zTree.getNodeByParam("id", 101));
	})

</script>
</head>
<body>
  <ul id="tree" class="ztree" style="width:260px; overflow:auto;"></ul>
</body>
</html>