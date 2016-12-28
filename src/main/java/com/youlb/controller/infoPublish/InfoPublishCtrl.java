package com.youlb.controller.infoPublish;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youlb.biz.infoPublish.IInfoPublishBiz;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.entity.infoPublish.InfoPublish;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.helper.DateHelper;

/** 
 * @ClassName: InfoPublish.java 
 * @Description: 信息发布 
 * @author: Pengjy
 * @date: 2015-12-1
 * 
 */
@Controller
@Scope("prototype")
@RequestMapping("/mc/infoPublish")
public class InfoPublishCtrl extends BaseCtrl {
	private static Logger log = LoggerFactory.getLogger(InfoPublishCtrl.class);

	@Autowired
    private IInfoPublishBiz infoPublishBiz;
	public void setInfoPublishBiz(IInfoPublishBiz infoPublishBiz) {
		this.infoPublishBiz = infoPublishBiz;
	}

	/**
	 * 显示门禁授权table数据
	 * @return
	 */
	@RequestMapping("/showList.do")
	@ResponseBody
	public  Map<String, Object> showList(InfoPublish infoPublish){
		List<InfoPublish> list = new ArrayList<InfoPublish>();
		try {
			list = infoPublishBiz.showList(infoPublish,getLoginUser());
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
   	public String toSaveOrUpdate(String[] ids,InfoPublish infoPublish,Model model){
    	String opraterType = infoPublish.getOpraterType();
    	if(ids!=null&&ids.length>0){
    		try {
				infoPublish = infoPublishBiz.get(ids[0]);
			} catch (BizException e) {
				log.error("获取单条数据失败");
				e.printStackTrace();
			}
    		//如果是全部推送类型不需要返回标签
    		if("1".equals(infoPublish.getSendType())){
    			infoPublish.setTreecheckbox(null);
    		}
    		infoPublish.setOpraterType(opraterType);
    	}
    	model.addAttribute("infoPublish",infoPublish);
   		return "/infoPublish/addOrEdit";
   	}
    /**
     * 保存或修改
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("/saveOrUpdate.do")
    @ResponseBody
    public String save(InfoPublish infoPublish,Model model){
    	if(StringUtils.isBlank(infoPublish.getTitle())){
    		super.message = "标题名称不能为空！";
			return  super.message;
    	}
    	if(StringUtils.isBlank(infoPublish.getInfoDetail())){
    		super.message = "内容不能为空！";
			return  super.message;
    	}
    	if(StringUtils.isBlank(infoPublish.getExpDateStr())){
    		super.message = "有效期不能为空！";
			return  super.message;
    	}
    	Date expDate = DateHelper.strParseDate(infoPublish.getExpDateStr(), "yyyy-MM-dd");
    	if(new Date().getTime()>expDate.getTime()){
    		super.message = "有效期要在今天以后！";
			return  super.message;
    	}
    	if("2".equals(infoPublish.getSendType())){
    		List<String> treecheckbox = infoPublish.getTreecheckbox();
    		if(treecheckbox==null||treecheckbox.size()!=1){
    			super.message = "请选择一个域发布信息！";
    			return  super.message;
    		}
    	}
    	try {
    		infoPublishBiz.saveOrUpdate(infoPublish,getLoginUser());
		} catch (Exception e) {
			super.message = "操作失败！";
			e.printStackTrace();
			//TODO log
		}
    	 return  super.message;
    }
    
    	
	 /**
     * 发布
     * @param ids
     * @param model
     * @return
     */
	@RequestMapping("/publish.do")
	@ResponseBody
	public String publish(String[] ids,Model model){
		if(ids!=null&&ids.length>0){
			try {
				infoPublishBiz.publish(ids,getLoginUser());
			} catch (Exception e) {
				super.message =  "发布出错";
			}
		}
		return super.message;
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
				infoPublishBiz.delete(ids,getLoginUser());
			} catch (Exception e) {
				super.message =  "删除出错";
			}
		}
		return super.message;
	}
}
