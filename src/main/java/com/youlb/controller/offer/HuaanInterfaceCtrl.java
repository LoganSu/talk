package com.youlb.controller.offer;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youlb.biz.access.impl.PermissionBizImpl;
import com.youlb.biz.houseInfo.IDomainBiz;
import com.youlb.controller.access.HouseTree;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.utils.common.JsonUtils;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.exception.JsonException;
/**
 * 华安数据接口
 * @author Pengjingyu
 * @date 2017-04-19
 */
@Controller
@RequestMapping("/huaan")
public class HuaanInterfaceCtrl  extends BaseCtrl{
	private static Logger logger = LoggerFactory.getLogger(HuaanInterfaceCtrl.class);

	@Autowired
	private IDomainBiz domainBiz;
	
	public void setDomainBiz(IDomainBiz domainBiz) {
		this.domainBiz = domainBiz;
	}
	@RequestMapping(value="/insertHouseInfo",method=RequestMethod.POST)
    @ResponseBody
	public String domainTree(String strJson){
    	Map<String,Object> retMap = new LinkedHashMap<String, Object>();
    	if(StringUtils.isBlank(strJson)){
    		retMap.put("code", "1");
			retMap.put("message", "参数不能为空");
    	}
    	try {
    		logger.info("华安参数："+strJson);
			HouseTree houseTree = JsonUtils.fromJson(strJson, HouseTree.class);
			domainBiz.insertHouseInfo(houseTree);
			
			retMap.put("code", "0");
			retMap.put("message", "成功");
			
		} catch (JsonException e) {
			retMap.put("code", "2");
			retMap.put("message", "参数解析出错");
			e.printStackTrace();
			logger.error(e.getMessage());
			return JsonUtils.toJson(retMap);
		}
    	
    	
		return JsonUtils.toJson(retMap);
		
	}
}
