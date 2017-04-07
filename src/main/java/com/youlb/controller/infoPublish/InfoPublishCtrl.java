package com.youlb.controller.infoPublish;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
				//选择区域发送处理数据回显
				if("2".equals(infoPublish.getSendType())){
					List<String> parentIds = infoPublishBiz.getParentIds(ids[0]);
					model.addAttribute("parentIds",parentIds);
				}
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
    		return "标题名称不能为空！";
    	}
    	if(StringUtils.isBlank(infoPublish.getInfoDetail())){
    		return "内容不能为空！";
    	}else{
    		if(infoPublish.getInfoDetail().length()>200){
    			return "内容长度超出200字符！";
    		}
    	}
    	if(StringUtils.isBlank(infoPublish.getStartTime())){
    		return "生效时间不能为空！";
    	}
    	Date startTime = DateHelper.strParseDate(infoPublish.getStartTime(), "yyyy-MM-dd HH:mm:ss");
    	if(new Date().getTime()>startTime.getTime()){
    		return "生效时间要在今天以后！";
    	}
    	if(StringUtils.isBlank(infoPublish.getExpDateStr())){
    		return "截止时间不能为空！";
    	}
    	Date expDate = DateHelper.strParseDate(infoPublish.getExpDateStr(), "yyyy-MM-dd HH:mm:ss");
    	if(new Date().getTime()>expDate.getTime()){
    		return "截止时间要在今天以后！";
    	}
    	if(StringUtils.isBlank(infoPublish.getInfoSign())){
    		return "署名不能为空！";
    	}
    	if("2".equals(infoPublish.getSendType())){
    		List<String> treecheckbox = infoPublish.getTreecheckbox();
    		if(treecheckbox==null||treecheckbox.size()!=1){
    			return "请选择一个域发布信息！";
    		}
    	}
    	try {
    		infoPublishBiz.saveOrUpdate(infoPublish,getLoginUser());
		} catch (Exception e) {
			e.printStackTrace();
			//TODO log
			return "操作失败！";
		}
    	 return  null;
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
				return "发布出错";
			}
		}
		return null;
	}
	 /**
     * 撤回
     * @param ids
     * @param model
     * @return
     */
	@RequestMapping("/recall.do")
	@ResponseBody
	public String recall(String[] ids,Model model){
		if(ids!=null&&ids.length>0){
			try {
				int i = infoPublishBiz.recall(ids,getLoginUser());
				if(i<1){
					return "撤回失败";
				}
			} catch (Exception e) {
				return "撤回出错";
			}
		}
		return null;
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
				return  "删除出错";
			}
		}
		return null;
	}
	
}
