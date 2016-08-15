package com.youlb.utils.tag;


import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.common.SysStatic;



/**
 * 
 * @Title:操作权限控制标签 
 * @Desription:操作权限控制标签，通过传入权限标识名称判断标签体内容是否显示，auth属性为权限唯一名称，多个权限使用，分割
 * @ClassName:RoleTag.java
 * @Author:pengjingyu
 * @CreateDate:2015-7-8 
 */
public class RoleTag extends BodyTagSupport{
	/**权限细项名称*/
	private String auth;
	
	public String getAuth() {
		return auth;
	}
	
	public void setAuth(String auth) {
		this.auth = auth;
	}


	@Override
	public int doEndTag() throws JspException {
		return Tag.EVAL_PAGE;
	}
    
	/**
	 * 
	 * @ClassName: RoleTag.java
	 * @Description: 分配权限
	 * @Author:yaoxuetong
	 * @CreateDate:2013-6-7 下午6:16:30
	 * @return
	 * @throws JspException
	 */
	@Override
	public int doStartTag() throws JspException {
		Operator login  = (Operator) pageContext.getAttribute(SysStatic.LOGINUSER,PageContext.SESSION_SCOPE);
		if(login!=null){
			//分割权限
			auth = auth.replace("，", ",");
			String[] auths = auth.split(",");
			List<String> privilegeList  = login.getPrivilegeList();
			//特殊管理员权限过滤
			if(SysStatic.SPECIALADMIN.equals(login.getIsAdmin())){
				return Tag.EVAL_BODY_INCLUDE;
			}
			if((privilegeList!=null)){
				for (String checkAuth : auths) {
					if(privilegeList.contains(checkAuth)) {
						return Tag.EVAL_BODY_INCLUDE;
					}
				}
			}
			}
		return Tag.SKIP_BODY;
	}
}