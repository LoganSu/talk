package com.youlb.controller.management;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youlb.biz.houseInfo.IRoomBiz;
import com.youlb.biz.management.IRepairsBiz;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.entity.management.Repairs;
import com.youlb.utils.common.RegexpUtils;
import com.youlb.utils.exception.BizException;

/**
 * 
* @ClassName: RepairsCtrl.java 
* @Description: 工单服务control 
* @author: Pengjy
* @date: 2016年6月13日
*
 */
@Controller
@RequestMapping("/mc/repairs")
public class RepairsCtrl extends BaseCtrl{
	private static Logger log = LoggerFactory.getLogger(BaseCtrl.class);

	@Autowired
	private IRepairsBiz repairsBiz;
	public void setRepairsBiz(IRepairsBiz repairsBiz) {
		this.repairsBiz = repairsBiz;
	}
	@Autowired
    private IRoomBiz roomBiz;
	public void setRoomBiz(IRoomBiz roomBiz) {
		this.roomBiz = roomBiz;
	}
	/**
	 * 显示table数据
	 * @return
	 */
	@RequestMapping("/showList.do")
	@ResponseBody
	public  Map<String, Object> showList(Repairs repairs){
		List<Repairs> list = new ArrayList<Repairs>();
		try {
			list = repairsBiz.showList(repairs,getLoginUser());
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
				Repairs repairs = repairsBiz.get(ids[0]);
				model.addAttribute("repairs",repairs);
			} catch (BizException e) {
				log.error("获取单条数据失败");
				e.printStackTrace();
			}
    	}
   		return "/repairs/addOrEdit";
   	}
    /**
     * 保存或修改
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("/saveOrUpdate.do")
    @ResponseBody
    public String save(Repairs repairs,Model model){
    	try {
    		if(StringUtils.isNotBlank(repairs.getId())){
    			Repairs r = repairsBiz.get(repairs.getId());
    			if(!"1".equals(r.getStatus())){
    				return "只能修改未处理的工单！";
    			}
    		}
    		
    		if(StringUtils.isBlank(repairs.getLinkman())){
    			return "联系人不能为空！";
    		}
    		if(StringUtils.isBlank(repairs.getPhone())){
    			return "联系电话不能为空！";
    		}else{
    			if(!RegexpUtils.checkMobile(repairs.getPhone())){
    				return "请填写正确的手机号码！";
    			}
    		}
    		if(StringUtils.isBlank(repairs.getDomainId())){
    			return "请选择地址！";
    		}
    		if(StringUtils.isBlank(repairs.getServiceContent())){
    			return "内容不能为空！";
    		}else{
    			if(repairs.getServiceContent().length()>200){
    				return "内容长度超出200字符！";
        		}
    		}
    		repairsBiz.saveOrUpdate(repairs,getLoginUser());
		} catch (Exception e) {
			e.printStackTrace();
			return "操作失败！";
			//TODO log
		}
    	 return  null;
    }
    @Override
    public String showPage(Model model) {
    	HttpServletRequest request = getRequest();
    	String orderNature = request.getParameter("orderNature");
    	//获取展现数字数据
		try {
			String[] countArr = repairsBiz.countArr(Integer.parseInt(orderNature),getLoginUser());
	    	model.addAttribute("all", countArr[0]);
	    	model.addAttribute("finish", countArr[1]);
	    	model.addAttribute("unfinish", countArr[2]);
	    	model.addAttribute("finishing", countArr[3]);
		} catch (NumberFormatException | BizException e) {
			log.error("获取数字显示出错");
			e.printStackTrace();
		}
    	return super.showPage(model);
    }
	
}
