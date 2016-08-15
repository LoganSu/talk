package com.youlb.controller.guidePage;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.youlb.controller.common.BaseCtrl;
@Controller
@Scope("prototype")
@RequestMapping("/mc/guidePage")
public class GuidePageCtrl extends BaseCtrl {
   
	 /**
     * 跳转到添加、更新页面
     * @return
     */
    @RequestMapping("/toGuidePage.do")
   	public String toSaveOrUpdate(String[] ids,Model model){
   		return "/guidePage/guidePage";
   	}
}
