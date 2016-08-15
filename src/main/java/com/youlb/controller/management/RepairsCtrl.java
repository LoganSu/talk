package com.youlb.controller.management;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youlb.biz.houseInfo.IRoomBiz;
import com.youlb.biz.management.IRepairsBiz;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.entity.management.Repairs;

/**
 * 
* @ClassName: RepairsCtrl.java 
* @Description: 工单服务control 
* @author: Pengjy
* @date: 2016年6月13日
*
 */
@Controller
@Scope("prototype")
@RequestMapping("/mc/repairs")
public class RepairsCtrl extends BaseCtrl{
	@Autowired
	private IRepairsBiz repairsBiz;
	public void setRepairsBiz(IRepairsBiz repairsBiz) {
		this.repairsBiz = repairsBiz;
	}
	@Autowired
    private IRoomBiz roomBiz;
	public void setRoomBiz(IRoomBiz roomBiz) {
		this.roomBiz = roomBiz;
	}
	/**
	 * 显示table数据
	 * @return
	 */
	@RequestMapping("/showList.do")
	@ResponseBody
	public  Map<String, Object> showList(Repairs repairs){
		List<Repairs> list = new ArrayList<Repairs>();
		try {
			list = repairsBiz.showList(repairs,getLoginUser());
		} catch (Exception e) {
			//TODO log
		}
		return setRows(list);
	}
	 /**
     * 跳转到添加、更新页面
     * @return
     */
    @RequestMapping("/toSaveOrUpdate.do")
   	public String toSaveOrUpdate(String[] ids,Model model){
    	if(ids!=null&&ids.length>0){
    		Repairs repairs = repairsBiz.get(ids[0]);
    		model.addAttribute("repairs",repairs);
    	}
   		return "/repairs/addOrEdit";
   	}
    /**
     * 保存或修改
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("/saveOrUpdate.do")
    @ResponseBody
    public String save(Repairs repairs,Model model){
    	try {
    		if(StringUtils.isBlank(repairs.getLinkman())){
    			super.message = "联系人不能为空！";
    			return  super.message;
    		}
    		if(StringUtils.isBlank(repairs.getPhone())){
    			super.message = "联系电话不能为空！";
    			return  super.message;
    		}
    		if(StringUtils.isBlank(repairs.getDomainId())){
    			super.message = "请选择地址！";
    			return  super.message;
    		}
    		if(StringUtils.isBlank(repairs.getServiceContent())){
    			super.message = "内容不能为空！";
    			return  super.message;
    		}
    		repairsBiz.saveOrUpdate(repairs,getLoginUser());
		} catch (Exception e) {
			super.message = "操作失败！";
			e.printStackTrace();
			//TODO log
		}
    	 return  super.message;
    }
//    @Override
//    public String search(String modulePath, Model model) {
//    	HttpServletRequest request = getRequest();
//    	String orderNature = request.getParameter("orderNature");
//    	//获取展现数字数据
//    	String[] countArr = repairsBiz.countArr(Integer.parseInt(orderNature));
//    	model.addAttribute("all", countArr[0]);
//    	model.addAttribute("finish", countArr[1]);
//    	model.addAttribute("unfinish", countArr[2]);
//    	model.addAttribute("finishing", countArr[3]);
//    	return super.search(modulePath, model);
//    }
    @Override
    public String showPage(Model model) {
    	HttpServletRequest request = getRequest();
    	String orderNature = request.getParameter("orderNature");
    	//获取展现数字数据
    	String[] countArr = repairsBiz.countArr(Integer.parseInt(orderNature),getLoginUser());
    	model.addAttribute("all", countArr[0]);
    	model.addAttribute("finish", countArr[1]);
    	model.addAttribute("unfinish", countArr[2]);
    	model.addAttribute("finishing", countArr[3]);
    	return super.showPage(model);
    }
	
}
