package com.youlb.controller.countManage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youlb.biz.countManage.ISipCountBiz;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.entity.countManage.SipCount;
import com.youlb.utils.exception.BizException;
@Controller
@Scope("prototype")
@RequestMapping("/mc/sipCount")
public class SipCountCtrl extends BaseCtrl {
	@Autowired
    private ISipCountBiz sipCountBiz;
	public void setSipCountBiz(ISipCountBiz sipCountBiz) {
		this.sipCountBiz = sipCountBiz;
	}
    

	/**
	 * sip账号在线显示table数据
	 * @return
	 */
	@RequestMapping("/showList.do")
	@ResponseBody
	public  Map<String, Object> showList(SipCount sipCount){
		List<SipCount> list = new ArrayList<SipCount>();
		try {
			list = sipCountBiz.showList(sipCount,getLoginUser());
		} catch (Exception e) {
			//TODO log
		}
		return setRows(list);
	}
	
	/**
	 * sip账号关联查询显示table数据
	 * @return
	 */
	@RequestMapping("/showAllList.do")
	@ResponseBody
	public  Map<String, Object> showAllList(SipCount sipCount){
		List<SipCount> list = new ArrayList<SipCount>();
		try {
			list = sipCountBiz.showAllList(sipCount,getLoginUser());
		} catch (Exception e) {
			//TODO log
		}
		return setRows(list);
	}
	
	
	
	/**
	 * 门口机sip账号关联查询显示table数据
	 * @return
	 */
	@RequestMapping("/deviceCountSipShowList.do")
	@ResponseBody
	public  Map<String, Object> deviceCountSipShowList(SipCount sipCount){
		List<SipCount> list = new ArrayList<SipCount>();
		try {
			list = sipCountBiz.deviceCountSipShowList(sipCount,getLoginUser());
		} catch (Exception e) {
			//TODO log
		}
		return setRows(list);
	}
	
	
	@RequestMapping("/getAddressByDomainId.do")
	@ResponseBody
	public String getAddressByDomainId(String domainId){
		try {
			String address = sipCountBiz.getAddressByDomainId(domainId);
			return address;
		} catch (BizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
