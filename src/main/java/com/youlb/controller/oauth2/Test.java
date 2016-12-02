package com.youlb.controller.oauth2;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youlb.controller.common.BaseCtrl;
@Controller
@Scope("prototype")
@RequestMapping("/abcs/oauth")
public class Test extends BaseCtrl {
    
	@RequestMapping("/hello.do")
	@ResponseBody
	public String hello(){
		return "hello auth";
	}
}
