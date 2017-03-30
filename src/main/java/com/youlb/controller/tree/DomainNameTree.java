package com.youlb.controller.tree;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youlb.biz.domainName.IDomainNameBiz;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.entity.domainName.DomainName;
import com.youlb.entity.privilege.Operator;
import com.youlb.entity.vo.QJson;
import com.youlb.entity.vo.QTree;
import com.youlb.utils.exception.BizException;
@Controller
@RequestMapping("/mc/domainNameTree")
public class DomainNameTree extends BaseCtrl {
	private static Logger log = LoggerFactory.getLogger(privilegeTreeCtrl.class);

	@Autowired
	private ServletContext servletContext;
	@Autowired
    private IDomainNameBiz domainNameBiz;
	public void setDomainNameBiz(IDomainNameBiz domainNameBiz) {
		this.domainNameBiz = domainNameBiz;
	}
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	/**
     * 树状数据返回
     * @return
     */
	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	@ResponseBody 
    public QJson tree() {
		QJson json = new QJson();
		try {
			//获取根路径
			String path = servletContext.getContextPath();
			Operator loginUser = getLoginUser();
			//第一级菜单
			QTree t = new QTree();
			t.setText("");
			List<DomainName> authList = domainNameBiz.showList(new DomainName(), loginUser);
			List<QTree> alist = domainNameList(authList, path ,loginUser);
			t.setChildren(alist);
			json.setMsg("OK");
			json.setObject(t);
			json.setSuccess(true);
			json.setType("1");
		} catch (BizException e) {
			log.error("获取树状结构数据失败");
			e.printStackTrace();
		}
		
		return json;
	}
	
	/**
	 * 递归获取权限树数据
	 * @param list
	 * @param path
	 * @return
	 * @throws BizException 
	 */
    private List<QTree> domainNameList(List<DomainName> list,String path,Operator loginUser) throws BizException{
    	List<QTree> treeList = new ArrayList<QTree>();
    	if(list!=null&&!list.isEmpty()){
			for(DomainName a:list){
				List<DomainName> privLis = domainNameBiz.showList(a,loginUser);
				if(privLis!=null){
					QTree atree = new QTree();
					List<QTree> authorityList = domainNameList(privLis,path, loginUser);
					atree.setId(a.getId());
					atree.setText(a.getFname());
					atree.setChildren(authorityList);
					atree.setUrl(path+"/mc/domainName/domainNameListshowPage.do?module=domainNameTable&modulePath=/domainName&id="+a.getId());
					treeList.add(atree);
				}
			}
		}
    	return treeList;
	}
}
