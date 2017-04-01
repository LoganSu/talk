package com.youlb.controller.appManage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youlb.biz.IPManage.IIPManageBiz;
import com.youlb.biz.appManage.IAppManageBiz;
import com.youlb.biz.doorMachine.IDoorMachineBiz;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.entity.IPManage.IPManage;
import com.youlb.entity.appManage.AppManage;
import com.youlb.utils.common.SysStatic;
import com.youlb.utils.exception.BizException;

/** 
 * @ClassName: AppManage.java 
 * @Description: app管理 
 * @author: Pengjy
 * @date: 2015-11-26
 * 
 */
@Controller
@RequestMapping("/mc/appManage")
public class AppManageCtrl extends BaseCtrl {
	
	private static Logger log = LoggerFactory.getLogger(AppManageCtrl.class);
	@Autowired
    private IAppManageBiz appManageBiz;
	public void setAppManageBiz(IAppManageBiz appManageBiz) {
		this.appManageBiz = appManageBiz;
	}
	
	@Autowired
	private IDoorMachineBiz doorMachineBiz;
	public void setDoorMachineBiz(IDoorMachineBiz doorMachineBiz) {
		this.doorMachineBiz = doorMachineBiz;
	}
	@Autowired
	private IIPManageBiz iPManageBiz;
	public void setiPManageBiz(IIPManageBiz iPManageBiz) {
		this.iPManageBiz = iPManageBiz;
	}
//	@Overrideyyyyyyyyyyy
//	public String search(String modulePath, String appType, Model model) {
//		model.addAttribute("appType", appType);
//		return modulePath+"/search";
//	}
	/**
	 * 显示门禁授权table数据
	 * @return
	 */
	@RequestMapping("/showList.do")
	@ResponseBody
	public  Map<String, Object> showList(AppManage appManage){
		List<AppManage> list = new ArrayList<AppManage>();
		try {
			list = appManageBiz.showList(appManage,getLoginUser());
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
   	public String toSaveOrUpdate(String[] ids,AppManage appManage,Model model){
    	String opraterType = appManage.getOpraterType();
    	try {
	    	if(ids!=null&&ids.length>0){
				appManage = appManageBiz.get(ids[0]);
	    		appManage.setOpraterType(opraterType);
	    	}
	    	if(SysStatic.two.equals(appManage.getAppType())){
	    		List<AppManage> appList = appManageBiz.getOldVersion();
	    		model.addAttribute("appList",appList);
	    	}
	    	//门口机需要选择类型
	    	if("1".equals(appManage.getAppType())){
	    		//获取软件型号列表
	    		List<String> softwareTypeList = doorMachineBiz.getSoftwareTypeList();
	    		model.addAttribute("softwareTypeList",softwareTypeList);
	    	}
	    	model.addAttribute("appManage",appManage);
	    	List<IPManage> ipManageList = iPManageBiz.showList(new IPManage(), getLoginUser());
	    	model.addAttribute("ipManageList",ipManageList);

    	} catch (BizException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
   		return "/appManage/addOrEdit";
   	}
    /**
     * 保存或修改
     * @param user
     * @param model
     * @return
     */
    @RequestMapping(value="/saveOrUpdate.do",method=RequestMethod.POST)
    @ResponseBody
    public String save(HttpServletRequest request,HttpSession session,AppManage appManage,Model model){
    	try {
    		if(!"IOS".equals(appManage.getThreeAppType())){
	    		if(StringUtils.isBlank(appManage.getAppName())){
	    			return "APP名称不能为空！";
	    		}
	    		if(StringUtils.isBlank(appManage.getVersionName())){
	    			return "版本名称不能为空！";
	    		}
	    		if(StringUtils.isBlank(appManage.getVersionCode())){
	    			return "版本号不能为空！";
	    		}
	    		if(StringUtils.isBlank(appManage.getAppName())){
	    			return "APP名称不能为空！";
	    		}
    		}else{
    			if(StringUtils.isBlank(appManage.getAppName())){
    				return "APP名称不能为空！";
	    		}
    		}
    		if(StringUtils.isBlank(appManage.getVersionDes())){
    			return "版本说明不能为空！";
    		}else{
    			//过滤特殊字符
        		for(String s:SysStatic.SPECIALSTRING){
        			if(appManage.getVersionDes().contains(s)){
        				return "您提交的相关表单数据字符含有非法字符!";
        			}
        		}
    		}
    		if(StringUtils.isBlank(appManage.getId())&&StringUtils.isNotBlank(appManage.getMd5Value())){
    			boolean b = appManageBiz.checkVersion(appManage.getMd5Value());
    			if(b){
    				return "软件版本已经存在！";
    			}
    		}
				appManageBiz.saveOrUpdate(appManage,getLoginUser());
		} catch (BizException e) {
			e.printStackTrace();
			return "操作失败！";
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return "app上传失败！";
		} catch (IOException e) {
			e.printStackTrace();
			return "app上传失败！";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 return null;
    }
    
    @RequestMapping("/progress.do")
    @ResponseBody
    public Progress initCreateInfo(HttpSession session) {
    	System.out.println(session.getId());
        Progress status = (Progress) session.getAttribute("upload_ps");
        return status;
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
				appManageBiz.delete(ids);
			} catch (Exception e) {
				e.printStackTrace();
				return  "删除出错";
			}
		}
		return null;
	}
	
	/**
     * 软件版本已经存在
     * @param appManage
     * @return
     */
	@RequestMapping("/checkVersion.do")
	@ResponseBody
	public String checkVersion(AppManage appManage){
		try {
			boolean b = appManageBiz.checkVersion(appManage.getMd5Value());
			if(b){
				return "软件版本已经存在！";
			}
		} catch (BizException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	  /** 
     * process 获取进度 
     * @param request 
     * @param response 
     * @return 
     * @throws Exception 
      
    @RequestMapping(value = "/process.json", method = RequestMethod.GET)  
    @ResponseBody  
    public Object process(HttpServletRequest request,HttpServletResponse response) throws Exception {  
        return ( ProcessInfo)request.getSession().getAttribute("proInfo");  
    }  	*/ 

}
