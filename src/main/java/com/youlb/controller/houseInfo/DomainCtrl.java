package com.youlb.controller.houseInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youlb.biz.houseInfo.IDomainBiz;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.entity.common.Domain;
import com.youlb.utils.exception.BizException;
@Controller
@RequestMapping("/mc/domain")
public class DomainCtrl extends BaseCtrl {
	@Autowired
	private IDomainBiz domainBiz;
	@Autowired
	private ServletContext servletContext;
	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	public void setDomainBiz(IDomainBiz domainBiz) {
		this.domainBiz = domainBiz;
	}
	@RequestMapping("/getNodes.do")
	@ResponseBody
	public List<HashMap<String,Object>> getNodes(String id,String name,Integer level,String nocheckLevel,Boolean isAll){
		List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		//最顶级
		if(id==null){
			id="1";
		}
		//默认显示全部节点
		if(isAll==null){
			isAll=true;
		}
		level = level==null?0:level+1;
		try {
			List<Domain> domainList = domainBiz.getDomainByParentId(id,getLoginUser(),isAll);
			if(domainList!=null&&!domainList.isEmpty()){
				for(Domain domain:domainList){
					HashMap<String,Object> hm = new HashMap<String,Object>();   
					hm.put("id",domain.getId());//id属性  ，数据传递
					hm.put("name", domain.getRemark()); //name属性，显示节点名称 
					hm.put("level", level);//设置层级
					if(StringUtils.isNotBlank(nocheckLevel)){
						if(nocheckLevel.contains(level+"")){
						   hm.put("nocheck", true);
						}
					}
					List<Domain> child = domainBiz.getDomainByParentId(domain.getId(),getLoginUser(),isAll);
					if(child!=null&&!child.isEmpty()){
						hm.put("isParent", true);
					}else{
						//普通用户在没有子节点的时候 需要显示子节点页面 最末级不显示
						if(getLoginUser().getIsAdmin()!=2&&level!=4){
							hm.put("isParent", true);
						}else{
						    hm.put("isParent", false);
						}
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
