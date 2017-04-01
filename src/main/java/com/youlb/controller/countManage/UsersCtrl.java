package com.youlb.controller.countManage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youlb.biz.countManage.IUsersBiz;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.entity.countManage.Users;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.exception.BizException;

/** 
 * @ClassName: UsersCtrl.java 
 * @Description: 注册用户 
 * @author: Pengjy
 * @date: 2015-11-24
 * 
 */
@Controller
@RequestMapping("/mc/users")
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
		try {
			list = usersBiz.showList(users, loginUser);
		} catch (BizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return setRows(list);
	}
	

	/**
     * 跳转到添加、更新页面
     * @return
     */
    @RequestMapping("/toSaveOrUpdate.do")
   	public String toSaveOrUpdate(String[] ids,Users users,Model model){
    	
    	if(ids!=null&&ids.length>0){
    		try {
				users = usersBiz.get(ids[0]);
			} catch (BizException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
			e.printStackTrace();
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
	public String deleteUser(String[] ids,Model model){
		if(ids!=null&&ids.length>0){
			try {
				usersBiz.delete(ids);
			} catch (Exception e) {
				e.printStackTrace();
				return  "删除出错";
			}
		}
		return null;
	}
	
	/**
     * 暂停用户使用
     * @param ids
     * @param model
     * @return
     */
	@RequestMapping("/usersStop.do")
	@ResponseBody
	public String usersStop(Integer id,String status,Model model){
		if(id!=null&&StringUtils.isNotBlank(status)){
			try {
				usersBiz.update(id,status);
			} catch (Exception e) {
				e.printStackTrace();
				return  "操作失败";
			}
		}else{
			return  "参数为空";
		}
		return null;
	}
}
