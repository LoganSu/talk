$(function() {
	//保存访问的url地址
//	var url=location.href; 
//	document.cookie="talkbackUrl="+url+";expires="+24*60*60*1000;
//	alert(document.cookie);
	$("#loginForm").validate({
	    rules: {
	      loginName: "required",
	      loginName: {
	        required: true,
	        minlength: 5
	      },
	      password: {
	        required: true,
	        minlength: 5
	      },
	      verificationCode: {
	        required: true,
	        minlength: 6,
// 	        equalTo: "#password"
	      }
	    },
	    messages: {
	      loginName: {
	        required: "请输入用户名",
	        minlength: "用户名必需由5~11个数字或字母组成"
	      },
	      password: {
	        required: "请输入密码",
	        minlength: "密码长度不能小于 5 个字符"
	      },
	      verificationCode: {
	        required: "请输入验证码"
	      },
	    }
	});
//	if(remain){
//		   _this.html(remain+"s 后可重新获取");
//		   _this.attr("disabled","disabled");
//	  }
	 //获取手机验证码
	 $("#loginForm .getVerificationCode").on("click",function(){
		   var carrierNum = $("#loginForm .carrierNum").val();
		   var loginName = $("#loginForm .loginName").val();
		   var _this=$(this);
		   if(loginName){
			   var url = $(this).attr("rel");
			   var data = "carrier.carrierNum="+carrierNum+"&loginName="+loginName;
			   $.post(url,data,function($data){
				   //成功
				   if(!$data){
					   $data="验证码发送成功！";
                       //定时器设置按钮失效和恢复
					   var i = 600;
					   var remain;//剩余时间
					  var timer = setInterval(function(){
						   remain = --i;
						   _this.html(remain+"s 后可重新获取");
						   _this.attr("disabled","disabled");
						   if(remain==0){
							   clearInterval(timer);
							   _this.html("获取验证码");
							   _this.removeAttr("disabled");
						   }
					   } , 1000);
					   
				   } 
				   $("#loginForm .msg").remove();
				   _this.parent().parent().parent().append("<label class='msg'>"+$data+"</label>");
			   });
		   }else{
			   $("#loginForm .loginName").next().remove();
			   $("#loginForm .loginName").parent().append("<label>请输入用户名</label>");
		   }
	   })
	//提交
   	$("#loginForm").on("submit",function(){
   		   var url = $(this).attr("rel");
	   	   var carrierNum = $("#loginForm .carrierNum").val();
		   var loginName = $("#loginForm .loginName").val();
		   if(loginName){
			   var password = $("#loginForm .password").val();
			   if(password){
				   var verificationCode = $("#loginForm .verificationCode").val();
				  if(verificationCode){
					  password = $.md5(password);
					  $("#loginForm .password").val($.md5(carrierNum+loginName+password+verificationCode));
				  } 
			   }
		   }
			   
     })
     
     
//     var handler = function(bnt){
//	    	alert(1111);
//	    }
//	    var timer = setInterval(handler , 1000);
//	    
//	    var clear = function(){
//	        clearInterval(timer);
//	    }
});
