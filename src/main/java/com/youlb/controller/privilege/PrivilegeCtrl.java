package com.youlb.controller.privilege;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youlb.biz.privilege.IPrivilegeBiz;
import com.youlb.biz.privilege.IRoleBiz;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.entity.privilege.Privilege;

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
    		 privilege = privilegeBiz.get(ids[0]);
		}
    	model.addAttribute("privilege", privilege);
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
}