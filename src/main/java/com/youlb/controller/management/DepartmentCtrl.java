package com.youlb.controller.management;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youlb.biz.management.IDepartmentBiz;
import com.youlb.biz.management.IWorkerBiz;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.entity.management.Department;
import com.youlb.entity.management.Worker;
import com.youlb.utils.common.RegexpUtils;
import com.youlb.utils.exception.BizException;
/**
* @ClassName: DepartmentCtrl.java 
* @Description: 部门controller 
* @author: Pengjy
* @date: 2016年6月14日
*
 */
@Controller
@Scope("prototype")
@RequestMapping("/mc/department")
public class DepartmentCtrl extends BaseCtrl {
	private static Logger log = LoggerFactory.getLogger(DepartmentCtrl.class);

	@Autowired
    private IDepartmentBiz departmentBiz;
	public void setDepartmentBiz(IDepartmentBiz departmentBiz) {
		this.departmentBiz = departmentBiz;
	}
	
	@Autowired
	private IWorkerBiz workerBiz;
	public void setWorkerBiz(IWorkerBiz workerBiz) {
		this.workerBiz = workerBiz;
	}

	/**
	 * 显示table数据
	 * @return
	 */
	@RequestMapping("/showList.do")
	@ResponseBody
	public  Map<String, Object> showList(Department department){
		List<Department> list = new ArrayList<Department>();
		try {
			list = departmentBiz.showList(department,getLoginUser());
		} catch (Exception e) {
			//TODO log
		}
		return setRows(list);
	}
	
	 /**
     * 跳转到部门添加、更新页面
     * @return
     */
    @RequestMapping("/toSaveOrUpdate.do")
   	public String toSaveOrUpdate(String[] ids,Model model,String parentId){
    	Department department = new Department();
    	try {
	    	if(ids!=null&&ids.length>0){
	    		 department = departmentBiz.get(ids[0]);
	    		 model.addAttribute("department", department);
	    	}
	    	if(StringUtils.isNotBlank(parentId)){
					department = departmentBiz.get(parentId);
	    		model.addAttribute("parentDepartment", department);
	    	}
    	} catch (BizException e) {
			log.error("获取单条数据失败");
    		e.printStackTrace();
    	}
   		return "/department/addOrEdit";
   	}
    
    /**
     * 跳转到物业公司添加、更新页面
     * @return
     */
    @RequestMapping("/toCompanySaveOrUpdate.do")
   	public String toCompanySaveOrUpdate(String[] ids,Model model){
    	if(ids!=null&&ids.length>0){
    		
			try {
				Department department = departmentBiz.get(ids[0]);
				//选择区域发送处理数据回显
				List<String> parentIds = departmentBiz.getParentIds(ids[0]);
				model.addAttribute("parentIds",parentIds);
				model.addAttribute("department", department);
			} catch (BizException e) {
				log.error("获取单条数据失败");
				e.printStackTrace();
			}
    	}
   		return "/departmentCompany/addOrEdit";
   	}
    
