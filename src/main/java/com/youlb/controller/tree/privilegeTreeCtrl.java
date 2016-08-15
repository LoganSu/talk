package com.youlb.controller.tree;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youlb.biz.privilege.IPrivilegeBiz;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.entity.privilege.Operator;
import com.youlb.entity.privilege.Privilege;
import com.youlb.entity.vo.QJson;
import com.youlb.entity.vo.QTree;

/** 
 * @ClassName: AuthorityTreeCtrl.java 
 * @Description: TODO 
 * @author: Pengjy
 * @date: 2015年8月12日
 * 
 */
@Controller
@RequestMapping("/mc/privilegeTree")
@Scope("prototype")
public class privilegeTreeCtrl extends BaseCtrl{
	
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private IPrivilegeBiz privilegeBiz;
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	public void setPrivilegeBiz(IPrivilegeBiz privilegeBiz) {
		this.privilegeBiz = privilegeBiz;
	}


	/**
     * 树状数据返回
     * @return
     */
	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	@ResponseBody 
    public QJson tree() {
		//获取根路径
		String path = servletContext.getContextPath();
		Operator loginUser = getLoginUser();
		//第一级菜单
		QTree t = new QTree();
		t.setText("权限列表");
		List<Privilege> authList = privilegeBiz.showList(new Privilege(), loginUser);
		List<QTree> alist = privilegeList(authList, path ,loginUser);
		t.setChildren(alist);
		
		QJson json = new QJson();
		json.setMsg("OK");
		json.setObject(t);
		json.setSuccess(true);
		json.setType("1");
		
		return json;
	}
	/**
	 * 递归获取权限树数据
	 * @param list
	 * @param path
	 * @return
	 */
    private List<QTree> privilegeList(List<Privilege> list,String path,Operator loginUser){
    	List<QTree> treeList = new ArrayList<QTree>();
    	if(list!=null&&!list.isEmpty()){
			for(Privilege a:list){
				List<Privilege> privLis = privilegeBiz.showListByParentId(a.getId(),loginUser);
				if(privLis!=null){
					QTree atree = new QTree();
					List<QTree> authorityList = privilegeList(privLis,path, loginUser);
					atree.setId(a.getId());
					atree.setText(a.getName());
					atree.setChildren(authorityList);
					atree.setUrl(path+"/mc/privilege/privilegeListshowPage.do?module=privilegeTable&modulePath=/privilege&parentId="+a.getId());
					treeList.add(atree);
				}
			}
		}
    	return treeList;
	}
}
