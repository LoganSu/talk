package com.youlb.controller.doorMachine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youlb.biz.doorMachine.IDoorMachineBiz;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.entity.doorMachine.DoorMachine;
import com.youlb.utils.exception.BizException;
@RequestMapping("/mc/doorMachine")
@Controller
public class DoorMachineCtrl extends BaseCtrl {
	@Autowired
	private IDoorMachineBiz doorMachineBiz;
	public void setDoorMachineBiz(IDoorMachineBiz doorMachineBiz) {
		this.doorMachineBiz = doorMachineBiz;
	}

	/**
	 * 显示table数据
	 * @return
	 */
	@RequestMapping("/showList.do")
	@ResponseBody
	public  Map<String, Object> showList(DoorMachine doorMachine){
		List<DoorMachine> list = new ArrayList<DoorMachine>();
		try {
			list = doorMachineBiz.showList(doorMachine,getLoginUser());
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
				DoorMachine doorMachine = doorMachineBiz.get(ids[0]);
				model.addAttribute("doorMachine", doorMachine);
			} catch (BizException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
   		return "/doorMachine/addOrEdit";
   	}
    /**
     * 保存或修改
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("/saveOrUpdate.do")
    @ResponseBody
    public String save(DoorMachine doorMachine,Model model){
    	try {
    		if(StringUtils.isBlank(doorMachine.getSoftwareType())){
    			return "型号不能为空";
    		}
            if(StringUtils.isBlank(doorMachine.getHardwareModel())){
            	return "硬件型号不能为空";
    		}
            
            boolean b = doorMachineBiz.checkExist(doorMachine);
            if(b){
            	return "该组合已经存在,请不要重复添加";
            }
    		doorMachineBiz.saveOrUpdate(doorMachine,getLoginUser());
		} catch (Exception e) {
			e.printStackTrace();
			//TODO log
			return "操作失败！";
		}
    	 return  null;
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
				doorMachineBiz.delete(ids);
			} catch (Exception e) {
				return  "删除出错";
			}
		}
		return null;
	}
	
}
