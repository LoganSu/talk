package com.youlb.controller.domainName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youlb.biz.domainName.IDomainNameBiz;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.entity.domainName.DomainName;
import com.youlb.utils.exception.BizException;

@Controller
@RequestMapping("/mc/domainName")
public class DomainNameCtrl extends BaseCtrl {
	@Autowired
    private IDomainNameBiz domainNameBiz;
	public void setDomainNameBiz(IDomainNameBiz domainNameBiz) {
		this.domainNameBiz = domainNameBiz;
	}
   
	
	/**
	 * 显示table数据
	 * @return
	 */
	@RequestMapping("/showList.do")
	@ResponseBody
	public  Map<String, Object> showList(DomainName domainName){
		List<DomainName> list = new ArrayList<DomainName>();
		try {
			list = domainNameBiz.showList(domainName,getLoginUser());
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
   	public String toSaveOrUpdate(String[] ids,String parentid,Model model){
    	if(StringUtils.isNotBlank(parentid)){
    		try {
				DomainName parentDomain = domainNameBiz.get(parentid);
				model.addAttribute("parentDomain", parentDomain);
			} catch (BizException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	if(ids!=null&&ids.length>0){
			try {
				DomainName domainName = domainNameBiz.get(ids[0]);
//				domainName.setDomain(domainName.getDomain().substring(0, domainName.getDomain().indexOf(".")));
				model.addAttribute("domainName", domainName);
			} catch (BizException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
   		return "/domainName/addOrEdit";
   	}
    /**
     * 保存或修改
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("/saveOrUpdate.do")
    @ResponseBody
    public String save(DomainName domainName,Model model){
    	try {
    		
    		if(StringUtils.isBlank(domainName.getFname())){
    			return "名称不能为空";
    		}
    		if(StringUtils.isBlank(domainName.getDomain())){
    			return "域名不能为空";
    		}else{
    			if(1>domainName.getDomain().length()||domainName.getDomain().length()>100){
    				return "域名为1~100个字符组成";
    			}
    		}
//    		if(StringUtils.isNotBlank(domainName.getId())){
//    			String s = "11,12,13,14";
//    			if(s.contains(domainName.getId())){
//    				return "该条数据不能修改";
//    			}
//    		}
    		
    		domainNameBiz.saveOrUpdate(domainName,getLoginUser());
		} catch (Exception e) {
			e.printStackTrace();
			return "操作失败！";
			//TODO log
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
				String s = "11,12,13,14";
				for(String id:ids){
					if(s.contains(id)){
						return "该条数据不能修改";
					}
				}
				domainNameBiz.delete(ids);
			} catch (Exception e) {
				return  "删除出错";
			}
		}
		return null;
	}
	
	@RequestMapping("/getNodes.do")
	@ResponseBody
	public List<HashMap<String,Object>> getNodes(String id,String name,Integer level,String nocheckLevel){
		List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		try {
			DomainName domainName = new DomainName();
			domainName.setParentid(id);
			List<DomainName> dList = domainNameBiz.showList(domainName,getLoginUser());
			if(dList!=null&&!dList.isEmpty()){
				for(DomainName d:dList){
					HashMap<String,Object> hm = new HashMap<String,Object>();   
					hm.put("id",d.getId());//id属性  ，数据传递
					hm.put("name", d.getFname()); //name属性，显示节点名称 
					hm.put("level", level==null?0:level+1);//设置层级
					if(StringUtils.isNotBlank(nocheckLevel)){
						if(nocheckLevel.contains(level+"")){
						   hm.put("nocheck", true);
						}
					}
					 domainName = new DomainName();
					 domainName.setParentid(d.getId());
					List<DomainName> child = domainNameBiz.showList(domainName,getLoginUser());
					if(child!=null&&!child.isEmpty()){
						hm.put("isParent", true);
					}else{
						hm.put("isParent", false);
					}
					hm.put("pId", id);  
					
					list.add(hm);  
				}  
			}
		} catch (BizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
