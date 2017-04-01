package com.youlb.controller.staticParam;

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

import com.youlb.biz.staticParam.IStaticParamBiz;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.entity.staticParam.StaticParam;
import com.youlb.utils.common.SysStatic;
import com.youlb.utils.exception.BizException;
@Controller
@RequestMapping("/mc/staticParam")
public class StaticParamCtrl extends BaseCtrl{
	private static Logger log = LoggerFactory.getLogger(StaticParamCtrl.class);

	@Autowired
    private IStaticParamBiz staticParamBiz;
	public void setStaticParamBiz(IStaticParamBiz staticParamBiz) {
		this.staticParamBiz = staticParamBiz;
	}
	/**
	 * 显示table数据
	 * @return
	 */
	@RequestMapping("/showList.do")
	@ResponseBody
	public  Map<String, Object> showList(StaticParam staticParam){
		List<StaticParam> list = new ArrayList<StaticParam>();
		try {
			list = staticParamBiz.showList(staticParam,getLoginUser());
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
				StaticParam staticParam = staticParamBiz.get(ids[0]);
				model.addAttribute("staticParam", staticParam);
			} catch (BizException e) {
				log.error("获取列表数据失败");
				e.printStackTrace();
			}
    	}
   		return "/staticParam/addOrEdit";
   	}
    /**
     * 保存或修改
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("/saveOrUpdate.do")
    @ResponseBody
    public String save(StaticParam staticParam,Model model){
    	try {
    		//添加的时候判断key是否有重复
    		if(StringUtils.isBlank(staticParam.getId())){
    			if(StringUtils.isBlank(staticParam.getFkey())){
    				return "key不能为空！";
    			}
    			if(StringUtils.isBlank(staticParam.getFvalue())){
    				return "参数值不能为空！";
    			}
    			if(StringUtils.isBlank(staticParam.getFdescr())){
    				return "参数描述不能为空！";
    			}
    			
    			staticParam.setId(null);
    			StaticParam s = staticParamBiz.getParamByKey(staticParam.getFkey());
    			if(s!=null){
    				return "key值已经存在！";
    			}
    		}
    		//修改参数
    		if("fileUploadIp".equals(staticParam.getFkey())){
    			SysStatic.FILEUPLOADIP=staticParam.getFvalue();
    		}
    		staticParamBiz.saveOrUpdate(staticParam,getLoginUser());
		} catch (Exception e) {
			e.printStackTrace();
			return "操作失败！";
			//TODO log
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
				staticParamBiz.delete(ids);
			} catch (Exception e) {
				return "删除出错";
			}
		}
		return null;
	}
	
}