    /**
     * 公司保存或修改
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("/companySaveOrUpdate.do")
    @ResponseBody
    public String companySaveOrUpdate(Department department,Model model){
    	try {
    		
    		if(StringUtils.isBlank(department.getDepartmentName())){
    			super.message = "公司名称不能为空！";
				 return  super.message;
    		}
    		if(StringUtils.isBlank(department.getTel())){
    			super.message = "公司电话不能为空！";
				 return  super.message;
    		}else {
				if(!RegexpUtils.checkPhone(department.getTel())&&!RegexpUtils.checkMobile(department.getTel())){
					super.message = "请填写正确的电话！";
					 return  super.message;
				}
			}
    		if(StringUtils.isBlank(department.getAddress())){
    			super.message = "公司地址不能为空！";
				 return  super.message;
    		}
    		//范围必须选择
    		if(department.getDomainIds()==null||department.getDomainIds().isEmpty()){
    			super.message = "请选择区域！";
				 return  super.message;
    		}
    		if(StringUtils.isBlank(department.getId())){
    			for(String domainId:department.getDomainIds()){
    				//检查社区是否已经绑定物业公司
    				String neiborName = departmentBiz.checkDomain(domainId);
    				if(StringUtils.isNotBlank(neiborName)){
    					super.message = neiborName+"已经绑定物业公司！";
    					return  super.message;
    				}
    			}
    		}else{
    			Department d= departmentBiz.get(department.getId());
    			List<String> oldDomain = d.getDomainIds();
    			List<String> newDomain = new ArrayList<String>();
    			for(String domain:department.getDomainIds()){
    				if(!oldDomain.contains(domain)){
    					newDomain.add(domain);
    				}
    			}
    			//说明有更新
    			if(!newDomain.isEmpty()){
    				for(String domainId:newDomain){
        				//检查社区是否已经绑定物业公司
        				String neiborName = departmentBiz.checkDomain(domainId);
        				if(StringUtils.isNotBlank(neiborName)){
        					super.message = neiborName+"已经绑定物业公司！";
        					return  super.message;
        				}
        			}
    			}
    		}
    		
    		departmentBiz.saveOrUpdate(department,getLoginUser());
//    		super.message = "保存成功！";
		} catch (Exception e) {
			super.message = "操作失败！";
			e.printStackTrace();
		}
    	 return  super.message;
    }
    /**
     * 部门保存或修改
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("/saveOrUpdate.do")
    @ResponseBody
    public String save(Department department,Model model){
    	try {
    		//判断父节点是否存在
    		if(StringUtils.isNotBlank(department.getParentId())){
    			Department parent = departmentBiz.get(department.getParentId());
    			if(parent==null){
    				super.message = "上级部门不存在！";
    				 return  super.message;
    			}
    		}else{
    			super.message = "上级部门不能为空！";
				 return  super.message;
    		}
    		if(StringUtils.isBlank(department.getDepartmentName())){
    			super.message = "部门名称不能为空！";
				 return  super.message;
    		}
    		if(StringUtils.isBlank(department.getDescription())){
    			super.message = "部门描述不能为空！";
				 return  super.message;
    		}
    		departmentBiz.saveOrUpdate(department,getLoginUser());
//    		super.message = "保存成功！";
		} catch (Exception e) {
			super.message = "操作失败！";
			e.printStackTrace();
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
	public String delete(String[] ids,String parentId){
		if(ids!=null&&ids.length>0){
			try {
//				//删部门
//				if(StringUtils.isNotBlank(parentId)){
//					departmentBiz.delete(ids);
//
//				}else{
//					删公司
					departmentBiz.delete(ids);
//				}
			} catch (Exception e) {
				super.message =  "删除出错";
			}
		}
		return super.message;
	}
	@RequestMapping("/getNodes.do")
	@ResponseBody
	public List<HashMap<String,Object>> getNodes(String id,String name,Integer level,String nocheckLevel){
		List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		try {
			Department sd = new Department();
			sd.setParentId(id);
			List<Department> dList = departmentBiz.showList(sd,getLoginUser());
			if(dList!=null&&!dList.isEmpty()){
				for(Department d:dList){
					HashMap<String,Object> hm = new HashMap<String,Object>();   
					hm.put("id",d.getId());//id属性  ，数据传递
					hm.put("name", d.getDepartmentName()); //name属性，显示节点名称 
					hm.put("level", level==null?0:level+1);//设置层级
					if(StringUtils.isNotBlank(nocheckLevel)){
						if(nocheckLevel.contains(level+"")){
						   hm.put("nocheck", true);
						}
					}
					 sd = new Department();
					sd.setParentId(d.getId());
					List<Department> child = departmentBiz.showList(sd,getLoginUser());
					if(child!=null&&!child.isEmpty()){
						hm.put("isParent", true);
					}else{
						hm.put("isParent", false);
					}
					hm.put("pId", id);  
					
					list.add(hm);  
				}  
			}
		} catch (BizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	
	@RequestMapping("/getWorkerNodes.do")
	@ResponseBody
	public List<HashMap<String,Object>> getWorkerNodes(String id,String name,Integer level,String nocheckLevel,String departmentId){
		List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		try {
			level = level==null?0:level+1;
			Department sd = new Department();
			if(StringUtils.isBlank(id)){
				sd.setParentId(departmentId);
			}else{
				sd.setParentId(id);
			}
			List<Department> dList = departmentBiz.showList(sd,getLoginUser());
			if(dList!=null&&!dList.isEmpty()){
				for(Department d:dList){
					HashMap<String,Object> hm = new HashMap<String,Object>();   
					hm.put("id",d.getId());//id属性  ，数据传递
					hm.put("name", d.getDepartmentName()); //name属性，显示节点名称 
					hm.put("level", level);//设置层级
					if(StringUtils.isNotBlank(nocheckLevel)){
						if(nocheckLevel.contains(level+"")){
						   hm.put("nocheck", true);
						}
					}
					 sd = new Department();
					sd.setParentId(d.getId());
					List<Department> child = departmentBiz.showList(sd,getLoginUser());
					List<Worker> workerList = workerBiz.getWorkerList(d.getId());
					if((child!=null&&!child.isEmpty())||(workerList!=null&&!workerList.isEmpty())){
						hm.put("isParent", true);
					}else{
						hm.put("isParent", false);
					}
					hm.put("pId", id);  
					
					list.add(hm);  
				}  
			}
			if(StringUtils.isNotBlank(id)){
				List<Worker> workerList = workerBiz.getWorkerList(id);
				if(workerList!=null&&!workerList.isEmpty()){
					for(Worker w:workerList){
						HashMap<String,Object> hm = new HashMap<String,Object>();   
						hm.put("id",w.getId());//id属性  ，数据传递
						hm.put("name", w.getWorkerName()); //name属性，显示节点名称 
						hm.put("level", level);//设置层级
						hm.put("isParent", false);
//						hm.put("nocheck", false);
						hm.put("pId", id);  
						list.add(hm);  
					}
				}
			}
		} catch (BizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
