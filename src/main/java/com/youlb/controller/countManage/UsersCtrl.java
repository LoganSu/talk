package com.youlb.controller.countManage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youlb.biz.countManage.IUsersBiz;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.entity.countManage.Users;
import com.youlb.entity.privilege.Operator;

/** 
 * @ClassName: UsersCtrl.java 
 * @Description: 注册用户 
 * @author: Pengjy
 * @date: 2015-11-24
 * 
 */
@Controller
@RequestMapping("/mc/users")
@Scope("prototype")
public class UsersCtrl extends BaseCtrl {
	@Autowired
    private IUsersBiz usersBiz;
	public void setUsersBiz(IUsersBiz usersBiz) {
		this.usersBiz = usersBiz;
	}
	
	/**
	 * 显示table数据
	 * @return
	 */
	@RequestMapping("/showList.do")
	@ResponseBody
	public  Map<String, Object> showList(Users users){
		List<Users> list = new ArrayList<Users>();
		Operator loginUser = getLoginUser();
		list = usersBiz.showList(users, loginUser);
		
		return setRows(list);
	}
	

	/**
     * 跳转到添加、更新页面
     * @return
     */
    @RequestMapping("/toSaveOrUpdate.do")
   	public String toSaveOrUpdate(String[] ids,Users users,Model model){
    	
    	if(ids!=null&&ids.length>0){
    		users = usersBiz.get(ids[0]);
		}
//    	Operator loginUser = getLoginUser();
    	//获取权限列表
//    	List<Privilege> privilegeList = carrierBiz.getPrivilegeList(loginUser,role);
//    	model.addAttribute("privilegeList", privilegeList);
    	model.addAttribute("users", users);
   		return "/carrier/addOrEdit";
   	}
    
    /**
     * 保存
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("/saveOrUpdate.do")
    @ResponseBody
    public String saveOrUpdate(Users users,Model model){
    	try {
    		usersBiz.saveOrUpdate(users);
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
				usersBiz.delete(ids);
			} catch (Exception e) {
				e.printStackTrace();
				super.message =  "删除出错";
			}
		}
		return super.message;
	}
}
