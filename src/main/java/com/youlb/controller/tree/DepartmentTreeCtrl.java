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

import com.youlb.biz.management.IDepartmentBiz;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.dao.common.BaseDaoBySql;
import com.youlb.entity.management.Department;
import com.youlb.entity.privilege.Operator;
import com.youlb.entity.vo.QJson;
import com.youlb.entity.vo.QTree;


@Controller
@RequestMapping("/mc/departmentTree")
@Scope("prototype")
public class DepartmentTreeCtrl extends BaseCtrl {
	@Autowired
	private ServletContext servletContext;
	@Autowired
    private BaseDaoBySql<Department> departmentDao;
	@Autowired
	private IDepartmentBiz departmentBiz;
	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	public void setDepartmentDao(BaseDaoBySql<Department> departmentDao) {
		this.departmentDao = departmentDao;
	}
	public void setDepartmentBiz(IDepartmentBiz departmentBiz) {
		this.departmentBiz = departmentBiz;
	}
	/**
	 *  * 树状数据返回
	 * @param isDweller 住户信息选择房间参数
	 * @return
	 */
    
	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	@ResponseBody 
    public QJson tree(String dwellerId) {
		//获取根路径
		String path = servletContext.getContextPath();
		Operator loginUser = getLoginUser();
		QJson json = new QJson();
		QTree t = new QTree();
		t.setText("");
		List<Department> topList = departmentBiz.showList(new Department(),loginUser);
		List<QTree> children = getDepartmentList(topList,loginUser,path);
		t.setUrl("checkfalse");//url字段标识不需要显示多选框
		t.setChildren(children);
		t.setChecked(true);
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
    private List<QTree> getDepartmentList(List<Department> list,Operator loginUser,String path){
    	List<QTree> treeList = new ArrayList<QTree>();
    	if(list!=null&&!list.isEmpty()){
			for(Department a:list){
				a.setParentId(a.getId());
				List<Department> departLis = departmentBiz.showList(a,loginUser);
				if(departLis!=null){
					QTree atree = new QTree();
					List<QTree> authorityList = getDepartmentList(departLis, loginUser,path);
					atree.setId(a.getId());
					atree.setText(a.getDepartmentName());
					atree.setChildren(authorityList);
					atree.setUrl(path+"/mc/department/departmentshowPage.do?module=departmentTable&modulePath=/department&parentId="+a.getId());
					treeList.add(atree);
				}
			}
		}
    	return treeList;
	}
}
