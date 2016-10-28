package com.youlb.controller.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youlb.utils.common.JsonUtils;
import com.youlb.utils.common.QiniuUtils;

@Controller
@Scope("prototype")
@RequestMapping("mc/qiniu")
public class QiniuCtrl {
  
	@RequestMapping("/token")
	@ResponseBody
	public String token() {
		String token = QiniuUtils.getUpToken();
		Map<String, String> map = new HashMap<String, String>();
		map.put("uptoken", token);
		String json = JsonUtils.toJson(map);
		System.out.println(json);
		return  json;
	}
}
