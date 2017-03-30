package com.youlb.controller.management;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.youlb.biz.management.IBillManageBiz;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.entity.management.BillManage;
import com.youlb.utils.exception.BizException;
/**
 * 
* @ClassName: BillManageCtrl.java 
* @Description: 费用管理控制类 
* @author: Pengjy
* @date: 2016年10月17日
*
 */
@Controller
@RequestMapping("/mc/billManage")
public class BillManageCtrl extends BaseCtrl {
	@Autowired
	private IBillManageBiz billManageBiz;
	public void setBillManageBiz(IBillManageBiz billManageBiz) {
		this.billManageBiz = billManageBiz;
	}

	/**
	 * 显示table数据
	 * @return
	 */
	@RequestMapping("/showList.do")
	@ResponseBody
	public  Map<String, Object> showList(BillManage billManage){
		List<BillManage> list = new ArrayList<BillManage>();
		try {
			list = billManageBiz.showList(billManage,getLoginUser());
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
    		
			try {
				BillManage billManage = billManageBiz.get(ids[0]);
				model.addAttribute("billManage", billManage);
			} catch (BizException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
   		return "/billManage/addOrEdit";
   	}
    /**
     * 保存或修改
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("/saveOrUpdate.do")
    @ResponseBody
    public String save(BillManage billManage,Model model){
    	try {
			billManageBiz.saveOrUpdate(billManage,getLoginUser());
		} catch (Exception e) {
			super.message = "操作失败！";
			e.printStackTrace();
			//TODO log
		}
    	 return  super.message;
    }
    /**
     * 删除
     * @param ids
     * @param model
     * @return
     */
	@RequestMapping("/delete.do")
	@ResponseBody
	public String delete(String[] ids,Model model){
		if(ids!=null&&ids.length>0){
			try {
				billManageBiz.delete(ids);
			} catch (Exception e) {
				super.message =  "删除出错";
			}
		}
		return super.message;
	}
}
