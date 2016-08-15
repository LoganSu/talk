package com.youlb.controller.access;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youlb.biz.access.IDeviceBiz;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.entity.access.DeviceInfo;

/** 
 * @ClassName: DeviceCtrl.java 
 * @Description: 设备管理 
 * @author: Pengjy
 * @date: 2015-11-23
 * 
 */
@Controller
@Scope("prototype")
@RequestMapping("/mc/device")
public class DeviceCtrl extends BaseCtrl{
	@Autowired
    private IDeviceBiz deviceBiz;
	public void setDeviceBiz(IDeviceBiz deviceBiz) {
		this.deviceBiz = deviceBiz;
	}
	/**
	 * 显示门禁授权table数据
	 * @return
	 */
	@RequestMapping("/showList.do")
	@ResponseBody
	public  Map<String, Object> showList(DeviceInfo device){
		List<DeviceInfo> list = new ArrayList<DeviceInfo>();
		try {
			list = deviceBiz.showList(device,getLoginUser());
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
   	public String toSaveOrUpdate(String[] ids,DeviceInfo device,Model model){
    	if(ids!=null&&ids.length>0){
    		 device = deviceBiz.get(ids[0]);
    	}
    	//获取父节点
//    	if(StringUtils.isNotBlank(domain.getParentId())){
//    		Domain parentDomain = domainBiz.get(domain.getParentId());
//    		domain.setParentName(parentDomain.getName());//父节点名称
//    		domain.setLevel(parentDomain.getLevel());//父节点等级
//    	}else{
//    		domain.setParentId("1");
//    	}
    	model.addAttribute("device",device);
   		return "/device/addOrEdit";
   	}
    /**
     * 保存或修改
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("/saveOrUpdate.do")
    @ResponseBody
    public String save(DeviceInfo device,Model model){
    	try {
    		deviceBiz.saveOrUpdate(device,getLoginUser());
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
				deviceBiz.delete(ids);
			} catch (Exception e) {
				super.message =  "删除出错";
			}
		}
		return super.message;
	}
	/**
     * 设置密码
     * @param ids
     * @param model
     * @return
     */
	@RequestMapping("/toDeviceInfoSetPws.do")
	public String toDeviceInfoSetPws(DeviceInfo device,Model model){
		
		model.addAttribute("device",device);
		return "/device/deviceInfoSetPws";
	}
	/**
     * 设置密码
     * @param ids
     * @param model
     * @return
     */
	@RequestMapping("/deviceInfoSetPws.do")
	@ResponseBody
	public String deviceInfoSetPws(DeviceInfo device,Model model){
			try {
				deviceBiz.update(device);
			} catch (Exception e) {
				super.message =  "设置密码出错";
			}
		return super.message;
	}
	
}
