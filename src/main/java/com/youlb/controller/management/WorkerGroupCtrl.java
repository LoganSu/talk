package com.youlb.controller.management;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youlb.biz.management.IWorkerGroupBiz;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.entity.management.Department;
import com.youlb.entity.management.Worker;
import com.youlb.entity.management.WorkerGroup;
@Controller
@Scope("prototype")
@RequestMapping("/mc/workerGroup")
public class WorkerGroupCtrl extends BaseCtrl {
	@Autowired
    private IWorkerGroupBiz workerGroupBiz;
	public void setWorkerGroupBiz(IWorkerGroupBiz workerGroupBiz) {
		this.workerGroupBiz = workerGroupBiz;
	}
	/**
	 * 显示table数据
	 * @return
	 */
	@RequestMapping("/showList.do")
	@ResponseBody
	public  Map<String, Object> showList(WorkerGroup workerGroup){
		List<WorkerGroup> list = new ArrayList<WorkerGroup>();
		try {
			list = workerGroupBiz.showList(workerGroup,getLoginUser());
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
    		WorkerGroup workerGroup = workerGroupBiz.get(ids[0]);
    		model.addAttribute("workerGroup",workerGroup);
    	}
    	List<Department> companyList = workerGroupBiz.getCompanyList(getLoginUser());
		model.addAttribute("companyList", companyList);
   		return "/workerGroup/addOrEdit";
   	}
    /**
     * 保存或修改
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("/saveOrUpdate.do")
    @ResponseBody
    public String save(WorkerGroup workerGroup,Model model){
    	try {
    		if(StringUtils.isBlank(workerGroup.getDepartmentId())){
    			super.message = "请选择所属公司！";
    			return  super.message;
    		}
    		if(StringUtils.isBlank(workerGroup.getCreateTimeStr())){
    			super.message = "组名称不能为空！";
    			return  super.message;
    		}
    		workerGroupBiz.saveOrUpdate(workerGroup,getLoginUser());
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
				workerGroupBiz.delete(ids);
			} catch (Exception e) {
				super.message =  "删除出错";
			}
		}
		return super.message;
	}
	
	 /**
     * 跳转添加人员页面
     * @return
     */
    @RequestMapping("/toAddWorker.do")
   	public String toAddWorker(String id,Model model){
    	if(StringUtils.isNotBlank(id)){
    		WorkerGroup workerGroup = workerGroupBiz.get(id);
    		model.addAttribute("workerGroup",workerGroup);
    	}
    	
   		return "/workerGroup/toAddWorker";
   	}
    
    /**
	 * 分组员工table数据
	 * @return
	 */
	@RequestMapping("/showgroupWorkerList.do")
	@ResponseBody
	public  Map<String, Object> showgroupWorkerList(WorkerGroup workerGroup){
		List<Worker> list = new ArrayList<Worker>();
		try {
			list = workerGroupBiz.showgroupWorkerList(workerGroup);
		} catch (Exception e) {
			//TODO log
		}
		return setRows(list);
	}
	
	
	/**
     * 添加分组员工
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("/addWorker.do")
    @ResponseBody
    public String addWorker(WorkerGroup workerGroup,Model model){
    	try {
    		if(StringUtils.isBlank(workerGroup.getId())){
    			super.message = "请选择组！";
    			return  super.message;
    		}
    		workerGroupBiz.addWorker(workerGroup,getLoginUser());
		} catch (Exception e) {
			super.message = "操作失败！";
			e.printStackTrace();
			//TODO log
		}
    	 return  super.message;
    }
}
