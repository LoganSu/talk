$(function(){
	   $(document).on("click",".showChars",function(){
		   var btn =  $(this);
		   var url = btn.attr("rel");
		   btn.parents("form").one("submit",function() {
//			   alert(btn.html());
			   jQuery.ajax({
				   url: url,
//                 url:$path+"/mc/chars/line.do",
				   data: 'url='+btn.attr("rel"),
				   type: "POST",
				   success: function($html){
					  $("#showChars").html($html);
				   }
				});
				   return false;
			});
	   });
});