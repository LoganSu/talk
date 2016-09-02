package com.youlb.controller.monitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youlb.biz.countManage.IDeviceCountBiz;
import com.youlb.biz.monitor.IRealTimeMonitorBiz;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.entity.countManage.DeviceCount;
import com.youlb.entity.infoPublish.AdPublish;
import com.youlb.entity.monitor.PointInfo;
import com.youlb.entity.monitor.RealTimeMonitor;
/**
 * 
* @ClassName: RealTimeMonitorCtrl.java 
* @Description: 实时监控控制类 
* @author: Pengjy
* @date: 2016年8月24日
*
 */
@Controller
@Scope("prototype")
@RequestMapping("/mc/realTimeMonitor")
public class RealTimeMonitorCtrl extends BaseCtrl {
	private static Logger logger = LoggerFactory.getLogger(RealTimeMonitorCtrl.class);
	@Autowired
    private IRealTimeMonitorBiz realTimeMonitorBiz;
	public void setRealTimeMonitorBiz(IRealTimeMonitorBiz realTimeMonitorBiz) {
		this.realTimeMonitorBiz = realTimeMonitorBiz;
	}
	 @Autowired
	private IDeviceCountBiz deviceCountBiz;
	public void setDeviceCountBiz(IDeviceCountBiz deviceCountBiz) {
		this.deviceCountBiz = deviceCountBiz;
	}
	
	@Autowired
	private ServletContext servletContext;
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	/**
	 * 显示table数据
	 * @return
	 */
	@RequestMapping("/showList.do")
	@ResponseBody
	public  Map<String, Object> showList(RealTimeMonitor realTimeMonitor){
		List<RealTimeMonitor> list = new ArrayList<RealTimeMonitor>();
		try {
			list = realTimeMonitorBiz.showList(realTimeMonitor,getLoginUser());
		} catch (Exception e) {
			logger.error("获取列表出错");
		}
		return setRows(list);
	}
	
	/**
	 * 页面跳转
	 * @return
	 */
	@RequestMapping("/realTimeMonitor.do")
	public String realTimeMonitor(RealTimeMonitor realTimeMonitor){
		return "/realTimeMonitor/realTimeMonitor";
	}
	/**
	 * 删除作用域里面的id
	 * @return
	 */
	@RequestMapping("/removeRealTimeMonitorId.do")
	@ResponseBody
	public String removeRealTimeMonitorId(String[] ids){
		List<String> newList = (List<String>)servletContext.getAttribute("realTimeMonitorIds");
		if(ids!=null){
			logger.info("ids："+Arrays.toString(ids));
			for(String id :ids){
				newList.remove(id);
			}
			servletContext.setAttribute("realTimeMonitorIds", newList);
		}
		return null;
	}
	
	
	/**
	 * 获取作用域里面的id
	 * @return
	 */
	@RequestMapping("/getRealTimeMonitorId.do")
	@ResponseBody
	public List<String> getRealTimeMonitorId(){
		return (List<String>) servletContext.getAttribute("realTimeMonitorIds");
	}
	

	 /**
    * 跳转到提交告警备注页面
    * @return
    */
    @RequestMapping("/toDisposeEvent.do")
  	public String toDisposeEvent(String id,AdPublish adPublish,Model model){
	   RealTimeMonitor realTimeMonitor = realTimeMonitorBiz.get(id);
	   DeviceCount deviceCount = deviceCountBiz.getByCount(realTimeMonitor.getDeviceCount());
	   realTimeMonitor.setLatitude(deviceCount.getLatitude());
	   realTimeMonitor.setLongitude(deviceCount.getLongitude());
	    model.addAttribute("realTimeMonitor", realTimeMonitor);
  		return "/realTimeMonitor/disposeEvent";
  	}
    
    /**
     * 提交告警备注
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("/disposeEvent.do")
    @ResponseBody
    public String save(RealTimeMonitor realTimeMonitor,Model model){
    	try {
    		if(StringUtils.isBlank(realTimeMonitor.getId())||StringUtils.isBlank(realTimeMonitor.getRemark())){
    			super.message = "所需参数为空！";
    			return  super.message;
    		}
    		realTimeMonitorBiz.update(realTimeMonitor);
		} catch (Exception e) {
			super.message = "操作失败！";
			e.printStackTrace();
			logger.error("提交告警备注失败");
		}
    	 return  super.message;
    }
    
    	/**
         * 获取告警数据
         * @param user
         * @param model
         * @return
         */
        @RequestMapping("/getData.do")
        @ResponseBody
        public List<PointInfo> getData(String[] ids,Model model){
        	System.out.println(ids);
        	List<PointInfo> info =  realTimeMonitorBiz.getData(ids);
//        	 List<PointInfo> info = new ArrayList<PointInfo>();
//        	 PointInfo p = new PointInfo(113.383748,23.148225, "顺盈大厦", "广州市天河区棠福路1号", "020-88888888");
//        	 info.add(p);
        	return info;
        }
}
