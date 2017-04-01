package com.youlb.controller.countManage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youlb.biz.countManage.ISipCountBiz;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.entity.countManage.SipCount;
import com.youlb.utils.exception.BizException;
@Controller
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
	
	 /**
     * 跳转到重启页面
     * @return
     */
    @RequestMapping("/toRestDevice.do")
   	public String toRestDevice(String[] usernames,SipCount sipCount,Model model){
    	if(usernames!=null&&usernames.length==1){
    		model.addAttribute("username",usernames[0]);
    	}
//    	String opraterType = appManage.getOpraterType();
//    	try {
//	    	if(ids!=null&&ids.length>0){
//				appManage = appManageBiz.get(ids[0]);
//	    		appManage.setOpraterType(opraterType);
//	    	}
//	    	if(SysStatic.two.equals(appManage.getAppType())){
//	    		List<AppManage> appList = appManageBiz.getOldVersion();
//	    		model.addAttribute("appList",appList);
//	    	}
//	    	//门口机需要选择类型
//	    	if("1".equals(appManage.getAppType())){
//	    		//获取软件型号列表
//	    		List<String> softwareTypeList = doorMachineBiz.getSoftwareTypeList();
//	    		model.addAttribute("softwareTypeList",softwareTypeList);
//	    	}
//	    	model.addAttribute("appManage",appManage);
//    	} catch (BizException e) {
//    		// TODO Auto-generated catch block
//    		e.printStackTrace();
//    	}
   		return "/deviceCountSip/resetDevice";
   	}
    
    /**
     * 重启
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("/restDevice.do")
    @ResponseBody
    public String restDevice(String username,String reset_time,String reset_type){
    	try {
    		sipCountBiz.restDevice(username,reset_time,reset_type);
		} catch (Exception e) {
			e.printStackTrace();
			return "操作失败！";
		}
    	 return  null;
    } 

}
