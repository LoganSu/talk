package com.youlb.controller.guidePage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.youlb.controller.common.BaseCtrl;
@Controller
@RequestMapping("/mc/guidePage")
public class GuidePageCtrl extends BaseCtrl {
   
	 /**
     * 跳转到引导页
     * @return
     */
    @RequestMapping("/toGuidePage.do")
   	public String toSaveOrUpdate(String[] ids,Model model){
   		return "/guidePage/guidePage";
   	}
    
    /**
     * 跳转到爱社区引导页
     * @return
     */
    @RequestMapping("/asqGuidePage.do")
   	public String asqGuidePage(String[] ids,Model model){
   		return "/guidePage/asqGuidePage";
   	}
    /**
     * 跳转到华安区引导页
     * @return
     */
    @RequestMapping("/huaanGuidePage.do")
   	public String huaanGuidePage(String[] ids,Model model){
   		return "/guidePage/huaanGuidePage";
   	}
}
