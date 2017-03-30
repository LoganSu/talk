package com.youlb.controller.privilege;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.youlb.biz.baseInfo.ICarrierBiz;
import com.youlb.biz.privilege.IRoleBiz;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.entity.privilege.Operator;
import com.youlb.entity.privilege.Privilege;
import com.youlb.entity.privilege.Role;
import com.youlb.entity.vo.QTree;
import com.youlb.utils.exception.BizException;

/** 
 * @ClassName: RoleContrl 
 * @Description: 角色管理控制类
 * @author: Pengjy
 * @date: 2015年7月7日
 * 
 */
@Controller
@RequestMapping("/mc/role")
public class RoleCtrl extends BaseCtrl{
	private static Logger log = LoggerFactory.getLogger(RoleCtrl.class);

	@Autowired
	private IRoleBiz roleBiz;
	@Autowired
	private ICarrierBiz carrierBiz;
	public void setRoleBiz(IRoleBiz roleBiz) {
		this.roleBiz = roleBiz;
	}
	public void setCarrierBiz(ICarrierBiz carrierBiz) {
		this.carrierBiz = carrierBiz;
	}

	/**
	 * 显示table数据
	 * @return
	 */
	@RequestMapping("/showList.do")
	@ResponseBody
	public  Map<String, Object> showList(Role role){
		List<Role> list = new ArrayList<Role>();
		Operator loginUser = getLoginUser();
		try {
			list = roleBiz.showList(role, loginUser);
		} catch (BizException e) {
			log.error("获取列表数据失败");
			e.printStackTrace();
		}
		
		return setRows(list);
	}
	

	/**
     * 跳转到添加、更新页面
     * @return
     */
    @RequestMapping("/toSaveOrUpdate.do")
   	public String toSaveOrUpdate(String[] ids,Role role,Model model){
    	//先获取参数值
    	String isCarrier = role.getIsCarrier();
    	if(ids!=null&&ids.length>0){
    		try {
				role = roleBiz.get(ids[0]);
			} catch (BizException e) {
				log.error("获取单个数据失败");
				e.printStackTrace();
			}
		}
    	model.addAttribute("role", role);
    	//运营商用户管理
    	if(StringUtils.isNotBlank(isCarrier)){
    		return "/carrierRole/addOrEdit";
    	} 
    	//获取运营商信息
//    	List<Carrier> carrierList = carrierBiz.showList(new Carrier(), getLoginUser());
//    	model.addAttribute("carrierList", carrierList);
   		return "/role/addOrEdit";
   	}
    /**
     * 保存
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("/saveOrUpdate.do")
    @ResponseBody
    public String saveOrUpdate(Role role,Model model){
    	try {
    		Operator loginUser = getLoginUser();
    		role.setCarrierId(loginUser.getCarrier().getId());
    		//区分区分第三方添加权限  加前缀
//    		if(loginUser.getLoginName().contains("_")&&!role.getRoleName().contains("_")){
//    			role.setRoleName(loginUser.getLoginName().substring(0, loginUser.getLoginName().indexOf("_")+1)+role.getRoleName());
//    		}
    		//一个运营商只能创建一个空角色
    		if(role.getDomainIds()==null&&role.getTreecheckbox()==null){
    			boolean b = roleBiz.checkEmptyRole(role,loginUser);
    			if(b){
    				super.message = "一个运营商有且只能创建一个空角色";
    				return  super.message;
    			}
    		}
    		roleBiz.saveOrUpdate(role,loginUser);
		} catch (Exception e) {
			super.message = "操作失败！";
			e.printStackTrace();
		}
    	 return  super.message;
    }
    /**
     * 获取树状结构权限列表
     * @return
     */
    @RequestMapping(value = "/getNodes.do", method = RequestMethod.POST)
    @ResponseBody
   	public List<HashMap<String,Object>> privilegeList(String id,String name,Integer level,String nocheckLevel,String roleId){
		List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
    	if(StringUtils.isNotBlank(roleId)){
    		try {
    			Role role = roleBiz.get(roleId);
    			List<Privilege> privilegeList = roleBiz.getPrivilegeList(getLoginUser(),role);
			} catch (BizException e) {
				log.error("获取单个数据失败");
				e.printStackTrace();
			}
		}
   		return list;
   	}
    /**
     * 把集合转换成树状图数据
     * @param privilegeList
     * @return 
     */
    private List<QTree> objToTree(List<Privilege> privilegeList){
    	 List<QTree> listTree = new ArrayList<QTree>();
    	if(privilegeList!=null){
    		for(Privilege privilege:privilegeList){
    			QTree tree = new QTree();
    			tree.setId(privilege.getId());//id
    			tree.setText(privilege.getName());//名称
    			tree.setChecked(privilege.getChecked()==null?false:true);//是否选中
    			tree.setChildren(objToTree(privilege.getChildren()));
    			listTree.add(tree);
    		}
    	}
    	return listTree;
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
				roleBiz.delete(ids);
			} catch (Exception e) {
				super.message =  "删除出错";
			}
		}
		return super.message;
	}
	
	/**
	 * 显示运营商角色table数据
	 * @return
	 */
	@RequestMapping("/carrierShowList.do")
	@ResponseBody
	public  Map<String, Object> carrierShowList(Role role){
		List<Role> list = new ArrayList<Role>();
		try {
			list = roleBiz.carrierShowList(role,getLoginUser());
		} catch (Exception e) {
			//TODO log
		}
		return setRows(list);
	}
}
