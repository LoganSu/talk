package com.youlb.controller.privilege;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youlb.biz.privilege.IPrivilegeBiz;
import com.youlb.biz.privilege.IRoleBiz;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.controller.personnel.DwellerCtrl;
import com.youlb.entity.common.Domain;
import com.youlb.entity.privilege.Privilege;
import com.youlb.utils.exception.BizException;

/** 
 * @ClassName: AuthorityCtrl.java 
 * @Description: 添加操作权限控制器
 * @author: Pengjy
 * @date: 2015年7月8日
 * 
 */
@Controller
@RequestMapping("/mc/privilege")
@Scope("prototype")
public class PrivilegeCtrl extends BaseCtrl {
	private static Logger log = LoggerFactory.getLogger(PrivilegeCtrl.class);

	@Autowired
	private IPrivilegeBiz privilegeBiz;
	@Autowired
	private IRoleBiz roleBiz;
	public void setRoleBiz(IRoleBiz roleBiz) {
		this.roleBiz = roleBiz;
	}
	public void setPrivilegeBiz(IPrivilegeBiz privilegeBiz) {
		this.privilegeBiz = privilegeBiz;
	}


	/**
     * 跳转到添加、更新页面
     * @return
     */
    @RequestMapping("/toSaveOrUpdate.do")
   	public String toSaveOrUpdate(String[] ids,Privilege privilege,Model model){
    	if(ids!=null&&ids.length>0){
    		 try {
				privilege = privilegeBiz.get(ids[0]);
				model.addAttribute("privilege", privilege);
			} catch (BizException e) {
				log.error("获取单个数据失败");
				e.printStackTrace();
			}
		}
   		return "/privilege/addOrEdit";
   	}
    /**
     * 保存
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("/saveOrUpdate.do")
    @ResponseBody
    public String saveOrUpdate(Privilege privilege,Model model){
    	try {
    		
//    		ManageUser loginUser = getLoginUser();
    		privilegeBiz.saveOrUpdate(privilege);
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
	public String deleteUser(String[] ids,Model model){
		if(ids!=null&&ids.length>0){
			try {
				privilegeBiz.delete(ids);
			} catch (Exception e) {
				super.message =  "删除出错";
			}
		}
		return super.message;
	}
	
	/**
	 * 显示table数据
	 * @param privilege
	 * @return
	 */
	@RequestMapping("/showList.do")
	@ResponseBody
	public  Map<String, Object> showList(Privilege privilege){
		List<Privilege> list = new ArrayList<Privilege>();
		try {
			list = privilegeBiz.showList(privilege,getLoginUser());
		} catch (Exception e) {
			//TODO log
		}
		return setRows(list);
	}
	/**
	 * 查询子节点
	 * @param privilege
	 * @return
	 */
	 @RequestMapping("/hashChildren.do")
	 @ResponseBody
	 public List<Privilege> hashChildren(Privilege privilege){
		 List<Privilege> list = new ArrayList<Privilege>();
			try {
				list = privilegeBiz.showList(privilege,getLoginUser());
			} catch (Exception e) {
				//TODO log
			}
		 return list;
	 }
	 
	   @RequestMapping("/getNodes.do")
		@ResponseBody
		public List<HashMap<String,Object>> getNodes(String id,String name,Integer level,String nocheckLevel,String roleId){
			List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
			Privilege privilege = new Privilege();
			privilege.setParentId(id);
			try {
				List<Privilege> pList = privilegeBiz.showList(privilege,getLoginUser());
				if(pList!=null&&!pList.isEmpty()){
					for(Privilege p:pList){
						HashMap<String,Object> hm = new HashMap<String,Object>();   
						hm.put("id",p.getId());//id属性  ，数据传递
						hm.put("name", p.getName()); //name属性，显示节点名称 
						hm.put("level", level==null?0:level+1);//设置层级
						if(StringUtils.isNotBlank(nocheckLevel)){
							if(nocheckLevel.contains(level+"")){
							   hm.put("nocheck", true);
							}
						}
						privilege = new Privilege();
						privilege.setParentId(p.getId());
						List<Privilege> child = privilegeBiz.showList(privilege,getLoginUser());
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
}