package com.youlb.controller.offer;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youlb.biz.houseInfo.IDomainBiz;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.utils.common.JsonUtils;
import com.youlb.utils.exception.BizException;


/**
 * 荣邦银联数据提供接口
 * @author Pengjingyu
 *
 */
@Controller
@RequestMapping("/rongbang")
public class RongBangUnionpayCtrl extends BaseCtrl{
	@Autowired
	private IDomainBiz domainBiz;
	
	public void setDomainBiz(IDomainBiz domainBiz) {
		this.domainBiz = domainBiz;
	}
    @RequestMapping(value="/domainTree",method=RequestMethod.GET)
    @ResponseBody
	public String domainTree(){
    	Map<String,Object> retMap = new LinkedHashMap<String, Object>();
		try {
			List<Map<String, String>> list = domainBiz.domainTree();
			retMap.put("code", "0");
			retMap.put("message", "成功");
			retMap.put("result", list);
		} catch (BizException e) {
			e.printStackTrace();
		}
		return JsonUtils.toJson(retMap);
		
	}
}
