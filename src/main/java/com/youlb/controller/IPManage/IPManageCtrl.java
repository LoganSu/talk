package com.youlb.controller.IPManage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.youlb.biz.IPManage.IIPManageBiz;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.entity.IPManage.IPManage;
import com.youlb.utils.common.RegexpUtils;
import com.youlb.utils.exception.BizException;
/**
 * 
* @ClassName: IPManageCtrl.java 
* @Description: 运营商ip管理 
* @author: Pengjy
* @date: 2016年9月1日
*
 */
@Controller
@RequestMapping("/mc/IPManage")
public class IPManageCtrl extends BaseCtrl {
	private static Logger log = LoggerFactory.getLogger(IPManageCtrl.class);

	@Autowired
	private IIPManageBiz iPManageBiz;
	public void setiPManageBiz(IIPManageBiz iPManageBiz) {
		this.iPManageBiz = iPManageBiz;
	}

	/**
	 * 显示table数据
	 * @return
	 */
	@RequestMapping("/showList.do")
	@ResponseBody
	public  Map<String, Object> showList(IPManage iPManage){
		List<IPManage> list = new ArrayList<IPManage>();
		try {
			list = iPManageBiz.showList(iPManage,getLoginUser());
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
				IPManage iPManage = iPManageBiz.get(ids[0]);
				model.addAttribute("iPManage", iPManage);
			} catch (BizException e) {
				log.error("获取单条数据失败");
				e.printStackTrace();
			}
    	}
   		return "/IPManage/addOrEdit";
   	}
    /**
     * 保存或修改
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("/saveOrUpdate.do")
    @ResponseBody
    public String save(IPManage iPManage,Model model){
//    	if(StringUtils.isBlank(iPManage.getIp())||!RegexpUtils.checkIpAddress(iPManage.getIp())){
    	if(StringUtils.isBlank(iPManage.getIp())){
    		return "访问IP不能为空！";
    	}
    	if(iPManage.getPort()!=null){
    		if(iPManage.getPort()>65535||!RegexpUtils.checkNumber(iPManage.getPort()+"")){
    			return "请填写正确的https端口！";
    		}
    	}else{
    		return "https端口不能为空！";
    	}
    	
    	if(StringUtils.isBlank(iPManage.getHttpIp())){
    		return "服务器真实IP不能为空！";
    	}
    	if(iPManage.getHttpPort()!=null){
    		if(iPManage.getHttpPort()>65535||!RegexpUtils.checkNumber(iPManage.getHttpPort()+"")){
    			return "请填写正确的http端口！";
    		}
    	}else{
    		return "http端口不能为空！";
    	}
    	if(StringUtils.isBlank(iPManage.getPlatformName())){
    		return "平台名称不能为空！";
    	}
    	if(StringUtils.isBlank(iPManage.getNeibName())){
    		return "社区名称不能为空！";
    	}else{
			try {
				boolean b = iPManageBiz.checkNeibName(iPManage);
				if(b){
					return "社区名称已经存在！";
				}
			} catch (BizException e) {
 				e.printStackTrace();
			}
    	}
    	
//    	if(StringUtils.isBlank(iPManage.getFsIp())||!RegexpUtils.checkIpAddress(iPManage.getFsIp())){
    	if(StringUtils.isBlank(iPManage.getFsIp())){
    		return "FS ip地址不能为空！";
    	}
    	if(iPManage.getFsPort()!=null){
    		if(iPManage.getFsPort()>65535||!RegexpUtils.checkNumber(iPManage.getFsPort()+"")){
    			return "请填写正确的fs端口！";
    		}
    	}else{
    		return "fs端口不能为空！";
    	}
    	if(iPManage.getFsExternalPort()!=null){
    		if(iPManage.getFsExternalPort()>65535||!RegexpUtils.checkNumber(iPManage.getFsExternalPort()+"")){
    			return "请填写正确的fs外呼端口！";
    		}
    	}else{
    		return "fs外呼端口不能为空！";
    	}
    	try {
    		
    		iPManageBiz.saveOrUpdate(iPManage,getLoginUser());
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
	public String delete(String[] ids,Model model){
		if(ids!=null&&ids.length>0){
			try {
				iPManageBiz.delete(ids);
			} catch (Exception e) {
				return "删除出错";
			}
		}
		return null;
	}
	
}
